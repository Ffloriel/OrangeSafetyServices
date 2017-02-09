package com.example.floriel.orangesafetyservices.helper

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.example.floriel.orangesafetyservices.data.Contact

object PreferencesKey {
    val PREFS_DATA_NAME = "DataAppFile"
    val KEY_ACCOUNT_NAME = "accountName"
    val KEY_IS_FIRST_TIME_LAUNCH = "isFirstTimeLaunch"
    val KEY_USERNAME = "DataUsernameKey"
    val KEY_PHONE_NUMBER = "DataPhoneNumberKey"
    val KEY_DATE_INFO = "DataDateInfoKey"
    val KEY_HEART_RATE = "DataHeartRateKey"
    val KEY_HEALTH_INFO = "DataHealthInfoKey"
    val KEY_MESSAGE_EMERGENCY = "message_emergency"
    val KEY_ANDROID_WEAR = "android_wear"
    val KEY_LIST_HEALTH_ISSUES = "multi_select_health_conditions"
    val KEY_MESSAGE_SAFETY_CHECK = "message_safety_check"
    val KEY_SIMPLIFIED_UI = "simplifiedUI"

    val KEY_CONTACT_EMERGENCY_NAME = "contact_emergency_name"
    val KEY_CONTACT_EMERGENCY_PHONE_NUMBER = "contact_emergency_phone_number"
}


class PreferencesManager(context: Context) {

    val PRIVATE_MODE = 0
    val pref: SharedPreferences = context.getSharedPreferences(PreferencesKey.PREFS_DATA_NAME, PRIVATE_MODE)
    val defaultSharedPref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun setPreferenceString(key: String, value: String) {
        this.pref.edit().putString(key, value).apply()
    }

    fun getFirstTimeLaunch(): Boolean {
        return this.pref.getBoolean(PreferencesKey.KEY_IS_FIRST_TIME_LAUNCH, true)
    }

    fun setFirstTimeLaunch(value: Boolean) {
        this.pref.edit().putBoolean(PreferencesKey.KEY_IS_FIRST_TIME_LAUNCH, value).apply()
    }

    fun getUsername(): String {
        return this.pref.getString(PreferencesKey.KEY_USERNAME, "")
    }

    fun getPhoneNumber(): String {
        return this.pref.getString(PreferencesKey.KEY_PHONE_NUMBER, "")
    }

    fun getDateInfo(): String {
        return this.pref.getString(PreferencesKey.KEY_DATE_INFO, "No recent information available")
    }

    fun getHeartRate(): String {
        return this.pref.getString(PreferencesKey.KEY_HEART_RATE, "?? bpm")
    }

    fun getHealthInfo(): String {
        return this.pref.getString(PreferencesKey.KEY_HEALTH_INFO, "Enter your health information")
    }

    fun getAccountName(): String {
        return this.pref.getString(PreferencesKey.KEY_ACCOUNT_NAME, "")
    }

    fun getListHealthIssues(): MutableList<String> {
        return this.defaultSharedPref.getStringSet(PreferencesKey.KEY_LIST_HEALTH_ISSUES, setOf("")).toMutableList()
    }

    fun getCustomMessageEmergency(): String {
        return this.defaultSharedPref.getString(PreferencesKey.KEY_MESSAGE_EMERGENCY, "")
    }

    fun getSimplifiedUI(): Boolean {
        return this.defaultSharedPref.getBoolean(PreferencesKey.KEY_SIMPLIFIED_UI, false)
    }

    fun setSimplifiedUI(value: Boolean) {
        this.defaultSharedPref.edit().putBoolean(PreferencesKey.KEY_SIMPLIFIED_UI, value).commit()
    }

    fun setContactEmergency(name: String, phoneNumber: String) {
        this.pref.edit()
                .putString(PreferencesKey.KEY_CONTACT_EMERGENCY_NAME, name)
                .putString(PreferencesKey.KEY_CONTACT_EMERGENCY_PHONE_NUMBER, phoneNumber)
                .apply()
    }

    fun getContactEmergency(): Contact {
        val name = this.pref.getString(PreferencesKey.KEY_CONTACT_EMERGENCY_NAME, "Default name")
        val phone = this.pref.getString(PreferencesKey.KEY_CONTACT_EMERGENCY_PHONE_NUMBER, "045")
        return Contact(name, phone)
    }

}
