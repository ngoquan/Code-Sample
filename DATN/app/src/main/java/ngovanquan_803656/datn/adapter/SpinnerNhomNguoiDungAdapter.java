package ngovanquan_803656.datn.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ngovanquan_803656.datn.R;
import ngovanquan_803656.datn.model.NhomNguoiDung_ett;

/**
 * Created by NGOQUAN on 5/27/2016.
 */
public class SpinnerNhomNguoiDungAdapter extends ArrayAdapter<NhomNguoiDung_ett> {

    private Activity activity;
    private int layout;
    private ArrayList<NhomNguoiDung_ett> arrNND = new ArrayList<>();
    public SpinnerNhomNguoiDungAdapter(Activity activity, int layout, ArrayList<NhomNguoiDung_ett> arrNND) {
        super(activity, layout, arrNND);
        this.activity = activity;
        this.layout = layout;
        this.arrNND = arrNND;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NhomNguoiDung_ett nhomNguoiDung_ett = arrNND.get(position);
        if (convertView == null)
            convertView = LayoutInflater.from(activity).inflate(R.layout.list_item_spinner, parent, false);
        TextView txt_value = (TextView) convertView.findViewById(R.id.txt_value);
        txt_value.setText(nhomNguoiDung_ett.getMaNhom() + " - " + nhomNguoiDung_ett.getTenNhom());
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        NhomNguoiDung_ett nhomNguoiDung_ett = arrNND.get(position);
        if (convertView == null)
            convertView = LayoutInflater.from(activity).inflate(R.layout.list_drop_down_spinner, parent, false);
        TextView txt_value = (TextView) convertView.findViewById(R.id.txt_value);
        txt_value.setText(nhomNguoiDung_ett.getMaNhom() + " - " + nhomNguoiDung_ett.getTenNhom());
        return convertView;
    }
}
