package com.joshuacerdenia.android.nicefeed.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.joshuacerdenia.android.nicefeed.R
import com.joshuacerdenia.android.nicefeed.data.local.NiceFeedPreferences

class SortFeedManagerFragment: BottomSheetDialogFragment() {

    companion object {
        const val SORT_BY_ADDED = 0
        const val SORT_BY_CATEGORY = 1
        const val SORT_BY_TITLE = 2
        
        fun newInstance(): SortFeedManagerFragment {
            return SortFeedManagerFragment()
        }
    }

    interface Callbacks {
        fun onSortPreferenceSelected()
    }

    private lateinit var radioGroupSortFeeds: RadioGroup
    private lateinit var cancelButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sort_feeds, container, false)
        radioGroupSortFeeds = view.findViewById(R.id.radioGroup_sort)
        cancelButton = view.findViewById(R.id.cancel_button)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentSelection = context?.let {
            NiceFeedPreferences.getFeedManagerOrder(it)
        } ?: SORT_BY_ADDED

        radioGroupSortFeeds.apply {
            check(when (currentSelection) {
                SORT_BY_CATEGORY -> R.id.radioButton_category
                SORT_BY_TITLE -> R.id.radioButton_title
                else -> R.id.radioButton_added // Default
            })

            setOnCheckedChangeListener { _, checkedId ->
                val selection: Int = when (checkedId) {
                    R.id.radioButton_category -> SORT_BY_CATEGORY
                    R.id.radioButton_title -> SORT_BY_TITLE
                    else -> SORT_BY_ADDED // Default
                }

                NiceFeedPreferences.saveFeedManagerOrder(context, selection)
                targetFragment?.let { (it as Callbacks).onSortPreferenceSelected() }
                dismiss()
            }
        }

        cancelButton.setOnClickListener {
                dismiss()
        }
    }
}