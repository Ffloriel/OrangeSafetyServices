package com.example.floriel.orangesafetyservices.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.floriel.orangesafetyservices.R
import com.example.floriel.orangesafetyservices.helper.PreferencesKey
import com.example.floriel.orangesafetyservices.helper.PreferencesManager
import com.example.floriel.orangesafetyservices.screen.BottomTabsActivity
import com.example.floriel.orangesafetyservices.view.notification.NewDisasterNotification
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.data.Field
import com.google.android.gms.fitness.request.DataReadRequest
import com.olab.smplibrary.SMPLibrary
import kotlinx.android.synthetic.main.fragment_health.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class HealthFragment : BaseFragment() {

    val mPrefManager by lazy { PreferencesManager(this.context) }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_health, container, false)

        val buttonTest = view.findViewById(R.id.button2) as Button
        buttonTest.setOnClickListener { NewDisasterNotification.notify(this.context, "Earthquake", 1) }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        date_info.text = mPrefManager.getDateInfo()
        heartRateBpm.text = mPrefManager.getHeartRate()
        health_info.text = mPrefManager.getHealthInfo()
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
        editButton.setOnClickListener {
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

                        date_info.text = date
                        heartRateBpm.text = bpm
                    }
                }
    }

}

