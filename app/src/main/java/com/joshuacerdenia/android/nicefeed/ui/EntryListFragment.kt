package com.joshuacerdenia.android.nicefeed.ui

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.*
import com.google.android.material.snackbar.Snackbar
import com.joshuacerdenia.android.nicefeed.R
import com.joshuacerdenia.android.nicefeed.data.local.SharedPreferences
import com.joshuacerdenia.android.nicefeed.data.model.Entry
import com.joshuacerdenia.android.nicefeed.data.model.Feed
import com.joshuacerdenia.android.nicefeed.utils.sortedByDate
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_entry_list.*
import java.text.DateFormat
import java.text.DateFormat.MEDIUM
import java.text.DateFormat.SHORT

private const val TAG = "EntryListFragment"
private const val MAX_ENTRIES = 30 // TODO: user setting

class EntryListFragment : Fragment() {

    companion object {
        private val websiteLiveData = MutableLiveData<String>()

        fun setWebsite(website: String?) {
            websiteLiveData.value = website
        }

        fun getWebsite(): String? {
            return websiteLiveData.value
        }

        fun newInstance(): EntryListFragment {
            return EntryListFragment()
        }
    }

    private var currentFeed: Feed? = null
    private var currentEntries: List<Entry> = listOf()
        get() = field.sortedByDate()

    private lateinit var progressBar: ProgressBar
    private lateinit var entryRecyclerView: RecyclerView
    private var adapter: EntryAdapter = EntryAdapter()

    private val entryListViewModel: EntryListViewModel by lazy {
        ViewModelProvider(this).get(EntryListViewModel::class.java)
    }

    private var callbacks: Callbacks? = null

    interface Callbacks {
        fun onFeedLoaded(feedTitle: String)
        fun onFeedDeleted()
        fun onEntrySelected(entry: Entry)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val savedWebsite = context?.let { SharedPreferences.getSavedWebsite(it) }
        setWebsite(
            savedWebsite
        )
        Log.d(TAG, "Fragment created. Found website: $savedWebsite")

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,

        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_entry_list, container, false)

        entryRecyclerView = view.findViewById(R.id.entry_recycler_view)

        entryRecyclerView.layoutManager = LinearLayoutManager(context)
        val divider = DividerItemDecoration(
            entryRecyclerView.context,
            (entryRecyclerView.layoutManager as LinearLayoutManager).orientation
        )
        entryRecyclerView.addItemDecoration(divider)
        entryRecyclerView.adapter = adapter

        progressBar = view.findViewById(R.id.progress_bar)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TODO: Enable auto-refresh?

        progressBar.visibility = View.VISIBLE

        websiteLiveData.observe(viewLifecycleOwner, Observer { website ->
            if (website != null) {
                entryListViewModel.getFeedWithEntries(website)
            } else {
                progressBar.visibility = View.GONE
            }
        })

        entryListViewModel.feedWithEntriesLiveData.observe(viewLifecycleOwner, Observer {
            var entries: List<Entry> = emptyList()

            it?.let {
                Log.d(TAG, "Retrieved ${it.feed.title}, ${it.entries.size} entries")
                it.feed.title?.let { title -> callbacks?.onFeedLoaded(title) }

                currentFeed = it.feed
                currentEntries = it.entries

                entries = it.entries.sortedByDate()
            } ?: callbacks?.onFeedDeleted()

            adapter.submitList(entries)
            progressBar.visibility = View.GONE
        })

