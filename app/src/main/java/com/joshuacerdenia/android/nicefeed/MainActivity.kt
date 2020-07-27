package com.joshuacerdenia.android.nicefeed

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.joshuacerdenia.android.nicefeed.data.local.UserPreferences
import com.joshuacerdenia.android.nicefeed.data.model.Entry
import com.joshuacerdenia.android.nicefeed.ui.EntryFragment
import com.joshuacerdenia.android.nicefeed.ui.EntryListFragment
import com.joshuacerdenia.android.nicefeed.ui.FeedListFragment
import com.joshuacerdenia.android.nicefeed.utils.simplified
import java.text.DateFormat.*
import java.util.*

private const val TAG = "MainActivityLogs"
private const val REQUEST_CODE_ADD_FEED = 0
const val MANAGE_FEEDS = 0
const val ADD_FEEDS = 1

class MainActivity : AppCompatActivity(),
    FeedListFragment.Callbacks,
    EntryListFragment.Callbacks,
    EntryFragment.Callbacks {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawerLayout = findViewById(R.id.drawer_layout)
        toolbar = findViewById(R.id.toolbar)

        this.setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        toolbar.setNavigationIcon(R.drawable.ic_menu)

        if (getMainFragment() == null) {
            val feedId = UserPreferences.getSavedFeedId(this)
            val mainFragment = EntryListFragment.newInstance(feedId)
            val drawerFragment = FeedListFragment.newInstance()

            supportFragmentManager
                .beginTransaction()
                .add(R.id.main_fragment_container, mainFragment)
                .add(R.id.drawer_fragment_container, drawerFragment)
                .commit()
        }
    }

    override fun onStart() {
        super.onStart()

        toolbar.apply {
            setOnClickListener {
                when (getMainFragment()) {
                    is EntryListFragment -> (getMainFragment() as EntryListFragment).scrollToTop()
                    is EntryFragment -> (getMainFragment() as EntryFragment).scrollToTop()
                }
            }

            setNavigationOnClickListener {
                when (getMainFragment()) {
                    is EntryFragment -> onBackPressed()
                    else -> { // Default
                        (getMainFragment() as EntryListFragment).updateUnreadEntriesCount()
                        drawerLayout.openDrawer(GravityCompat.START, true)
                    }
                }
            }
        }
    }

    private fun getMainFragment(): Fragment? {
        return supportFragmentManager.findFragmentById(R.id.main_fragment_container)
    }

    private fun getDrawerFragment(): Fragment? {
        return supportFragmentManager.findFragmentById(R.id.drawer_fragment_container)
    }

    private fun replaceMainFragment(newFragment: Fragment, addToBackStack: Boolean) {
        if (addToBackStack) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_fragment_container, newFragment)
                .addToBackStack(null)
                .commit()
        } else {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_fragment_container, newFragment)
                .commit()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) {
            return
        } else if (requestCode == REQUEST_CODE_ADD_FEED) {
            val website = data?.getStringExtra(EXTRA_FEED_WEBSITE)
            if (website != null) {
                onFeedSelected(null, website)
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

    override fun onFeedSelected(currentFeedId: String?, newFeedId: String) {
        drawerLayout.closeDrawers()

        if (currentFeedId != newFeedId) {
            val newFragment = EntryListFragment.newInstance(newFeedId)
            val handler = Handler()
            handler.postDelayed({
                replaceMainFragment(newFragment, false)
            }, 250)
        }
    }

    override fun onFeedLoaded(feedId: String, title: String) {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(false)
            this.title = title
            this.subtitle = null
        }

        toolbar.setNavigationIcon(R.drawable.ic_menu)
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNDEFINED)

        val handler = Handler()
        handler.postDelayed({
            (getDrawerFragment() as FeedListFragment?)?.setActiveSelection(feedId)
        }, 500)
    }

    override fun onFeedRemoved() {
        supportActionBar?.title = getString(R.string.app_name)
        (getDrawerFragment() as FeedListFragment?)?.setActiveSelection(null)
        drawerLayout.openDrawer(GravityCompat.START, true)
    }

    override fun onEntrySelected(entry: Entry) {
        val newFragment = EntryFragment.newInstance(entry)
        replaceMainFragment(newFragment, true)
    }

    override fun onCategoriesNeeded(): List<String> {
        return (getDrawerFragment() as FeedListFragment).viewModel.categories
    }

    override fun onEntryLoaded(date: Date?, website: String?) {
        val formattedDate = date?.let { getDateInstance(MEDIUM).format(it) }
        val time = date?.let { getTimeInstance(SHORT).format(it) }

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            this.title = formattedDate ?: "No Date"
            this.subtitle = "$time – ${website.simplified()}"
        }

        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}