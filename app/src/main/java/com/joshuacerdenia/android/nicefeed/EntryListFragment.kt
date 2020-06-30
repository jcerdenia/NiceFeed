package com.joshuacerdenia.android.nicefeed

import android.content.Context
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
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_entry_list.*
import kotlinx.android.synthetic.main.fragment_feed_list.*

private const val TAG = "EntryListFragment"

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

    private val fragment = this@EntryListFragment
    private var callbacks: Callbacks? = null

    private lateinit var progressBar: ProgressBar
    private lateinit var entryRecyclerView: RecyclerView
    private var adapter: EntryAdapter = EntryAdapter()

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private var feed: Feed? = null
    private var entries: List<Entry>? = null

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

        /* // TODO: Enable auto-refresh?
        val url = feed?.url
        if (url != null) {
            if (mainViewModel.isNotYetRequested) {
                //Log.d(TAG, "Requesting channel: $url")
                //mainViewModel.requestChannel(url)

                //progressBar.visibility = View.VISIBLE
                mainViewModel.isNotYetRequested = false
            }
        } */

        getWebsite()?.let { mainViewModel.getFeedWithEntries(it) }
        progressBar.visibility = View.VISIBLE

        websiteLiveData.observe(this, Observer { website ->
            if (website != null) {
                mainViewModel.getFeedWithEntries(website)
            } else {
                progressBar.visibility = View.GONE
            }
        })

        mainViewModel.feedWithEntriesLiveData.observe(this, Observer { feedWithEntries ->
            if (feedWithEntries != null) {
                Log.d(TAG, "Found ${feedWithEntries.feed.title} with " +
                            "${feedWithEntries.entries.size} entries")

                // TODO: Do something about observer firing multiple times?

                feed = feedWithEntries.feed
                entries = feedWithEntries.entries

                feed?.title?.let { callbacks?.onFeedLoaded(it) }
                adapter.submitList(entries)

            } else {
                adapter.submitList(emptyList())
                callbacks?.onFeedDeleted()
            }

            progressBar.visibility = View.GONE
        })

        /*
        mainViewModel.feedRequestLiveData?.observe(this, Observer {
            // TODO: Receive and map data to Entries; add new data to database, ignore the rest
            if (it != null) {
                Log.d(TAG, "Retrieved ${it.feed.title}")
            }
            progressBar.visibility = View.GONE
        }) */
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_entry_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_item_refresh -> {
                val url = feed?.url
                if (url != null) {
                    mainViewModel.requestFeed(url)
                }
                progressBar.visibility = View.VISIBLE
                Log.d(TAG, "Requesting channel: $url")
                true
            }
            R.id.menu_item_delete_feed -> {
                if (feed != null && entries != null) {
                    Snackbar.make(
                        entry_list_view,
                        getString(R.string.feed_removed, feed!!.title),
                        Snackbar.LENGTH_SHORT
                    ).show()
                    mainViewModel.deleteFeedAndEntries(feed!!, entries!!)
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

    override fun onAttachFragment(childFragment: Fragment) {
        super.onAttachFragment(childFragment)
        Log.d(TAG, "onAttachFrag called")
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

            private var thisEntry = Entry()

            val titleTextView: TextView = itemView.findViewById(R.id.title)
            val dateTextView: TextView = itemView.findViewById(R.id.date)

            init {
                itemView.setOnClickListener(this)
            }

            fun bind(entry: Entry) {
                thisEntry = entry

                titleTextView.text = entry.title
                dateTextView.text = entry.date

                // TODO: Option to view/hide description
            }

            override fun onClick(v: View) {
                // TODO: View entry, mark as read
                Log.d(TAG, "${thisEntry.title} clicked!")
                //titleTextView.setTextColor(Color.GRAY)
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