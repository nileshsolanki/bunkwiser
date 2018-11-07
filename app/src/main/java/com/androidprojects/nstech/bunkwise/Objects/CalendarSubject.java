package com.androidprojects.nstech.bunkwise.Objects;

public class CalendarSubject {

    String subName;
    int state;

    public CalendarSubject(String subName, int state) {
        this.subName = subName;
        this.state = state;
    }

    public String getSubName() {
        return subName;
    }

    public int getState() {
        return state;
    }
}
