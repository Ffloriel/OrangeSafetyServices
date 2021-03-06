package com.example.floriel.orangesafetyservices.view.adapter

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.example.floriel.orangesafetyservices.R
import com.example.floriel.orangesafetyservices.data.Contact
import com.h6ah4i.android.widget.advrecyclerview.swipeable.SwipeableItemAdapter
import com.h6ah4i.android.widget.advrecyclerview.swipeable.SwipeableItemConstants
import com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultAction
import com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultActionMoveToSwipedDirection
import com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultActionRemoveItem
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractSwipeableItemViewHolder
import com.h6ah4i.android.widget.advrecyclerview.utils.RecyclerViewAdapterUtils
import java.util.*


class SwipeableContactAdapter
(dataProvider: MutableList<Contact>) : RecyclerView.Adapter<SwipeableContactAdapter.ContactViewHolder>(),
        SwipeableItemAdapter<SwipeableContactAdapter.ContactViewHolder> {

    private var mProvider: MutableList<Contact>? = dataProvider
    var mEventListener: EventListener? = null
    private var mItemViewOnClickListener: View.OnClickListener? = null
    private var mSwipeableViewContainerOnClickListener: View.OnClickListener? = null

    fun newData(contacts: MutableList<Contact>) {
        mProvider = contacts
        notifyDataSetChanged()
    }

    private fun onItemViewClick(v: View) {
        mEventListener?.onItemViewClicked(v, true)
    }

    private fun onSwipeableViewContainerClick(v: View) {
        mEventListener?.onItemViewClicked(RecyclerViewAdapterUtils.getParentViewHolderItemView(v)!!, false)
    }

    class ContactViewHolder(v: View) : AbstractSwipeableItemViewHolder(v) {

        val mContainer = v.findViewById(R.id.contact_container) as ConstraintLayout
        val mImage = v.findViewById(R.id.contact_image) as ImageView
        val mName = v.findViewById(R.id.contact_name) as TextView
        val mPhoneNumber = v.findViewById(R.id.contact_phone_number) as TextView

        override fun getSwipeableContainerView(): View? {
            return mContainer
        }

        fun bind(contact: Contact) {
            mName.text = contact.name
            mPhoneNumber.text = contact.phoneNumber
            val names = contact.name.split(" ")
            val initials = names.elementAtOrElse(0, { " " }).capitalize().first().toString() + names.elementAtOrElse(1, { " " }).capitalize().first()
            val color = ColorGenerator.MATERIAL.getColor(names)
            val drawable = TextDrawable.builder().buildRound(initials, color)
            mImage.setImageDrawable(drawable)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ContactViewHolder {
        val v = LayoutInflater.from(parent!!.context)
                .inflate(R.layout.list_contacts_item, parent, false)
        return ContactViewHolder(v)
    }

    override fun onBindViewHolder(holder: ContactViewHolder?, position: Int) {
        val item = mProvider!![position]
        holder!!.bind(item)
        // set swiping properties
        holder.swipeItemHorizontalSlideAmount = 0f
    }

    override fun getItemId(position: Int): Long {
        return Objects.hashCode(mProvider!![position].id).toLong()
    }

    override fun getItemCount(): Int {
        return mProvider!!.size
    }

    override fun onSwipeItem(holder: ContactViewHolder?, position: Int, result: Int): SwipeResultAction? {
        Log.d("ueuee", "onSwipeItem(position = " + position + ", result = " + result + ")")
        when (result) {
            SwipeableItemConstants.RESULT_SWIPED_RIGHT -> return SwipeRightResultAction(this, position)
            SwipeableItemConstants.RESULT_SWIPED_LEFT -> return SwipeRightResultAction(this, position)
            else -> return null
        }
    }

    override fun onGetSwipeReactionType(holder: ContactViewHolder?, position: Int, x: Int, y: Int): Int {
        return SwipeableItemConstants.REACTION_CAN_SWIPE_BOTH_H
    }

    override fun onSetSwipeBackground(holder: ContactViewHolder?, position: Int, type: Int) {}

    private class SwipeLeftResultAction internal constructor(private var mAdapter: SwipeableContactAdapter?,
                                                             private val mPosition: Int) : SwipeResultActionMoveToSwipedDirection() {
        override fun onPerformAction() {
            super.onPerformAction()
        }

        override fun onSlideAnimationEnd() {
            super.onSlideAnimationEnd()
        }

        override fun onCleanUp() {
            super.onCleanUp()
            mAdapter = null
        }
    }

    private class SwipeRightResultAction internal constructor(private var mAdapter: SwipeableContactAdapter?, private val mPosition: Int) : SwipeResultActionRemoveItem() {

        override fun onPerformAction() {
            super.onPerformAction()
            mAdapter!!.mProvider!!.removeAt(mPosition)
            mAdapter!!.notifyItemRemoved(mPosition)
        }

        override fun onSlideAnimationEnd() {
            super.onSlideAnimationEnd()
            //mAdapter!!.mEventListener?.onItemRemoved(mPosition)
        }

        override fun onCleanUp() {
            super.onCleanUp()
            // clear the references
            mAdapter = null
        }
    }


    interface EventListener {
        fun onItemRemoved(position: Int)
        fun onItemViewClicked(v: View, pinned: Boolean)
    }

    init {
        mItemViewOnClickListener = View.OnClickListener { v -> onItemViewClick(v) }
        mSwipeableViewContainerOnClickListener = View.OnClickListener { v -> onSwipeableViewContainerClick(v) }
        setHasStableIds(true)
    }

}
