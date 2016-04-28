package king.muchbeer.kumbukasms.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import king.muchbeer.kumbukasms.data.SmsContract.SmsEntry;
/**
 * Created by muchbeer on 4/7/2016.
 */
public class SmsDbHelper extends SQLiteOpenHelper {

    private static final String DATABAE_NAME = "database.db";
    private static final int DATABAE_VERSION = 1 ;

    public SmsDbHelper(Context context) {
        super(context, DATABAE_NAME, null, DATABAE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create a table to hold locations.  A location consists of the string supplied in the
        // location setting, the city name, and the latitude and longitude

        // TBD

        final String SQL_CREATE_SMS_TABLE = "CREATE TABLE " + SmsEntry.TABLE_NAME + " (" +
        // Why AutoIncrement here, and not above?
        // Unique keys will be auto-generated in either case.  But for weather
        // forecasting, it's reasonable to assume the user will want information
        // for a certain date and all dates *following*, so the forecast data

        SmsEntry.COLUMN_SMS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
        SmsEntry.COLUMN_ADDRESS + " TEXT NULL" +
                SmsEntry.COLUMN_BODY + " TEXT NULL" +
                SmsEntry.COLUMN_DATE + " TEXT NULL" + ");";

            sqLiteDatabase.execSQL(SQL_CREATE_SMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        // Note that this only fires if you change the version number for your database.
        // It does NOT depend on the version number for your application.
        // If you want to update the schema without wiping data, commenting out the next 2 lines
        // should be your top priority before modifying this method.
       // sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + LocationEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SmsEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
