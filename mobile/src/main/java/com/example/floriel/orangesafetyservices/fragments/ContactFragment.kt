package com.example.floriel.orangesafetyservices.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.floriel.orangesafetyservices.App
import com.example.floriel.orangesafetyservices.R
import com.example.floriel.orangesafetyservices.objects.Contact
import com.example.floriel.orangesafetyservices.objects.ContactDao
import com.example.floriel.orangesafetyservices.recyclers.ContactRAdapter

class ContactFragment : BaseFragment() {

    private lateinit var mRecyclerViewEmergency: RecyclerView
    private lateinit var mRecyclerViewSafety: RecyclerView
    private lateinit var mContactsEmergency: MutableList<Contact>
    private lateinit var mContactsSafety: MutableList<Contact>
    private lateinit var mAdapterEmergency: ContactRAdapter
    private lateinit var mAdapterSafety: ContactRAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_contact, container, false)

        mRecyclerViewEmergency = view.findViewById(R.id.emergencyList) as RecyclerView
        mRecyclerViewSafety = view.findViewById(R.id.safetyList) as RecyclerView

        mRecyclerViewEmergency.layoutManager = LinearLayoutManager(this.activity)
        mAdapterEmergency = ContactRAdapter(mContactsEmergency)
        mRecyclerViewEmergency.adapter = mAdapterEmergency

        mRecyclerViewSafety.layoutManager = LinearLayoutManager(this.activity)
        mAdapterSafety = ContactRAdapter(mContactsSafety)
        mRecyclerViewSafety.adapter = mAdapterSafety

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val daoSession = (this.activity.application as App).getDaoSession()
        val contactDao = daoSession.contactDao
        mContactsEmergency = contactDao.queryBuilder()
                .where(ContactDao.Properties.Type.eq(2))
                .orderAsc(ContactDao.Properties.Name)
                .list()
        mContactsSafety = contactDao.queryBuilder()
                .where(ContactDao.Properties.Type.eq(1))
                .orderAsc(ContactDao.Properties.Name)
                .list()
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


