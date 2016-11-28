package com.example.floriel.orangesafetyservices

import android.app.Application
import com.example.floriel.orangesafetyservices.objects.DaoMaster
import com.example.floriel.orangesafetyservices.objects.DaoSession

class App : Application() {

    private lateinit var daoSession: DaoSession

    override fun onCreate() {
        super.onCreate()

        val helper = DaoMaster.DevOpenHelper(this, "contacts-db")
        val db = helper.getWritableDb()
        this.daoSession = DaoMaster(db).newSession()
    }

    fun getDaoSession(): DaoSession {
        return this.daoSession
    }

}
