package com.nocom.capstone_stage2;

/**
 * Created by Moha on 1/27/2018.
 */


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

class MenRankingAdapter extends RecyclerView.Adapter<MenRankingAdapter.resultadapterviewholder> {
    static private ListItemClickListener mOnClickListener;


    Context mContext;
    ArrayList<RankingMen> results;


    public MenRankingAdapter(ArrayList<RankingMen> items, Context context) {

        mContext = context;
        results = items;
    }

    public MenRankingAdapter() {

    }

    @Override
    public resultadapterviewholder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.menrankingadapter;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new resultadapterviewholder(view);
    }

    @Override
    public void onBindViewHolder(resultadapterviewholder holder, int position) {


        final RankingMen currentrankingmen = results.get(position);

        String name = currentrankingmen.getMenname();
        int rankmen = currentrankingmen.getRankingmen();
        int pointsmen = currentrankingmen.getPointsmen();
        String nationality = currentrankingmen.getMennationality();


        holder.nametextview.setText(name);
        holder.rankmentextview.setText(String.valueOf(rankmen));
        holder.pointsmentextview.setText(String.valueOf(pointsmen));
        holder.nationalitytextview.setText(nationality);


    }


    @Override
    public int getItemCount() {

        if (null == results) return 0;
        return results.size();

    }

    public void setWeatherData(ArrayList<RankingMen> weatherData) {

        results = weatherData;
        notifyDataSetChanged();
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }


    public class resultadapterviewholder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public TextView nametextview;
        public TextView rankmentextview;
        public TextView pointsmentextview;
        public TextView nationalitytextview;

        public resultadapterviewholder(View itemView) {
            super(itemView);

            nametextview = itemView.findViewById(R.id.name);
            pointsmentextview = itemView.findViewById(R.id.pointsmen);
            rankmentextview = itemView.findViewById(R.id.rankmen);
            nationalitytextview = itemView.findViewById(R.id.nationality);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {


            Log.i((mContext.getString(R.string.button_clicked)), (mContext.getString(R.string.button_clicked)));

        }

    }
}