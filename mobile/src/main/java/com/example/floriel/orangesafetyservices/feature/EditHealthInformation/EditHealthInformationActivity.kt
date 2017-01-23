package com.example.floriel.orangesafetyservices.feature.editHealthInformation

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.floriel.orangesafetyservices.App
import com.example.floriel.orangesafetyservices.R
import com.example.floriel.orangesafetyservices.util.addFragmentToActivity
import com.example.floriel.orangesafetyservices.util.initFragment
import kotlinx.android.synthetic.main.edit_health_information_act.*

class EditHealthInformationActivity : AppCompatActivity() {

    private lateinit var mEditHealthInformationPresenter: EditHealthInformationPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.edit_health_information_act)

        // Set up the toolbar.
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        actionBar?.setTitle(R.string.edit_health_information)

        val edithealthinformationFragment = initFragment(R.id.content_frame) {
            EditHealthInformationFragment.newInstance().apply { addFragmentToActivity(this, R.id.content_frame) }
        }

        // Create the presenter
        val preferenceManager = (this.application as App).preferenceManager
        mEditHealthInformationPresenter = EditHealthInformationPresenter(edithealthinformationFragment, preferenceManager)
        edithealthinformationFragment.setPresenter(mEditHealthInformationPresenter)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
