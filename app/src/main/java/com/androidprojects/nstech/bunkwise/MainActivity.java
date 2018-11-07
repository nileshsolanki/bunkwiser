package com.androidprojects.nstech.bunkwise;

import android.animation.Animator;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidprojects.nstech.bunkwise.Utils.DatesReaderDbHelper;
import com.androidprojects.nstech.bunkwise.Utils.PrefHandler;
import com.androidprojects.nstech.bunkwise.adapters.ClassesListAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.androidprojects.nstech.bunkwise.Utils.DatesReaderDbHelper.COL_DATE;
import static com.androidprojects.nstech.bunkwise.Utils.DatesReaderDbHelper.TABLE_NAME;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab;
    Toolbar toolbar;
    TextView dateText;
    ListView dailyClassesList;
    GridLayout subjectOptionsGrid;
    CollapsingToolbarLayout collapsingToolbar;
    ClassesListAdapter adapter;
    ArrayList<Object> subjects = new ArrayList<>();
    Calendar calendar;
    Date date;
    int dbDate;
    String day;
    boolean isVisible = false;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM");
    SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");

    DatesReaderDbHelper dbHelper;
    SQLiteDatabase db;
    int navActivityId = 0;
    DrawerLayout drawerLayout;
    NavigationView navView;
    ImageView drawerPic;
    TextView drawerName;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar_mainactivity);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        calendar = Calendar.getInstance();
        date = calendar.getTime();
        dbDate = Integer.parseInt(dateFormat.format(date));
        day = dayFormat.format(date);

        dateText = findViewById(R.id.tv_date_mainactivity);
        dateText.setText(dbDate % 100 + " " + monthFormat.format(date).toLowerCase() + " " + dbDate / 10000);
        collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(day);
        dailyClassesList = findViewById(R.id.lv_daily_tt);

        subjectOptionsGrid = findViewById(R.id.gl_subject_options);
        createOptionsView();


        drawerLayout = findViewById(R.id.drawer_layout);



        addDrawerListener(drawerLayout);
        navView = findViewById(R.id.nav_view);
        setNavigationItemListener(navView);

        fab = findViewById(R.id.fab_add_subs_main_activity);
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

        fillListwithSubjects();



        //TODO: clear state when date changes DONE
        PrefHandler pf = new PrefHandler(this);
        int date = pf.getParams("date");
        if(dbDate != date){
            updateDatabase(pf, date);
            pf.clearPrevStates();
            pf.putParams(dbDate);
        }

    }

    private void fillListwithSubjects() {

        ArrayList<String> subSet = new PrefHandler(this).getSubjectsFor(day.substring(0,3).toLowerCase());

        PrefHandler pf =  new PrefHandler(this);
        for(String sub : pf.getSubjects()){

            if(!subSet.contains(sub) && pf.getState(sub) != 0){

                subSet.add(sub.replace("_", " "));
            }
        }
        subjects.addAll(subSet);
        adapter = new ClassesListAdapter(MainActivity.this, subjects.toArray());
        dailyClassesList.setAdapter(adapter);
        dailyClassesList.setEmptyView(this.findViewById(R.id.emptyElement));


    }

    private void setPicAndName() {

        PrefHandler pf = new PrefHandler(this);
        int gender = pf.getParams("gender");
        String name = pf.getParamsUserName("userName");

        if(gender == 1){
            drawerPic.setImageResource(R.drawable.boy);
        }
        else if(gender == 2){
            drawerPic.setImageResource(R.drawable.girl);
        }

        drawerName.setText(name);

    }

    private void addDrawerListener(DrawerLayout drawerLayout) {


        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {

                drawerName = view.findViewById(R.id.tv_drawer_name);
                drawerPic = view.findViewById(R.id.iv_drawer);
                setPicAndName();
            }

            @Override
            public void onDrawerOpened(@NonNull View view) {



            }

            @Override
            public void onDrawerClosed(@NonNull View view) {

                switch (navActivityId){

                    case R.id.analysis:
                        startActivity(new Intent(MainActivity.this, AnalysisActivity.class));
                        break;

                    case R.id.profile:
                        Intent i = new Intent(MainActivity.this, SetupActivity.class);
                        i.setData(Uri.parse("stay"));
                        startActivity(i);
                        break;
                }

            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });

    }




    private void setNavigationItemListener(final NavigationView navView) {

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                menuItem.setChecked(true);
                drawerLayout.closeDrawers();

                switch (menuItem.getItemId()){

                    case R.id.profile:
                        navActivityId = R.id.profile;
                        break;

                    case R.id.analysis:
//                        startActivity(new Intent(MainActivity.this, AnalysisActivity.class));
                        navActivityId = R.id.analysis;
                        break;
                    case R.id.predict:
                        navActivityId = R.id.predict;
//                        startActivity(new Intent(MainActivity.this, PredictorActivity.class));


                }
                return false;
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(dbHelper != null) {
            dbHelper.close();
            db.close();
        }
    }

    private void updateDatabase(PrefHandler pf, int date) {
        dbHelper = new DatesReaderDbHelper(MainActivity.this);
        db = dbHelper.getWritableDatabase();
        Object [] objs = pf.getSubjects().toArray();
        ContentValues values = new ContentValues();
        values.put(COL_DATE, date);
        for(Object obj : objs){

            String subName = ((String) obj).replace(" ", "_");
            values.put(subName, pf.getState(subName));

        }

        db.insert(TABLE_NAME, null, values);
    }


    private void createOptionsView(){

        PrefHandler pf = new PrefHandler(MainActivity.this);
        Object [] subjects = pf.getSubjects().toArray();
        subjectOptionsGrid.setColumnCount(2);
        subjectOptionsGrid.setRowCount(subjects.length);

        for(int i = 0,j = 0; i < subjects.length*2-1; i+=2, j++){

            DisplayMetrics metrics = getResources().getDisplayMetrics();
            int px_padding = (int)16*(metrics.densityDpi / metrics.DENSITY_DEFAULT);
            int px_textSize = (int)10*(metrics.densityDpi / metrics.DENSITY_DEFAULT);

            CheckBox checkBox = new CheckBox(MainActivity.this);
            checkBox.setPadding(px_padding,px_padding,px_padding,px_padding);
            checkBox.setId(i);

            TextView textView = new TextView(MainActivity.this);
            textView.setId(i+1);
            textView.setPadding(px_padding,px_padding,px_padding,px_padding);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, px_textSize);
            textView.setText((String)subjects[j]);

            subjectOptionsGrid.addView(checkBox, i);
            subjectOptionsGrid.addView(textView, i+1);


        }

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
                for(int i = 0; i < subjectOptionsGrid.getRowCount()*2-1; i+=2){

                    CheckBox checkBox = subjectOptionsGrid.findViewById(i);
                    if(checkBox.isChecked()){

                        TextView textView = subjectOptionsGrid.findViewById(i+1);
                        String subName = textView.getText().toString().trim();

                        if(subjects.contains(subName)){
                            Toast.makeText(MainActivity.this, subName + " Already in your day", Toast.LENGTH_LONG).show();
                        }
                        else{
                            subjects.add(subName);
                        }
                        checkBox.setChecked(false);
                    }

                }
                adapter = new ClassesListAdapter(MainActivity.this, subjects.toArray());
                dailyClassesList.setAdapter(adapter);
                dailyClassesList.setEmptyView(MainActivity.this.findViewById(R.id.emptyElement));
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
}
