package com.example.floriel.orangesafetyservices.feature.EditHealthInformation

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.floriel.orangesafetyservices.util.addFragmentToActivity
import com.example.floriel.orangesafetyservices.util.initFragment

class EditHealthInformationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.taskdetail_act)

        // Set up the toolbar.
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        val edithealthinformationFragment = initFragment(R.id.contentFrame) {
            EditHealthInformationFragment.newInstance().apply { addFragmentToActivity(this, R.id.contentFrame) }
        }

        // Create the presenter
        EditHealthInformationPresenter(edithealthinformationFragment)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
