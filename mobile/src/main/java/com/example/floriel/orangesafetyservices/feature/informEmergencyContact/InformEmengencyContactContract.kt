package com.example.floriel.orangesafetyservices.feature.informEmergencyContact

interface InformEmengencyContactContract {

    interface View {
        fun setPresenter(presenter: InformEmengencyContactContract.Presenter)
        fun showHealthInformation(information: String)
    }

    interface Presenter {
        fun start()
        fun sendSmsContact()
    }
}
