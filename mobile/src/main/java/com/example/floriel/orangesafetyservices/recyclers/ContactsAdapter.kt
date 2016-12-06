package com.example.floriel.orangesafetyservices.recyclers

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.floriel.orangesafetyservices.R
import com.example.floriel.orangesafetyservices.activities.BottomTabsActivity
import com.example.floriel.orangesafetyservices.helpers.CursorRecyclerViewAdapter

class ContactsAdapter(context: Context, cursor: Cursor) : CursorRecyclerViewAdapter<ContactViewHolder>(context, cursor) {

    private var mCursor: Cursor
    private var mContext: Context

    init {
        mCursor = cursor
        mContext = context
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
            val intent = Intent(this.mContext, BottomTabsActivity::class.java)
            Log.d("Truc", contact.name)
            this.mContext.startActivity(intent)
        }
    }

}
