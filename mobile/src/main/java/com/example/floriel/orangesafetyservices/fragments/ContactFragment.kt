package com.example.floriel.orangesafetyservices.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.floriel.orangesafetyservices.App
import com.example.floriel.orangesafetyservices.R
import com.example.floriel.orangesafetyservices.objects.Contact
import com.example.floriel.orangesafetyservices.objects.ContactDao
import com.example.floriel.orangesafetyservices.recyclers.ContactRAdapter
import com.example.floriel.orangesafetyservices.recyclers.helper.SimpleItemTouchHelperCallback

class ContactFragment : BaseFragment(), OnStartDragListener {

    val mRecyclerViewEmergency by lazy { view?.findViewById(R.id.emergencyList) as RecyclerView }
    val mRecyclerViewSafety by lazy { view?.findViewById(R.id.safetyList) as RecyclerView }
    val mContactDao: ContactDao by lazy { (this.activity.application as App).getDaoSession().contactDao }

    private lateinit var mContactsEmergency: MutableList<Contact>
    private lateinit var mContactsSafety: MutableList<Contact>
    private lateinit var mAdapterEmergency: ContactRAdapter
    private lateinit var mAdapterSafety: ContactRAdapter
    private lateinit var mItemTouchHelper: ItemTouchHelper

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_contact, container, false)
    }

    override fun onStart() {
        super.onStart()

        mContactsEmergency = mContactDao.queryBuilder()
                .where(ContactDao.Properties.Type.eq(2))
                .orderAsc(ContactDao.Properties.Name)
                .list()
        mContactsSafety = mContactDao.queryBuilder()
                .where(ContactDao.Properties.Type.eq(1))
                .orderAsc(ContactDao.Properties.Name)
                .list()

        mRecyclerViewEmergency.layoutManager = LinearLayoutManager(this.activity)
        mAdapterEmergency = ContactRAdapter(mContactsEmergency, mContactDao)
        mRecyclerViewEmergency.adapter = mAdapterEmergency

        mRecyclerViewSafety.layoutManager = LinearLayoutManager(this.activity)
        mAdapterSafety = ContactRAdapter(mContactsSafety, mContactDao)
        mRecyclerViewSafety.adapter = mAdapterSafety

        val callback = SimpleItemTouchHelperCallback(mAdapterSafety)
        mItemTouchHelper = ItemTouchHelper(callback)
        mItemTouchHelper.attachToRecyclerView(mRecyclerViewSafety)
    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {

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

interface OnStartDragListener {
    fun onStartDrag(viewHolder: RecyclerView.ViewHolder)
}


