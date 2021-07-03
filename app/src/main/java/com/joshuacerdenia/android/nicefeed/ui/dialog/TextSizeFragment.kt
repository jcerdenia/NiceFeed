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

    private lateinit var radioGroup: RadioGroup

    interface Callbacks {

        fun onTextSizeSelected(textSize: Int)
    }

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
        val currentSelection = arguments?.getInt(TEXT_SIZE) ?: 0

        val map = mapOf(
            TEXT_SIZE_NORMAL to R.id.radio_button_normal,
            TEXT_SIZE_LARGE to R.id.radio_button_large,
            TEXT_SIZE_LARGER to R.id.radio_button_larger
        )

        radioGroup.apply {
            check(map[currentSelection] ?: R.id.radio_button_normal)
            setOnCheckedChangeListener { _, checkedId ->
                val textSize = map.getKey(checkedId) ?: TEXT_SIZE_NORMAL
                parentFragmentManager.setFragmentResult(TEXT_SIZE, Bundle().apply {
                    putInt(TEXT_SIZE, textSize)
                })

                dismiss()
            }
        }
    }

    private fun <K, V> Map<K, V>.getKey(value: V): K? {
        return this.toList().find { it.first == value }?.first
    }

    companion object {

        const val TAG = "TextSizeFragment"
        const val TEXT_SIZE = "TEXT_SIZE"

        fun newInstance(currentTextSize: Int): TextSizeFragment {
            return TextSizeFragment().apply {
                arguments = Bundle().apply {
                    putInt(TEXT_SIZE, currentTextSize)
                }
            }
        }
    }
}