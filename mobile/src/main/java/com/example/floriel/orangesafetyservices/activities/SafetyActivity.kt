package com.example.floriel.orangesafetyservices.activities

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.telephony.SmsManager
import com.example.floriel.orangesafetyservices.R
import com.example.floriel.orangesafetyservices.data.Contact
import com.example.floriel.orangesafetyservices.data.source.ContactsDataSource
import com.example.floriel.orangesafetyservices.view.notification.NewDisasterNotification

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
        finish()
    }

    private fun sendSmsContacts(message: String) {
        val contactsDataSource = ContactsDataSource.getInstance(this.applicationContext)
        contactsDataSource.getContacts(object : ContactsDataSource.LoadContactsCallback {
            override fun onContactsLoaded(contacts: MutableList<Contact>) {
                if (contacts.isNotEmpty()) {
                    val smsManager = SmsManager.getDefault()
                    for ((phoneNumber) in contacts) {
                        smsManager.sendMultipartTextMessage(phoneNumber, null, smsManager.divideMessage(message), null, null)
                    }
                }
            }
        })
    }
}
