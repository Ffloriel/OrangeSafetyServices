package com.example.floriel.orangesafetyservices.activities

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.telephony.SmsManager
import android.util.Log
import com.example.floriel.orangesafetyservices.App
import com.example.floriel.orangesafetyservices.NewDisasterNotification
import com.example.floriel.orangesafetyservices.R
import com.example.floriel.orangesafetyservices.objects.ContactDao

class SafetyActivity : AppCompatActivity() {

    val KEY_CUSTOM_MESSAGE = "message_safety_check"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_safety)
        NewDisasterNotification.cancel(this.applicationContext)
        val alertType = intent.getStringExtra(Intent.EXTRA_SUBJECT)
        val defaultSharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        val message = """
        |${defaultSharedPref.getString(KEY_CUSTOM_MESSAGE, "I am Ok")}
        |This message has been sent with Orange Safety App because of the following event: $alertType
        """.trimMargin()
        sendSmsContacts(message)
        when (alertType) {
            "Earthquake" -> {
                Log.d("Truc", alertType)
                finish()
            }
        }
    }

    private fun sendSmsContacts(message: String) {
        val app = this.application as App
        val contactDao = app.getDaoSession().contactDao

        val contacts = contactDao.queryBuilder()
                .where(ContactDao.Properties.Type.eq(1))
                .orderAsc(ContactDao.Properties.Name)
                .list()
        if (contacts.isNotEmpty()) {
            val smsManager = SmsManager.getDefault()
            for (contact in contacts) {
                smsManager.sendMultipartTextMessage(contact.phoneNumber, null, smsManager.divideMessage(message), null, null)
            }
        }
    }
}
