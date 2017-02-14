package com.example.floriel.orangesafetyservices.feature.informEmergencyContact

import android.content.Context
import android.location.Geocoder
import android.location.LocationManager
import android.telephony.SmsManager
import com.example.floriel.orangesafetyservices.helper.PreferencesManager
import java.util.*

class InformEmergencyContactPresenter(private val mInformEmergencyContactView: InformEmengencyContactContract.View,
                                      private val preferencesManager: PreferencesManager,
                                      private val activity: InformEmergencyContactActivity) : InformEmengencyContactContract.Presenter {
    override fun sendSmsContact(message: String) {
        //val message = getMessageToSend()
        val smsManager = SmsManager.getDefault()
        val contact = this.preferencesManager.getContactEmergency()
        smsManager.sendMultipartTextMessage(contact.phoneNumber, null, smsManager.divideMessage(message), null, null)
    }

    override fun start() {
        val message = getMessageToSend()
        sendSmsContact(message)
        this.mInformEmergencyContactView.showHealthInformation(message)
    }

    private fun getMessageToSend(): String {
        var health_issues = ""
        for (issue in this.preferencesManager.getListHealthIssues()) {
            health_issues += issue + "\n"
        }

        return """
        |${this.preferencesManager.getCustomMessageEmergency()}
        |Last known location: ${getLocationUser()}
        |Useful health information:
        |${health_issues}
        |${this.preferencesManager.getHealthInfo()}
        |Message sent with the application Orange Emergency.
        |Try to contact the person and contact the emergency (112).
        """.trimMargin()
    }

    private fun getLocationUser(): String {
        val locationManager = this.activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val locationProvider = LocationManager.NETWORK_PROVIDER
        val lastLocation = locationManager.getLastKnownLocation(locationProvider) ?: return "unknown"
        var coordinate = lastLocation.latitude.toString() + "," + lastLocation.longitude

        val addresses = Geocoder(this.activity, Locale.getDefault())
                .getFromLocation(lastLocation.latitude, lastLocation.longitude, 1)
        if (addresses != null) {
            coordinate = addresses[0].getAddressLine(0)
        }

        return coordinate
    }

}
