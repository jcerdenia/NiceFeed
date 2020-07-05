package com.joshuacerdenia.android.nicefeed

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

const val EXTRA_FEED_WEBSITE = "EXTRA_FEED_WEBSITE"

class FeedSettingActivity : AppCompatActivity(),
    AddFeedFragment.Callbacks {

    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed_setting)

        toolbar = findViewById(R.id.toolbar)

        this.setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.add_feed)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

        if (currentFragment == null) {
            val fragment = AddFeedFragment.newInstance()
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
}