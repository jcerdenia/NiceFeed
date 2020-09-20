package com.joshuacerdenia.android.nicefeed.ui.fragment

import android.content.Context
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.joshuacerdenia.android.nicefeed.R
import com.joshuacerdenia.android.nicefeed.data.local.NiceFeedPreferences
import com.joshuacerdenia.android.nicefeed.data.model.EntryLight
import com.joshuacerdenia.android.nicefeed.data.model.Feed
import com.joshuacerdenia.android.nicefeed.ui.OnHomePressed
import com.joshuacerdenia.android.nicefeed.ui.OnToolbarInflated
import com.joshuacerdenia.android.nicefeed.ui.adapter.EntryListAdapter
import com.joshuacerdenia.android.nicefeed.ui.dialog.AboutFeedFragment
import com.joshuacerdenia.android.nicefeed.ui.dialog.ConfirmRemoveFragment
import com.joshuacerdenia.android.nicefeed.ui.dialog.EditCategoryFragment
import com.joshuacerdenia.android.nicefeed.ui.dialog.FilterEntriesFragment
import com.joshuacerdenia.android.nicefeed.ui.menu.EntryPopupMenu
import com.joshuacerdenia.android.nicefeed.ui.viewmodel.EntryListViewModel
import com.joshuacerdenia.android.nicefeed.utils.Utils
import com.joshuacerdenia.android.nicefeed.utils.extensions.addRipple

private const val TAG = "EntryListFragment"

