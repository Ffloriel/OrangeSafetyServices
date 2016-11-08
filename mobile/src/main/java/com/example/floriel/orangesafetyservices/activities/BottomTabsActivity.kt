package com.example.floriel.orangesafetyservices.activities

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.example.floriel.orangesafetyservices.NewDisasterNotification
import com.example.floriel.orangesafetyservices.R
import com.example.floriel.orangesafetyservices.fragments.HealthFragment
import com.example.floriel.orangesafetyservices.fragments.SettingsFragment
import com.ncapdevi.fragnav.FragNavController
import com.roughike.bottombar.BottomBar
import java.util.*

class BottomTabsActivity : AppCompatActivity() {

    private val mBottomBar: BottomBar? = null
    private var mNavController: FragNavController? = null
    private val REQUEST_PERMISSIONS_REQUEST_CODE = 34

    private val INDEX_HEALTH = FragNavController.TAB1
    private val INUEX_CONTACTS = FragNavController.TAB2
    private val INDEX_SETTINGS = FragNavController.TAB3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_tabs)

        val fragments = ArrayList<Fragment>(3)

        fragments.add(HealthFragment.newInstance(0))
        fragments.add(SettingsFragment.newInstance(0))
        fragments.add(SettingsFragment.newInstance(0))

        mNavController = FragNavController(supportFragmentManager, R.id.container, fragments)

        val bottomBar = findViewById(R.id.bottomBar) as BottomBar
        bottomBar.setOnTabSelectListener { tabId ->
            when (tabId) {
                R.id.tab_information -> mNavController!!.switchTab(INDEX_HEALTH)
                R.id.tab_contact -> mNavController!!.switchTab(INUEX_CONTACTS)
                R.id.tab_setting -> mNavController!!.switchTab(INDEX_SETTINGS)
            }
        }

        val fabButton = findViewById(R.id.fab) as FloatingActionButton
        fabButton.setOnClickListener { NewDisasterNotification.notify(applicationContext, "Earthquake", 1) }
    }
}
