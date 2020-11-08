package com.joshuacerdenia.android.nicefeed.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.joshuacerdenia.android.nicefeed.R
import com.joshuacerdenia.android.nicefeed.data.model.feed.FeedManageable
import com.joshuacerdenia.android.nicefeed.util.Utils
import com.joshuacerdenia.android.nicefeed.util.extensions.addRipple
import com.joshuacerdenia.android.nicefeed.util.extensions.hide
import com.joshuacerdenia.android.nicefeed.util.extensions.toEditable
import com.squareup.picasso.Picasso

class EditFeedFragment : BottomSheetDialogFragment() {

    interface Callback {
        fun onFeedInfoChanged(title: String, category: String)
    }

    private lateinit var imageView: ImageView
    private lateinit var titleEditText: EditText
    private lateinit var urlTextView: TextView
    private lateinit var categoryEditText: AutoCompleteTextView
    private lateinit var descriptionTextView: TextView
    private lateinit var undoButton: Button
    private lateinit var doneButton: Button

    private var callback: Callback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogNoFloating)
        callback = targetFragment as? Callback
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_feed, container, false)
        imageView = view.findViewById(R.id.image_view)
        titleEditText = view.findViewById(R.id.title_edit_text)
        urlTextView = view.findViewById(R.id.url_text_view)
        categoryEditText = view.findViewById(R.id.category_edit_text)
        descriptionTextView = view.findViewById(R.id.description_text_view)
        undoButton = view.findViewById(R.id.undo_changes_button)
        doneButton = view.findViewById(R.id.done_button)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val feed = arguments?.getSerializable(ARG_FEED) as FeedManageable?
        val categories = arguments?.getStringArray(ARG_CATEGORIES) ?: emptyArray()
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, categories)

        Picasso.get().load(feed?.imageUrl).placeholder(R.drawable.feed_icon).into(imageView)
        fillEditables(feed?.title, feed?.category)
        if (!feed?.description.isNullOrEmpty()) {
            descriptionTextView.text = feed?.description
        } else {
            descriptionTextView.hide()
        }

        categoryEditText.apply {
            setAdapter(adapter)
            threshold = 1
            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) submit(feed)
                true
            }
        }

        urlTextView.apply {
            text = feed?.url
            addRipple()
            setOnClickListener {
                setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_green_check, 0, 0,0)
                Utils.copyLinkToClipboard(context, this.text.toString())
            }
        }

        undoButton.setOnClickListener { fillEditables(feed?.title, feed?.category) }
        doneButton.setOnClickListener { submit(feed) }
    }

    private fun fillEditables(title: String?, category: String?) {
        titleEditText.text = title.toEditable()
        categoryEditText.text = category.toEditable()
    }

    private fun submit(feed: FeedManageable?) {
        val newTitle = titleEditText.text.toString().trim()
        val newCategory = categoryEditText.text.toString().trim()
        if (newTitle.isNotEmpty() && newCategory.isNotEmpty()) {
            if (feed?.title != newTitle || feed.category != newCategory) {
                callback?.onFeedInfoChanged(newTitle, newCategory)
            }
        }
        dismiss()
    }

    companion object {

        private const val ARG_FEED = "ARG_FEED"
        private const val ARG_CATEGORIES = "ARG_CATEGORIES"

        fun newInstance(feed: FeedManageable, categories: Array<String>): EditFeedFragment {
            val args = Bundle().apply {
                putSerializable(ARG_FEED, feed)
                putStringArray(ARG_CATEGORIES, categories)
            }
            return EditFeedFragment().apply { arguments = args }
        }
    }
}