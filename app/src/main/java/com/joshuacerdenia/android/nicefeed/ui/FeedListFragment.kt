package com.joshuacerdenia.android.nicefeed.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.joshuacerdenia.android.nicefeed.R
import com.joshuacerdenia.android.nicefeed.data.model.Feed
import com.joshuacerdenia.android.nicefeed.data.model.FeedInfo
import com.joshuacerdenia.android.nicefeed.data.model.FeedListItem
import com.joshuacerdenia.android.nicefeed.utils.Utils
import com.joshuacerdenia.android.nicefeed.utils.sortedByUnreadCount

private const val TAG = "FeedListFragment"

class FeedListFragment: Fragment(), FeedListAdapter.OnItemClickListener {

    companion object {
        fun newInstance(): FeedListFragment {
            return FeedListFragment()
        }
    }

    val viewModel: FeedListViewModel by lazy {
        ViewModelProvider(this).get(FeedListViewModel::class.java)
    }

    private lateinit var manageButton: Button
    private lateinit var addButton: Button
    private lateinit var bottomDivider: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FeedListAdapter
    private var callbacks: Callbacks? = null

    interface Callbacks {
        fun onManageFeedsSelected()
        fun onAddFeedSelected()
        fun onFeedSelected(currentFeedId: String?, newFeedId: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = activity as Callbacks?
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = FeedListAdapter(context, this, viewModel.currentFeedId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_feed_list, container, false)
        manageButton = view.findViewById(R.id.button_manage)
        addButton = view.findViewById(R.id.button_add)
        bottomDivider = view.findViewById(R.id.divider_bottom)
        recyclerView = view.findViewById(R.id.recyclerView_feed)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        manageButton.setOnClickListener {
            callbacks?.onManageFeedsSelected()
        }

        addButton.setOnClickListener {
            callbacks?.onAddFeedSelected()
        }

        /*
        viewModel.feedListLiveData.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                manageButton.isEnabled = true
                bottomDivider.visibility = View.VISIBLE
            } else {
                manageButton.isEnabled = false
                bottomDivider.visibility = View.GONE
            }

            // TODO: Sort feeds by title, date updated, etc.
            val arrangedList = arrangeFeedsAndCategories(it.sortedByUnreadCount())
            adapter.submitList(arrangedList)
        }) */

        viewModel.feedsInfoLiveData.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                manageButton.isEnabled = true
                bottomDivider.visibility = View.VISIBLE
            } else {
                manageButton.isEnabled = false
                bottomDivider.visibility = View.GONE
            }

            val arrangedList = arrangeFeedsAndCategories(it.sortedByUnreadCount())
            adapter.submitList(arrangedList)
        })
    }

    private fun arrangeFeedsAndCategories(feeds: List<FeedInfo>): List<FeedListItem> {
        viewModel.categories = getCategories(feeds)
        val arrangedList: MutableList<FeedListItem> = mutableListOf()

        for (category in viewModel.categories) {
            arrangedList.add(FeedListItem(category))

            for (feed in feeds) {
                if (feed.category == category) {
                    arrangedList.add(FeedListItem(feed))
                }
            }
        }

        return arrangedList
    }

    // TODO Fix later
    private fun getCategories(feeds: List<FeedInfo>): List<String> {
        val categories: MutableSet<String> = mutableSetOf()
        for (feed in feeds) {
            categories.add(feed.category)
        }
        return categories.toList().sorted()
    }

    fun setActiveSelection(feedId: String?) {
        viewModel.currentFeedId = feedId
        adapter.currentFeedId = feedId
        recyclerView.adapter = adapter
    }

    override fun onItemClicked(feedId: String) {
        callbacks?.onFeedSelected(viewModel.currentFeedId, feedId)
    }
}