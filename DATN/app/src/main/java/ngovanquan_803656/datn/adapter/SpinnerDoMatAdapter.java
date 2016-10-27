package ngovanquan_803656.datn.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ngovanquan_803656.datn.R;
import ngovanquan_803656.datn.model.DoMat_ett;

/**
 * Created by NGOQUAN on 5/25/2016.
 */
public class SpinnerDoMatAdapter extends ArrayAdapter<DoMat_ett> {
    private Activity activity;
    private int layout;
    private ArrayList<DoMat_ett> arrData = new ArrayList<>();
    public SpinnerDoMatAdapter(Activity activity, int layout, ArrayList<DoMat_ett> arrData) {
        super(activity, layout, arrData);
        this.activity = activity;
        this.layout = layout;
        this.arrData = arrData;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DoMat_ett doMat_ett = arrData.get(position);
        if (convertView == null)
            convertView = LayoutInflater.from(activity).inflate(R.layout.list_item_spinner, parent, false);
        TextView txt_value = (TextView) convertView.findViewById(R.id.txt_value);
        txt_value.setText(doMat_ett.getMaDoMat() + " - " + doMat_ett.getTenDoMat());
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        DoMat_ett doMat_ett = arrData.get(position);
        if (convertView == null)
            convertView = LayoutInflater.from(activity).inflate(R.layout.list_drop_down_spinner, parent, false);
        TextView txt_value = (TextView) convertView.findViewById(R.id.txt_value);
        txt_value.setText(doMat_ett.getMaDoMat() + " - " + doMat_ett.getTenDoMat());
        return convertView;
    }
}
