package king.muchbeer.kumbukasms.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import king.muchbeer.kumbukasms.DataBaseHelperAdapter;
import king.muchbeer.kumbukasms.DataBaseHelperAdapter.*;
import king.muchbeer.kumbukasms.R;

/**
 * Created by muchbeer on 4/28/2016.
 */
public class DisplayListActivity extends AppCompatActivity {

    ListView listView;
    SQLiteDatabase sqLiteDatabase;
    DataBaseHelperAdapter.DataBaseHelper userDbHelper;
  //  DataBaseHelperAdapter.ge
    Cursor cursor;
    ListDataAdapter listDataAdpter;
  //  DataBaseHelperAdapter.DataBaseHelper
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sqlite_activity);

        listView=(ListView)findViewById(R.id.list_view);
        listDataAdpter=new ListDataAdapter(getApplicationContext(),R.layout.row_layout);
        listView.setAdapter(listDataAdpter);
        userDbHelper=new DataBaseHelper(getApplicationContext());
        sqLiteDatabase=userDbHelper.getReadableDatabase();
        cursor=userDbHelper.getinformation(sqLiteDatabase);

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
}
