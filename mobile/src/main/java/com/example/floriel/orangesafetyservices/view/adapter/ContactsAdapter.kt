package com.example.floriel.orangesafetyservices.view.adapter

import android.app.Activity
import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.floriel.orangesafetyservices.R
import com.example.floriel.orangesafetyservices.data.Contact
import com.example.floriel.orangesafetyservices.data.source.ContactsDataSource
import com.example.floriel.orangesafetyservices.view.holder.ContactModel
import com.example.floriel.orangesafetyservices.view.holder.ContactViewHolder

class ContactsAdapter(context: Context, cursor: Cursor) : CursorRecyclerViewAdapter<ContactViewHolder>(context, cursor) {

    private var mCursor: Cursor = cursor
    private var mContext: Context = context

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
        viewHolder.itemView.setOnClickListener {
            contact.phoneNumber = this.getPhoneNumber(contact)
            // Sqlite
            val contactsDataSource = ContactsDataSource.getInstance(mContext)
            contactsDataSource.saveContact(Contact(contact.name, contact.phoneNumber))

            val activity = this.mContext as Activity
            activity.finish()
        }
    }

    private fun getPhoneNumber(contact: ContactModel): String {
        val phones = this.mContext.contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY + " = '" + contact.name + "' AND " +
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
