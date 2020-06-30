package com.joshuacerdenia.android.nicefeed

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
import kotlinx.android.synthetic.main.fragment_add_feed.*
import java.util.*

private const val TAG = "AddFeedFragment"

private fun String?.pathified() = this?.substringAfter(
    "www.",
    this.substringAfter("://")
)

open class AddFeedFragment: Fragment(), ConfirmAddDialogFragment.Callbacks {

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

    private val currentFeedPaths: MutableList<String> = mutableListOf()
    var feedWithEntries: FeedWithEntries? = null
    lateinit var progressBar: ProgressBar

    private var callbacks: Callbacks? = null

    interface Callbacks {
        fun onNewFeedAdded(title: String)
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
            getCurrentFeedPaths(it)
        })

        addFeedViewModel.feedRequestLiveData?.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()?.let {
                feedWithEntries = it
                addFeedViewModel.requestFailedNoticeEnabled = false
                showConfirmDialog(this@AddFeedFragment, it.feed)
            } ?: showRequestFailedNotice(R.string.failed_to_get_feed)

            progressBar.visibility = View.GONE
            submitButton.apply {
                isEnabled = true
                text = getString(R.string.submit)
            }
        })

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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

            if (isAlreadyAdded(address)) {
                showAlreadyAddedNotice()
            } else {
                val url = "https://$address"
                addFeedViewModel.apply{
                    requestFeed(url)
                    requestFailedNoticeEnabled = true
                }

                Log.d(TAG, "Requesting $url...")
                progressBar.visibility = View.VISIBLE
                submitButton.apply {
                    isEnabled = false
                    text = getString(R.string.loading)
                }
            }

            activity?.let { view -> Utils.hideSoftKeyBoard(view, it) }
        }
    }

    fun getCurrentFeedPaths(feeds: List<Feed>) {
        currentFeedPaths.clear()
        for (feed in feeds) {
            val path = feed.url?.pathified()
            path?.let { currentFeedPaths.add(it) }
        }
        Log.d(TAG, "Current feeds: $currentFeedPaths")
    }

    fun showRequestFailedNotice(messageResId: Int) {
        if (addFeedViewModel.requestFailedNoticeEnabled) {
            Snackbar.make(linear_layout,
                getString(messageResId),
                Snackbar.LENGTH_SHORT
            ).show()
            addFeedViewModel.requestFailedNoticeEnabled = false
        }
    }

    fun showAlreadyAddedNotice() {
        Snackbar.make(
            linear_layout,
            getString(R.string.feed_already_added),
            Snackbar.LENGTH_SHORT)
            .show()
    }

    fun showConfirmDialog(fragment: Fragment, feed: Feed) {
        ConfirmAddDialogFragment.newInstance(feed).apply {
            show(fragment.requireFragmentManager(), "preview")
            setTargetFragment(fragment, 0)
        }
    }

    fun isAlreadyAdded(url: String?): Boolean {
        val path = url.pathified()
        return currentFeedPaths.contains(path)
    }

    override fun onAddConfirmed(title: String) {
        feedWithEntries?.let { addFeedViewModel.saveFeedWithEntries(it) }
        callbacks?.onNewFeedAdded(title)
    }
}