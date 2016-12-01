package com.example.floriel.orangesafetyservices.recyclers

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.floriel.orangesafetyservices.R

class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var mImage: ImageView
    private var mName: TextView
    private var mBoundContact: ContactModel? = null

    init {
        mImage = itemView.findViewById(R.id.image_profile) as ImageView
        mName = itemView.findViewById(R.id.id) as TextView
    }

    fun bind(contact: ContactModel) {
        mBoundContact = contact
        mName.text = contact.name
    }

}
