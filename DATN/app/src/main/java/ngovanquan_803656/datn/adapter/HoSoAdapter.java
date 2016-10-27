package ngovanquan_803656.datn.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ngovanquan_803656.datn.R;
import ngovanquan_803656.datn.activity.AddNewAndUpdateVanBanActivity;
import ngovanquan_803656.datn.asynctask.qllt.GetNumberVanBanAsyncTask;
import ngovanquan_803656.datn.asynctask.qllt.SearchVanBanAsyncTask;
import ngovanquan_803656.datn.model.HoSo_ett;
import ngovanquan_803656.datn.utils.Helper;

/**
 * Created by ngoquan on 5/9/2016.
 */
public class HoSoAdapter extends ArrayAdapter<HoSo_ett> {
    private Activity activity;
    private int layout;
    private ArrayList<HoSo_ett> arrData = new ArrayList<>();
    public HoSoAdapter(Activity activity, int layout, ArrayList<HoSo_ett> arrData) {
        super(activity, layout, arrData);
        this.activity = activity;
        this.layout = layout;
        this.arrData = arrData;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final HoSo_ett hoSo_ett = arrData.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(activity).inflate(R.layout.list_item, parent, false);
        }
        ImageView img_item = (ImageView) convertView.findViewById(R.id.img_item);
        TextView txt_hoso = (TextView) convertView.findViewById(R.id.txt_hoso);
        int sl_vanban = 0;
        try {
            sl_vanban = new GetNumberVanBanAsyncTask(activity, "VbbyHs", hoSo_ett.getHoSoSo(), 1).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        txt_hoso.setText("HS Số: " + hoSo_ett.getHoSoSo() + "(" + sl_vanban + ")");
        img_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ExpandableListAdapter.arrVanBan = new SearchVanBanAsyncTask(activity, "VbbyHs", hoSo_ett.getHoSoSo(), 1).execute().get();
                    ExpandableListAdapter.vanBanAdapter = new VanBanAdapter(activity, R.layout.list_item_vanban, ExpandableListAdapter.arrVanBan);
                    ExpandableListAdapter.lv_vanBan.setAdapter(ExpandableListAdapter.vanBanAdapter);
                    Helper.setListViewHeightBasedOnChildren(ExpandableListAdapter.lv_vanBan);
                    ExpandableListAdapter.type = 3;
                    if (!ExpandableListAdapter.arrVanBan.isEmpty()) {
                        ExpandableListAdapter.total_record = ExpandableListAdapter.arrVanBan.get(0).getTotal_record();
                        ExpandableListAdapter.pageLoad(ExpandableListAdapter.type, ExpandableListAdapter.pageVanBan);
                        ExpandableListAdapter.ll_detail.setVisibility(View.VISIBLE);
                        ExpandableListAdapter.lv_vanBan.setVisibility(View.VISIBLE);
                    }
                    ExpandableListAdapter.gridView.setVisibility(View.GONE);
                    ExpandableListAdapter.HoSoSo = hoSo_ett.getHoSoSo();
                    ExpandableListAdapter.txt_header.setText(ExpandableListAdapter.TenPhong + " > " + "Hộp " + ExpandableListAdapter.TenHop +
                            " > " + "Hồ sơ số " + hoSo_ett.getHoSoSo() + " > " + "Danh sách văn bản");
//                     design btn_Addnew
                    ExpandableListAdapter.btn_AddNew.setText("Thêm mới Văn bản");
                    ExpandableListAdapter.btn_AddNew.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(activity, AddNewAndUpdateVanBanActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("MaPhong", ExpandableListAdapter.MaPhong);
                            bundle.putString("TenPhong", ExpandableListAdapter.TenPhong);
                            bundle.putString("MaHop", ExpandableListAdapter.MaHop);
                            bundle.putString("TenHop", ExpandableListAdapter.TenHop);
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

        return convertView;
    }
}
