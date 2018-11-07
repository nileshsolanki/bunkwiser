package com.androidprojects.nstech.bunkwise;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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
        timeTableOfDay.setEmptyView(view.findViewById(R.id.emptyElement));

        return view;
    }



    public  void addToAdapter(String name){

        for (int i = 0; i < subjectsAdapter.getCount() ; i++){

            if(subjectsAdapter.getItem(i).equals(name)){

                Toast.makeText(getContext(), name + " Already added", Toast.LENGTH_LONG).show();
                return;
            }

        }
        subjectsAdapter.add(name);


    }


}
