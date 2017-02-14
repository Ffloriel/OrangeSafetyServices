package com.example.floriel.orangesafetyservices.feature.informEmergencyContact

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.floriel.orangesafetyservices.App
import com.example.floriel.orangesafetyservices.R
import com.example.floriel.orangesafetyservices.util.addFragmentToActivity
import com.example.floriel.orangesafetyservices.util.initFragment
import kotlinx.android.synthetic.main.inform_emergency_contact_act.*

class InformEmergencyContactActivity : AppCompatActivity() {

    private lateinit var mInformEmergencyContactPresenter: InformEmergencyContactPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inform_emergency_contact_act)

        // Set up the toolbar.
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        actionBar?.setTitle(R.string.inform_emergency_contact)

        val informEmergencyContactFragment = initFragment(R.id.content_frame) {
            InformEmergencyContactFragment.newInstance().apply { addFragmentToActivity(this, R.id.content_frame) }
        }

        val preferenceManager = (this.application as App).preferenceManager
        mInformEmergencyContactPresenter = InformEmergencyContactPresenter(informEmergencyContactFragment,
                preferenceManager, this)
        informEmergencyContactFragment.setPresenter(mInformEmergencyContactPresenter)
    }
}
