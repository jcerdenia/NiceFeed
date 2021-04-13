package com.joshuacerdenia.android.nicefeed.ui.fragment

import android.content.Context
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.net.Uri
import android.os.Bundle
import android.os.Handler
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
import com.joshuacerdenia.android.nicefeed.data.model.entry.EntryLight
import com.joshuacerdenia.android.nicefeed.data.model.feed.Feed
import com.joshuacerdenia.android.nicefeed.data.model.feed.FeedManageable
import com.joshuacerdenia.android.nicefeed.ui.OnHomePressed
import com.joshuacerdenia.android.nicefeed.ui.OnToolbarInflated
import com.joshuacerdenia.android.nicefeed.ui.adapter.EntryListAdapter
import com.joshuacerdenia.android.nicefeed.ui.dialog.ConfirmActionFragment
import com.joshuacerdenia.android.nicefeed.ui.dialog.ConfirmActionFragment.Companion.REMOVE
import com.joshuacerdenia.android.nicefeed.ui.dialog.EditFeedFragment
import com.joshuacerdenia.android.nicefeed.ui.dialog.FilterEntriesFragment
import com.joshuacerdenia.android.nicefeed.ui.menu.EntryPopupMenu
import com.joshuacerdenia.android.nicefeed.ui.viewmodel.EntryListViewModel
import com.joshuacerdenia.android.nicefeed.util.Utils
import com.joshuacerdenia.android.nicefeed.util.extensions.hide
import com.joshuacerdenia.android.nicefeed.util.extensions.show
import com.joshuacerdenia.android.nicefeed.util.work.BackgroundSyncWorker

