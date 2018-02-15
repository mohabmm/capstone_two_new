package com.nocom.capstone_stage2;

import android.os.AsyncTask;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import org.json.JSONException;

public class MyService extends JobService {

    private AsyncTask<Void, Void, Void> mFetchWeatherTask;

    /**
     * The entry point to your Job. Implementations should offload work to another thread of
     * execution as soon as possible.
     * <p>
     * This is called by the Job Dispatcher to tell us we should start our job. Keep in mind this
     * method is run on the application's main thread, so we need to offload work to a background
     * thread.
     *
     * @return whether there is more work remaining.
     */
    @Override
    public boolean onStartJob(final JobParameters jobParameters) {

        mFetchWeatherTask = new AsyncTask<Void, Void, Void>() {
            String website = "http://api.nytimes.com/svc/search/v2/articlesearch.json?q=tennis&?sort=newest&api-key=23b843ce687642739ffbbd75bc779a84";

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    QueryUtlisHome.extractFeatureFromJson(website);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                jobFinished(jobParameters, false);

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                jobFinished(jobParameters, false);
            }
        };

        mFetchWeatherTask.execute();
        return true;
    }

    /**
     * Called when the scheduling engine has decided to interrupt the execution of a running job,
     * most likely because the runtime constraints associated with the job are no longer satisfied.
     *
     * @return whether the job should be retried
     */
    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        if (mFetchWeatherTask != null) {
            mFetchWeatherTask.cancel(true);
        }
        return true;
    }
}