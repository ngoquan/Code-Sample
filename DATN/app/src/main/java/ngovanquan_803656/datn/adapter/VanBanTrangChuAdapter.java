package ngovanquan_803656.datn.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ngovanquan_803656.datn.R;
import ngovanquan_803656.datn.activity.VanBanDetailActivity;
import ngovanquan_803656.datn.fragment.TimKiemNangCaoFragment;
import ngovanquan_803656.datn.fragment.TrangChuFragment;
import ngovanquan_803656.datn.model.VanBan_ett;

/**
 * Created by NGOQUAN on 5/31/2016.
 */
public class VanBanTrangChuAdapter extends ArrayAdapter<VanBan_ett>{

    private Activity activity;
    private int layout;
    private ArrayList<VanBan_ett> arrVanBan = new ArrayList<>();

    public VanBanTrangChuAdapter(Activity activity, int layout, ArrayList<VanBan_ett> arrVanBan) {
        super(activity, layout, arrVanBan);
        this.activity = activity;
        this.layout = layout;
        this.arrVanBan = arrVanBan;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final VanBan_ett vanBan_ett = arrVanBan.get(position);
        if (convertView == null)
            convertView = LayoutInflater.from(activity).inflate(R.layout.list_item_vanban_trangchu, parent, false);
        TextView txt_STT = (TextView) convertView.findViewById(R.id.txt_STT);
        TextView txt_hoSoSo = (TextView) convertView.findViewById(R.id.txt_hoSoSo);
        TextView txt_mucLucSo = (TextView) convertView.findViewById(R.id.txt_mucLucSo);
        TextView txt_soKHVB = (TextView) convertView.findViewById(R.id.txt_soKHVB);
        TextView txt_trichYeu = (TextView) convertView.findViewById(R.id.txt_trichYeu);

        if (TrangChuFragment.pageVanBan > 1)
            txt_STT.setText((TrangChuFragment.pageVanBan - 1) * 10 + (position + 1) + "");
        else if (TimKiemNangCaoFragment.pageVanBan > 1)
            txt_STT.setText((TimKiemNangCaoFragment.pageVanBan - 1) * 10 + (position + 1) + "");
        else
            txt_STT.setText((position + 1) + "");
        txt_hoSoSo.setText(vanBan_ett.getHoSoSo());
        txt_mucLucSo.setText(vanBan_ett.getMucLucSo());
        txt_soKHVB.setText(vanBan_ett.getSoKyHieu());
        txt_trichYeu.setText(vanBan_ett.getTrichYeuND());

        txt_soKHVB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, VanBanDetailActivity.class);
                intent.putExtra("VanBan_ett", vanBan_ett);
                activity.startActivity(intent);
            }
        });

        txt_trichYeu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, VanBanDetailActivity.class);
                intent.putExtra("VanBan_ett", vanBan_ett);
                activity.startActivity(intent);
            }
        });
        return convertView;
    }
}
