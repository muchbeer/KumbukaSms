package king.muchbeer.kumbukasms;

import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.PrivateKey;

/**
 * Created by muchbeer on 4/5/2016.
 */
public class MainActivityContent extends AppCompatActivity {

    // The Java namespace for the Content Provider
    static final String PROVIDER_NAME = "king.muchbeer.kumbukasms.ContentProvider";

       // Assigned to a content provider so any application can access it
    // cpsms is the virtual directory in the provider
    static final String URL = "content://" + PROVIDER_NAME + "/cpsms";
    // The URL used to target the content provider
    static final Uri CONTENT_URL =
            Uri.parse(URL);

    TextView contactsTextView = null;
    EditText deleteIDEditText, idLookupEditText, addNameEditText;
    CursorLoader cursorLoader;

    // Provides access to other applications Content Providers
    ContentResolver resolver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_content);

       // String data= dataBaseHelper.getAllData();
     //   ToastMessage.message(this, data);
        resolver = getContentResolver();

        contactsTextView = (TextView) findViewById(R.id.contactsTextView);
        deleteIDEditText = (EditText) findViewById(R.id.deleteIDEditText);
        idLookupEditText = (EditText) findViewById(R.id.idLookupEditText);
        addNameEditText = (EditText) findViewById(R.id.addNameEditText);

        getContacts();

    }

    private void getContacts() {

        // Projection contains the columns we want
        String[] projection = new String[]{
                DataBaseHelperAdapter.DataBaseHelper.COLUMN_ADDRESS,
                DataBaseHelperAdapter.DataBaseHelper.COLUMN_BODY};

        String selection = "address = ?";

        String[] selectionArgs = {"M-PESA"};

        // Pass the URL, projection and I'll cover the other options below
        Cursor cursor = resolver.query(CONTENT_URL, projection, null, null, null);

        String contactList = "";

        // Cycle through and display every row of data
        if(cursor.moveToFirst()){

            do{

            //    String cid = cursor.getString(cursor.getColumnIndex(DataBaseHelperAdapter.DataBaseHelper.COLUMN_ID));
                String caddress = cursor.getString(cursor.getColumnIndex(DataBaseHelperAdapter.DataBaseHelper.COLUMN_ADDRESS));
                String cbody = cursor.getString(cursor.getColumnIndex(DataBaseHelperAdapter.DataBaseHelper.COLUMN_BODY));


                contactList = contactList + "From" + " :> " + caddress +
                        "\n" + cbody + "\n";

            }while (cursor.moveToNext());

        }

        contactsTextView.setText(contactList);


    }

    public void deleteContact(View view) {

        String idToDelete = deleteIDEditText.getText().toString();

        // Use the resolver to delete ids by passing the content provider url
        // what you are targeting with the where and the string that replaces
        // the ? in the where clause
        long idDeleted = resolver.delete(CONTENT_URL,
                "id = ? ", new String[]{idToDelete});

        getContacts();

    }

    public void lookupContact(View view) {

        // The id we want to search for
        String idToFind = idLookupEditText.getText().toString();

        // Holds the column data we want to retrieve
        String[] projection = new String[]{DataBaseHelperAdapter.DataBaseHelper.COLUMN_ID,
                DataBaseHelperAdapter.DataBaseHelper.COLUMN_ADDRESS,
                DataBaseHelperAdapter.DataBaseHelper.COLUMN_BODY};

        // Pass the URL for Content Provider, the projection,
        // the where clause followed by the matches in an array for the ?
        // null is for sort order
        Cursor cursor = resolver.query(CONTENT_URL,
                projection, "id = ? ", new String[]{idToFind}, null);

        String contact = "";

        // Cycle through our one result or print error
        if(cursor.moveToFirst()){

            String cid = cursor.getString(cursor.getColumnIndex(DataBaseHelperAdapter.DataBaseHelper.COLUMN_ID));
            String caddress = cursor.getString(cursor.getColumnIndex(DataBaseHelperAdapter.DataBaseHelper.COLUMN_ADDRESS));
            String cbody = cursor.getString(cursor.getColumnIndex(DataBaseHelperAdapter.DataBaseHelper.COLUMN_BODY));

            contact = contact + cid + " :> " + caddress +
                    "\n" + cbody + "\n";

        } else {

            ToastMessage.message(this, "Contact Not Found");

        }

        contactsTextView.setText(contact+"\n\n");

    }

    public void showContacts(View view) {

        Intent startAdapterSms = new Intent(this, MainActivityAdapter.class);
        startActivity(startAdapterSms);

    }

    public void addContact(View view) {



    }

}
