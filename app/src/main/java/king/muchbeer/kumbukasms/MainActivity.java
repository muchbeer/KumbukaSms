package king.muchbeer.kumbukasms;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import king.muchbeer.kumbukasms.sqlite.UserContract;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {


    private static MainActivity inst;
    ArrayList<String> smsMessagesList = new ArrayList<String>();
    ListView smsListView;
    ArrayAdapter arrayAdapter;
    private String varAddress  = "";
    private String varBody = "";
    private String varCodeBody="";
    DataBaseHelperAdapter dataBaseHelper;
    SQLiteDatabase sqLiteDatabase;
    public static final String TABLE_NAME="database";
    private DataBaseHelperAdapter.DataBaseHelper dataBaseHelper23;
    long rowID;
    private String getName;
    private int indexOfBody;
    private String codebody;
    private int indexOfSecondLimit;
    private int indexOfFirstLimit;
    private String getCodeId;

    public static MainActivity instance() {
        return inst;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//  DatabaseHelper db = new DatabaseHelper(this);
        dataBaseHelper = new DataBaseHelperAdapter(this);

//check the weaather I have add


        smsListView = (ListView) findViewById(R.id.SMSList);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, smsMessagesList);
        smsListView.setAdapter(arrayAdapter);
        smsListView.setOnItemClickListener(this);


    }


    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    public void refreshSmsInbox(String findAddress) {




        // List required columns
        String[] projections={ UserContract.NewUserInfo.COLUMN_ADDRESS,UserContract.NewUserInfo.COLUMN_BODY};

        // String selection = "address = ? AND body = ? AND read = ?";
        String selection = "address = ?";

        String[] selectionArgs = {"M-PESA"};


        ContentResolver contentResolver = getContentResolver();
        Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), projections,
                UserContract.NewUserInfo.COLUMN_ADDRESS+" ='"+findAddress+"'", null , null);
        int indexBody = smsInboxCursor.getColumnIndex("body");
        int indexAddress = smsInboxCursor.getColumnIndex("address");
        if (indexBody < 0 || !smsInboxCursor.moveToFirst()) return;




        arrayAdapter.clear();
        do {
            getName = smsInboxCursor.getString(indexBody);
            varAddress=smsInboxCursor.getString(indexAddress);
            varBody = smsInboxCursor.getString(indexBody);
            getCodeId = varBody.substring(0,9);
            indexOfBody = varBody.indexOf("cash");
            indexOfSecondLimit = varBody.indexOf("New");
            indexOfFirstLimit = indexOfBody + 7;

            if (indexOfBody>0) {
                codebody = getName.substring(indexOfFirstLimit, indexOfSecondLimit);

            }else {
                codebody="No Name";
            }

            String str = "SMS From: " + smsInboxCursor.getString(indexAddress) +
                    "\n" + getCodeId + "\n" + "Name: " + codebody;
            arrayAdapter.add(str);

        //  rowID =   dataBaseHelper.insertData(varAddress, varBody);
        } while (smsInboxCursor.moveToNext());


        // Verify a row has been added
        if (rowID > 0) {
            Toast.makeText(getApplicationContext(), "Database has been added successful", Toast.LENGTH_LONG).show();

            // Append the given id to the path and return a Builder used to manipulate URI
            // references
        } else {
            Toast.makeText(getApplicationContext(), "Something is wrong", Toast.LENGTH_LONG).show();
        }


        //  insertDataForContentProvider(varAddress, varBody);
        /*
      long rowId =   dataBaseHelper.insertData(varAddress,varBody);

        if(rowId<0) {
            ToastMessage.message(this, "Something went wrong");
        }else {
            ToastMessage.message(this,"Record successfull added");
        }
*/
    }

    public void insertDataForContentProvider(String address, String body) {

        // Get the name supplied
        //  String name = contactNameEditText.getText().toString();

        // Stores a key value pair
        ContentValues values = new ContentValues();
        values.put(DataBaseHelperAdapter.DataBaseHelper.COLUMN_ADDRESS, address);
        values.put(DataBaseHelperAdapter.DataBaseHelper.COLUMN_BODY, body);

        // Provides access to other applications Content Providers
        Uri uri = getContentResolver().insert(ContentProvider.CONTENT_URL, values);
        ToastMessage.message(this, "New Record has been added");
    }


    public void deleteDataForContentProvider() {

        //    sqLiteDatabase.delete(MYDATABASE_TABLE, KEY_ID+"="+id, null);
        //sqLiteDatabase.delete(MYDATABASE_TABLE, null, null);

        dataBaseHelper23 = new DataBaseHelperAdapter.DataBaseHelper(this);
        sqLiteDatabase  = dataBaseHelper23.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME, null, null);
        ToastMessage.message(this, "All recorded successfull deleted");
    }
    public void updateList(final String smsMessage) {
        arrayAdapter.insert(smsMessage, 0);
        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Intent startDatabas =  new Intent(this, king.muchbeer.kumbukasms.sqlite.MainActivity.class);
            startActivity(startDatabas);

            return true;
        }

        if(id==R.id.main_settings) {

            Intent startSetting =  new Intent(this, SettingActivity.class);
            startActivity(startSetting);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {

        try {
            String[] smsMessages = smsMessagesList.get(pos).split("\n");
            String address = smsMessages[0];
            String smsMessage = "";
            for (int i = 1; i < smsMessages.length; ++i) {
                smsMessage += smsMessages[i];
            }

            String smsMessageStr = address + "\n";
            smsMessageStr += smsMessage;
            Toast.makeText(this, smsMessageStr, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String messageAddress = prefs.getString(getString(R.string.pref_message_key),
                getString(R.string.pref_sms_default));


        refreshSmsInbox(messageAddress);
    }
}
