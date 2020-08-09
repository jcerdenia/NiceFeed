package com.joshuacerdenia.android.nicefeed.ui

import android.content.Context
import android.os.Bundle
import android.os.Handler
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
import com.joshuacerdenia.android.nicefeed.data.local.UserPreferences
import com.joshuacerdenia.android.nicefeed.data.model.Feed
import com.joshuacerdenia.android.nicefeed.data.model.FeedIdPair
import com.joshuacerdenia.android.nicefeed.utils.sortedByUnreadCount

private const val TAG = "FeedListFragment"

class FeedListFragment: Fragment(), FeedListAdapter.OnItemClickListener {

    companion object {
        private const val ARG_ACTIVE_FEED_ID = "ARG_ACTIVE_FEED_ID"

        fun newInstance(activeFeedId: String?): FeedListFragment {
            return FeedListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_ACTIVE_FEED_ID, activeFeedId)
                }
            }
        }

        fun newInstance(): FeedListFragment {
            return FeedListFragment()
        }
    }

    private val viewModel: FeedListViewModel by lazy {
        ViewModelProvider(this).get(FeedListViewModel::class.java)
    }

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
        fun onNoFeedsToLoad()
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
        if (viewModel.activeFeedId == null) {
            viewModel.activeFeedId = arguments?.getString(ARG_ACTIVE_FEED_ID)
        }

        adapter = FeedListAdapter(context, this, viewModel.activeFeedId)
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

        viewModel.feedsLiveData.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                manageButton.isEnabled = true
                bottomDivider.visibility = View.VISIBLE

                if (viewModel.isInitialLoading) {
                    handleInitialLoading(it)
                    viewModel.isInitialLoading = false
                }

            } else {
                manageButton.isEnabled = false
                bottomDivider.visibility = View.GONE
                callbacks?.onNoFeedsToLoad()
            }

            adapter.submitFeeds(it.sortedByUnreadCount())
        })
    }

    private fun handleInitialLoading(feeds: List<Feed>) {
        for (feed in feeds) {
            if (viewModel.activeFeedId == feed.url) {
                callbacks?.onFeedSelected(FeedIdPair(feed.url, feed.title), null)
                return
            }
        }

        callbacks?.onNoFeedsToLoad()
    }

    override fun onItemClicked(feed: Feed) {
        callbacks?.onFeedSelected(FeedIdPair(feed.url, feed.title), viewModel.activeFeedId)
        viewModel.activeFeedId = feed.url

        handler.postDelayed({
                recyclerView.adapter = adapter
        }, 500)
    }

    fun forceUpdateActiveFeedId(feedId: String?) {
        viewModel.activeFeedId = feedId
        adapter.overrideActiveFeedId(feedId)
        recyclerView.adapter = adapter
    }

    override fun onStop() {
        super.onStop()
        if (context != null) {
            viewModel.activeFeedId?.let { UserPreferences.saveFeedId(context!!, it) }
        }
    }
}