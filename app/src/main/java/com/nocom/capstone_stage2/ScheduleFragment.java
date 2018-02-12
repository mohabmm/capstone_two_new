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
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Moha on 1/25/2018.
 */



public class ScheduleFragment extends Fragment  {


    public static final String SHARED_PREFS_KEY = "SHARED_PREFS_KEY";
    private static final int MOVIE_LOADER_ID = 2;


    // private ArrayList<Object> objects = new ArrayList<>();
    // update the website
    // String DailyScheduleWebsite = "http://api.sportradar.us/tennis-t2/en/schedules/2018-02-11/schedule.json?api_key=qcxx9ms27b2mnjvmj5db737u";
    // String DailyScheduleWebsite2 = "http://api.sportradar.us/tennis-t2/en/schedules/2018-02-10/schedule.json?api_key=qcxx9ms27b2mnjvmj5db737u";
    private static final String BUNDLE_RECYCLER_LAYOUT = "classname.recycler.layout";

    //String DailyResult = "http://api.sportradar.us/tennis-t2/en/schedules/2018-01-28/results.json?api_key=qcxx9ms27b2mnjvmj5db737u";
    private final String LOG_TAG = HomeLoader.class.getName();
    //  ArrayList<Recipe> mNewsData;
    public RecyclerView ScheduleRecycleView;
    List<Schedule> listItems = new ArrayList<>();
    long savedMillis;
    Parcelable savedRecyclerLayoutState;
    TextView emptytextview ;
    Bundle bundle;
    ScheduleMainAdapter ScheduleAdapter;
    // the problem i think is here
    ArrayList<CollectionSchedule> recepieList ;
    String savedTime;
    //ScheduleMainAdapter adapter ;
    // remeber to remove and change this trial
    String trial = "http://api.sportradar.us/tennis-t2/en/schedules/2018-01-28/results.json?api_key=qcxx9ms27b2mnjvmj5db737u";

    String DailyResult = "http://api.sportradar.us/tennis-t2/en/schedules/" + savedTime + "/results.json?api_key=qcxx9ms27b2mnjvmj5db737u";
    public LoaderManager.LoaderCallbacks<ArrayList<CollectionSchedule>> loaderone = new LoaderManager.LoaderCallbacks<ArrayList<CollectionSchedule>>() {
        @Override
        public android.support.v4.content.Loader<ArrayList<CollectionSchedule>> onCreateLoader(int id, Bundle args) {
            // Create a new loader for the given URL

            return new ScheduleLoader(getActivity(), trial);
        }

        @Override
        public void onLoadFinished(android.support.v4.content.Loader<ArrayList<CollectionSchedule>> loader, ArrayList<CollectionSchedule> data) {


//            progressBar.setVisibility(View.INVISIBLE);

            if (savedRecyclerLayoutState != null) {

                ScheduleRecycleView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
            }

            if (data != null) {

                Toast.makeText(getContext(), "datanotnull", Toast.LENGTH_SHORT).show();

///////////////// update the adapter after wayching the tutorial /////////////
                ScheduleAdapter.setWeatherData(data);

                //  ScheduleRecycleView.setAdapter(ScheduleAdapter);


            } else {
                recepieList = data;

                Log.i("moha", "e7na gwa al else ");

            }

        }

        @Override
        public void onLoaderReset(android.support.v4.content.Loader<ArrayList<CollectionSchedule>> loader) {

        }


    };
    //   LoaderManager loaderManager;
    TextView emptytext;
    ProgressBar progressBar;
    private Toast mToast;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.schedulefragment, container, false);
        final Intent intentThatStartedThisActivity = getActivity().getIntent();
        savedMillis = System.currentTimeMillis();

        double t0 = System.currentTimeMillis(), t1;


        if (savedInstanceState != null) {

            Toast.makeText(getContext(), "on activity created is used ", Toast.LENGTH_SHORT).show();


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

            DailyResult = "http://api.sportradar.us/tennis-t2/en/schedules/" + savedTime + "/results.json?api_key=qcxx9ms27b2mnjvmj5db737u";

            //DailyScheduleWebsite = "http://api.sportradar.us/tennis-t2/en/schedules/"+savedTime+"/schedule.json?api_key=qcxx9ms27b2mnjvmj5db737u";


        }


        //    http://api.sportradar.us/tennis-t2/en/schedules/2018-02-11/schedule.json?api_key=qcxx9ms27b2mnjvmj5db737u



        else {



            Toast.makeText(getContext(),"time dont pass",Toast.LENGTH_SHORT).show();
            // the date havenot changed query for current date

            DailyResult = "http://api.sportradar.us/tennis-t2/en/schedules/" + savedTime + "/results.json?api_key=qcxx9ms27b2mnjvmj5db737u";
        }












        //   progressBar = view. findViewById(R.id.schedulespinner);
        //   emptytext = view. findViewById(R.id.sechudleempty);
        ScheduleRecycleView =  view.findViewById(R.id.sechedulerecycle);



        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        ScheduleRecycleView.setLayoutManager(linearLayoutManager);
        // update the adapter aftr watchin the tutorial
        ScheduleAdapter = new ScheduleMainAdapter(getActivity(), recepieList);
        ScheduleRecycleView.setAdapter(ScheduleAdapter);

        //  ScheduleRecycleView.setItemViewCacheSize(20);

        // ScheduleRecycleView.setNestedScrollingEnabled(false);


        getLoaderManager().initLoader(MOVIE_LOADER_ID, null, loaderone);










        connect();

        return view ;
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
            //   emptytext.setText("No Internet Connection ");
            emptytextview.setText("no onternet conection ");
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