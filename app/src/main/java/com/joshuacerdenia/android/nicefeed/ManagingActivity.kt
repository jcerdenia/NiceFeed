package com.joshuacerdenia.android.nicefeed

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.joshuacerdenia.android.nicefeed.data.model.FeedIdPair
import com.joshuacerdenia.android.nicefeed.ui.*

private const val TAG = "ManagingActivity"
const val EXTRA_FEED_ID_PAIR = "com.joshuacerdenia.android.nicefeed.feed_id_pair"
private const val EXTRA_MANAGING = "com.joshuacerdenia.android.nicefeed.managing"
private const val REQUEST_CODE_READ_OPML = 0

class ManagingActivity : AppCompatActivity(),
    FeedAddingFragment.Callbacks,
    ManageFeedsFragment.Callbacks {

    companion object {
        fun newIntent(packageContext: Context, command: Int): Intent {
            return Intent(packageContext, ManagingActivity::class.java).apply {
                putExtra(EXTRA_MANAGING, command)
            }
        }
    }

    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_managing)
        toolbar = findViewById(R.id.toolbar)

        this.setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (getCurrentFragment() == null) {
            val fragment = when (intent.getIntExtra(EXTRA_MANAGING, ADD_FEEDS)) {
                ADD_FEEDS -> AddFeedsFragment.newInstance()
                MANAGE_FEEDS -> ManageFeedsFragment.newInstance()
                SETTINGS -> SettingsFragment.newInstance()
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
        } else if (requestCode == REQUEST_CODE_READ_OPML) {
            data?.data?.also {
                (getCurrentFragment() as AddFeedsFragment?)?.submitUriForImport(it)
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
            putExtra(EXTRA_FEED_ID_PAIR, feedIdPair)
        }
        setResult(Activity.RESULT_OK, data)
        finish()
    }

    override fun onQuerySubmitted(query: String) {
        val fragment = FeedSearchFragment.newInstance(query)
        replaceFragment(fragment)
    }

    override fun onImportOpmlSelected() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            type = "*/*"
            addCategory(Intent.CATEGORY_OPENABLE)
        }
        startActivityForResult(intent, REQUEST_CODE_READ_OPML)
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

    override fun onDoneManaging() {
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}