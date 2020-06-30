package com.joshuacerdenia.android.nicefeed

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

const val EXTRA_FEED_URL = "EXTRA_FEED_URL"
const val EXTRA_FEED_TITLE = "EXTRA_FEED_TITLE"

class FeedSettingActivity : AppCompatActivity(),
    AddFeedFragment.Callbacks,
    FeedSearchFragment.Callbacks {

    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed_setting)

        toolbar = findViewById(R.id.toolbar)

        this.setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Add Feed" // temporary

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

        if (currentFragment == null) {
            val fragment = AddFeedFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()
        }
    }

    /*
    override fun onFeedUrlSubmitted(url: String) {
        //BackupUrl.setUrl(null)
        val data = Intent().apply {
            putExtra(EXTRA_FEED_URL, url)
        }
        setResult(Activity.RESULT_OK, data)
        finish()
    }*/

    override fun onNewFeedAdded(title: String) {
        val data = Intent().apply {
            putExtra(EXTRA_FEED_TITLE, title)
        }
        setResult(Activity.RESULT_OK, data)
        finish()
    }

    override fun onQuerySubmitted(query: String) {
        val newFragment = FeedSearchFragment.newInstance(query)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, newFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onSearchItemSelected(url: String) {
        val data = Intent().apply {
            putExtra(EXTRA_FEED_URL, url)
        }
        setResult(Activity.RESULT_OK, data)
        finish()
    }
}