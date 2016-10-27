package ngovanquan_803656.datn.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ngovanquan_803656.datn.R;
import ngovanquan_803656.datn.activity.AddNewAndUpdateHoSoActivity;
import ngovanquan_803656.datn.asynctask.qllt.SearchHoSoAsyncTask;
import ngovanquan_803656.datn.model.HoSo_ett;
import ngovanquan_803656.datn.model.HopHoSo_ett;
import ngovanquan_803656.datn.utils.Constants;
import ngovanquan_803656.datn.utils.Helper;

/**
 * Created by ngoquan on 5/14/2016.
 */
public class GridHopSoSoAdapter extends ArrayAdapter<HopHoSo_ett> {
    private Activity activity;
    private int layout;
    private ArrayList<HopHoSo_ett> arrData = new ArrayList<>();
    public GridHopSoSoAdapter(Activity activity, int layout, ArrayList<HopHoSo_ett> arrData) {
        super(activity, layout, arrData);
        this.activity = activity;
        this.layout = layout;
        this.arrData = arrData;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final HopHoSo_ett hopHoSo_ett = arrData.get(position);
        if (convertView == null)
            convertView = LayoutInflater.from(activity).inflate(R.layout.grid_item, parent, false);
        ImageView img_item = (ImageView) convertView.findViewById(R.id.img_item);
        TextView txt_item = (TextView) convertView.findViewById(R.id.txt_item);

        txt_item.setText("Hộp : " + hopHoSo_ett.getTenHop());
        img_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    MenuPhongAdapter.arrHoSo = new SearchHoSoAsyncTask(activity, "HoSoByHopHoSo", String.valueOf(hopHoSo_ett.getMaHopHS()), 1, Constants.NUM_ROW_PER_PAGE).execute().get();
                    if (!MenuPhongAdapter.arrHoSo.isEmpty()) {
                        MenuPhongAdapter.gridHoSoAdapter = new GridHoSoAdapter(activity, R.layout.grid_item, MenuPhongAdapter.arrHoSo);
                        MenuPhongAdapter.gridView.setAdapter(MenuPhongAdapter.gridHoSoAdapter);
                        Helper.setGridViewHeightBasedOnChildren(MenuPhongAdapter.gridView, 2);
                        MenuPhongAdapter.type = 2;
                        MenuPhongAdapter.total_record = MenuPhongAdapter.arrHoSo.get(0).getTotal_record();
                        MenuPhongAdapter.pageLoad(MenuPhongAdapter.type, MenuPhongAdapter.pageHoso);
                        MenuPhongAdapter.gridView.setVisibility(View.VISIBLE);
                    } else {
                        MenuPhongAdapter.gridView.setVisibility(View.GONE);
                    }
                    MenuPhongAdapter.MaHop = String.valueOf(hopHoSo_ett.getMaHopHS());
                    MenuPhongAdapter.TenHop = hopHoSo_ett.getTenHop();
                    MenuPhongAdapter.txt_header.setText(MenuPhongAdapter.TenPhong + " > " + "Hộp " + hopHoSo_ett.getTenHop() + " > " + "Danh sách Hồ sơ");
                    MenuPhongAdapter.btn_AddNew.setText("Thêm mới Hồ sơ");
                    MenuPhongAdapter.btn_AddNew.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(activity, AddNewAndUpdateHoSoActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("MaPhong", MenuPhongAdapter.MaPhong);
                            bundle.putString("TenPhong", MenuPhongAdapter.TenPhong);
                            bundle.putString("MaHop", String.valueOf(hopHoSo_ett.getMaHopHS()));
                            bundle.putString("TenHop", hopHoSo_ett.getTenHop());
                            intent.putExtra("Data", bundle);
                            activity.startActivity(intent);
                        }
                    });
                    MenuPhongAdapter.ll_detail.setVisibility(View.GONE);
                    MenuPhongAdapter.lv_vanBan.setVisibility(View.GONE);

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
