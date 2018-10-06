package com.androidprojects.nstech.bunkwise;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.androidprojects.nstech.bunkwise.adapters.CalendarAdapter;

public class AnalysisActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewPager viewPager;
    CalendarAdapter adapter;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);


        toolbar = findViewById(R.id.toolbar_analysis);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        tabLayout = findViewById(R.id.tablayout_analysis);
        viewPager = findViewById(R.id.viewpager_analysis);
        adapter = new CalendarAdapter(getSupportFragmentManager());
        adapter.addFragments(new CalendarFragment(), "Calendar");
        adapter.addFragments(new GraphFragment(), "Graph");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


    }
}
