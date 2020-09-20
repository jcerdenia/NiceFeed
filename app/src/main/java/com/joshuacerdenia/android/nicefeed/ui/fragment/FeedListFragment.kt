package com.joshuacerdenia.android.nicefeed.ui.fragment

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.joshuacerdenia.android.nicefeed.R
import com.joshuacerdenia.android.nicefeed.data.local.NiceFeedPreferences
import com.joshuacerdenia.android.nicefeed.ui.adapter.FeedListAdapter
import com.joshuacerdenia.android.nicefeed.ui.viewmodel.FeedListViewModel
import com.joshuacerdenia.android.nicefeed.utils.extensions.addRipple

class FeedListFragment: VisibleFragment(), FeedListAdapter.OnItemClickListener {

    interface Callbacks {
        fun onMenuItemSelected(item: Int)
        fun onFeedSelected(feedId: String, activeFeedId: String?)
        fun onNoFeeds()
    }

    private lateinit var viewModel: FeedListViewModel
    private lateinit var manageButton: Button
    private lateinit var addButton: Button
    private lateinit var newEntriesButton: Button
    private lateinit var starredEntriesButton: Button
    private lateinit var settingsButton: Button
    private lateinit var bottomDivider: View
    private lateinit var recyclerView: RecyclerView
    lateinit var adapter: FeedListAdapter

    private var callbacks: Callbacks? = null
    private val handler = Handler()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = activity as Callbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FeedListViewModel::class.java)
        adapter = FeedListAdapter(context, this)
        
        context?.let { context ->
            viewModel.setFeedOrder(NiceFeedPreferences.getFeedsOrder(context))
            viewModel.setMinimizedCategories(NiceFeedPreferences.getMinimizedCategories(context))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_feed_list, container, false)
        manageButton = view.findViewById(R.id.manage_button)
        addButton = view.findViewById(R.id.add_button)
        newEntriesButton = view.findViewById(R.id.recent_entries_button)
        starredEntriesButton = view.findViewById(R.id.starred_entries_button)
        settingsButton = view.findViewById(R.id.settings_button)
        bottomDivider = view.findViewById(R.id.bottom_divider)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.feedListLiveData.observe(viewLifecycleOwner, { list ->
            adapter.submitList(list)
            if (list.isNotEmpty()) {
                manageButton.visibility = View.VISIBLE
                newEntriesButton.visibility = View.VISIBLE
                starredEntriesButton.visibility = View.VISIBLE
                bottomDivider.visibility = View.VISIBLE
            } else {
                manageButton.visibility = View.GONE
                newEntriesButton.visibility = View.GONE
                starredEntriesButton.visibility = View.GONE
                bottomDivider.visibility = View.GONE
                updateActiveFeedId(null)
                callbacks?.onNoFeeds()
            }
        })
    }

    override fun onStart() {
        super.onStart()
        manageButton.setOnClickListener {
            callbacks?.onMenuItemSelected(ITEM_MANAGE_FEEDS)
        }

        addButton.setOnClickListener {
            callbacks?.onMenuItemSelected(ITEM_ADD_FEEDS)
        }

        newEntriesButton.setOnClickListener {
            callbacks?.onFeedSelected(EntryListFragment.FOLDER_NEW, viewModel.activeFeedId)
        }

        starredEntriesButton.setOnClickListener {
            callbacks?.onFeedSelected(EntryListFragment.FOLDER_STARRED, viewModel.activeFeedId)
        }

        settingsButton.setOnClickListener {
            callbacks?.onMenuItemSelected(ITEM_SETTINGS)
        }
    }

    override fun onResume() {
        super.onResume()
        context?.let { context ->
            NiceFeedPreferences.getFeedsOrder(context).run {
                viewModel.setFeedOrder(this)
            }
        }
    }

    override fun onFeedSelected(feedId: String) {
        starredEntriesButton.addRipple()
        newEntriesButton.addRipple()

        callbacks?.onFeedSelected(feedId, viewModel.activeFeedId)
        viewModel.activeFeedId = feedId

        handler.postDelayed({
            recyclerView.adapter = adapter
        }, 500)
    }

    override fun onCategoryClicked(category: String) {
        viewModel.toggleCategoryDropDown(category)
    }

    fun updateActiveFeedId(feedId: String?) {
        if (feedId == EntryListFragment.FOLDER_NEW) {
            starredEntriesButton.addRipple()
        } else if (feedId == EntryListFragment.FOLDER_STARRED) {
            newEntriesButton.addRipple()
        }

        viewModel.activeFeedId = feedId
        adapter.setActiveFeedId(feedId)
        recyclerView.adapter = adapter

        context?.let { context ->
            val color = ContextCompat.getColor(context, R.color.colorSelect)
            if (feedId == EntryListFragment.FOLDER_NEW) {
                newEntriesButton.setBackgroundColor(color)
            } else if (feedId == EntryListFragment.FOLDER_STARRED) {
                starredEntriesButton.setBackgroundColor(color)
            }
        }
    }

    fun getCategories(): Array<String> {
        return viewModel.categories
    }

    override fun onStop() {
        super.onStop()
        context?.let { context ->
            NiceFeedPreferences.saveMinimizedCategories(context, viewModel.minimizedCategories)
        }
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    companion object {
        const val ITEM_MANAGE_FEEDS = 0
        const val ITEM_ADD_FEEDS = 1
        const val ITEM_SETTINGS = 2

        fun newInstance(): FeedListFragment {
            return FeedListFragment()
        }
    }
}