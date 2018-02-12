package com.nocom.capstone_stage2;

/**
 * Created by Moha on 1/27/2018.
 */


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

class WomenRankingAdapter extends RecyclerView.Adapter<WomenRankingAdapter.resultadapterviewholder>  {
    static private ListItemClickListener mOnClickListener;


    Context mContext;
     ArrayList<RankingWomen> results;
    //  ArrayList<RankingMen> rankingMen;

    //ArrayList<RankingMen> rankingMen = new ArrayList<RankingMen>();




    public WomenRankingAdapter(ArrayList<RankingWomen> items, Context context) {

        mContext = context;
        results = items;
    }

    public WomenRankingAdapter() {

    }

    @Override
    public resultadapterviewholder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.womenrankingadapter;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new resultadapterviewholder(view);
    }

    @Override
    public void onBindViewHolder(resultadapterviewholder holder, int position) {



        final RankingWomen currentrankingWomen = results.get(position);

        String name = currentrankingWomen.getWomenname();
        int rankWomen = currentrankingWomen.getRankwomen();
        int pointsWomen = currentrankingWomen.getPointswomen();
        String nationality = currentrankingWomen.getWomennationality();




        holder.nametextview.setText(name);
        holder.rankmentextview.setText(String.valueOf(rankWomen));
        holder.pointsmentextview.setText(String.valueOf(pointsWomen));
        holder.nationalitytextview.setText(nationality);


        // holder.set1.setText(currentNews.getMhomescore());
        // holder.set2.setText(currentNews.getset2());



    }




    @Override
    public int getItemCount() {

        if (null == results) return 0;
        return results.size();

    }

    public void setWeatherData(ArrayList<RankingWomen> weatherData) {

       // Toast.makeText(mContext,"aywa ana gwa schedule fragemnt",Toast.LENGTH_SHORT).show();
        results = weatherData;
        notifyDataSetChanged();
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }


    public  class resultadapterviewholder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public TextView nametextview;
        public  TextView rankmentextview ;
        public  TextView pointsmentextview ;
        public  TextView nationalitytextview ;

        public resultadapterviewholder(View itemView) {
            super(itemView);

            nametextview = (TextView) itemView.findViewById(R.id.name);
            pointsmentextview = (TextView) itemView.findViewById(R.id.pointsmen);
            rankmentextview = (TextView) itemView.findViewById(R.id.rankmen);
            nationalitytextview = (TextView) itemView.findViewById(R.id.nationality);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }

    }
}