package com.androidprojects.nstech.bunkwise.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.androidprojects.nstech.bunkwise.R;
import com.androidprojects.nstech.bunkwise.Utils.PrefHandler;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static com.androidprojects.nstech.bunkwise.Utils.PrefHandler.ATTEND;
import static com.androidprojects.nstech.bunkwise.Utils.PrefHandler.BUNK;
import static com.androidprojects.nstech.bunkwise.Utils.PrefHandler.CANCEL;
import static com.androidprojects.nstech.bunkwise.Utils.PrefHandler.changeSubjectStats;
import static com.androidprojects.nstech.bunkwise.Utils.PrefHandler.getState;
import static com.androidprojects.nstech.bunkwise.Utils.PrefHandler.getSubjectStats;
import static com.androidprojects.nstech.bunkwise.Utils.PrefHandler.setState;

public class ClassesListAdapter extends BaseSwipeAdapter {

    Context context;
    Object[] subjectsList;
    public ClassesListAdapter(Context context, Object[] subjectsList){
        this.context = context;
        this.subjectsList = subjectsList;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public View generateView(int position, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.main_activity_list_item, null);
    }

    @Override
    public void fillValues(int position, View convertView) {

        PrefHandler prefHandler = new PrefHandler(context);
        attachListeners(convertView, position, prefHandler);

        String subjectName = (String)subjectsList[position];
        TextView subName = convertView.findViewById(R.id.tv_subName);
        TextView advice = convertView.findViewById(R.id.tv_advice);
        TextView percentage = convertView.findViewById(R.id.tv_sub_percentage);

        int stats[] = getSubjectStats(subjectName);
        subName.setText(subjectName);

        double percent = 0;
        try {
            percent = stats[0] * 100.00 / stats[2];
        }catch (Exception e){
            percent = 100;
        }
        percentage.setText((int)percent+"");

    }

    private void attachListeners(final View convertView, final int position, final PrefHandler prefHandler) {
        final String subName = (String) subjectsList[position];
        final int state = getState(subName);
        CardView attend = convertView.findViewById(R.id.btn_attend);
        CardView bunked = convertView.findViewById(R.id.btn_bunked);
        CardView cancelled = convertView.findViewById(R.id.btn_cancelled);


        attend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Clicked attend for "+subjectsList[position], Toast.LENGTH_SHORT).show();
                if(state == BUNK){
                    changeSubjectStats(subName, new int[]{1, -1, 0});
                }else if(state == CANCEL || state == 0){
                    changeSubjectStats(subName, new int[]{1, 0, 1});
                }
                setState(subName, ATTEND);
                fillValues(position, convertView);
            }
        });




        bunked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Clicked bunked for "+subjectsList[position], Toast.LENGTH_SHORT).show();
                if(state == ATTEND){
                    changeSubjectStats(subName, new int[]{-1, 1, 0});
                }
                else if(state == CANCEL || state == 0){
                    changeSubjectStats(subName, new int[]{0, 1, 1});
                }
                setState(subName, BUNK);
                fillValues(position, convertView);
            }
        });



        cancelled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Clicked cancel for "+subjectsList[position], Toast.LENGTH_SHORT).show();
                //prefHandler.changeSubjectStats(subName, new int[]{0, 0, 0});
                if(state == ATTEND){
                    changeSubjectStats(subName, new int[]{-1, 0, -1});
                }
                else if(state == BUNK){
                    changeSubjectStats(subName, new int[]{0, -1, -1});
                }
                setState(subName, CANCEL);
                fillValues(position, convertView);
            }
        });

    }

    @Override
    public int getCount() {
        return subjectsList.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
}