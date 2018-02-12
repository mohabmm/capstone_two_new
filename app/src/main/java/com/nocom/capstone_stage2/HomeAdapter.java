
package com.nocom.capstone_stage2;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import database.TennisContract;

class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.adapterViewHolder> {
    static private ListItemClickListener mOnClickListener;
    public FloatingActionButton floatingActionButton;
    public Button button;
    Cursor c;
    Context mContext;
    ArrayList<Home> news;
    private boolean isFavorite = false;


    public HomeAdapter(ArrayList<Home> items, Context context) {

        mContext = context;
        news = items;
    }


    @Override
    public adapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.homeadapter;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;


//        onToggleClicked();

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new adapterViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(final adapterViewHolder holder, final int position) {


        String stringid = Integer.toString(position);
        Uri uri = TennisContract.TennisEntry.CONTENT_URI;
        uri.buildUpon().appendPath(stringid).build();


        final Home currentNews = news.get(position);

        // the button that will be added part



        floatingActionButton.setOnClickListener(new View.OnClickListener() {


            @Override


            public void onClick(View view) {


                if (!database(currentNews)) {

                    ContentValues contentValues = new ContentValues();
                    // Put the task description and selected mPriority into the ContentValues
                    contentValues.put(TennisContract.TennisEntry.COLUMN_DESCRIBTION, currentNews.getMsnippet());
                    contentValues.put(TennisContract.TennisEntry.COLUMN_SHORTDESCRIBTION, currentNews.getMarticleheadline());
                    contentValues.put(TennisContract.TennisEntry.COLUMN_IMAGE, currentNews.getMimageurl());

                    Uri uri = mContext.getContentResolver().insert(TennisContract.TennisEntry.CONTENT_URI, contentValues);
                    if (uri != null) {
                        Toast.makeText(mContext, "movie is added ", Toast.LENGTH_SHORT).show();


                    }

                } else {

                    Uri uri = TennisContract.TennisEntry.CONTENT_URI;

                    String selection = TennisContract.TennisEntry.COLUMN_SHORTDESCRIBTION + "=?";
                    String[] selectionArgs = {String.valueOf(currentNews.getMarticleheadline())};


                    mContext.getContentResolver().delete(uri, selection, selectionArgs);
                    Toast.makeText(mContext, "movie is deleted", Toast.LENGTH_SHORT).show();


                }
            }


        });


        button.setOnClickListener(new View.OnClickListener() {


            @Override


            public void onClick(View view) {

                //  holder.itemView.setOnClickListener(new View.OnClickListener() {
                //                                       @Override
                //                                     public void onClick(View v) {


                Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(currentNews.getMweburl()));
                mContext.startActivity(intent);


            }
        });

        holder.shortdes.setText(currentNews.getMarticleheadline());
        holder.describtion.setText(currentNews.getMsnippet());


        String temp = "https://static01.nyt.com/images/2018/01/17/sports/17tennis-men1/merlin_132343739_8018a288-07ae-48ed-ac71-deedcd7ad757-master768.jpg";


        String myimage = "https://static01.nyt.com/" + currentNews.getMimageurl().replace("master768", "articleLarge");
        Log.i("that it pls ", myimage);

        Picasso
                .with(holder.image.getContext())
                .load(myimage)
                .into(holder.image);


    }





    @Override
    public int getItemCount() {

        if (null == news) return 0;
        return news.size();

    }




    public void setWeatherData(ArrayList<Home> weatherData) {
        news = weatherData;
        notifyDataSetChanged();
    }

    private boolean database(Home home) {
        //  Cursor cursor = getContentResolver().query(
        //          MoviesContract.MoviesEntry.CONTENT_URI, null, null, null, null);

        c = mContext.getContentResolver().query(TennisContract.TennisEntry.CONTENT_URI, null, null, null, null);
        if (c != null) {
            while (c.moveToNext()) {
                String movieId = c.getString(
                        c.getColumnIndex(TennisContract.TennisEntry.COLUMN_SHORTDESCRIBTION));
                if (movieId.equals(home.getMarticleheadline())) {
                    return true;
                }
            }
        }
        if (c != null) {
            c.close();
        }
        return false;
    }


    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public class adapterViewHolder extends RecyclerView.ViewHolder {

        public TextView shortdes;
        public TextView describtion;
        ImageView image;



        public adapterViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.thumbnail);
            shortdes = itemView.findViewById(R.id.title);
            describtion = itemView.findViewById(R.id.subtitle);
            floatingActionButton = itemView.findViewById(R.id.share_fab);
            button = itemView.findViewById(R.id.button);

            // itemView.setOnClickListener(this);
        }


    }

}
