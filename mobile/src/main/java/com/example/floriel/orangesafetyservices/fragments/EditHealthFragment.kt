package com.example.floriel.orangesafetyservices.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.floriel.orangesafetyservices.R
import com.example.floriel.orangesafetyservices.helpers.PreferencesHelper

class EditHealthFragment : BaseFragment() {

    private lateinit var mHealthInfo:EditText
    private lateinit var mFabButton:FloatingActionButton
    private lateinit var mDataApp:SharedPreferences

    companion object {

        fun newInstance(instance: Int): EditHealthFragment {
            val fragment = EditHealthFragment()
            val args = Bundle()
            args.putInt(BaseFragment.ARGS_INSTANCE, instance)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDataApp = this.context.getSharedPreferences(PreferencesHelper.PREFS_DATA_NAME, 0)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_edit_health, container, false)
        mHealthInfo = view.findViewById(R.id.editText) as EditText
        mFabButton = view.findViewById(R.id.fabSave) as FloatingActionButton

        mFabButton.setOnClickListener {
            val editor = mDataApp.edit()
            editor.putString(PreferencesHelper.KEY_HEALTH_INFO, mHealthInfo.text.toString())
            editor.commit()
            mFragmentNavigation.pushFragment(HealthFragment.newInstance(0))
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        if (mDataApp.contains(PreferencesHelper.KEY_HEALTH_INFO)) {
            mHealthInfo.setText(mDataApp.getString(PreferencesHelper.KEY_HEALTH_INFO, ""))
        }
    }


}

