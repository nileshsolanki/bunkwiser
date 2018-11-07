package com.androidprojects.nstech.bunkwise.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.androidprojects.nstech.bunkwise.Objects.CalendarSubject;
import com.androidprojects.nstech.bunkwise.R;

import java.util.ArrayList;
import java.util.List;

public class CalendarListAdapter extends ArrayAdapter<CalendarSubject>{

    List<CalendarSubject> calendarSubjects;
    Context context;

    public CalendarListAdapter(Context context, ArrayList<CalendarSubject> calendarSubjects){
        super(context, 0, calendarSubjects);
        this.calendarSubjects = calendarSubjects;
        this.context = context;



    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View v = LayoutInflater.from(context).inflate(R.layout.calendar_frag_list_item, null, false);
        TextView subNameText = (TextView) v.findViewById(R.id.tv_subName_cal_frag);
        subNameText.setText(calendarSubjects.get(position).getSubName());

        CardView colorDot = v.findViewById(R.id.view_color);
        int state = calendarSubjects.get(position).getState();
        if(state == 1){ colorDot.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorAccent))); }
        else if(state == 2){ colorDot.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.red))); }
        else {colorDot.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorPrimary))); }

        return  v;
    }
}
