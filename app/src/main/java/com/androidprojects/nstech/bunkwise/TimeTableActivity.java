package com.androidprojects.nstech.bunkwise;

import android.animation.Animator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.TextView;

import com.androidprojects.nstech.bunkwise.Utils.PrefHandler;
import com.androidprojects.nstech.bunkwise.adapters.FragmentAdapter;

import java.util.HashSet;
import java.util.Set;

public class TimeTableActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    AppBarLayout appBar;
    Toolbar toolbar;
    FloatingActionButton fab;
    GridLayout subjectOptionsGrid;
    DayFragment fragment;
    boolean isVisible = false;
    private final String DOW []= {"sun", "mon", "tue", "wed", "thu", "fri", "sat"};

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){

            case R.id.tt_done: startActivity(new Intent(this, MainActivity.class));
                                finish();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.timetable_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!new PrefHandler(this).getSubjectsFor(DOW[1]).isEmpty()){
            //TODO : not robust enough
            startActivity(new Intent(this, MainActivity.class));
            finish();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);

//        setWindowParams();
        //TODO : disabled window params


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewPager = findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(6);
        tabLayout = findViewById(R.id.tablayout);
        appBar = findViewById(R.id.appBar);
        fab = findViewById(R.id.fab);
        subjectOptionsGrid = findViewById(R.id.gl_subject_options);
        createOptionsView();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isVisible) {
                    isVisible = true;
                    revealOptions(fab.getRootView());
                }else{
                    isVisible = false;
                    hideOptions(fab.getRootView());
                }
            }
        });


        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void hideOptions(View view) {

        final ConstraintLayout layout = view.findViewById(R.id.cl_options);
        int cx = (int)fab.getX() + fab.getWidth() / 2;
        int cy = (int)fab.getY() + fab.getHeight() / 2;

        int finalRadius = (int) Math.hypot(view.getWidth()/2, view.getHeight()/2);

        Animator anim = ViewAnimationUtils.createCircularReveal(layout, cx, cy, finalRadius, 0);

        fab.setImageResource(R.drawable.ic_add);
        anim.start();
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Set<String> subjects = new HashSet<>();
                fragment = (DayFragment) getSupportFragmentManager().getFragments().get(tabLayout.getSelectedTabPosition());
                for(int i = 0; i < subjectOptionsGrid.getRowCount()*2-1; i+=2){

                    CheckBox checkBox = subjectOptionsGrid.findViewById(i);
                    if(checkBox.isChecked()){

                        TextView textView = subjectOptionsGrid.findViewById(i+1);
                        String subName = textView.getText().toString().trim();

                        fragment.addToAdapter(subName); //can only be ran in fragment
                        subjects.add(subName);
                        checkBox.setChecked(false);
                    }

                }
                String day = DOW[tabLayout.getSelectedTabPosition()];
                if(subjects.size() > 0) {
                    new PrefHandler(TimeTableActivity.this).putSubjectsFor(day, subjects);
                }

                layout.setVisibility(View.GONE);

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }


    private void setWindowParams() {

        if(Build.VERSION.SDK_INT > 21){

            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

    }


    private void revealOptions(View view) {

    ConstraintLayout layout = view.findViewById(R.id.cl_options);
    int cx = (int)fab.getX() + fab.getWidth() / 2;
    int cy = (int)fab.getY() + fab.getHeight() / 2;

    int finalRadius = (int) Math.hypot(view.getWidth(), view.getHeight());

    Animator anim = ViewAnimationUtils.createCircularReveal(layout, cx, cy, 0, finalRadius);

    layout.setVisibility(View.VISIBLE);

    fab.setImageResource(R.drawable.ic_done);
    anim.setDuration(500);
    anim.start();


    }


    private void createOptionsView(){

        PrefHandler pf = new PrefHandler(TimeTableActivity.this);
        Object [] subjects = pf.getSubjects().toArray();
        subjectOptionsGrid.setColumnCount(2);
        subjectOptionsGrid.setRowCount(subjects.length);

        for(int i = 0,j = 0; i < subjects.length*2-1; i+=2, j++){

            DisplayMetrics metrics = getResources().getDisplayMetrics();
            int px_padding = (int)16*(metrics.densityDpi / metrics.DENSITY_DEFAULT);
            int px_textSize = (int)10*(metrics.densityDpi / metrics.DENSITY_DEFAULT);

            CheckBox checkBox = new CheckBox(TimeTableActivity.this);
            checkBox.setPadding(px_padding,px_padding,px_padding,px_padding);
            checkBox.setId(i);

            TextView textView = new TextView(TimeTableActivity.this);
            textView.setId(i+1);
            textView.setPadding(px_padding,px_padding,px_padding,px_padding);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, px_textSize);
            textView.setText((String)subjects[j]);

            subjectOptionsGrid.addView(checkBox, i);
            subjectOptionsGrid.addView(textView, i+1);


        }

    }
}
