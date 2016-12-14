package com.example.floriel.orangesafetyservices.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.floriel.orangesafetyservices.R
import com.example.floriel.orangesafetyservices.helpers.PreferencesKey
import com.example.floriel.orangesafetyservices.helpers.PreferencesManager
import kotlinx.android.synthetic.main.fragment_edit_health.*

class EditHealthFragment : BaseFragment() {

    val mPrefManager by lazy { PreferencesManager(this.context) }

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
        return inflater!!.inflate(R.layout.fragment_edit_health, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fabSave.setOnClickListener {
            mPrefManager.setPreferenceString(PreferencesKey.KEY_HEALTH_INFO, editText.text.toString())
            mFragmentNavigation.pushFragment(HealthFragment.newInstance(0))
        }
    }

    override fun onResume() {
        super.onResume()
        editText.setText(mPrefManager.getHealthInfo())
    }

}

