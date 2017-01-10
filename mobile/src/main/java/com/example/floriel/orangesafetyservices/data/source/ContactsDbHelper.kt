package com.example.floriel.orangesafetyservices.data.source

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ContactsDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "Contacts.db"
        val TEXT_TYPE = " TEXT"
        val COMMA_SEP = ","
        val SQL_CREATE_ENTRIES = "CREATE TABLE " + ContactsPersistenceContract.ContactsEntry.TABLE_NAME + "( " +
                ContactsPersistenceContract.ContactsEntry._ID + " PRIMARY KEY," +
                ContactsPersistenceContract.ContactsEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                ContactsPersistenceContract.ContactsEntry.COLUMN_NAME_FULLNAME + TEXT_TYPE + COMMA_SEP +
                ContactsPersistenceContract.ContactsEntry.COLUMN_NAME_PHONE_NUMBER + TEXT_TYPE +
                " )"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}
}
