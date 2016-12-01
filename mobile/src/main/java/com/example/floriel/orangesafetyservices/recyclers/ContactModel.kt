package com.example.floriel.orangesafetyservices.recyclers

import android.content.ContentUris
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract

data class ContactModel(var profilePic: Uri, var name: String) {
    companion object {
        fun fromCursor(cursor: Cursor): ContactModel {
            val mNameColIdx = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)
            val mIdColIdx = cursor.getColumnIndex(ContactsContract.Contacts._ID)

            val contactName = cursor.getString(mNameColIdx)
            val contactId = cursor.getLong(mIdColIdx)
            val profilePic = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId)

            val contact = ContactModel(profilePic, contactName)
            return contact
        }
    }
}


