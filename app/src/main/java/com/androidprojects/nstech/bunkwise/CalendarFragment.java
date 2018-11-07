package com.androidprojects.nstech.bunkwise;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;

import com.androidprojects.nstech.bunkwise.Objects.CalendarSubject;
import com.androidprojects.nstech.bunkwise.Utils.DatesReaderDbHelper;
import com.androidprojects.nstech.bunkwise.adapters.CalendarListAdapter;

import java.util.ArrayList;

import static com.androidprojects.nstech.bunkwise.Utils.DatesReaderDbHelper.COLUMNS;
import static com.androidprojects.nstech.bunkwise.Utils.DatesReaderDbHelper.COL_DATE;
import static com.androidprojects.nstech.bunkwise.Utils.DatesReaderDbHelper.TABLE_NAME;

public class CalendarFragment extends Fragment{

    DatesReaderDbHelper helper;
    SQLiteDatabase db;
    CalendarListAdapter adapter;
    ArrayList<CalendarSubject> calendarSubjects;
    View v;

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(db != null){
            helper.close();
            db.close();

        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        helper = new DatesReaderDbHelper(getContext());
        db = helper.getReadableDatabase();

        v = inflater.inflate(R.layout.fragment_calendar, container, false);
        final ListView subjectStateList = v.findViewById(R.id.lv_calendar_frag);

        final CalendarView cv = v.findViewById(R.id.calendar_view);
        cv.setMaxDate(cv.getDate() - 1*24*60*60*1000);

        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
//                int dbDate = Integer.parseInt(year + "" + month + "" + day);
                if (adapter != null){
                    adapter.clear();
                }
                String dbDate = getFormattedDate(year, month, day);
                Log.i("CalendarFrag", "onSelectedDayChange: date"+ dbDate);
                calendarSubjects = new ArrayList<>();
                Cursor cursor = db.query(TABLE_NAME, COLUMNS, COL_DATE + " LIKE ?", new String[] { dbDate}, null, null, null);
                Log.i("Adapter", "CalendarListAdapter: "+ cursor.getCount());

                if(cursor.getCount() > 0) {

                    cursor.moveToFirst();
                    for (String subName : COLUMNS) {
                        calendarSubjects.add(new CalendarSubject(subName.replace("_"," "), cursor.getInt(cursor.getColumnIndex(subName))));
                    }
                    cursor.close();


                    adapter = new CalendarListAdapter(getContext(), calendarSubjects);
                    subjectStateList.setAdapter(adapter);
                    subjectStateList.setEmptyView(v.findViewById(R.id.emptyElement));

                }
                else{
                    TextView tv = new TextView(getContext());
                    tv.setText("Nothing to Show");
                    subjectStateList.setEmptyView(tv);
                }
            }
        });

        cv.setDate(cv.getDate() - 1);
        return v;
    }

    private String getFormattedDate(int year, int month, int day) {

        String smonth = month+ 1 + "";
        String sday = day + "";
        if((month + 1) < 10){ smonth = "0" + smonth;}
        if(day < 10){ sday = "0" + sday;}

        return ""+year + smonth + sday;

    }
}
