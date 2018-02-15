package database;

/**
 * Created by Moha on 9/22/2017.
 */

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;


public class TennisProvider extends ContentProvider {
    private static final String LOG_TAG = TennisProvider.class.getSimpleName();
    // Codes for the UriMatcher //////
    private static final int TENNIS = 100;
    private static final int TENNIS_WITH_ID = 201;
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private TennisDBHelper mOpenHelper;
    ////////

    private static UriMatcher buildUriMatcher() {
        // Build a UriMatcher by adding a specific code to return based on a match
        // It's common to use NO_MATCH as the code for this case.
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = TennisContract.CONTENT_AUTHORITY;

        // add a code for each type of URI you want
        matcher.addURI(authority, TennisContract.TennisEntry.TABLE_TENNIS, TENNIS);
        matcher.addURI(authority, TennisContract.TennisEntry.TABLE_TENNIS + "/#", TENNIS_WITH_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new TennisDBHelper(getContext());

        return true;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case TENNIS: {
                return TennisContract.TennisEntry.CONTENT_DIR_TYPE;
            }
            case TENNIS_WITH_ID: {
                return TennisContract.TennisEntry.CONTENT_ITEM_TYPE;
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            // All Flavors selected
            case TENNIS: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        TennisContract.TennisEntry.TABLE_TENNIS,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                return retCursor;
            }
            // Individual flavor based on Id selected
            case TENNIS_WITH_ID: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        TennisContract.TennisEntry.TABLE_TENNIS,
                        projection,
                        TennisContract.TennisEntry.COLUMN_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);

                return retCursor;
            }
            default: {
                // By default, we assume a bad URI
                throw new UnsupportedOperationException("Unknown uri: " + uri);

            }
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        Uri returnUri;
        switch (match) {
            case TENNIS: {
                long _id = db.insert(TennisContract.TennisEntry.TABLE_TENNIS, null, values);
                // insert unless it is already contained in the database
                if (_id > 0) {
                    returnUri = ContentUris.withAppendedId(TennisContract.TennisEntry.CONTENT_URI, _id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into: " + uri);
                }
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }

        getContext().getContentResolver().notifyChange(uri, null);


        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int numDeleted;
        switch (match) {
            case TENNIS:
                numDeleted = db.delete(
                        TennisContract.TennisEntry.TABLE_TENNIS, selection, selectionArgs);
                // reset _ID
                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                        TennisContract.TennisEntry.TABLE_TENNIS + "'");
                break;
            case TENNIS_WITH_ID:

                numDeleted = db.delete(TennisContract.TennisEntry.TABLE_TENNIS,
                        TennisContract.TennisEntry.COLUMN_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                // reset _ID
                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                        TennisContract.TennisEntry.TABLE_TENNIS + "'");

                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (numDeleted != 0) {
            // A task was deleted, set notification
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numDeleted;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case TENNIS:
                // allows for multiple transactions
                db.beginTransaction();

                // keep track of successful inserts
                int numInserted = 0;
                try {
                    for (ContentValues value : values) {
                        if (value == null) {
                            throw new IllegalArgumentException("Cannot have null content values");
                        }
                        long _id = -1;
                        try {
                            _id = db.insertOrThrow(TennisContract.TennisEntry.TABLE_TENNIS,
                                    null, value);
                        } catch (SQLiteConstraintException e) {
                            Log.w(LOG_TAG, "Attempting to insert " +
                                    value.getAsString(
                                            TennisContract.TennisEntry.COLUMN_DESCRIBTION)
                                    + " but value is already in database.");
                        }
                        if (_id != -1) {
                            numInserted++;
                        }
                    }
                    if (numInserted > 0) {
                        // If no errors, declare a successful transaction.
                        // database will not populate if this is not called
                        db.setTransactionSuccessful();
                    }
                } finally {
                    // all transactions occur at once
                    db.endTransaction();
                }
                if (numInserted > 0) {
                    // if there was successful insertion, notify the content resolver that there
                    // was a change
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return numInserted;
            default:
                return super.bulkInsert(uri, values);
        }
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int numUpdated = 0;

        if (contentValues == null) {
            throw new IllegalArgumentException("Cannot have null content values");
        }

        switch (sUriMatcher.match(uri)) {
            case TENNIS: {
                numUpdated = db.update(TennisContract.TennisEntry.TABLE_TENNIS,
                        contentValues,
                        selection,
                        selectionArgs);
                break;
            }
            case TENNIS_WITH_ID: {
                numUpdated = db.update(TennisContract.TennisEntry.TABLE_TENNIS,
                        contentValues,
                        TennisContract.TennisEntry._ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }

        if (numUpdated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numUpdated;
    }

}