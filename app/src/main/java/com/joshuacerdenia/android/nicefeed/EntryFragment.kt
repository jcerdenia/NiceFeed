package com.joshuacerdenia.android.nicefeed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

private const val ARG_ENTRY = "ARG_ENTRY"
private const val TAG = "EntryFragment"

class EntryFragment: Fragment() {

    companion object {

        fun newInstance(entry: Entry): EntryFragment {
            return EntryFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_ENTRY, entry)
                }
            }
        }
    }

    private val entryViewModel: EntryViewModel by lazy {
        ViewModelProvider(this).get(EntryViewModel::class.java)
    }

    private lateinit var titleTextView: TextView
    private lateinit var dateTextView: TextView
    private lateinit var contentTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_entry, container, false)

        titleTextView = view.findViewById(R.id.entry_title)
        dateTextView = view.findViewById(R.id.entry_date)
        contentTextView = view.findViewById(R.id.entry_content)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val entry = arguments?.getSerializable(ARG_ENTRY) as Entry

        titleTextView.text = entry.title
        dateTextView.text = entry.date.toString()
        contentTextView.text = HtmlCompat.fromHtml(
            entry.content ?: entry.description ?: "",
            0
        )

        entry.isRead = true

        entryViewModel.updateEntry(entry)
    }
}