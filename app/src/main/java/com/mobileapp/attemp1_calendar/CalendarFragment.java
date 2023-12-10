package com.mobileapp.attemp1_calendar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarFragment extends Fragment {

    private Calendar currentCalendar;

    private View view;

    private GridView calendarGridView;

    private GridLayout daysOfWeek;

    private ImageButton prevBtn;

    private ImageButton nextBtn;

    private TextView monthDisplay;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_calendar, container, false);

        calendarGridView = view.findViewById(R.id.calendarGridView);
        daysOfWeek = view.findViewById(R.id.days_of_week);

        prevBtn = view.findViewById(R.id.prev_btn);
        nextBtn = view.findViewById(R.id.next_btn);

        monthDisplay = view.findViewById(R.id.month_display);

        currentCalendar = Calendar.getInstance();

        // Set the initial month display
        updateMonthDisplay();

        // Set the initial calendar grid
        setCalendarGrid();

        // Set click listeners for the next and previous buttons
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Move to the previous month
                currentCalendar.add(Calendar.MONTH, -1);

                // Update the month display
                updateMonthDisplay();

                // Refresh the calendar grid
                setCalendarGrid();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Move to the next month
                currentCalendar.add(Calendar.MONTH, 1);

                // Update the month display
                updateMonthDisplay();

                // Refresh the calendar grid
                setCalendarGrid();
            }
        });


        calendarGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedDay = (String) parent.getItemAtPosition(position);
                if (!selectedDay.isEmpty()) {
//                    showPopup(selectedDay);

                    // FIX THIS PART BELOW, NEED TO FIND CORRESPONDING BUTTON TO DATE PASSED IN FROM ARGUMENT
//                    // Gathering all of the arguments from the bundle
//                    String category = CalendarFragmentArgs.fromBundle(requireArguments()).getEventCategory();
//                    String title = CalendarFragmentArgs.fromBundle(requireArguments()).getEventTitle();
//                    String description = CalendarFragmentArgs.fromBundle(requireArguments()).getEventDesc();
//                    String time = CalendarFragmentArgs.fromBundle(requireArguments()).getEventTime();
//                    String date = CalendarFragmentArgs.fromBundle(requireArguments()).getEventDate();

                    // Calling on function to display popup that holds the information on the event
//                    showPopup(category, title, description, time);
                }
            }
        });



        return view;
    }

    ////////////////////////////////////////////////////////////////////////////////////////  FUNCTIONS ARE BELOW  //////////////////////////////////////////////////////////////////////////////////////////////

//    public void showPopup(String selectedDay) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
//        builder.setTitle("Selected Day");
//        builder.setMessage("You clicked on: " + selectedDay);
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//        builder.show();
//    }

    public void showPopup(String category, String title, String description, String time, String date) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(category);
        builder.setMessage("Title: " + title + "\nDescription: " + description + "Event starts at " + time);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public void updateMonthDisplay() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
        System.out.println(sdf.format(currentCalendar.getTime()));
        monthDisplay.setText(sdf.format(currentCalendar.getTime()));
    }

    public void setCalendarGrid() {
        // Get the number of days in the month
        int daysInMonth = currentCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        // Get the starting day of the week (1 = Sunday, 2 = Monday, ..., 7 = Saturday)
        int startingDayOfWeek = currentCalendar.get(Calendar.DAY_OF_WEEK);

        // Calculate the number of days to display from the previous month
        int daysFromPrevMonth = (startingDayOfWeek - Calendar.SUNDAY + 7) % 7;

        // Calculate the total number of cells needed in the grid view
        int totalCells = daysInMonth + daysFromPrevMonth;

        // Create a list to hold the days of the month
        List<String> daysOfMonth = new ArrayList<>();

        // Add days from the previous month if needed
        for (int i = 0; i < daysFromPrevMonth; i++) {
            daysOfMonth.add("");
        }

        // Add days of the current month
        for (int i = 1; i <= daysInMonth; i++) {
            daysOfMonth.add(String.valueOf(i));
        }

        // Add days from the next month if needed
        while (daysOfMonth.size() < totalCells) {
            daysOfMonth.add("");
        }

        CalendarAdapter adapter = new CalendarAdapter(requireContext(), daysOfMonth);
        calendarGridView.setAdapter(adapter);

    }
}
