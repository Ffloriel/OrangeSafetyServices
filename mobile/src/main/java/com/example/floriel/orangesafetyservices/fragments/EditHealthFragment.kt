package com.example.floriel.orangesafetyservices.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.floriel.orangesafetyservices.R
import com.example.floriel.orangesafetyservices.helpers.PreferencesKey

class EditHealthFragment : BaseFragment() {

    val mHealthInfo by lazy { view?.findViewById(R.id.editText) as EditText }
    val mFabButton by lazy { view?.findViewById(R.id.fabSave) as FloatingActionButton }
    val mDataApp: SharedPreferences by lazy { this.context.getSharedPreferences(PreferencesKey.PREFS_DATA_NAME, 0) }

    companion object {

        fun newInstance(instance: Int): EditHealthFragment {
            val fragment = EditHealthFragment()
            val args = Bundle()
            args.putInt(BaseFragment.ARGS_INSTANCE, instance)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_edit_health, container, false)

        mFabButton.setOnClickListener {
            mDataApp.edit()
                    .putString(PreferencesKey.KEY_HEALTH_INFO, mHealthInfo.text.toString())
                    .apply()
            mFragmentNavigation.pushFragment(HealthFragment.newInstance(0))
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        mHealthInfo.setText(mDataApp.getString(PreferencesKey.KEY_HEALTH_INFO, ""))
    }

}

