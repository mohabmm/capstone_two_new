package com.nocom.capstone_stage2;

/**
 * Created by Moha on 1/31/2018.
 */

public class RankingMen {

    int rankingmen;
    int pointsmen;
    String menname;
    String mennationality;

    public RankingMen(int lrankmen, int lpointsmen, String lmenname, String lmennationality) {

        rankingmen = lrankmen;
        pointsmen = lpointsmen;
        menname = lmenname;
        mennationality = lmennationality;

    }

    public int getPointsmen() {
        return pointsmen;
    }

    public int getRankingmen() {
        return rankingmen;
    }

    public String getMenname() {
        return menname;
    }

    public String getMennationality() {
        return mennationality;
    }


}
