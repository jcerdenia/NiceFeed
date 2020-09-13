package com.joshuacerdenia.android.nicefeed.ui.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.SwitchCompat
import androidx.appcompat.widget.Toolbar
import com.joshuacerdenia.android.nicefeed.R
import com.joshuacerdenia.android.nicefeed.data.local.NiceFeedPreferences
import com.joshuacerdenia.android.nicefeed.ui.ToolbarCallbacks
import com.joshuacerdenia.android.nicefeed.utils.Utils

class SettingsFragment: VisibleFragment() {

    interface Callbacks: ToolbarCallbacks {
        fun onBackgroundWorkSettingChanged(isOn: Boolean)
    }

    private lateinit var toolbar: Toolbar
    private lateinit var scrollView: ScrollView
    private lateinit var autoUpdateSwitch: SwitchCompat
    private lateinit var notificationSwitch: SwitchCompat
    private lateinit var themeSpinner: Spinner
    private lateinit var sortFeedsSpinner: Spinner
    private lateinit var sortEntriesSpinner: Spinner
    private lateinit var aboutTextView: TextView
    private val spinnerLayout = android.R.layout.simple_spinner_item
    private val spinnerItem = android.R.layout.simple_spinner_dropdown_item
    private var callbacks: Callbacks? = null

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
        scrollView = view.findViewById(R.id.scroll_view)
        autoUpdateSwitch = view.findViewById(R.id.auto_update_switch)
        notificationSwitch = view.findViewById(R.id.notification_switch)
        themeSpinner = view.findViewById(R.id.theme_spinner)
        sortFeedsSpinner = view.findViewById(R.id.sort_feeds_spinner)
        sortEntriesSpinner = view.findViewById(R.id.sort_entries_spinner)
        aboutTextView = view.findViewById(R.id.about_text_view)

        toolbar.title = getString(R.string.settings)
        callbacks?.onToolbarInflated(toolbar)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        themeSpinner.apply {
            adapter = arrayOf(
                getString(R.string.system_default),
                getString(R.string.light),
                getString(R.string.dark)
            ).run { getDefaultAdapter(context, this)}
            setSelection(NiceFeedPreferences.getTheme(context))
            onItemSelectedListener = getSpinnerListener(context, ACTION_SAVE_THEME)
        }

        sortFeedsSpinner.apply {
            adapter = arrayOf(
                getString(R.string.title),
                getString(R.string.unread_items)
            ).run { getDefaultAdapter(context, this) }
            setSelection(NiceFeedPreferences.getFeedsOrder(context))
            onItemSelectedListener = getSpinnerListener(context, ACTION_SAVE_FEEDS_ORDER)
        }

        sortEntriesSpinner.apply {
            adapter = arrayOf(
                getString(R.string.date_published),
                getString(R.string.unread_on_top)
            ).run { getDefaultAdapter(context, this)}
            setSelection(NiceFeedPreferences.getEntriesOrder(context))
            onItemSelectedListener = getSpinnerListener(context, ACTION_SAVE_ENTRIES_ORDER)
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
            Utils.openLink(requireActivity(), scrollView, Uri.parse(GITHUB_REPO))
        }
    }

    private fun getDefaultAdapter(context: Context, items: Array<String>): ArrayAdapter<String> {
        return ArrayAdapter(context, spinnerLayout, items).apply {
            setDropDownViewResource(spinnerItem)
        }
    }

    private fun getSpinnerListener(context: Context, action: Int): AdapterView.OnItemSelectedListener {
        return object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (action) {
                    ACTION_SAVE_THEME -> {
                        NiceFeedPreferences.saveTheme(context, position)
                        Utils.setTheme(position)
                    }
                    ACTION_SAVE_FEEDS_ORDER -> NiceFeedPreferences.saveFeedsOrder(context, position)
                    ACTION_SAVE_ENTRIES_ORDER -> NiceFeedPreferences.saveEntriesOrder(context, position)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    companion object {
        private const val GITHUB_REPO = "https://www.github.com/joshuacerdenia/nicefeed"
        private const val ACTION_SAVE_THEME = 0
        private const val ACTION_SAVE_FEEDS_ORDER = 1
        private const val ACTION_SAVE_ENTRIES_ORDER = 2

        fun newInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }
}