package com.nocom.capstone_stage2;

import java.util.ArrayList;

/**
 * Created by Moha on 1/31/2018.
 */

public class OverallRanking {

    ArrayList rankingWomenArrayList;
    ArrayList rankingMenArrayList;

    public OverallRanking(ArrayList<RankingWomen> mrankingWomenArrayList, ArrayList<RankingMen> mrankingMenArrayList) {

        rankingWomenArrayList=mrankingWomenArrayList;
        rankingMenArrayList=mrankingMenArrayList;

    }

    public ArrayList getRankingMenArrayList() {
        return rankingMenArrayList;
    }

    public ArrayList getRankingWomenArrayList() {
        return rankingWomenArrayList;
    }
}
