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
import com.example.floriel.orangesafetyservices.util.Constant
import com.example.floriel.orangesafetyservices.view.adapter.ContactAdapter
import com.miguelcatalan.materialsearchview.MaterialSearchView


class AddContactActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor> {

    val toolbar by lazy { findViewById(R.id.toolbar) as Toolbar }
    val mSearchView by lazy { findViewById(R.id.search_view) as MaterialSearchView }
    val mContactRecyclerView by lazy { findViewById(R.id.recycler_list_contacts) as RecyclerView }
    private lateinit var mAdapter: ContactAdapter

    val PROJECTION = arrayOf(ContactsContract.Contacts._ID,
            ContactsContract.Contacts.LOOKUP_KEY,
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)

    val SELECTION = "${ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY} LIKE ? AND ${ContactsContract.Contacts.HAS_PHONE_NUMBER}=1"
    var mSearchString = ""
    var mSelectionArgs = arrayOf("%$mSearchString%")

    private var mContactList: MutableList<Contact> = arrayListOf()
    private var mContactType = Constant.SAFETY_CONTACT_SELECTION

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_contact_act)

        mContactType = this.intent.extras.getInt(Constant.KEY_CONTACT_TYPE)

        // Set up the toolbar.
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(false)
            setDisplayShowHomeEnabled(false)
        }
        actionBar?.setTitle(R.string.add_contact)

        mContactRecyclerView.layoutManager = LinearLayoutManager(this)
        mContactRecyclerView.itemAnimator = DefaultItemAnimator()
        mAdapter = ContactAdapter(mutableListOf(), this, mContactType)
        mContactRecyclerView.adapter = mAdapter

        mSearchView.setVoiceSearch(true)
        mSearchView.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                mSearchString = newText!!
                mAdapter.updateContactList(mContactList.filter { it -> it.name.toLowerCase().contains(newText) } as MutableList<Contact>)
                mContactRecyclerView.adapter = mAdapter
                return true
            }

        })

        this.mSearchView.setOnSearchViewListener(object : MaterialSearchView.SearchViewListener {
            override fun onSearchViewShown() {}
            override fun onSearchViewClosed() {}
        })
        supportLoaderManager.initLoader(0, null, this)
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

    override fun onLoaderReset(loader: Loader<Cursor>?) {}

    override fun onLoadFinished(loader: Loader<Cursor>?, data: Cursor?) {
        if (data != null && data.count > 0) {
            while (data.moveToNext()) {
                val name = data.getString(data.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY))
                val phoneNumber = this.getPhoneNumber(name)
                val contact = Contact(name, phoneNumber)
                mContactList.add(contact)
            }
        }
        mContactRecyclerView.adapter = ContactAdapter(mContactList, this, mContactType)
    }

    private fun getPhoneNumber(contact: String): String {
        val phones = this.baseContext.contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY + " = '" + contact + "' AND " +
                        ContactsContract.CommonDataKinds.Phone.TYPE + "='" + ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE + "'",
                null, null)
        var phoneNumber = ""
        if (phones.count > 0) {
            while (phones.moveToNext()) {
                phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            }
        }
        phones.close()
        return phoneNumber
    }

}
