package ngovanquan_803656.datn.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ngovanquan_803656.datn.R;
import ngovanquan_803656.datn.model.THBQ_ett;

/**
 * Created by NGOQUAN on 5/23/2016.
 */
public class SpinnerTHBQAdapter extends ArrayAdapter<THBQ_ett> {
    private Activity activity;
    private int layout;
    private ArrayList<THBQ_ett> arrData = new ArrayList<>();

    public SpinnerTHBQAdapter(Activity activity, int layout, ArrayList<THBQ_ett> arrData) {
        super(activity, layout, arrData);
        this.activity = activity;
        this.layout = layout;
        this.arrData = arrData;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        THBQ_ett thbq_ett = arrData.get(position);
        if (convertView == null)
            convertView = LayoutInflater.from(activity).inflate(R.layout.list_item_spinner, parent, false);
        TextView txt_value = (TextView) convertView.findViewById(R.id.txt_value);
        txt_value.setText(thbq_ett.getMaTHBQ() + " - " + thbq_ett.getTenTHBQ());
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        THBQ_ett thbq_ett = arrData.get(position);
        if (convertView == null)
            convertView = LayoutInflater.from(activity).inflate(R.layout.list_drop_down_spinner, parent, false);
        TextView txt_value = (TextView) convertView.findViewById(R.id.txt_value);
        txt_value.setText(thbq_ett.getMaTHBQ() + " - " + thbq_ett.getTenTHBQ());
        return convertView;
    }
}
