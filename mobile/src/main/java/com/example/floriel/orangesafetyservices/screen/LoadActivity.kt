package com.example.floriel.orangesafetyservices.screen

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.floriel.orangesafetyservices.R
import com.example.floriel.orangesafetyservices.model.PrivateContact
import com.google.gson.Gson
import com.olab.smplibrary.SMPLibrary

class LoadActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load)
        SMPLibrary.Initialise(this.applicationContext, "testid", "testpass")
        getPrivateContacts()
        //finish()
    }

    private fun connectSmpLibrary() {
        SMPLibrary.ShowLoginDialog(this) { response ->
            if (response == 200) {
                getPrivateContacts()
            }
        }
    }

    private fun getPrivateContacts() {
        if (!SMPLibrary.IsLoggedIn()) {
            this.connectSmpLibrary()
            return
        }
        val gson = Gson()
        SMPLibrary.GetPrivateContacts(this.applicationContext, 15, { responseCode, data ->
            val contacts = gson.fromJson(data, Array<PrivateContact>::class.java)
            Log.d("yo dude!", contacts.size.toString())
            for (contact in contacts) {
                Log.d("yo dude!", contact.namesPhones[0].name)
                val name = ""
                val phoneNumber = ""
                //val contactToAdd = Contact(null, name, phoneNumber, 1, Date())
                //this.mContactDao.insert(contactToAdd)
            }
        })
    }

}
