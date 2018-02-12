package database;

/**
 * Created by Moha on 9/22/2017.
 */

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class TennisContract {

    public static final String CONTENT_AUTHORITY = "database";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final class TennisEntry implements BaseColumns {
        // table name
        public static final String TABLE_TENNIS = "tennis";// path tasks
        // columns
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_SHORTDESCRIBTION = "shortdescribtion";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_DESCRIBTION = "describtion";



        // create content uri
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(TABLE_TENNIS).build();
        // create cursor of base type directory for multiple entries
        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_TENNIS;
        // create cursor of base type item for single entry
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE +"/" + CONTENT_AUTHORITY + "/" + TABLE_TENNIS;

        // for building URIs on insertion
        public static Uri buildMovieUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}