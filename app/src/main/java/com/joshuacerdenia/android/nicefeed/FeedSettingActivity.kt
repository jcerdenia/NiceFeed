package com.joshuacerdenia.android.nicefeed

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.joshuacerdenia.android.nicefeed.ui.AddFeedFragment
import com.joshuacerdenia.android.nicefeed.ui.FeedSearchFragment
import com.joshuacerdenia.android.nicefeed.ui.ManageFeedsFragment
import java.lang.IllegalArgumentException

const val EXTRA_FEED_WEBSITE = "com.joshuacerdenia.android.nicefeed.feed_website"
private const val EXTRA_FEED_SETTING = "com.joshuacerdenia.android.nicefeed.feed_setting"

class FeedSettingActivity : AppCompatActivity(),
    AddFeedFragment.Callbacks,
    ManageFeedsFragment.Callbacks {

    companion object {
        fun newIntent(packageContext: Context, command: Int): Intent {
            return Intent(packageContext, FeedSettingActivity::class.java).apply {
                putExtra(EXTRA_FEED_SETTING, command)
            }
        }
    }

    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed_setting)

        toolbar = findViewById(R.id.toolbar)

        this.setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

        if (currentFragment == null) {
            val fragment: Fragment
            val title: String

            when (intent.getIntExtra(EXTRA_FEED_SETTING, MANAGE_FEEDS)) {
                ADD_FEEDS -> {
                    title = getString(R.string.add_feed)
                    fragment = AddFeedFragment.newInstance()
                }
                MANAGE_FEEDS -> {
                    title = getString(R.string.manage_feeds)
                    fragment = ManageFeedsFragment.newInstance()
                }
                // TODO: Add others
                else -> throw IllegalArgumentException()
            }

            //supportActionBar?.title = title

            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()
        }
    }

    override fun onNewFeedAdded(website: String) {
        val data = Intent().apply {
            putExtra(EXTRA_FEED_WEBSITE, website)
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onSelectedItemsChanged(count: Int) {
        val title = if (count > 0) {
            getString(R.string.number_selected, count)
        } else {
            getString(R.string.manage_feeds)
        }
        supportActionBar?.title = title
    }

    override fun onDoneManaging() {
        finish()
    }
}