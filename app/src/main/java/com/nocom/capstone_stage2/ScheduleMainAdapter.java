package com.nocom.capstone_stage2;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import static com.nocom.capstone_stage2.ScheduleFragment.SHARED_PREFS_KEY;



public class ScheduleMainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = ScheduleMainAdapter.class.getSimpleName();
    public static ArrayList<CollectionSchedule> personList;
    private final int USER = 0, IMAGE = 1;
    private Context context;

    public ScheduleMainAdapter(Context context,ArrayList<CollectionSchedule> people){
        personList = people;
        this.context=context;
        // added part
     //   setHasStableIds(true);

    }

    public void setWeatherData(ArrayList<CollectionSchedule> data) {

        personList = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if(position % 3==0){
            return R.layout.schduleadapter;// schedule=advertisment
        }else{
            return R.layout.resultadapter; //result=person
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        final RecyclerView.ViewHolder viewHolder;
        String json = new Gson().toJson(personList );
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(SHARED_PREFS_KEY, json).commit();







        switch (viewType){
            case R.layout.schduleadapter:
               View v1= LayoutInflater.from(context).inflate(R.layout.schduleadapter,parent,false);
                viewHolder=new Scheduleviewholder(v1);
                break;

            case R.layout.resultadapter:
              View  v2= LayoutInflater.from(context).inflate(R.layout.resultadapter,parent,false);
                viewHolder=new resultadapterviewholder(v2);

                break;
            default:
                v2= LayoutInflater.from(context).inflate(R.layout.schduleadapter,parent,false);
                viewHolder=new Scheduleviewholder(v2);
                break;

        }

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {



        CollectionSchedule object = personList.get(position);

        if (viewHolder instanceof Scheduleviewholder) {

            Scheduleviewholder vh1 = (Scheduleviewholder) viewHolder;
            configureViewHolder1(vh1, position);

        } else if (viewHolder instanceof resultadapterviewholder) {

            resultadapterviewholder vh2 = (resultadapterviewholder) viewHolder;
            configureViewHolder2(vh2, position);
        }
        }

    @Override
    public int getItemCount() {
        return personList.size();
    }
// added part
    @Override
    public long getItemId(int position) {
        return position;
    }

    private void configureViewHolder1(Scheduleviewholder vh1, int position) {
        CollectionSchedule user = personList.get(position);
        if (user != null) {

            vh1.player.setText(user.getMnamehome());
            vh1.VS.setText("VS");
            vh1.opponent.setText(user.getMnameaway());
        }
    }

    private void configureViewHolder2(resultadapterviewholder vh2, int position) {

        CollectionSchedule user = personList.get(position);

        if (user != null) {

            vh2.set1.setText(user.getScorehome1());
            vh2.set2.setText(user.getScoreaway1());
            vh2.set3.setText(user.getScorehome2());
            vh2.set4.setText(user.getScoreaway2());
            vh2.dash1.setText("/");
            vh2.dash2.setText("/");




        }
    }

     class resultadapterviewholder extends RecyclerView.ViewHolder{
        public  TextView set1;
        public  TextView set2 ;
         public  TextView set3 ;
         public  TextView set4 ;
         public  TextView dash1 ;
         public  TextView dash2 ;

        public resultadapterviewholder(View view){
            super(view);
            set1 = itemView.findViewById(R.id.home_score1);
            set2 = itemView.findViewById(R.id.away_score1);
            set3 = itemView.findViewById(R.id.home_score2);
            set4 = itemView.findViewById(R.id.away_scoretwo);
            dash1=itemView.findViewById(R.id.dash);
            dash2=itemView.findViewById(R.id.dash2);
        }
    }

     class Scheduleviewholder extends RecyclerView.ViewHolder{
        public  TextView player;
        public  TextView VS ;
        public  TextView opponent;

        public Scheduleviewholder(View view){
            super(view);
            player = itemView.findViewById(R.id.player);
            VS = itemView.findViewById(R.id.vs);
            opponent = itemView.findViewById(R.id.opponent);
        }
    }


    }
