package ngovanquan_803656.datn.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ngovanquan_803656.datn.R;
import ngovanquan_803656.datn.fragment.TimKiemNangCaoFragment;
import ngovanquan_803656.datn.model.Phong_ett;

/**
 * Created by NGOQUAN on 6/3/2016.
 */
public class PhongSearchAdapter extends ArrayAdapter<Phong_ett> {

    private Activity activity;
    private int layout;
    private ArrayList<Phong_ett> arrP = new ArrayList<>();

    public PhongSearchAdapter(Activity activity, int layout, ArrayList<Phong_ett> arrP) {
        super(activity, layout, arrP);
        this.activity = activity;
        this.layout = layout;
        this.arrP = arrP;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Phong_ett phong_ett = arrP.get(position);
        if (convertView == null)
            convertView = LayoutInflater.from(activity).inflate(R.layout.list_item_search_phong, parent, false);
        TextView txt_STT = (TextView) convertView.findViewById(R.id.txt_STT);
        TextView txt_maPhong = (TextView) convertView.findViewById(R.id.txt_maPhong);
        TextView txt_tenPhong = (TextView) convertView.findViewById(R.id.txt_tenPhong);
        TextView txt_ghiChu = (TextView) convertView.findViewById(R.id.txt_ghiChu);

        txt_STT.setText((TimKiemNangCaoFragment.pagePhong - 1) * 10 + (position + 1) + "");
        txt_maPhong.setText(phong_ett.getMaPhong() + "");
        txt_tenPhong.setText(phong_ett.getTenPhong());
        txt_ghiChu.setText(phong_ett.getGhiChu());
        return convertView;
    }
}
