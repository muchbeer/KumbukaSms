package king.muchbeer.kumbukasms;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    DataBaseHelperAdapter dataBaseHelper;
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


    public static MainActivityAdapter instance() {
        return inst;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_adapter);

        resolver = getContentResolver();

        smsListView = (ListView) findViewById(R.id.SMSList);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, smsMessagesList);
        smsListView.setAdapter(arrayAdapter);
        smsListView.setOnItemClickListener(this);

        refreshSmsInbox();
    }

    private void refreshSmsInbox() {

        // Projection contains the columns we want
        String[] projection = new String[]{
                DataBaseHelperAdapter.DataBaseHelper.COLUMN_ADDRESS,
                DataBaseHelperAdapter.DataBaseHelper.COLUMN_BODY};

        String selection = "address = ?";

        String[] selectionArgs = {"M-PESA"};

        // Pass the URL, projection and I'll cover the other options below
        Cursor cursor = resolver.query(CONTENT_URL, projection, null, null, null);

        String contactList = "";
        arrayAdapter.clear();
        // Cycle through and display every row of data
        if(cursor.moveToFirst()){

            do{

                //    String cid = cursor.getString(cursor.getColumnIndex(DataBaseHelperAdapter.DataBaseHelper.COLUMN_ID));
                String caddress = cursor.getString(cursor.getColumnIndex(DataBaseHelperAdapter.DataBaseHelper.COLUMN_ADDRESS));
                String cbody = cursor.getString(cursor.getColumnIndex(DataBaseHelperAdapter.DataBaseHelper.COLUMN_BODY));


                contactList = contactList + "From" + " :> " + caddress +
                        "\n" + cbody + "\n";

                arrayAdapter.add(contactList);

            }while (cursor.moveToNext());

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
}
