package com.example.floriel.orangesafetyservices.data.source

import android.content.ContentValues
import android.content.Context
import com.example.floriel.orangesafetyservices.data.Contact
import com.example.floriel.orangesafetyservices.data.source.ContactsPersistenceContract.ContactsEntry

class ContactsDataSource(context: Context) {

    interface LoadContactsCallback {
        fun onContactsLoaded(contacts: MutableList<Contact>)
    }

    private val mDbHelper: ContactsDbHelper = ContactsDbHelper(context)

    fun getContacts(callback: LoadContactsCallback) {
        var contacts: MutableList<Contact> = arrayListOf()
        val db = mDbHelper.readableDatabase
        val projection = arrayOf(ContactsEntry.COLUMN_NAME_ENTRY_ID,
                ContactsEntry.COLUMN_NAME_FULLNAME,
                ContactsEntry.COLUMN_NAME_PHONE_NUMBER)
        val c = db.query(ContactsEntry.TABLE_NAME, projection, null, null, null, null, null)
        if (c != null && c.count > 0) {
            while (c.moveToNext()) {
                val itemId = c.getString(c.getColumnIndexOrThrow(ContactsEntry.COLUMN_NAME_ENTRY_ID))
                val name = c.getString(c.getColumnIndexOrThrow(ContactsEntry.COLUMN_NAME_FULLNAME))
                val phoneNumber = c.getString(c.getColumnIndexOrThrow(ContactsEntry.COLUMN_NAME_PHONE_NUMBER))
                val contact = Contact(name, phoneNumber, itemId)
                contacts.add(contact)
            }
        }
        c?.close()
        db.close()

        if (contacts.isNotEmpty()) {
            callback.onContactsLoaded(contacts)
        }
    }

    fun saveContact(contact: Contact) {
        val db = mDbHelper.writableDatabase
        var values = ContentValues()
        values.put(ContactsEntry.COLUMN_NAME_ENTRY_ID, contact.id)
        values.put(ContactsEntry.COLUMN_NAME_FULLNAME, contact.name)
        values.put(ContactsEntry.COLUMN_NAME_PHONE_NUMBER, contact.phoneNumber)
        db.insert(ContactsEntry.TABLE_NAME, null, values)
        db.close()
    }

    fun deleteContact(contactId: String) {
        val db = mDbHelper.writableDatabase
        val selection = ContactsEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?"
        val selectionArgs = arrayOf(contactId)
        db.delete(ContactsEntry.TABLE_NAME, selection, selectionArgs)
        db.close()
    }

    companion object {
        private var INSTANCE: ContactsDataSource? = null
        fun getInstance(context: Context): ContactsDataSource {
            if (INSTANCE == null) {
                INSTANCE = ContactsDataSource(context)
            }
            return INSTANCE as ContactsDataSource
        }
    }
}
