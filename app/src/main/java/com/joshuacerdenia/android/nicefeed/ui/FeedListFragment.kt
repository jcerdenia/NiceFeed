package com.joshuacerdenia.android.nicefeed.ui

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.joshuacerdenia.android.nicefeed.R
import com.joshuacerdenia.android.nicefeed.data.local.NiceFeedPreferences
import com.joshuacerdenia.android.nicefeed.data.model.Feed
import com.joshuacerdenia.android.nicefeed.data.model.FeedIdPair

private const val TAG = "FeedListFragment"

class FeedListFragment: VisibleFragment(), FeedListAdapter.OnItemClickListener {

    private lateinit var viewModel: FeedListViewModel
    private lateinit var manageButton: Button
    private lateinit var addButton: Button
    private lateinit var settingsButton: Button
    private lateinit var bottomDivider: View
    private lateinit var recyclerView: RecyclerView
    lateinit var adapter: FeedListAdapter
    private val handler = Handler()
    private var callbacks: Callbacks? = null

    interface Callbacks {
        fun onManageFeedsSelected()
        fun onAddFeedSelected()
        fun onFeedSelected(feedIdPair: FeedIdPair, activeFeedId: String?)
        fun onSettingsSelected()
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
        viewModel = ViewModelProvider(this).get(FeedListViewModel::class.java)
        adapter = FeedListAdapter(context, this)
        context?.let { context ->
            viewModel.setMinimizedCategories(NiceFeedPreferences.getMinimizedCategories(context))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_feed_list, container, false)
        manageButton = view.findViewById(R.id.button_manage)
        addButton = view.findViewById(R.id.button_add)
        settingsButton = view.findViewById(R.id.button_settings)
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

        settingsButton.setOnClickListener {
            callbacks?.onSettingsSelected()
        }

        viewModel.feedListLiveData.observe(viewLifecycleOwner, Observer { list ->
            adapter.submitList(list)

            if (list.isNotEmpty()) {
                manageButton.isEnabled = true
                bottomDivider.visibility = View.VISIBLE
            } else {
                manageButton.isEnabled = false
                bottomDivider.visibility = View.GONE
                updateActiveFeedId(null)
            }
        })
    }

    override fun onFeedSelected(feed: Feed) {
        callbacks?.onFeedSelected(FeedIdPair(feed.url, feed.title), viewModel.activeFeedId)
        viewModel.activeFeedId = feed.url

        handler.postDelayed({
                recyclerView.adapter = adapter
        }, 500)
    }

    override fun onCategoryClicked(category: String) {
        viewModel.toggleCategoryDropDown(category)
    }

    fun updateActiveFeedId(feedId: String?) {
        viewModel.activeFeedId = feedId
        adapter.setActiveFeedId(feedId)
        recyclerView.adapter = adapter
    }

    fun getCategories(): Array<String> {
        return viewModel.categories
    }

    override fun onStop() {
        super.onStop()
        context?.let { context ->
            NiceFeedPreferences.saveLastViewedFeedId(context, viewModel.activeFeedId)
            NiceFeedPreferences.saveMinimizedCategories(context, viewModel.minimizedCategories)
        }
    }

    companion object {
        fun newInstance(): FeedListFragment {
            return FeedListFragment()
        }
    }
}