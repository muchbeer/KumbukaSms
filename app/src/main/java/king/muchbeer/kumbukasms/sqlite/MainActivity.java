package king.muchbeer.kumbukasms.sqlite;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import king.muchbeer.kumbukasms.R;
import king.muchbeer.kumbukasms.ToastMessage;

/**
 * Created by muchbeer on 4/28/2016.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sqlite);

        Button additem = (Button) findViewById(R.id.additem);
        Button viewitem = (Button) findViewById(R.id.viewitem);

        additem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Already on solved to Search Address", Toast.LENGTH_LONG).show();
                Intent iSearch = new Intent(MainActivity.this, GetAddressActivity.class);
                startActivity(iSearch);
            }
        });

        viewitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,DisplayListActivity.class);
                startActivity(i);
            }
        });
    }
}
