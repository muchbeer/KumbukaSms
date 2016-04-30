package king.muchbeer.kumbukasms.sqlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import king.muchbeer.kumbukasms.R;

/**
 * Created by muchbeer on 4/28/2016.
 */
public class ListDataAdapter extends ArrayAdapter {

    List list= new ArrayList();
    public ListDataAdapter(Context context, int resource) {
        super(context, resource);
    }
    static class LayoutHandler{
        TextView ADDRESS,BODY, NAME;
    }
    public void add(Object object){
        super.add(object);
        list.add(object);

    }
    public int getCount(){
        return list.size();
    }
    public Object getTtem(int position){
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LayoutHandler layoutHandler;
        if(row == null)
        {
            LayoutInflater layoutInflater =(LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=layoutInflater.inflate(R.layout.row_layout,parent,false);
            layoutHandler=new LayoutHandler();
            layoutHandler.ADDRESS=(TextView)row.findViewById(R.id.test_user_name);
            layoutHandler.BODY=(TextView)row.findViewById(R.id.test_user_mob);
          //  layoutHandler.NAME = (TextView) row.findViewById(R.id.test_user);
         //   layoutHandler.EMAIL=(TextView)row.findViewById(R.id.test_user_Email);
            row.setTag(layoutHandler);
        }

        else{
            layoutHandler=(LayoutHandler)row.getTag();
        }
        DataProvider dataProvider=(DataProvider)this.getTtem(position);
        layoutHandler.ADDRESS.setText(dataProvider.getAddress());
        layoutHandler.BODY.setText(dataProvider.getBody());
     //   layoutHandler.NAME.setText(dataProvider.getBody());
     //   layoutHandler.EMAIL.setText(dataProvider.getEmail());
        return row;
    }

}
