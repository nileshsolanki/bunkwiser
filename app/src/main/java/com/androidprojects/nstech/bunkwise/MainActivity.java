package com.androidprojects.nstech.bunkwise;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.androidprojects.nstech.bunkwise.Utils.PrefHandler;
import com.androidprojects.nstech.bunkwise.adapters.ClassesListAdapter;

import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {


    Toolbar toolbar;
    ListView dailyClassesList;
    ClassesListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar_mainactivity);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        Object [] subjects = new PrefHandler(this).getSubjectsFor("mon").toArray();
        dailyClassesList = findViewById(R.id.lv_daily_tt);
        adapter = new ClassesListAdapter(this, subjects);
        dailyClassesList.setAdapter(adapter);

        //TODO: clear state when date changes


    }
}
