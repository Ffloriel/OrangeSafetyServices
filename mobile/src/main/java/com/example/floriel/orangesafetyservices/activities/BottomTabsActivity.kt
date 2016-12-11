package com.example.floriel.orangesafetyservices.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.multidex.MultiDex
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.example.floriel.orangesafetyservices.App
import com.example.floriel.orangesafetyservices.R
import com.example.floriel.orangesafetyservices.fragments.BaseFragment
import com.example.floriel.orangesafetyservices.fragments.ContactFragment
import com.example.floriel.orangesafetyservices.fragments.HealthFragment
import com.example.floriel.orangesafetyservices.fragments.SettingsFragment
import com.example.floriel.orangesafetyservices.helpers.PreferencesManager
import com.example.floriel.orangesafetyservices.objects.ConnectionFitFailedListener
import com.example.floriel.orangesafetyservices.objects.Contact
import com.example.floriel.orangesafetyservices.objects.GoogleFitConnectionCallbacks
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.Scope
import com.google.android.gms.fitness.Fitness
import com.ncapdevi.fragnav.FragNavController
import com.roughike.bottombar.BottomBar
import java.util.*


class BottomTabsActivity : AppCompatActivity(), BaseFragment.FragmentNavigation {

    val mBottomBar by lazy { findViewById(R.id.bottomBar) as BottomBar }
    val mPrefManager by lazy { PreferencesManager(this.applicationContext) }

    private lateinit var mNavController: FragNavController
    var mClient: GoogleApiClient? = null

    val INDEX_HEALTH = FragNavController.TAB1
    val INDEX_CONTACTS = FragNavController.TAB2
    val INDEX_SETTINGS = FragNavController.TAB3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (mPrefManager.getFirstTimeLaunch()) {
            startActivity(Intent(this.baseContext, IntroActivity::class.java))
            mPrefManager.setFirstTimeLaunch(false)

            val app = this.application as App
            val contactDao = app.getDaoSession().contactDao
            contactDao.insert(Contact(null, "Floriel Fedry", "+33622545688", 2, Date()))
        } else {
            this.connectGoogleClient()
        }
        createActivity()
    }

    private fun createActivity() {
        MultiDex.install(this)
        setContentView(R.layout.activity_bottom_tabs)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        val fabButton = findViewById(R.id.fab) as FloatingActionButton

        val fragments = ArrayList<Fragment>(3)
        fragments.add(HealthFragment.newInstance(0))
        fragments.add(ContactFragment.newInstance(0))
        fragments.add(SettingsFragment.newInstance(0))

        mNavController = FragNavController(supportFragmentManager, R.id.container, fragments)

        mBottomBar.setOnTabSelectListener { tabId ->
            when (tabId) {
                R.id.tab_information -> {
                    mNavController.switchTab(INDEX_HEALTH)
                    fabButton.setImageResource(android.R.drawable.ic_dialog_alert)
                    fabButton.setOnClickListener { startActivity(Intent(this, EmergencyActivity::class.java)) }
                }
                R.id.tab_contact -> {
                    mNavController.switchTab(INDEX_CONTACTS)
                    fabButton.setImageResource(android.R.drawable.ic_input_add)
                    fabButton.setOnClickListener { startActivity(Intent(this, SearchContactActivity::class.java)) }
                }
                R.id.tab_setting -> mNavController.switchTab(INDEX_SETTINGS)
            }
        }

        fabButton.setOnClickListener { startActivity(Intent(this, EmergencyActivity::class.java)) }
    }

    override fun onBackPressed() {
        if (mNavController.currentStack.size > 1) {
            mNavController.pop()
        } else {
            super.onBackPressed()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        if (mClient != null) {
            mClient!!.stopAutoManage(this)
            mClient!!.disconnect()
        }
    }

    override fun pushFragment(fragment: Fragment?) {
        mNavController.push(fragment)
    }

    private fun connectGoogleClient() {
        val connectionCallbacks = GoogleFitConnectionCallbacks()
        val connectionFailedListener = ConnectionFitFailedListener()
        mClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this, connectionFailedListener)
                .addApi(Fitness.HISTORY_API)
                .addScope(Scope(Scopes.FITNESS_BODY_READ))
                .addConnectionCallbacks(connectionCallbacks)
                .setAccountName(mPrefManager.getAccountName())
                .build()
        mClient!!.connect()
    }

}
