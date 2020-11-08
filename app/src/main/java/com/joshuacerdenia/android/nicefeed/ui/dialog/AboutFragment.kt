package com.joshuacerdenia.android.nicefeed.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.joshuacerdenia.android.nicefeed.R
import com.joshuacerdenia.android.nicefeed.util.extensions.addRipple

class AboutFragment: BottomSheetDialogFragment() {

    interface Callback {
        fun onGoToRepoClicked()
    }

    private lateinit var titleTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var addInfoTextView: TextView
    private lateinit var imageView: ImageView
    private lateinit var goButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_about_feed, container, false)
        titleTextView = view.findViewById(R.id.textView_title)
        descriptionTextView = view.findViewById(R.id.textView_description)
        addInfoTextView = view.findViewById(R.id.textView_additional_info)
        imageView = view.findViewById(R.id.imageView_feed)
        goButton = view.findViewById(R.id.button_positive)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titleTextView.text = getString(R.string.about_nicefeed)
        descriptionTextView.text = getString(R.string.about_nicefeed_detail)
        descriptionTextView.addRipple()
        imageView.setImageDrawable(ResourcesCompat.getDrawable(
            resources,
            R.mipmap.ic_launcher_round,
            null
        ))
        addInfoTextView.visibility = View.GONE
        goButton.apply {
            text = getString(R.string.go)
            setOnClickListener {
                targetFragment?.let { fragment ->
                    (fragment as Callback).onGoToRepoClicked()
                }
                dismiss()
            }
        }
    }

    companion object {
        fun newInstance(): AboutFragment {
            return AboutFragment()
        }
    }
}