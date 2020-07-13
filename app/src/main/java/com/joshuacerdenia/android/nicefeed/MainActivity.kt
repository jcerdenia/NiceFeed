package com.joshuacerdenia.android.nicefeed

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.joshuacerdenia.android.nicefeed.data.model.Entry
import com.joshuacerdenia.android.nicefeed.ui.EntryListFragment
import com.joshuacerdenia.android.nicefeed.ui.FeedListFragment

private const val TAG = "MainActivityLogs"
private const val REQUEST_CODE_ADD_FEED = 0
const val MANAGE_FEEDS = 0
const val ADD_FEEDS = 1

class MainActivity : AppCompatActivity(),
    FeedListFragment.Callbacks,
    EntryListFragment.Callbacks {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //tabLayout = findViewById(R.id.tabs)
        drawerLayout = findViewById(R.id.drawer_layout)
        toolbar = findViewById(R.id.toolbar)

        this.setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        toolbar.apply {
            setNavigationIcon(R.drawable.ic_menu)

            setOnClickListener {
                (getCurrentMainFragment() as EntryListFragment?)?.scrollToTop()
            }

            setNavigationOnClickListener {
                (getCurrentMainFragment() as EntryListFragment?)?.updateUnreadEntriesCount()
                drawerLayout.openDrawer(GravityCompat.START, true)
            }
        }

        /*
        val unreadTab = tabLayout.newTab()
        val allTab = tabLayout.newTab()
        val favoritesTab = tabLayout.newTab()

        //unreadTab.text = "Unread"
        allTab.text = "All"
        favoritesTab.text = "Favorites"
        unreadTab.setIcon(R.drawable.ic_new)

        tabLayout.addTab(unreadTab)
        tabLayout.addTab(allTab)
        tabLayout.addTab(favoritesTab) */

        if (getCurrentMainFragment() == null) {
            val mainFragment = EntryListFragment.newInstance()
            val drawerFragment = FeedListFragment.newInstance()
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.main_fragment_container, mainFragment)
                    .add(R.id.drawer_fragment_container, drawerFragment)
                    .commit()
        }
    }

    private fun getCurrentMainFragment(): Fragment? {
        return supportFragmentManager.findFragmentById(R.id.main_fragment_container)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) {
            return
        } else if (requestCode == REQUEST_CODE_ADD_FEED) {
            val website = data?.getStringExtra(EXTRA_FEED_WEBSITE)
            if (website != null) {
                onFeedSelected(website)
            }
        }
    }

    override fun onManageFeedsSelected() {
        val intent = FeedSettingActivity.newIntent(this@MainActivity, MANAGE_FEEDS)
        startActivity(intent)
    }

    override fun onAddFeedSelected() {
        val intent = FeedSettingActivity.newIntent(this@MainActivity, ADD_FEEDS)
        startActivityForResult(intent, REQUEST_CODE_ADD_FEED)
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