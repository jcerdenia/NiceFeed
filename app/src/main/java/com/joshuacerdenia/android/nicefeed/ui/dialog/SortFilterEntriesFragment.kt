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

class SortFilterEntriesFragment: BottomSheetDialogFragment() {

    companion object {
        const val FILTER_ALL = 0
        const val FILTER_UNREAD = 1
        const val FILTER_STARRED = 2
        const val SORT_BY_PUBLISHED = 0
        const val SORT_UNREAD_ON_TOP = 1

        fun newInstance(): SortFilterEntriesFragment {
            return SortFilterEntriesFragment()
        }
    }

    interface Callbacks {
        fun onSortFilterConfirmed()
    }

    private lateinit var sorterRadioGroup: RadioGroup
    private lateinit var filterRadioGroup: RadioGroup
    private lateinit var doneButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sort_filter_entries, container, false)
        sorterRadioGroup = view.findViewById(R.id.radioGroup_sorter)
        filterRadioGroup = view.findViewById(R.id.radioGroup_filter)
        doneButton = view.findViewById(R.id.button_confirm)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentSorter = context?.let { UserPreferences.getEntriesSortPref(it) } ?: SORT_BY_PUBLISHED
        val currentFilter = context?.let { UserPreferences.getEntriesFilterPref(it) } ?: FILTER_ALL
        var sorter = currentSorter
        var filter = currentFilter

        sorterRadioGroup.apply {
            check(if (currentSorter == SORT_UNREAD_ON_TOP) {
                R.id.radioButton_sortBy_unread
            } else {
                R.id.radioButton_sortBy_date
            })

            setOnCheckedChangeListener { _, checkedId ->
                sorter = if (checkedId == R.id.radioButton_sortBy_unread) {
                    SORT_UNREAD_ON_TOP
                } else {
                    SORT_BY_PUBLISHED
                }
            }
        }

        filterRadioGroup.apply {
            check(when (currentFilter) {
                FILTER_UNREAD -> R.id.radioButton_filter_unread
                FILTER_STARRED -> R.id.radioButton_filter_starred
                else -> R.id.radioButton_filter_all // Default
            })

            setOnCheckedChangeListener { _, checkedId ->
                filter = when (checkedId) {
                    R.id.radioButton_filter_unread -> FILTER_UNREAD
                    R.id.radioButton_filter_starred -> FILTER_STARRED
                    else -> FILTER_ALL // Default
                }
            }
        }

        doneButton.setOnClickListener {
            context?.let { context ->
                UserPreferences.saveEntriesSortPref(context, sorter)
                UserPreferences.saveEntriesFilterPref(context, filter)
            }

            targetFragment?.let { (it as Callbacks).onSortFilterConfirmed() }
            dismiss()
        }
    }
}