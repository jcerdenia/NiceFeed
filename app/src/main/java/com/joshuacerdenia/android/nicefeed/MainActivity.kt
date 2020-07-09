package com.joshuacerdenia.android.nicefeed

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout

private const val TAG = "MainActivityLogs"
private const val REQUEST_CODE_NEW_FEED = 1

class MainActivity : AppCompatActivity(),
    FeedListFragment.Callbacks,
    EntryListFragment.Callbacks {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.main_fragment_container)

        drawerLayout = findViewById(R.id.drawer_layout)
        toolbar = findViewById(R.id.toolbar)

        this.setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        toolbar.setNavigationIcon(R.drawable.ic_menu)
        toolbar.setNavigationOnClickListener {
            currentFragment?.let {
                val fragment = it as EntryListFragment
                fragment.updateUnreadEntriesCount()
            }
            drawerLayout.openDrawer(GravityCompat.START, true)
        }

        if (currentFragment == null) {
            val mainFragment = EntryListFragment.newInstance()
            val drawerFragment = FeedListFragment.newInstance()
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.main_fragment_container, mainFragment)
                    .add(R.id.drawer_fragment_container, drawerFragment)
                    .commit()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) {
            return
        } else if (requestCode == REQUEST_CODE_NEW_FEED) {
            val website = data?.getStringExtra(EXTRA_FEED_WEBSITE)
            if (website != null) {
                onFeedSelected(website)
            }
        }
    }

    override fun onAddFeedSelected() {
        val intent = Intent(this, FeedSettingActivity::class.java)
        startActivityForResult(intent, REQUEST_CODE_NEW_FEED)
    }

    override fun onFeedSelected(website: String) {
        EntryListFragment.setWebsite(website)
        drawerLayout.closeDrawers()
    }

    override fun onFeedLoaded(feedTitle: String) {
        supportActionBar?.title = feedTitle
    }

    override fun onFeedDeleted() {
        supportActionBar?.title = getString(R.string.app_name)
        drawerLayout.openDrawer(GravityCompat.START, true)
    }

    override fun onEntrySelected(entry: Entry) {
        val intent = ReadingActivity.newIntent(this@MainActivity, entry)
        startActivity(intent)
    }
}