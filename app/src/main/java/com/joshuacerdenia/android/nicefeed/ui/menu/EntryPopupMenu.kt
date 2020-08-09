package com.joshuacerdenia.android.nicefeed.ui.menu

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.PopupMenu
import androidx.core.content.ContextCompat
import com.joshuacerdenia.android.nicefeed.R
import com.joshuacerdenia.android.nicefeed.data.model.Entry

class EntryPopupMenu(
    context: Context?,
    view: View?,
    private val listener: OnItemClickListener,
    private val entry: Entry
) : PopupMenu(context, view) {

    companion object {
        const val ACTION_MARK_AS = 0
        const val ACTION_STAR = 1
        const val ACTION_OPEN = 2
    }

    interface OnItemClickListener {
        fun onPopupMenuItemClicked(entry: Entry, action: Int)
    }

    init {
        view?.setBackgroundColor(ContextCompat.getColor(context!!, R.color.colorSelect))
        menuInflater.inflate(R.menu.popup_menu_entry, menu)

        if (entry.isStarred) {
            menu.findItem(R.id.menuItem_star).title = context?.getString(R.string.unstar)
        }

        if (entry.isRead) {
            menu.findItem(R.id.menuItem_mark_as_read).title = context?.getString(R.string.mark_as_unread)
        }

        setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menuItem_star -> listener.onPopupMenuItemClicked(entry, ACTION_STAR)
                R.id.menuItem_mark_as_read -> listener.onPopupMenuItemClicked(entry, ACTION_MARK_AS)
                R.id.menuItem_read -> listener.onPopupMenuItemClicked(entry, ACTION_OPEN)
            }

            true
        }

        setOnDismissListener {
            view?.setBackgroundColor(Color.TRANSPARENT)
        }
    }
}