package com.example.floriel.orangesafetyservices.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.floriel.orangesafetyservices.R
import com.example.floriel.orangesafetyservices.helpers.PreferencesManager
import com.example.floriel.orangesafetyservices.objects.ConnectionFitFailedListener
import com.example.floriel.orangesafetyservices.objects.GoogleFitConnectionCallbacks
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.Scope
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.data.Field
import com.google.android.gms.fitness.request.DataReadRequest
import com.olab.smplibrary.SMPLibrary
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class HealthFragment : BaseFragment() {

    private lateinit var mUsername: TextView
    private lateinit var mPhoneNumber: TextView
    private lateinit var mDateInfo: TextView
    private lateinit var mHeartRateBpm: TextView
    private lateinit var mEditButton: Button
    private lateinit var mHealthInfo: TextView

    private lateinit var mClient: GoogleApiClient
    private lateinit var mPrefManager: PreferencesManager

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_health, container, false)

        mUsername = view.findViewById(R.id.username) as TextView
        mPhoneNumber = view.findViewById(R.id.user_number) as TextView
        mDateInfo = view.findViewById(R.id.date_info) as TextView
        mHeartRateBpm = view.findViewById(R.id.heartRateBpm) as TextView
        mEditButton = view.findViewById(R.id.editButton) as Button
        mHealthInfo = view.findViewById(R.id.health_info) as TextView

        mPhoneNumber.text = mPrefManager.getPhoneNumber()
        mUsername.text = mPrefManager.getUsername()
        mDateInfo.text = mPrefManager.getDateInfo()
        mHeartRateBpm.text = mPrefManager.getHeartRate()
        mHealthInfo.text = mPrefManager.getHealthInfo()

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPrefManager = PreferencesManager(this.context)
    }

    companion object {

        fun newInstance(instance: Int): HealthFragment {
            val fragment = HealthFragment()
            val args = Bundle()
            args.putInt(BaseFragment.ARGS_INSTANCE, instance)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onStart() {
        super.onStart()
        mEditButton.setOnClickListener {
            if (mFragmentNavigation != null) {
                mFragmentNavigation.pushFragment(EditHealthFragment.newInstance(0))
            }
        }
        //connectSmpLibrary()
        //getHeartRate()
        //getContacts()
    }

    private fun getContacts() {
        SMPLibrary.GetAPPContacts(this.context, 10, "OrangeSafetyServices") { responseCode, response ->
            if (responseCode == 200) {
                Log.d("blebbe", response)
            }
        }
    }

    override fun onPause() {
        super.onPause()
//        mClient.stopAutoManage(this.activity)
//        mClient.disconnect()
    }

    override fun onDestroy() {
        super.onDestroy()
        mClient.stopAutoManage(this.activity)
        mClient.disconnect()
    }

    private fun connectGoogleClient() {
        var connectionCallbacks = GoogleFitConnectionCallbacks()
        var connectionFailedListener = ConnectionFitFailedListener()
        mClient = GoogleApiClient.Builder(this.context)
                .enableAutoManage(this.activity, connectionFailedListener)
                .addApi(Fitness.HISTORY_API)
                .addScope(Scope(Scopes.FITNESS_BODY_READ))
                .addConnectionCallbacks(connectionCallbacks)
                .build()
        mClient.connect()
    }

    private fun connectSmpLibrary() {
        SMPLibrary.Initialise(this.context, "testid", "testpass")
        SMPLibrary.ShowLoginDialog(this.context) { response ->
            if (response == 200) {
                val username = SMPLibrary.LoggedUserName()
                mPrefManager.setPreferenceString(mPrefManager.KEY_USERNAME, username)
                mUsername.text = username
            }
        }
    }

    private fun getHeartRate() {
        if (!checkPermissions()) {
            requestPermissions()
            return
        }

        this.connectGoogleClient()

        val cal = Calendar.getInstance()
        val now = Date()
        cal.time = now
        val endTime = cal.timeInMillis
        cal.add(Calendar.WEEK_OF_YEAR, -1)
        val startTime = cal.timeInMillis

        var dataRequest: DataReadRequest = DataReadRequest.Builder()
                .read(DataType.TYPE_HEART_RATE_BPM)
                .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                .build()
        Fitness.HistoryApi.readData(mClient, dataRequest)
                .setResultCallback {
                    val lastHeartRate = it.dataSets.first().dataPoints.lastOrNull()
                    if (lastHeartRate !== null) {
                        val dateFormat: SimpleDateFormat = SimpleDateFormat("EEEE dd MMMM", Locale.getDefault())
                        val date = dateFormat.format(Date(lastHeartRate.getStartTime(TimeUnit.MILLISECONDS)))
                        val bpm = lastHeartRate.getValue(Field.FIELD_BPM).toString() + " bpm"

                        mPrefManager.setPreferenceString(mPrefManager.KEY_DATE_INFO, date)
                        mPrefManager.setPreferenceString(mPrefManager.KEY_HEART_RATE, bpm)

                        mDateInfo.text = date
                        mHeartRateBpm.text = bpm
                    }

                }

    }

    private fun checkPermissions(): Boolean {
        val permissionState = ActivityCompat.checkSelfPermission(this.context,
                Manifest.permission.BODY_SENSORS)
        return permissionState == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        val shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(this.activity,
                Manifest.permission.BODY_SENSORS)

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {

        } else {
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            ActivityCompat.requestPermissions(this@HealthFragment.activity,
                    arrayOf(Manifest.permission.BODY_SENSORS),
                    34)
        }
    }

}

