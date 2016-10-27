package ngovanquan_803656.datn.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ngovanquan_803656.datn.R;
import ngovanquan_803656.datn.activity.HoSoDetailActivity;
import ngovanquan_803656.datn.model.VanBan_ett;

/**
 * Created by NGOQUAN on 6/1/2016.
 */
public class HoSoDetailAdapter extends ArrayAdapter<VanBan_ett> {

    private Activity activity;
    private int layout;
    private ArrayList<VanBan_ett> arrVanBan = new ArrayList<>();

    public HoSoDetailAdapter(Activity activity, int layout, ArrayList<VanBan_ett> arrVanBan) {
        super(activity, layout, arrVanBan);
        this.activity = activity;
        this.layout = layout;
        this.arrVanBan = arrVanBan;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VanBan_ett vanBan_ett = arrVanBan.get(position);
        if (convertView == null)
            convertView = LayoutInflater.from(activity).inflate(R.layout.list_item_detail_vanban, parent, false);
        TextView txt_STT = (TextView) convertView.findViewById(R.id.txt_STT);
        TextView txt_soKHVB = (TextView) convertView.findViewById(R.id.txt_soKHVB);
        TextView txt_hoSoSo = (TextView) convertView.findViewById(R.id.txt_hoSoSo);
//        TextView txt_hopHoSo = (TextView) convertView.findViewById(R.id.txt_hopHoSo);
        TextView txt_trichYeu = (TextView) convertView.findViewById(R.id.txt_trichYeu);

        txt_STT.setText((HoSoDetailActivity.pageVanBan - 1) * 10 + (position + 1) + "");
        txt_soKHVB.setText(vanBan_ett.getSoKyHieu());
        txt_hoSoSo.setText(vanBan_ett.getHoSoSo());
//        txt_hopHoSo.setText(vanBan_ett.getM);
        txt_trichYeu.setText(vanBan_ett.getTrichYeuND());
        txt_STT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        txt_soKHVB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        txt_hoSoSo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        txt_trichYeu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return convertView;
    }
}
