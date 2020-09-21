package com.joshuacerdenia.android.nicefeed.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.joshuacerdenia.android.nicefeed.R
import com.joshuacerdenia.android.nicefeed.ui.OnHomePressed

class WelcomeFragment : VisibleFragment() {

    private lateinit var toolbar: Toolbar
    private lateinit var messageTextView: TextView
    private var callback: OnHomePressed? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = activity as OnHomePressed
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_welcome, container, false)
        messageTextView = view.findViewById(R.id.no_feeds_text_view)
        toolbar = view.findViewById(R.id.toolbar)
        toolbar.title = getString(R.string.app_name)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.apply {
            setNavigationIcon(R.drawable.ic_menu)
            setNavigationOnClickListener { callback?.onHomePressed() }
        }

        messageTextView.setOnClickListener { callback?.onHomePressed() }
    }

    override fun onDetach() {
        super.onDetach()
        callback = null
    }

    companion object {
        fun newInstance(): WelcomeFragment {
            return WelcomeFragment()
        }
    }
}