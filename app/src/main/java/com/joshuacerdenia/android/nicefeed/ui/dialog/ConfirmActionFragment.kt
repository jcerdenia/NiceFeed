package com.joshuacerdenia.android.nicefeed.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.joshuacerdenia.android.nicefeed.R
import com.joshuacerdenia.android.nicefeed.util.extensions.hide

class ConfirmActionFragment : BottomSheetDialogFragment() {

    interface OnRemoveConfirmed {
        fun onRemoveConfirmed()
    }

    interface OnExportConfirmed {
        fun onExportConfirmed()
    }

    interface OnImportConfirmed {
        fun onImportConfirmed()
    }

    private lateinit var titleTextView: TextView
    private lateinit var messageTextView: TextView
    private lateinit var confirmButton: Button

    private var titleStringRes: Int = 0
    private var drawableRes: Int = 0

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
        val action = arguments?.getInt(ARG_ACTION)
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
                messageTextView.hide()
            }
            IMPORT -> {
                drawableRes = R.drawable.ic_import
                titleStringRes = R.string.confirm_import_title
                messageTextView.text = getString(R.string.confirm_import_message)
            }
            else -> throw throw IllegalArgumentException("Action not found!")
        }

        titleTextView.apply {
            setCompoundDrawablesWithIntrinsicBounds(drawableRes, 0, 0, 0)
            text = getString(titleStringRes, itemString)
        }

        confirmButton.setOnClickListener {
            when (action) {
                REMOVE -> (targetFragment as? OnRemoveConfirmed)?.onRemoveConfirmed()
                EXPORT -> (targetFragment as? OnExportConfirmed)?.onExportConfirmed()
                IMPORT -> (targetFragment as? OnImportConfirmed)?.onImportConfirmed()
            }
            dismiss()
        }
    }

    companion object {
        private const val ARG_TITLE = "ARG_TITLE"
        private const val ARG_COUNT = "ARG_COUNT"
        private const val ARG_ACTION = "ARG_ACTION"

        const val REMOVE = 0
        const val EXPORT = 1
        const val IMPORT = 2

        fun newInstance(action: Int, title: String?, count: Int = 1): ConfirmActionFragment {
            val args = Bundle().apply {
                putInt(ARG_ACTION, action)
                putString(ARG_TITLE, title)
                putInt(ARG_COUNT, count)
            }
            return ConfirmActionFragment().apply { arguments = args }
        }

        fun newInstance(action: Int, count: Int): ConfirmActionFragment {
            val args = Bundle().apply {
                putInt(ARG_ACTION, action)
                putInt(ARG_COUNT, count)
            }
            return ConfirmActionFragment().apply { arguments = args }
        }
    }
}