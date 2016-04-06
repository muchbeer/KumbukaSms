package king.muchbeer.kumbukasms;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by muchbeer on 4/5/2016.
 */
public class DataBaseHelperAdapter {
DataBaseHelper dataBaseHelper;
public DataBaseHelperAdapter(Context context) {
    dataBaseHelper = new DataBaseHelper(context);
}
    public long insertData(String address,  String  body) {

        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(dataBaseHelper.COLUMN_ADDRESS, address );
        values.put(dataBaseHelper.COLUMN_BODY, body);

        // long id=  db.insert("datatable", null, values);
       long id = db.insert(dataBaseHelper.TABLE_NAME, null, values);

        return id;

    }
  static class DataBaseHelper   extends SQLiteOpenHelper {
      //public static final String SMS_URI = “/data/data/org.secure.sms/databases/”;
      public static final String db_name = "sms.db";
      public static final String TABLE_NAME="database";
      public static final String COLUMN_ADDRESS = "address";
      public static final String COLUMN_BODY = "body";
      public static final int version =1;

      Context context;
      public DataBaseHelper(Context context) {
          super(context, db_name, null, version);
          this.context = context;
          ToastMessage.message(context, "constructor called");
      }

      @Override
      public void onCreate(SQLiteDatabase sqLiteDatabase) {

          //Auto Increment Field
          //_id INTEGER PRIMARY KEY AUTOINCREMENT

          try {
              sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_NAME+" ("+COLUMN_ADDRESS+" VARCHAR(20), "+COLUMN_BODY+" VARCHAR(30))");
              ToastMessage.message(context, "onCreate called");
          }catch (SQLiteException e) {
              ToastMessage.message(context, ""+ e);
          }

          //  Toast.makeText(context, "database created", Toast.LENGTH_LONG).show();
          Log.i("dbcreate", "DATABASE HAS CREATED");
      }

      public boolean checkDataBase(String db) {

          SQLiteDatabase checkDB = null;

          try {
              String myPath = "data/data/"+ context.getPackageName() +"/databases/" + db;
              checkDB = SQLiteDatabase.openDatabase(myPath, null,
                      SQLiteDatabase.OPEN_READONLY);

          } catch (SQLiteException e) {

// database does’t exist yet.

          } catch (Exception e) {

          }

          if (checkDB != null) {

              checkDB.close();

          }

          return checkDB != null ? true : false;

      }

      @Override
      public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// TODO Auto-generated method stub
          if (oldVersion >= newVersion)
              return;

          if (oldVersion == 1) {
              Log.d("New Version", "Datas can be upgraded");
              ToastMessage.message(context, "onUpgrade data is called");
          }

          Log.d("Sample Data", "onUpgrade : " + newVersion);
          ToastMessage.message(context, "onUpgrade");
      }
  }
}
