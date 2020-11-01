package com.joshuacerdenia.android.nicefeed.ui.fragment

import android.content.Context
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
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
import com.joshuacerdenia.android.nicefeed.data.model.EntryLight
import com.joshuacerdenia.android.nicefeed.data.model.Feed
import com.joshuacerdenia.android.nicefeed.data.model.FeedManageable
import com.joshuacerdenia.android.nicefeed.ui.OnHomePressed
import com.joshuacerdenia.android.nicefeed.ui.OnToolbarInflated
import com.joshuacerdenia.android.nicefeed.ui.adapter.EntryListAdapter
import com.joshuacerdenia.android.nicefeed.ui.dialog.ConfirmRemoveFragment
import com.joshuacerdenia.android.nicefeed.ui.dialog.EditFeedFragment
import com.joshuacerdenia.android.nicefeed.ui.dialog.FilterEntriesFragment
import com.joshuacerdenia.android.nicefeed.ui.menu.EntryPopupMenu
import com.joshuacerdenia.android.nicefeed.ui.viewmodel.EntryListViewModel
import com.joshuacerdenia.android.nicefeed.utils.Utils

class EntryListFragment : VisibleFragment(),
    EntryListAdapter.OnEntrySelected,
    EntryPopupMenu.OnPopupMenuItemClicked,
    FilterEntriesFragment.Callbacks,
    EditFeedFragment.Callback,
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
    private lateinit var noItemsTextView: TextView
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
           arguments?.remove(ARG_ENTRY_ID) // So it doesn't get loaded more than once
           callbacks?.onEntrySelected(entryId)
        }

        viewModel = ViewModelProvider(this).get(EntryListViewModel::class.java)
        viewModel.setOrder(NiceFeedPreferences.getEntriesOrder(requireContext()))
        autoUpdateIsEnabled = NiceFeedPreferences.getAutoUpdateSetting(requireContext())
        adapter = EntryListAdapter(this)
        
        feedId = arguments?.getString(ARG_FEED_ID)
        val blockAutoUpdate = arguments?.getBoolean(ARG_BLOCK_AUTOUPDATE) ?: false
        if (blockAutoUpdate || !autoUpdateIsEnabled) viewModel.isAutoUpdating = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_entry_list, container, false)
        toolbar = view.findViewById(R.id.toolbar)
        noItemsTextView = view.findViewById(R.id.empty_message_text_view)
        masterProgressBar = view.findViewById(R.id.master_progress_bar)
        progressBar = view.findViewById(R.id.progress_bar)

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = if (resources.configuration.orientation == ORIENTATION_LANDSCAPE) {
            GridLayoutManager(context, 2)
        } else LinearLayoutManager(context)
        recyclerView.adapter = adapter

        toolbar.title = getString(R.string.loading)
        callbacks?.onToolbarInflated(toolbar, false)
        setHasOptionsMenu(feedId != null)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.feedLiveData.observe(viewLifecycleOwner, { feed ->
            if (feed != null) {
                viewModel.onFeedLoaded()
                callbacks?.onFeedLoaded(feed.url)
            } else feedId?.let { if (!it.startsWith(FOLDER)) callbacks?.onFeedRemoved() }
            // Check if not currently updating
            if (toolbar.title != getString(R.string.updating)) {
                toolbar.title = when (feedId) {
                    FOLDER_NEW -> getString(R.string.new_entries)
                    FOLDER_STARRED -> getString(R.string.starred_entries)
                    else -> feed?.title
                }
            }
        })

        viewModel.entriesLightLiveData.observe(viewLifecycleOwner, { entries ->
            noItemsTextView.visibility = if (entries.isNullOrEmpty()) View.VISIBLE else View.GONE
            masterProgressBar.visibility = View.GONE
            adapter.submitList(entries)
            toggleOptionsItems()

            if (adapter.latestClickedPosition == 0) {
                Handler().postDelayed({ recyclerView.scrollToPosition(0) }, 250)
            }
            // Show update notice, if any
            if (viewModel.updateValues.isNotEmpty()) viewModel.updateValues.let { values ->
                showUpdatedNotice(values.added, values.updated)
                viewModel.updateValues.clear()
            }
        })

        viewModel.updateResultLiveData.observe(viewLifecycleOwner, { results ->
            progressBar.visibility = View.GONE
            if (results != null) {
                viewModel.onUpdatesDownloaded(results)
                toolbar.title = results.feed.title
            } else toolbar.title = viewModel.getCurrentFeed()?.title
        })
    }

    override fun onStart() {
        super.onStart()
        feedId?.let { feedId ->
            viewModel.getFeedWithEntries(feedId)
            if (feedId.startsWith(FOLDER)) {
                viewModel.isAutoUpdating = false
                callbacks?.onFeedLoaded(feedId)
            }
            // Auto-update on launch
            if (viewModel.isAutoUpdating) Handler().postDelayed({
                viewModel.requestUpdate(feedId)
                toolbar.title = context?.getString(R.string.updating)
                progressBar.visibility = View.VISIBLE
            }, 750)
        } ?: let {
            masterProgressBar.visibility = View.GONE
            noItemsTextView.visibility = View.VISIBLE
            toolbar.title = getString(R.string.app_name)
        }
        
        toolbar.apply {
            setNavigationIcon(R.drawable.ic_menu)
            setNavigationOnClickListener { callbacks?.onHomePressed() }
            setOnClickListener { recyclerView.smoothScrollToPosition(0) }
        }
    }

    override fun onResume() {
        super.onResume()
        context?.let { viewModel.setOrder(NiceFeedPreferences.getEntriesOrder(it)) }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_entry_list, menu)
        searchItem = menu.findItem(R.id.menuItem_search)
        markAllOptionsItem = menu.findItem(R.id.mark_all_item)
        starAllOptionsItem = menu.findItem(R.id.star_all_item)
        toggleOptionsItems()

        if (feedId?.startsWith(FOLDER) == true) {
            menu.findItem(R.id.update_item).isVisible = false
            menu.findItem(R.id.visit_website_item).isVisible = false
            menu.findItem(R.id.about_feed_item).isVisible = false
            menu.findItem(R.id.remove_feed_item).isVisible = false
        }

        (searchItem.actionView as SearchView).apply {
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
                    } else false
                }
            })
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.update_item -> handleCheckForUpdates(viewModel.getCurrentFeed()?.url)
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

    private fun handleCheckForUpdates(url: String?): Boolean {
        return if (url != null) {
            progressBar.visibility = View.VISIBLE
            viewModel.submitQuery("")
            viewModel.requestUpdate(url)
            toolbar.title = getString(R.string.updating)
            searchItem.collapseActionView()
            true
        } else false
    }

    private fun showUpdatedNotice(newCount: Int, updatedCount: Int) {
        val entriesAdded = resources.getQuantityString(R.plurals.numberOfNewEntries, newCount, newCount)
        val entriesUpdated = resources.getQuantityString(R.plurals.numberOfEntries, updatedCount, updatedCount)
        val message = when {
            newCount > 0 && updatedCount == 0 -> getString(R.string.added, entriesAdded)
            newCount == 0 && updatedCount > 0 -> getString(R.string.updated, entriesUpdated)
            else -> getString(R.string.added_and_updated, entriesAdded, updatedCount)
        }
        Snackbar.make(recyclerView, message as CharSequence, Snackbar.LENGTH_SHORT).show()
    }

    private fun handleShowFeedInfo(feed: Feed?): Boolean {
        return if (feed != null) {
            val mFeed = FeedManageable(url = feed.url, title = feed.title, website = feed.website,
                imageUrl = feed.imageUrl, description = feed.description, category = feed.category)
            val categories = callbacks?.onCategoriesNeeded() ?: emptyArray()
            EditFeedFragment.newInstance(mFeed, categories).apply {
                setTargetFragment(fragment, 0)
                show(fragment.parentFragmentManager, "about")
            }
            true
        } else false
    }

    override fun onFeedInfoChanged(title: String, category: String) {
        viewModel.getCurrentFeed()?.let { currentFeed ->
            val editedFeed = currentFeed.apply {
                this.title = title
                this.category = category
            }
            viewModel.updateFeed(editedFeed)
            Handler().postDelayed({
                Snackbar.make(recyclerView, getString(R.string.saved_changes_to, title), Snackbar.LENGTH_SHORT)
                    .show()
            }, 250)
        }
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
        } else false
    }

    private fun handleRemoveFeed(): Boolean {
        val feed = viewModel.getCurrentFeed()
        return if (feed != null) {
            ConfirmRemoveFragment.newInstance(feed.title).apply {
                setTargetFragment(fragment, 0)
                show(fragment.parentFragmentManager,"unsubscribe")
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

    override fun onEntryClicked(entryId: String) {
        if (NiceFeedPreferences.getBrowserSetting(requireContext())) {
            Utils.openLink(requireContext(), recyclerView, Uri.parse(entryId))
            viewModel.updateEntryIsRead(entryId, true)
        } else callbacks?.onEntrySelected(entryId)
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
        private const val ARG_ENTRY_ID = "ARG_ENTRY_ID"
        private const val ARG_BLOCK_AUTOUPDATE = "ARG_BLOCK_AUTOUPDATE"

        fun newInstance(
            feedId: String?,
            entryId: String? = null,
            blockAutoUpdate: Boolean = false
        ): EntryListFragment {
            return EntryListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_FEED_ID, feedId)
                    putString(ARG_ENTRY_ID, entryId)
                    putBoolean(ARG_BLOCK_AUTOUPDATE, blockAutoUpdate)
                }
            }
        }
    }
}