package com.joshuacerdenia.android.nicefeed.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.joshuacerdenia.android.nicefeed.R
import com.joshuacerdenia.android.nicefeed.utils.Utils
import java.util.*

private const val TAG = "AddFeedFragment"
private const val HTTPS = "https://"

class AddFeedsFragment: FeedAddingFragment() {

    companion object {
        fun newInstance(): AddFeedsFragment {
            return AddFeedsFragment()
        }
    }

    private val viewModel: AddFeedsViewModel by lazy {
        ViewModelProvider(this).get(AddFeedsViewModel::class.java)
    }

    private lateinit var linearLayout: LinearLayout
    private lateinit var urlEditText: EditText
    private lateinit var subscribeButton: Button
    private lateinit var searchView: SearchView
    private lateinit var progressBar: ProgressBar
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_feeds, container, false)
        linearLayout = view.findViewById(R.id.linearLayout)
        urlEditText = view.findViewById(R.id.editText_url)
        subscribeButton = view.findViewById(R.id.button_subscribe)
        searchView = view.findViewById(R.id.searchView)
        progressBar = view.findViewById(R.id.progressBar_add_feed)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val manager = RequestResultManager(viewModel, linearLayout, R.string.failed_to_get_feed)

        viewModel.feedIdsLiveData.observe(viewLifecycleOwner, Observer {
            currentFeedIds = it
        })

        viewModel.feedRequestLiveData?.observe(viewLifecycleOwner, Observer {
            manager.submitData(it)
            progressBar.visibility = View.INVISIBLE
            subscribeButton.apply {
                isEnabled = true
                text = getString(R.string.subscribe)
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

        subscribeButton.setOnClickListener {
            val address = urlEditText.text.toString()
                .substringAfter("://")
                .toLowerCase(Locale.ROOT)
                .trim()

            viewModel.requestFeed(HTTPS + address)
            progressBar.visibility = View.VISIBLE

            subscribeButton.apply {
                isEnabled = false
                text = getString(R.string.loading)
            }

            activity?.let { view -> Utils.hideSoftKeyBoard(view, it) }
        }
    }
}