package ngovanquan_803656.datn.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ngovanquan_803656.datn.R;
import ngovanquan_803656.datn.fragment.tkbc.MucLucVanBanFragment;
import ngovanquan_803656.datn.model.VanBan_ett;

/**
 * Created by NGOQUAN on 6/2/2016.
 */
public class MucLucVanBanAdapter extends ArrayAdapter<VanBan_ett> implements View.OnClickListener{

    private Activity activity;
    private int layout;
    private ArrayList<VanBan_ett> arrVanBan = new ArrayList<>();
    public MucLucVanBanAdapter(Activity activity, int layout, ArrayList<VanBan_ett> arrVanBan) {
        super(activity, layout, arrVanBan);
        this.activity = activity;
        this.layout = layout;
        this.arrVanBan = arrVanBan;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VanBan_ett vanBan_ett = arrVanBan.get(position);
        if (convertView == null)
            convertView = LayoutInflater.from(activity).inflate(R.layout.list_item_muc_luc_van_ban, parent, false);
        TextView txt_STT = (TextView) convertView.findViewById(R.id.txt_STT);
        TextView txt_soKHVB = (TextView) convertView.findViewById(R.id.txt_soKHVB);
        TextView txt_ngayThang = (TextView) convertView.findViewById(R.id.txt_ngayThang);
        TextView txt_trichYeu = (TextView) convertView.findViewById(R.id.txt_trichYeu);
        TextView txt_tacGia = (TextView) convertView.findViewById(R.id.txt_tacGia);

        txt_STT.setText((MucLucVanBanFragment.pageVanBan - 1) * 10 + (position + 1) + "");
        txt_soKHVB.setText(vanBan_ett.getSoKyHieu());
        txt_ngayThang.setText(vanBan_ett.getThoiGian());
        txt_trichYeu.setText(vanBan_ett.getTrichYeuND());
        txt_tacGia.setText(vanBan_ett.getTacGia());

        txt_STT.setOnClickListener(this);
        txt_soKHVB.setOnClickListener(this);
        txt_ngayThang.setOnClickListener(this);
        txt_trichYeu.setOnClickListener(this);
        txt_tacGia.setOnClickListener(this);


        return convertView;
    }

    @Override
    public void onClick(View v) {

    }
}
