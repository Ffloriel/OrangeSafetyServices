package com.example.floriel.orangesafetyservices.feature.EditHealthInformation

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class EditHealthInformationFragment : Fragment(), EditHealthInformationContract.View {
    private lateinit var mPresenter: EditHealthInformationContract.Presenter

    override fun onResume() {
        super.onResume()
        mPresenter.start()
    }

    override fun setPresenter(presenter: EditHealthInformationContract.Presenter) {
        mPresenter = presenter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.addtask_frag, container, false)
    }

    companion object {
        fun newInstance() = EditHealthInformationFragment()
    }


}
