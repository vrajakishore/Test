package courier.kishore.com.test.Declaration;

/**
 * Created by m.v on 06-05-2015.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import courier.kishore.com.test.R;

import java.util.ArrayList;
import java.util.HashMap;


import static courier.kishore.com.test.Declaration.Constant.FIRST_COLUMN;
import static courier.kishore.com.test.Declaration.Constant.FOURTH_COLUMN;
import static courier.kishore.com.test.Declaration.Constant.SECOND_COLUMN;
import static courier.kishore.com.test.Declaration.Constant.THIRD_COLUMN;

public class ListViewAdapter extends BaseAdapter {

    public ArrayList<HashMap<String, String>> list;
    Activity activity;
    //Fragment frg;
    TextView txtFirst;
    TextView txtSecond;
    TextView txtThird;
    TextView txtFourth;
    public ListViewAdapter(Activity activity,ArrayList<HashMap<String, String>> list){
        super();
        this.activity=activity;
        this.list=list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub



        LayoutInflater inflater = activity.getLayoutInflater();

        if(convertView == null){

            convertView=inflater.inflate(R.layout.table, null);

            txtFirst=(TextView) convertView.findViewById(R.id.email);
            txtSecond=(TextView) convertView.findViewById(R.id.name);
            txtThird=(TextView) convertView.findViewById(R.id.date);
            txtFourth=(TextView) convertView.findViewById(R.id.time);

        }

        HashMap<String, String> map=list.get(position);
        txtFirst.setText(map.get(FIRST_COLUMN));
        txtSecond.setText(map.get(SECOND_COLUMN));
        txtThird.setText(map.get(THIRD_COLUMN));
        txtFourth.setText(map.get(FOURTH_COLUMN));

        return convertView;
    }

}