        entryListViewModel.feedRequestLiveData?.observe(this, Observer {
            it?.let {
                Log.d(TAG, "Request successful. Got ${it.entries.size} entries")

                if (!entryListViewModel.newEntriesHaveBeenHandled) {
                    handleNewEntries(it.entries, currentEntries)

                    entryListViewModel.newEntriesHaveBeenHandled = true
                }
            }

            progressBar.visibility = View.GONE
        })
    }

    private fun getUnreadCount(entries: List<Entry>): Int {
        var unreadCount = 0
        for (entry in entries) {
            if (!entry.isRead) {
                unreadCount += 1
            }
        }
        return unreadCount
    }

    private fun handleNewEntries(newEntries: List<Entry>, currentEntries: List<Entry>) {
        val newEntriesByGuid = getEntriesByGuid(newEntries)
        val currentEntriesByGuid = getEntriesByGuid(currentEntries)

        val entriesToSave = mutableListOf<Entry>()
        val entriesToUpdate = mutableListOf<Entry>()
        val entriesToDelete = mutableListOf<Entry>()

        for (entry in newEntries) {
            if (!isAddedAndUnchanged(entry, currentEntries)) {
                if (currentEntriesByGuid.contains(entry.guid)) {
                    // i.e., if an old version of the entry already exists
                    entriesToUpdate.add(entry)
                } else {
                    entriesToSave.add(entry)
                }
            }
        }

        for (entry in currentEntries) {
            if (!newEntriesByGuid.contains(entry.guid)) {
                entriesToDelete.add(entry)
            }
        }

        entryListViewModel.deleteEntries(entriesToDelete)
        entryListViewModel.saveEntries(entriesToSave)
        entryListViewModel.updateEntries(entriesToUpdate)

        Log.d(
            TAG, "Saving ${entriesToSave.size}, " +
                "updating ${entriesToUpdate.size}, and deleting ${entriesToDelete.size} entries...")
        showRefreshedNotice(entriesToSave.size, entriesToUpdate.size)
    }

    private fun getEntriesByGuid(entries: List<Entry>): List<String> {
        val entriesByGuid = mutableListOf<String>()
        for (entry in entries) {
            entriesByGuid.add(entry.guid)
        }
        return entriesByGuid
    }

    private fun isAddedAndUnchanged(entry: Entry, currentEntries: List<Entry>): Boolean {
        // Checks a new entry against all current entries to see if the content is the same
        var isAddedAndUnchanged = false
        for (currentEntry in currentEntries) {
            if (entry.isTheSameAs(currentEntry)) {
                isAddedAndUnchanged = true
                break
            }
        }
        return isAddedAndUnchanged
    }

    private fun showRefreshedNotice(newCount: Int, updatedCount: Int) {
        val entriesAdded = resources.getQuantityString(R.plurals.numberOfNewEntries, newCount, newCount)
        val entriesUpdated = resources.getQuantityString(R.plurals.numberOfEntries, updatedCount, updatedCount)

        val message = when {
            newCount > 0 && updatedCount > 0 -> getString(R.string.added_and_updated, entriesAdded, updatedCount)
            newCount > 0 && updatedCount == 0 -> getString(R.string.added, entriesAdded)
            newCount == 0 && updatedCount > 0 -> getString(R.string.updated, entriesUpdated)
            else -> getString(R.string.feed_up_to_date)
        }

        Snackbar.make(entry_list_view, message as CharSequence, Snackbar.LENGTH_SHORT).show()
    }

    fun updateUnreadEntriesCount() {
        currentFeed?.let {
            it.unreadCount = getUnreadCount(currentEntries)
            entryListViewModel.updateFeed(it)
        }
    }

    fun scrollToTop() {
        entryRecyclerView.scrollToPosition(0)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_entry_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_item_refresh -> {
                val url = currentFeed?.url
                if (url != null) {
                    entryListViewModel.requestFeed(url)
                }
                progressBar.visibility = View.VISIBLE
                Log.d(TAG, "Requesting channel: $url")
                true
            }
            R.id.menu_item_delete_feed -> {
                if (currentFeed != null) {
                    Snackbar.make(
                        entry_list_view,
                        getString(R.string.feed_removed, currentFeed!!.title),
                        Snackbar.LENGTH_SHORT
                    ).show()
                    entryListViewModel.deleteFeedAndEntries(currentFeed!!, currentEntries)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onStop() {
        super.onStop()

        updateUnreadEntriesCount()

        if (context != null && getWebsite() != null) {
            SharedPreferences.saveWebsite(context!!, getWebsite()!!)
        }
    }

    private inner class EntryAdapter
        : ListAdapter<Entry, EntryAdapter.EntryHolder>(DiffCallback()) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryHolder {
            val view = layoutInflater.inflate(R.layout.list_item_entry, parent, false)
            return EntryHolder(view)
        }

        override fun onBindViewHolder(holder: EntryHolder, position: Int) {
            holder.bind(getItem(position))
        }

        private inner class EntryHolder(view: View)
            : RecyclerView.ViewHolder(view), View.OnClickListener, View.OnLongClickListener {

            private var currentEntry =
                Entry()

            val titleTextView: TextView = itemView.findViewById(R.id.title)
            val dateTextView: TextView = itemView.findViewById(R.id.date)
            val imageView: ImageView = itemView.findViewById(R.id.entry_image)

            init {
                itemView.setOnClickListener(this)
                itemView.setOnLongClickListener(this)
            }

            fun bind(entry: Entry) {
                currentEntry = entry

                titleTextView.text = entry.title
                dateTextView.text = entry.date?.let {
                    DateFormat.getDateTimeInstance(MEDIUM, SHORT).format(it)
                }

                Picasso.get()
                    .load(entry.image)
                    .resize(56, 56)
                    .centerCrop()
                    .placeholder(R.drawable.ic_entry)
                    .into(imageView)

                if (entry.isRead) {
                    titleTextView.setTextColor(Color.GRAY)
                } else {
                    titleTextView.setTextColor(Color.BLACK)
                }

                // TODO: Option to view/hide description
            }

            override fun onClick(v: View) {
                callbacks?.onEntrySelected(currentEntry)
            }

            override fun onLongClick(v: View?): Boolean {
                // TODO: Manage feeds
                entryListViewModel.isManagingFeeds = !entryListViewModel.isManagingFeeds

                Log.d(TAG, "Managing feeds: ${entryListViewModel.isManagingFeeds}")
                return true
            }
        }
    }

    private inner class DiffCallback : DiffUtil.ItemCallback<Entry>() {

        override fun areItemsTheSame(oldItem: Entry, newItem: Entry): Boolean {
            return oldItem.guid == newItem.guid
        }

        override fun areContentsTheSame(oldItem: Entry, newItem: Entry): Boolean {
            return oldItem == newItem
        }
    }
}