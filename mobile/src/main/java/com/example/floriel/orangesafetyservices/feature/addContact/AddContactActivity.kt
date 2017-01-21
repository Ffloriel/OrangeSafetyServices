package com.example.floriel.orangesafetyservices.feature.addContact

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.speech.RecognizerIntent
import android.support.v4.app.LoaderManager
import android.support.v4.content.CursorLoader
import android.support.v4.content.Loader
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.Menu
import com.example.floriel.orangesafetyservices.R
import com.example.floriel.orangesafetyservices.data.Contact
import com.example.floriel.orangesafetyservices.view.adapter.ContactAdapter
import com.miguelcatalan.materialsearchview.MaterialSearchView


class SearchContactActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor> {

    val toolbar by lazy { findViewById(R.id.toolbar) as Toolbar }
    val mSearchView by lazy { findViewById(R.id.search_view) as MaterialSearchView }
    val mContactRecyclerView by lazy { findViewById(R.id.recycler_list_contacts) as RecyclerView }

    val PROJECTION = arrayOf(ContactsContract.Contacts._ID,
            ContactsContract.Contacts.LOOKUP_KEY,
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)

    val SELECTION = "${ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY} LIKE ? AND ${ContactsContract.Contacts.HAS_PHONE_NUMBER}=1"
    var mSearchString = ""
    var mSelectionArgs = arrayOf("%$mSearchString%")

    private var mContactList: MutableList<Contact> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_contact)
        setSupportActionBar(toolbar)

        mContactRecyclerView.layoutManager = LinearLayoutManager(this)
        mContactRecyclerView.itemAnimator = DefaultItemAnimator()

        mSearchView.setVoiceSearch(true)
        mSearchView.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                mSearchString = newText!!
                supportLoaderManager.restartLoader(0, null, this@SearchContactActivity)
                return true
            }

        })

        this.mSearchView.setOnSearchViewListener(object : MaterialSearchView.SearchViewListener {
            override fun onSearchViewShown() {
                //Do some magic
            }

            override fun onSearchViewClosed() {
                //Do some magic
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val item = menu?.findItem(R.id.action_search)
        mSearchView.setMenuItem(item)
        return true
    }

    override fun onBackPressed() {
        if (mSearchView.isSearchOpen) {
            mSearchView.closeSearch()
        } else {
            super.onBackPressed()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == Activity.RESULT_OK) {
            val matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            if (matches != null && matches.size > 0) {
                val searchWrd = matches[0]
                if (!TextUtils.isEmpty(searchWrd)) {
                    mSearchView.setQuery(searchWrd, false)
                }
            }
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        val contentUri: Uri
        if (mSearchString.isNotEmpty()) {
            contentUri = Uri.withAppendedPath(
                    ContactsContract.Contacts.CONTENT_FILTER_URI,
                    Uri.encode(mSearchString)
            )
        } else {
            contentUri = ContactsContract.Contacts.CONTENT_URI
        }

        return CursorLoader(
                this,
                contentUri,
                PROJECTION,
                SELECTION,
                mSelectionArgs,
                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " ASC"
        )
    }

    override fun onLoaderReset(loader: Loader<Cursor>?) {
    }

    override fun onLoadFinished(loader: Loader<Cursor>?, data: Cursor?) {
        if (data != null && data.count > 0) {
            while (data.moveToNext()) {
                val itemId = data.getString(data.getColumnIndexOrThrow(ContactsEntry.COLUMN_NAME_ENTRY_ID))
                val name = data.getString(data.getColumnIndexOrThrow(ContactsEntry.COLUMN_NAME_FULLNAME))
                val phoneNumber = data.getString(data.getColumnIndexOrThrow(ContactsEntry.COLUMN_NAME_PHONE_NUMBER))
                val contact = Contact(name, phoneNumber, itemId)
                mContactList.add(contact)
            }
        }
        mContactRecyclerView.adapter = ContactAdapter(mContactList, this)
    }

}
