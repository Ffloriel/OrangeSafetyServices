package com.example.floriel.orangesafetyservices.feature.editHealthInformation

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.floriel.orangesafetyservices.App
import com.example.floriel.orangesafetyservices.R
import kotlinx.android.synthetic.main.edit_health_information_frag.*

class EditHealthInformationFragment : Fragment(), EditHealthInformationContract.View {

    private lateinit var mPresenter: EditHealthInformationContract.Presenter
    private lateinit var mEditTextHealthInformation: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.edit_health_information_frag, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val healthInformation = edit_text_health_information
        val fab = this.activity.findViewById(R.id.fab_save_health_information)
        fab.setOnClickListener {
            mPresenter.saveHealthInformation(healthInformation.text.toString())
            this.activity.finish()
        }
    }

    override fun onResume() {
        super.onResume()
        mPresenter.start()
    }

    override fun setPresenter(presenter: EditHealthInformationContract.Presenter) {
        mPresenter = presenter
    }

    override fun showHealthInformation() {
        edit_text_health_information.setText((this.activity.application as App).preferenceManager.getHealthInfo())
    }

    companion object {
        fun newInstance() = EditHealthInformationFragment()
    }


}
