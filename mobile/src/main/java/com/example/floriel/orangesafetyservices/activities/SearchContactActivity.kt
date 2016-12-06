package com.example.floriel.orangesafetyservices.activities

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
import com.example.floriel.orangesafetyservices.recyclers.ContactsAdapter
import com.miguelcatalan.materialsearchview.MaterialSearchView


class SearchContactActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor> {

    private lateinit var searchView: MaterialSearchView
    private val PROJECTION = arrayOf(ContactsContract.Contacts._ID,
            ContactsContract.Contacts.LOOKUP_KEY,
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)
    private val SELECTION = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY + " LIKE ? AND " + ContactsContract.Contacts.HAS_PHONE_NUMBER +
            "=1"// + ContactsContract.CommonDataKinds.Phone.TYPE + "='" + ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE + "'"
    private var mSearchString = ""
    private var mSelectionArgs = arrayOf("%$mSearchString%")
    private lateinit var mContactRecyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_contact)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        mContactRecyclerView = findViewById(R.id.recycler_list_contacts) as RecyclerView
        mContactRecyclerView.layoutManager = LinearLayoutManager(this)
        mContactRecyclerView.itemAnimator = DefaultItemAnimator()

        val loader = this
        searchView = findViewById(R.id.search_view) as MaterialSearchView
        searchView.setVoiceSearch(true)
        searchView.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                mSearchString = newText!!
                supportLoaderManager.restartLoader(0, null, loader)
                return true
            }

        })

        searchView.setOnSearchViewListener(object : MaterialSearchView.SearchViewListener {
            override fun onSearchViewShown() {
                //Do some magic
            }

            override fun onSearchViewClosed() {
                //Do some magic
            }
        })

        supportLoaderManager.initLoader(0, null, this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val item = menu!!.findItem(R.id.action_search)
        searchView.setMenuItem(item)

        return true
    }

    override fun onBackPressed() {
        if (searchView.isSearchOpen) {
            searchView.closeSearch()
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
                    searchView.setQuery(searchWrd, false)
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
        //To change body of created functions use File | Settings | File Templates.

    }

    override fun onLoadFinished(loader: Loader<Cursor>?, data: Cursor?) {
        mContactRecyclerView.adapter = ContactsAdapter(this, data!!)
    }

}
