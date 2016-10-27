package ngovanquan_803656.datn.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ngovanquan_803656.datn.R;
import ngovanquan_803656.datn.asynctask.qllt.GetNumberOfObject;
import ngovanquan_803656.datn.model.Phong_ett;
import ngovanquan_803656.datn.utils.Constants;

/**
 * Created by NGOQUAN on 6/3/2016.
 */
public class DanhSachPhongAdapter extends ArrayAdapter<Phong_ett> implements View.OnClickListener {

    private Activity activity;
    private int layout;
    private ArrayList<Phong_ett> arrP = new ArrayList<>();

    public DanhSachPhongAdapter(Activity activity, int layout, ArrayList<Phong_ett> arrP) {
        super(activity, layout, arrP);
        this.activity = activity;
        this.layout = layout;
        this.arrP = arrP;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Phong_ett phong_ett = arrP.get(position);
        if (convertView == null)
            convertView = LayoutInflater.from(activity).inflate(R.layout.list_item_danh_sach_phong, parent, false);
        TextView txt_maPhong = (TextView) convertView.findViewById(R.id.txt_maPhong);
        TextView txt_tenPhong = (TextView) convertView.findViewById(R.id.txt_tenPhong);
        TextView txt_thoiGian = (TextView) convertView.findViewById(R.id.txt_thoiGian);
        TextView txt_soLuong = (TextView) convertView.findViewById(R.id.txt_soLuong);
        TextView txt_ghiChu = (TextView) convertView.findViewById(R.id.txt_ghiChu);

        txt_maPhong.setText(phong_ett.getMaPhong() + "");
        txt_tenPhong.setText(phong_ett.getTenPhong());
        txt_thoiGian.setText(phong_ett.getThoiGianNhapTaiLieu());
        txt_ghiChu.setText(phong_ett.getGhiChu());
        String MaPhong = String.valueOf(phong_ett.getMaPhong());
        try {
            int total_hopHS = new GetNumberOfObject(activity, Constants.F_GET_NB_HOPHS_PHONG, MaPhong).execute().get();
            int total_hoSo = new GetNumberOfObject(activity, Constants.F_GET_NB_HOSO_PHONG, MaPhong).execute().get();
            int total_vanBan = new GetNumberOfObject(activity, Constants.F_GET_NB_VB_PHONG, MaPhong).execute().get();
//            int total_vanBan_fileAttach = new GetNumberOfObject(activity, Constants.F_GET_NB_VB_FILE_ATTACH_PHONG, MaPhong).execute().get();

//            Log.e("Resulttotal", total_hopHS + " - " + total_hoSo + " - " + total_vanBan + " - " + total_vanBan_fileAttach);
            txt_soLuong.setText(total_hopHS + total_hoSo + total_vanBan + "");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        txt_maPhong.setOnClickListener(this);
        txt_tenPhong.setOnClickListener(this);
        txt_thoiGian.setOnClickListener(this);
        txt_soLuong.setOnClickListener(this);
        txt_ghiChu.setOnClickListener(this);

        return convertView;
    }

    @Override
    public void onClick(View v) {

    }
}
