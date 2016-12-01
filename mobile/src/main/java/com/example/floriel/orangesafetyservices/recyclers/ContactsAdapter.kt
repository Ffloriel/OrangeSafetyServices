package com.example.floriel.orangesafetyservices.recyclers

import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.floriel.orangesafetyservices.R
import com.example.floriel.orangesafetyservices.helpers.CursorRecyclerViewAdapter

class ContactsAdapter(context: Context, cursor: Cursor) : CursorRecyclerViewAdapter<ContactViewHolder>(context, cursor) {

    private var mCursor: Cursor
    private val mNameColIdx: Int
    private val mIdColIdx: Int

    init {
        mCursor = cursor
        mNameColIdx = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)
        mIdColIdx = cursor.getColumnIndex(ContactsContract.Contacts._ID)
    }

    override fun getItemCount(): Int {
        return mCursor.count
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ContactViewHolder {
        val itemView = LayoutInflater.from(parent!!.context)
                .inflate(R.layout.fragment_contactsafetycheck, parent, false)
        return ContactViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: ContactViewHolder?, cursor: Cursor?) {
        val contact = ContactModel.fromCursor(cursor!!)
        viewHolder!!.bind(contact)
    }

}
