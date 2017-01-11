package com.example.floriel.orangesafetyservices.screen

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.floriel.orangesafetyservices.App
import com.example.floriel.orangesafetyservices.R
import com.example.floriel.orangesafetyservices.feature.listContacts.ListContactsFragment
import com.example.floriel.orangesafetyservices.feature.settings.SettingsFragment
import com.example.floriel.orangesafetyservices.fragments.HealthFragment
import com.ncapdevi.fragnav.FragNavController
import kotlinx.android.synthetic.main.bottom_navigation_act.*

class BottomNavigationActivity : AppCompatActivity() {

    private lateinit var mNavController: FragNavController
    val INDEX_HOME = FragNavController.TAB1
    val INDEX_CONTACTS = FragNavController.TAB2
    val INDEX_SETTINGS = FragNavController.TAB3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val preferencesManager = (this.application as App).preferenceManager
        if (preferencesManager.getFirstTimeLaunch()) {
            startActivity(Intent(this.applicationContext, IntroActivity::class.java))
            preferencesManager.setFirstTimeLaunch(false)
        }

        setContentView(R.layout.bottom_navigation_act)
        // Set up the toolbar.
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(false)
            setDisplayShowHomeEnabled(false)
        }
        actionBar?.setTitle(R.string.app_name)

        val tabsFragments = arrayListOf(HealthFragment.newInstance(0),
                ListContactsFragment(),
                SettingsFragment.newInstance())
        mNavController = FragNavController(supportFragmentManager, R.id.content_frame, tabsFragments)

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_menu_home -> {
                    mNavController.switchTab(INDEX_HOME)
                }
                R.id.action_menu_contacts -> {
                    mNavController.switchTab(INDEX_CONTACTS)
                }
                R.id.action_menu_settings -> {
                    mNavController.switchTab(INDEX_SETTINGS)
                }
                else -> false
            }
            true
        }
    }

    override fun onBackPressed() {
        if (mNavController.currentStack.size > 1) {
            mNavController.pop()
        } else {
            super.onBackPressed()
        }
    }

}
