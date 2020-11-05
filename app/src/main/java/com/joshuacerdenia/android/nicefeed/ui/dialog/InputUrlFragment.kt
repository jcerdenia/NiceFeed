package com.joshuacerdenia.android.nicefeed.ui.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.joshuacerdenia.android.nicefeed.R
import com.joshuacerdenia.android.nicefeed.ui.FeedRequestCallbacks
import com.joshuacerdenia.android.nicefeed.ui.viewmodel.AddFeedsViewModel
import com.joshuacerdenia.android.nicefeed.utils.extensions.isVisible
import com.joshuacerdenia.android.nicefeed.utils.extensions.show
import com.joshuacerdenia.android.nicefeed.utils.extensions.toEditable
import java.util.*

class InputUrlFragment(
    private val viewModel: AddFeedsViewModel
): BottomSheetDialogFragment() {

    private lateinit var urlEditText: EditText
    private lateinit var subscribeButton: Button
    private lateinit var progressBar: ProgressBar

    private var isRequested = false
    private var callbacks: FeedRequestCallbacks? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogNoFloating)
        callbacks = targetFragment as? FeedRequestCallbacks
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_input_url, container, false)
        urlEditText = view.findViewById(R.id.url_edit_text)
        subscribeButton = view.findViewById(R.id.subscribe_button)
        progressBar = view.findViewById(R.id.progress_bar)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.feedRequestLiveData.observe(viewLifecycleOwner, { result ->
            if (isRequested) {
                Handler().postDelayed({ callbacks?.onRequestCompleted(result) }, 250)
                dismiss()
            }
        })

        urlEditText.apply {
            text = viewModel.lastAttemptedUrl.toEditable()
            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) submitFeedUrl(urlEditText.text.toString())
                true
            }
        }

        subscribeButton.setOnClickListener {
            submitFeedUrl(urlEditText.text.toString())
            it.isEnabled = false
        }
    }

    private fun submitFeedUrl(link: String) {
        isRequested = true
        progressBar.show()
        val url = link.toLowerCase(Locale.ROOT).trim()
        if (url.contains("://")) {
            viewModel.requestFeed(url) // If scheme is provided, use as is
        } else viewModel.requestFeed("https://$url", "http://$url")
        viewModel.lastAttemptedUrl = url
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        if (progressBar.isVisible()) callbacks?.onRequestCanceled()
        dismiss()
    }

    companion object {

        fun newInstance(viewModel: AddFeedsViewModel): InputUrlFragment {
            return InputUrlFragment(viewModel)
        }
    }
}