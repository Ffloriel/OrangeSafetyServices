package com.example.floriel.orangesafetyservices.addSafetyContact.AddSafetyContact

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class AddSafetyContactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //setContentView(R.layout.taskdetail_act)

        // Set up the toolbar.
        //setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

//        val addsafetycontactFragment = initFragment(R.id.contentFrame) {
//            AddSafetyContactFragment.newInstance().apply { addFragmentToActivity(this, R.id.contentFrame) }
//        }

        // Create the presenter
        //AddSafetyContactPresenter(addsafetycontactFragment)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
