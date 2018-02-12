package com.nocom.capstone_stage2;

import android.content.Context;
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
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;

/**
 * Created by Moha on 1/25/2018.
 */



public class RankingFragment extends Fragment {

    private static final int MOVIE1_LOADER_ID = 1;
    private static final int MOVIE2_LOADER_ID = 2;
    private static final String BUNDLE_RECYCLER_LAYOUT = "classname.recycler.layout";
    final String RANKINGWEBSITE = "http://api.sportradar.us/tennis-t2/en/players/rankings.json?api_key=qcxx9ms27b2mnjvmj5db737u";
    public RecyclerView mrecyclerview;
    Button Atp;
    Button Wta;
    Parcelable savedRecyclerLayoutState;
    TextView emptytextview ;
    Bundle bundle;
    MenRankingAdapter menRankingAdapter;
    WomenRankingAdapter womenRankingAdapter;

    ArrayList<RankingMen> recepieList ;
    public LoaderManager.LoaderCallbacks<ArrayList<RankingMen>> loaderone = new LoaderManager.LoaderCallbacks<ArrayList<RankingMen>>() {
        @Override
        public android.support.v4.content.Loader<ArrayList<RankingMen>> onCreateLoader(int id, Bundle args) {
            // Create a new loader for the given URL

            return new RankingMenLoader(getActivity(), RANKINGWEBSITE);
        }

        @Override
        public void onLoadFinished(android.support.v4.content.Loader<ArrayList<RankingMen>> loader, ArrayList<RankingMen> data) {


//            progressBar.setVisibility(View.INVISIBLE);

            if (savedRecyclerLayoutState != null) {

                mrecyclerview.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
            }

            if (data != null) {


                menRankingAdapter.setWeatherData(data);


            } else {
                recepieList = data;

                Log.i("moha", "e7na gwa al else ");

            }

        }

        @Override
        public void onLoaderReset(android.support.v4.content.Loader<ArrayList<RankingMen>> loader) {

        }


    };
    ArrayList<RankingWomen> recepieList2 ;
    public LoaderManager.LoaderCallbacks<ArrayList<RankingWomen>> loadertwo = new LoaderManager.LoaderCallbacks<ArrayList<RankingWomen>>() {
        @Override
        public android.support.v4.content.Loader<ArrayList<RankingWomen>> onCreateLoader(int id, Bundle args) {
            // Create a new loader for the given URL

            return new RankingWomenLoader(getActivity(), RANKINGWEBSITE);
        }

        @Override
        public void onLoadFinished(android.support.v4.content.Loader<ArrayList<RankingWomen>> loader, ArrayList<RankingWomen> data2) {


//            progressBar.setVisibility(View.INVISIBLE);

            if (savedRecyclerLayoutState != null) {

                mrecyclerview.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
            }

            if (data2 != null) {


                womenRankingAdapter.setWeatherData(data2);


            } else {
                recepieList2 = data2;

                Log.i("moha", "e7na gwa al else ");

            }

        }

        @Override
        public void onLoaderReset(android.support.v4.content.Loader<ArrayList<RankingWomen>> loader) {

        }


    };
    TextView emptytext;
    ProgressBar progressBar;


    // loader one for menatp
    // loader two for womenatp
    private FirebaseAnalytics mFirebaseAnalytics;
    private Toast mToast;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ranking,container,false);

        if (savedInstanceState != null) {

            Toast.makeText(getContext(), "on activity created is used ", Toast.LENGTH_SHORT).show();


            savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);


        }


        progressBar = view. findViewById(R.id.loading_spinner);
        emptytext = view. findViewById(R.id.empty_view);
        mrecyclerview =  view.findViewById(R.id.mrecycle);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        mrecyclerview.setLayoutManager(linearLayoutManager);
        menRankingAdapter = new MenRankingAdapter(recepieList,getContext());
        womenRankingAdapter = new WomenRankingAdapter(recepieList2,getContext());


        Atp = view.findViewById(R.id.ATP);
        Wta = view.findViewById(R.id.WTA);
        mrecyclerview.setAdapter(menRankingAdapter);

        // loader one is for atp
        getLoaderManager().initLoader(MOVIE1_LOADER_ID, null, loaderone);


        String id = "womenranking";
        String name = "wta women ranking result";
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);


        connect();

        Atp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mrecyclerview.setAdapter(menRankingAdapter);

                // loader one is for atp
                getLoaderManager().initLoader(MOVIE1_LOADER_ID, null, loaderone);


                String id = "womenranking";
                String name = "wta women ranking result";
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID,id );
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME,name );
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);




                connect();

            }
        });


        Wta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mrecyclerview.setAdapter(womenRankingAdapter);

                // loader one is for atp
                getLoaderManager().initLoader(MOVIE2_LOADER_ID, null, loadertwo);



                connect();


            }
        });




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
            loaderManager.restartLoader(MOVIE1_LOADER_ID, bundle, loaderone);
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
        outState.putParcelable(BUNDLE_RECYCLER_LAYOUT, mrecyclerview.getLayoutManager().onSaveInstanceState());


    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        if (savedInstanceState != null) {
            savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
        }
    }





}
