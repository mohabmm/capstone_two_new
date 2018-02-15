package com.nocom.capstone_stage2;

/**
 * Created by Moha on 1/31/2018.
 */

public class RankingWomen {

    int rankwomen;
    int pointswomen;
    String womenname;
    String womennationality;


    public RankingWomen(int mrankwomen, int mpointswomen, String mwomenname, String mwomennationality) {

        rankwomen = mrankwomen;
        pointswomen = mpointswomen;
        womenname = mwomenname;
        womennationality = mwomennationality;
    }

    public int getPointswomen() {
        return pointswomen;
    }

    public int getRankwomen() {
        return rankwomen;
    }

    public String getWomenname() {
        return womenname;
    }

    public String getWomennationality() {
        return womennationality;
    }


}
