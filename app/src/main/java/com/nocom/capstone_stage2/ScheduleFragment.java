package com.nocom.capstone_stage2;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Moha on 1/25/2018.
 */


public class ScheduleFragment extends Fragment {


    public static final String SHARED_PREFS_KEY = "SHARED_PREFS_KEY";
    private static final int MOVIE_LOADER_ID = 2;


    private static final String BUNDLE_RECYCLER_LAYOUT = "classname.recycler.layout";
    private final String LOG_TAG = HomeLoader.class.getName();
    public RecyclerView ScheduleRecycleView;
    long savedMillis;
    Parcelable savedRecyclerLayoutState;
    TextView emptytextview;
    Bundle bundle;
    ScheduleMainAdapter ScheduleAdapter;
    ArrayList<CollectionSchedule> recepieList;
    String savedTime;

    String DailyResult = "http://api.sportradar.us/tennis-t2/en/schedules/" + savedTime + "/results.json?api_key=regz4ypwqjdq2h43d6d66kkh";
    ProgressBar progressBar;
    public LoaderManager.LoaderCallbacks<ArrayList<CollectionSchedule>> loaderone = new LoaderManager.LoaderCallbacks<ArrayList<CollectionSchedule>>() {
        @Override
        public android.support.v4.content.Loader<ArrayList<CollectionSchedule>> onCreateLoader(int id, Bundle args) {
            // Create a new loader for the given URL

            return new ScheduleLoader(getActivity(), DailyResult);
        }

        @Override
        public void onLoadFinished(android.support.v4.content.Loader<ArrayList<CollectionSchedule>> loader, ArrayList<CollectionSchedule> data) {


            progressBar.setVisibility(View.INVISIBLE);

            if (savedRecyclerLayoutState != null) {

                ScheduleRecycleView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
            }

            if (data != null) {


///////////////// update the adapter after wayching the tutorial /////////////
                ScheduleAdapter.setWeatherData(data);


            } else {
                recepieList = data;


            }

        }

        @Override
        public void onLoaderReset(android.support.v4.content.Loader<ArrayList<CollectionSchedule>> loader) {

        }


    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.schedulefragment, container, false);
        final Intent intentThatStartedThisActivity = getActivity().getIntent();
        savedMillis = System.currentTimeMillis();

        progressBar = view.findViewById(R.id.loading_spinner);
        emptytextview = view.findViewById(R.id.empty_view);
        if (savedInstanceState != null) {



            savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);

            savedTime = savedInstanceState.getString("someVarB");
            savedMillis = savedInstanceState.getLong("someVarc");


        }


        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        savedTime = df.format(new Date());
        recepieList = new ArrayList<>();

        if (System.currentTimeMillis() >= savedMillis + 24 * 60 * 60 * 1000) {

            // 24 hours have passed


            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();

            try {
                c.setTime(sdf.parse(savedTime));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            c.add(Calendar.DATE, 1);  // number of days to add
            savedTime = sdf.format(c.getTime());
            Log.i("savedtime", savedTime);

            DailyResult = "http://api.sportradar.us/tennis-t2/en/schedules/" + savedTime + "/results.json?api_key=regz4ypwqjdq2h43d6d66kkh";


        } else {



            DailyResult = "http://api.sportradar.us/tennis-t2/en/schedules/" + savedTime + "/results.json?api_key=regz4ypwqjdq2h43d6d66kkh";
        }


        ScheduleRecycleView = view.findViewById(R.id.sechedulerecycle);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        ScheduleRecycleView.setLayoutManager(linearLayoutManager);

        ScheduleAdapter = new ScheduleMainAdapter(getActivity(), recepieList);
        ScheduleRecycleView.setAdapter(ScheduleAdapter);


        getLoaderManager().initLoader(MOVIE_LOADER_ID, null, loaderone);


        connect();

        return view;
    }

    public void connect() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (isConnected) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.restartLoader(MOVIE_LOADER_ID, bundle, loaderone);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            emptytextview.setText(getString(R.string.internetconnctionmessage));
            progressBar.setVisibility(View.INVISIBLE);

            // Update empty state with no connection error message
        }

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {


        super.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_RECYCLER_LAYOUT, ScheduleRecycleView.getLayoutManager().onSaveInstanceState());
        outState.putString("someVarB", savedTime);
        outState.putLong("someVarc", savedMillis);


    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        if (savedInstanceState != null) {
            savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
            savedTime = savedInstanceState.getString("someVarB");
            savedMillis = savedInstanceState.getLong("someVarc");

        }
    }


}