package com.example.floriel.orangesafetyservices.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.floriel.orangesafetyservices.R;

public class HealthFragment extends BaseFragment {

    public HealthFragment() {
        // Required empty public constructor
    }

    public static HealthFragment newInstance(int instance) {
        HealthFragment fragment = new HealthFragment();
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_health, container, false);
    }
}
