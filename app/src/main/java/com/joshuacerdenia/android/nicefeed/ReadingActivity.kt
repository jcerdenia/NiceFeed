package com.joshuacerdenia.android.nicefeed

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.joshuacerdenia.android.nicefeed.data.model.Entry
import com.joshuacerdenia.android.nicefeed.ui.EntryFragment

private const val EXTRA_ENTRY = "com.joshuacerdenia.android.nicefeed.entry"

private lateinit var toolbar: Toolbar

class ReadingActivity : AppCompatActivity() {

    companion object {
        fun newIntent(packageContext: Context, entry: Entry): Intent {
            return Intent(packageContext, ReadingActivity::class.java).apply {
                putExtra(EXTRA_ENTRY, entry)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reading)

        val entry = intent.getSerializableExtra(EXTRA_ENTRY) as Entry

        toolbar = findViewById(R.id.toolbar)

        this.setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //supportActionBar?.title = getString(R.string.add_feed)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

        if (currentFragment == null) {
            val fragment = EntryFragment.newInstance(entry)
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}