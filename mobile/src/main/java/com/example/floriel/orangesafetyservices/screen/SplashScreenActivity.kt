package com.example.floriel.orangesafetyservices.screen

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, BottomNavigationActivity::class.java))
        //startActivity(Intent(this, LoadActivity::class.java))
        finish()
    }
}
