package com.example.floriel.orangesafetyservices.feature.informEmergencyContact

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.inform_emergency_contact_frag.*

class InformEmergencyContactFragment : Fragment(), InformEmengencyContactContract.View {

    private lateinit var mPresenter: InformEmengencyContactContract.Presenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        mPresenter.start()
    }

    override fun setPresenter(presenter: InformEmengencyContactContract.Presenter) {
        mPresenter = presenter
    }

    override fun showHealthInformation(information: String) {
        health_information.text = information
    }

    companion object {
        fun newInstance() = InformEmergencyContactFragment()
    }

}
