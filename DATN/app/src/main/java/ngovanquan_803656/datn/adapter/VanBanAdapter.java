package ngovanquan_803656.datn.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ngovanquan_803656.datn.R;
import ngovanquan_803656.datn.activity.AddNewAndUpdateVanBanActivity;
import ngovanquan_803656.datn.activity.VanBanDetailActivity;
import ngovanquan_803656.datn.asynctask.qllt.DeleteVanBanAsyncTask;
import ngovanquan_803656.datn.asynctask.qllt.SearchVanBanAsyncTask;
import ngovanquan_803656.datn.model.VanBan_ett;
import ngovanquan_803656.datn.utils.Helper;

/**
 * Created by NGOQUAN on 5/24/2016.
 */
public class VanBanAdapter extends ArrayAdapter<VanBan_ett> {
    private Activity activity;
    private int layout;
    private ArrayList<VanBan_ett> arrVanBan = new ArrayList<>();
    public VanBanAdapter(Activity activity, int layout, ArrayList<VanBan_ett> arrVanBan) {
        super(activity, layout, arrVanBan);
        this.activity = activity;
        this.layout = layout;
        this.arrVanBan = arrVanBan;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final VanBan_ett vanBan_ett = arrVanBan.get(position);
        if (convertView == null)
            convertView = LayoutInflater.from(activity).inflate(layout, parent, false);
        TextView txt_STT = (TextView) convertView.findViewById(R.id.txt_STT);
        TextView txt_tacGia = (TextView) convertView.findViewById(R.id.txt_tacGia);
        TextView txt_TYND = (TextView) convertView.findViewById(R.id.txt_TYND);
        ImageButton btn_info = (ImageButton) convertView.findViewById(R.id.btn_info);
        ImageButton btn_update = (ImageButton) convertView.findViewById(R.id.btn_update);
        ImageButton btn_delete = (ImageButton) convertView.findViewById(R.id.btn_delete);

        txt_STT.setText((ExpandableListAdapter.pageVanBan - 1) * 10 + (position + 1) + "");
        txt_tacGia.setText(vanBan_ett.getTacGia());
        txt_TYND.setText(vanBan_ett.getTrichYeuND());

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity, AddNewAndUpdateVanBanActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("MaPhong", MenuPhongAdapter.MaPhong);
                bundle.putString("TenPhong", MenuPhongAdapter.TenPhong);
                bundle.putString("MaHop", MenuPhongAdapter.MaHop);
                bundle.putString("TenHop", MenuPhongAdapter.TenHop);
                bundle.putString("HoSoSo", MenuPhongAdapter.HoSoSo);
                bundle.putSerializable("VanBan_ett", vanBan_ett);
                intent.putExtra("Data1", bundle);
                activity.startActivity(intent);

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
                        String VanBanID = vanBan_ett.getVanBanId();
                        try {
                            VanBan_ett vanBanEtt = new DeleteVanBanAsyncTask(activity, VanBanID).execute().get();
                            arrVanBan.remove(position);
                            MenuPhongAdapter.vanBanAdapter = new VanBanAdapter(activity, R.layout.list_item_vanban, arrVanBan);
                            MenuPhongAdapter.lv_vanBan.setAdapter(MenuPhongAdapter.vanBanAdapter);
                            Helper.setListViewHeightBasedOnChildren(MenuPhongAdapter.lv_vanBan);
                            MenuPhongAdapter.type = 3;
                            if (!arrVanBan.isEmpty()) {
                                MenuPhongAdapter.total_record = arrVanBan.get(0).getTotal_record();
                                MenuPhongAdapter.pageLoad(MenuPhongAdapter.type, MenuPhongAdapter.pageVanBan);
                            }
                            Toast.makeText(getContext(), vanBanEtt.getErrDesc(), Toast.LENGTH_LONG).show();
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

        btn_info.setOnClickListener(new View.OnClickListener() {
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
