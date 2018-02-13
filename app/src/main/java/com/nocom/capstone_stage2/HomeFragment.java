package com.nocom.capstone_stage2;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

import java.util.ArrayList;

/**
 * Created by Moha on 1/25/2018.
 */


public class HomeFragment extends Fragment {


    public static final String PLAY_BACK_POSTION = "playbackpstion";
    private static final int MOVIE_LOADER_ID = 1;
    private static final String JOB_TAG = "my_job_tag";
    private static final String BUNDLE_RECYCLER_LAYOUT = "classname.recycler.layout";
    static Cursor c;
    final String ARTICLESWebsite = "http://api.nytimes.com/svc/search/v2/articlesearch.json?q=tennis&?sort=newest&api-key=23b843ce687642739ffbbd75bc779a84";
    private final String LOG_TAG = HomeLoader.class.getName();
    //  ArrayList<Recipe> mNewsData;
    public RecyclerView mrecyclerview;
    public boolean isFavorite = true;
    public FirebaseJobDispatcher jobDispatcher;
    Parcelable savedRecyclerLayoutState;
    TextView emptytextview;
    Bundle bundle;
    HomeAdapter mMainAdapter;
    ArrayList<Home> recepieList;
    TextView emptytext;
    ProgressBar progressBar;
    public LoaderManager.LoaderCallbacks<ArrayList<Home>> loaderone = new LoaderManager.LoaderCallbacks<ArrayList<Home>>() {
        @Override
        public android.support.v4.content.Loader<ArrayList<Home>> onCreateLoader(int id, Bundle args) {
            // Create a new loader for the given URL

            return new HomeLoader(getActivity(), ARTICLESWebsite);
        }

        @Override
        public void onLoadFinished(android.support.v4.content.Loader<ArrayList<Home>> loader, ArrayList<Home> data) {


            progressBar.setVisibility(View.INVISIBLE);

            if (savedRecyclerLayoutState != null) {


                mrecyclerview.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
            }

            if (data != null) {


                mMainAdapter.setWeatherData(data);


            } else {
                recepieList = data;



            }

        }

        @Override
        public void onLoaderReset(android.support.v4.content.Loader<ArrayList<Home>> loader) {

        }


    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homefragment, container, false);


        if (savedInstanceState != null) {


            savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);


            isFavorite = savedInstanceState.getBoolean(PLAY_BACK_POSTION);


        }


        progressBar = view.findViewById(R.id.loading_spinner);
        emptytext = view.findViewById(R.id.empty_view);
        mrecyclerview = view.findViewById(R.id.mrecycle);

        jobDispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(getContext()));


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        mrecyclerview.setLayoutManager(linearLayoutManager);
        mMainAdapter = new HomeAdapter(recepieList, getContext());
        mrecyclerview.setAdapter(mMainAdapter);

        getLoaderManager().initLoader(MOVIE_LOADER_ID, null, loaderone);


        connect();

        startjob();


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

            emptytextview.setText("no onternet conection ");
            progressBar.setVisibility(View.INVISIBLE);

            // Update empty state with no connection error message
        }

    }


    public void startjob() {

        // here we can sepecify the job paramters

        Job job = jobDispatcher.newJobBuilder().
                setService(MyService.class).
                setLifetime(Lifetime.FOREVER).
                // that means i want the job to be repeated
                        setRecurring(true).
                        setTag(JOB_TAG).
                // i want to start the job in 10 seconds and i want to repeat the job every 180 seconds
                        setTrigger(Trigger.executionWindow(10, 180)).
                        setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL).
                        setConstraints(Constraint.ON_ANY_NETWORK).
                        setReplaceCurrent(false).build();
        jobDispatcher.mustSchedule(job);
        Toast.makeText(getContext(), "Job Scheduled from capstone started job ", Toast.LENGTH_SHORT).show();


    }


    @Override
    public void onSaveInstanceState(Bundle outState) {


        super.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_RECYCLER_LAYOUT, mrecyclerview.getLayoutManager().onSaveInstanceState());

        outState.putBoolean(PLAY_BACK_POSTION, isFavorite);


    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        if (savedInstanceState != null) {
            savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
        }
    }


}
