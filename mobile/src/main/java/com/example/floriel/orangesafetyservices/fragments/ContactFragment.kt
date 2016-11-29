package com.example.floriel.orangesafetyservices.fragments

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.floriel.orangesafetyservices.App
import com.example.floriel.orangesafetyservices.R
import com.example.floriel.orangesafetyservices.activities.SearchContactActivity


class ContactFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_contact, container, false)

        val fabAddContact = view.findViewById(R.id.fabAddContact) as FloatingActionButton
        fabAddContact.setOnClickListener { startActivity(Intent(this.context, SearchContactActivity::class.java)) }

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val daoSession = (this.activity.application as App).getDaoSession()
        var contactDao = daoSession.getContactDao()
    }

    companion object {

        fun newInstance(instance: Int): ContactFragment {
            val fragment = ContactFragment()
            val args = Bundle()
            args.putInt(BaseFragment.ARGS_INSTANCE, instance)
            fragment.arguments = args
            return fragment
        }
    }

}


