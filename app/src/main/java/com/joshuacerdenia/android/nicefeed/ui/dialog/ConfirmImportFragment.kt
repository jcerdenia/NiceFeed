package com.joshuacerdenia.android.nicefeed.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.joshuacerdenia.android.nicefeed.R

class ConfirmImportFragment: BottomSheetDialogFragment() {

    interface Callbacks {
        fun onImportConfirmed(count: Int)
    }

    private lateinit var titleTextView: TextView
    private lateinit var confirmButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_confirm_import, container, false)
        titleTextView = view.findViewById(R.id.textView_title)
        confirmButton = view.findViewById(R.id.button_confirm)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val count = arguments?.getInt(ARG_COUNT) ?: 1
        val whatToRemove = resources.getQuantityString(R.plurals.numberOfFeeds, count, count)

        titleTextView.text = getString(R.string.confirm_import_title, whatToRemove)

        confirmButton.setOnClickListener {
            targetFragment?.let { fragment ->
                (fragment as Callbacks).onImportConfirmed(count)
            }
            dismiss()
        }
    }

    companion object {
        private const val ARG_COUNT = "ARG_COUNT"

        fun newInstance(count: Int): ConfirmImportFragment {
            val args = Bundle().apply {
                putInt(ARG_COUNT, count)
            }
            return ConfirmImportFragment().apply {
                arguments = args
            }
        }
    }
}