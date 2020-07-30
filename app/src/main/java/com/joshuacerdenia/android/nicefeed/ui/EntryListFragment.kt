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
import com.joshuacerdenia.android.nicefeed.utils.RefreshHelper
import com.joshuacerdenia.android.nicefeed.utils.sortedByDatePublished
import com.joshuacerdenia.android.nicefeed.utils.unreadOnTop

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
        private const val ARG_FEED = "ARG_FEED"

        fun newInstance(feed: Feed?): EntryListFragment {
            return EntryListFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_FEED, feed)
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
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EntryListAdapter
    private lateinit var helper: RefreshHelper
    private var markAllOptionsItem: MenuItem? = null
    private var starAllOptionsItem: MenuItem? = null
    private val handler = Handler()
    private var callbacks: Callbacks? = null

    interface Callbacks {
        fun onFeedLoaded(feedId: String, title: String)
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
        val feed = arguments?.getSerializable(ARG_FEED) as Feed?
        adapter = EntryListAdapter(this)
        helper = RefreshHelper(this, feed) // Store reference to current feed
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

        // TODO? recyclerView.layoutManager = StaggeredGridLayoutManager(2, VERTICAL)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getCurrentFeed()?.let { feed ->
            viewModel.getEntriesByFeedId(feed.website)
            callbacks?.onFeedLoaded(feed.website, feed.title)
            setHasOptionsMenu(true)
        } ?:let {
            progressBar.visibility = View.GONE
            emptyImage.visibility = View.VISIBLE
            setHasOptionsMenu(false)
        }

        viewModel.entriesLiveData.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "Database change detected")
            if (!it.isNullOrEmpty()) {
                helper.submitInitialData(it)
                adapter.submitList(sortAndFilterEntries(it))
                updateFeedUnreadCount()

                if (!viewModel.hasAutoRefreshed) {
                    getCurrentFeed()?.let { feed ->
                        viewModel.requestFeed(feed.url)
                        callbacks?.onCheckingForUpdates()
                        viewModel.hasAutoRefreshed = true
                    }
                } else {
                    progressBar.visibility = View.GONE
                    emptyImage.visibility = View.GONE
                }

            } else {
                // If there are no entries, we can safely assume the feed is not in the database.
                callbacks?.onFeedRemoved()
            }
        })

        viewModel.requestResultLiveData?.observe(viewLifecycleOwner, Observer {
            if (!viewModel.refreshHasBeenManaged) {
                it?.let { helper.submitNewData(it.feed, it.entries) }
                viewModel.refreshHasBeenManaged = true
            }

            callbacks?.onCheckingForUpdates(false, getCurrentFeed()?.title)
            progressBar.visibility = View.GONE
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

        viewModel.requestFeed(url)
        progressBar.visibility = View.VISIBLE
        return true
    }

    override fun onFeedNeedsRefresh(feed: Feed) {
        viewModel.updateFeed(feed)
    }

    override fun onEntriesNeedRefresh(toAdd: List<Entry>, toUpdate: List<Entry>, toDelete: List<Entry>) {
        viewModel.refreshEntries(toAdd, toUpdate, toDelete)

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
        val list = sortAndFilterEntries(getCurrentEntries())
        adapter.submitList(list)
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
                handler.postDelayed({ recyclerView.scrollToPosition(0) }, 200)
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

    override fun onRemoveConfirmed(count: Int) {
        getCurrentFeed()?.let {
            viewModel.deleteFeedAndEntries(it, getCurrentEntries())
            callbacks?.onFeedRemoved()

            Snackbar.make(
                recyclerView,
                getString(R.string.feed_removed, it.title),
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    private fun updateFeedUnreadCount() {
        getCurrentFeed()?.let {
            val count = getUnreadCount(getCurrentEntries())
            viewModel.updateFeedUnreadCountById(it.website, count)
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