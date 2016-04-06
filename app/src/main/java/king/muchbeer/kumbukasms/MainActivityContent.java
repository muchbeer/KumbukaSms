package king.muchbeer.kumbukasms;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.security.PrivateKey;

/**
 * Created by muchbeer on 4/5/2016.
 */
public class MainActivityContent extends AppCompatActivity {

    private Context context;
    SmsBroadcastReceiver dataBaseHelper = new SmsBroadcastReceiver();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_content);

       // String data= dataBaseHelper.getAllData();
     //   ToastMessage.message(this, data);
    }
}
