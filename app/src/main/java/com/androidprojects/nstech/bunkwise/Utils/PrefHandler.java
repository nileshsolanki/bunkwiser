package com.androidprojects.nstech.bunkwise.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

public class PrefHandler {

    final static  String SUBJECTS = "subjects";
    final static  String ONDAY = "onday";
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


}
