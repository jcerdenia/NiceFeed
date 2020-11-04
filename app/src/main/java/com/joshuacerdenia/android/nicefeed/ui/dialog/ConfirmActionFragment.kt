package com.joshuacerdenia.android.nicefeed.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.joshuacerdenia.android.nicefeed.R

class ConfirmActionFragment(private val action: Int) : BottomSheetDialogFragment() {

    interface Callbacks {
        fun onActionConfirmed(action: Int)
    }

    private lateinit var titleTextView: TextView
    private lateinit var messageTextView: TextView
    private lateinit var confirmButton: Button

    private var callbacks: Callbacks? = null
    private var titleStringRes: Int = 0
    private var drawableRes: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        callbacks = targetFragment as? Callbacks
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_confirm_action, container, false)
        titleTextView = view.findViewById(R.id.title_text_view)
        messageTextView = view.findViewById(R.id.message_text_view)
        confirmButton = view.findViewById(R.id.confirm_button)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val count = arguments?.getInt(ARG_COUNT) ?: 1
        val title = arguments?.getString(ARG_TITLE)
        val itemString = title ?: if (count == 1) {
            getString(R.string.feed)
        } else {
            resources.getQuantityString(R.plurals.numberOfFeeds, count, count)
        }

        when (action) {
            REMOVE -> {
                drawableRes = R.drawable.ic_delete
                titleStringRes = R.string.confirm_remove
                messageTextView.text = getString(R.string.confirm_remove_dialog_message)
            }
            EXPORT -> {
                drawableRes = R.drawable.ic_export
                titleStringRes = R.string.confirm_export
                messageTextView.visibility = View.GONE
            }
            else -> throw IllegalArgumentException()
        }

        titleTextView.apply {
            setCompoundDrawablesWithIntrinsicBounds(drawableRes, 0, 0, 0)
            text = getString(titleStringRes, itemString)
        }

        confirmButton.setOnClickListener {
            callbacks?.onActionConfirmed(action)
            dismiss()
        }
    }

    companion object {
        private const val ARG_TITLE = "ARG_TITLE"
        private const val ARG_COUNT = "ARG_COUNT"

        const val REMOVE = 0
        const val EXPORT = 1

        fun newInstance(action: Int, title: String?, count: Int = 1): ConfirmActionFragment {
            val args = Bundle().apply {
                putString(ARG_TITLE, title)
                putInt(ARG_COUNT, count)
            }
            return ConfirmActionFragment(action).apply {
                arguments = args
            }
        }
    }
}