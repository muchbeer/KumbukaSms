package king.muchbeer.kumbukasms.data;

/**
 * Created by muchbeer on 4/7/2016.
 */


import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Defines table and column names for the weather database.
 */
public class SmsContract {

    // The "Content authority" is a name for the entire content provider, similar to the
    // relationship between a domain name and its website.  A convenient string to use for the
    // content authority is the package name for the app, which is guaranteed to be unique on the
    // device.

    //static final String PROVIDER_NAME = "king.muchbeer.kumbukasms.ContentProvider";

    public static final String CONTENT_AUTHORITY = "king.muchbeer.kumbukasms.ContentProvider";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Possible paths (appended to base content URI for possible URI's)
    // For instance, content://com.example.android.sunshine.app/weather/ is a valid path for
    // looking at weather data. content://com.example.android.sunshine.app/givemeroot/ will fail,
    // as the ContentProvider hasn't been given any information on what to do with "givemeroot".
    // At least, let's hope not.  Don't be that dev, reader.  Don't be that dev.


    public static final String PATH_FOR_SMS = "smstable";

    public static final class SmsEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FOR_SMS).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY +"/" + PATH_FOR_SMS;

        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" +PATH_FOR_SMS;

        public static final String TABLE_NAME = "smsmoney";

        // Column with the foreign key into the location table.
        public static final String COLUMN_IDX = "uid";

        // SMS id as returned by A PI, to identify the icon to be used
        public static final String COLUMN_SMS_ID = "_id";

        // Date, stored as Text with format yyyy-MM-dd
        public static final String COLUMN_DATE = "date";

        // Short description and long description of the sms, as provided by API.
        // e.g "clear" vs "sky is clear".
        public static final String COLUMN_BODY = "body";

        // who sent the sms
        public static final String COLUMN_ADDRESS = "address";

        //who the person
        public static final String COLUMN_PERSON = "person";

        // status of the sms if delivered
        public static final String COLUMN_STATUS = "status";

        // check read messages
        public static final String COLUMN_READ = "read";

        //check type message
        public static final String COLUMN_TYPE = "type";

        // subjects.
        public static final String COLUMN_SUBJECT = "subject";

        //To retrieve a row where id is the number specified from the query content provider
        public static Uri buildSmsUriId(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        //Query according to date
        public static Uri buildSmssDate(String dateSetting) {
            return CONTENT_URI.buildUpon().appendPath(dateSetting).build();
        }

        //Query according to bdoy to get name and amount

               //Query according to date
        public static Uri buildSmsGetName(String nameSetting) {
            return CONTENT_URI.buildUpon().appendPath(nameSetting).build();
        }

        //Query according to date
        public static Uri buildSmsGetPrice(String priceSetting) {
            return CONTENT_URI.buildUpon().appendPath(priceSetting).build();
        }

        public static Uri buildSmsWithStartDate(
                String nameSetting, String startDate) {
            return CONTENT_URI.buildUpon().appendPath(nameSetting)
                    .appendQueryParameter(COLUMN_DATE, startDate).build();
        }

        public static Uri buildSmsWithDate(String priceSetting, String date) {
            return CONTENT_URI.buildUpon().appendPath(priceSetting).appendPath(date).build();
        }

        public static String getSmsAddressSettingFromUri(Uri uri) {
            return uri.getPathSegments().get(0);
        }

        public static String getDateFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

        public static String getStartDateFromUri(Uri uri) {
            return uri.getQueryParameter(COLUMN_DATE);
        }
    }

    /* Inner class that defines the table contents of the location table */
    public static final class QuerySmsEntry implements BaseColumns {

        // Table name
        public static final String TABLE_NAME = "smsOut";


        public static final String COLUMN_SMS_SETTING = "location_setting";

        // Human readable location string, provided by the API.  Because for styling,
        // "Mountain View" is more recognizable than 94043.
        public static final String COLUMN_BODY_SMS = "sms_address";

        // In order to uniquely pinpoint the location on the map when we launch the
        // map intent, we store the latitude and longitude as returned by openweathermap.
        public static final String COLUMN_NAMES_SMS = "sms_body";

        public static final String COLUMN_DATE_SMS = "sms_date";

        public static final String COLUMN_PRICE_SMS = "sms_price";
    }
}
