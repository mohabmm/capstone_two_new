package com.nocom.capstone_stage2;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import database.MovieCursorAdapter;
import database.TennisContract;
import database.TennisDBHelper;

/**
 * Created by Moha on 9/24/2017.
 */

public class Favorites extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int MLoader = 3;
    MovieCursorAdapter tennisCursorAdapter;
    //Context context =  this ;
    //Button button ;
    //Cursor c;
    private TennisDBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_favorites);
        getLoaderManager().initLoader(MLoader, null, this);
        ListView listView = (ListView) findViewById(R.id.favoriteList);
        tennisCursorAdapter = new MovieCursorAdapter(this, null);
        listView.setAdapter(tennisCursorAdapter);
        //   button= (Button)findViewById(R.id.delete);
        dbHelper = new TennisDBHelper(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        // re-queries for all tasks
        getLoaderManager().restartLoader(MLoader, null, this);

    }
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Cursor>(this) {

            // Initialize a Cursor, this will hold all the task data
            Cursor mTaskData = null;

            // onStartLoading() is called when a loader first starts loading data
            @Override
            protected void onStartLoading() {
                if (mTaskData != null) {
                    // Delivers any previously loaded data immediately
                    deliverResult(mTaskData);
                } else {
                    // Force a new load
                    forceLoad();
                }
            }
            // loadInBackground() performs asynchronous loading of data
            @Override
            public Cursor loadInBackground() {
                // Will implement to load data
                try {
                    return getContentResolver().query(TennisContract.TennisEntry.CONTENT_URI,
                            null,
                            null,
                            null,
                            TennisContract.TennisEntry.COLUMN_DESCRIBTION);

                } catch (Exception e) {
                    Log.e("ERROR", "Failed to asynchronously load data.");
                    e.printStackTrace();
                    return null;
                }
            }

            // deliverResult sends the result of the load, a Cursor, to the registered listener
            public void deliverResult(Cursor data) {
                mTaskData = data;
                super.deliverResult(data);
            }
        };
    }
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        tennisCursorAdapter.swapCursor(data);
      //  HomeFragment.isFavorite = true;
        tennisCursorAdapter.notifyDataSetChanged();
    }
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        tennisCursorAdapter.swapCursor(null);
    }
}
