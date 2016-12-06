package com.example.floriel.orangesafetyservices.recyclers

import android.database.Cursor
import android.provider.ContactsContract


data class ContactModel(var phoneNumber: String, var name: String) {
    companion object {
        fun fromCursor(cursor: Cursor): ContactModel {
            val mNameColIdx = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)
            //val mPhoneNumberIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)

            val contactName = cursor.getString(mNameColIdx)
            val contact = ContactModel("", contactName)
            return contact
        }
    }
}


