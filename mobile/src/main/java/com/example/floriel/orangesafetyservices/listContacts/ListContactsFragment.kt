package com.example.floriel.orangesafetyservices.listContacts

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.floriel.orangesafetyservices.R
import com.example.floriel.orangesafetyservices.data.Contact
import com.example.floriel.orangesafetyservices.data.source.ContactsDataSource
import com.example.floriel.orangesafetyservices.recyclers.adapters.SwipeableContactAdapter
import com.h6ah4i.android.widget.advrecyclerview.animator.SwipeDismissItemAnimator
import com.h6ah4i.android.widget.advrecyclerview.swipeable.RecyclerViewSwipeManager
import com.h6ah4i.android.widget.advrecyclerview.touchguard.RecyclerViewTouchActionGuardManager

class ListContactsFragment : Fragment(), ListContactsContract.View {

    private lateinit var mPresenter: ListContactsContract.Presenter
    private lateinit var mAdapter: SwipeableContactAdapter
    private lateinit var mRecycler: RecyclerView
    private lateinit var mLayoutManager: RecyclerView.LayoutManager
    private lateinit var mWrappedAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>
    private lateinit var mRecyclerViewSwipeManager: RecyclerViewSwipeManager
    private lateinit var mRecyclerTouchActionGuardManager: RecyclerViewTouchActionGuardManager

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater!!.inflate(R.layout.fragment_contact, container, false)
        mRecycler = v.findViewById(R.id.safetyList) as RecyclerView
        mLayoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        mRecyclerTouchActionGuardManager = RecyclerViewTouchActionGuardManager()
        mRecyclerTouchActionGuardManager.isEnabled = true
        mRecyclerViewSwipeManager = RecyclerViewSwipeManager()
        mWrappedAdapter = mRecyclerViewSwipeManager.createWrappedAdapter(mAdapter)
        val animator = SwipeDismissItemAnimator()
        animator.supportsChangeAnimations = false
        mRecycler.layoutManager = mLayoutManager
        mRecycler.adapter = mWrappedAdapter
        mRecycler.itemAnimator = animator
        mRecyclerTouchActionGuardManager.attachRecyclerView(mRecycler)
        mRecyclerViewSwipeManager.attachRecyclerView(mRecycler)
        return v
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val presenter = ListContactsPresenter(ContactsDataSource(context), this)
        setPresenter(presenter)
        mAdapter = SwipeableContactAdapter(mutableListOf())
    }

    override fun onResume() {
        super.onResume()
        mPresenter.start()
    }

    override fun setPresenter(presenter: ListContactsContract.Presenter) {
        mPresenter = presenter
    }

    override fun showSafetyContacts(contacts: MutableList<Contact>) {
        mAdapter.newData(contacts)
//        mAdapter = SwipeableContactAdapter(contacts)
//        mWrappedAdapter = mRecyclerViewSwipeManager.createWrappedAdapter(mAdapter)
//        mRecycler.adapter = mWrappedAdapter
        Log.d("Truc", "Hey Jack")
    }

    override fun showEmergencyContact() {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
