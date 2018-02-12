package com.nocom.capstone_stage2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;



public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new MyWidgetRemoteViewsFactory(this.getApplicationContext());
    }

    class MyWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        private Context context;


        public MyWidgetRemoteViewsFactory(Context context) {
            this.context = context;



        }


        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String json = preferences.getString(ScheduleFragment.SHARED_PREFS_KEY, "");
            if (!json.equals("")) {
                Gson gson = new Gson();
                ScheduleMainAdapter.personList= gson.fromJson(json, new TypeToken<ArrayList<CollectionSchedule>>() {
                }.getType());



            }
        }


        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            if (ScheduleMainAdapter.personList != null) {
                return ScheduleMainAdapter.personList.size();
            }
            else return 0;

        }


        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.qw);
            rv.setTextViewText(R.id.player, ScheduleMainAdapter.personList.get(position).getMnamehome());
            rv.setTextViewText(R.id.vs,"vs");
            rv.setTextViewText(R.id.opponent, ScheduleMainAdapter.personList.get(position).getMnameaway());
            //  rv.setTextViewText(R.id.text,"upcoming matches");


            Intent intent = new Intent(context, WidgetService.class);
            rv.setRemoteAdapter(R.id.widget_list_view, intent);

            Intent startChildActivityIntent = new Intent(context, MainActivity.class);




            Bundle extras = new Bundle();
            extras.putInt(CollectionWidget.EXTRA_ITEM,position);
            Intent fillintenet = new Intent();
            fillintenet.putExtras(extras);

            rv.setOnClickFillInIntent(R.id.df,fillintenet);


            return rv;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return ScheduleMainAdapter.personList.size();
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}