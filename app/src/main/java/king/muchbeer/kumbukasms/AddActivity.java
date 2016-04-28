package king.muchbeer.kumbukasms;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by muchbeer on 4/27/2016.
 */
public class AddActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_save;
    private EditText edit_first,edit_last;
    private DataBaseHelperAdapter.DataBaseHelper mHelper;
    private SQLiteDatabase dataBase;
    private String id,fname,lname;
    private boolean isUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        btn_save=(Button)findViewById(R.id.save_btn);
        edit_first=(EditText)findViewById(R.id.frst_editTxt);
        edit_last=(EditText)findViewById(R.id.last_editTxt);

        isUpdate=getIntent().getExtras().getBoolean("update");
        if(isUpdate)
        {
            id=getIntent().getExtras().getString("ID");
            fname=getIntent().getExtras().getString("Fname");
            lname=getIntent().getExtras().getString("Lname");
            edit_first.setText(fname);
            edit_last.setText(lname);

        }

        btn_save.setOnClickListener(this);

        mHelper=new DataBaseHelperAdapter.DataBaseHelper(this);
    }

    @Override
    public void onClick(View view) {

        fname=edit_first.getText().toString().trim();
        lname=edit_last.getText().toString().trim();
        if(fname.length()>0 && lname.length()>0)
        {
            saveData();
        }
        else
        {
            AlertDialog.Builder alertBuilder=new AlertDialog.Builder(AddActivity.this);
            alertBuilder.setTitle("Invalid Data");
            alertBuilder.setMessage("Please, Enter valid data");
            alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();

                }
            });
            alertBuilder.create().show();
        }


    }

    private void saveData() {

        dataBase=mHelper.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(mHelper.COLUMN_ADDRESS,fname);
        values.put(mHelper.COLUMN_BODY,lname );

        System.out.println("");
        if(isUpdate)
        {
            //update database with new data
            dataBase.update(mHelper.TABLE_NAME, values, mHelper.COLUMN_ID+"="+id, null);
        }
        else
        {
            //insert data into database
            dataBase.insert(mHelper.TABLE_NAME, null, values);
        }
        //close database
        dataBase.close();
        finish();


    }
    }


