package com.joshuacerdenia.android.nicefeed.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.joshuacerdenia.android.nicefeed.R
import com.joshuacerdenia.android.nicefeed.data.local.NiceFeedPreferences.TEXT_SIZE_LARGE
import com.joshuacerdenia.android.nicefeed.data.local.NiceFeedPreferences.TEXT_SIZE_LARGER
import com.joshuacerdenia.android.nicefeed.data.local.NiceFeedPreferences.TEXT_SIZE_NORMAL

class TextSizeFragment: BottomSheetDialogFragment() {

    interface Callbacks {
        fun onTextSizeSelected(textSize: Int)
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
        val currentSelection = arguments?.getInt(ARG_TEXT_SIZE) ?: 0

        radioGroup.apply {
            when (currentSelection) {
                TEXT_SIZE_LARGE -> R.id.radio_button_large
                TEXT_SIZE_LARGER -> R.id.radio_button_larger
                else -> R.id.radio_button_normal
            }.let { selection ->
                check(selection)
            }

            setOnCheckedChangeListener { _, checkedId ->
                val textSize = when (checkedId) {
                    R.id.radio_button_large -> TEXT_SIZE_LARGE
                    R.id.radio_button_larger -> TEXT_SIZE_LARGER
                    else -> TEXT_SIZE_NORMAL
                }
                targetFragment?.let { fragment ->
                    (fragment as Callbacks).onTextSizeSelected(textSize)
                }
                dismiss()
            }
        }
    }

    companion object {
        private const val ARG_TEXT_SIZE = "ARG_TEXT_SIZE"

        fun newInstance(currentTextSize: Int): TextSizeFragment {
            return TextSizeFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_TEXT_SIZE, currentTextSize)
                }
            }
        }
    }
}