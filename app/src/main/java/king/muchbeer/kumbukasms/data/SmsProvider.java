package king.muchbeer.kumbukasms.data;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import king.muchbeer.kumbukasms.ContentProvider;
import king.muchbeer.kumbukasms.data.SmsContract;

/**
 * Created by muchbeer on 4/7/2016.
 */
public class SmsProvider extends ContentProvider {

    // The URI Matcher used by this content provider.
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private SmsDbHelper mOpenHelper;

    private static final int SMS_TABLE = 100;
    private static final int SMS_ITEM_ID = 101;

 //   private static final int SMS_WITH_LOCATION = 101;
   // private static final int SMS_WITH_LOCATION_AND_DATE = 102;
   // private static final int LOCATION = 300;
   // private static final int LOCATION_ID = 301;


    private static SQLiteQueryBuilder sMessageByBodySettingQueryBuilder;


    //Name or anything in particular such as part price
    private static final String messageBodySelectionAndSetting =
            SmsContract.SmsEntry.TABLE_NAME+
                    "." + SmsContract.SmsEntry.COLUMN_BODY + " = ? ";

    private static final String messageDateSelection =
            SmsContract.SmsEntry.TABLE_NAME+
                    "." + SmsContract.SmsEntry.COLUMN_DATE + " = ? AND " +
                    SmsContract.SmsEntry.COLUMN_BODY + " >= ? ";

    private static final String messageSettingsAndMoretocome =
            SmsContract.SmsEntry.TABLE_NAME +
                    "." + SmsContract.SmsEntry.COLUMN_SUBJECT + " = ? AND " +
                    SmsContract.SmsEntry.COLUMN_SMS_ID + " = ? ";

    private Cursor getSmsByDateAndBody(Uri uri, String[] projection, String sortOrder) {
        String locationSetting = SmsContract.SmsEntry.getSmsAddressSettingFromUri(uri);
        String startDate = SmsContract.SmsEntry.getStartDateFromUri(uri);

        String[] selectionArgs;
        String selection;

        if (startDate == null) {
            selection = messageBodySelectionAndSetting;
            selectionArgs = new String[]{locationSetting};
        } else {
            selectionArgs = new String[]{locationSetting, startDate};
            selection = messageDateSelection;
        }

        return sMessageByBodySettingQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getWeatherByLocationSettingAndDate(
            Uri uri, String[] projection, String sortOrder) {
       String locationSetting = SmsContract.SmsEntry.getSmsAddressSettingFromUri(uri);
        String date =          SmsContract.SmsEntry.getDateFromUri(uri);

        return sMessageByBodySettingQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                messageDateSelection,
                new String[]{locationSetting, date},
                null,
                null,
                sortOrder
        );
    }

    private static UriMatcher buildUriMatcher() {
        // I know what you're thinking.  Why create a UriMatcher when you can use regular
        // expressions instead?  Because you're not crazy, that's why.

        // All paths added to the UriMatcher have a corresponding code to return when a match is
        // found.  The code passed into the constructor represents the code to return for the root
        // URI.  It's common to use NO_MATCH as the code for this case.
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = SmsContract.CONTENT_AUTHORITY;

        // For each type of URI you want to add, create a corresponding code.
        matcher.addURI(authority, SmsContract.PATH_FOR_SMS, SMS_TABLE);
        matcher.addURI(authority, SmsContract.PATH_FOR_SMS + "/*", SMS_ITEM_ID);


      //  matcher.addURI(authority, WeatherContract.PATH_WEATHER + "/*", WEATHER_WITH_LOCATION);
      // matcher.addURI(authority, WeatherContract.PATH_WEATHER + "/*/*", WEATHER_WITH_LOCATION_AND_DATE);
      //  matcher.addURI(authority, WeatherContract.PATH_LOCATION, LOCATION);


        return matcher;
    }
    @Override
    public boolean onCreate() {
        mOpenHelper = new SmsDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // Here's the switch statement that, given a URI, will determine what kind of request it is,
        // and query the database accordingly.
        Cursor retCursor;
        switch(sUriMatcher.match(uri)) {

// "sms table"
            case SMS_TABLE: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        SmsContract.SmsEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            // "sms/*"
            case SMS_ITEM_ID: {
                selection = messageBodySelectionAndSetting;
                String locationSetting = SmsContract.SmsEntry.getSmsAddressSettingFromUri(uri);
                selectionArgs = new String[]{locationSetting};

                //Replace selection with SmsContract.SmsEntry.COLUMN_IDX + ....
                //   SmsContract.SmsEntry.COLUMN_IDX + " = '" + ContentUris.parseId(uri) + "'",

                retCursor = mOpenHelper.getReadableDatabase().query(
                        SmsContract.SmsEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;



    }

    @Override
    public String getType(Uri uri) {
        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case SMS_TABLE:
                return SmsContract.SmsEntry.CONTENT_TYPE;
            case SMS_ITEM_ID:
                return SmsContract.SmsEntry.CONTENT_ITEM_TYPE;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        return super.insert(uri, contentValues);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return super.delete(uri, selection, selectionArgs);
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        return super.update(uri, contentValues, selection, selectionArgs);
    }
}
