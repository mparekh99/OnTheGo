package com.mobileapp.attemp1_calendar;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.List;

public class CalendarAdapter extends BaseAdapter {
    private final Context context;
    private final List<String> daysOfMonth;

    public CalendarAdapter(Context context, List<String> daysOfMonth) {
        this.context = context;
        this.daysOfMonth = daysOfMonth;
    }

    @Override
    public int getCount() {
        return daysOfMonth.size();
    }

    @Override
    public Object getItem(int position) {
        return daysOfMonth.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.calendar_cell_layout, null);
        }

        TextView dayTextView = view.findViewById(R.id.dayTextView);
        String day = daysOfMonth.get(position);

        dayTextView.setText(day);

        int orientation = context.getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Adjusts the size of the calendar cells in landscape mode
            ViewGroup.LayoutParams params = dayTextView.getLayoutParams();
            params.height = 140;
            dayTextView.setLayoutParams(params);
        } else {
            // Adjusts the size of the calendar cells in portrait mode
            ViewGroup.LayoutParams params = dayTextView.getLayoutParams();
            params.height = 270;
            dayTextView.setLayoutParams(params);
        }

        return view;
    }
}
