package com.joshuacerdenia.android.nicefeed.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.joshuacerdenia.android.nicefeed.R
import com.joshuacerdenia.android.nicefeed.data.local.NiceFeedPreferences
import com.joshuacerdenia.android.nicefeed.data.local.NiceFeedPreferences.TEXT_SIZE_LARGE
import com.joshuacerdenia.android.nicefeed.data.local.NiceFeedPreferences.TEXT_SIZE_LARGER
import com.joshuacerdenia.android.nicefeed.data.local.NiceFeedPreferences.TEXT_SIZE_NORMAL

class TextSizeFragment: BottomSheetDialogFragment() {

    companion object {
        fun newInstance(): TextSizeFragment {
            return TextSizeFragment()
        }
    }

    interface Callbacks {
        fun onTextSizeSelected()
    }

    private lateinit var radioGroup: RadioGroup

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_text_size, container, false)
        radioGroup = view.findViewById(R.id.radio_group_text_size)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentSelection = context?.let {
            NiceFeedPreferences.getTextSize(it)
        } ?: TEXT_SIZE_NORMAL

        radioGroup.apply {
            when (currentSelection) {
                TEXT_SIZE_LARGE -> R.id.radio_button_large
                TEXT_SIZE_LARGER -> R.id.radio_button_larger
                else -> R.id.radio_button_normal
            }.let { selection ->
                check(selection)
            }

            setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.radio_button_large -> TEXT_SIZE_LARGE
                    R.id.radio_button_larger -> TEXT_SIZE_LARGER
                    else -> TEXT_SIZE_NORMAL
                }.let { selection ->
                    NiceFeedPreferences.saveTextSize(context, selection)
                }

                targetFragment?.let { fragment ->
                    (fragment as Callbacks).onTextSizeSelected()
                }
                dismiss()
            }
        }
    }
}