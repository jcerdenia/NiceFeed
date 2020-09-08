package com.joshuacerdenia.android.nicefeed

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.joshuacerdenia.android.nicefeed.data.model.FeedIdPair
import com.joshuacerdenia.android.nicefeed.ui.*

private const val TAG = "ManagingActivity"
private const val EXTRA_MANAGING = "com.joshuacerdenia.android.nicefeed.managing"
private const val REQUEST_CODE_READ_OPML = 0
private const val REQUEST_CODE_WRITE_OPML = 1

class ManagingActivity : AppCompatActivity(),
    FeedAddingFragment.Callbacks,
    ManageFeedsFragment.Callbacks,
    SettingsFragment.Callbacks {

    private lateinit var app: OnBackgroundWorkSettingListener

    interface OnBackgroundWorkSettingListener {
        fun onBackgroundWorkSettingChanged(isOn: Boolean)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_managing)
        //toolbar = findViewById(R.id.toolbar)
        //this.setSupportActionBar(toolbar)
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)
        app = applicationContext as OnBackgroundWorkSettingListener

        if (getCurrentFragment() == null) {
            val fragment = when (intent.getIntExtra(EXTRA_MANAGING, MainActivity.ADD_FEEDS)) {
                MainActivity.ADD_FEEDS -> AddFeedsFragment.newInstance()
                MainActivity.MANAGE_FEEDS -> ManageFeedsFragment.newInstance()
                MainActivity.SETTINGS -> SettingsFragment.newInstance()
                else -> throw IllegalArgumentException()
            }

            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()
        }
    }

    override fun onStart() {
        super.onStart()
        when (getCurrentFragment()) {
            is FeedAddingFragment -> supportActionBar?.title = getString(R.string.add_feeds)
            is SettingsFragment -> supportActionBar?.title = getString(R.string.settings)
            else -> return
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        } else when (requestCode) {
            REQUEST_CODE_READ_OPML -> {
                data?.data?.also { uri ->
                    (getCurrentFragment() as AddFeedsFragment?)?.submitUriForImport(uri)
                }
            }
            REQUEST_CODE_WRITE_OPML -> {
                data?.data?.also { uri ->
                    (getCurrentFragment() as ManageFeedsFragment?)?.writeOpml(uri)
                }
            }
        }
    }

    private fun getCurrentFragment(): Fragment? {
        return supportFragmentManager.findFragmentById(R.id.fragment_container)
    }

    private fun replaceFragment(fragment: Fragment, addToBackStack: Boolean = true) {
        if (addToBackStack) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        } else {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
        }

    }

    override fun onNewFeedAdded(feedIdPair: FeedIdPair) {
        val data = Intent().apply {
            putExtra(MainActivity.EXTRA_FEED_ID_PAIR, feedIdPair)
        }
        setResult(Activity.RESULT_OK, data)
        finish()
    }

    override fun onQuerySubmitted(query: String) {
        val fragment = FeedSearchFragment.newInstance(query)
        replaceFragment(fragment)
    }

    override fun onImportOpmlSelected() {
        Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            type = "*/*"
            addCategory(Intent.CATEGORY_OPENABLE)
        }.also { intent ->
            startActivityForResult(intent, REQUEST_CODE_READ_OPML)
        }
    }

    override fun onDoneImporting() {
        finish()
    }

    override fun onFeedsBeingManagedChanged(count: Int) {
        supportActionBar?.title = if (count > 0) {
            getString(R.string.number_selected, count)
        } else {
            getString(R.string.manage_feeds)
        }
    }

    override fun onAddFeedsSelected() {
        val fragment = AddFeedsFragment.newInstance()
        replaceFragment(fragment)
        supportActionBar?.title = getString(R.string.add_feeds)
    }

    override fun onExportOpmlSelected() {
        Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            type = "text/*"
            addCategory(Intent.CATEGORY_OPENABLE)
            putExtra(Intent.EXTRA_TITLE, "NiceFeed_${System.currentTimeMillis()}.opml")
        }.also {intent ->
            startActivityForResult(intent, REQUEST_CODE_WRITE_OPML)
        }
    }

    override fun onDoneManaging() {
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackgroundWorkSettingChanged(isOn: Boolean) {
        app.onBackgroundWorkSettingChanged(isOn)
    }

    override fun onDoneChangingSettings() {
        finish()
    }

    companion object {
        fun newIntent(packageContext: Context, command: Int): Intent {
            return Intent(packageContext, ManagingActivity::class.java).apply {
                putExtra(EXTRA_MANAGING, command)
            }
        }
    }
}