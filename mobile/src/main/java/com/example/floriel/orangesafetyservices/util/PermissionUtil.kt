package com.example.floriel.orangesafetyservices.util

import android.Manifest

object PermissionUtil {

    val permissions = arrayOf(Manifest.permission.READ_CONTACTS,
            Manifest.permission.BODY_SENSORS,
            Manifest.permission.SEND_SMS,
            Manifest.permission.ACCESS_FINE_LOCATION)
}