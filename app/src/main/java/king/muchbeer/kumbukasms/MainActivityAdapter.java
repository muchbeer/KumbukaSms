package king.muchbeer.kumbukasms;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by muchbeer on 4/7/2016.
 */
public class MainActivityAdapter extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static MainActivityAdapter inst;
    ArrayList<String> smsMessagesList = new ArrayList<String>();
    ListView smsListView;
    ArrayAdapter arrayAdapter;
    private String varAddress  = "";
    private String varBody = "";

    // Provides access to other applications Content Providers
    ContentResolver resolver;

    // The Java namespace for the Content Provider
    static final String PROVIDER_NAME = "king.muchbeer.kumbukasms.ContentProvider";

    // Assigned to a content provider so any application can access it
    // cpsms is the virtual directory in the provider
    static final String URL = "content://" + PROVIDER_NAME + "/cpsms";
    // The URL used to target the content provider
    static final Uri CONTENT_URL =
            Uri.parse(URL);

    String caddress;
    String cbody;
    DataBaseHelperAdapter dataBaseHelper;
    public static MainActivityAdapter instance() {
        return inst;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_adapter);

      //  DatabaseHelper db = new DatabaseHelper(this);
     dataBaseHelper = new DataBaseHelperAdapter(this);


        resolver = getContentResolver();

        smsListView = (ListView) findViewById(R.id.SMSList);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, smsMessagesList);
        smsListView.setAdapter(arrayAdapter);
        smsListView.setOnItemClickListener(this);

        refreshSmsInbox();
    }

    private void refreshSmsInbox() {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String messageAddress = prefs.getString(getString(R.string.pref_message_key),
                getString(R.string.pref_sms_default));

        // Projection contains the columns we want
        String[] projection = new String[]{
                DataBaseHelperAdapter.DataBaseHelper.COLUMN_ADDRESS,
                DataBaseHelperAdapter.DataBaseHelper.COLUMN_BODY};

        String selection = "address = ?";

        String[] selectionArgs = {messageAddress};
        String[] selectionArgs2= {"ECOBANK"};

        // Pass the URL, projection and I'll cover the other options below
        Cursor cursor = resolver.query(CONTENT_URL, projection, null, null, null);

        String contactList = "";
        arrayAdapter.clear();
        // Cycle through and display every row of data


        try{
            if(cursor.moveToFirst()){

                do{

                    //    String cid = cursor.getString(cursor.getColumnIndex(DataBaseHelperAdapter.DataBaseHelper.COLUMN_ID));
                   caddress = cursor.getString(cursor.getColumnIndex(DataBaseHelperAdapter.DataBaseHelper.COLUMN_ADDRESS));
                    cbody = cursor.getString(cursor.getColumnIndex(DataBaseHelperAdapter.DataBaseHelper.COLUMN_BODY));

                    String getaddress = "George";
                    String getbody = "Machibya";
                    long rowID =   dataBaseHelper.insertData(getaddress, getbody);

                    // Verify a row has been added
                    if (rowID > 0) {
                        Toast.makeText(getApplicationContext(), "Something is wrong", Toast.LENGTH_LONG).show();

                        // Append the given id to the path and return a Builder used to manipulate URI
                        // references
                    } else {
                        Toast.makeText(getApplicationContext(), "Something is wrong", Toast.LENGTH_LONG).show();
                    }

                    contactList = contactList + "From" + " :> " + caddress +
                            "\n" + cbody + "\n";

                    arrayAdapter.add(contactList);

                }while (cursor.moveToNext());

               //dataBaseHelper.insertData(caddress, cbody);
             //   boolean checkifDataInsert=

               // boolean tempBoo = dataBaseHelper.DataBaseHelper.insertMessage(caddress,cbody);
/*
                if(checkifDataInsert) {
                    Toast.makeText(getApplicationContext(),"Data Inserted Succesfull", Toast.LENGTH_LONG).show();
                }
                else {
                   Toast.makeText(getApplicationContext(),"Data failed to insert", Toast.LENGTH_LONG).show();

               }

               */
            }
        }finally {
            cursor.close();
        }


     //   contactsTextView.setText(contactList);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.messages_settings) {

            Intent startSetting = new Intent(this, SettingActivity.class);
            startActivity(startSetting);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        refreshSmsInbox();
        super.onStart();
    }
}
