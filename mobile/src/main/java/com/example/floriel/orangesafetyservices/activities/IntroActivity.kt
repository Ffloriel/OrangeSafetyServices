package com.example.floriel.orangesafetyservices.activities

import android.Manifest
import android.accounts.AccountManager
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import com.example.floriel.orangesafetyservices.R
import com.example.floriel.orangesafetyservices.helpers.PreferencesManager
import com.github.paolorotolo.appintro.AppIntro2
import com.github.paolorotolo.appintro.AppIntro2Fragment
import com.google.android.gms.common.AccountPicker
import com.olab.smplibrary.SMPLibrary


class IntroActivity : AppIntro2() {

    private var nSlide = 1
    private val REQUEST_CODE_PICKER = 56
    private lateinit var mPrefManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPrefManager = PreferencesManager(this.applicationContext)

        val titles = arrayOf("Welcome",
                "Permissions",
                "Login",
                "Health Data",
                "Ready")
        val descriptions = arrayOf("Orange Safety Services is here if you need to contact emergency, or inform your close friends you are safe during a dangerous situation",
                "The application requires access to your contact to inform them you are safe, and your health information to help in case of emergency.",
                "The application uses SMP to bring advanced features such as managing your contacts automatically.",
                "The application uses health information such as the heart rate from your Google Account",
                "The application is now configured."
        )

        addSlide(AppIntro2Fragment.newInstance(titles[0], descriptions[0], R.drawable.abc_btn_radio_material, Color.parseColor("#FF5722")))
        addSlide(AppIntro2Fragment.newInstance(titles[1], descriptions[1], R.drawable.ic_perm_device_information_black_128dp, Color.parseColor("#1976D2")))
        addSlide(AppIntro2Fragment.newInstance(titles[2], descriptions[2], R.drawable.ic_logo, Color.parseColor("#FF5722")))
        addSlide(AppIntro2Fragment.newInstance(titles[3], descriptions[3], R.drawable.abc_btn_radio_material, Color.parseColor("#1976D2")))
        addSlide(AppIntro2Fragment.newInstance(titles[4], descriptions[4], R.drawable.ic_done_all_black_24dp, Color.parseColor("#FF5722")))

        showSkipButton(false)
        showStatusBar(false)
        this.progressButtonEnabled = true

        askForPermissions(arrayOf(Manifest.permission.BODY_SENSORS, Manifest.permission.SEND_SMS, Manifest.permission.READ_CONTACTS), 2)
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        finish()
        //startActivity(Intent(this,BottomTabsActivity::class.java))
    }

    override fun onNextPressed() {
        nSlide += 1
        when (nSlide) {
            3 -> connectSmpLibrary()
            4 -> selectGoogleAccount()
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

    private fun selectGoogleAccount() {
        val intent = AccountPicker.newChooseAccountIntent(null, null, arrayOf("com.google"),
                false, null, null, null, null)
        startActivityForResult(intent, REQUEST_CODE_PICKER)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === REQUEST_CODE_PICKER && resultCode === Activity.RESULT_OK) {
            val accountName = data!!.getStringExtra(AccountManager.KEY_ACCOUNT_NAME)
            mPrefManager.setPreferenceString(mPrefManager.KEY_ACCOUNT_NAME, accountName)
        }
    }

}