class EntryListFragment : VisibleFragment(),
    EntryListAdapter.OnEntrySelected,
    EntryPopupMenu.OnPopupMenuItemClicked,
    FilterEntriesFragment.Callbacks,
    AboutFeedFragment.Callbacks,
    EditCategoryFragment.Callbacks,
    ConfirmRemoveFragment.Callbacks
{

    interface Callbacks: OnHomePressed, OnToolbarInflated {
        fun onFeedLoaded(feedId: String)
        fun onEntrySelected(entryId: String)
        fun onCategoriesNeeded(): Array<String>
        fun onFeedRemoved()
    }

    private lateinit var viewModel: EntryListViewModel
    private lateinit var toolbar: Toolbar
    private lateinit var emptyMessageTextView: TextView
    private lateinit var masterProgressBar: ProgressBar
    private lateinit var progressBar: ProgressBar
    private lateinit var searchItem: MenuItem
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EntryListAdapter

    private var markAllOptionsItem: MenuItem? = null
    private var starAllOptionsItem: MenuItem? = null
    private var autoUpdateIsEnabled = true
    private var feedId: String? = null
    private var callbacks: Callbacks? = null
    private val fragment = this@EntryListFragment

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString(ARG_ENTRY_ID)?.let { entryId ->
           arguments?.remove(ARG_ENTRY_ID)
           callbacks?.onEntrySelected(entryId)
        }

        viewModel = ViewModelProvider(this).get(EntryListViewModel::class.java)
        adapter = EntryListAdapter(this)
        context?.let { context ->
            viewModel.setOrder(NiceFeedPreferences.getEntriesOrder(context))
            autoUpdateIsEnabled = NiceFeedPreferences.getAutoUpdateSetting(context)
        }

        feedId = arguments?.getString(ARG_FEED_ID)
        feedId?.let { feedId ->
            viewModel.getFeedWithEntries(feedId)
            if (feedId.startsWith(FOLDER)) {
                autoUpdateIsEnabled = false
            }
        }

        val isNewlyAdded = arguments?.getBoolean(ARG_IS_NEWLY_ADDED) ?: false
        if (isNewlyAdded || !autoUpdateIsEnabled) {
            viewModel.shouldAutoRefresh = false
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_entry_list, container, false)
        toolbar = view.findViewById(R.id.toolbar)
        emptyMessageTextView = view.findViewById(R.id.empty_message_text_view)
        masterProgressBar = view.findViewById(R.id.master_progress_bar)
        progressBar = view.findViewById(R.id.progress_bar)

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = if (resources.configuration.orientation == ORIENTATION_LANDSCAPE) {
            GridLayoutManager(context, 2)
        } else {
            LinearLayoutManager(context)
        }
        recyclerView.adapter = adapter

        toolbar.title = getString(R.string.loading)
        callbacks?.onToolbarInflated(toolbar, false)
        setHasOptionsMenu(feedId != null)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.feedLiveData.observe(viewLifecycleOwner, { feed ->
            Log.d(TAG, "Feed observer triggered!")
            if (feed != null) {
                viewModel.onFeedLoaded()
                callbacks?.onFeedLoaded(feed.url)
            } else {
                // feedId could still either be a FOLDER instead of url, so:
                feedId?.let { feedId ->
                    if (feedId.startsWith(FOLDER)) {
                        callbacks?.onFeedLoaded(feedId)
                    } else {
                        callbacks?.onFeedRemoved()
                    }
                }
            }
            // Check if not currently updating:
            if (toolbar.title != getString(R.string.updating)) {
                toolbar.title = when (feedId) {
                    FOLDER_NEW -> getString(R.string.new_entries)
                    FOLDER_STARRED -> getString(R.string.starred_entries)
                    else -> feed?.title
                }
            }
        })

        viewModel.entriesLightLiveData.observe(viewLifecycleOwner, { entries ->
            Log.d(TAG, "Entries observer triggered!")
            masterProgressBar.visibility = View.GONE
            adapter.submitList(entries)
            toggleOptionsItems()

            if (adapter.latestClickedPosition == 0) {
                Handler().postDelayed({
                    recyclerView.scrollToPosition(0)
                }, 250)
            }
            // Show update notice, if any
            viewModel.updateValues?.let { values ->
                showRefreshedNotice(values.first, values.second)
                viewModel.onUpdateNoticeShown()
            }
            // Show if entry list is empty
            emptyMessageTextView.visibility = if (entries.isNullOrEmpty()) {
                View.VISIBLE
            } else {
                View.GONE
            }
        })

        viewModel.updateResultLiveData.observe(viewLifecycleOwner, { results ->
            progressBar.visibility = View.GONE
            if (results != null) {
                viewModel.onUpdatesDownloaded(results)
                toolbar.title = results.feed.title
            } else {
                toolbar.title = viewModel.getCurrentFeed()?.title
            }
        })
    }

    override fun onStart() {
        super.onStart()
        if (feedId == null) {
            masterProgressBar.visibility = View.GONE
            emptyMessageTextView.visibility = View.VISIBLE
            toolbar.title = getString(R.string.app_name)
        }
        
        toolbar.apply {
            setNavigationIcon(R.drawable.ic_menu)
            setNavigationOnClickListener {
                callbacks?.onHomePressed()
            }
            setOnClickListener {
                recyclerView.smoothScrollToPosition(0)
            }
        }
        
        // Auto-refresh on launch
        if (viewModel.shouldAutoRefresh) {
            Handler().postDelayed({
                feedId?.let { feedId ->
                    viewModel.requestUpdate(feedId)
                    toolbar.title = context?.getString(R.string.updating)
                    progressBar.visibility = View.VISIBLE
                }
            }, 750)
        }
    }

    override fun onResume() {
        super.onResume()
        context?.let {  context ->
            viewModel.setOrder(NiceFeedPreferences.getEntriesOrder(context))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_entry_list, menu)
        searchItem = menu.findItem(R.id.menuItem_search)
        markAllOptionsItem = menu.findItem(R.id.menuItem_mark_all)
        starAllOptionsItem = menu.findItem(R.id.menuItem_star_all)
        toggleOptionsItems()

        feedId?.let { feedId ->
            if (feedId.startsWith(FOLDER)) {
                menu.findItem(R.id.menuItem_refresh).isVisible = false
                menu.findItem(R.id.menuItem_visit_website).isVisible = false
                menu.findItem(R.id.menuItem_about_feed).isVisible = false
                menu.findItem(R.id.menuItem_delete_feed).isVisible = false
            }
        }

        val searchView = searchItem.actionView as SearchView
        searchView.apply {
            if (viewModel.currentQuery.isNotEmpty()) {
                searchItem.expandActionView()
                setQuery(viewModel.currentQuery, false)
                clearFocus()
            }

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(queryText: String): Boolean {
                    viewModel.submitQuery(queryText)
                    clearFocus()
                    return true
                }

                override fun onQueryTextChange(queryText: String): Boolean {
                    return if (queryText.isEmpty()) {
                        viewModel.submitQuery(queryText)
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
            R.id.menuItem_refresh -> handleRefresh(feedId)
            R.id.menuItem_about_feed -> handleShowFeedInfo(viewModel.getCurrentFeed())
            R.id.menuItem_filter -> handleFilter()
            R.id.menuItem_mark_all -> handleMarkAll()
            R.id.menuItem_star_all -> handleStarAll()
            R.id.menuItem_visit_website -> handleVisitWebsite(viewModel.getCurrentFeed()?.website)
            R.id.menuItem_delete_feed -> handleRemoveFeed()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun toggleOptionsItems() {
        markAllOptionsItem?.apply {
            if (viewModel.allIsRead()) {
                title = getString(R.string.mark_all_as_unread)
                setIcon(R.drawable.ic_check_circle_outline)
            } else {
                title = getString(R.string.mark_all_as_read)
                setIcon(R.drawable.ic_check_circle)
            }
        }

        starAllOptionsItem?.apply {
            if (viewModel.allIsStarred()) {
                title = getString(R.string.unstar_all)
                setIcon(R.drawable.ic_star)
            } else {
                title = getString(R.string.star_all)
                setIcon(R.drawable.ic_star_border)
            }
        }
    }

    private fun handleRefresh(url: String?): Boolean {
        return if (url != null) {
            searchItem.collapseActionView()
            progressBar.visibility = View.VISIBLE
            toolbar.title = getString(R.string.updating)

            viewModel.submitQuery("")
            viewModel.requestUpdate(url)
            true
        } else {
            false
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
            show(fragment.parentFragmentManager, "about")
        }
        return true
    }

    override fun onEditCategoryClicked() {
        val categories = callbacks?.onCategoriesNeeded() ?: emptyArray()
        EditCategoryFragment.newInstance(categories, viewModel.getCurrentFeed()?.title).apply {
            setTargetFragment(fragment, 0)
            show(fragment.parentFragmentManager, "edit category")
        }
    }

    override fun onEditCategoryConfirmed(category: String) {
        viewModel.updateCategory(category)
        Handler().postDelayed({
            Snackbar.make(
                recyclerView,
                getString(R.string.category_assigned, category, viewModel.getCurrentFeed()?.title),
                Snackbar.LENGTH_SHORT
            ).show()
        }, 250)
    }

    private fun handleFilter(): Boolean {
        FilterEntriesFragment.newInstance(viewModel.currentFilter).apply {
            setTargetFragment(fragment, 0)
            show(fragment.parentFragmentManager, "filter")
        }
        return true
    }

    private fun handleMarkAll(): Boolean {
        viewModel.markAllCurrentEntriesAsRead()
        adapter.notifyDataSetChanged()
        return true
    }

    private fun handleStarAll(): Boolean {
        viewModel.starAllCurrentEntries()
        adapter.notifyDataSetChanged()
        return true
    }

    private fun handleVisitWebsite(website: String?): Boolean {
        return if (website != null) {
            Utils.openLink(requireActivity(), recyclerView, Uri.parse(website))
            true
        } else {
            false
        }
    }

    private fun handleRemoveFeed(): Boolean {
        val feed = viewModel.getCurrentFeed()
        return if (feed != null) {
            ConfirmRemoveFragment.newInstance(feed.title).apply {
                setTargetFragment(fragment, 0)
                show(fragment.parentFragmentManager,"unsubscribe")
            }
            true
        } else {
            false
        }
    }

    override fun onRemoveConfirmed() {
        val title = viewModel.getCurrentFeed()?.title
        Snackbar.make(
            recyclerView,
            getString(R.string.feed_removed_message, title),
            Snackbar.LENGTH_SHORT
        ).show()

        viewModel.deleteFeedAndEntries()
        callbacks?.onFeedRemoved()
    }

    override fun onEntryClicked(entryId: String) {
        if (NiceFeedPreferences.getBrowserSetting(requireContext())) {
            Utils.openLink(requireContext(), recyclerView, Uri.parse(entryId))
            viewModel.updateEntryIsRead(entryId, true)
        } else {
            callbacks?.onEntrySelected(entryId)
        }
    }

    override fun onEntryLongClicked(entry: EntryLight, view: View?) {
        context?.let { context ->
            view?.let { view ->
                EntryPopupMenu(context, view, this, entry).show()
            }
        }
    }

    override fun onPopupMenuItemClicked(entry: EntryLight, action: Int) {
        val url = entry.url
        when (action) {
            EntryPopupMenu.ACTION_STAR -> viewModel.updateEntryIsStarred(url, !entry.isStarred)
            EntryPopupMenu.ACTION_MARK_AS -> viewModel.updateEntryIsRead(url, !entry.isRead)
            else -> {
                onEntryClicked(entry.url)
                return
            }
        }
        adapter.notifyDataSetChanged()
    }

    override fun onFilterSelected(filter: Int) {
        viewModel.setFilter(filter)
    }

    override fun onStop() {
        super.onStop()
        context?.let { NiceFeedPreferences.saveLastViewedFeedId(it, feedId) }
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    companion object {
        const val FOLDER = "FOLDER"
        const val FOLDER_NEW = "FOLDER_NEW"
        const val FOLDER_STARRED = "FOLDER_STARRED"

        private const val ARG_FEED_ID = "ARG_FEED_ID"
        private const val ARG_IS_NEWLY_ADDED = "ARG_IS_NEWLY_ADDED"
        private const val ARG_ENTRY_ID = "ARG_ENTRY_ID"

        fun newInstance(
            feedId: String?,
            entryId: String? = null,
            isNewlyAdded: Boolean = false
        ): EntryListFragment {
            return EntryListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_FEED_ID, feedId)
                    putString(ARG_ENTRY_ID, entryId)
                    putBoolean(ARG_IS_NEWLY_ADDED, isNewlyAdded)
                }
            }
        }
    }
}