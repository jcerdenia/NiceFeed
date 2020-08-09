package com.joshuacerdenia.android.nicefeed.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.joshuacerdenia.android.nicefeed.R
import com.joshuacerdenia.android.nicefeed.data.local.UserPreferences
import com.joshuacerdenia.android.nicefeed.ui.dialog.SortFilterEntriesFragment
import kotlinx.android.synthetic.main.fragment_settings.*

private const val TAG = "SettingsFragment"

class SettingsFragment: Fragment(), SortFilterEntriesFragment.Callbacks {

    companion object {
        fun newInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }

    private val fragment = this@SettingsFragment
    private lateinit var sortFilterButton: TextView
    private lateinit var autoUpdateSwitch: Switch

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        autoUpdateSwitch = view.findViewById(R.id.switch_auto_update)
        sortFilterButton = view.findViewById(R.id.textView_sort_filter)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        autoUpdateSwitch.apply {
            isChecked = UserPreferences.getAutoUpdatePref(context)

            setOnCheckedChangeListener { _, isOn ->
                UserPreferences.saveAutoUpdatePref(context, isOn)
            }
        }

        sortFilterButton.setOnClickListener {
            SortFilterEntriesFragment.newInstance().apply {
                setTargetFragment(fragment, 0)
                show(fragment.requireFragmentManager(), "filter")
            }
        }
    }

    override fun onSortFilterConfirmed() {
        Snackbar.make(
            linearLayout,
            getString(R.string.changes_saved),
            Snackbar.LENGTH_SHORT
        ).show()
    }
}