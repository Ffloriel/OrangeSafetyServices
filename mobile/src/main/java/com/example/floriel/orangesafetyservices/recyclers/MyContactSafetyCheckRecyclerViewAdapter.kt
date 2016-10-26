package com.example.floriel.orangesafetyservices.recyclers

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.example.floriel.orangesafetyservices.R
import com.example.floriel.orangesafetyservices.listeners.OnListFragmentInteractionListener
import com.example.floriel.orangesafetyservices.objects.AddressBookContact

class MyContactSafetyCheckRecyclerViewAdapter(private val mValues: List<AddressBookContact>,
                                              private val mListener: OnListFragmentInteractionListener?) : RecyclerView.Adapter<MyContactSafetyCheckRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_contactsafetycheck, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = mValues[position]
        holder.mIdView.text = mValues[position].name
        holder.mContentView.text = mValues[position].phones.toString()

        holder.mView.setOnClickListener {
            mListener?.onListFragmentInteraction(holder.mItem as AddressBookContact)
        }
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdView: TextView
        val mContentView: TextView
        var mItem: AddressBookContact? = null

        init {
            mIdView = mView.findViewById(R.id.id) as TextView
            mContentView = mView.findViewById(R.id.content) as TextView
        }

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}
