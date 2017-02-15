package com.example.floriel.orangesafetyservices.feature.simplifiedUI

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.floriel.orangesafetyservices.R


class SimplifiedUIFragment : Fragment(), SimplifiedUIContract.View {

    private lateinit var mPresenter: SimplifiedUIContract.Presenter
    private lateinit var mButtonEmergency: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.simplified_ui_frag, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mButtonEmergency = this.activity.findViewById(R.id.button_emergency) as Button
        mButtonEmergency.setOnClickListener {
            mPresenter.launchEmergencyActivity()
        }
    }

    override fun onResume() {
        super.onResume()
        mPresenter.start()
    }

    override fun setPresenter(presenter: SimplifiedUIContract.Presenter) {
        mPresenter = presenter
    }

    override fun showEmergencyContactButton(name: String) {
        mButtonEmergency.text = "Emergency Contact"
    }

    companion object {
        fun newInstance() = SimplifiedUIFragment()
    }

}
