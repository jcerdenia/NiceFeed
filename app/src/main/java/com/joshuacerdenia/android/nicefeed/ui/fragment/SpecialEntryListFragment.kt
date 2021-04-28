package com.joshuacerdenia.android.nicefeed.ui.fragment

import android.os.Bundle
import android.view.View

class SpecialEntryListFragment : EntryListFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //feedId = arguments?.getString(ARG_FOLDER_ID)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {

        private const val TAG = "SpecialEntryListFragment"
        private const val ARG_FOLDER_ID = "ARG_FOLDER_ID"

        const val FOLDER_NEW = "FOLDER_NEW"
        const val FOLDER_STARRED = "FOLDER_STARRED"

        fun newInstance(
            folderId: String
        ): EntryListFragment {
            return EntryListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_FOLDER_ID, folderId)
                }
            }
        }
    }
}