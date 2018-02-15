package com.nocom.capstone_stage2;

import java.util.ArrayList;

public class CollectionSchedule {
    ArrayList name;
    String mnamehome;
    String mnameaway;

    String scorehome1;
    String scoreaway1;

    String scorehome2;
    String scoreaway2;


    String scorehome3;
    String scoreaway3;


    public CollectionSchedule(String name, String away, String homescore, String awayscore, String homescore2, String awayscore2) {

        mnamehome = name;
        mnameaway = away;
        scorehome1 = homescore;
        scoreaway1 = awayscore;
        scorehome2 = homescore2;
        scoreaway2 = awayscore2;


    }

    public CollectionSchedule(String name, String away, String homescore, String awayscore, String homescore2, String awayscore2, String homescore3, String awayscore3) {

        mnamehome = name;
        mnameaway = away;
        scorehome1 = homescore;
        scoreaway1 = awayscore;
        scorehome2 = homescore2;
        scoreaway2 = awayscore2;
        scorehome3 = homescore3;
        scoreaway3 = awayscore3;


    }


    public String getMnameaway() {
        return mnameaway;
    }

    public String getMnamehome() {
        return mnamehome;
    }

    public String getScoreaway1() {
        return scoreaway1;
    }

    public String getScoreaway2() {
        return scoreaway2;
    }

    public String getScorehome1() {
        return scorehome1;
    }

    public String getScorehome2() {
        return scorehome2;
    }

    public String getScorehome3() {
        return scorehome3;
    }

    public String getScoreaway3() {
        return scoreaway3;
    }
}