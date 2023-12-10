package com.mobileapp.attemp1_calendar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;

public class EventAddFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    Button dateBtn;
    Button timeBtn;
    ImageButton infoIconBtn;
    CardView cardView;
    Button addEventBtn;
    Spinner categorySpinner;
    EditText eventTitle;
    EditText eventDesc;
    int hour, minute;
    int year, month, day;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_add, container, false);

        cardView = view.findViewById(R.id.cardview_1);
        categorySpinner = view.findViewById(R.id.eventCategory_spinner);
        eventTitle = view.findViewById(R.id.eventTitle_edtTxt);
        eventDesc = view.findViewById(R.id.eventDesc_edtTxt);

        // Spinner dropdown menu
        Spinner coloredSpinner = view.findViewById(R.id.eventCategory_spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getContext(), R.array.event_categories, R.layout.color_spinner_layout);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        coloredSpinner.setAdapter(adapter);
        coloredSpinner.setOnItemSelectedListener(this);

        // Information icon button
        infoIconBtn = view.findViewById(R.id.informationIconBtn);
        infoIconBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup();
            }
        });

        // Date selection button
        dateBtn = view.findViewById(R.id.selectDateBtn);
        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateSelection();
            }
        });

        //  Time selection button
        timeBtn = view.findViewById(R.id.selectTimeBtn);
        timeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        hour = selectedHour;
                        minute = selectedMinute;
                        String timeFormat = String.format(Locale.getDefault(), "%02d:%02d", hour, minute);
                        timeFormat = convertTo12HourFormat(timeFormat);
//                        Log.i("timeFormat", "Time Format: " + timeFormat);
                        timeBtn.setText(timeFormat);
                    }
                };

                // Sets timer up in spinner style
                int style = AlertDialog.THEME_HOLO_DARK;

                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), style, onTimeSetListener, hour, minute, true);

                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });

        // Navigate all the arguments to the CalendarFragment using navigational directions
        addEventBtn = view.findViewById(R.id.addEventBtn);
        addEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = categorySpinner.getSelectedItem().toString();
                String title = eventTitle.getText().toString();
                String description = eventDesc.getText().toString();
                String time = timeBtn.getText().toString();
                String date = dateBtn.getText().toString();

                EventAddFragmentDirections.ActionEventAddFragmentToCalendarFragment action = EventAddFragmentDirections.actionEventAddFragmentToCalendarFragment(category, title, description, time, date);

                // navigates to Calendar Fragment after storing all the variables in action
                Navigation.findNavController(view).navigate(action);
            }
        });



        return view;
    }
////////////////////////////////////////////////////////////////////////////////////////  FUNCTIONS ARE BELOW  //////////////////////////////////////////////////////////////////////////////////////////////

    // Function to display a popup for when the information icon is clicked on
    public void showPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Event Information");
        String message = "<b>Assignments:</b> Homeworks, mini projects or small essays.<br><br>" +
                "<b>Repetitive:</b> Tasks that you normally do throughout the week or month. For example, picking up your sibling after school everyday.<br><br>" +
                "<b>Major:</b> Tasks that are urgent or require a lot of effort! Some examples of this are projects, appointments or large essays.";
        builder.setMessage(Html.fromHtml(message));
        builder.setPositiveButton("GOT IT!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    // Function to show the a mini calendar and the date selected on it
    private void showDateSelection() {
        final Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                        year = selectedYear;
                        month = selectedMonth;
                        day = selectedDay;

                        // Display the selected date
                        String selectedDate = String.format(Locale.getDefault(), "%02d/%02d/%04d", month + 1, day, year);
                        dateBtn.setText(selectedDate);
                    }
                },
                currentYear,
                currentMonth,
                currentDay
        );

        // Show the date picker dialog
        datePickerDialog.show();
    }

    // function to convert timePickerDialog to 12pm/am format instead of military format
    private String convertTo12HourFormat(String time24Hour) {
        try {
            // Parse the input time string in 24-hour format
            java.text.SimpleDateFormat simpleDateFormat24 = new java.text.SimpleDateFormat("HH:mm", Locale.getDefault());
            java.util.Date date = simpleDateFormat24.parse(time24Hour);

            // Format the parsed date in 12-hour format with AM/PM
            java.text.SimpleDateFormat simpleDateFormat12 = new java.text.SimpleDateFormat("hh:mm a", Locale.getDefault());
            return simpleDateFormat12.format(date);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return time24Hour;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (view != null) {
            TextView textView = (TextView) view;

            // switch case to handle each item selected and what color is assigned to its textview. Also, matches cardview background color to textview color
            switch (i) {
                case 0:
                    textView.setTextColor(ContextCompat.getColor(adapterView.getContext(), R.color.assignmentsColor));
                    cardView.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.assignmentsColor));
                    break;
                case 1:
                    textView.setTextColor(ContextCompat.getColor(adapterView.getContext(), R.color.repetitiveColor));
                    cardView.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.repetitiveColor));
                    break;
                case 2:
                    textView.setTextColor(ContextCompat.getColor(adapterView.getContext(), R.color.majorColor));
                    cardView.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.majorColor));
                    break;
            }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
