package com.joshuacerdenia.android.nicefeed.ui

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.CheckBox
import android.widget.ProgressBar
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.joshuacerdenia.android.nicefeed.R
import com.joshuacerdenia.android.nicefeed.data.local.UserPreferences
import com.joshuacerdenia.android.nicefeed.data.model.Feed
import com.joshuacerdenia.android.nicefeed.ui.dialog.ConfirmRemoveFragment
import com.joshuacerdenia.android.nicefeed.ui.dialog.EditCategoryFragment
import com.joshuacerdenia.android.nicefeed.ui.dialog.SortFeedManagerFragment
import com.joshuacerdenia.android.nicefeed.utils.Utils
import com.joshuacerdenia.android.nicefeed.utils.sortedByCategory
import com.joshuacerdenia.android.nicefeed.utils.sortedByTitle
import com.joshuacerdenia.android.nicefeed.utils.sortedByUpdated

private const val TAG = "ManageFeedsFragment"
private const val ACTION_REMOVE = 0
private const val ACTION_EDIT = 1
const val SORT_BY_ADDED = 0
const val SORT_BY_UPDATED = 1
const val SORT_BY_CATEGORY = 2
const val SORT_BY_TITLE = 3

class ManageFeedsFragment: Fragment(),
    EditCategoryFragment.Callbacks,
    ConfirmRemoveFragment.Callbacks,
    SortFeedManagerFragment.Callbacks,
    FeedManagerAdapter.ItemCheckBoxListener {

    companion object {
        fun newInstance(): ManageFeedsFragment {
            return ManageFeedsFragment()
        }
    }

    private val fragment = this@ManageFeedsFragment
    private val viewModel: ManageFeedsViewModel by lazy {
        ViewModelProvider(this).get(ManageFeedsViewModel::class.java)
    }

    private lateinit var nestedScrollView: NestedScrollView
    private lateinit var progressBar: ProgressBar
    private lateinit var selectAllCheckBox: CheckBox
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FeedManagerAdapter
    private var categories = listOf<String>()
    private var callbacks: Callbacks? = null

    interface Callbacks {
        fun onSelectedItemsChanged(count: Int)
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
            R.id.menu_item_edit -> handleEditCategory()
            R.id.menu_item_remove -> handleRemoveSelected()
            R.id.menu_item_sort -> handleSortFeeds()
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_manage_feeds, container, false)

        nestedScrollView = view.findViewById(R.id.nested_scroll_view)
        progressBar = view.findViewById(R.id.progress_bar)
        selectAllCheckBox = view.findViewById(R.id.select_all_checkbox)
        recyclerView = view.findViewById(R.id.feed_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        attachAdapter()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callbacks?.onSelectedItemsChanged(viewModel.selectedItems.size)
        progressBar.visibility = View.VISIBLE

        selectAllCheckBox.setOnClickListener { (it as CheckBox)
            if (it.isChecked) {
                viewModel.selectedItems = adapter.currentList.toMutableList()
            } else {
                viewModel.selectedItems.clear()
            }

            adapter.handleCheckBoxes(it.isChecked)
            callbacks?.onSelectedItemsChanged(viewModel.selectedItems.size)
        }

        observeFeedListLiveData()
    }

    private fun attachAdapter() {
        recyclerView.adapter = adapter
    }

    private fun observeFeedListLiveData() {
        val sortBy = context?.let {
            UserPreferences.getFeedManagerSortPref(it)
        } ?: SORT_BY_ADDED

        viewModel.feedListLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                selectAllCheckBox.visibility = if (it.size > 1) {
                    View.VISIBLE
                } else {
                    View.GONE
                }

                categories = Utils.getCategories(it)
                val sortedFeeds = sortFeeds(it, sortBy)
                adapter.submitList(sortedFeeds)
                progressBar.visibility = View.GONE
            }
        })
    }

    private fun handleSortFeeds(): Boolean {
        SortFeedManagerFragment.newInstance().apply {
            setTargetFragment(fragment, 0)
            show(fragment.requireFragmentManager(), "sort")
        }
        return true
    }

    override fun onSortPreferenceSelected() {
        observeFeedListLiveData()
    }

    private fun sortFeeds(feeds: List<Feed>, sortBy: Int): List<Feed> {
        return when (sortBy) {
            SORT_BY_UPDATED -> feeds.sortedByUpdated()
            SORT_BY_CATEGORY -> feeds.sortedByCategory()
            SORT_BY_TITLE -> feeds.sortedByTitle()
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
        } else showNothingSelectedNotice(ACTION_REMOVE)
        return true
    }

    override fun onRemoveConfirmed(count: Int) {
        val websites = mutableListOf<String>()
        for (feed in viewModel.selectedItems) {
            websites.add(feed.website)
        }

        resetSelection()
        viewModel.deleteFeedsWithEntries(websites)
        showFeedsRemovedNotice(count)
    }

    private fun showFeedsRemovedNotice(count: Int) {
        val feedsRemoved = resources.getQuantityString(
            R.plurals.numberOfFeeds,
            count,
            count
        )

        Snackbar.make(
            nestedScrollView,
            getString(R.string.removed_feeds, feedsRemoved),
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
        val updatedFeeds = mutableListOf<Feed>()
        for (feed in viewModel.selectedItems) {
            feed.category = category
            updatedFeeds.add(feed)
        }

        viewModel.updateFeeds(updatedFeeds)
        adapter.notifyDataSetChanged()
        resetSelection()

        // Crude solution to Snackbar jumping; wait until keyboard is fully hidden
        val handler = Handler()
        handler.postDelayed({
            showFeedsCategorizedNotice(category, updatedFeeds.size)
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

    private fun allItemsAreSelected(): Boolean {
        return viewModel.selectedItems.size == adapter.currentList.size
    }

    private fun anyItemIsSelected(): Boolean {
        return viewModel.selectedItems.size > 0
    }

    private fun resetSelection() {
        viewModel.selectedItems.clear()
        callbacks?.onSelectedItemsChanged(viewModel.selectedItems.size)
        selectAllCheckBox.isChecked = false
        adapter.handleCheckBoxes(false)
    }

    override fun onItemClicked(feed: Feed, isChecked: Boolean) {
        if (isChecked) {
            viewModel.selectedItems.add(feed)
            selectAllCheckBox.isChecked = allItemsAreSelected()
        } else {
            viewModel.selectedItems.remove(feed)
            selectAllCheckBox.isChecked = false
        }

        callbacks?.onSelectedItemsChanged(viewModel.selectedItems.size)
    }

    override fun onAllItemsChecked(isChecked: Boolean) {
        selectAllCheckBox.isChecked = isChecked
    }
}