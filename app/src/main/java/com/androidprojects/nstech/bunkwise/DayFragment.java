package com.androidprojects.nstech.bunkwise;

import android.animation.Animator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.androidprojects.nstech.bunkwise.R;

public class DayFragment extends Fragment {

//    FloatingActionButton fab;
    View view;
    ArrayAdapter<String> subjectsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_day, container, false);
        ListView timeTableOfDay = view.findViewById(R.id.lv_time_table);
        subjectsAdapter = new ArrayAdapter<>(container.getContext(), android.R.layout.simple_list_item_1);
        timeTableOfDay.setAdapter(subjectsAdapter);


        return view;
    }



    public  void addToAdapter(String name){

        subjectsAdapter.add(name);

    }


}
