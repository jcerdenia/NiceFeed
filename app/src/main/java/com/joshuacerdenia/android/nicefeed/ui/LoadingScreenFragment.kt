package com.joshuacerdenia.android.nicefeed.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.joshuacerdenia.android.nicefeed.R

class LoadingScreenFragment: Fragment() {

    companion object {
        fun newInstance(): LoadingScreenFragment {
            return LoadingScreenFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_loading_screen, container, false)
    }
}