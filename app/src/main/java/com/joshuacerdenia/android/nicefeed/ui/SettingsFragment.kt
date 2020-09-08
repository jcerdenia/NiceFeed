package com.joshuacerdenia.android.nicefeed.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.appcompat.widget.Toolbar
import com.joshuacerdenia.android.nicefeed.R
import com.joshuacerdenia.android.nicefeed.data.local.NiceFeedPreferences

private const val TAG = "SettingsFragment"
private const val GITHUB_REPO = "https://www.github.com/joshuacerdenia/nicefeed"

class SettingsFragment: VisibleFragment() {

    private lateinit var toolbar: Toolbar
    private lateinit var autoUpdateSwitch: SwitchCompat
    private lateinit var notificationSwitch: SwitchCompat
    private lateinit var sortFeedsSpinner: Spinner
    private lateinit var sortEntriesSpinner: Spinner
    private lateinit var aboutTextView: TextView
    private var callbacks: Callbacks? = null

    interface Callbacks {
        fun onBackgroundWorkSettingChanged(isOn: Boolean)
        fun onDoneChangingSettings()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        toolbar = view.findViewById(R.id.toolbar)
        autoUpdateSwitch = view.findViewById(R.id.auto_update_switch)
        notificationSwitch = view.findViewById(R.id.notification_switch)
        sortFeedsSpinner = view.findViewById(R.id.sort_feeds_spinner)
        sortEntriesSpinner = view.findViewById(R.id.sort_entries_spinner)
        aboutTextView = view.findViewById(R.id.about_text_view)

        toolbar.title = getString(R.string.settings)
        (activity as AppCompatActivity?)?.setSupportActionBar(toolbar)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.apply {
            setNavigationIcon(R.drawable.ic_check)
            setNavigationOnClickListener {
                callbacks?.onDoneChangingSettings()
            }
        }

        val feedSortingOptions = arrayOf(
            getString(R.string.title),
            getString(R.string.unread_items)
        )
        val sortFeedsAdapter = context?.let { context ->
            ArrayAdapter(context, android.R.layout.simple_spinner_item, feedSortingOptions)
        }.also { adapter ->
            adapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        sortFeedsSpinner.apply {
            adapter = sortFeedsAdapter
            setSelection(0) // TODO shared preferences
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    // TODO save preference
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Do nothing
                }
            }
        }

        val entrySortingOptions = arrayOf(
            getString(R.string.date_published),
            getString(R.string.unread_on_top)
        )
        val sortEntriesAdapter = context?.let { context ->
            ArrayAdapter(context, android.R.layout.simple_spinner_item, entrySortingOptions)
        }.also { adapter ->
            adapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        sortEntriesSpinner.apply {
            adapter = sortEntriesAdapter
            setSelection(NiceFeedPreferences.getEntriesOrder(context))
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    NiceFeedPreferences.saveEntriesOrder(context, position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Do nothing
                }
            }
        }

        autoUpdateSwitch.apply {
            isChecked = NiceFeedPreferences.getAutoUpdateSetting(context)
            setOnCheckedChangeListener { _, isOn ->
                NiceFeedPreferences.saveAutoUpdateSetting(context, isOn)
            }
        }

        notificationSwitch.apply {
            isChecked = NiceFeedPreferences.getPollingSetting(context)
            setOnCheckedChangeListener { _, isOn ->
                NiceFeedPreferences.savePollingSetting(context, isOn)
                callbacks?.onBackgroundWorkSettingChanged(isOn)
            }
        }

        aboutTextView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(GITHUB_REPO))
            startActivity(intent)
        }
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    companion object {
        fun newInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }
}