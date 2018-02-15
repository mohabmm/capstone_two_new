package database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nocom.capstone_stage2.R;
import com.squareup.picasso.Picasso;

public class MovieCursorAdapter extends CursorAdapter {

    TennisDBHelper mdd;
    private SQLiteDatabase mDb;


    public MovieCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.favoritelist, parent, false);
    }

    /**
     * This method binds the pet data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current pet can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView title = view.findViewById(R.id.title);
        TextView subtitle = view.findViewById(R.id.subtitle);
        ImageView myimage = view.findViewById(R.id.thumbnail);

        // Extract properties from cursor
        int DescribtionColumnIndex = cursor.getColumnIndex(TennisContract.TennisEntry.COLUMN_DESCRIBTION);
        int ShortDescribtionColumnIndex = cursor.getColumnIndex(TennisContract.TennisEntry.COLUMN_SHORTDESCRIBTION);
        int imageColumnIndex = cursor.getColumnIndex(TennisContract.TennisEntry.COLUMN_IMAGE);


        String ColumnDescribtion = cursor.getString(DescribtionColumnIndex);
        String ColumnShortDescribtion = cursor.getString(ShortDescribtionColumnIndex);
        String ColumnImage = cursor.getString(imageColumnIndex);

        String myimage2 = "https://static01.nyt.com/" + ColumnImage.replace("master768", "articleLarge");


        Picasso.with(context).load(myimage2)
                .into(myimage);
        title.setText(ColumnShortDescribtion);
        subtitle.setText(ColumnDescribtion);

    }
}