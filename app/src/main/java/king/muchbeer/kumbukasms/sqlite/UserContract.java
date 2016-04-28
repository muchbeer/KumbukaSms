package king.muchbeer.kumbukasms.sqlite;

/**
 * Created by muchbeer on 4/28/2016.
 */
public class UserContract {

    public static abstract class NewUserInfo
    {
        public static final String db_name = "sms.db";
        public static final String TABLE_NAME="database";
        public static final String COLUMN_ADDRESS = "address";
        public static final String COLUMN_BODY = "body";
        public static final String COLUMN_ID="id";
    }
}
