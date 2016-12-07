package com.example.floriel.orangesafetyservices.recyclers

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.example.floriel.orangesafetyservices.R
import com.example.floriel.orangesafetyservices.objects.Contact

class ContactRAdapter(private val mContacts: MutableList<Contact>) : RecyclerView.Adapter<ContactRAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactRAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_contactsafetycheck, parent, false)
        val vh = ContactRAdapter.ViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: ContactRAdapter.ViewHolder, position: Int) {
        val contact = mContacts[position]
        holder.bind(contact)
    }

    override fun getItemCount(): Int {
        return mContacts.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var mImage: ImageView
        private var mName: TextView
        private var mPhoneNumber: TextView
        private var mBoundContact: Contact? = null

        init {
            mImage = itemView.findViewById(R.id.image_profile) as ImageView
            mName = itemView.findViewById(R.id.id) as TextView
            mPhoneNumber = itemView.findViewById(R.id.content) as TextView
        }

        fun bind(contact: Contact) {
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

}