class EntryListFragment : VisibleFragment(),
    EntryListAdapter.OnEntrySelected,
    EntryPopupMenu.OnPopupMenuItemClicked,
    FilterEntriesFragment.Callbacks,
    EditFeedFragment.Callback,
    ConfirmActionFragment.OnRemoveConfirmed
{

    interface Callbacks: OnHomePressed, OnToolbarInflated {
        fun onFeedLoaded(feedId: String)
        fun onEntrySelected(entryId: String)
        fun onCategoriesNeeded(): Array<String>
        fun onFeedRemoved()
    }

    private lateinit var viewModel: EntryListViewModel
    private lateinit var toolbar: Toolbar
    private lateinit var noItemsTextView: TextView
    private lateinit var masterProgressBar: ProgressBar
    private lateinit var progressBar: ProgressBar
    private lateinit var searchItem: MenuItem
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EntryListAdapter

    private var markAllOptionsItem: MenuItem? = null
    private var starAllOptionsItem: MenuItem? = null
    private var autoUpdateOnLaunch = true
    private var feedId: String? = null
    private var callbacks: Callbacks? = null
    private val handler = Handler()
    private val fragment = this@EntryListFragment

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EntryListViewModel::class.java)
        loadEntryOnStart()

        viewModel.setOrder(NiceFeedPreferences.getEntriesOrder(requireContext()))
        viewModel.keepOldUnreadEntries(NiceFeedPreferences.keepOldUnreadEntries(requireContext()))
        autoUpdateOnLaunch = NiceFeedPreferences.getAutoUpdateSetting(requireContext())
        adapter = EntryListAdapter(this)
        feedId = arguments?.getString(ARG_FEED_ID)
        setHasOptionsMenu(feedId != null)

        val blockAutoUpdate = arguments?.getBoolean(ARG_BLOCK_AUTO_UPDATE) ?: false
        if (blockAutoUpdate || !autoUpdateOnLaunch) viewModel.isAutoUpdating = false
    }

    private fun loadEntryOnStart() {
        // If there is an entryID argument, load immediately and only once
        arguments?.getString(ARG_ENTRY_ID)?.let { entryId ->
            arguments?.remove(ARG_ENTRY_ID)
            onEntryClicked(entryId, null)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_entry_list, container, false)
        toolbar = view.findViewById(R.id.toolbar)
        noItemsTextView = view.findViewById(R.id.no_items_text_view)
        masterProgressBar = view.findViewById(R.id.master_progress_bar)
        progressBar = view.findViewById(R.id.progress_bar)
        recyclerView = view.findViewById(R.id.recycler_view)
        setupRecyclerView()
        setupToolbar()
        return view
    }

    private fun setupRecyclerView() {
        val isPortrait = resources.configuration.orientation == ORIENTATION_PORTRAIT
        val layoutManager = if (isPortrait) LinearLayoutManager(context) else GridLayoutManager(context, 2)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    private fun setupToolbar() {
        toolbar.title = getString(R.string.loading)
        callbacks?.onToolbarInflated(toolbar, false)
        toolbar.apply {
            setNavigationIcon(R.drawable.ic_menu)
            setNavigationOnClickListener { callbacks?.onHomePressed() }
            setOnClickListener { recyclerView.smoothScrollToPosition(0) }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.feedLiveData.observe(viewLifecycleOwner, { feed ->
            progressBar.hide()
            masterProgressBar.hide()
            viewModel.onFeedRetrieved(feed)
            restoreToolbar()
            feed?.let { callbacks?.onFeedLoaded(it.url) } ?: run {
                if (feedId?.startsWith(FOLDER) == false) {
                    callbacks?.onFeedRemoved()
                }
            }
        })

        viewModel.entriesLightLiveData.observe(viewLifecycleOwner, { entries ->
            progressBar.hide()
            adapter.submitList(entries)
            showUpdateNotice()
            toggleOptionsItems()
            if (entries.isNullOrEmpty()) noItemsTextView.show() else noItemsTextView.hide()
            if (adapter.lastClickedPosition == 0) {
                handler.postDelayed({ recyclerView.scrollToPosition(0) }, 250)
            }
        })

        viewModel.updateResultLiveData.observe(viewLifecycleOwner, { results ->
            progressBar.hide()
            results?.let { viewModel.onUpdatesDownloaded(results) }
            restoreToolbar()
        })
    }

    override fun onStart() {
        super.onStart()
        feedId?.let { feedId ->
            viewModel.getFeedWithEntries(feedId)
            if (feedId.startsWith(FOLDER)) callbacks?.onFeedLoaded(feedId)
            if (viewModel.isAutoUpdating) { // Auto-update on launch:
                handler.postDelayed({ handleCheckForUpdates(feedId) }, 750)
            }
        } ?: run { // If there is no feed to load:
            masterProgressBar.hide()
            noItemsTextView.show()
            restoreToolbar()
        }
    }

    private fun restoreToolbar() {
        toolbar.title = when (feedId) {
            FOLDER_NEW -> getString(R.string.new_entries)
            FOLDER_STARRED -> getString(R.string.starred_entries)
            null -> getString(R.string.app_name)
            else -> viewModel.getCurrentFeed()?.title
        }
    }

    override fun onResume() {
        super.onResume()
        context?.let {  context ->
            viewModel.setOrder(NiceFeedPreferences.getEntriesOrder(context))
            viewModel.keepOldUnreadEntries(NiceFeedPreferences.keepOldUnreadEntries(context))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_entry_list, menu)
        searchItem = menu.findItem(R.id.menuItem_search)
        markAllOptionsItem = menu.findItem(R.id.mark_all_item)
        starAllOptionsItem = menu.findItem(R.id.star_all_item)
        toggleOptionsItems()

        if (feedId?.startsWith(FOLDER) == true) {
            menu.findItem(R.id.visit_website_item).isVisible = false
            menu.findItem(R.id.about_feed_item).isVisible = false
            menu.findItem(R.id.remove_feed_item).isVisible = false
        }

        if (feedId == FOLDER_STARRED) menu.findItem(R.id.update_item).isVisible = false

        searchItem.setOnActionExpandListener(object: MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean = true

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                viewModel.clearQuery()
                return true
            }
        })

        (searchItem.actionView as SearchView).apply {
            if (viewModel.query.isNotEmpty()) {
                searchItem.expandActionView()
                setQuery(viewModel.query, false)
                clearFocus()
            }

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(queryText: String): Boolean = true

                override fun onQueryTextSubmit(queryText: String): Boolean {
                    viewModel.submitQuery(queryText)
                    this@apply.clearFocus()
                    return true
                }
            })
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.update_item -> handleCheckForUpdates()
            R.id.about_feed_item -> handleShowFeedInfo(viewModel.getCurrentFeed())
            R.id.filter_item -> handleFilter()
            R.id.mark_all_item -> handleMarkAll()
            R.id.star_all_item -> handleStarAll()
            R.id.visit_website_item -> handleVisitWebsite(viewModel.getCurrentFeed()?.website)
            R.id.remove_feed_item -> handleRemoveFeed()
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

    private fun handleCheckForUpdates(
        url: String? = viewModel.getCurrentFeed()?.url
    ): Boolean {
        searchItem.collapseActionView()
        viewModel.clearQuery()

        if (feedId == FOLDER_NEW) {
            context?.let {
                BackgroundSyncWorker.runOnce(it)
                Snackbar.make(recyclerView, getString(R.string.updating_all_feeds), Snackbar.LENGTH_LONG).show()
            }
        } else {
            if (url == null) return false
            toolbar.title = getString(R.string.updating)
            progressBar.show()
            viewModel.requestUpdate(url)
        }
        return true
    }
    
    private fun showUpdateNotice() {
        val count = viewModel.updateValues
        if (count.isEmpty()) return
        val itemsAddedString = resources.getQuantityString(R.plurals.numberOfNewEntries, count.added, count.added)
        val itemsUpdatedString = resources.getQuantityString(R.plurals.numberOfEntries, count.updated, count.updated)
        val message = when {
            count.added > 0 && count.updated == 0 -> getString(R.string.added, itemsAddedString)
            count.added == 0 && count.updated > 0 -> getString(R.string.updated, itemsUpdatedString)
            else -> getString(R.string.added_and_updated, itemsAddedString, count.updated)
        }
        Snackbar.make(recyclerView, message, Snackbar.LENGTH_SHORT).show()
        viewModel.updateValues.clear()
    }

    private fun handleShowFeedInfo(feed: Feed?): Boolean {
        return if (feed != null) {
            val mFeed = FeedManageable(url = feed.url, title = feed.title, website = feed.website,
                imageUrl = feed.imageUrl, description = feed.description, category = feed.category)
            val categories = callbacks?.onCategoriesNeeded() ?: emptyArray()
            EditFeedFragment.newInstance(mFeed, categories).apply {
                setTargetFragment(fragment, 0)
                show(fragment.parentFragmentManager, null)
            }
            true
        } else false
    }

    override fun onFeedInfoSubmitted(title: String, category: String, isChanged: Boolean) {
        if (!isChanged) return
        viewModel.getCurrentFeed()?.let { currentFeed ->
            val editedFeed = currentFeed.apply {
                this.title = title
                this.category = category
            }
            viewModel.updateFeed(editedFeed)
            handler.postDelayed({
                Snackbar.make(recyclerView, getString(R.string.saved_changes_to, title), Snackbar.LENGTH_SHORT).show()
            }, 250)
        }
    }

    private fun handleFilter(): Boolean {
        FilterEntriesFragment.newInstance(viewModel.filter).apply {
            setTargetFragment(fragment, 0)
            show(fragment.parentFragmentManager, null)
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
        } else false
    }

    private fun handleRemoveFeed(): Boolean {
        val feed = viewModel.getCurrentFeed()
        return if (feed != null) {
            ConfirmActionFragment.newInstance(REMOVE, feed.title).apply {
                setTargetFragment(fragment, 0)
                show(fragment.parentFragmentManager,null)
            }
            true
        } else false
    }

    override fun onRemoveConfirmed() {
        val title = viewModel.getCurrentFeed()?.title
        Snackbar.make(recyclerView, getString(R.string.unsubscribed_message, title), Snackbar.LENGTH_SHORT).show()
        viewModel.deleteFeedAndEntries()
        callbacks?.onFeedRemoved()
    }

    override fun onEntryClicked(entryId: String, view: View?) {
        if (NiceFeedPreferences.getBrowserSetting(requireContext())) {
            Utils.openLink(requireContext(), view, Uri.parse(entryId))
            viewModel.updateEntryIsRead(entryId, true)
        } else {
            callbacks?.onEntrySelected(entryId)
        }
    }

    override fun onEntryLongClicked(entry: EntryLight, view: View?) {
        view?.let { EntryPopupMenu(requireContext(), it, this, entry).show() }
    }

    override fun onPopupMenuItemClicked(entry: EntryLight, action: Int) {
        val url = entry.url
        when (action) {
            EntryPopupMenu.ACTION_STAR -> viewModel.updateEntryIsStarred(url, !entry.isStarred)
            EntryPopupMenu.ACTION_MARK_AS -> viewModel.updateEntryIsRead(url, !entry.isRead)
            else -> {
                onEntryClicked(entry.url, recyclerView)
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

        private const val TAG = "EntryListFragment"
        private const val ARG_FEED_ID = "ARG_FEED_ID"
        private const val ARG_ENTRY_ID = "ARG_ENTRY_ID"
        private const val ARG_BLOCK_AUTO_UPDATE = "ARG_BLOCK_AUTO_UPDATE"

        const val FOLDER = "FOLDER"
        const val FOLDER_NEW = "FOLDER_NEW"
        const val FOLDER_STARRED = "FOLDER_STARRED"

        fun newInstance(
            feedId: String?,
            entryId: String? = null,
            blockAutoUpdate: Boolean = false
        ): EntryListFragment {
            return EntryListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_FEED_ID, feedId)
                    putString(ARG_ENTRY_ID, entryId)
                    putBoolean(ARG_BLOCK_AUTO_UPDATE, blockAutoUpdate)
                }
            }
        }
    }
}