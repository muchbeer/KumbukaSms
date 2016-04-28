package king.muchbeer.kumbukasms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by muchbeer on 4/27/2016.
 */
public class DisplayAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> id;
    private ArrayList<String> smsAddress;
    private ArrayList<String> smsBody;

    public DisplayAdapter(Context c, ArrayList<String> id, ArrayList<String> sAddress, ArrayList<String> sBody) {

        this.mContext = c;

        this.id = id;
        this.smsAddress = sAddress;
        this.smsBody = sBody;
    }




    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View child, ViewGroup viewGroup) {
        Holder mHolder;
        LayoutInflater layoutInflater;
        if (child == null) {
            layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            child = layoutInflater.inflate(R.layout.listcell, null);
            mHolder = new Holder();
            mHolder.txt_id = (TextView) child.findViewById(R.id.txt_id);
            mHolder.txt_fName = (TextView) child.findViewById(R.id.txt_fName);
            mHolder.txt_lName = (TextView) child.findViewById(R.id.txt_lName);
            child.setTag(mHolder);
        } else {
            mHolder = (Holder) child.getTag();
        }
        mHolder.txt_id.setText(id.get(position));
        mHolder.txt_fName.setText(smsAddress.get(position));
        mHolder.txt_lName.setText(smsBody.get(position));

        return child;
    }

    public class Holder {
        TextView txt_id;
        TextView txt_fName;
        TextView txt_lName;
    }

}
