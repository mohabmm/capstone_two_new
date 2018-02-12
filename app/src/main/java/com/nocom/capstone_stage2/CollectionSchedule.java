package com.nocom.capstone_stage2;

import java.util.ArrayList;

/**
 * Created by Moha on 1/29/2018.
 */

public class CollectionSchedule {
    ArrayList name;
    String mnamehome;
    String mnameaway;

    String scorehome1;
    String scoreaway1;

    String scorehome2;
    String scoreaway2;








    public CollectionSchedule(String name, String away, String homescore, String awayscore, String homescore2, String awayscore2) {

        mnamehome=name;
        mnameaway=away;
        scorehome1=homescore;
        scoreaway1=awayscore;
        scorehome2=homescore2;
        scoreaway2=awayscore2;




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


}