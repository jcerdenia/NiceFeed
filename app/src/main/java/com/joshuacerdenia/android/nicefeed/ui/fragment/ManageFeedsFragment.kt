package com.joshuacerdenia.android.nicefeed.ui.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.CheckBox
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.joshuacerdenia.android.nicefeed.R
import com.joshuacerdenia.android.nicefeed.data.local.NiceFeedPreferences
import com.joshuacerdenia.android.nicefeed.data.model.FeedMinimal
import com.joshuacerdenia.android.nicefeed.ui.OnFinished
import com.joshuacerdenia.android.nicefeed.ui.adapter.FeedManagerAdapter
import com.joshuacerdenia.android.nicefeed.ui.viewmodel.ManageFeedsViewModel
import com.joshuacerdenia.android.nicefeed.ui.dialog.ConfirmRemoveFragment
import com.joshuacerdenia.android.nicefeed.ui.dialog.EditCategoryFragment
import com.joshuacerdenia.android.nicefeed.ui.dialog.SortFeedManagerFragment
import com.joshuacerdenia.android.nicefeed.utils.OpmlExporter
import com.joshuacerdenia.android.nicefeed.ui.OnToolbarInflated

class ManageFeedsFragment: VisibleFragment(),
    EditCategoryFragment.Callbacks,
    ConfirmRemoveFragment.Callbacks,
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
    private lateinit var emptyMessageTextView: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FeedManagerAdapter

    private var opmlExporter: OpmlExporter? = null
    private var callbacks: Callbacks? = null
    private val fragment = this@ManageFeedsFragment
    private val handler = Handler()

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
        viewModel = ViewModelProvider(this).get(ManageFeedsViewModel::class.java)
        adapter = FeedManagerAdapter(this, viewModel.selectedItems)
        setHasOptionsMenu(true)

        context?.let { context ->
            opmlExporter = OpmlExporter(context, this)
            viewModel.setOrder(NiceFeedPreferences.getFeedManagerOrder(context))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_manage_feeds, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuItem_edit -> handleEditCategory()
            R.id.menuItem_remove -> handleRemoveSelected()
            R.id.menuItem_sort -> handleSortFeeds()
            R.id.menuItem_add_feeds -> {
                callbacks?.onAddFeedsSelected()
                true
            }
            R.id.menuItem_export_opml -> handleExportOPML(viewModel.selectedItems)
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun handleExportOPML(feeds: List<FeedMinimal>): Boolean {
        if (feeds.isNotEmpty()) {
            opmlExporter?.submitFeeds(feeds)
            callbacks?.onExportOpmlSelected()
        } else {
            showNothingSelectedNotice(ACTION_EXPORT)
        }
        return true
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
        emptyMessageTextView = view.findViewById(R.id.empty_message_text_view)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        toolbar.title = getString(R.string.loading)
        callbacks?.onToolbarInflated(toolbar)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateToolbar(toolbar, viewModel.selectedItems.size)
        progressBar.visibility = View.VISIBLE

        viewModel.feedsMinimalLiveData.observe(viewLifecycleOwner, { feeds ->
            adapter.submitList(feeds)
            progressBar.visibility = View.GONE
            selectAllCheckBox.visibility = if (feeds.size > 1) {
                View.VISIBLE
            } else {
                View.GONE
            }

            if (feeds.isEmpty()) {
                emptyMessageTextView.visibility = View.VISIBLE
            }
        })
    }

    override fun onStart() {
        super.onStart()
        toolbar.setOnClickListener {
            recyclerView.smoothScrollToPosition(0)
        }

        selectAllCheckBox.setOnClickListener { (it as CheckBox)
            if (it.isChecked) {
                viewModel.selectedItems = adapter.currentList.toMutableList()
            } else {
                viewModel.selectedItems.clear()
            }

            adapter.toggleCheckBoxes(it.isChecked)
            updateToolbar(toolbar, viewModel.selectedItems.size)
        }
    }

    private fun updateToolbar(toolbar: Toolbar, selectedItemCount: Int) {
        toolbar.title = if (selectedItemCount > 0) {
            getString(R.string.number_selected, selectedItemCount)
        } else {
            getString(R.string.manage_feeds)
        }
    }

    private fun handleSortFeeds(): Boolean {
        SortFeedManagerFragment.newInstance(viewModel.currentOrder).apply {
            setTargetFragment(fragment, 0)
            show(fragment.parentFragmentManager, "sort")
        }
        return true
    }

    override fun onOrderSelected(order: Int) {
        viewModel.setOrder(order)
    }

    private fun handleRemoveSelected(): Boolean {
        if (anyItemIsSelected()) {
            val count = viewModel.selectedItems.size
            val title = if (count == 1) {
                viewModel.selectedItems[0].title
            } else null

            ConfirmRemoveFragment.newInstance(title, count).apply {
                setTargetFragment(fragment, 0)
                show(fragment.parentFragmentManager,"unsubscribe")
            }

            return true
        } else {
            showNothingSelectedNotice(ACTION_REMOVE)
            return false
        }
    }

    override fun onRemoveConfirmed() {
        val feedIds = viewModel.selectedItems.map { feed ->
            feed.url
        }.toTypedArray()

        if (feedIds.size == 1) {
            showFeedsRemovedNotice(title = viewModel.selectedItems.first().title)
        } else {
            context?.let { context ->
                if (feedIds.contains(NiceFeedPreferences.getLastViewedFeedId(context))) {
                    NiceFeedPreferences.saveLastViewedFeedId(context, null)
                }
            }
            showFeedsRemovedNotice(feedIds.size)
        }
        viewModel.deleteItems(*feedIds)
        resetSelection()
    }

    private fun showFeedsRemovedNotice(
        count: Int = 1,
        title: String? = null
    ) {
        val feedsRemoved = title ?: resources.getQuantityString(
            R.plurals.numberOfFeeds,
            count,
            count
        )

        Snackbar.make(
            recyclerView,
            getString(R.string.feed_removed_message, feedsRemoved),
            Snackbar.LENGTH_LONG
        ).setAction(R.string.done) {
            callbacks?.onFinished()
        }.show()
    }

    private fun handleEditCategory(): Boolean {
        if (anyItemIsSelected()) {
            val count = viewModel.selectedItems.size
            val title = if (count == 1) {
                viewModel.selectedItems[0].title
            } else null

            EditCategoryFragment.newInstance(viewModel.getCategories(), title, count).apply {
                setTargetFragment(fragment, 0)
                show(fragment.parentFragmentManager, "edit category")
            }
        } else {
            showNothingSelectedNotice(ACTION_EDIT)
        }
        return true
    }

    override fun onEditCategoryConfirmed(category: String) {
        val ids = mutableListOf<String>()
        for (feed in viewModel.selectedItems) {
            ids.add(feed.url)
        }

        viewModel.updateCategoryByFeedIds(ids, category)
        adapter.notifyDataSetChanged()
        resetSelection()

        // Crude solution to Snackbar jumping: wait until keyboard is fully hidden
        handler.postDelayed({
            showFeedsCategorizedNotice(category, ids.size)
        }, 400)
    }

    private fun showFeedsCategorizedNotice(category: String, count: Int) {
        val feedsUpdated = resources.getQuantityString(
            R.plurals.numberOfFeeds,
            count,
            count
        )

        Snackbar.make(
            recyclerView,
            getString(R.string.category_assigned, category, feedsUpdated),
            Snackbar.LENGTH_LONG
        ).setAction(R.string.done) {
            callbacks?.onFinished()
        }.show()
    }

    private fun showNothingSelectedNotice(action: Int) {
        val actionString = when (action) {
            ACTION_EDIT -> R.string.edit
            ACTION_REMOVE -> R.string.remove
            ACTION_EXPORT -> R.string.export
            else -> throw IllegalArgumentException()
        }.run {
            getString(this)
        }

        Snackbar.make(
            recyclerView,
            getString(R.string.select_feeds_to, actionString),
            Snackbar.LENGTH_SHORT
        ).show()
    }

    fun writeOpml(uri: Uri) {
        opmlExporter?.submitUri(uri)
    }

    private fun allItemsAreSelected(): Boolean {
        return viewModel.selectedItems.size == adapter.currentList.size
    }

    private fun anyItemIsSelected(): Boolean {
        return viewModel.selectedItems.size > 0
    }

    private fun resetSelection() {
        viewModel.selectedItems.clear()
        updateToolbar(toolbar, viewModel.selectedItems.size)
        selectAllCheckBox.isChecked = false
        adapter.toggleCheckBoxes(false)
    }

    override fun onItemClicked(feed: FeedMinimal, isChecked: Boolean) {
        if (isChecked) {
            viewModel.selectedItems.add(feed)
            selectAllCheckBox.isChecked = allItemsAreSelected()
        } else {
            viewModel.selectedItems.remove(feed)
            selectAllCheckBox.isChecked = false
        }

        adapter.selectedItems = viewModel.selectedItems
        updateToolbar(toolbar, viewModel.selectedItems.size)
    }

    override fun onAllItemsChecked(isChecked: Boolean) {
        selectAllCheckBox.isChecked = isChecked
    }

    override fun onExportAttempted(isSuccessful: Boolean, fileName: String?) {
        val message = if (isSuccessful) {
            getString(R.string.exported_opml_message, fileName)
        } else {
            getString(R.string.error_message)
        }

        Snackbar.make(
            recyclerView,
            message,
            Snackbar.LENGTH_SHORT
        ).show()
    }

    override fun onStop() {
        super.onStop()
        context?.let { context ->
            NiceFeedPreferences.saveFeedManagerOrder(context, viewModel.currentOrder)
        }
    }

    companion object {
        private const val ACTION_REMOVE = 0
        private const val ACTION_EDIT = 1
        private const val ACTION_EXPORT = 2

        fun newInstance(): ManageFeedsFragment {
            return ManageFeedsFragment()
        }
    }
}