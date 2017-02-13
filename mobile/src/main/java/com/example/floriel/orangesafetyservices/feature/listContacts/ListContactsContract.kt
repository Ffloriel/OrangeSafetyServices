package com.example.floriel.orangesafetyservices.feature.listContacts

import com.example.floriel.orangesafetyservices.data.Contact

interface ListContactsContract {
    interface View {
        fun setPresenter(presenter: Presenter)
        fun showSafetyContacts(contacts: MutableList<Contact>)
        fun showEmergencyContact()
    }

    interface Presenter {
        fun start()
        fun loadSafetyContacts()
        fun getEmergencyContact()
    }
}
