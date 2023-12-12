package com.mobileapp.attemp1_calendar;

import com.mobileapp.attemp1_calendar.Event;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
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

    private View view;

    private GridView calendarGridView;

    private GridLayout daysOfWeek;

    private ImageButton prevBtn;

    private ImageButton nextBtn;

//    private List<Event> list = new ArrayList<>();

    private CalendarViewModel calendarViewModel;

    private String category;
    private String title;
    private String description;
    private String time;
    private String date;

    private Event event;
    private TextView monthDisplay;

    private Event temp;
    private boolean argsFound = false;
//    private Map<String, List<Event>> eventsMap = new HashMap<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        calendarViewModel = new ViewModelProvider(requireActivity()).get(CalendarViewModel.class);

        view = inflater.inflate(R.layout.fragment_calendar, container, false);

        calendarGridView = view.findViewById(R.id.calendarGridView);
        daysOfWeek = view.findViewById(R.id.days_of_week);

        prevBtn = view.findViewById(R.id.prev_btn);
        nextBtn = view.findViewById(R.id.next_btn);

        monthDisplay = view.findViewById(R.id.month_display);


        // Checks if there are arguements passed in
        if (getArguments() != null && getArguments().containsKey("eventCategory") && getArguments().containsKey("eventTitle") && getArguments().containsKey("eventDesc") && getArguments().containsKey("eventTime") && getArguments().containsKey("eventDate") ) {
            category = CalendarFragmentArgs.fromBundle(requireArguments()).getEventCategory();
            title = CalendarFragmentArgs.fromBundle(requireArguments()).getEventTitle();
            description = CalendarFragmentArgs.fromBundle(requireArguments()).getEventDesc();
            time = CalendarFragmentArgs.fromBundle(requireArguments()).getEventTime();
            date = CalendarFragmentArgs.fromBundle(requireArguments()).getEventDate();
            event = new Event(category, title, description, date, time);
//            list.add(event);
            calendarViewModel.addEvent(event);
            System.out.println(event.toString());
            argsFound = true;
        }
        else {
            argsFound = false;
        }

        if (!calendarViewModel.getEventsList().isEmpty()) {
            System.out.println("Printing whole list: \n");
            for (int i = 0; i < calendarViewModel.getEventsList().size(); i++) {
                System.out.println(calendarViewModel.getEventsList().get(i));
                System.out.println("The size is: " + calendarViewModel.getEventsList().size());
            }
        }


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
                String event_date;
                System.out.println("Were the args found: " + argsFound);
                if (argsFound == true) {

                    for (int i = 0; i < calendarViewModel.getEventsList().size(); i++) {
                        temp = calendarViewModel.getEventsList().get(i);

                        if (Integer.parseInt(selectedDay) < 10) {
                            event_date = temp.getDate().substring(4,5);
                        }
                        else {
                            event_date = temp.getDate().substring(3,5);
                        }

                        System.out.println(event_date + "\n" + selectedDay + "\n");

                        if (event_date.equals(selectedDay)) {
                            System.out.println("The dates matched!!");
                            showPopup(temp);
                        }

                    }
                }
                else {
                    showBlankPopup(selectedDay);
                }
            }
        });



        return view;
    }

    public void showBlankPopup(String selectedDay) {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(requireContext());
        View mView = getLayoutInflater().inflate(R.layout.popup, null);
        TextView title_date = (TextView) mView.findViewById(R.id.popUp_date);
        title_date.setText(selectedDay);
        TextView events = (TextView) mView.findViewById(R.id.popUp_events);
        events.setText("NO EVENTS FOR TODAY!!!!");
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
    public void showPopup(Event event) {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(requireContext());
        View mView = getLayoutInflater().inflate(R.layout.popup, null);
        TextView title_date = (TextView) mView.findViewById(R.id.popUp_date);
        title_date.setText(event.getDate());
        TextView upcoming_events = (TextView) mView.findViewById(R.id.popUp_events);
        String str = upcoming_events.getText().toString();
        str += "Title: " + event.getTitle() + " \nDescription: " + event.getDescription() + " \nTime: " + event.getTime() + "\n";
        upcoming_events.setText(str);

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
        monthDisplay.setText(sdf.format(currentCalendar.getTime()));
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
        calendarGridView.setAdapter(adapter);

    }
}
