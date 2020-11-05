package com.joshuacerdenia.android.nicefeed.ui.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.CheckBox
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.joshuacerdenia.android.nicefeed.R
import com.joshuacerdenia.android.nicefeed.data.local.NiceFeedPreferences
import com.joshuacerdenia.android.nicefeed.data.model.feed.FeedManageable
import com.joshuacerdenia.android.nicefeed.ui.OnFinished
import com.joshuacerdenia.android.nicefeed.ui.OnToolbarInflated
import com.joshuacerdenia.android.nicefeed.ui.adapter.FeedManagerAdapter
import com.joshuacerdenia.android.nicefeed.ui.dialog.ConfirmActionFragment
import com.joshuacerdenia.android.nicefeed.ui.dialog.ConfirmActionFragment.Companion.EXPORT
import com.joshuacerdenia.android.nicefeed.ui.dialog.ConfirmActionFragment.Companion.REMOVE
import com.joshuacerdenia.android.nicefeed.ui.dialog.EditCategoryFragment
import com.joshuacerdenia.android.nicefeed.ui.dialog.EditFeedFragment
import com.joshuacerdenia.android.nicefeed.ui.dialog.SortFeedManagerFragment
import com.joshuacerdenia.android.nicefeed.ui.viewmodel.ManageFeedsViewModel
import com.joshuacerdenia.android.nicefeed.utils.OpmlExporter
import com.joshuacerdenia.android.nicefeed.utils.extensions.hide
import com.joshuacerdenia.android.nicefeed.utils.extensions.show
import com.leinardi.android.speeddial.SpeedDialActionItem
import com.leinardi.android.speeddial.SpeedDialView

