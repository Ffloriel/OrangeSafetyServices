package com.example.floriel.orangesafetyservices.recyclers

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.floriel.orangesafetyservices.R
import com.example.floriel.orangesafetyservices.activities.BottomTabsActivity
import com.example.floriel.orangesafetyservices.helpers.CursorRecyclerViewAdapter
import com.example.floriel.orangesafetyservices.objects.Contact
import com.example.floriel.orangesafetyservices.objects.ContactDao
import java.util.*

class ContactsAdapter(context: Context, cursor: Cursor, contactDao: ContactDao) : CursorRecyclerViewAdapter<ContactViewHolder>(context, cursor) {

    private var mCursor: Cursor
    private var mContext: Context
    private var mContactDao: ContactDao

    init {
        mCursor = cursor
        mContext = context
        mContactDao = contactDao
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
        viewHolder.itemView.setOnClickListener {

            contact.phoneNumber = this.getPhoneNumber(contact)
            val contactToAdd = Contact(null, contact.name, contact.phoneNumber, 1, Date())
            this.mContactDao.insert(contactToAdd)
            val intent = Intent(this.mContext, BottomTabsActivity::class.java)

            val activity = this.mContext as Activity
            activity.finish()
            //this.mContext.startActivity(intent)
        }
    }

    private fun getPhoneNumber(contact: ContactModel): String {
        val phones = this.mContext.contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY + " = '" + contact.name + "' AND " + ContactsContract.CommonDataKinds.Phone.TYPE + "='" + ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE + "'",
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
