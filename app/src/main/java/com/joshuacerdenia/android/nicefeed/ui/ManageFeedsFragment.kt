package com.joshuacerdenia.android.nicefeed.ui

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.joshuacerdenia.android.nicefeed.R
import com.joshuacerdenia.android.nicefeed.data.local.NiceFeedPreferences
import com.joshuacerdenia.android.nicefeed.data.model.FeedMinimal
import com.joshuacerdenia.android.nicefeed.ui.dialog.ConfirmRemoveFragment
import com.joshuacerdenia.android.nicefeed.ui.dialog.EditCategoryFragment
import com.joshuacerdenia.android.nicefeed.ui.dialog.SortFeedManagerFragment
import com.joshuacerdenia.android.nicefeed.utils.OpmlExporter
import com.joshuacerdenia.android.nicefeed.utils.sortedByCategory
import com.joshuacerdenia.android.nicefeed.utils.sortedByTitle

private const val TAG = "ManageFeedsFragment"

class ManageFeedsFragment: VisibleFragment(),
    EditCategoryFragment.Callbacks,
    ConfirmRemoveFragment.Callbacks,
    SortFeedManagerFragment.Callbacks,
    FeedManagerAdapter.ItemCheckBoxListener {

    companion object {
        private const val ACTION_REMOVE = 0
        private const val ACTION_EDIT = 1

        fun newInstance(): ManageFeedsFragment {
            return ManageFeedsFragment()
        }
    }

    private val fragment = this@ManageFeedsFragment
    private val viewModel: ManageFeedsViewModel by lazy {
        ViewModelProvider(this).get(ManageFeedsViewModel::class.java)
    }

    private lateinit var toolbar: Toolbar
    private lateinit var progressBar: ProgressBar
    private lateinit var emptyListImage: ImageView
    private lateinit var selectAllCheckBox: CheckBox
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FeedManagerAdapter
    private var opmlExporter: OpmlExporter? = null
    private val handler = Handler()
    private var categories = arrayOf<String>()
    private var callbacks: Callbacks? = null

    interface Callbacks {
        fun onFeedsBeingManagedChanged(count: Int)
        fun onAddFeedsSelected()
        fun onExportOpmlSelected()
        fun onDoneManaging()
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
        adapter = FeedManagerAdapter(this, viewModel.selectedItems)
        setHasOptionsMenu(true)
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
            R.id.menuItem_export_opml -> {
                callbacks?.onExportOpmlSelected()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_manage_feeds, container, false)
        toolbar = view.findViewById(R.id.toolbar)
        progressBar = view.findViewById(R.id.progressBar)
        emptyListImage = view.findViewById(R.id.imageView_empty)
        selectAllCheckBox = view.findViewById(R.id.checkBox_select_all)
        recyclerView = view.findViewById(R.id.recyclerView_feed)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        toolbar.title = "Fuck this shit"
        (activity as AppCompatActivity?)?.setSupportActionBar(toolbar)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callbacks?.onFeedsBeingManagedChanged(viewModel.selectedItems.size)
        opmlExporter = context?.let { context -> OpmlExporter(context, recyclerView) }
        progressBar.visibility = View.VISIBLE

        selectAllCheckBox.setOnClickListener { (it as CheckBox)
            if (it.isChecked) {
                viewModel.selectedItems = adapter.currentList.toMutableList()
            } else {
                viewModel.selectedItems.clear()
            }

            adapter.toggleCheckBoxes(it.isChecked)
            callbacks?.onFeedsBeingManagedChanged(viewModel.selectedItems.size)
        }

        observeFeedsLiveData()
    }

    private fun observeFeedsLiveData() {
        viewModel.feedsMinimalLiveData.observe(viewLifecycleOwner, Observer { feeds ->
            selectAllCheckBox.visibility = if (feeds.size > 1) {
                View.VISIBLE
            } else {
                View.GONE
            }

            categories = getCategories(feeds)
            opmlExporter?.submitFeeds(feeds, categories)
            adapter.submitList(getSortedList(feeds))
            progressBar.visibility = View.GONE

            if (feeds.isEmpty()) {
                emptyListImage.visibility = View.VISIBLE
            }
        })
    }

    private fun getCategories(feeds: List<FeedMinimal>): Array<String> {
        val categories: MutableSet<String> = mutableSetOf()
        for (feed in feeds) {
            categories.add(feed.category)
        }
        return categories.toTypedArray()
    }

    private fun handleSortFeeds(): Boolean {
        SortFeedManagerFragment.newInstance().apply {
            setTargetFragment(fragment, 0)
            show(fragment.requireFragmentManager(), "sort")
        }
        return true
    }

    override fun onSortPreferenceSelected() {
        observeFeedsLiveData()
    }

    private fun getSortedList(feeds: List<FeedMinimal>): List<FeedMinimal> {
        val sortBy = context?.let {
            NiceFeedPreferences.getFeedManagerOrder(it)
        } ?: SortFeedManagerFragment.SORT_BY_ADDED

        return when (sortBy) {
            SortFeedManagerFragment.SORT_BY_CATEGORY -> feeds.sortedByCategory()
            SortFeedManagerFragment.SORT_BY_TITLE -> feeds.sortedByTitle()
            else -> feeds.reversed() // Default
        }
    }

    private fun handleRemoveSelected(): Boolean {
        if (anyItemIsSelected()) {
            val count = viewModel.selectedItems.size
            val title = if (count == 1) {
                viewModel.selectedItems[0].title
            } else null

            ConfirmRemoveFragment.newInstance(title, count).apply {
                setTargetFragment(fragment, 0)
                show(fragment.requireFragmentManager(),"unsubscribe")
            }

            return true
        } else {
            showNothingSelectedNotice(ACTION_REMOVE)
            return false
        }
    }

    override fun onRemoveConfirmed() {
        val feedIds = mutableListOf<String>()
        for (feed in viewModel.selectedItems) {
            feedIds.add(feed.url)
        }

        if (feedIds.size == 1) {
            showFeedsRemovedNotice(title = viewModel.selectedItems[0].title)
        } else {
            if (feedIds.size == adapter.currentList.size) {
                context?.let { context ->
                    NiceFeedPreferences.saveLastViewedFeedId(context, null)
                }
            }
            showFeedsRemovedNotice(feedIds.size)
        }

        viewModel.deleteFeedsAndEntriesByIds(feedIds)
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
            callbacks?.onDoneManaging()
        }.show()
    }

    private fun handleEditCategory(): Boolean {
        if (anyItemIsSelected()) {
            val count = viewModel.selectedItems.size
            val title = if (count == 1) {
                viewModel.selectedItems[0].title
            } else null

            EditCategoryFragment.newInstance(categories, title, count).apply {
                setTargetFragment(fragment, 0)
                show(fragment.requireFragmentManager(), "edit category")
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
            callbacks?.onDoneManaging()
        }.show()
    }

    private fun showNothingSelectedNotice(action: Int) {
        val actionString = if (action == ACTION_REMOVE) {
            getString(R.string.remove)
        } else {
            getString(R.string.edit)
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
        callbacks?.onFeedsBeingManagedChanged(viewModel.selectedItems.size)
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

        callbacks?.onFeedsBeingManagedChanged(viewModel.selectedItems.size)
    }

    override fun onAllItemsChecked(isChecked: Boolean) {
        selectAllCheckBox.isChecked = isChecked
    }
}