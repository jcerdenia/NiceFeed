package com.joshuacerdenia.android.nicefeed.ui.dialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.joshuacerdenia.android.nicefeed.R

private const val TAG = "SetCategoryDialog"

class SetCategoryDialogFragment: DialogFragment() {

    companion object {
        private const val ARG_NUM_OF_SELECTED = "ARG_NUM_OF_SELECTED"
        private const val ARG_CATEGORIES = "ARG_CATEGORIES"

        fun newInstance(numberOfSelected: Int, categories: List<String>): SetCategoryDialogFragment {
            val args = Bundle().apply {
                putInt(ARG_NUM_OF_SELECTED, numberOfSelected)
                putStringArray(ARG_CATEGORIES, categories.toTypedArray())
            }
            return SetCategoryDialogFragment()
                .apply {
                arguments = args
            }
        }
    }

    private lateinit var titleTextView: TextView
    private lateinit var categoryTextView: AutoCompleteTextView
    private lateinit var cancelButton: Button
    private lateinit var confirmButton: Button

    interface Callbacks {
        fun onSetCategoryConfirmed(category: String)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_set_category, container, false)

        titleTextView = view.findViewById(R.id.dialog_title)
        categoryTextView = view.findViewById(R.id.category_edit_text)
        cancelButton = view.findViewById(R.id.cancel_button)
        confirmButton = view.findViewById(R.id.confirm_button)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val numberOfSelected = arguments?.getInt(ARG_NUM_OF_SELECTED) ?: 1
        val categories = arguments?.getStringArray(ARG_CATEGORIES)?.toList() ?: emptyList()

        Log.d(TAG, "Found categories: $categories")

        val feedsBeingEdited = resources.getQuantityString(
            R.plurals.numberOfFeeds,
            numberOfSelected,
            numberOfSelected
        )

        val adapter = context?.let { ArrayAdapter(it, android.R.layout.simple_list_item_1, categories) }
        categoryTextView.setAdapter(adapter)
        categoryTextView.threshold = 1

        titleTextView.text = getString(R.string.editing_feeds, feedsBeingEdited)

        cancelButton.setOnClickListener {
            dismiss()
        }

        confirmButton.setOnClickListener {
            val category = categoryTextView.text.toString().trim()
            targetFragment?.let { (it as Callbacks).onSetCategoryConfirmed(category) }
            dismiss()
        }
    }
}