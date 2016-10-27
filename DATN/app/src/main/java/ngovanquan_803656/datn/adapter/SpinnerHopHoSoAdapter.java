package ngovanquan_803656.datn.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ngovanquan_803656.datn.R;
import ngovanquan_803656.datn.model.HopHoSo_ett;

/**
 * Created by NGOQUAN on 5/23/2016.
 */
public class SpinnerHopHoSoAdapter extends ArrayAdapter<HopHoSo_ett> {
    private Activity activity;
    private int layout;
    private ArrayList<HopHoSo_ett> arrData = new ArrayList<>();
    public SpinnerHopHoSoAdapter(Activity activity, int layout, ArrayList<HopHoSo_ett> arrData) {
        super(activity, layout, arrData);
        this.activity = activity;
        this.layout = layout;
        this.arrData = arrData;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HopHoSo_ett hopHoSo_ett = arrData.get(position);
        if (convertView == null)
            convertView = LayoutInflater.from(activity).inflate(R.layout.list_item_spinner, parent, false);
        TextView txt_value = (TextView) convertView.findViewById(R.id.txt_value);
        txt_value.setText(hopHoSo_ett.getMaHopHS() + " - " + hopHoSo_ett.getTenHop());
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        HopHoSo_ett hopHoSo_ett = arrData.get(position);
        if (convertView == null)
            convertView = LayoutInflater.from(activity).inflate(R.layout.list_drop_down_spinner, parent, false);
        TextView txt_value = (TextView) convertView.findViewById(R.id.txt_value);
        txt_value.setText(hopHoSo_ett.getMaHopHS() + " - " + hopHoSo_ett.getTenHop());
        return convertView;
    }
}
