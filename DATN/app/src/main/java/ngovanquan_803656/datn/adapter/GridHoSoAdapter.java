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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ngovanquan_803656.datn.R;
import ngovanquan_803656.datn.activity.AddNewAndUpdateHoSoActivity;
import ngovanquan_803656.datn.activity.AddNewAndUpdateVanBanActivity;
import ngovanquan_803656.datn.activity.HoSoDetailActivity2;
import ngovanquan_803656.datn.asynctask.qllt.DeleteHoSoAsyncTask;
import ngovanquan_803656.datn.asynctask.qllt.SearchHoSoAsyncTask;
import ngovanquan_803656.datn.asynctask.qllt.SearchVanBanAsyncTask;
import ngovanquan_803656.datn.model.HoSo_ett;
import ngovanquan_803656.datn.utils.Constants;
import ngovanquan_803656.datn.utils.Helper;

/**
 * Created by ngoquan on 5/17/2016.
 */
public class GridHoSoAdapter extends ArrayAdapter<HoSo_ett> {
    private Activity activity;
    private int layout;
    private ArrayList<HoSo_ett> arrData = new ArrayList<>();
    public GridHoSoAdapter(Activity activity, int layout, ArrayList<HoSo_ett> arrData) {
        super(activity, layout, arrData);
        this.activity = activity;
        this.layout = layout;
        this.arrData = arrData;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final HoSo_ett hoSo_ett = arrData.get(position);
        if (convertView == null)
            convertView = LayoutInflater.from(activity).inflate(R.layout.grid_item, parent, false);
        ImageView img_item = (ImageView) convertView.findViewById(R.id.img_item);
        TextView txt_item = (TextView) convertView.findViewById(R.id.txt_item);
        LinearLayout ll_button = (LinearLayout) convertView.findViewById(R.id.ll_button);
        ImageButton btn_update = (ImageButton) convertView.findViewById(R.id.btn_update);
        ImageButton btn_info = (ImageButton) convertView.findViewById(R.id.btn_info);
        ImageButton btn_delete = (ImageButton) convertView.findViewById(R.id.btn_delete);

        img_item.setImageResource(R.drawable.hsicon);
        txt_item.setText("HS Số: " + hoSo_ett.getIDShow());
        img_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    MenuPhongAdapter.arrVanBan = new SearchVanBanAsyncTask(activity, "VbbyHs", hoSo_ett.getHoSoSo(), 1).execute().get();
                    if (!MenuPhongAdapter.arrVanBan.isEmpty()) {
                        MenuPhongAdapter.vanBanAdapter = new VanBanAdapter(activity, R.layout.list_item_vanban, MenuPhongAdapter.arrVanBan);
                        MenuPhongAdapter.lv_vanBan.setAdapter(MenuPhongAdapter.vanBanAdapter);
                        Helper.setListViewHeightBasedOnChildren(MenuPhongAdapter.lv_vanBan);
                        MenuPhongAdapter.type = 3;
                        MenuPhongAdapter.total_record = MenuPhongAdapter.arrVanBan.get(0).getTotal_record();
                        MenuPhongAdapter.pageLoad(MenuPhongAdapter.type, MenuPhongAdapter.pageVanBan);
                        MenuPhongAdapter.ll_detail.setVisibility(View.VISIBLE);
                        MenuPhongAdapter.lv_vanBan.setVisibility(View.VISIBLE);
                    } else {
                        MenuPhongAdapter.ll_detail.setVisibility(View.GONE);
                        MenuPhongAdapter.lv_vanBan.setVisibility(View.GONE);
                    }
                    MenuPhongAdapter.gridView.setVisibility(View.GONE);
                    MenuPhongAdapter.HoSoSo = hoSo_ett.getHoSoSo();
                    MenuPhongAdapter.txt_header.setText(MenuPhongAdapter.TenPhong + " > " + "Hộp " + MenuPhongAdapter.TenHop +
                            " > " + "Hồ sơ số " + hoSo_ett.getIDShow() + " > " + "Danh sách văn bản");
//                     design btn_Addnew
                    MenuPhongAdapter.btn_AddNew.setText("Thêm mới Văn bản");
                    MenuPhongAdapter.btn_AddNew.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(activity, AddNewAndUpdateVanBanActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("MaPhong", MenuPhongAdapter.MaPhong);
                            bundle.putString("TenPhong", MenuPhongAdapter.TenPhong);
                            bundle.putString("MaHop", MenuPhongAdapter.MaHop);
                            bundle.putString("TenHop", MenuPhongAdapter.TenHop);
                            bundle.putString("HoSoSo", hoSo_ett.getHoSoSo());
                            intent.putExtra("Data", bundle);
                            activity.startActivity(intent);
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });

        ll_button.setVisibility(View.VISIBLE);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, AddNewAndUpdateHoSoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("MaPhong", MenuPhongAdapter.MaPhong);
                bundle.putString("TenPhong", MenuPhongAdapter.TenPhong);
                bundle.putString("MaHop", MenuPhongAdapter.MaHop);
                bundle.putString("TenHop", MenuPhongAdapter.TenHop);
                bundle.putString("HoSoSo", hoSo_ett.getHoSoSo());
                bundle.putSerializable("HoSo_ett", hoSo_ett);
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
                        String HoSoSo = hoSo_ett.getHoSoSo();
                        try {
                            HoSo_ett hoSoEtt = new DeleteHoSoAsyncTask(activity, HoSoSo).execute().get();
                            arrData.remove(position);
                            MenuPhongAdapter.gridHoSoAdapter = new GridHoSoAdapter(activity, R.layout.grid_item, arrData);
                            MenuPhongAdapter.gridView.setAdapter(MenuPhongAdapter.gridHoSoAdapter);
                            Helper.setGridViewHeightBasedOnChildren(MenuPhongAdapter.gridView, 2);
                            MenuPhongAdapter.total_record = arrData.get(0).getTotal_record();
                            MenuPhongAdapter.pageLoad(MenuPhongAdapter.type, MenuPhongAdapter.pageHoso);
                            Toast.makeText(getContext(), hoSoEtt.getErrDesc(), Toast.LENGTH_LONG).show();
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
                Intent intent = new Intent(activity, HoSoDetailActivity2.class);
                intent.putExtra("HoSo_ett", hoSo_ett);
                activity.startActivity(intent);
            }
        });
        return convertView;
    }
}
