package com.joshuacerdenia.android.nicefeed.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.joshuacerdenia.android.nicefeed.R
import com.joshuacerdenia.android.nicefeed.data.local.UserPreferences
import com.joshuacerdenia.android.nicefeed.data.model.Entry
import com.joshuacerdenia.android.nicefeed.data.model.Feed
import com.joshuacerdenia.android.nicefeed.ui.dialog.AboutFeedFragment
import com.joshuacerdenia.android.nicefeed.ui.dialog.ConfirmRemoveFragment
import com.joshuacerdenia.android.nicefeed.ui.dialog.EditCategoryFragment
import com.joshuacerdenia.android.nicefeed.ui.dialog.SortFilterEntriesFragment
import com.joshuacerdenia.android.nicefeed.ui.menu.EntryPopupMenu
import com.joshuacerdenia.android.nicefeed.utils.RefreshManager
import com.joshuacerdenia.android.nicefeed.utils.sortedByDatePublished
import com.joshuacerdenia.android.nicefeed.utils.unreadOnTop

private const val TAG = "EntryListFragment"

class EntryListFragment : Fragment(),
    EntryListAdapter.OnItemClickListener,
    EntryPopupMenu.OnItemClickListener,
    RefreshManager.OnRefreshedListener,
    SortFilterEntriesFragment.Callbacks,
    AboutFeedFragment.Callbacks,
    EditCategoryFragment.Callbacks,
    ConfirmRemoveFragment.Callbacks {

    companion object {
        private const val ARG_FEED_ID = "ARG_FEED_ID"

        fun newInstance(feedId: String?): EntryListFragment {
            return EntryListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_FEED_ID, feedId)
                }
            }
        }
    }

    private val fragment = this@EntryListFragment
    private val viewModel: EntryListViewModel by lazy {
        ViewModelProvider(this).get(EntryListViewModel::class.java)
    }

    ///private lateinit var nestedScrollView: NestedScrollView
    private lateinit var emptyImage: ImageView
    private lateinit var filterNotice: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EntryListAdapter
    private lateinit var refreshManager: RefreshManager
    private var markAllOptionsItem: MenuItem? = null
    private var starAllOptionsItem: MenuItem? = null
    private val handler = Handler()
    private var callbacks: Callbacks? = null

    interface Callbacks {
        fun onFeedLoaded(feedId: String, title: String)
        fun onFeedRemoved()
        fun onEntrySelected(entry: Entry)
        fun onCategoriesNeeded(): List<String>
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
        adapter = EntryListAdapter(this)
        refreshManager = RefreshManager(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_entry_list, container, false)
        //nestedScrollView = view.findViewById(R.id.nestedScrollView)
        filterNotice = view.findViewById(R.id.textView_filter_notice)
        emptyImage = view.findViewById(R.id.imageView_empty)
        progressBar = view.findViewById(R.id.progressBar)
        recyclerView = view.findViewById(R.id.recyclerView_entry)

        //recyclerView.layoutManager = StaggeredGridLayoutManager(2, VERTICAL)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val feedId = arguments?.getString(ARG_FEED_ID)

        if (feedId != null) {
            progressBar.visibility = View.VISIBLE
            viewModel.getFeedWithEntries(feedId)
        } else {
            progressBar.visibility = View.GONE
        }

        emptyImage.setOnClickListener {
            callbacks?.onFeedRemoved()
        }

        viewModel.feedRequestLiveData?.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (!viewModel.newEntriesHaveBeenHandled) {
                    refreshManager.submitNew(it)
                    viewModel.newEntriesHaveBeenHandled = true
                }
            }
            
            progressBar.visibility = View.GONE
        })

        observeFeedWithEntriesLiveData()
    }

    private fun onFeedLoaded() {
        val feed = getCurrentFeed()

        if (!viewModel.hasAutoRefreshed) {
            handleRefresh()
            viewModel.hasAutoRefreshed = true
        }

        callbacks?.onFeedLoaded(feed?.website.toString(), feed?.title.toString()) // TODO Fix
    }

    private fun observeFeedWithEntriesLiveData() {
        viewModel.feedWithEntriesLiveData.observe(viewLifecycleOwner, Observer {
            var list = emptyList<Entry>()

            if (it != null) {
                val sortedList = sortAndFilterEntries(it.entries)
                refreshManager.submitCurrent(it)
                emptyImage.visibility = View.GONE
                list = sortedList
                onFeedLoaded()
            } else {
                callbacks?.onFeedRemoved()
                emptyImage.visibility = View.VISIBLE
                filterNotice.visibility = View.GONE
            }

            adapter.submitList(list)
            progressBar.visibility = View.GONE
            setHasOptionsMenu(it != null)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_entry_list, menu)
        markAllOptionsItem = menu.findItem(R.id.menuItem_mark_all)
        starAllOptionsItem = menu.findItem(R.id.menuItem_star_all)
        setMarkAllOptionsItem()
        setStarAllOptionsItem()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuItem_refresh -> handleRefresh()
            R.id.menuItem_about_feed -> showAboutFeed()
            R.id.menuItem_sort_filter -> handleSortAndFilter()
            R.id.menuItem_mark_all -> handleMarkAll()
            R.id.menuItem_star_all -> handleStarAll()
            R.id.menuItem_visit_website -> handleVisitWebsite()
            R.id.menuItem_delete_feed -> handleRemoveFeed()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun allAreRead(entries: List<Entry>): Boolean {
        var count = 0
        for (entry in entries) {
            if (entry.isRead) {
                count += 1
            } else break
        }
        return count == entries.size
    }

    private fun allAreStarred(entries: List<Entry>): Boolean {
        var count = 0
        for (entry in entries) {
            if (entry.isStarred) {
                count += 1
            } else break
        }
        return count == entries.size
    }

    private fun setMarkAllOptionsItem() {
        markAllOptionsItem?.title = if (allAreRead(getCurrentEntries())) {
            getString(R.string.mark_all_as_unread)
        } else {
            getString(R.string.mark_all_as_read)
        }
    }

    private fun setStarAllOptionsItem() {
        starAllOptionsItem?.title = if (allAreStarred(getCurrentEntries())) {
            getString(R.string.unstar_all)
        } else {
            getString(R.string.star_all)
        }
    }

    private fun handleRefresh(): Boolean {
        val url = getCurrentFeed()?.url
        url?.let {
            viewModel.requestFeed(it)
            progressBar.visibility = View.VISIBLE
        }
        return true
    }

    override fun onRefreshedFeed(feed: Feed) {
        viewModel.updateFeed(feed)
    }

    override fun onRefreshedEntries(
        toSave: List<Entry>,
        toUpdate: List<Entry>,
        toDelete: List<Entry>
    ) {
        viewModel.saveEntries(toSave)
        viewModel.updateEntries(toUpdate)
        viewModel.deleteEntries(toDelete)
        showRefreshedNotice(toSave.size, toUpdate.size)
    }

    private fun showAboutFeed(): Boolean {
        getCurrentFeed()?.let {
            AboutFeedFragment.newInstance(it).apply {
                setTargetFragment(fragment, 0)
                show(fragment.requireFragmentManager(),"about")
            }
        }
        return true
    }

    override fun onEditCategoryClicked() {
        val categories = callbacks?.onCategoriesNeeded() ?: emptyList()
        EditCategoryFragment.newInstance(categories, getCurrentFeed()?.title).apply {
            setTargetFragment(fragment, 0)
            show(fragment.requireFragmentManager(), "edit category")
        }
    }

    override fun onEditCategoryConfirmed(category: String) {
        getCurrentFeed()?.let {
            it.category = category
            viewModel.updateFeed(it)

            handler.postDelayed({
                Snackbar.make(
                    recyclerView,
                    getString(R.string.category_assigned, category, it.title),
                    Snackbar.LENGTH_SHORT
                ).show()
            }, 250)
        }
    }

    private fun handleSortAndFilter(): Boolean {
        SortFilterEntriesFragment.newInstance().apply {
            setTargetFragment(fragment, 0)
            show(fragment.requireFragmentManager(), "filter")
        }
        return true
    }

    override fun onSortFilterSubmitted() {
        observeFeedWithEntriesLiveData()
    }

    private fun sortAndFilterEntries(entries: List<Entry>): List<Entry> {
        val filter = context?.let { UserPreferences.getEntriesFilterPref(it) }
            ?: SortFilterEntriesFragment.FILTER_ALL
        val sorter = context?.let { UserPreferences.getEntriesSortPref(it) }
            ?: SortFilterEntriesFragment.SORT_BY_PUBLISHED

        val filteredEntries = when (filter) {
            SortFilterEntriesFragment.FILTER_UNREAD -> entries.filter { !it.isRead }
            SortFilterEntriesFragment.FILTER_STARRED -> entries.filter { it.isStarred }
            else -> entries
        }

        setFilterNotice(filter, (entries.size - filteredEntries.size))
        return if (sorter == SortFilterEntriesFragment.SORT_UNREAD_ON_TOP) {
            if (adapter.latestClickedPosition == 0) {
                // Crude, but good enough:
                handler.postDelayed({ recyclerView.scrollToPosition(0) }, 200)
            }
            filteredEntries.unreadOnTop()
        } else {
            filteredEntries.sortedByDatePublished()
        }
    }

    private fun handleMarkAll(): Boolean {
        val entries = getCurrentEntries()
        val allAreRead = allAreRead(entries)
        for (entry in entries) {
            entry.isRead = !allAreRead
        }

        viewModel.updateEntries(entries)
        setMarkAllOptionsItem()
        return true
    }

    private fun handleStarAll(): Boolean {
        val entries = getCurrentEntries()
        val allAreStarred = allAreStarred(entries)
        for (entry in entries) {
            entry.isStarred = !allAreStarred
        }

        viewModel.updateEntries(entries)
        setStarAllOptionsItem()
        return true
    }

    private fun handleVisitWebsite(): Boolean {
        getCurrentFeed()?.let {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.website))
            startActivity(intent)
        }
        return true
    }

    private fun handleRemoveFeed(): Boolean {
        ConfirmRemoveFragment.newInstance(getCurrentFeed()?.title).apply {
            setTargetFragment(fragment, 0)
            show(fragment.requireFragmentManager(),"unsubscribe")
        }
        return true
    }

    override fun onRemoveConfirmed(count: Int) {
        getCurrentFeed()?.let {
            viewModel.deleteFeedAndEntries(it, getCurrentEntries())

            Snackbar.make(
                recyclerView,
                getString(R.string.feed_removed, it.title),
                Snackbar.LENGTH_SHORT
            ).show()
        }
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

    fun updateUnreadEntriesCount() {
        getCurrentFeed()?.let {
            it.unreadCount = getUnreadCount(getCurrentEntries())
            viewModel.updateFeed(it)
        }
    }

    override fun onItemClicked(entry: Entry) {
        callbacks?.onEntrySelected(entry)
    }

    override fun onItemLongClicked(entry: Entry, view: View?) {
        val popupMenu = EntryPopupMenu(context, view, this, entry)
        popupMenu.show()
    }

    override fun onPopupMenuItemClicked(entry: Entry, action: Int) {
        when (action) {
            EntryPopupMenu.ACTION_MARK_AS -> entry.isRead = !entry.isRead
            EntryPopupMenu.ACTION_STAR -> entry.isStarred = !entry.isStarred
            else -> {
                callbacks?.onEntrySelected(entry)
                return
            }
        }

        viewModel.updateEntry(entry)
    }

    fun scrollToTop() {
        //nestedScrollView.smoothScrollTo(0, 0)
        recyclerView.smoothScrollToPosition(0)
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

        Snackbar.make(
            recyclerView,
            message as CharSequence,
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun setFilterNotice(filter: Int, diff: Int) {
        if (filter == SortFilterEntriesFragment.FILTER_ALL || diff == 0) {
            filterNotice.visibility = View.GONE
        } else {
            filterNotice.apply {
                visibility = View.VISIBLE

                val filtered = resources.getQuantityString(R.plurals.numberOfEntries, diff, diff)
                filterNotice.text = getString(R.string.filter_notice, filtered)
                setOnClickListener { handleSortAndFilter() }
            }
        }
    }

    private fun getCurrentFeed(): Feed? = refreshManager.currentFeed

    private fun getCurrentEntries(): List<Entry> = refreshManager.currentEntries

    override fun onCurrentEntriesUpdated() {
        adapter.notifyDataSetChanged()
        setMarkAllOptionsItem()
        setStarAllOptionsItem()
    }

    override fun onStop() {
        super.onStop()
        updateUnreadEntriesCount()
        if (context != null && getCurrentFeed() != null) {
            UserPreferences.saveFeedId(context!!, getCurrentFeed()!!.website)
        }
    }
}