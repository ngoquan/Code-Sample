package ngovanquan_803656.datn.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ngovanquan_803656.datn.R;
import ngovanquan_803656.datn.asynctask.qtht.AddNewUpdateAndDeleteNguoiDungAsyncTask;
import ngovanquan_803656.datn.fragment.qtht.NguoiDungFragment;
import ngovanquan_803656.datn.model.NhomNguoiDung_ett;
import ngovanquan_803656.datn.model.QLND_ett;
import ngovanquan_803656.datn.utils.Constants;
import ngovanquan_803656.datn.utils.Helper;

/**
 * Created by NGOQUAN on 5/27/2016.
 */
public class NguoiDungAdapter extends ArrayAdapter<QLND_ett> {

    private Activity activity;
    private int layout;
    private ArrayList<QLND_ett> arrND = new ArrayList<>();

    public NguoiDungAdapter(Activity activity, int layout, ArrayList<QLND_ett> arrND) {
        super(activity, layout, arrND);
        this.activity = activity;
        this.layout = layout;
        this.arrND = arrND;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final QLND_ett qlnd_ett = arrND.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(activity).inflate(R.layout.list_item_dtc, parent, false);
        }
        TextView txt_STT = (TextView) convertView.findViewById(R.id.txt_STT);
        TextView txt_taiKhoan = (TextView) convertView.findViewById(R.id.txt_MaDTC);
        TextView txt_tenNhom = (TextView) convertView.findViewById(R.id.txt_TenDTC);
        ImageView img_status = (ImageView) convertView.findViewById(R.id.img_Status);
        ImageButton btn_update = (ImageButton) convertView.findViewById(R.id.btn_Update);
        ImageButton btn_delete = (ImageButton) convertView.findViewById(R.id.btn_Delete);

        txt_STT.setText((position + 1) + "");
        txt_taiKhoan.setText(qlnd_ett.getLoginID());
        for (int i = 0; i < NguoiDungFragment.arrNND.size(); i++) {
            if (NguoiDungFragment.arrNND.get(i).getMaNhom() == qlnd_ett.getMaNhom()) {
                txt_tenNhom.setText(NguoiDungFragment.arrNND.get(i).getTenNhom());
            }
        }

        if (qlnd_ett.isActive())
            img_status.setImageResource(R.drawable.checked);
        else
            img_status.setImageResource(R.drawable.not_checked);

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NguoiDungFragment.txt_tenDN.setText(qlnd_ett.getLoginID());
                NguoiDungFragment.txt_tenDN.setEnabled(false);
//                NguoiDungFragment.txt_matKhau.setText(qlnd_ett.getMatKhau());
//                NguoiDungFragment.txt_NLMK.setText(qlnd_ett.getMatKhau());
                NguoiDungFragment.txt_hoTen.setText(qlnd_ett.getHoTen());
                NguoiDungFragment.txt_ghiChu.setText(qlnd_ett.getGhiChu());
                if (qlnd_ett.isActive())
                    NguoiDungFragment.cb_active.setChecked(true);
                else
                    NguoiDungFragment.cb_active.setChecked(false);
                for (int i = 0; i < NguoiDungFragment.arrNND.size(); i++) {
                    if (qlnd_ett.getMaNhom() == NguoiDungFragment.arrNND.get(i).getMaNhom())
                        NguoiDungFragment.spn_nhomND.setSelection(i);
                }
                NguoiDungFragment.btn_addNew.setVisibility(View.GONE);
                NguoiDungFragment.btn_update.setVisibility(View.VISIBLE);
            }
        });


        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                        String LoginID = qlnd_ett.getLoginID();
                        try {
                            QLND_ett qlndEtt = new AddNewUpdateAndDeleteNguoiDungAsyncTask(activity, Constants.FUNCTION_DELETE, LoginID, "", "",
                                    "", 0, false).execute().get();
                            arrND.remove(position);
                            NguoiDungFragment.nguoiDungAdapter = new NguoiDungAdapter(activity, R.layout.list_item_dtc, arrND);
                            NguoiDungFragment.lv_nguoiDung.setAdapter(NguoiDungFragment.nguoiDungAdapter);
                            Helper.setListViewHeightBasedOnChildren(NguoiDungFragment.lv_nguoiDung);
                            Toast.makeText(activity, qlndEtt.getErrDesc(), Toast.LENGTH_SHORT).show();
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
