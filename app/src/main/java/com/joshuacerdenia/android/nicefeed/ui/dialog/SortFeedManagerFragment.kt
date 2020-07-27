package com.joshuacerdenia.android.nicefeed.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.joshuacerdenia.android.nicefeed.R
import com.joshuacerdenia.android.nicefeed.data.local.UserPreferences
import com.joshuacerdenia.android.nicefeed.ui.SORT_BY_ADDED
import com.joshuacerdenia.android.nicefeed.ui.SORT_BY_CATEGORY
import com.joshuacerdenia.android.nicefeed.ui.SORT_BY_TITLE
import com.joshuacerdenia.android.nicefeed.ui.SORT_BY_UPDATED

class SortFeedManagerFragment: BottomSheetDialogFragment() {

    companion object {
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

        /* val selectionMap = listOf(
            Pair(R.id.radioButton_added, SORT_BY_ADDED),
            Pair(R.id.radioButton_updated, SORT_BY_UPDATED),
            Pair(R.id.radioButton_category, SORT_BY_CATEGORY),
            Pair(R.id.radioButton_title, SORT_BY_TITLE)
        ) */

        val currentSelection = context?.let {
            UserPreferences.getFeedManagerSortPref(it)
        } ?: SORT_BY_ADDED

        radioGroupSortFeeds.apply {
            check(when (currentSelection) {
                SORT_BY_UPDATED -> R.id.radioButton_updated
                SORT_BY_CATEGORY -> R.id.radioButton_category
                SORT_BY_TITLE -> R.id.radioButton_title
                else -> R.id.radioButton_added // Default
            })

            setOnCheckedChangeListener { _, checkedId ->
                val selection: Int = when (checkedId) {
                    R.id.radioButton_updated -> SORT_BY_UPDATED
                    R.id.radioButton_category -> SORT_BY_CATEGORY
                    R.id.radioButton_title -> SORT_BY_TITLE
                    else -> SORT_BY_ADDED // Default
                }

                UserPreferences.saveFeedManagerSortPref(context, selection)
                targetFragment?.let { (it as Callbacks).onSortPreferenceSelected() }
                dismiss()
            }
        }

        cancelButton.setOnClickListener {
                dismiss()
        }
    }
}