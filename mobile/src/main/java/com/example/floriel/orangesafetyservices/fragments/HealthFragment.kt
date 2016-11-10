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
import com.example.floriel.orangesafetyservices.objects.ConnectionFitFailedListener
import com.example.floriel.orangesafetyservices.objects.GoogleFitConnectionCallbacks
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.Scope
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.data.Field
import com.google.android.gms.fitness.request.DataReadRequest
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class HealthFragment : BaseFragment() {

    private var mDateInfo: TextView? = null
    private var mHeartRateBpm: TextView? = null
    private var mEditButton: Button? = null


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_health, container, false)

        mDateInfo = view.findViewById(R.id.date_info) as TextView
        mHeartRateBpm = view.findViewById(R.id.heartRateBpm) as TextView
        mEditButton = view.findViewById(R.id.editButton) as Button
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getHeartRate()
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
        mEditButton!!.setOnClickListener {
            Log.d("tha", "truc")
            if (mFragmentNavigation != null) {
                mFragmentNavigation.pushFragment(ContactFragment.newInstance(0))
                Log.d("tha", "eeee")
            }
        }
    }

    private fun getHeartRate() {
        if (!checkPermissions()) {
            requestPermissions()
            return
        }

        var connectionCallbacks = GoogleFitConnectionCallbacks()
        var connectionFailedListener = ConnectionFitFailedListener()
        var mClient = GoogleApiClient.Builder(this.context)
                .enableAutoManage(this.activity, connectionFailedListener)
                .addApi(Fitness.HISTORY_API)
                .addScope(Scope(Scopes.FITNESS_BODY_READ))
                .addConnectionCallbacks(connectionCallbacks)
                .build()
        mClient.connect()

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
                    val lastHeartRate = it.dataSets.first().dataPoints.last()
                    val dateFormat: SimpleDateFormat = SimpleDateFormat("EEEE dd MMMM", Locale.getDefault())
                    val date = dateFormat.format(Date(lastHeartRate.getStartTime(TimeUnit.MILLISECONDS)))
                    mDateInfo!!.text = date
                    mHeartRateBpm!!.text = lastHeartRate.getValue(Field.FIELD_BPM).toString() + " bpm"


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

