package com.joshuacerdenia.android.nicefeed

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.joshuacerdenia.android.nicefeed.ui.*
import java.lang.IllegalArgumentException

const val EXTRA_FEED_ID = "com.joshuacerdenia.android.nicefeed.feed_website"
private const val EXTRA_FEED_SETTING = "com.joshuacerdenia.android.nicefeed.feed_setting"

class ManagingActivity : AppCompatActivity(),
    FeedAddingFragment.Callbacks,
    ManageFeedsFragment.Callbacks {

    companion object {
        fun newIntent(packageContext: Context, command: Int): Intent {
            return Intent(packageContext, ManagingActivity::class.java).apply {
                putExtra(EXTRA_FEED_SETTING, command)
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
            val fragment = when (intent.getIntExtra(EXTRA_FEED_SETTING, ADD_FEEDS)) {
                ADD_FEEDS -> AddFeedsFragment.newInstance()
                MANAGE_FEEDS -> ManageFeedsFragment.newInstance()
                // Space here for more
                else -> throw IllegalArgumentException()
            }

            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()
        }
    }

    override fun onStart() {
        super.onStart()
        when (getCurrentFragment()) {
            is FeedAddingFragment -> supportActionBar?.title = getString(R.string.add_feeds)
            // Space here for more
            else -> return
        }
    }

    private fun getCurrentFragment(): Fragment? {
        return supportFragmentManager.findFragmentById(R.id.fragment_container)
    }

    override fun onNewFeedAdded(feedId: String) {
        val data = Intent().apply {
            putExtra(EXTRA_FEED_ID, feedId)
        }
        setResult(Activity.RESULT_OK, data)
        finish()
    }

    override fun onQuerySubmitted(query: String) {
        val fragment = FeedSearchFragment.newInstance(query)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onManagingItemsChanged(count: Int) {
        supportActionBar?.title = if (count > 0) {
            getString(R.string.number_selected, count)
        } else {
            getString(R.string.manage_feeds)
        }
    }

    override fun onDoneManaging() {
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}