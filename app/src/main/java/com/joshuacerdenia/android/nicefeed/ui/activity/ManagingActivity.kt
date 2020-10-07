package com.joshuacerdenia.android.nicefeed.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.joshuacerdenia.android.nicefeed.R
import com.joshuacerdenia.android.nicefeed.ui.OnBackgroundWorkSettingChanged
import com.joshuacerdenia.android.nicefeed.ui.fragment.*
import com.joshuacerdenia.android.nicefeed.utils.Utils

class ManagingActivity : AppCompatActivity(),
    ManageFeedsFragment.Callbacks,
    FeedAddingFragment.Callbacks,
    SettingsFragment.Callbacks {

    private lateinit var callback: OnBackgroundWorkSettingChanged

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_managing)
        callback = applicationContext as OnBackgroundWorkSettingChanged
        Utils.setStatusBarMode(this)

        if (getCurrentFragment() == null) {
            when (intent.getIntExtra(EXTRA_MANAGING, FeedListFragment.ITEM_ADD_FEEDS)) {
                FeedListFragment.ITEM_ADD_FEEDS -> AddFeedsFragment.newInstance()
                FeedListFragment.ITEM_MANAGE_FEEDS -> ManageFeedsFragment.newInstance()
                FeedListFragment.ITEM_SETTINGS -> SettingsFragment.newInstance()
                else -> throw IllegalArgumentException()
            }.let { fragment ->
                supportFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        } else when (requestCode) {
            REQUEST_CODE_READ_OPML -> {
                data?.data?.let { uri ->
                    (getCurrentFragment() as? AddFeedsFragment)?.submitUriForImport(uri)
                }
            }
            REQUEST_CODE_WRITE_OPML -> {
                data?.data?.let { uri ->
                    (getCurrentFragment() as? ManageFeedsFragment)?.writeOpml(uri)
                }
            }
        }
    }

    override fun onNewFeedAdded(feedId: String) {
        Intent().apply {
            putExtra(MainActivity.EXTRA_FEED_ID, feedId)
        }.also { intent ->
            setResult(Activity.RESULT_OK, intent)
        }
        finish()
    }

    override fun onQuerySubmitted(query: String) {
        replaceFragment(SearchFeedsFragment.newInstance(query))
    }

    override fun onImportOpmlSelected() {
        Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            type = OPML_DOC_TYPE
            addCategory(Intent.CATEGORY_OPENABLE)
        }.also { intent ->
            startActivityForResult(intent, REQUEST_CODE_READ_OPML)
        }
    }

    override fun onAddFeedsSelected() {
        replaceFragment(AddFeedsFragment.newInstance())
        supportActionBar?.title = getString(R.string.add_feeds)
    }

    override fun onExportOpmlSelected() {
        Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            type = OPML_DOC_TYPE
            addCategory(Intent.CATEGORY_OPENABLE)
            putExtra(
                Intent.EXTRA_TITLE,
                OPML_FILE_PREFIX + System.currentTimeMillis() + OPML_FILE_EXT
            )
        }.also {intent ->
            startActivityForResult(intent, REQUEST_CODE_WRITE_OPML)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackgroundWorkSettingChanged(isOn: Boolean) {
        callback.onBackgroundWorkSettingChanged(isOn)
    }

    override fun onToolbarInflated(toolbar: Toolbar, isNavigableUp: Boolean) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(isNavigableUp)
    }

    override fun onFinished() {
        finish()
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

    companion object {
        private const val EXTRA_MANAGING = "com.joshuacerdenia.android.nicefeed.managing"
        private const val REQUEST_CODE_READ_OPML = 0
        private const val REQUEST_CODE_WRITE_OPML = 1
        private const val OPML_DOC_TYPE ="*/*"
        private const val OPML_FILE_PREFIX = "NiceFeed_"
        private const val OPML_FILE_EXT = ".opml"

        fun newIntent(packageContext: Context, item: Int): Intent {
            return Intent(packageContext, ManagingActivity::class.java).apply {
                putExtra(EXTRA_MANAGING, item)
            }
        }
    }
}