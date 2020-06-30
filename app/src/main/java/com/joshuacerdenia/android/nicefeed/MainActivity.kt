package com.joshuacerdenia.android.nicefeed

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_add_feed.*
import kotlinx.android.synthetic.main.fragment_feed_list.*

private const val TAG = "MainActivityLogs"
private const val REQUEST_CODE_FEED_URL = 0
private const val REQUEST_CODE_NEW_FEED = 1

class MainActivity : AppCompatActivity(),
    FeedListFragment.Callbacks,
    EntryListFragment.Callbacks {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawer_layout)
        toolbar = findViewById(R.id.toolbar)

        this.setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        toolbar.setNavigationIcon(R.drawable.ic_menu)
        toolbar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START, true)
        }

        val currentFragment = supportFragmentManager.findFragmentById(R.id.main_fragment_container)

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
            val title = data?.getStringExtra(EXTRA_FEED_TITLE)
            if (title != null) {
                Snackbar.make(left_drawer, getString(R.string.feed_added, title), Snackbar.LENGTH_SHORT).show()
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

    private fun replaceMainFragmentWith(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_fragment_container, fragment)
            .commit()
    }
}