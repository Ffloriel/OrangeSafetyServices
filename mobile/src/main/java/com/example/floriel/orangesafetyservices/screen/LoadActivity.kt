package com.example.floriel.orangesafetyservices.screen

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.floriel.orangesafetyservices.R
import com.example.floriel.orangesafetyservices.data.Contact
import com.example.floriel.orangesafetyservices.data.source.ContactsDataSource
import com.example.floriel.orangesafetyservices.model.PrivateContact
import com.google.gson.Gson
import com.olab.smplibrary.SMPLibrary

class LoadActivity : AppCompatActivity() {

    val contactsDataSource by lazy { ContactsDataSource.getInstance(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load)
        SMPLibrary.Initialise(this.applicationContext, "testid", "testpass")
        getPrivateContacts()
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
        SMPLibrary.GetPrivateContactsByDeviceID(this.applicationContext, 15, 8, { responseCode, data ->
            var contacts = gson.fromJson(data, Array<PrivateContact>::class.java)
            val contactList = contacts.distinctBy { it.phoneNumbers[0] }
            for ((contactId, name, phoneNumbers) in contactList) {
                if (!contactsDataSource.isInDatabase(name, phoneNumbers[0])) {
                    contactsDataSource.saveContact(Contact(name, phoneNumbers[0]))
                }
            }
            this.finish()
        })
    }

}
