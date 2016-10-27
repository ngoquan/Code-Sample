package ngovanquan_803656.datn.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ngovanquan_803656.datn.R;
import ngovanquan_803656.datn.activity.HoSoDetailActivity;
import ngovanquan_803656.datn.asynctask.qllt.SearchPhongAsyncTask;
import ngovanquan_803656.datn.fragment.TimKiemNangCaoFragment;
import ngovanquan_803656.datn.fragment.TrangChuFragment;
import ngovanquan_803656.datn.model.HoSo_ett;
import ngovanquan_803656.datn.model.Phong_ett;

/**
 * Created by NGOQUAN on 5/31/2016.
 */
public class HoSoTrangChuAdapter extends ArrayAdapter<HoSo_ett> {

    private Activity activity;
    private int layout;
    private ArrayList<HoSo_ett> arrHoSo = new ArrayList<>();
    private ArrayList<Phong_ett> arrP = new ArrayList<>();

    public HoSoTrangChuAdapter(Activity activity, int layout, ArrayList<HoSo_ett> arrHoSo) {
        super(activity, layout, arrHoSo);
        this.activity = activity;
        this.layout = layout;
        this.arrHoSo = arrHoSo;
        try {
            arrP = new SearchPhongAsyncTask(activity, "ID", "", 1).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final HoSo_ett hoSo_ett = arrHoSo.get(position);
        if (convertView == null)
            convertView = LayoutInflater.from(activity).inflate(R.layout.list_item_hoso_trangchu, parent, false);

        TextView txt_STT = (TextView) convertView.findViewById(R.id.txt_STT);
        TextView txt_tenPhong = (TextView) convertView.findViewById(R.id.txt_tenPhong);
        TextView txt_hoSoSo = (TextView) convertView.findViewById(R.id.txt_hoSoSo);
        TextView txt_tieuDeHS = (TextView) convertView.findViewById(R.id.txt_tieuDeHS);
        TextView txt_SLT = (TextView) convertView.findViewById(R.id.txt_SLT);
        if (TrangChuFragment.pageHoSo > 1)
            txt_STT.setText((TrangChuFragment.pageHoSo - 1) * 10 + (position + 1) + "");
        else if (TimKiemNangCaoFragment.pageHoSo > 1)
            txt_STT.setText((TimKiemNangCaoFragment.pageHoSo - 1) * 10 + (position + 1) + "");
        else {
            txt_STT.setText((position + 1) + "");
        }
        for (Phong_ett phong_ett : arrP) {
            if (hoSo_ett.getMaPhong() == phong_ett.getMaPhong()) {
                txt_tenPhong.setText(phong_ett.getTenPhong());
            }
        }
        txt_hoSoSo.setText(hoSo_ett.getHoSoSo());
        txt_tieuDeHS.setText(hoSo_ett.getTieuDeHs());
        txt_SLT.setText(hoSo_ett.getSoLuongTo());

        txt_hoSoSo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, HoSoDetailActivity.class);
                intent.putExtra("HoSo_ett", hoSo_ett);
                activity.startActivity(intent);
            }
        });
        txt_tieuDeHS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, HoSoDetailActivity.class);
                intent.putExtra("HoSo_ett", hoSo_ett);
                activity.startActivity(intent);
            }
        });
        return convertView;
    }
}
