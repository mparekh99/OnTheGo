package com.mobileapp.attemp1_calendar;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Locale;

public class EventAddFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    Button timeBtn;
    int hour, minute;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_add, container, false);

        // Spinner dropdown menu
        Spinner coloredSpinner = view.findViewById(R.id.eventCategory_spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getContext(), R.array.event_categories, R.layout.color_spinner_layout);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        coloredSpinner.setAdapter(adapter);
        coloredSpinner.setOnItemSelectedListener(this);

        //  Time picker button
        timeBtn = view.findViewById(R.id.timeBtn);
        timeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        hour = selectedHour;
                        minute = selectedMinute;
                        timeBtn.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
                    }
                };

                // Sets timer up in spinner style
                int style = AlertDialog.THEME_HOLO_DARK;

                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), style, onTimeSetListener, hour, minute, true);

                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (view != null) {
            TextView textView = (TextView) view;

            // switch case to handle each item selected and what color is assigned to its textview
            switch (i) {
                case 0:
                    textView.setTextColor(ContextCompat.getColor(adapterView.getContext(), R.color.assignmentsColor));
                    break;
                case 1:
                    textView.setTextColor(ContextCompat.getColor(adapterView.getContext(), R.color.repetitiveColor));
                    break;
                case 2:
                    textView.setTextColor(ContextCompat.getColor(adapterView.getContext(), R.color.majorColor));
                    break;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}