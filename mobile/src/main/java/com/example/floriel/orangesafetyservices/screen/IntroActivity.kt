package com.example.floriel.orangesafetyservices.screen

import android.accounts.AccountManager
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import com.example.floriel.orangesafetyservices.R
import com.example.floriel.orangesafetyservices.helper.PreferencesKey
import com.example.floriel.orangesafetyservices.helper.PreferencesManager
import com.example.floriel.orangesafetyservices.util.PermissionUtil
import com.github.paolorotolo.appintro.AppIntro2
import com.github.paolorotolo.appintro.AppIntro2Fragment
import com.google.android.gms.common.AccountPicker
import com.olab.smplibrary.SMPLibrary


class IntroActivity : AppIntro2() {

    private var nSlide = 1
    private val REQUEST_CODE_PICKER = 56
    val mPrefManager by lazy { PreferencesManager(this.applicationContext) }
    val mTitles by lazy { resources.getStringArray(R.array.intro_title) }
    val mDescriptions by lazy { resources.getStringArray(R.array.intro_description) }
    val mDrawables = arrayOf(R.drawable.abc_btn_radio_material,
            R.drawable.ic_perm_device_information_black_128dp,
            R.drawable.ic_logo,
            R.drawable.abc_btn_radio_material,
            R.drawable.fab_icon_emergency_24dp,
            R.drawable.ic_done_all_black_24dp
    )
    val mColors = arrayOf(Color.parseColor("#FF5722"),
            Color.parseColor("#1976D2"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        for (i in 0..mTitles.size - 1) {
            addSlide(AppIntro2Fragment.newInstance(mTitles[i], mDescriptions[i], mDrawables[i], mColors[i % 2]))
        }

        showSkipButton(false)
        showStatusBar(true)
        this.progressButtonEnabled = true

        askForPermissions(PermissionUtil.permissions, 2)
    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
    }

    override fun onSlideChanged(oldFragment: Fragment?, newFragment: Fragment?) {
        super.onSlideChanged(oldFragment, newFragment)
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        startActivity(Intent(this.applicationContext, LoadActivity::class.java))
        finish()
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
                mPrefManager.setPreferenceString(PreferencesKey.KEY_USERNAME, username)
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
            mPrefManager.setPreferenceString(PreferencesKey.KEY_ACCOUNT_NAME, accountName)
        }
    }

}
