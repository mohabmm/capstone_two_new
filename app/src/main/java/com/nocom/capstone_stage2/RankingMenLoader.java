package com.nocom.capstone_stage2;

import android.content.Context;
import android.os.Looper;
import android.support.v4.content.AsyncTaskLoader;

import org.json.JSONException;

import java.util.ArrayList;

public class RankingMenLoader extends AsyncTaskLoader<ArrayList<RankingMen>> {
    private static final String LOG_TAG = ScheduleLoader.class.getName();
    ArrayList<RankingMen> mNewsData;
    private String murl;

    public RankingMenLoader(Context context, String url) {
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
    public ArrayList<RankingMen> loadInBackground() {

        if (Looper.myLooper() == null)
            Looper.prepare();
        // Perform the network request, parse the response, and extract a list of movies.
        ArrayList<RankingMen> movies = null;
        try {
            movies = QueryUtlisRankingmen.featchrecipedata(murl);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movies;
    }
}
