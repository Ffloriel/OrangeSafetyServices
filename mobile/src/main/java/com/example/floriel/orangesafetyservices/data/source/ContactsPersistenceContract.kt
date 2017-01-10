package com.example.floriel.orangesafetyservices.data.source

import android.provider.BaseColumns

object ContactsPersistenceContract {
    class ContactsEntry : BaseColumns {
        companion object {
            val TABLE_NAME = "contact"
            val _ID = BaseColumns._ID
            val COLUMN_NAME_ENTRY_ID = "entryid"
            val COLUMN_NAME_FULLNAME = "fullname"
            val COLUMN_NAME_PHONE_NUMBER = "phoneNumber"
        }
    }

}