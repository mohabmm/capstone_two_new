package com.nocom.capstone_stage2;

import android.content.Context;
import android.os.Looper;
import android.support.v4.content.AsyncTaskLoader;

import org.json.JSONException;

import java.util.ArrayList;

public class RankingWomenLoader extends AsyncTaskLoader<ArrayList<RankingWomen>> {
    private static final String LOG_TAG = ScheduleLoader.class.getName();
    ArrayList<RankingWomen> mNewsData;
    private String murl;

    public RankingWomenLoader(Context context, String url) {
        super(context);
        murl = url;
    }

    @Override
    protected void onStartLoading() {

        if (mNewsData != null) {
            deliverResult(mNewsData);
        } else {
            forceLoad();

        }
    }

    @Override
    public ArrayList<RankingWomen> loadInBackground() {

        if (Looper.myLooper() == null)
            Looper.prepare();
        // Perform the network request, parse the response, and extract a list of movies.
        ArrayList<RankingWomen> movies = null;
        try {
            movies = QueryUtlisRankingWomen.featchrecipedata(murl);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movies;
    }
}
