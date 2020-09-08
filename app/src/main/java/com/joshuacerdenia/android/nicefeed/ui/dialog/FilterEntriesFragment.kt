package com.joshuacerdenia.android.nicefeed.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.joshuacerdenia.android.nicefeed.R

class FilterEntriesFragment: BottomSheetDialogFragment() {

    private lateinit var filterRadioGroup: RadioGroup

    interface Callbacks {
        fun onFilterSelected(filter: Int)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_filter_entries, container, false)
        filterRadioGroup = view.findViewById(R.id.filter_radio_group)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val checkedItem = when (arguments?.getInt(ARG_CURRENT_FILTER)) {
            FILTER_UNREAD -> R.id.unread_radio_button
            FILTER_STARRED -> R.id.starred_radio_button
            else -> R.id.all_entries_radio_button // Default
        }

        filterRadioGroup.apply {
            check(checkedItem)
            setOnCheckedChangeListener { _, checkedId ->
                val filter = when (checkedId) {
                    R.id.unread_radio_button -> FILTER_UNREAD
                    R.id.starred_radio_button -> FILTER_STARRED
                    else -> FILTER_DEFAULT // Default
                }
                targetFragment?.let { fragment ->
                    (fragment as Callbacks).onFilterSelected(filter)
                }
                dismiss()
            }
        }
    }

    companion object {
        private const val ARG_CURRENT_FILTER = "ARG_CURRENT_FILTER"
        const val FILTER_DEFAULT = 0
        const val FILTER_UNREAD = 1
        const val FILTER_STARRED = 2

        fun newInstance(currentFilter: Int): FilterEntriesFragment {
            val args = Bundle().apply {
                putInt(ARG_CURRENT_FILTER, currentFilter)
            }
            return FilterEntriesFragment().apply {
                arguments = args
            }
        }
    }
}