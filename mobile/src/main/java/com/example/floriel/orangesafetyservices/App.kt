package com.example.floriel.orangesafetyservices

import android.app.Application
import com.example.floriel.orangesafetyservices.helper.PreferencesManager

class App : Application() {

    val preferenceManager by lazy { PreferencesManager(this.applicationContext) }

    override fun onCreate() {
        super.onCreate()
    }

}
