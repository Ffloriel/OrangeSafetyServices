package com.example.floriel.orangesafetyservices.screen

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.floriel.orangesafetyservices.R
import com.olab.smplibrary.SMPLibrary

class LoadActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load)
        finish()
    }

    private fun connectSmpLibrary() {
        SMPLibrary.Initialise(this.applicationContext, "testid", "testpass")
        SMPLibrary.ShowLoginDialog(this) { response ->
            if (response == 200) {
                val username = SMPLibrary.LoggedUserName()
                //mPrefManager.setPreferenceString(PreferencesKey.KEY_USERNAME, username)
            }
        }
    }

//    private fun getPrivateContacts() {
//        if (!SMPLibrary.IsLoggedIn()) this.connectSmpLibrary()
//        SMPLibrary.GetPrivateContacts(this.applicationContext, 15, { responseCode, data ->
//            val contacts = transformJson(data)
//            for (contact in contacts) {
//                val name = ""
//                val phoneNumber = ""
//                val contactToAdd = Contact(null, name, phoneNumber, 1, Date())
//                this.mContactDao.insert(contactToAdd)
//            }
//        })
//    }

}
