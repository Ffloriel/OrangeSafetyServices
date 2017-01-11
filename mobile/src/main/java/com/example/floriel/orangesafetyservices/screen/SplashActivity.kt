package com.example.floriel.orangesafetyservices.screen

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //startActivity(Intent(this, BottomTabsActivity::class.java))
        Log.d("tre", "euoueuouoeuo")
        startActivity(Intent(this, BottomNavigationActivity::class.java))
        finish()
    }
}
