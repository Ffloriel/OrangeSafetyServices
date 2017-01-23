package com.example.floriel.orangesafetyservices.feature.simplifiedUI

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import com.example.floriel.orangesafetyservices.R
import com.example.floriel.orangesafetyservices.util.addFragmentToActivity
import com.example.floriel.orangesafetyservices.util.initFragment
import kotlinx.android.synthetic.main.simplified_ui_act.*

class SimplifiedUIActivity : AppCompatActivity() {

    private lateinit var mPresenter: SimplifiedUIPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.simplified_ui_act)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(false)
            setDisplayShowHomeEnabled(false)
        }
        actionBar?.setTitle(R.string.app_name)

        val simplifiedUIFragment = initFragment(R.id.content_frame) {
            SimplifiedUIFragment.newInstance().apply { addFragmentToActivity(this, R.id.content_frame) }
        }

        mPresenter = SimplifiedUIPresenter(simplifiedUIFragment, this)
        simplifiedUIFragment.setPresenter(mPresenter)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }
}
