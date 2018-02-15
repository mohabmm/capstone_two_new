package database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class TennisDBHelper extends SQLiteOpenHelper {
    public static final String LOG_TAG = TennisDBHelper.class.getSimpleName();

    //name & version
    private static final String DATABASE_NAME = "tennis.db";
    private static final int DATABASE_VERSION = 2;

    public TennisDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create the database
    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_PETS_TABLE = "CREATE TABLE " + TennisContract.TennisEntry.TABLE_TENNIS + " ("
                + TennisContract.TennisEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TennisContract.TennisEntry.COLUMN_DESCRIBTION + " TEXT NOT NULL , "
                + TennisContract.TennisEntry.COLUMN_SHORTDESCRIBTION + " TEXT NOT NULL, "
                + TennisContract.TennisEntry.COLUMN_IMAGE + " TEXT NOT NULL );";


        db.execSQL(SQL_CREATE_PETS_TABLE);
    }

    // Upgrade database when version is changed.
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {


        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TennisContract.TennisEntry.TABLE_TENNIS);
        onCreate(sqLiteDatabase);
    }


}