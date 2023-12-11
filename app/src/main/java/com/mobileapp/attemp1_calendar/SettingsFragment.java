package com.mobileapp.attemp1_calendar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

public class SettingsFragment extends Fragment {
    private View view;
    Switch switchDarkMode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_settings, container, false);

        switchDarkMode = view.findViewById(R.id.switchDarkMode);

        // Retrieve switch state from SharedPreferences
        boolean isDarkModeOn = loadSwitchState();
        switchDarkMode.setChecked(isDarkModeOn);

        switchDarkMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isDarkModeOn = switchDarkMode.isChecked();
                saveSwitchState(isDarkModeOn);
                updateDarkMode(isDarkModeOn);
            }
        });

        return view;

    }

    private void saveSwitchState(boolean isDarkModeOn) {
        // Saving the switch state in SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("darkMode", isDarkModeOn);
        editor.apply();
    }

    private void updateDarkMode(boolean isDarkModeOn) {
        // This changes the theme to night mode or not
        if(switchDarkMode.isChecked()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        getActivity().sendBroadcast(new Intent("RECREATE_ACTIVITY"));
    }

    private boolean loadSwitchState() {
        // Loads the switch state from SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("darkMode", false);
    }
}