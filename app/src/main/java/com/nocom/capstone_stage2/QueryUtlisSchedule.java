package com.nocom.capstone_stage2;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;


public final class QueryUtlisSchedule {

    static final String LOG_TAG = QueryUtlisSchedule.class.getSimpleName();
    String weburl;

    private QueryUtlisSchedule() {
    }

    public static ArrayList<CollectionSchedule> featchrecipedata(String requestUrl) throws JSONException {


        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);

        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        ArrayList<CollectionSchedule> moha = extractFeatureFromJson(jsonResponse);//,jsonResponse2);

        return moha;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {

                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the tennis news JSON results.", e);


        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.

                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }

        return output.toString();
    }

    private static ArrayList<CollectionSchedule> extractFeatureFromJson(String tennisjson) throws JSONException {
        if (TextUtils.isEmpty(tennisjson)) {
            return null;
        }

        ArrayList<CollectionSchedule> list = new ArrayList<>();
        try {

            JSONObject rootjsonobject = new JSONObject(tennisjson);
            JSONArray results = rootjsonobject.getJSONArray("results");
            String name = null;

            String homescore = null;
            String awayscore = null;
            for (int i = 0; i < results.length(); i++) {

                JSONObject currentresult = results.getJSONObject(i);
                JSONArray period_scores = null;
                JSONObject currentSportEvent = currentresult.getJSONObject("sport_event");
                JSONArray competitors = currentSportEvent.getJSONArray("competitors");
                JSONObject currentSportEventStatus = currentresult.getJSONObject("sport_event_status");


                String homescore2 = null;
                String awayscore2 = null;
                String homescore3 = null;
                String awayscore3 = null;
                if (!(currentSportEventStatus.optString("period_scores").equals(""))) {


                    period_scores = currentSportEventStatus.optJSONArray("period_scores");


                    for (int k = 0; k < 1; k++) {


                        JSONObject currentperiodscore = period_scores.getJSONObject(k);
                        homescore = currentperiodscore.optString("home_score");
                        awayscore = currentperiodscore.optString("away_score");

                    }

                    if (!(currentSportEventStatus.optString("period_scores").equals(""))) {

                        for (int k = 1; k < 2; k++) {


                            JSONObject currentperiodscore = period_scores.getJSONObject(k);
                            homescore2 = currentperiodscore.optString("home_score");
                            awayscore2 = currentperiodscore.optString("away_score");

                        }
                    }


                    for (int k = 2; k <= 3; k++) {


                        if (!(currentSportEventStatus.optString("period_scores").equals(""))) {
                            JSONObject currentperiodscore = period_scores.optJSONObject(k);
                            if (currentperiodscore != null) {
                                homescore3 = currentperiodscore.optString("home_score");
                                awayscore3 = currentperiodscore.optString("away_score");

                            }

                        }
                    }


                    //   mySchedule = new ArrayList<Schedule>(); // used to get the player names

                    for (int j = 0; j < 1; j++) {

                        JSONObject currentcompetitors = competitors.getJSONObject(j);
                        name = currentcompetitors.getString("name");
                        Log.i("the player name is ", name);
                    }


                }


                String away = null;
                for (int j = 1; j < 2; j++) {

                    JSONObject currentcompetitors = competitors.getJSONObject(j);
                    away = currentcompetitors.getString("name");

                    Log.i("the player name is ", name);


                }


                if (homescore3 != null && awayscore3 != null) {

                    CollectionSchedule collection = new CollectionSchedule(name, away, homescore, awayscore, homescore2, awayscore2, homescore3, awayscore3);
                    list.add(collection);
                } else {


                    CollectionSchedule collection = new CollectionSchedule(name, away, homescore, awayscore, homescore2, awayscore2);
                    list.add(collection);
                }
            }
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the Movie JSON results", e);
        }
        return list;


    }
}

