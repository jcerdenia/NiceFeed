package com.joshuacerdenia.android.nicefeed.ui.menu

import android.content.Context
import android.util.Log
import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import com.joshuacerdenia.android.nicefeed.R
import com.joshuacerdenia.android.nicefeed.data.model.EntryLight
import com.joshuacerdenia.android.nicefeed.utils.addRipple

class EntryPopupMenu(
    context: Context,
    view: View,
    private val listener: OnPopupMenuItemClicked,
    private val entry: EntryLight
) : PopupMenu(context, view) {

    interface OnPopupMenuItemClicked {
        fun onPopupMenuItemClicked(entry: EntryLight, action: Int)
    }

    init {
        view.setBackgroundColor(ContextCompat.getColor(context, R.color.colorSelect))
        menuInflater.inflate(R.menu.popup_menu_entry, menu)
        val starItem = menu.findItem(R.id.menuItem_star)
        val markAsReadItem = menu.findItem(R.id.menuItem_mark_as_read)

        // Default values for the following are set in XML
        if (entry.isStarred) {
            starItem.title = context.getString(R.string.unstar)
            starItem.setIcon(R.drawable.ic_star)
        }

        if (entry.isRead) {
            markAsReadItem.title = context.getString(R.string.mark_as_unread)
            markAsReadItem.setIcon(R.drawable.ic_check_circle_outline)
        }

        // Force show icons
        try {
            val fieldMPopup = PopupMenu::class.java.getDeclaredField("mPopup")
            fieldMPopup.isAccessible = true
            val mPopup = fieldMPopup.get(this)
            mPopup.javaClass
                .getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                .invoke(mPopup, true)
        } catch (e: Exception){
            e.printStackTrace()
        }

        setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menuItem_star -> listener.onPopupMenuItemClicked(entry, ACTION_STAR)
                R.id.menuItem_mark_as_read -> listener.onPopupMenuItemClicked(entry, ACTION_MARK_AS)
                R.id.menuItem_read -> listener.onPopupMenuItemClicked(entry, ACTION_OPEN)
            }
            true
        }

        setOnDismissListener {
            view.addRipple()
        }
    }

    companion object {
        const val ACTION_MARK_AS = 0
        const val ACTION_STAR = 1
        const val ACTION_OPEN = 2
    }
}