package com.example.floriel.orangesafetyservices.view.holder

import android.database.Cursor
import android.provider.ContactsContract


data class ContactModel(var phoneNumber: String, var name: String) {
    companion object {
        fun fromCursor(cursor: Cursor): ContactModel {
            val mNameColIdx = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)

            val contactName = cursor.getString(mNameColIdx)
            val contact = ContactModel("", contactName)
            return contact
        }
    }
}


