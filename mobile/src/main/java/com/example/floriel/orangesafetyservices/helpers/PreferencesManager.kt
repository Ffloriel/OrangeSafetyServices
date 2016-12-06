package com.example.floriel.orangesafetyservices.helpers

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {

    val PRIVATE_MODE = 0
    val PREFS_DATA_NAME = "DataAppFile"
    val pref: SharedPreferences = context.getSharedPreferences(PREFS_DATA_NAME, PRIVATE_MODE)

    val KEY_ACCOUNT_NAME = "accountName"
    val KEY_IS_FIRST_TIME_LAUNCH = "isFirstTimeLaunch"
    val KEY_USERNAME = "DataUsernameKey"
    val KEY_PHONE_NUMBER = "DataPhoneNumberKey"
    val KEY_DATE_INFO = "DataDateInfoKey"
    val KEY_HEART_RATE = "DataHeartRateKey"
    val KEY_HEALTH_INFO = "DataHealthInfoKey"

    fun setPreferenceString(key: String, value: String) {
        this.pref.edit().putString(key, value).apply()
    }

    fun getFirstTimeLaunch(): Boolean {
        return this.pref.getBoolean(KEY_IS_FIRST_TIME_LAUNCH, true)
    }

    fun setFirstTimeLaunch(value: Boolean) {
        this.pref.edit().putBoolean(KEY_IS_FIRST_TIME_LAUNCH, value).apply()
    }

    fun getUsername(): String {
        return this.pref.getString(KEY_USERNAME, "")
    }

    fun getPhoneNumber(): String {
        return this.pref.getString(KEY_PHONE_NUMBER, "")
    }

    fun getDateInfo(): String {
        return this.pref.getString(KEY_DATE_INFO, "No recent information available")
    }

    fun getHeartRate(): String {
        return this.pref.getString(KEY_HEART_RATE, "?? bpm")
    }

    fun getHealthInfo(): String {
        return this.pref.getString(KEY_HEALTH_INFO, "No information")
    }

    fun getAccountName(): String {
        return this.pref.getString(KEY_ACCOUNT_NAME, "")
    }


}
