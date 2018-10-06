package com.androidprojects.nstech.bunkwise;

import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.androidprojects.nstech.bunkwise.Utils.PrefHandler;

import java.sql.Time;
import java.util.HashSet;
import java.util.Set;

public class SubjectsActivity extends AppCompatActivity {

    Toolbar toolbar;
    ListView subjectsList;
    FloatingActionButton fab_add;
    ArrayAdapter adapter;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.done_adding_subjects:  saveAdapterContentsToPrefrences();
            startActivity(new Intent(this, TimeTableActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.subjects_activity_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(new PrefHandler(this).getSubjects() != null){

            startActivity(new Intent(this, TimeTableActivity.class));
            finish();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        subjectsList = findViewById(R.id.lv_subjects_added);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        subjectsList.setAdapter(adapter);
        fab_add = findViewById(R.id.fab_add_subs);


        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createDialog();

            }
        });

    }

    private void createDialog() {

        SubjectDialog dialog = new SubjectDialog(this, adapter);
        dialog.show();

    }


    private void saveAdapterContentsToPrefrences() {

        int count = adapter.getCount();
        Set<String> subjectSet = new HashSet<>();
        for(int i = 0 ; i < count; i++){
            subjectSet.add((String) adapter.getItem(i).toString().replace(" ", "_"));
        }
        PrefHandler pf = new PrefHandler(SubjectsActivity.this);
        pf.addSubjects(subjectSet);

    }
}
