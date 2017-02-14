package com.example.floriel.orangesafetyservices.feature.informEmergencyContact

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.floriel.orangesafetyservices.R

class InformEmergencyContactFragment : Fragment(), InformEmengencyContactContract.View {

    private lateinit var mPresenter: InformEmengencyContactContract.Presenter
    private lateinit var mHealthInformation: TextView

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.inform_emergency_contact_frag, container, false)
        mHealthInformation = view!!.findViewById(R.id.health_information) as TextView
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        mPresenter.start()
    }

    override fun setPresenter(presenter: InformEmengencyContactContract.Presenter) {
        mPresenter = presenter
    }

    override fun showHealthInformation(information: String) {
        mHealthInformation.text = information
    }

    companion object {
        fun newInstance() = InformEmergencyContactFragment()
    }

}
