package com.example.floriel.orangesafetyservices.feature.EditHealthInformation

import com.example.floriel.orangesafetyservices.helper.PreferencesKey
import com.example.floriel.orangesafetyservices.helper.PreferencesManager

class EditHealthInformationPresenter(private val mEditHealthInformationView: EditHealthInformationContract.View,
                                     private val preferencesManager: PreferencesManager) : EditHealthInformationContract.Presenter {
    override fun saveHealthInformation(information: String) {
        this.preferencesManager.setPreferenceString(PreferencesKey.KEY_HEALTH_INFO, information)
    }

    override fun start() {
        mEditHealthInformationView.showHealthInformation()
    }
    // implement EditHealthInformationContract.Presenter members here
}
