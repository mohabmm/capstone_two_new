package com.nocom.capstone_stage2;

/**
 * Created by Moha on 1/28/2018.
 */

public class Result {
    String mhomescore ;
    String mawayScore;
    public Result(String home_score, String away_score) {
        mhomescore=home_score;
        mawayScore=away_score;
    }

    public String getset2() {
        return mawayScore;
    }

    public String getMhomescore() {
        return mhomescore;
    }
}
