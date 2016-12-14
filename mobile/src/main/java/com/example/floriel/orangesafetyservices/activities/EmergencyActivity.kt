package com.example.floriel.orangesafetyservices.activities

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.telephony.SmsManager
import android.view.View
import android.widget.TextView
import com.example.floriel.orangesafetyservices.App
import com.example.floriel.orangesafetyservices.R
import com.example.floriel.orangesafetyservices.helpers.PreferencesManager
import com.example.floriel.orangesafetyservices.objects.Contact
import com.example.floriel.orangesafetyservices.objects.ContactDao
import java.util.*


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class EmergencyActivity : AppCompatActivity() {
    private val mHideHandler = Handler()
    private var mContentView: View? = null
    private val mHidePart2Runnable = Runnable {
        // Delayed removal of status and navigation bar

        // Note that some of these constants are new as of API 16 (Jelly Bean)
        // and API 19 (KitKat). It is safe to use them, as they are inlined
        // at compile-time and do nothing on earlier devices.
        mContentView!!.systemUiVisibility = View.SYSTEM_UI_FLAG_LOW_PROFILE or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    }
    private var mControlsView: View? = null
    private val mShowPart2Runnable = Runnable {
        // Delayed display of UI elements
        val actionBar = supportActionBar
        actionBar?.show()
        mControlsView!!.visibility = View.VISIBLE
    }
    private var mVisible: Boolean = false
    private val mHideRunnable = Runnable { hide() }
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private val mDelayHideTouchListener = View.OnTouchListener { view, motionEvent ->
        if (AUTO_HIDE) {
            delayedHide(AUTO_HIDE_DELAY_MILLIS)
        }
        false
    }

    private lateinit var mContactDao: ContactDao
    private lateinit var mContactList: MutableList<Contact>
    private lateinit var mPref: PreferencesManager
    private lateinit var mHealthInfo: TextView
    private lateinit var mHealthInfoTitle: TextView
    private lateinit var mPersonToContact: TextView
    private lateinit var mPersonToContactTitle: TextView
    private lateinit var mHeartRateTitle: TextView
    private lateinit var mHeartRateDate: TextView
    private lateinit var mHeartRateValue: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_emergency)

        mVisible = true
        mControlsView = findViewById(R.id.fullscreen_content_controls)
        mContentView = findViewById(R.id.fullscreen_content)

        // Set up the user interaction to manually show or hide the system UI.
        mContentView!!.setOnClickListener { toggle() }

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        findViewById(R.id.exit_button).setOnTouchListener(mDelayHideTouchListener)

        val app = this.application as App
        mContactDao = app.getDaoSession().contactDao
        mContactList = mContactDao.queryBuilder()
                .where(ContactDao.Properties.Type.eq(2))
                .orderAsc(ContactDao.Properties.Name)
                .list()
        mPref = PreferencesManager(this.applicationContext)

        mHealthInfo = findViewById(R.id.health_info) as TextView
        mHealthInfoTitle = findViewById(R.id.health_info_title) as TextView
        mHeartRateTitle = findViewById(R.id.heart_rate_title) as TextView
        mHeartRateDate = findViewById(R.id.heart_rate_date) as TextView
        mHeartRateValue = findViewById(R.id.heart_rate_value) as TextView
        mPersonToContactTitle = findViewById(R.id.person_to_contact_title) as TextView
        mPersonToContact = findViewById(R.id.person_to_contact) as TextView

        mHealthInfo.text = mPref.getHealthInfo()
        mHeartRateDate.text = mPref.getDateInfo()
        mHeartRateValue.text = mPref.getHeartRate()
        if (mContactList.isNotEmpty()) {
            val personToContact = """
            |${mContactList[0].name}
            |${mContactList[0].phoneNumber}
            """.trimMargin()
            mPersonToContact.text = personToContact
        }
        mHealthInfoTitle.paintFlags = mHealthInfoTitle.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        mHeartRateTitle.paintFlags = mHeartRateTitle.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        mPersonToContactTitle.paintFlags = mPersonToContactTitle.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        sendSmsContact()
    }

    private fun sendSmsContact() {
        if (mContactList.isNotEmpty()) {
            val message = getMessageToSend()
            val smsManager = SmsManager.getDefault()
            for (contact in mContactList) {
                smsManager.sendMultipartTextMessage(contact.phoneNumber, null, smsManager.divideMessage(message), null, null)
            }
        }
    }

    private fun getMessageToSend(): String {
        var health_issues = ""
        for (issue in mPref.getListHealthIssues()) {
            health_issues += issue + "\n"
        }

        return """
        |${mPref.getCustomMessageEmergency()}
        |Last known location: ${getLocationUser()}
        |Useful health information:
        |${health_issues}
        |${mPref.getHealthInfo()}
        |Message sent with the application Orange Emergency.
        |Try to contact the person and contact the emergency (112).
        """.trimMargin()
    }

    private fun getLocationUser(): String {
        val locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val locationProvider = LocationManager.NETWORK_PROVIDER
        val lastLocation = locationManager.getLastKnownLocation(locationProvider)
        if (lastLocation == null) {
            return "unknown"
        }
        var coordinate = lastLocation.latitude.toString() + "," + lastLocation.longitude

        val addresses = Geocoder(this, Locale.getDefault())
                .getFromLocation(lastLocation.latitude, lastLocation.longitude, 1)
        if (addresses != null) {
            coordinate = addresses[0].getAddressLine(0)
        }

        return coordinate
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100)
    }

    private fun toggle() {
        if (mVisible) {
            hide()
        } else {
            show()
        }
    }

    private fun hide() {
        // Hide UI first
        val actionBar = supportActionBar
        actionBar?.hide()
        mControlsView!!.visibility = View.GONE
        mVisible = false

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable)
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY.toLong())
    }

    @SuppressLint("InlinedApi")
    private fun show() {
        // Show the system bar
        mContentView!!.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        mVisible = true

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable)
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY.toLong())
    }

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private fun delayedHide(delayMillis: Int) {
        mHideHandler.removeCallbacks(mHideRunnable)
        mHideHandler.postDelayed(mHideRunnable, delayMillis.toLong())
    }

    companion object {
        /**
         * Whether or not the system UI should be auto-hidden after
         * [.AUTO_HIDE_DELAY_MILLIS] milliseconds.
         */
        private val AUTO_HIDE = true

        /**
         * If [.AUTO_HIDE] is set, the number of milliseconds to wait after
         * user interaction before hiding the system UI.
         */
        private val AUTO_HIDE_DELAY_MILLIS = 3000

        /**
         * Some older devices needs a small delay between UI widget updates
         * and a change of the status and navigation bar.
         */
        private val UI_ANIMATION_DELAY = 300
    }
}
