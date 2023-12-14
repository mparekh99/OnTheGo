/**
 Authors: <Javier Solares, Mihir Parekh, Al-Amin Muhammad>
 Date: <12/13/2023>
 Purpose: <OnTheGo Calendar app, our main purpose is to help give organization to the normal person’s life.
 After taking inspiration from Google Calendar, we wanted to give the user a visually appealing calendar
 that can take in manually added events and store them on specific days. After adding your events that
 can be categorized in either Major, Repetitive, or Assignment, we can navigate back to the calendar
 and select specific days. Every time a day is clicked, a popUp will appear with either a list of the
 events on the day with their descriptions and times. Or the popUp will display a message saying
 “THERE ARE NO EVENTS FOR TODAY!!!”. In this list of events they are color coded based on their category.
 Also we added view binding to make it quicker and reduce runtime errors. Lastly we implemented ViewModel
 to ensure the app saves the data when switching to other apps or switching the app orientation state.
 >
 What Learned: <Utilizing Online resources and learning new methods can solve problems previous lessons wouldn’t be able to otherwise.
 Apps will crash randomly and it's very frustrating, so being pateint is the key. We learned that starting early and setting realistic goals made our app easier.
 Creating an app design + structure before implementing any code.>
 Sources of Help: <GeeksForGeeks, YouTube Tutorials, Lecture Slides, Google.>
 Time Spent (Hours): <21 days>
 **/
/*
Mobile App Development I -- COMP.4630 Honor Statement
The practice of good ethical behavior is essential for maintaining good order
in the classroom, providing an enriching learning experience for students,
and training as a practicing computing professional upon graduation. This
practice is manifested in the University's Academic Integrity policy.
Students are expected to strictly avoid academic dishonesty and adhere to the
Academic Integrity policy as outlined in the course catalog. Violations will
be dealt with as outlined therein. All programming assignments in this class
are to be done by the student alone unless otherwise specified. No outside
help is permitted except the instructor and approved tutors.
I certify that the work submitted with this assignment is mine and was
generated in a manner consistent with this document, the course academic
policy on the course website on Blackboard, and the UMass Lowell academic
code.
Date: 12/13/2023
Names: Javier Solares, Mihir Parekh, Al-Amin Muhammad
*/


package com.mobileapp.attemp1_calendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();

        // Sets up the bottom navigational bar to switch between fragments
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }


}