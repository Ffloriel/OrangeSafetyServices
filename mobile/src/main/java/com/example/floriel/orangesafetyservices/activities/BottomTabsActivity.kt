package com.example.floriel.orangesafetyservices.activities

import android.content.Intent
import android.os.Bundle
import android.support.multidex.MultiDex
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import com.example.floriel.orangesafetyservices.R
import com.example.floriel.orangesafetyservices.fragments.BaseFragment
import com.example.floriel.orangesafetyservices.fragments.ContactFragment
import com.example.floriel.orangesafetyservices.fragments.HealthFragment
import com.example.floriel.orangesafetyservices.fragments.SettingsFragment
import com.example.floriel.orangesafetyservices.helpers.PreferencesHelper
import com.example.floriel.orangesafetyservices.helpers.PreferencesManager
import com.ncapdevi.fragnav.FragNavController
import com.olab.smplibrary.SMPLibrary
import com.roughike.bottombar.BottomBar
import java.util.*


class BottomTabsActivity : AppCompatActivity(), BaseFragment.FragmentNavigation {

    private var mBottomBar: BottomBar? = null
    private lateinit var mNavController: FragNavController
    private lateinit var mPrefManager:PreferencesManager

    private val INDEX_HEALTH = FragNavController.TAB1
    private val INDEX_CONTACTS = FragNavController.TAB2
    private val INDEX_SETTINGS = FragNavController.TAB3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPrefManager = PreferencesManager(this.applicationContext)
        if (mPrefManager.getFirstTimeLaunch()) {
            startActivity(Intent(this.baseContext, IntroActivity::class.java))
            mPrefManager.setFirstTimeLaunch(false)
        }

        MultiDex.install(this)
        setContentView(R.layout.activity_bottom_tabs)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val fragments = ArrayList<Fragment>(3)
        fragments.add(HealthFragment.newInstance(0))
        fragments.add(ContactFragment.newInstance(0))
        fragments.add(SettingsFragment.newInstance(0))

        mNavController = FragNavController(supportFragmentManager, R.id.container, fragments)

        mBottomBar = findViewById(R.id.bottomBar) as BottomBar
        mBottomBar!!.setOnTabSelectListener { tabId ->
            when (tabId) {
                R.id.tab_information -> mNavController.switchTab(INDEX_HEALTH)
                R.id.tab_contact -> mNavController.switchTab(INDEX_CONTACTS)
                R.id.tab_setting -> mNavController.switchTab(INDEX_SETTINGS)
            }
        }


//        val fabButton = findViewById(R.id.fab) as FloatingActionButton
//        fabButton.setOnClickListener { NewDisasterNotification.notify(applicationContext, "Earthquake", 1) }
    }

    override fun onBackPressed() {
        if (mNavController.currentStack.size > 1) {
            mNavController.pop()
        } else {
            super.onBackPressed()
        }

    }

    override fun pushFragment(fragment: Fragment?) {
        mNavController!!.push(fragment)
    }
}
