package com.example.floriel.orangesafetyservices.activities

import android.Manifest
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import com.example.floriel.orangesafetyservices.R
import com.example.floriel.orangesafetyservices.helpers.PreferencesManager
import com.github.paolorotolo.appintro.AppIntro2
import com.github.paolorotolo.appintro.AppIntro2Fragment
import com.olab.smplibrary.SMPLibrary

class IntroActivity : AppIntro2() {

    private var nSlide = 1
    private lateinit var mPrefManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPrefManager = PreferencesManager(this.applicationContext)

        val title1 = "Welcome"
        val description1 = "Orange Safety Services is here if you need to contact emergency, or inform your close friends you are safe during a dangerous situation"
        val title2 = "Permissions"
        val description2 = "The application requires access to your contact to inform them you are safe, and your health information to help in case of emergency."
        val title3 = "Login"
        val description3 = "The application uses SMP to bring advanced features such as managing your contacts automatically."
        addSlide(AppIntro2Fragment.newInstance(title1, description1, R.drawable.abc_btn_radio_material, Color.parseColor("#FF5722")))
        addSlide(AppIntro2Fragment.newInstance(title2, description2, R.drawable.ic_perm_device_information_black_128dp, Color.parseColor("#1976D2")))
        addSlide(AppIntro2Fragment.newInstance(title3, description3, R.drawable.ic_logo, Color.parseColor("#FF5722")))
        addSlide(AppIntro2Fragment.newInstance(title2, description2, R.drawable.abc_btn_radio_material, Color.parseColor("#1976D2")))

        showSkipButton(false)
        showStatusBar(false)
        this.progressButtonEnabled = true

        askForPermissions(arrayOf(Manifest.permission.BODY_SENSORS, Manifest.permission.SEND_SMS, Manifest.permission.READ_CONTACTS), 2)
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        finish()
    }

    override fun onNextPressed() {
        nSlide += 1
        if (nSlide == 3) {
            connectSmpLibrary()
        }
    }

    private fun connectSmpLibrary() {
        SMPLibrary.Initialise(this.applicationContext, "testid", "testpass")
        SMPLibrary.ShowLoginDialog(this) { response ->
            if (response == 200) {
                val username = SMPLibrary.LoggedUserName()
                mPrefManager.setPreferenceString(mPrefManager.KEY_USERNAME, username)
            }
        }
    }

}
