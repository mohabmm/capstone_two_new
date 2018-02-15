package com.nocom.capstone_stage2;

import android.content.Context;
import android.os.Looper;
import android.support.v4.content.AsyncTaskLoader;

import org.json.JSONException;

import java.util.ArrayList;

public class HomeLoader extends AsyncTaskLoader<ArrayList<Home>> {
    private static final String LOG_TAG = HomeLoader.class.getName();
    ArrayList<Home> mNewsData;
    private String murl;

    public HomeLoader(Context context, String url) {
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
    public ArrayList<Home> loadInBackground() {

        if (Looper.myLooper() == null)
            Looper.prepare();
        // Perform the network request, parse the response, and extract a list of movies.
        ArrayList<Home> movies = null;
        try {
            movies = QueryUtlisHome.featchrecipedata(murl);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movies;
    }
}
