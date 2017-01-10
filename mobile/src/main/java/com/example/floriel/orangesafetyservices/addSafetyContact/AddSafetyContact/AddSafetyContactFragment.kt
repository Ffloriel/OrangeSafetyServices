package com.example.floriel.orangesafetyservices.addSafetyContact.AddSafetyContact

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class AddSafetyContactFragment : Fragment(), AddSafetyContactContract.View {
    private lateinit var mPresenter: AddSafetyContactContract.Presenter

    override fun onResume() {
        super.onResume()
        //mPresenter.start()
    }

    fun setPresenter(presenter: AddSafetyContactContract.Presenter) {
        mPresenter = presenter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return null//inflater.inflate(R.layout.addtask_frag, container, false)
    }

    companion object {
        fun newInstance() = AddSafetyContactFragment()
    }


}
