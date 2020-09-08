package com.joshuacerdenia.android.nicefeed

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.joshuacerdenia.android.nicefeed.data.local.NiceFeedPreferences
import com.joshuacerdenia.android.nicefeed.data.model.FeedIdPair
import com.joshuacerdenia.android.nicefeed.ui.EntryFragment
import com.joshuacerdenia.android.nicefeed.ui.EntryListFragment
import com.joshuacerdenia.android.nicefeed.ui.FeedListFragment
import com.joshuacerdenia.android.nicefeed.utils.simplified

private const val TAG = "MainActivityLogs"

class MainActivity : AppCompatActivity(),
    FeedListFragment.Callbacks,
    EntryListFragment.Callbacks,
    EntryFragment.Callbacks {

    private lateinit var drawerLayout: DrawerLayout
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawerLayout = findViewById(R.id.drawerLayout)

        if (getMainFragment() == null) {
            val feedIdPair = intent?.getSerializableExtra(EXTRA_FEED_ID_PAIR) as FeedIdPair?
            val feedId = NiceFeedPreferences.getLastViewedFeedId(this)
            loadFragments(
                EntryListFragment.newInstance(feedId),
                FeedListFragment.newInstance()
            )
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val feedIdPair = intent?.getSerializableExtra(EXTRA_FEED_ID_PAIR) as FeedIdPair?
        replaceMainFragment(EntryListFragment.newInstance(feedIdPair?.url), false)
        (getNavigationFragment() as FeedListFragment?)?.updateActiveFeedId(feedIdPair?.url)
        drawerLayout.closeDrawers()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        } else if (requestCode == REQUEST_CODE_ADD_FEED) {
            val feedIdPair = data?.getSerializableExtra(EXTRA_FEED_ID_PAIR) as FeedIdPair?
            if (feedIdPair != null) {
                val fragment = EntryListFragment.newInstance(feedIdPair.url, true)
                handler.postDelayed({
                    replaceMainFragment(fragment, false)
                }, 350)

                (getNavigationFragment() as FeedListFragment?)?.updateActiveFeedId(feedIdPair.url)
                drawerLayout.closeDrawers()
            }
        }
    }

    private fun loadFragments(main: Fragment, drawer: Fragment) {
        supportFragmentManager.beginTransaction()
            .add(R.id.main_fragment_container, main)
            .add(R.id.drawer_fragment_container, drawer)
            .commit()
    }

    private fun getMainFragment(): Fragment? {
        return supportFragmentManager.findFragmentById(R.id.main_fragment_container)
    }

    private fun getNavigationFragment(): Fragment? {
        return supportFragmentManager.findFragmentById(R.id.drawer_fragment_container)
    }

    private fun replaceMainFragment(newFragment: Fragment, addToBackStack: Boolean) {
        if (addToBackStack) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, newFragment)
                .addToBackStack(null)
                .commit()
        } else {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, newFragment)
                .commit()
        }
    }

    override fun onManageFeedsSelected() {
        val intent = ManagingActivity.newIntent(this@MainActivity, MANAGE_FEEDS)
        startActivityForResult(intent, REQUEST_CODE_ADD_FEED)
    }

    override fun onAddFeedSelected() {
        val intent = ManagingActivity.newIntent(this@MainActivity, ADD_FEEDS)
        startActivityForResult(intent, REQUEST_CODE_ADD_FEED)
    }

    override fun onFeedSelected(feedIdPair: FeedIdPair, activeFeedId: String?) {
        drawerLayout.closeDrawers()
        if (feedIdPair.url != activeFeedId) {
            val fragment = EntryListFragment.newInstance(feedIdPair.url)
            handler.postDelayed({
                replaceMainFragment(fragment, false)
            }, 350)
        }
    }

    override fun onSettingsSelected() {
        val intent = ManagingActivity.newIntent(this@MainActivity, SETTINGS)
        startActivity(intent)
    }

    override fun onHomeSelected() {
        drawerLayout.apply {
            openDrawer(GravityCompat.START, true)
            setDrawerLockMode(DrawerLayout.LOCK_MODE_UNDEFINED)
        }
    }

    override fun onFeedLoaded(feedId: String) {
        (getNavigationFragment() as FeedListFragment?)?.updateActiveFeedId(feedId)
    }

    override fun onEntrySelected(entryId: String) {
        val newFragment = EntryFragment.newInstance(entryId)
        replaceMainFragment(newFragment, true)
    }

    override fun onEntryLoaded(website: String) {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            this.title = website.simplified()
        }
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    override fun onCategoriesNeeded(): Array<String> {
        return (getNavigationFragment() as FeedListFragment).getCategories()
    }

    override fun onFeedRemoved() {
        replaceMainFragment(EntryListFragment.newInstance(null), false)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        private const val REQUEST_CODE_ADD_FEED = 0
        const val EXTRA_FEED_ID_PAIR = "com.joshuacerdenia.android.nicefeed.feed_id_pair"
        const val MANAGE_FEEDS = 0
        const val ADD_FEEDS = 1
        const val SETTINGS = 2

        fun newIntent(context: Context, feedIdPair: FeedIdPair): Intent {
            return Intent(context, MainActivity::class.java).apply {
                putExtra(EXTRA_FEED_ID_PAIR, feedIdPair)
                addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
        }
    }
}