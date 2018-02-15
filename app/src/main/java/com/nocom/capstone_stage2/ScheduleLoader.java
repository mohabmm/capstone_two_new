package com.nocom.capstone_stage2;

import android.content.Context;
import android.os.Looper;
import android.support.v4.content.AsyncTaskLoader;

import org.json.JSONException;

import java.util.ArrayList;

public class ScheduleLoader extends AsyncTaskLoader<ArrayList<CollectionSchedule>> {
    private static final String LOG_TAG = ScheduleLoader.class.getName();
    ArrayList<CollectionSchedule> mNewsData;
    private String murl;

    public ScheduleLoader(Context context, String url) {
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
    public ArrayList<CollectionSchedule> loadInBackground() {

        if (Looper.myLooper() == null)
            Looper.prepare();
        // Perform the network request, parse the response, and extract a list of movies.
        ArrayList<CollectionSchedule> movies = null;
        try {
            movies = QueryUtlisSchedule.featchrecipedata(murl);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movies;
    }
}
