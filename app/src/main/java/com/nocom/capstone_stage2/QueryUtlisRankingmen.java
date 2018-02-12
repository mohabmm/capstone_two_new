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


public final class QueryUtlisRankingmen {

    String weburl;


    static final String LOG_TAG = QueryUtlisSchedule.class.getSimpleName();

    private QueryUtlisRankingmen() {
    }
    public static ArrayList<RankingMen> featchrecipedata(String requestUrl) throws JSONException {


        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);

        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        ArrayList<RankingMen> moha = extractFeatureFromJson(jsonResponse);//,jsonResponse2);

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

    private static ArrayList<RankingMen> extractFeatureFromJson(String tennisjson) throws JSONException {
        if (TextUtils.isEmpty(tennisjson)) {
            return null;
        }
           ArrayList<RankingWomen> rankingWomenArrayList = new ArrayList<>();
        ArrayList<RankingMen> list = new ArrayList<>();
            ArrayList<RankingMen> rankingMenArrayList = new ArrayList<>();

        try {

            JSONObject rootjsonobject = new JSONObject(tennisjson);
            JSONArray rankings = rootjsonobject.getJSONArray("rankings");

            for (int i = 0; i<rankings.length(); i++){


          //      if(i==0){

                    // here is women ranking

                   // JSONObject currentrankingwomen = rankings.getJSONObject(0);
                 //   JSONArray player_rankingswomen = currentrankingwomen.getJSONArray("player_rankings");
                  /*  for(int j=0;j<player_rankingswomen.length();j++){

                        JSONObject currentplayer_rankingswomen = player_rankingswomen.getJSONObject(j);
                        int rankwomen = currentplayer_rankingswomen.getInt("rank");
                        int pointswomen = currentplayer_rankingswomen.getInt("points");
                        JSONObject playerwomen = currentplayer_rankingswomen.getJSONObject("player");
                        String womenname =  playerwomen.getString("name");
                        String womennationality = playerwomen.getString("nationality");
                        RankingWomen rankingWomen = new RankingWomen(rankwomen,pointswomen,womenname,womennationality);
                        rankingWomenArrayList.add(rankingWomen);





                    }

                }
*/
                if (i==1){


                    //  here is men
                   // JSONObject currentrankingmen = rankings.getJSONObject(1);

                    JSONObject currentrankingmen = rankings.getJSONObject(1);
                    JSONArray player_rankingsmen = currentrankingmen.getJSONArray("player_rankings");
                    for(int j=0;j<player_rankingsmen.length();j++){

                        JSONObject currentplayer_rankingsmen = player_rankingsmen.getJSONObject(j);
                        int rankmen = currentplayer_rankingsmen.getInt("rank");
                        int pointsmen = currentplayer_rankingsmen.getInt("points");
                        JSONObject playermen = currentplayer_rankingsmen.getJSONObject("player");
                        String menname =  playermen.getString("name");
                        String mennationality = playermen.getString("nationality");
                        RankingMen rankingmen = new RankingMen(rankmen,pointsmen,menname,mennationality);
                        list.add(rankingmen);
                        Log.i("player men ",menname);
                        Log.i("ranking men ",String.valueOf(rankingmen));





                    }




                }

               // RankingMen overallRanking = new RankingMen(rankingWomenArrayList,rankingMenArrayList);
                //list.add(overallRanking);

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

