package com.joshuacerdenia.android.nicefeed

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.text.HtmlCompat
import androidx.fragment.app.DialogFragment

class ConfirmAddDialogFragment: DialogFragment() {

    companion object {
        private const val ARG_FEED = "ARG_FEED"

        fun newInstance(feed: Feed): ConfirmAddDialogFragment {
            val args = Bundle().apply {
                putSerializable(ARG_FEED, feed)
                }
            return ConfirmAddDialogFragment().apply {
                arguments = args
            }
        }
    }

    interface Callbacks {
        fun onAddConfirmed(title: String)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)

        val feed = arguments?.getSerializable(ARG_FEED) as Feed

        val dialogBuilder = AlertDialog.Builder(context!!)

        val message = "${feed.description?.trim()}<br><br>" +
                "Last updated ${feed.updated}"

        dialogBuilder
            .setTitle(feed.title)
            .setMessage(HtmlCompat.fromHtml(message, 0))
            .setIcon(R.drawable.ic_success)
            .setCancelable(true)
            .setPositiveButton(getString(R.string.add_feed)) { dialog, _ ->
                targetFragment?.let { fragment ->
                    feed.title?.let { (fragment as Callbacks).onAddConfirmed(it) }
                }
                dialog.dismiss()
            }
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ -> dialog.cancel() }

        val dialog = dialogBuilder.create()
        dialog.show()

        return dialog
    }
}