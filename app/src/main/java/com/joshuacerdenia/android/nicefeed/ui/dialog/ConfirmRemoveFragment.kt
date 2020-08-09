package com.joshuacerdenia.android.nicefeed.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.joshuacerdenia.android.nicefeed.R

private const val ARG_TITLE = "ARG_TITLE"
private const val ARG_COUNT = "ARG_COUNT"

class ConfirmRemoveFragment : BottomSheetDialogFragment() {

    companion object {
        fun newInstance(title: String?, count: Int = 1): ConfirmRemoveFragment {
            val args = Bundle().apply {
                putString(ARG_TITLE, title)
                putInt(ARG_COUNT, count)
            }
            return ConfirmRemoveFragment().apply {
                arguments = args
            }
        }
    }

    private lateinit var dialogTitle: TextView
    private lateinit var cancelButton: Button
    private lateinit var confirmButton: Button

    interface Callbacks {
        fun onRemoveConfirmed()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_confirm_remove, container, false)
        dialogTitle = view.findViewById(R.id.dialog_title)
        cancelButton = view.findViewById(R.id.cancel_button)
        confirmButton = view.findViewById(R.id.confirm_button)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val count = arguments?.getInt(ARG_COUNT) ?: 1
        val title = arguments?.getString(ARG_TITLE)

        val whatToRemove = title ?: if (count == 1) {
            getString(R.string.feed)
        } else {
            resources.getQuantityString(R.plurals.numberOfFeeds, count, count)
        }

        dialogTitle.text = getString(R.string.confirm_remove, whatToRemove)

        cancelButton.setOnClickListener {
            dismiss()
        }

        confirmButton.setOnClickListener {
            targetFragment?.let { fragment ->
                (fragment as Callbacks).onRemoveConfirmed()
            }
            dismiss()
        }
    }
}