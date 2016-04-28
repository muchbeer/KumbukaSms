package king.muchbeer.kumbukasms.sqlite;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import king.muchbeer.kumbukasms.DataBaseHelperAdapter;
import king.muchbeer.kumbukasms.R;
import king.muchbeer.kumbukasms.SettingActivity;

/**
 * Created by muchbeer on 4/28/2016.
 */
public class GetAddressActivity extends AppCompatActivity {

    ListView listView;
    SQLiteDatabase sqLiteDatabase;
    DataBaseHelperAdapter.DataBaseHelper userDbHelper;
    //  DataBaseHelperAdapter.ge
    Cursor cursor;
    ListDataAdapter listDataAdpter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_address);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String messageAddress = prefs.getString(getString(R.string.pref_message_key),
                getString(R.string.pref_sms_default));


        listView=(ListView)findViewById(R.id.list_view);
        listDataAdpter=new ListDataAdapter(getApplicationContext(),R.layout.row_layout);
        listView.setAdapter(listDataAdpter);
        userDbHelper=new DataBaseHelperAdapter.DataBaseHelper(getApplicationContext());
        sqLiteDatabase=userDbHelper.getReadableDatabase();
        cursor=userDbHelper.getSpecificAddress(sqLiteDatabase, messageAddress);

        if(cursor.moveToFirst())
        {
            do {
                String id,address,body;

                // mCursor.getColumnIndex(dataBaseHelper.COLUMN_BODY)
                address= cursor.getString(cursor.getColumnIndex(UserContract.NewUserInfo.COLUMN_ADDRESS));
                body= cursor.getString(cursor.getColumnIndex(UserContract.NewUserInfo.COLUMN_BODY));
                // email=cursor.getString(cursor.);
                DataProvider dataProvider=new DataProvider(address,body);
                listDataAdpter.add(dataProvider);

            }while (cursor.moveToNext());
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
}
