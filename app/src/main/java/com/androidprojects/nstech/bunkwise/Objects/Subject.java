package com.androidprojects.nstech.bunkwise.Objects;

public class Subject {

    int attended, bunked;
    String subName;

    public Subject(int attended, int bunked, String subName) {
        this.attended = attended;
        this.bunked = bunked;
        this.subName = subName;
    }


    public int getAttended() {
        return attended;
    }

    public int getBunked() {
        return bunked;
    }

    public String getSubName() {
        return subName;
    }
}
