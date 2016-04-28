package king.muchbeer.kumbukasms.sqlite;

/**
 * Created by muchbeer on 4/28/2016.
 */

public class DataProvider {
    private String address;
    private String body;
   // private String email;

    public DataProvider(String smsAddress,String smsBody)
    {
        this.address = smsAddress;
        this.body=smsBody;
     //   this.email=email;

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String smsAddress) {
        this.address = smsAddress;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String smsBody) {
        this.body = smsBody;
    }

    /*
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    */
}
