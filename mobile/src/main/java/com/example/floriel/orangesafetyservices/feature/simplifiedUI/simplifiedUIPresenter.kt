package com.example.floriel.orangesafetyservices.feature.simplifiedUI

import android.content.Intent
import com.example.floriel.orangesafetyservices.App
import com.example.floriel.orangesafetyservices.feature.informEmergencyContact.InformEmergencyContactActivity

class SimplifiedUIPresenter(private val mSimplifiedUIView: SimplifiedUIContract.View, private val activity: SimplifiedUIActivity) : SimplifiedUIContract.Presenter {

    override fun start() {
        val emergencyContact = (this.activity.application as App).preferenceManager.getContactEmergency()
        this.mSimplifiedUIView.showEmergencyContactButton(emergencyContact.name)
    }

    override fun launchEmergencyActivity() {
        this.activity.startActivity(Intent(this.activity.applicationContext, InformEmergencyContactActivity::class.java))
    }

}
