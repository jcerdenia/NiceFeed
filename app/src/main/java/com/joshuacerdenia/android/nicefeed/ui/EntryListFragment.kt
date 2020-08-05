package com.joshuacerdenia.android.nicefeed.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.SearchView
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
import com.joshuacerdenia.android.nicefeed.data.model.FeedIdPair
import com.joshuacerdenia.android.nicefeed.ui.dialog.AboutFeedFragment
import com.joshuacerdenia.android.nicefeed.ui.dialog.ConfirmRemoveFragment
import com.joshuacerdenia.android.nicefeed.ui.dialog.EditCategoryFragment
import com.joshuacerdenia.android.nicefeed.ui.dialog.SortFilterEntriesFragment
import com.joshuacerdenia.android.nicefeed.ui.menu.EntryPopupMenu
import com.joshuacerdenia.android.nicefeed.utils.RefreshHelper
import com.joshuacerdenia.android.nicefeed.utils.Utils
import com.joshuacerdenia.android.nicefeed.utils.sortedByDatePublished
import com.joshuacerdenia.android.nicefeed.utils.unreadOnTop
import java.util.*

private const val TAG = "EntryListFragment"

class EntryListFragment : Fragment(),
    EntryListAdapter.OnItemClickListener,
    EntryPopupMenu.OnItemClickListener,
    RefreshHelper.OnRefreshedListener,
    SortFilterEntriesFragment.Callbacks,
    AboutFeedFragment.Callbacks,
    EditCategoryFragment.Callbacks,
    ConfirmRemoveFragment.Callbacks {

    companion object {
        private const val ARG_FEED_ID_PAIR = "ARG_FEED_ID_PAIR"
        private const val ARG_IS_NEWLY_ADDED = "ARG_IS_NEWLY_ADDED"

        fun newInstance(
            feedIdPair: FeedIdPair?,
            isNewlyAdded: Boolean = false
        ): EntryListFragment {
            return EntryListFragment().apply {
                if (feedIdPair != null) {
                    arguments = Bundle().apply {
                        putSerializable(ARG_FEED_ID_PAIR, feedIdPair)
                        putBoolean(ARG_IS_NEWLY_ADDED, isNewlyAdded)
                    }
                }
            }
        }
    }

    private val fragment = this@EntryListFragment
    private val viewModel: EntryListViewModel by lazy {
        ViewModelProvider(this).get(EntryListViewModel::class.java)
    }

    private lateinit var emptyImage: ImageView
    private lateinit var filterNotice: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var searchView: SearchView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EntryListAdapter
    private lateinit var helper: RefreshHelper
    private var markAllOptionsItem: MenuItem? = null
    private var starAllOptionsItem: MenuItem? = null
    private val handler = Handler()
    private var callbacks: Callbacks? = null

    interface Callbacks {
        fun onFeedLoaded(title: String)
        fun onFeedRemoved()
        fun onEntrySelected(entry: Entry)
        fun onCheckingForUpdates(letContinue: Boolean = true, title: String? = null)
        fun onCategoriesNeeded(): Array<String>
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
        helper = RefreshHelper(this)
        arguments?.getBoolean(ARG_IS_NEWLY_ADDED)?.let { isNewlyAdded ->
            viewModel.shouldAutoRefresh = !isNewlyAdded
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_entry_list, container, false)
        filterNotice = view.findViewById(R.id.textView_filter_notice)
        emptyImage = view.findViewById(R.id.imageView_empty)
        progressBar = view.findViewById(R.id.progressBar)
        recyclerView = view.findViewById(R.id.recyclerView_entry)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val feedIdPair = arguments?.getSerializable(ARG_FEED_ID_PAIR) as FeedIdPair?

        feedIdPair?.let {
            viewModel.getFeedAndEntriesById(it.url)
            callbacks?.onFeedLoaded(it.title)
        } ?: let {
            progressBar.visibility = View.GONE
            emptyImage.visibility = View.VISIBLE
        }

        viewModel.feedLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                helper.currentFeed = it
                setHasOptionsMenu(true)
                if (it.title != feedIdPair?.title) {
                    callbacks?.onFeedLoaded(it.title)
                }
            } else {
                callbacks?.onFeedRemoved()
            }
        })

        viewModel.requestResultLiveData?.observe(viewLifecycleOwner, Observer {
            if (!viewModel.refreshHasBeenManaged) {
                it?.let {
                    Log.d(TAG, "Got ${it.entries.size} entries from internet")
                    helper.submitNewData(it.feed, it.entries)
                }
                viewModel.refreshHasBeenManaged = true
            }

            callbacks?.onCheckingForUpdates(false, getCurrentFeed()?.title)
            progressBar.visibility = View.GONE
        })

        observeEntriesLiveData()
    }

    private fun observeEntriesLiveData(queryText: String? = null) {
        viewModel.entriesLiveData.observe(viewLifecycleOwner, Observer { entries ->
            helper.submitInitialEntries(entries)

            val searchedEntries = queryText?.let { queryText ->
                searchEntries(queryText, entries)
            } ?: entries
            adapter.submitList(sortAndFilterEntries(searchedEntries))

            updateFeedUnreadCount()
            emptyImage.visibility = if (entries.isEmpty()) {
                View.VISIBLE
            } else {
                View.GONE
            }

            if (viewModel.shouldAutoRefresh) {
                handler.postDelayed({
                    getCurrentFeed()?.let { feed ->
                        viewModel.requestFeedUpdate(feed.url)
                        callbacks?.onCheckingForUpdates()
                        viewModel.shouldAutoRefresh = false
                    }
                }, 250)
            } else {
                progressBar.visibility = View.GONE
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_entry_list, menu)
        val searchItem: MenuItem = menu.findItem(R.id.menuItem_search)
        markAllOptionsItem = menu.findItem(R.id.menuItem_mark_all)
        starAllOptionsItem = menu.findItem(R.id.menuItem_star_all)
        searchView = searchItem.actionView as SearchView

        setMarkAllOptionsItem()
        setStarAllOptionsItem()

        searchView.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(queryText: String): Boolean {
                    if (queryText.isNotEmpty()) {
                        val list = searchEntries(
                            queryText.toLowerCase(Locale.ROOT),
                            getCurrentEntries()
                        )
                        observeEntriesLiveData(queryText)
                    }

                    clearFocus()
                    activity?.let { Utils.hideSoftKeyBoard(it, this@apply) }
                    return true
                }

                override fun onQueryTextChange(queryText: String): Boolean {
                    return if (queryText.isEmpty()) {
                        observeEntriesLiveData()
                        true
                    } else {
                        false
                    }
                }
            })
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuItem_refresh -> handleRefresh(getCurrentFeed()?.url)
            R.id.menuItem_about_feed -> handleShowFeedInfo(getCurrentFeed())
            R.id.menuItem_sort_filter -> handleSortAndFilter()
            R.id.menuItem_mark_all -> handleMarkAll(getCurrentEntries())
            R.id.menuItem_star_all -> handleStarAll(getCurrentEntries())
            R.id.menuItem_visit_website -> handleVisitWebsite(getCurrentFeed()?.website)
            R.id.menuItem_delete_feed -> handleRemoveFeed(getCurrentFeed()?.title)
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun searchEntries(queryText: String, entries: List<Entry>): List<Entry> {
        val resultList = mutableListOf<Entry>()
        for (entry in entries) {
            if (entry.title.toLowerCase(Locale.ROOT).contains(queryText)) {
                resultList.add(entry)
            }
        }
        return resultList
    }

    private fun allIsRead(entries: List<Entry>): Boolean {
        var count = 0
        for (entry in entries) {
            if (entry.isRead) {
                count += 1
            } else break
        }
        return count == entries.size
    }

    private fun allIsStarred(entries: List<Entry>): Boolean {
        var count = 0
        for (entry in entries) {
            if (entry.isStarred) {
                count += 1
            } else break
        }
        return count == entries.size
    }

    private fun setMarkAllOptionsItem() {
        markAllOptionsItem?.title = if (allIsRead(getCurrentEntries())) {
            getString(R.string.mark_all_as_unread)
        } else {
            getString(R.string.mark_all_as_read)
        }
    }

    private fun setStarAllOptionsItem() {
        starAllOptionsItem?.title = if (allIsStarred(getCurrentEntries())) {
            getString(R.string.unstar_all)
        } else {
            getString(R.string.star_all)
        }
    }

    private fun handleRefresh(url: String?): Boolean {
        if (url == null) {
            return false
        }

        viewModel.requestFeedUpdate(url)
        progressBar.visibility = View.VISIBLE
        callbacks?.onCheckingForUpdates()
        return true
    }

    override fun onFeedNeedsRefresh(feed: Feed) {
        viewModel.updateFeed(feed)
    }

    override fun onEntriesNeedRefresh(
        toAdd: List<Entry>,
        toUpdate: List<Entry>,
        toDelete: List<Entry>,
        feedId: String
    ) {
        viewModel.refreshEntries(toAdd, toUpdate, toDelete, feedId)
        if (toAdd.size + toUpdate.size > 0) {
            showRefreshedNotice(toAdd.size, toUpdate.size)
        }
    }

    private fun showRefreshedNotice(newCount: Int, updatedCount: Int) {
        val entriesAdded = resources.getQuantityString(R.plurals.numberOfNewEntries, newCount, newCount)
        val entriesUpdated = resources.getQuantityString(R.plurals.numberOfEntries, updatedCount, updatedCount)

        val message = when {
            newCount > 0 && updatedCount == 0 -> getString(R.string.added, entriesAdded)
            newCount == 0 && updatedCount > 0 -> getString(R.string.updated, entriesUpdated)
            else -> getString(R.string.added_and_updated, entriesAdded, updatedCount)
        }

        Snackbar.make(
            recyclerView,
            message as CharSequence,
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun handleShowFeedInfo(feed: Feed?): Boolean {
        if (feed == null) return false
        AboutFeedFragment.newInstance(feed).apply {
            setTargetFragment(fragment, 0)
            show(fragment.requireFragmentManager(), "about")
        }
        return true
    }

    override fun onEditCategoryClicked() {
        val categories = callbacks?.onCategoriesNeeded() ?: arrayOf()
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

    override fun onSortFilterConfirmed() {
        //val list = sortAndFilterEntries(getCurrentEntries())
        //adapter.submitList(list)
        observeEntriesLiveData()
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
                // Crude, but good enough for now:
                handler.postDelayed({
                    recyclerView.scrollToPosition(0)
                }, 200)
            }
            filteredEntries.unreadOnTop()
        } else {
            filteredEntries.sortedByDatePublished()
        }
    }

    private fun setFilterNotice(filter: Int, diff: Int) {
        if (filter == SortFilterEntriesFragment.FILTER_ALL || diff == 0) {
            filterNotice.visibility = View.GONE
        } else {
            filterNotice.apply {
                visibility = View.VISIBLE

                val filtered = resources.getQuantityString(R.plurals.numberOfEntries, diff, diff)
                filterNotice.text = getString(R.string.filter_notice, filtered)
                setOnClickListener {
                    handleSortAndFilter()
                }
            }
        }
    }

    private fun handleMarkAll(entries: List<Entry>): Boolean {
        if (entries.isEmpty()) return false
        val allIsRead = allIsRead(entries)
        for (entry in entries) {
            entry.isRead = !allIsRead
        }

        viewModel.updateEntries(entries)
        setMarkAllOptionsItem()
        return true
    }

    private fun handleStarAll(entries: List<Entry>): Boolean {
        if (entries.isEmpty()) return false
        val allIsStarred = allIsStarred(entries)
        for (entry in entries) {
            entry.isStarred = !allIsStarred
        }

        viewModel.updateEntries(entries)
        setStarAllOptionsItem()
        return true
    }

    private fun handleVisitWebsite(website: String?): Boolean {
        if (website == null) return false
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(website))
        startActivity(intent)
        return true
    }

    private fun handleRemoveFeed(title: String?): Boolean {
        ConfirmRemoveFragment.newInstance(title).apply {
            setTargetFragment(fragment, 0)
            show(fragment.requireFragmentManager(),"unsubscribe")
        }
        return true
    }

    override fun onRemoveConfirmed() {
        getCurrentFeed()?.let {
            viewModel.deleteFeedAndEntriesByFeedId(it.url)
            callbacks?.onFeedRemoved()

            Snackbar.make(
                recyclerView,
                getString(R.string.feed_removed_message, it.title),
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    private fun updateFeedUnreadCount() {
        getCurrentFeed()?.let { feed ->
            val count = getUnreadCount(getCurrentEntries())
            viewModel.updateFeedUnreadCountById(feed.url, count)
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

    override fun onItemClicked(entry: Entry) {
        callbacks?.onEntrySelected(entry)
    }

    override fun onItemLongClicked(entry: Entry, view: View?) {
        val popupMenu = EntryPopupMenu(context, view, this, entry)
        popupMenu.show()
    }

    override fun onPopupMenuItemClicked(entry: Entry, action: Int) {
        when (action) {
            EntryPopupMenu.ACTION_STAR -> entry.isStarred = !entry.isStarred
            EntryPopupMenu.ACTION_MARK_AS -> entry.isRead = !entry.isRead
            else -> {
                callbacks?.onEntrySelected(entry)
                return
            }
        }
        viewModel.updateEntry(entry)
    }

    private fun getCurrentFeed(): Feed? = helper.currentFeed

    private fun getCurrentEntries(): List<Entry> = helper.currentEntries

    override fun onCurrentEntriesChanged() {
        adapter.notifyDataSetChanged()
        setMarkAllOptionsItem()
        setStarAllOptionsItem()
    }

    fun scrollToTop() {
        recyclerView.scrollToPosition(0)
    }
}