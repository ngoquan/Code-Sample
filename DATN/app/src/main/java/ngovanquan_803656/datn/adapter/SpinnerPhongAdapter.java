package ngovanquan_803656.datn.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ngovanquan_803656.datn.R;
import ngovanquan_803656.datn.model.Phong_ett;

/**
 * Created by ngoquan on 5/12/2016.
 */
public class SpinnerPhongAdapter extends ArrayAdapter<Phong_ett> {
    private Activity activity;
    private int layout;
    private ArrayList<Phong_ett> arrData = new ArrayList<>();
    public SpinnerPhongAdapter(Activity activity, int layout, ArrayList<Phong_ett> arrData) {
        super(activity, layout, arrData);
        this.activity = activity;
        this.layout = layout;
        this.arrData = arrData;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Phong_ett phong_ett = arrData.get(position);
        if (convertView == null)
            convertView = LayoutInflater.from(activity).inflate(R.layout.list_item_spinner, parent, false);
        TextView txt_value = (TextView) convertView.findViewById(R.id.txt_value);
        txt_value.setText(phong_ett.getMaPhong() + " - " + phong_ett.getTenPhong());
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        Phong_ett phong_ett = arrData.get(position);
        if (convertView == null)
            convertView = LayoutInflater.from(activity).inflate(R.layout.list_drop_down_spinner, parent, false);
        TextView txt_value = (TextView) convertView.findViewById(R.id.txt_value);
        txt_value.setText(phong_ett.getMaPhong() + " - " + phong_ett.getTenPhong());
        return convertView;
    }
}
