package king.muchbeer.kumbukasms;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import king.muchbeer.kumbukasms.sqlite.UserContract;

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


    public boolean insertMessage (String address, String body)
    {
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("name", address);
        contentValues.put("phone", body);

        db.insert("Message", null, contentValues);
        return true;
    }



 public static class DataBaseHelper   extends SQLiteOpenHelper {
      //public static final String SMS_URI = “/data/data/org.secure.sms/databases/”;
      public static final String db_name = "sms.db";
      public static final String TABLE_NAME="database";
      public static final String COLUMN_ADDRESS = "address";
      public static final String COLUMN_BODY = "body";
      public static final String COLUMN_ID="id";
      public static final int version =4;

      // Database creation SQL statement
     private static final String CREATE_DB_TABLE = "CREATE TABLE "
              + TABLE_NAME
              + "("
              + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
              + COLUMN_ADDRESS + " TEXT NOT NULL, "
              + COLUMN_BODY + " TEXT NOT NULL"
              +");";



      Context context;
      public DataBaseHelper(Context context) {
          super(context, db_name, null, version);
          this.context = context;
          ToastMessage.message(context, "constructor called");
      }

      @Override
      public void onCreate(SQLiteDatabase sqLiteDatabase) {

          //Auto Increment Field
          sqLiteDatabase.execSQL(CREATE_DB_TABLE);
          ToastMessage.message(context, "onCreate called");

          Log.i("dbcreate", "DATABASE HAS CREATED");
      }




     public Cursor getinformation(SQLiteDatabase db){
         Cursor cursor;
         String[] projections={ UserContract.NewUserInfo.COLUMN_ADDRESS,UserContract.NewUserInfo.COLUMN_BODY};
         cursor= db.query(UserContract.NewUserInfo.TABLE_NAME, projections, null, null, null, null, null);
         return cursor;
     }

     public Cursor getSpecificAddress(SQLiteDatabase db, String address) {

     //    SQLiteDatabase db = getWritableDatabase();
         Cursor cursor;
         //select address, Password from table
         String[] columns = {UserContract.NewUserInfo.COLUMN_ADDRESS, UserContract.NewUserInfo.COLUMN_BODY};
       cursor = db.query(UserContract.NewUserInfo.TABLE_NAME,
                 columns,UserContract.NewUserInfo.COLUMN_ADDRESS+" ='"+address+"'",
                 null,null,null,null);

         return cursor;

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
