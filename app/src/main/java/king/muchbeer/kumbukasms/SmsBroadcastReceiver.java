package king.muchbeer.kumbukasms;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.SmsMessage;

import java.util.Calendar;

/**
 * Created by muchbeer on 4/4/2016.
 */
public class SmsBroadcastReceiver extends BroadcastReceiver {

    public static final String SMS_BUNDLE = "pdus";
    public String putAddress;
    public String putBody;
    private Context context;
    DataBaseHelperAdapter dataBaseHelper = new DataBaseHelperAdapter(context);
    private StringBuffer returnAllData;


    public void onReceive(Context context, Intent intent) {
        Bundle intentExtras = intent.getExtras();
        if (intentExtras != null) {
            Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
            String smsMessageStr = "";
            for (int i = 0; i < sms.length; ++i) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);

                String smsBody = smsMessage.getMessageBody().toString();
                String address = smsMessage.getOriginatingAddress();

                smsMessageStr += "SMS From: " + address + "\n";
                smsMessageStr += smsBody + "\n";
                
                putSmsToDatabase(smsMessage, context);

                /*
                if(rowID<0) {
                            ToastMessage.message(context, "Something went wrong");
                }else {
                    ToastMessage.message(context,"Success Inserted a row");
                }
                */
            }
         //   Toast.makeText(context, smsMessageStr, Toast.LENGTH_SHORT).show();


            //this will update the UI with message
            MainActivity inst = MainActivity.instance();
            inst.updateList(smsMessageStr);
        }
    }

    private void putSmsToDatabase(SmsMessage smsMessage, Context context) {




     //  putAddress  = smsMessage.getOriginatingAddress().toString();
        putBody = smsMessage.getMessageBody().toString();
   //     SQLiteDatabase db = dataBaseHelper.getWritableDatabase();

        String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
// Create SMS row
        ContentValues values = new ContentValues();
     //   values.put(dataBaseHelper.COLUMN_ADDRESS, putAddress );
     //   values.put(dataBaseHelper.COLUMN_BODY, putBody);

        // long id=  db.insert("datatable", null, values);
     //   db.insert(dataBaseHelper.TABLE_NAME, null, values);

        // values.put("date", mydate);
// values.put( READ, MESSAGE_IS_NOT_READ );
// values.put( STATUS, sms.getStatus() );
// values.put( TYPE, MESSAGE_TYPE_INBOX );
// values.put( SEEN, MESSAGE_IS_NOT_SEEN );



      //  db.close();

           // return id;
       }

    public void getAllData() {

     //   SQLiteDatabase db = dataBaseHelper.getWritableDatabase();

        //select _id, Name, Password from Table name
    //    String[] columns = {dataBaseHelper.COLUMN_ADDRESS, dataBaseHelper.COLUMN_BODY};
      //  Cursor cursor = db.query(dataBaseHelper.TABLE_NAME,columns,null,null,null,null,null);
StringBuffer buffer = new StringBuffer();
       // while(cursor.moveToNext()) {


         //   int indexAddress = cursor.getColumnIndex(dataBaseHelper.COLUMN_ADDRESS);
         //  int indexBody = cursor.getColumnIndex(dataBaseHelper.COLUMN_BODY);
        //    String addr = cursor.getString(indexAddress);
       //     String bdy = cursor.getString(indexBody);
        //    returnAllData =  buffer.append(dataBaseHelper.COLUMN_BODY + " " + dataBaseHelper.COLUMN_BODY + "\n");


     //   }
     //   String returnDataToUI = returnAllData.toString();

      //  return returnDataToUI;
    }

}
