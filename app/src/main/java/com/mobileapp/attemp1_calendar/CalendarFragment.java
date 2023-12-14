package com.mobileapp.attemp1_calendar;

import com.mobileapp.attemp1_calendar.Event;
import com.mobileapp.attemp1_calendar.databinding.FragmentCalendarBinding;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CalendarFragment extends Fragment {

    private Calendar currentCalendar;

    private GridView calendarGridView;
    private GridLayout daysOfWeek;
    private ImageButton prevBtn;
    private ImageButton nextBtn;
    private TextView monthDisplay;

    private CalendarViewModel calendarViewModel;

    private String category;
    private String title;
    private String description;
    private String time;
    private String date;
    private Event event;
    private FragmentCalendarBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCalendarBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        calendarViewModel = new ViewModelProvider(requireActivity()).get(CalendarViewModel.class);

        calendarGridView = binding.calendarGridView;
        daysOfWeek = binding.daysOfWeek;

        prevBtn = binding.prevBtn;
        nextBtn = binding.nextBtn;

        monthDisplay = binding.monthDisplay;


        // Checks if there are arguements passed in
        if (getArguments() != null && getArguments().containsKey("eventCategory") && getArguments().containsKey("eventTitle") && getArguments().containsKey("eventDesc") && getArguments().containsKey("eventTime") && getArguments().containsKey("eventDate") ) {
            category = CalendarFragmentArgs.fromBundle(requireArguments()).getEventCategory();
            title = CalendarFragmentArgs.fromBundle(requireArguments()).getEventTitle();
            description = CalendarFragmentArgs.fromBundle(requireArguments()).getEventDesc();
            time = CalendarFragmentArgs.fromBundle(requireArguments()).getEventTime();
            date = CalendarFragmentArgs.fromBundle(requireArguments()).getEventDate();
            event = new Event(category, title, description, date, time);
            calendarViewModel.addEvent(date, event);
        }
        currentCalendar = Calendar.getInstance();

        // Set the initial month display
        updateMonthDisplay();

        // Set the initial calendar grid
        setCalendarGrid();

        // Set click listeners for the next and previous buttons
        binding.prevBtn.setOnClickListener(new View.OnClickListener() {
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

        binding.nextBtn.setOnClickListener(new View.OnClickListener() {
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

        binding.calendarGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedDay = (String) parent.getItemAtPosition(position);
                selectedDay = convertToDateFormat(selectedDay);

                if (calendarViewModel.getEventsMap().containsKey(selectedDay)) {
                    showPopup(calendarViewModel.getListForKey(selectedDay), selectedDay);
                } else {
                    showBlankPopup(selectedDay);
                }
            }
        });

        return view;
    }
    ////////////////////////////////////////////////////////////////////////////////////////  FUNCTIONS ARE BELOW  //////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private String convertToDateFormat(String selectedDay) {
        // if statement checks to see if the date exist (if you click on a date that's an empty box, it won't go into condition)
        if (selectedDay.length() > 0) {
            // Assuming currentCalendar represents the current month
            currentCalendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(selectedDay));

            // Create a SimpleDateFormat object for the desired date format
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());

            // Format the date and return
            return sdf.format(currentCalendar.getTime());
        }
        return "Date doesn't exist";
    }

    public void showBlankPopup(String selectedDay) {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(requireContext());
        View mView = getLayoutInflater().inflate(R.layout.popup, null);
        TextView title_date = (TextView) mView.findViewById(R.id.popUp_date);
        title_date.setText(selectedDay);

        // Added a linearlayout to the popup.xml to add a textview into this
        LinearLayout events = mView.findViewById(R.id.popUp_events_layout);

        // Creating new textview to add text
        TextView eventTxtView = new TextView(requireContext());

        eventTxtView.setText("NO EVENTS FOR TODAY!!!!");
        eventTxtView.setTextSize(18);

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
    public void showPopup(List<Event> list, String selectedDay) {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(requireContext());
        View mView = getLayoutInflater().inflate(R.layout.popup, null);
        TextView title_date = (TextView) mView.findViewById(R.id.popUp_date);
        title_date.setText(selectedDay);

        // Added a linearlayout to the popup.xml to add multiple textviews into this for each event
        LinearLayout upcoming_events = mView.findViewById(R.id.popUp_events_layout);

        for(int i = 0; i < list.size(); i++) {
            // Creating a new textview to customize its color and text
            TextView eventTxtView = new TextView(requireContext());

            eventTxtView.setText("Title: " + list.get(i).getTitle() + " \nDescription: " + list.get(i).getDescription() + " \nTime: " + list.get(i).getTime() + "\n");
            eventTxtView.setTextSize(18);

            // Switch statement determines which color to set the textview depending on what category it is
            switch (list.get(i).getCategory()) {
                case "Assignments":
                    eventTxtView.setTextColor(ContextCompat.getColor(requireContext(), R.color.assignmentsColor));
                    break;
                case "Repetitive":
                    eventTxtView.setTextColor(ContextCompat.getColor(requireContext(), R.color.repetitiveColor));
                    break;
                case "Major":
                    eventTxtView.setTextColor(ContextCompat.getColor(requireContext(), R.color.majorColor));
                    break;
            }

            // Add this customized textview into the linear layout of the popup.xml
            upcoming_events.addView(eventTxtView);
        }

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

    public void updateMonthDisplay() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
//        System.out.println(sdf.format(currentCalendar.getTime()));
        binding.monthDisplay.setText(sdf.format(currentCalendar.getTime()));
    }

    public void setCalendarGrid() {
        // Get the number of days in the month
        int daysInMonth = currentCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        // Get the starting day of the week (1 = Sunday, 2 = Monday, ..., 7 = Saturday)
        currentCalendar.set(Calendar.DAY_OF_MONTH, 1);
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
        binding.calendarGridView.setAdapter(adapter);
    }
}
