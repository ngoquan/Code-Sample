package ngovanquan_803656.datn.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ngovanquan_803656.datn.R;
import ngovanquan_803656.datn.asynctask.qtht.AddNewAndUpdateNhomNguoiDungAsyncTask;
import ngovanquan_803656.datn.fragment.qtht.NhomNguoiDungFragment;
import ngovanquan_803656.datn.model.NhomNguoiDung_ett;
import ngovanquan_803656.datn.utils.Constants;
import ngovanquan_803656.datn.utils.Helper;

/**
 * Created by NGOQUAN on 5/26/2016.
 */
public class NhomNguoiDungAdapter extends ArrayAdapter<NhomNguoiDung_ett> {

    private Activity activity;
    private int layout;
    private ArrayList<NhomNguoiDung_ett> arrNND = new ArrayList<>();

    public NhomNguoiDungAdapter(Activity activity, int layout, ArrayList<NhomNguoiDung_ett> arrNND) {
        super(activity, layout, arrNND);
        this.activity = activity;
        this.layout = layout;
        this.arrNND = arrNND;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final NhomNguoiDung_ett nhomNguoiDung_ett = arrNND.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(activity).inflate(R.layout.list_item_dtc, parent, false);
        }
        TextView txt_STT = (TextView) convertView.findViewById(R.id.txt_STT);
        TextView txt_maNhom = (TextView) convertView.findViewById(R.id.txt_MaDTC);
        TextView txt_tenNhom = (TextView) convertView.findViewById(R.id.txt_TenDTC);
        ImageView img_Status = (ImageView) convertView.findViewById(R.id.img_Status);
        ImageButton btn_update = (ImageButton) convertView.findViewById(R.id.btn_Update);
        ImageButton btn_delete = (ImageButton) convertView.findViewById(R.id.btn_Delete);

        txt_STT.setText((position + 1) + "");
        txt_maNhom.setText(nhomNguoiDung_ett.getMaNhom() + "");
        txt_tenNhom.setText(nhomNguoiDung_ett.getTenNhom());
        if (nhomNguoiDung_ett.isActive()) {
            img_Status.setImageResource(R.drawable.checked);
        } else
            img_Status.setImageResource(R.drawable.not_checked);

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                EditText txt_maNhom = (EditText) activity.findViewById(R.id.txt_maNhom);
//                EditText txt_tenNhom =(EditText) activity.findViewById(R.id.txt_tenNhom);
//                EditText txt_ghiChu =(EditText) activity.findViewById(R.id.txt_ghiChu);
//                CheckBox cb_active = (CheckBox) activity.findViewById(R.id.cb_active);
//                Button btn_addNew = (Button) activity.findViewById(R.id.btn_addNew);
//                Button btn_update = (Button) activity.findViewById(R.id.btn_update);

                NhomNguoiDungFragment.txt_maNhom.setText(nhomNguoiDung_ett.getMaNhom() + "");
                NhomNguoiDungFragment.txt_tenNhom.setText(nhomNguoiDung_ett.getTenNhom());
                NhomNguoiDungFragment.txt_ghiChu.setText(nhomNguoiDung_ett.getGhiChu());
                if (nhomNguoiDung_ett.isActive())
                    NhomNguoiDungFragment.cb_active.setChecked(true);
                else
                    NhomNguoiDungFragment.cb_active.setChecked(false);
                NhomNguoiDungFragment.btn_addNew.setVisibility(View.GONE);
                NhomNguoiDungFragment.btn_update.setVisibility(View.VISIBLE);
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
                        int MaNhom = nhomNguoiDung_ett.getMaNhom();
                        try {
                            NhomNguoiDung_ett nhomNguoiDungEtt = new AddNewAndUpdateNhomNguoiDungAsyncTask(activity, Constants.FUNCTION_DELETE, MaNhom,
                                    "", "", false).execute().get();
                            arrNND.remove(position);
                            NhomNguoiDungFragment.nhomNguoiDungAdapter = new NhomNguoiDungAdapter(activity, R.layout.list_item_dtc, arrNND);
                            NhomNguoiDungFragment.lv_nhomND.setAdapter(NhomNguoiDungFragment.nhomNguoiDungAdapter);
                            Helper.setListViewHeightBasedOnChildren(NhomNguoiDungFragment.lv_nhomND);
                            Toast.makeText(getContext(), nhomNguoiDungEtt.getErrDesc(), Toast.LENGTH_LONG).show();
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
