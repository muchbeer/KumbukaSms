package king.muchbeer.kumbukasms;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import java.util.HashMap;

/**
 * Created by muchbeer on 4/6/2016.
 */
public class ContentProvider extends android.content.ContentProvider {

    // The Java namespace for the Content Provider
    static final String PROVIDER_NAME = "king.muchbeer.kumbukasms.ContentProvider";

    private SQLiteDatabase sqlDB;

    // Assigned to a content provider so any application can access it
    // cpsms is the virtual directory in the provider
    static final String URL = "content://" + PROVIDER_NAME + "/cpsms";
    static final Uri CONTENT_URL = Uri.parse(URL);

    static final String address = "address";
    static final String body = "body";
    static final int uriCode = 1;

    private static HashMap<String, String> values;

    // Used to match uris with Content Providers
    static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "cpsms", uriCode);
    }

    @Override
    public boolean onCreate() {

        DataBaseHelperAdapter.DataBaseHelper dataBaseHelper = new DataBaseHelperAdapter.DataBaseHelper(getContext());

        sqlDB = dataBaseHelper.getWritableDatabase();
        if (sqlDB != null) {
            return true;
        }
        return false;
    }


    // Returns a cursor that provides read and write access to the results of the query
    // Uri : Links to the table in the provider (The From part of a query)
    // projection : an array of columns to retrieve with each row
    // selection : The where part of the query selection
    // selectionArgs : The argument part of the where (where id = 1)
    // sortOrder : The order by part of the query
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // Used to create a SQL query
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        // Set table to query
        queryBuilder.setTables(DataBaseHelperAdapter.DataBaseHelper.TABLE_NAME);

        // Used to match uris with Content Providers
        switch (uriMatcher.match(uri)) {
            case uriCode:

                // A projection map maps from passed column names to database column names
                queryBuilder.setProjectionMap(values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        // Cursor provides read and write access to the database
        Cursor cursor = queryBuilder.query(sqlDB, projection, selection, selectionArgs, null,
                null, sortOrder);

        // Register to watch for URI changes
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        // Used to match uris with Content Providers
        switch (uriMatcher.match(uri)) {

            // vnd.android.cursor.dir/cpcontacts states that we expect multiple pieces of data
            case uriCode:
                return "vnd.android.cursor.dir/cpsms";

            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    // Used to insert a new row into the provider
    // Receives the URI (Uniform Resource Identifier) for the Content Provider and a set of values

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        // Gets the row id after inserting a map with the keys representing the the column
        // names and their values. The second attribute is used when you try to insert
        // an empty row
        long rowID = sqlDB.insert(DataBaseHelperAdapter.DataBaseHelper.TABLE_NAME, null, contentValues);

        // Verify a row has been added
        if (rowID > 0) {

            // Append the given id to the path and return a Builder used to manipulate URI
            // references
            Uri _uri = ContentUris.withAppendedId(CONTENT_URL, rowID);

            // getContentResolver provides access to the content model
            // notifyChange notifies all observers that a row was updated
            getContext().getContentResolver().notifyChange(_uri, null);

            // Return the Builder used to manipulate the URI
            return _uri;
        }
        ToastMessage.message(getContext(), "Row insert Failed");
     //   Toast.makeText(getContext(), "Row Insert Failed", Toast.LENGTH_LONG).show();
        return null;
    }

    // Deletes a row or a selection of rows
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int rowsDeleted = 0;

        // Used to match uris with Content Providers
        switch (uriMatcher.match(uri)) {
            case uriCode:
                rowsDeleted = sqlDB.delete(DataBaseHelperAdapter.DataBaseHelper.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        // getContentResolver provides access to the content model
        // notifyChange notifies all observers that a row was updated
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    // Used to update a row or a selection of rows
    // Returns to number of rows updated
    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        int rowsUpdated = 0;

        // Used to match uris with Content Providers
        switch (uriMatcher.match(uri)) {
            case uriCode:

                // Update the row or rows of data
                rowsUpdated = sqlDB.update(DataBaseHelperAdapter.DataBaseHelper.TABLE_NAME, contentValues, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        // getContentResolver provides access to the content model
        // notifyChange notifies all observers that a row was updated
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }


}
