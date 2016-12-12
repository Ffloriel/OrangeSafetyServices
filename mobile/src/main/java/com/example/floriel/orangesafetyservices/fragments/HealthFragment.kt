package com.example.floriel.orangesafetyservices.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.floriel.orangesafetyservices.NewDisasterNotification
import com.example.floriel.orangesafetyservices.R
import com.example.floriel.orangesafetyservices.activities.BottomTabsActivity
import com.example.floriel.orangesafetyservices.helpers.PreferencesKey
import com.example.floriel.orangesafetyservices.helpers.PreferencesManager
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.data.Field
import com.google.android.gms.fitness.request.DataReadRequest
import com.olab.smplibrary.SMPLibrary
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class HealthFragment : BaseFragment() {

    val mPrefManager by lazy { PreferencesManager(this.context) }

    val mUsername by lazy { view?.findViewById(R.id.username) as TextView }
    val mPhoneNumber by lazy { view?.findViewById(R.id.user_number) as TextView }
    val mDateInfo by lazy { view?.findViewById(R.id.date_info) as TextView }
    val mHeartRateBpm by lazy { view?.findViewById(R.id.heartRateBpm) as TextView }
    val mEditButton by lazy { view?.findViewById(R.id.editButton) as Button }
    val mHealthInfo by lazy { view?.findViewById(R.id.health_info) as TextView }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_health, container, false)
        val buttonTest = view.findViewById(R.id.button2) as Button
        buttonTest.setOnClickListener { NewDisasterNotification.notify(this.context, "Earthquake", 1) }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mPhoneNumber.text = mPrefManager.getPhoneNumber()
        mUsername.text = mPrefManager.getUsername()
        mDateInfo.text = mPrefManager.getDateInfo()
        mHeartRateBpm.text = mPrefManager.getHeartRate()
        mHealthInfo.text = mPrefManager.getHealthInfo()

        if (mUsername.text.isBlank()) {
            mUsername.text = mPrefManager.getAccountName()
        }
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
        getHeartRate()
        //getContacts()
    }

    private fun getContacts() {
        SMPLibrary.GetAPPContacts(this.context, 10, "OrangeSafetyServices") { responseCode, response ->
            if (responseCode == 200) {
                Log.d("blebbe", response)
            }
        }
    }

    private fun connectSmpLibrary() {
        SMPLibrary.Initialise(this.context, "testid", "testpass")
        SMPLibrary.ShowLoginDialog(this.context) { response ->
            if (response == 200) {
                val username = SMPLibrary.LoggedUserName()
                mPrefManager.setPreferenceString(PreferencesKey.KEY_USERNAME, username)
                mUsername.text = username
            }
        }
    }

    private fun getHeartRate() {
        val cal = Calendar.getInstance()
        val now = Date()
        cal.time = now
        val endTime = cal.timeInMillis
        cal.add(Calendar.WEEK_OF_YEAR, -1)
        val startTime = cal.timeInMillis

        val dataRequest: DataReadRequest = DataReadRequest.Builder()
                .read(DataType.TYPE_HEART_RATE_BPM)
                .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                .build()

        val activity = this.activity as BottomTabsActivity
        if (activity.mClient == null) {
            return
        }

        Fitness.HistoryApi.readData(activity.mClient, dataRequest)
                .setResultCallback {
                    val lastHeartRate = it.dataSets.first().dataPoints.lastOrNull()
                    if (lastHeartRate !== null) {
                        val dateFormat: SimpleDateFormat = SimpleDateFormat("EEEE dd MMMM", Locale.getDefault())
                        val date = dateFormat.format(Date(lastHeartRate.getStartTime(TimeUnit.MILLISECONDS)))
                        val bpm = lastHeartRate.getValue(Field.FIELD_BPM).toString() + " bpm"

                        mPrefManager.setPreferenceString(PreferencesKey.KEY_DATE_INFO, date)
                        mPrefManager.setPreferenceString(PreferencesKey.KEY_HEART_RATE, bpm)

                        mDateInfo.text = date
                        mHeartRateBpm.text = bpm
                    }
                }
    }

}

