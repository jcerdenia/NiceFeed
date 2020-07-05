package com.joshuacerdenia.android.nicefeed

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_entry_list.*

private const val TAG = "EntryListFragment"
private const val MAX_ENTRIES = 30 // TODO: user setting

private fun List<Entry>.sortedByDate() = this.sortedByDescending{ entry -> entry.date}

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

    private var callbacks: Callbacks? = null

    private lateinit var progressBar: ProgressBar
    private lateinit var entryRecyclerView: RecyclerView
    private var adapter: EntryAdapter = EntryAdapter()

    private val entryListViewModel: EntryListViewModel by lazy {
        ViewModelProvider(this).get(EntryListViewModel::class.java)
    }

    private var currentFeed: Feed? = null
    private var currentEntries: List<Entry> = listOf()

    interface Callbacks {
        fun onFeedLoaded(feedTitle: String)
        fun onFeedDeleted()
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
        setWebsite(savedWebsite)
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
            if (it != null) {
                Log.d(TAG, "Retrieved ${it.feed.title} with ${it.entries.size} entries")
                it.feed.title?.let { title -> callbacks?.onFeedLoaded(title) }

                currentFeed = it.feed
                currentEntries = it.entries

                if (it.entries.size > MAX_ENTRIES) {
                    val entriesToDelete = it.entries.sortedByDate().subList(
                        it.entries.sortedByDate().lastIndex - (it.entries.size - MAX_ENTRIES),
                        it.entries.sortedByDate().lastIndex
                    )
                    entryListViewModel.deleteEntries(entriesToDelete)
                    return@Observer
                }

                adapter.submitList(it.entries.sortedByDate())
            } else {
                adapter.submitList(emptyList())
                callbacks?.onFeedDeleted()
            }

            progressBar.visibility = View.GONE
        })

        entryListViewModel.feedRequestLiveData?.observe(this, Observer {
            it?.let {
                handleNewEntries(it.entries)
            }

            progressBar.visibility = View.GONE
        })
    }

    private fun handleNewEntries(entries: List<Entry>) {
        // Get all current entries by GUID
        val currentEntriesByGuid = mutableListOf<String>()
        for (entry in currentEntries) {
            currentEntriesByGuid.add(entry.guid)
        }

        // Determine if entries are new or updated by comparing them to current entries
        val newEntries = mutableListOf<Entry>()
        val updatedEntries = mutableListOf<Entry>()

        for (entry in entries) {
            if (!currentEntries.contains(entry)) {
                if (currentEntriesByGuid.contains(entry.guid)) {
                    updatedEntries.add(entry)
                } else {
                    newEntries.add(entry)
                }
            }
        }
        Log.d(TAG, "Refreshed. Got ${newEntries.size} new and ${updatedEntries.size} updated entries.")

        // Save to database
        entryListViewModel.saveEntries(newEntries)
        entryListViewModel.updateEntries(updatedEntries)

        if (newEntries.size > 0) {
            Snackbar.make(entry_list_view,
                getString(R.string.entries_added, newEntries.size.toString()),
                Snackbar.LENGTH_SHORT)
                .show()
            entryListViewModel.saveEntries(newEntries)
        }
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

        if (context != null && getWebsite() != null) {
            SharedPreferences.saveWebsite(context!!, getWebsite()!!)
            Log.d(TAG, "${getWebsite()} saved to Shared Prefererences.")
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
            : RecyclerView.ViewHolder(view), View.OnClickListener {

            private var currentEntry = Entry()

            val titleTextView: TextView = itemView.findViewById(R.id.title)
            val dateTextView: TextView = itemView.findViewById(R.id.date)

            init {
                itemView.setOnClickListener(this)
            }

            fun bind(entry: Entry) {
                currentEntry = entry

                titleTextView.text = entry.title
                dateTextView.text = entry.date.toString() // TODO: format

                if (!entry.isUnread) {
                    titleTextView.setTextColor(Color.GRAY)
                }

                // TODO: Option to view/hide description
            }

            override fun onClick(v: View) {
                // TODO: View entry, mark as read
                //currentEntry.isUnread = false
                Log.d(TAG, "Clicked ${currentEntry.title}. Unread: ${currentEntry.isUnread}")
                //entryListViewModel.updateEntry(currentEntry)
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