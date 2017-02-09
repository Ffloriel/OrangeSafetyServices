package com.example.floriel.orangesafetyservices.view.adapter

import android.app.Activity
import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.example.floriel.orangesafetyservices.App
import com.example.floriel.orangesafetyservices.R
import com.example.floriel.orangesafetyservices.data.Contact
import com.example.floriel.orangesafetyservices.data.source.ContactsDataSource
import com.example.floriel.orangesafetyservices.util.Constant

class ContactAdapter(var contactsList: MutableList<Contact>, val context: Context, val contactType: Int) : RecyclerView.Adapter<ContactAdapter.ViewHolder>() {

    val contactsDataSource = ContactsDataSource.getInstance(context)

    override fun onBindViewHolder(holder: ContactAdapter.ViewHolder?, position: Int) {
        val contact = contactsList[position]
        holder!!.mName.text = contact.name
        holder.mImage.setImageDrawable(this.getDrawableName(contact))
        holder.mPhoneNumber.text = contact.phoneNumber
        holder.mContainer.setOnClickListener {
            when (contactType) {
                Constant.SAFETY_CONTACT_SELECTION -> {
                    contactsDataSource.saveContact(Contact(contact.name, contact.phoneNumber))
                }
                Constant.EMERGENCY_CONTACT_SELECTION -> {
                    ((this.context as Activity).application as App).preferenceManager
                            .setContactEmergency(contact.name, contact.phoneNumber)
                }
            }
            (this.context as Activity).finish()
        }
    }

    override fun getItemCount(): Int {
        return contactsList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ContactAdapter.ViewHolder {
        val v = LayoutInflater.from(parent!!.context).inflate(R.layout.list_contacts_item, parent, false)
        val viewHolder = ViewHolder(v)
        return viewHolder
    }

    private fun getDrawableName(contact: Contact): TextDrawable {
        val names = contact.name.split(" ")
        val initials = names.elementAtOrElse(0, { " " }).capitalize().first().toString() + names.elementAtOrElse(1, { " " }).capitalize().first()
        val color = ColorGenerator.MATERIAL.getColor(names)
        val drawable = TextDrawable.builder().buildRound(initials, color)
        return drawable
    }

    fun updateContactList(contactsList: MutableList<Contact>) {
        this.contactsList = contactsList
        notifyDataSetChanged()
    }

    companion object
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mName: TextView = itemView.findViewById(R.id.contact_name) as TextView
        var mImage: ImageView = itemView.findViewById(R.id.contact_image) as ImageView
        var mPhoneNumber: TextView = itemView.findViewById(R.id.contact_phone_number) as TextView
        var mContainer: ConstraintLayout = itemView.findViewById(R.id.contact_container) as ConstraintLayout
    }

}
