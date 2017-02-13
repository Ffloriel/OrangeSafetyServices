package com.example.floriel.orangesafetyservices.feature.healthInformation

import android.support.v4.app.Fragment
import com.example.floriel.orangesafetyservices.App
import com.example.floriel.orangesafetyservices.helper.PreferencesKey
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.Scope
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.data.Field
import com.google.android.gms.fitness.request.DataReadRequest
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class HealthInformationPresenter(val mHealthInformationView: HealthInformationContract.View,
                                 val fragment: Fragment) : HealthInformationContract.Presenter {

    private var apiClient: GoogleApiClient? = null
    private val preferencesManager = (this.fragment.activity.application as App).preferenceManager

    companion object {
        val FORMAT_DATE_HEART_RATE = SimpleDateFormat("EEEE dd MMMM HH:mm:ss")
    }

    override fun start() {
        val date = this.preferencesManager.getDateInfo()
        val heartRate = this.preferencesManager.getHeartRate()
        this.mHealthInformationView.showGoogleFitInformation(date, heartRate)
        if (this.apiClient !== null) {
            this.loadGoogleFitInformation(this.apiClient!!)
        } else {
            this.connectGoogleClient()
        }
        val healthInfo = this.preferencesManager.getHealthInfo()
        this.mHealthInformationView.showHealthInformation(healthInfo)
    }

    override fun loadGoogleFitInformation(apiClient: GoogleApiClient) {
        val cal = Calendar.getInstance()
        val now = Date()
        cal.time = now
        val endTime = cal.timeInMillis
        cal.add(Calendar.WEEK_OF_YEAR, -1)
        val startTime = cal.timeInMillis

        val dataRequest: DataReadRequest = DataReadRequest.Builder()
                .read(DataType.TYPE_HEART_RATE_BPM)
                .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                .build()
        Fitness.HistoryApi.readData(apiClient, dataRequest)
                .setResultCallback {
                    val lastHeartRate = it.dataSets.first().dataPoints.lastOrNull()
                    if (lastHeartRate !== null) {

                        val date = FORMAT_DATE_HEART_RATE.format(Date(lastHeartRate.getStartTime(TimeUnit.MILLISECONDS)))
                        val bpm = lastHeartRate.getValue(Field.FIELD_BPM).toString() + "bpm"
                        this.saveHeartRate(date, bpm)
                        this.mHealthInformationView.showGoogleFitInformation(date, bpm)
                    }
                }

    }

    private fun saveHeartRate(date: String, value: String) {
        this.preferencesManager.setPreferenceString(PreferencesKey.KEY_DATE_INFO, date)
        this.preferencesManager.setPreferenceString(PreferencesKey.KEY_HEART_RATE, value)
    }

    private fun connectGoogleClient() {
        val builder = GoogleApiClient.Builder(this.fragment.context)
                .enableAutoManage(this.fragment.activity) {

                }
                .addApi(Fitness.HISTORY_API)
                .addScope(Scope(Scopes.FITNESS_BODY_READ))
        if (this.preferencesManager.pref.contains(PreferencesKey.KEY_ACCOUNT_NAME)) {
            builder.setAccountName(this.preferencesManager.getAccountName())
        }
        this.apiClient = builder.build()
    }

}
