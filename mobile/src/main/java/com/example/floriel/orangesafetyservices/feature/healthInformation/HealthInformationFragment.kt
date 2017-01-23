package com.example.floriel.orangesafetyservices.feature.healthInformation

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.floriel.orangesafetyservices.R
import com.example.floriel.orangesafetyservices.activities.EmergencyActivity
import com.example.floriel.orangesafetyservices.feature.editHealthInformation.EditHealthInformationActivity
import kotlinx.android.synthetic.main.health_information_frag.*

class HealthInformationFragment : Fragment(), HealthInformationContract.View {

    private lateinit var mPresenter: HealthInformationPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setPresenter(HealthInformationPresenter(this, this))
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater!!.inflate(R.layout.health_information_frag, container, false)
        return v
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button_edit_health_information.setOnClickListener {
            this.startActivity(Intent(this.context, EditHealthInformationActivity::class.java))
        }
        fab_contact_emergency.setOnClickListener {
            this.startActivity(Intent(this.context, EmergencyActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        mPresenter.start()
    }

    override fun setPresenter(presenter: HealthInformationPresenter) {
        mPresenter = presenter
    }

    override fun showGoogleFitInformation(date: String, heartRate: String) {
        if (date_information !== null) {
            date_information.text = date
            heart_rate_bpm.text = heartRate
        }
    }

    override fun showHealthInformation(information: String) {
        health_information.text = information
    }

}