class ManageFeedsFragment: VisibleFragment(),
    EditCategoryFragment.Callbacks,
    EditFeedFragment.Callback,
    ConfirmActionFragment.Callbacks,
    SortFeedManagerFragment.Callbacks,
    FeedManagerAdapter.ItemCheckBoxListener,
    OpmlExporter.ExportResultListener {

    interface Callbacks: OnToolbarInflated, OnFinished {
        fun onAddFeedsSelected()
        fun onExportOpmlSelected()
    }

    private lateinit var viewModel: ManageFeedsViewModel
    private lateinit var toolbar: Toolbar
    private lateinit var progressBar: ProgressBar
    private lateinit var selectAllCheckBox: CheckBox
    private lateinit var counterTextView: TextView
    private lateinit var emptyMessageTextView: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FeedManagerAdapter
    private lateinit var speedDial: SpeedDialView
    private lateinit var searchItem: MenuItem

    private var opmlExporter: OpmlExporter? = null
    private var callbacks: Callbacks? = null
    private val fragment = this@ManageFeedsFragment
    private val handler = Handler()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ManageFeedsViewModel::class.java)
        viewModel.setOrder(NiceFeedPreferences.getFeedManagerOrder(requireContext()))
        adapter = FeedManagerAdapter(this, viewModel.selectedItems)
        opmlExporter = OpmlExporter(requireContext(), this)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_manage_feeds, container, false)
        toolbar = view.findViewById(R.id.toolbar)
        progressBar = view.findViewById(R.id.progress_bar)
        selectAllCheckBox = view.findViewById(R.id.select_all_checkbox)
        counterTextView = view.findViewById(R.id.counter_text_view)
        emptyMessageTextView = view.findViewById(R.id.empty_message_text_view)
        speedDial = view.findViewById(R.id.speed_dial)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        toolbar.title = getString(R.string.manage_feeds)
        callbacks?.onToolbarInflated(toolbar)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar.show()
        setupSpeedDial()

        viewModel.feedsManageableLiveData.observe(viewLifecycleOwner, { feeds ->
            progressBar.hide()
            adapter.submitList(feeds)
            selectAllCheckBox.isChecked = feeds.size == viewModel.selectedItems.size
            if (feeds.size > 1) selectAllCheckBox.show() else selectAllCheckBox.hide()
            if (feeds.isEmpty()) emptyMessageTextView.show() else emptyMessageTextView.hide()
        })

        viewModel.anyIsSelected.observe(viewLifecycleOwner, { anyIsSelected ->
            updateCounter()
            if (anyIsSelected) {
                speedDial.show()
                speedDial.open()
            } else {
                speedDial.hide()
            }
        })
    }

    override fun onStart() {
        super.onStart()
        toolbar.setOnClickListener { recyclerView.smoothScrollToPosition(0) }
        selectAllCheckBox.setOnClickListener { (it as CheckBox)
            if (it.isChecked) viewModel.resetSelection(adapter.currentList) else viewModel.resetSelection()
            adapter.toggleCheckBoxes(it.isChecked)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_manage_feeds, menu)
        searchItem = menu.findItem(R.id.menu_item_search)

        searchItem.setOnActionExpandListener(object: MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean = true
            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                viewModel.clearQuery()
                resetSelection()
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
                    clearFocus()
                    return true
                }
            })
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuItem_sort -> handleSortFeeds()
            R.id.menuItem_add_feeds -> {
                callbacks?.onAddFeedsSelected()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupSpeedDial() {
        speedDial.apply {
            addActionItem(defaultSpeedDialItem(R.id.fab_edit, R.drawable.ic_edit_light))
            addActionItem(defaultSpeedDialItem(R.id.fab_remove, R.drawable.ic_delete_light))
            addActionItem(defaultSpeedDialItem(R.id.fab_export, R.drawable.ic_export_light))

            setOnChangeListener(object : SpeedDialView.OnChangeListener {
                override fun onToggleChanged(isOpen: Boolean) { } // Blank on purpose
                override fun onMainActionSelected(): Boolean {
                    resetSelection()
                    return true
                }
            })

            setOnActionSelectedListener { actionItem ->
                when (actionItem.id) {
                    R.id.fab_edit -> handleEditSelected()
                    R.id.fab_remove -> handleRemoveSelected()
                    R.id.fab_export -> handleExportSelected()
                }
                true
            }
        }
    }

    private fun defaultSpeedDialItem(id: Int, iconRes: Int): SpeedDialActionItem {
        return SpeedDialActionItem.Builder(id, iconRes)
            .setFabBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            .create()
    }

    private fun updateCounter() {
        val count = viewModel.selectedItems.size
        if (count > 0) {
            counterTextView.show()
            counterTextView.text = getString(R.string.number_selected, count)
        } else {
            counterTextView.hide()
        }
    }

    private fun handleEditSelected(): Boolean {
        val count = viewModel.selectedItems.size
        if (count > 1) {
            EditCategoryFragment.newInstance(viewModel.getCategories(), null, count).apply {
                setTargetFragment(fragment, 0)
                show(fragment.parentFragmentManager, "TAG")
            }
        } else {
            EditFeedFragment.newInstance(viewModel.selectedItems[0], viewModel.getCategories()).apply {
                setTargetFragment(fragment, 0)
                show(fragment.parentFragmentManager, "TAG")
            }
        }
        return true
    }

    override fun onFeedInfoChanged(title: String, category: String) {
        viewModel.updateFeedDetails(viewModel.selectedItems[0].url, title, category)
        resetSelection()
        searchItem.collapseActionView()
        handler.postDelayed({ Snackbar.make(
            recyclerView,
            getString(R.string.saved_changes_to, title),
            Snackbar.LENGTH_SHORT
        ).show() }, 250)
    }

    override fun onEditCategoryConfirmed(category: String) {
        val ids = mutableListOf<String>()
        for (feed in viewModel.selectedItems) ids.add(feed.url)
        viewModel.updateCategoryByFeedIds(ids, category)
        resetSelection()
        searchItem.collapseActionView()
        // Crude solution to Snackbar jumping: wait until keyboard is fully hidden
        handler.postDelayed({ showFeedsCategorizedNotice(category, ids.size) }, 400)
    }

    private fun showFeedsCategorizedNotice(category: String, count: Int) {
        val feedsUpdated = resources.getQuantityString(R.plurals.numberOfFeeds, count, count)
        Snackbar.make(
            recyclerView,
            getString(R.string.category_assigned, category, feedsUpdated),
            Snackbar.LENGTH_LONG
        ).setAction(R.string.done) { callbacks?.onFinished() }.show()
    }

    private fun handleRemoveSelected(): Boolean {
        val count = viewModel.selectedItems.size
        val title = if (count == 1) viewModel.selectedItems[0].title else null
        ConfirmActionFragment.newInstance(REMOVE, title, count).apply {
            setTargetFragment(fragment, 0)
            show(fragment.parentFragmentManager,"TAG")
        }
        return true
    }

    override fun onActionConfirmed(action: Int) {
        when (action) {
            REMOVE -> onRemoveConfirmed()
            EXPORT -> onExportConfirmed()
            else -> throw IllegalArgumentException()
        }
    }

    private fun onRemoveConfirmed() {
        val feedIds = viewModel.selectedItems.map { feed -> feed.url }.toTypedArray()
        viewModel.deleteItems(*feedIds)

        if (feedIds.size == 1) {
            showFeedsRemovedNotice(title = viewModel.selectedItems.first().title)
        } else {
            showFeedsRemovedNotice(feedIds.size)
            // If last viewed feed was just deleted, prevent main page from loading it:
            val lastViewedFeedId = NiceFeedPreferences.getLastViewedFeedId(requireContext())
            if (feedIds.contains(lastViewedFeedId)) {
                NiceFeedPreferences.saveLastViewedFeedId(requireContext(), null)
            }
        }
        resetSelection()
    }

    private fun showFeedsRemovedNotice(count: Int = 1, title: String? = null) {
        val feedsRemoved = title ?: resources.getQuantityString(R.plurals.numberOfFeeds, count, count)
        Snackbar.make(
            recyclerView, getString(R.string.unsubscribed_message, feedsRemoved), Snackbar.LENGTH_LONG)
            .setAction(R.string.done) { callbacks?.onFinished() }.show()
    }

    private fun handleExportSelected(): Boolean {
        val count = viewModel.selectedItems.size
        val title = if (count == 1) viewModel.selectedItems[0].title else null
        ConfirmActionFragment.newInstance(EXPORT, title, count).apply {
            setTargetFragment(fragment, 0)
            show(fragment.parentFragmentManager,"TAG")
        }
        return true
    }

    private fun onExportConfirmed() {
        val feeds = viewModel.selectedItems
        opmlExporter?.submitFeeds(feeds)
        callbacks?.onExportOpmlSelected()
    }

    fun writeOpml(uri: Uri) {
        opmlExporter?.executeExport(uri)
    }

    override fun onExportAttempted(isSuccessful: Boolean, fileName: String?) {
        val count = viewModel.selectedItems.size
        val itemString = resources.getQuantityString(R.plurals.numberOfFeeds, count, count)
        val message = if (isSuccessful) {
            getString(R.string.exported_message, itemString)
        } else {
            getString(R.string.error_message)
        }
        resetSelection()
        Snackbar.make(recyclerView, message, Snackbar.LENGTH_SHORT)
            .setAction(R.string.done) { callbacks?.onFinished() }.show()
    }

    private fun handleSortFeeds(): Boolean {
        SortFeedManagerFragment.newInstance(viewModel.order).apply {
            setTargetFragment(fragment, 0)
            show(fragment.parentFragmentManager, "sort")
        }
        return true
    }

    override fun onOrderSelected(order: Int) {
        viewModel.setOrder(order)
    }

    private fun resetSelection() {
        viewModel.resetSelection()
        selectAllCheckBox.isChecked = false
        adapter.toggleCheckBoxes(false)
    }

    override fun onItemClicked(feed: FeedManageable, isChecked: Boolean) {
        if (isChecked) {
            viewModel.addSelection(feed)
            selectAllCheckBox.isChecked = viewModel.selectedItems.size == adapter.currentList.size
        } else {
            viewModel.removeSelection(feed)
            selectAllCheckBox.isChecked = false
        }
        adapter.selectedItems = viewModel.selectedItems
    }

    override fun onAllItemsChecked(isChecked: Boolean) {
        selectAllCheckBox.isChecked = isChecked
    }

    override fun onStop() {
        super.onStop()
        context?.let { NiceFeedPreferences.saveFeedManagerOrder(it, viewModel.order) }
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    companion object {

        fun newInstance(): ManageFeedsFragment {
            return ManageFeedsFragment()
        }
    }
}