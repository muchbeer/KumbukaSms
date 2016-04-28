package king.muchbeer.kumbukasms;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by muchbeer on 4/27/2016.
 */
public class DisplayActivity extends AppCompatActivity {

    private ListView userList;
    private DataBaseHelperAdapter.DataBaseHelper dataBaseHelper;
    public String KEY_ADDRESS;
    public String KEY_BODY;

    private SQLiteDatabase dataBase;

    private ArrayList<String> userId = new ArrayList<String>();

    private ArrayList<String> sms_Address = new ArrayList<String>();
    private ArrayList<String> sms_Body = new ArrayList<String>();

    public String KEY_UPDATE = "update";
    private AlertDialog.Builder build;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        userList = (ListView) findViewById(R.id.List);

        dataBaseHelper = new DataBaseHelperAdapter.DataBaseHelper(this);

        findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        AddActivity.class);
                i.putExtra(KEY_UPDATE, false);
                startActivity(i);
            }
        });
        //click to update data
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int arg2, long l) {
                Intent getNewIntent = new Intent(getApplicationContext(), AddActivity.class);
                getNewIntent.putExtra(KEY_ADDRESS, sms_Address.get(arg2));
                getNewIntent.putExtra(KEY_BODY, sms_Body.get(arg2));
                getNewIntent.putExtra("id", userId.get(arg2));
                getNewIntent.putExtra("update", true);
                startActivity(getNewIntent);
            }


            });

        //long click to delete data
        userList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           final int arg2, long arg3) {

                build = new AlertDialog.Builder(DisplayActivity.this);
                build.setTitle("Delete " + sms_Address.get(arg2) + " "
                        + sms_Body.get(arg2));
                build.setMessage("Do you want to delete ?");
                build.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog,
                                                int which) {

                                Toast.makeText(getApplicationContext(), sms_Address.get(arg2) + " "
                                        + sms_Body.get(arg2)
                                        + " is deleted.", Toast.LENGTH_LONG).show();

                                dataBase.delete(
                                        dataBaseHelper.TABLE_NAME,
                                        dataBaseHelper.COLUMN_ID + "="
                                                + userId.get(arg2), null);
                                displayData();
                                dialog.cancel();
                            }
                        });

                build.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = build.create();
                alert.show();

                return true;
            }
        });

        displayData();

    }

    @Override
    protected void onResume() {
        displayData();
        super.onResume();
    }

    /**
     * displays data from SQLite
     */
    private void displayData() {
        dataBase = dataBaseHelper.getWritableDatabase();

        String selectQuery = "SELECT * FROM "
                + dataBaseHelper.TABLE_NAME;

        Cursor mCursor = dataBase.rawQuery(selectQuery, null);

        String viewAddress = null;



        userId.clear();
        sms_Address.clear();
        sms_Body.clear();
        if (mCursor.moveToFirst()) {
            viewAddress =   mCursor.getString(mCursor.getColumnIndex(dataBaseHelper.COLUMN_ADDRESS));
            do {
                userId.add(mCursor.getString(mCursor.getColumnIndex(dataBaseHelper.COLUMN_ID)));
                sms_Address.add(viewAddress);
                sms_Body.add(mCursor.getString(mCursor.getColumnIndex(dataBaseHelper.COLUMN_BODY)));

            } while (mCursor.moveToNext());
        }
        Log.d("See muchbeer", viewAddress);
        DisplayAdapter disadpt = new DisplayAdapter(DisplayActivity.this,userId, sms_Address, sms_Body);
        userList.setAdapter(disadpt);
        mCursor.close();
    }





}
