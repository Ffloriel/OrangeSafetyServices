package com.example.floriel.orangesafetyservices.screen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import com.example.floriel.orangesafetyservices.R
import kotlinx.android.synthetic.main.activity_missing_permissions.*


class MissingPermissionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_missing_permissions)

        button_to_exit.setOnClickListener { finish() }
        button_to_settings.setOnClickListener {
            val intent = Intent()
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            val uri = Uri.fromParts("package", packageName, null)
            intent.data = uri
            startActivity(intent)
            finish()
        }
    }
}
