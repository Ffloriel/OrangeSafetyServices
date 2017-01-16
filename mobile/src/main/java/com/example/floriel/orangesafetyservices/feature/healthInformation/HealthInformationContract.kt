package com.example.floriel.orangesafetyservices.feature.healthInformation

import com.google.android.gms.common.api.GoogleApiClient

interface HealthInformationContract {
    interface View {
        fun setPresenter(presenter: HealthInformationPresenter)
        fun showGoogleFitInformation(date: String, heartRate: String)
        fun showHealthInformation(information: String)
    }

    interface Presenter {
        fun start()
        fun loadGoogleFitInformation(apiClient: GoogleApiClient)
    }
}