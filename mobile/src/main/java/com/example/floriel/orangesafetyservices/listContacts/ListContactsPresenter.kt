package com.example.floriel.orangesafetyservices.listContacts

import android.util.Log
import com.example.floriel.orangesafetyservices.data.Contact
import com.example.floriel.orangesafetyservices.data.source.ContactsDataSource

class ListContactsPresenter(var mContactsDataSource: ContactsDataSource,
                            var mListContactsView: ListContactsContract.View) : ListContactsContract.Presenter {
    override fun start() {
        loadSafetyContacts()
    }

    override fun loadSafetyContacts() {
        mContactsDataSource.getContacts(object : ContactsDataSource.LoadContactsCallback {
            override fun onContactsLoaded(contacts: MutableList<Contact>) {
                Log.d("Truc", contacts.toString())
                mListContactsView.showSafetyContacts(contacts)
            }

        })
    }

}