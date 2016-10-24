package com.example.floriel.orangesafetyservices.activities;

import android.preference.PreferenceFragment;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.floriel.orangesafetyservices.R;
import com.example.floriel.orangesafetyservices.fragments.HealthFragment;
import com.example.floriel.orangesafetyservices.fragments.SettingsFragment;
import com.ncapdevi.fragnav.FragNavController;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

public class BottomTabsActivity extends AppCompatActivity {

    private BottomBar mBottomBar;
    private FragNavController mNavController;

    private final int INDEX_HEALTH = FragNavController.TAB1;
    private final int INUEX_CONTACTS = FragNavController.TAB2;
    private final int INDEX_SETTINGS = FragNavController.TAB3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_tabs);

        List<Fragment> fragments = new ArrayList<>(3);

        fragments.add(HealthFragment.newInstance(0));
        fragments.add(SettingsFragment.newInstance(0));
        fragments.add(SettingsFragment.newInstance(0));

        mNavController = new FragNavController(getSupportFragmentManager(), R.id.container, fragments);

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab_information:
                        mNavController.switchTab(INDEX_HEALTH);
                        break;
                    case R.id.tab_contact:
                        mNavController.switchTab(INUEX_CONTACTS);
                        break;
                    case R.id.tab_setting:
                        mNavController.switchTab(INDEX_SETTINGS);
                        break;
                }
            }
        });


    }
}
