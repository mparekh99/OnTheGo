package com.mobileapp.attemp1_calendar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

public class SettingsFragment extends Fragment {
    private View view;
    Switch switchDarkMode;
    Button btnInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_settings, container, false);

        switchDarkMode = view.findViewById(R.id.switchDarkMode);
        btnInfo = view.findViewById(R.id.btnInfo);

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

        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup();
            }
        });

        return view;

    }

    public void showPopup() {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(requireContext());
        View mView = getLayoutInflater().inflate(R.layout.popup, null);
        TextView title= (TextView) mView.findViewById(R.id.popUp_date);
        title.setText("OntheGo Information");
        title.setTextSize(27);

        // Added a linearlayout to the popup.xml to add a textview into this
        LinearLayout events = mView.findViewById(R.id.popUp_events_layout);

        // Creating new textview to add text
        TextView eventTxtView = new TextView(requireContext());

        // Using HTML to add specific bolded words in the text
        String infoText = "Hello everyone and thank you for downloading our OntheGo Calendar App! " +
                "We have great updates in store for the future of our app and want to add a lot more features to make " +
                "it more user friendly.<br/><br/>To correctly use the app, navigate to the 'Add Event' page and enter in the " +
                "required fields. Once you have filled out the form click on 'Add Event' and boom! Your event has been " +
                "added to the calendar now and will display the information that you've entered.<br/><br/>Thank you for using " +
                "our app and we hope you continue to support us in the future.<br/><br/><b>Version:</b> 1.0<br/><b>Developed By:</b> Javier " +
                "Solares, Mihir Parekh, Al-Amin Muhammad<br/><br/><b>@ OntheGo.Inc 2023</b>";

        eventTxtView.setText(Html.fromHtml(infoText));
        eventTxtView.setTextSize(17);

        // Adding the textview to the linear layout of the popup.xml
        events.addView(eventTxtView);
        mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
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
