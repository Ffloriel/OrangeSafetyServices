package com.example.floriel.orangesafetyservices.feature.editHealthInformation

interface EditHealthInformationContract {

    interface View {
        fun setPresenter(presenter: EditHealthInformationContract.Presenter)
        fun showHealthInformation()
    }

    interface Presenter {
        fun start()
        fun saveHealthInformation(information: String)
    }
}
