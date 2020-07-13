package com.joshuacerdenia.android.nicefeed.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.joshuacerdenia.android.nicefeed.R
import com.joshuacerdenia.android.nicefeed.data.model.Feed
import com.joshuacerdenia.android.nicefeed.ui.dialog.SetCategoryDialogFragment
import com.joshuacerdenia.android.nicefeed.utils.Utils

private const val TAG = "ManageFeedsFragment"

class ManageFeedsFragment: Fragment(),
    SetCategoryDialogFragment.Callbacks,
    FeedManagerAdapter.ItemCheckBoxListener {

    companion object {
        fun newInstance(): ManageFeedsFragment {
            return ManageFeedsFragment()
        }
    }

    private val fragment = this@ManageFeedsFragment
    private val manageFeedsViewModel: ManageFeedsViewModel by lazy {
        ViewModelProvider(this).get(ManageFeedsViewModel::class.java)
    }

    private lateinit var selectAllCheckBox: CheckBox
    private lateinit var feedRecyclerView: RecyclerView
    private lateinit var adapter: FeedManagerAdapter

    private var categories = listOf<String>()

    private var callbacks: Callbacks? = null

    interface Callbacks {
        fun onItemsSelected(count: Int)
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
        adapter = FeedManagerAdapter(this, manageFeedsViewModel.selectedItems)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_manage_feeds, menu)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_manage_feeds, container, false)

        selectAllCheckBox = view.findViewById(R.id.select_all_checkbox)
        feedRecyclerView = view.findViewById(R.id.feed_recycler_view)
        feedRecyclerView.layoutManager = LinearLayoutManager(context)
        feedRecyclerView.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onSelectedItemsChanged(manageFeedsViewModel.selectedItems.size)

        selectAllCheckBox.apply {
            setOnClickListener {
               if (isChecked) {
                   manageFeedsViewModel.selectedItems = adapter.currentList.toMutableList()
                   adapter.onSelectAllChecked(true)
               } else {
                   manageFeedsViewModel.selectedItems.clear()
                   adapter.onSelectAllChecked(false)
               }

                onSelectedItemsChanged(manageFeedsViewModel.selectedItems.size)
            }
        }

        manageFeedsViewModel.feedListLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                Log.d(TAG, "Got ${it.size} feeds")
                categories = Utils.getCategories(it)
                adapter.submitList(it.reversed())
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_item_edit -> handleEditCategory()
            R.id.menu_item_delete -> handleDeleteFeeds()
            R.id.menu_item_select_all -> {
                // TODO
                true
            }
            R.id.menu_item_sort -> {
                // TODO
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun handleDeleteFeeds(): Boolean {
        if (anyItemIsSelected()) {
            // TODO
        } else {
            showNothingSelectedNotice()
        }
        return true
    }

    private fun handleEditCategory(): Boolean {
        if (anyItemIsSelected()) {
            val numberOfSelected = manageFeedsViewModel.selectedItems.size
            SetCategoryDialogFragment.newInstance(numberOfSelected, categories).apply {
                setTargetFragment(fragment, 0)
                show(fragment.requireFragmentManager(), "set category")
            }
        } else {
            showNothingSelectedNotice()
        }
        return true
    }

    private fun showNothingSelectedNotice() {
        Snackbar.make(
            feedRecyclerView,
            getString(R.string.nothing_selected),
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun onSelectedItemsChanged(count: Int) {
        callbacks?.onItemsSelected(count)
    }

    override fun onSetCategoryConfirmed(category: String) {
        // TODO
        /*
        val categorizedFeeds = mutableListOf<Feed>()
        for (feed in manageFeedsViewModel.getSelectedItems() {
            feed.category = category
            categorizedFeeds.add(feed)
        }

        manageFeedsViewModel.updateFeeds(categorizedFeeds)*/
    }

    private fun allItemsAreSelected(): Boolean {
        return manageFeedsViewModel.selectedItems.size == adapter.currentList.size
    }

    private fun anyItemIsSelected(): Boolean {
        return manageFeedsViewModel.selectedItems.size > 0
    }

    override fun onItemClicked(feed: Feed, isChecked: Boolean) {
        if (isChecked) {
            manageFeedsViewModel.selectedItems.add(feed)
            selectAllCheckBox.isChecked = allItemsAreSelected()
        } else {
            manageFeedsViewModel.selectedItems.remove(feed)
            selectAllCheckBox.isChecked = false
        }

        onSelectedItemsChanged(manageFeedsViewModel.selectedItems.size)
    }

    override fun onAllItemsChecked(isChecked: Boolean) {
        selectAllCheckBox.isChecked = isChecked
    }
}