package com.example.floriel.orangesafetyservices.feature.settings

import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat
import com.example.floriel.orangesafetyservices.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle, rootKey: String) {
        addPreferencesFromResource(R.xml.preferences)
    }

    companion object {

        fun newInstance(instance: Int): SettingsFragment {
            val fragment = SettingsFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

}
