package com.androidprojects.nstech.bunkwise.Utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatesReaderDbHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "AttendanceRecord";
    public static String [] COLUMNS;
    public static final String COL_DATE = "date";
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "DatesReader.db";

    public static String SQL_CREATE_ENTRIES = "CREATE TABLE "+ TABLE_NAME + "( "+COL_DATE +" TEXT, ";


    public DatesReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        Object objects[] = new PrefHandler(context).getSubjects().toArray();
        String [] subjects = new String[objects.length];
        COLUMNS = subjects;

        for(int i = 0 ; i < objects.length ; i++){

            subjects[i] = ((String) objects[i]).replace(" ", "_");

        }

        for(String colName : subjects){

            SQL_CREATE_ENTRIES += colName + " TEXT,";

        }

        SQL_CREATE_ENTRIES = SQL_CREATE_ENTRIES.substring(0, SQL_CREATE_ENTRIES.length()-1);
        SQL_CREATE_ENTRIES += ");";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
