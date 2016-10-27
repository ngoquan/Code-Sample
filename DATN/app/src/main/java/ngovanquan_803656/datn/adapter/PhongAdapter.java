package ngovanquan_803656.datn.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ngovanquan_803656.datn.R;
import ngovanquan_803656.datn.asynctask.qldm.SearchNgonNguAsyncTask;
import ngovanquan_803656.datn.asynctask.qldm.SearchCQLTAsyncTask;
import ngovanquan_803656.datn.asynctask.qllt.DeletePhongAsyncTask;
import ngovanquan_803656.datn.asynctask.qllt.SearchPhongAsyncTask;
import ngovanquan_803656.datn.model.CQLT_ett;
import ngovanquan_803656.datn.model.NgonNgu_ett;
import ngovanquan_803656.datn.model.Phong_ett;
import ngovanquan_803656.datn.utils.Helper;

/**
 * Created by ngoquan on 5/11/2016.
 */
public class PhongAdapter extends ArrayAdapter<Phong_ett> {
    private Activity activity;
    private int layout;
    private ArrayList<Phong_ett> arrData = new ArrayList<>();

    public PhongAdapter(Activity activity, int layout, ArrayList<Phong_ett> arrData) {
        super(activity, layout, arrData);
        this.activity = activity;
        this.layout = layout;
        this.arrData = arrData;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Phong_ett phong_ett = arrData.get(position);
        if (convertView == null)
            convertView = LayoutInflater.from(activity).inflate(R.layout.list_item_cqlt, parent, false);
        TextView txt_STT = (TextView) convertView.findViewById(R.id.txt_STT);
        TextView txt_MaPhong = (TextView) convertView.findViewById(R.id.txt_MaCQLT);
        TextView txt_TenPhong = (TextView) convertView.findViewById(R.id.txt_TenCQLT);
        TextView txt_GhiChu = (TextView) convertView.findViewById(R.id.txt_DiaChi);
        ImageButton btn_update = (ImageButton) convertView.findViewById(R.id.btn_update);
        ImageButton btn_delete = (ImageButton) convertView.findViewById(R.id.btn_delete);

        txt_STT.setText((position + 1) + "");
        txt_MaPhong.setText(phong_ett.getMaPhong() + "");
        txt_TenPhong.setText(phong_ett.getTenPhong());
        txt_GhiChu.setText(phong_ett.getGhiChu());

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText txt_MaPhong = (EditText) activity.findViewById(R.id.txt_MaPhong);
                EditText txt_TenPhong = (EditText) activity.findViewById(R.id.txt_TenPhong);
                EditText txt_TSTL = (EditText) activity.findViewById(R.id.txt_TSTL);
                EditText txt_STLCCL = (EditText) activity.findViewById(R.id.txt_STLCCL);
                EditText txt_STLDCL = (EditText) activity.findViewById(R.id.txt_STLDCL);
                EditText txt_CCTC = (EditText) activity.findViewById(R.id.txt_CCTC);
                EditText txt_TGTL = (EditText) activity.findViewById(R.id.txt_TGTL);
                EditText txt_CNTL = (EditText) activity.findViewById(R.id.txt_CNTL);
                EditText txt_TGNTL = (EditText) activity.findViewById(R.id.txt_TGNTL);
                EditText txt_LBSBH = (EditText) activity.findViewById(R.id.txt_LBSBH);
                EditText txt_LSPT = (EditText) activity.findViewById(R.id.txt_LSPT);
                EditText txt_GhiChu = (EditText) activity.findViewById(R.id.txt_GhiChu);
                Spinner spn_CQLT = (Spinner) activity.findViewById(R.id.spn_CQLT);
                Spinner spn_NgonNgu = (Spinner) activity.findViewById(R.id.spn_NgonNgu);
                Button btn_AddNew = (Button) activity.findViewById(R.id.btn_AddNew);
                Button btn_Update = (Button) activity.findViewById(R.id.btn_Update);

                txt_MaPhong.setText(phong_ett.getMaPhong() + "");
                txt_TenPhong.setText(phong_ett.getTenPhong());
                txt_TSTL.setText(phong_ett.getTongSoTaiLieu() + "");
                txt_STLCCL.setText(phong_ett.getSoTaiLieuChuaChinhLy() + "");
                txt_STLDCL.setText(phong_ett.getSoTaiLieuDaChinhLy() + "");
                txt_CCTC.setText(phong_ett.getCongCuTraCuu());
                txt_TGTL.setText(phong_ett.getThoiGianTaiLieu());
                txt_CNTL.setText(phong_ett.getCacNhomTaiLieu());
                txt_TGNTL.setText(phong_ett.getThoiGianNhapTaiLieu());
                txt_LBSBH.setText(phong_ett.getLapBanSaoBaoHiem());
                txt_LSPT.setText(phong_ett.getLichSuHinhThanh());
                txt_GhiChu.setText(phong_ett.getGhiChu());
                try {
                    ArrayList<CQLT_ett> cqlt_ett = new SearchCQLTAsyncTask(activity, "ID", "", 0, 0).execute().get();
                    for (int i = 0; i < cqlt_ett.size(); i++) {
                        if (cqlt_ett.get(i).getMaCQLT().equals(phong_ett.getMaCQLT()))
                            spn_CQLT.setSelection(i);
                    }
                    ArrayList<NgonNgu_ett> arrNgonNgu = new SearchNgonNguAsyncTask(activity, "ID", "", 0, 0).execute().get();
                    for (int j = 0; j < arrNgonNgu.size(); j++) {
                        if (arrNgonNgu.get(j).getMaNN() == phong_ett.getMaNN())
                            spn_NgonNgu.setSelection(j);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                btn_AddNew.setVisibility(View.GONE);
                btn_Update.setVisibility(View.VISIBLE);
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ListView lv_p = (ListView) activity.findViewById(R.id.lv_p);
//                final ArrayList<Phong_ett> arrPhong = new ArrayList<Phong_ett>();
//                PhongAdapter phongAdapter;

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
                alertDialog.setIcon(R.drawable.question_delete);
                alertDialog.setTitle(activity.getResources().getString(R.string.title_delete));
                alertDialog.setMessage(activity.getResources().getString(R.string.message_delete));

                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "You selected No", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        long MaPhong = phong_ett.getMaPhong();
                        try {
                            Phong_ett phongEtt = new DeletePhongAsyncTask(activity, MaPhong).execute().get();
                            arrData.remove(position);
                            ListView lv_p = (ListView) activity.findViewById(R.id.lv_p);
                            ArrayList<Phong_ett> arrPhong = new SearchPhongAsyncTask(activity, "ID", "", 1).execute().get();
                            PhongAdapter phongAdapter = new PhongAdapter(activity, R.layout.list_item_cqlt, arrPhong);
                            lv_p.setAdapter(phongAdapter);
                            Helper.setListViewHeightBasedOnChildren(lv_p);
                            Toast.makeText(getContext(), phongEtt.getErrDesc(), Toast.LENGTH_LONG).show();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }

                    }
                });
                alertDialog.show();
            }
        });
        return convertView;
    }
}
