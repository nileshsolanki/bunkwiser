package com.androidprojects.nstech.bunkwise.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

public class PrefHandler {

    final static  String SUBJECTS = "subjects";
    final static  String ONDAY = "onday";
    final static  String TIMETABLE = "timetable";
    final static  String STATE = "state";
    public final static int ATTEND = 1;
    public final static int BUNK = 2;
    public final static int CANCEL = 3;

    static Context context;
    PrefHandler(){}

    public PrefHandler(Context context){
        this.context = context;
    }

    public static void addSubjects(Set<String> subjects){

        SharedPreferences sharedPreferences = context.getSharedPreferences(SUBJECTS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(SUBJECTS, subjects);
        editor.apply();

    }


    public static Set<String> getSubjects(){

        SharedPreferences sharedPreferences = context.getSharedPreferences(SUBJECTS, Context.MODE_PRIVATE);
        return sharedPreferences.getStringSet(SUBJECTS, null);

    }


    public static  void putSubjectsFor(String day, Set<String> subjects){

        SharedPreferences sharedPreferences = context.getSharedPreferences(ONDAY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(day, subjects);
        editor.apply();

    }

    public static  Set<String> getSubjectsFor(String day){

        SharedPreferences sharedPreferences = context.getSharedPreferences(ONDAY, Context.MODE_PRIVATE);
        return sharedPreferences.getStringSet(day, null);

    }

    public static void changeSubjectStats(String subName, int [] del_values){

        SharedPreferences sharedPreferences = context.getSharedPreferences(TIMETABLE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        int attended = sharedPreferences.getInt(subName+"_attend", 0);
        editor.putInt(subName+"_attend", attended+del_values[0]);

        int bunked = sharedPreferences.getInt(subName+"_bunked", 0);
        editor.putInt(subName + "_bunked", bunked+del_values[1]);

        int total = sharedPreferences.getInt(subName+"_total", 0);
        editor.putInt(subName+"_total", total+del_values[2]);


        editor.apply();
    }


    public static int[] getSubjectStats(String subName){

        int [] stats = new int[3];
        SharedPreferences sharedPreferences = context.getSharedPreferences(TIMETABLE, Context.MODE_PRIVATE);

        stats[0] = sharedPreferences.getInt(subName+"_attend", 0);
        stats[1] = sharedPreferences.getInt(subName+"_bunked", 0);
        stats[2] = sharedPreferences.getInt(subName+"_total", 0);

        return stats;

    }

    public static void clearPrevStates(){

        SharedPreferences sharedPreferences = context.getSharedPreferences(STATE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

    }


    public static void setState(String subName, int state){
        SharedPreferences sharedPreferences = context.getSharedPreferences(STATE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(subName, state);
        editor.apply();
    }


    public static int getState(String subName){
        SharedPreferences sharedPreferences = context.getSharedPreferences(STATE, Context.MODE_PRIVATE);
        return  sharedPreferences.getInt(subName, 0);

    }



}
