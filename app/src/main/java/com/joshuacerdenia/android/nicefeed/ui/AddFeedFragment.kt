package com.joshuacerdenia.android.nicefeed.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.joshuacerdenia.android.nicefeed.R
import com.joshuacerdenia.android.nicefeed.data.model.Feed
import com.joshuacerdenia.android.nicefeed.data.model.FeedWithEntries
import com.joshuacerdenia.android.nicefeed.utils.Utils
import kotlinx.android.synthetic.main.fragment_add_feed.*
import java.util.*

private const val TAG = "AddFeedFragment"

open class AddFeedFragment: Fragment() {

    companion object {
        private lateinit var enterUrlEditText: EditText
        private lateinit var submitButton: Button
        private lateinit var searchView: SearchView

        fun newInstance(): AddFeedFragment {
            return AddFeedFragment()
        }
    }

    private val addFeedViewModel: AddFeedViewModel by lazy {
        ViewModelProvider(this).get(AddFeedViewModel::class.java)
    }

    var currentFeeds: MutableSet<String> = mutableSetOf()
    lateinit var progressBar: ProgressBar

    private var callbacks: Callbacks? = null

    interface Callbacks {
        fun onNewFeedAdded(website: String)
        fun onQuerySubmitted(query: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = activity as Callbacks?
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_feed, container, false)
        searchView = view.findViewById(R.id.search_view)
        enterUrlEditText = view.findViewById(R.id.enter_url)
        submitButton = view.findViewById(R.id.submit_button)
        progressBar = view.findViewById(R.id.progress_bar)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addFeedViewModel.feedListLiveData.observe(viewLifecycleOwner, Observer {
            for (feed in it) {
                currentFeeds.add(feed.website)
            }
        })

        addFeedViewModel.feedRequestLiveData?.observe(viewLifecycleOwner, Observer {
            handleFeedRequestResult(
                addFeedViewModel,
                linear_layout,
                it,
                R.string.failed_to_get_feed
            )

            progressBar.visibility = View.GONE
            submitButton.apply {
                isEnabled = true
                text = getString(R.string.submit)
            }
        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(queryText: String): Boolean {
                if (queryText.isNotBlank()) {
                    callbacks?.onQuerySubmitted(queryText)
                }
                return true
            }

            override fun onQueryTextChange(queryText: String?): Boolean {
                return true
            }
        })

        submitButton.setOnClickListener {
            val address = enterUrlEditText.text.toString().substringAfter("://")
                .toLowerCase(Locale.ROOT).trim()

            val url = "https://$address"
            addFeedViewModel.requestFeed(url)
            Log.d(TAG, "Requesting $url...")

            progressBar.visibility = View.VISIBLE
            submitButton.apply {
                isEnabled = false
                text = getString(R.string.loading)
            }
            activity?.let { view -> Utils.hideSoftKeyBoard(view, it) }
        }
    }

    fun handleFeedRequestResult(
        viewModel: AddFeedViewModel,
        view: View,
        feedWithEntries: FeedWithEntries?,
        errorMessageResId: Int) {

        feedWithEntries?.let {
            if (!isAlreadyAdded(it.feed)) {
                viewModel.saveFeedWithEntries(it)
                showFeedAddedNotice(viewModel, view, it.feed)
            } else {
                showAlreadyAddedNotice(viewModel, view)
            }
        } ?: showRequestFailedNotice(viewModel, view, errorMessageResId)
    }

    private fun showRequestFailedNotice(viewModel: AddFeedViewModel, view: View, messageResId: Int) {
        if (viewModel.requestFailedNoticeEnabled) {
            Snackbar.make(view, getString(messageResId), Snackbar.LENGTH_SHORT).show()
            viewModel.requestFailedNoticeEnabled = false
        }
    }

    private fun showFeedAddedNotice(viewModel: AddFeedViewModel, view: View, feed: Feed) {
        Snackbar.make(view, getString(R.string.feed_added, feed.title), Snackbar.LENGTH_LONG)
            .setAction(R.string.view_feed) {
                callbacks?.onNewFeedAdded(feed.website)
            }
            .show()
        viewModel.alreadyAddedNoticeEnabled = false
    }

    private fun showAlreadyAddedNotice(viewModel: AddFeedViewModel, view: View) {
        if (viewModel.alreadyAddedNoticeEnabled) {
            Snackbar.make(view, getString(R.string.feed_already_added), Snackbar.LENGTH_SHORT).show()
            viewModel.alreadyAddedNoticeEnabled = false
        }
    }

    private fun isAlreadyAdded(feed: Feed): Boolean {
        return currentFeeds.contains(feed.website)
    }
}