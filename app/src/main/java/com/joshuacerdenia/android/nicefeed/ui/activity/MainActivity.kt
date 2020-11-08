package com.joshuacerdenia.android.nicefeed.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.joshuacerdenia.android.nicefeed.R
import com.joshuacerdenia.android.nicefeed.data.local.NiceFeedPreferences
import com.joshuacerdenia.android.nicefeed.ui.OnHomePressed
import com.joshuacerdenia.android.nicefeed.ui.fragment.EntryFragment
import com.joshuacerdenia.android.nicefeed.ui.fragment.EntryListFragment
import com.joshuacerdenia.android.nicefeed.ui.fragment.FeedListFragment
import com.joshuacerdenia.android.nicefeed.util.Utils

class MainActivity : AppCompatActivity(),
    FeedListFragment.Callbacks,
    EntryListFragment.Callbacks,
    EntryFragment.Callbacks,
    OnHomePressed
{

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawerLayout = findViewById(R.id.drawerLayout)
        Utils.setStatusBarMode(this)

        if (getFragment(FRAGMENT_MAIN) == null) {
            val feedId = intent?.getStringExtra(EXTRA_FEED_ID)
                ?: NiceFeedPreferences.getLastViewedFeedId(this)
            val entryId = intent?.getStringExtra(EXTRA_ENTRY_ID)
            val mainFragment = EntryListFragment.newInstance(feedId, entryId)
            loadFragments(mainFragment, FeedListFragment.newInstance())
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val feedId = intent?.getStringExtra(EXTRA_FEED_ID)
        val entryId = intent?.getStringExtra(EXTRA_ENTRY_ID)
        supportFragmentManager.popBackStack()
        replaceMainFragment(EntryListFragment.newInstance(feedId, entryId, true), false)
        drawerLayout.closeDrawers()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        } else if (requestCode == REQUEST_CODE_ADD_FEED) {
            data?.getStringExtra(EXTRA_FEED_ID)?.let { feedId ->
                loadFeed(feedId, true)
            }
        }
    }

    private fun loadFragments(main: Fragment, navigation: Fragment) {
        supportFragmentManager.beginTransaction()
            .add(R.id.main_fragment_container, main)
            .add(R.id.drawer_fragment_container, navigation)
            .commit()
    }

    private fun replaceMainFragment(newFragment: Fragment, addToBackStack: Boolean) {
        if (addToBackStack) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, newFragment)
                .addToBackStack(null).commit()
        } else {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, newFragment).commit()
        }
    }

    private fun getFragment(code: Int): Fragment? {
        val fragmentId = when (code) {
            FRAGMENT_MAIN -> R.id.main_fragment_container
            FRAGMENT_NAVIGATION -> R.id.drawer_fragment_container
            else -> null
        }
        return if (fragmentId != null) {
            supportFragmentManager.findFragmentById(fragmentId)
        } else null
    }

    override fun onToolbarInflated(toolbar: Toolbar, isNavigableUp: Boolean) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(isNavigableUp)
    }

    override fun onHomePressed() {
        drawerLayout.apply {
            openDrawer(GravityCompat.START, true)
            setDrawerLockMode(DrawerLayout.LOCK_MODE_UNDEFINED)
        }
    }

    override fun onMenuItemSelected(item: Int) {
        val intent = ManagingActivity.newIntent(this@MainActivity, item)
        if (item == FeedListFragment.ITEM_SETTINGS) {
            startActivity(intent)
        } else startActivityForResult(intent, REQUEST_CODE_ADD_FEED)
    }

    override fun onFeedSelected(feedId: String, activeFeedId: String?) {
        if (feedId != activeFeedId) loadFeed(feedId) else drawerLayout.closeDrawers()
    }

    private fun loadFeed(feedId: String, blockAutoUpdate: Boolean = false) {
        EntryListFragment.newInstance(feedId, blockAutoUpdate = blockAutoUpdate).let { fragment ->
            Handler().postDelayed({ replaceMainFragment(fragment, false) }, 350)
        }
        drawerLayout.closeDrawers()
    }

    override fun onFeedLoaded(feedId: String) {
        (getFragment(FRAGMENT_NAVIGATION) as? FeedListFragment)?.updateActiveFeedId(feedId)
    }

    override fun onFeedRemoved() {
        replaceMainFragment(EntryListFragment.newInstance(null), false)
    }

    override fun onEntrySelected(entryId: String) {
        replaceMainFragment(EntryFragment.newInstance(entryId), true)
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    override fun onCategoriesNeeded(): Array<String> {
        return (getFragment(FRAGMENT_NAVIGATION) as? FeedListFragment)?.getCategories() ?: emptyArray()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {

        private const val REQUEST_CODE_ADD_FEED = 0
        private const val FRAGMENT_MAIN = 0
        private const val FRAGMENT_NAVIGATION = 1

        const val EXTRA_FEED_ID = "com.joshuacerdenia.android.nicefeed.feed_id"
        const val EXTRA_ENTRY_ID = "com.joshuacerdenia.android.nicefeed.entry_id"

        fun newIntent(context: Context, feedId: String, latestEntryId: String): Intent {
            return Intent(context, MainActivity::class.java).apply {
                putExtra(EXTRA_FEED_ID, feedId)
                putExtra(EXTRA_ENTRY_ID, latestEntryId)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
        }
    }
}