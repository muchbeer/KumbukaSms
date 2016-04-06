package king.muchbeer.kumbukasms;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by muchbeer on 4/6/2016.
 */
public class ToastMessage {

    public static void message(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
