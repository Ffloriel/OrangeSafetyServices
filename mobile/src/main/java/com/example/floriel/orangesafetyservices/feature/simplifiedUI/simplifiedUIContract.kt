package com.example.floriel.orangesafetyservices.feature.simplifiedUI

interface SimplifiedUIContract {

    interface View {
        fun setPresenter(presenter: SimplifiedUIContract.Presenter)
        fun showEmergencyContactButton(name: String)
    }

    interface Presenter {
        fun start()
        fun launchEmergencyActivity()
    }
}
