package com.example.floriel.orangesafetyservices.view.holder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.example.floriel.orangesafetyservices.R

class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var mImage: ImageView
    private var mName: TextView
    private var mPhoneNumber: TextView
    private var mBoundContact: ContactModel? = null

    init {
        mImage = itemView.findViewById(R.id.image_profile) as ImageView
        mName = itemView.findViewById(R.id.id) as TextView
        mPhoneNumber = itemView.findViewById(R.id.content) as TextView
    }

    fun bind(contact: ContactModel) {
        mBoundContact = contact
        mName.text = contact.name
        mPhoneNumber.text = contact.phoneNumber
        val names = contact.name.split(" ")
        val initials = names.elementAtOrElse(0, { " " }).capitalize().first().toString() + names.elementAtOrElse(1, { " " }).capitalize().first()
        val color = ColorGenerator.MATERIAL.getColor(names)
        val drawable = TextDrawable.builder().buildRound(initials, color)
        mImage.setImageDrawable(drawable)
    }

}
