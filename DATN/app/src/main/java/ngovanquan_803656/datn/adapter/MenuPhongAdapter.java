package ngovanquan_803656.datn.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ngovanquan_803656.datn.R;
import ngovanquan_803656.datn.activity.AddNewAndUpdateHoSoActivity;
import ngovanquan_803656.datn.activity.AddNewAndUpdateVanBanActivity;
import ngovanquan_803656.datn.asynctask.SearchHoSo;
import ngovanquan_803656.datn.asynctask.SearchVanBan;
import ngovanquan_803656.datn.asynctask.qllt.GetNumBerHopHoSoAsyncTask;
import ngovanquan_803656.datn.asynctask.qllt.GetNumberHoSoAsyncTask;
import ngovanquan_803656.datn.asynctask.qllt.GetNumberVanBanAsyncTask;
import ngovanquan_803656.datn.asynctask.qllt.SearchHoSoAsyncTask;
import ngovanquan_803656.datn.asynctask.qllt.SearchHoSoByIDAndHopHoSoID;
import ngovanquan_803656.datn.asynctask.qllt.SearchHopHoSoAsyncTask;
import ngovanquan_803656.datn.asynctask.qllt.SearchPhongAsyncTask;
import ngovanquan_803656.datn.asynctask.qllt.SearchVanBanAsyncTask;
import ngovanquan_803656.datn.asynctask.qllt.SearchVanBanByHSSoAndSKHVB;
import ngovanquan_803656.datn.fragment.qldm.HopHoSoFragment;
import ngovanquan_803656.datn.model.HoSo_ett;
import ngovanquan_803656.datn.model.HopHoSo_ett;
import ngovanquan_803656.datn.model.Phong_ett;
import ngovanquan_803656.datn.model.VanBan_ett;
import ngovanquan_803656.datn.utils.Constants;
import ngovanquan_803656.datn.utils.Helper;

/**
 * Created by NGOQUAN on 5/27/2016.
 */
public class MenuPhongAdapter extends ArrayAdapter<Phong_ett> {
    private static Activity activity;
    private int layout;
    private static ArrayList<Phong_ett> arrP = new ArrayList<>();
    private static ArrayList<HopHoSo_ett> listHeader = new ArrayList<>();
    private static HashMap<HopHoSo_ett, ArrayList<HoSo_ett>> listChild = new HashMap<>();
    private static MenuHopHoSoAndHoSoAdapter menuHopHoSoAndHoSoAdapter;


//    ArrayList<Phong_ett> arrP = new ArrayList<>();
    private static MenuPhongAdapter menuPhongAdapter;

    public static String MaPhong, TenPhong, MaHop, TenHop, HoSoSo;
    public static int pageHopHoSo = 1;
    public static int pageHoso = 1;
    public static int pageVanBan = 1;
    public static int type;
    public static int total_record;
    private static int pageTotal;
    private static String search_type, search_value;

    private static ArrayList<HopHoSo_ett> arrHopHoSo = new ArrayList<>();
    private static GridHopSoSoAdapter gridHopSoSoAdapter;
    public static ArrayList<HoSo_ett> arrHoSo = new ArrayList<>();
    public static GridHoSoAdapter gridHoSoAdapter;
    public static ArrayList<VanBan_ett> arrVanBan = new ArrayList<>();
    public static VanBanAdapter vanBanAdapter;

    ArrayAdapter<String> adapter;

    private static Button btn_first, btn_back, btn_jump_back, btn_1, btn_2, btn_3, btn_4, btn_5, btn_jump_next, btn_next, btn_end, btn_search;
    public static Button btn_AddNew;
    public static GridView gridView;
    private static LinearLayout ll_button;
    public static LinearLayout  ll_detail;
    public static ListView lv_vanBan, lv_qllt;
    public static TextView txt_header;
    EditText txt_searchValue;
    Spinner spn_searchType;
    public MenuPhongAdapter(Activity activity, int layout, ArrayList<Phong_ett> arrP) {
        super(activity, layout, arrP);
        this.activity = activity;
        this.layout = layout;
        this.arrP = arrP;
        addControls();
        adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, activity.getResources().getStringArray(R.array.search_hoso_vanban));
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spn_searchType.setAdapter(adapter);

        addEvents();
    }

    private void addControls() {
        btn_first = (Button) activity.findViewById(R.id.btn_first);
        btn_back = (Button) activity.findViewById(R.id.btn_back);
        btn_jump_back = (Button) activity.findViewById(R.id.btn_jump_back);
        btn_1 = (Button) activity.findViewById(R.id.btn_1);
        btn_2 = (Button) activity.findViewById(R.id.btn_2);
        btn_3 = (Button) activity.findViewById(R.id.btn_3);
        btn_4 = (Button) activity.findViewById(R.id.btn_4);
        btn_5 = (Button) activity.findViewById(R.id.btn_5);
        btn_jump_next = (Button) activity.findViewById(R.id.btn_jump_next);
        btn_next = (Button) activity.findViewById(R.id.btn_next);
        btn_end = (Button) activity.findViewById(R.id.btn_end);
        btn_AddNew = (Button) activity.findViewById(R.id.btn_AddNew);
        gridView = (GridView) activity.findViewById(R.id.grid_view);
        ll_button = (LinearLayout) activity.findViewById(R.id.ll_button);
        txt_header = (TextView) activity.findViewById(R.id.txt_header);
        ll_detail = (LinearLayout) activity.findViewById(R.id.ll_detail);
        lv_vanBan = (ListView) activity.findViewById(R.id.lv_vanBan);
        lv_qllt = (ListView) activity.findViewById(R.id.lv_qllt);
        btn_search = (Button) activity.findViewById(R.id.btn_Search);
        txt_searchValue = (EditText) activity.findViewById(R.id.txt_SearchValue);
        spn_searchType = (Spinner) activity.findViewById(R.id.spn_SearchType);
    }

    private void addEvents() {
        spn_searchType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        search_type = "Name";
                        break;
                    case 1:
                        search_type = "ID";
                        break;
                    case 2:
                        search_type = "SoKyHieuVanBan";
                        break;
                    case 3:
                        search_type = "TieuDe";
                        break;

                    case 4:
                        search_type = "TrichYeuND";
                        break;
                }
//                Toast.makeText(activity, search_type, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_value = txt_searchValue.getText().toString();
                try {
                    switch (search_type) {
                        case "Name":
                            if (MaPhong != null)
                                arrHopHoSo = new SearchHopHoSoAsyncTask(activity, 2, search_value, MaPhong, "", "", pageHopHoSo, Constants.NUM_ROW_PER_PAGE).execute().get();
                            else
                                arrHopHoSo = new SearchHopHoSoAsyncTask(activity, Constants.FUNCTION_SEARCH, "", "", search_type, search_value, pageHopHoSo, Constants.NUM_ROW_PER_PAGE).execute().get();
                            if (!arrHopHoSo.isEmpty()) {
                                type = 4;
                                gridHopSoSoAdapter = new GridHopSoSoAdapter(activity, R.layout.grid_item, arrHopHoSo);
                                gridView.setAdapter(gridHopSoSoAdapter);
                                total_record = arrHopHoSo.get(0).getTotal_record();
                                Helper.setGridViewHeightBasedOnChildren(gridView, 2);
                                pageLoad(type, pageHopHoSo);
                                gridView.setVisibility(View.VISIBLE);
                                ll_button.setVisibility(View.VISIBLE);
                                ll_detail.setVisibility(View.GONE);
                                lv_vanBan.setVelocityScale(View.GONE);
                            }
                            break;
                        case "ID":
                            if (MaHop != null)
                                arrHoSo = new SearchHoSoByIDAndHopHoSoID(activity, search_value, MaHop, pageHoso, Constants.NUM_ROW_PER_PAGE).execute().get();
                            else
                                arrHoSo = new SearchHoSoAsyncTask(activity, search_type, search_value, pageHoso, Constants.NUM_ROW_PER_PAGE).execute().get();
                            if (!arrHoSo.isEmpty()) {
                                type = 2;
                                gridHoSoAdapter = new GridHoSoAdapter(activity, R.layout.grid_item, arrHoSo);
                                gridView.setAdapter(gridHoSoAdapter);
                                Helper.setGridViewHeightBasedOnChildren(gridView, 2);
                                total_record = arrHoSo.get(0).getTotal_record();
                                pageLoad(type, pageHoso);
                                ll_button.setVisibility(View.VISIBLE);
                                gridView.setVisibility(View.VISIBLE);
                            } else {
                                ll_button.setVisibility(View.GONE);
                                gridView.setVisibility(View.GONE);
                            }
                            ll_detail.setVisibility(View.GONE);
                            lv_vanBan.setVisibility(View.GONE);
                            break;
                        case "SoKyHieuVanBan":
                            if (HoSoSo != null)
                                arrVanBan = new SearchVanBanByHSSoAndSKHVB(activity, HoSoSo, search_value, pageVanBan).execute().get();
                            else
                                arrVanBan = new SearchVanBanAsyncTask(activity, search_type, search_value, pageVanBan).execute().get();

                            if (!arrVanBan.isEmpty()) {
                                vanBanAdapter = new VanBanAdapter(activity, R.layout.list_item_vanban, arrVanBan);
                                lv_vanBan.setAdapter(vanBanAdapter);
                                Helper.setListViewHeightBasedOnChildren(lv_vanBan);
                                type = 3;
                                total_record = arrVanBan.get(0).getTotal_record();
                                pageLoad(type, pageVanBan);
                                ll_detail.setVisibility(View.VISIBLE);
                                lv_vanBan.setVisibility(View.VISIBLE);
                            } else {
                                ll_detail.setVisibility(View.GONE);
                                lv_vanBan.setVisibility(View.GONE);
                            }
                            gridView.setVisibility(View.GONE);
                            break;
                        case "TieuDe":
                            if (MaHop != null)
                                arrHoSo = new SearchHoSoAsyncTask(activity, "HoSoByHopHoSo", MaHop, pageHoso, Constants.NUM_ROW_PER_PAGE).execute().get();
                            else
                                arrHoSo = new SearchHoSo(activity, search_type, search_value, pageHoso, Constants.NUM_ROW_PER_PAGE).execute().get();
                            if (!arrHoSo.isEmpty()) {
                                type = 2;
                                gridHoSoAdapter = new GridHoSoAdapter(activity, R.layout.grid_item, arrHoSo);
                                gridView.setAdapter(gridHoSoAdapter);
                                Helper.setGridViewHeightBasedOnChildren(gridView, 2);
                                total_record = arrHoSo.get(0).getTotal_record();
                                pageLoad(type, pageHoso);
                                ll_button.setVisibility(View.VISIBLE);
                                gridView.setVisibility(View.VISIBLE);
                            } else {
                                ll_button.setVisibility(View.GONE);
                                gridView.setVisibility(View.GONE);
                            }
                            ll_detail.setVisibility(View.GONE);
                            lv_vanBan.setVisibility(View.GONE);
                            break;
                        case "TrichYeuND":
                            if (HoSoSo != null)
                                arrVanBan = new SearchVanBanAsyncTask(activity, "VbbyHs", HoSoSo, pageVanBan).execute().get();
                            else
                                arrVanBan = new SearchVanBan(activity, search_type, search_value, pageVanBan).execute().get();

                            if (!arrVanBan.isEmpty()) {
                                vanBanAdapter = new VanBanAdapter(activity, R.layout.list_item_vanban, arrVanBan);
                                lv_vanBan.setAdapter(vanBanAdapter);
                                Helper.setListViewHeightBasedOnChildren(lv_vanBan);
                                type = 3;
                                total_record = arrVanBan.get(0).getTotal_record();
                                pageLoad(type, pageVanBan);
                                ll_detail.setVisibility(View.VISIBLE);
                                lv_vanBan.setVisibility(View.VISIBLE);
                            } else {
                                ll_detail.setVisibility(View.GONE);
                                lv_vanBan.setVisibility(View.GONE);
                            }
                            gridView.setVisibility(View.GONE);
                            break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

            }
        });
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Phong_ett phong_ett = arrP.get(position);
        final ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(activity).inflate(R.layout.list_item_phong, parent, false);
            viewHolder.img_phong = (ImageButton) convertView.findViewById(R.id.img_phong);
            viewHolder.txt_phong = (TextView) convertView.findViewById(R.id.txt_phong);
            viewHolder.exp_listView = (ExpandableListView) convertView.findViewById(R.id.exp_listview);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.txt_phong.setText(phong_ett.getTenPhong() + "(" + phong_ett.getSl_phong() + ")");
        viewHolder.img_phong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaPhong = phong_ett.getMaPhong() + "";
                TenPhong = phong_ett.getTenPhong();
                type = 1;
                listHeader = initializeListHeader(MaPhong, pageHopHoSo);
                listChild = initializeListChild(MaHop, pageHoso);
                menuHopHoSoAndHoSoAdapter = new MenuHopHoSoAndHoSoAdapter(activity, listHeader, listChild);
                viewHolder.exp_listView.setAdapter(menuHopHoSoAndHoSoAdapter);
                Helper.setExpandableListViewParent(viewHolder.exp_listView);
                if (viewHolder.exp_listView.getVisibility() == View.GONE)
                    viewHolder.exp_listView.setVisibility(View.VISIBLE);
                else
                    viewHolder.exp_listView.setVisibility(View.GONE);

                viewHolder.exp_listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                    @Override
                    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                        Helper.setExpandableListViewHeight(viewHolder.exp_listView, groupPosition);
                        HopHoSo_ett hopHoSo_ett = (HopHoSo_ett) menuHopHoSoAndHoSoAdapter.getGroup(groupPosition);
                        MaHop = String.valueOf(hopHoSo_ett.getMaHopHS());
                        TenHop = hopHoSo_ett.getTenHop();
                        type = 2;
//                        Log.e("ResultClick", MaHop + " - " + TenHop + " - " + type);
                        try {
                            arrHoSo = new SearchHoSoAsyncTask(activity, "HoSoByHopHoSo", MaHop, pageHoso, Constants.NUM_ROW_PER_PAGE).execute().get();
                            if (!arrHoSo.isEmpty()) {
                                gridHoSoAdapter = new GridHoSoAdapter(activity, R.layout.grid_item, arrHoSo);
                                gridView.setAdapter(gridHoSoAdapter);
                                Helper.setGridViewHeightBasedOnChildren(gridView, 2);
                                total_record = hopHoSo_ett.getSl_hhs();
                                pageLoad(type, pageHoso);
                                ll_button.setVisibility(View.VISIBLE);
                                gridView.setVisibility(View.VISIBLE);
                            } else {
                                ll_button.setVisibility(View.GONE);
                                gridView.setVisibility(View.GONE);
                            }
                            txt_header.setText(TenPhong + " > " + "Hộp " + TenHop + " > " + "Danh sách Hồ sơ");
                            btn_AddNew.setText("Thêm mới Hồ sơ");
                            btn_AddNew.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(activity, AddNewAndUpdateHoSoActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("MaPhong", MaPhong);
                                    bundle.putString("TenPhong", TenPhong);
                                    bundle.putString("MaHop", MaHop);
                                    bundle.putString("TenHop", TenHop);
                                    intent.putExtra("Data", bundle);
                                    activity.startActivity(intent);
                                }
                            });

                            btn_AddNew.setVisibility(View.VISIBLE);
                            ll_detail.setVisibility(View.GONE);
                            lv_vanBan.setVisibility(View.GONE);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        return false;
                    }
                });

                viewHolder.exp_listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                        final HoSo_ett hoSo_ett =(HoSo_ett) menuHopHoSoAndHoSoAdapter.getChild(groupPosition, childPosition);
                        HoSoSo = hoSo_ett.getIDShow();
                        try {

                            arrVanBan = new SearchVanBanAsyncTask(activity, "VbbyHs", HoSoSo, pageVanBan).execute().get();

                            if (!arrVanBan.isEmpty()) {
                                vanBanAdapter = new VanBanAdapter(activity, R.layout.list_item_vanban, arrVanBan);
                                lv_vanBan.setAdapter(vanBanAdapter);
                                Helper.setListViewHeightBasedOnChildren(lv_vanBan);
                                type = 3;
                                total_record = arrVanBan.get(0).getTotal_record();
                                pageLoad(type, pageVanBan);
                                ll_detail.setVisibility(View.VISIBLE);
                                lv_vanBan.setVisibility(View.VISIBLE);
                            } else {
                                ll_detail.setVisibility(View.GONE);
                                lv_vanBan.setVisibility(View.GONE);
                            }
                            gridView.setVisibility(View.GONE);
                            txt_header.setText(TenPhong + " > " + "Hộp " + TenHop +
                                    " > " + "Hồ sơ số " + hoSo_ett.getHoSoSo() + " > " + "Danh sách văn bản");
//                     design btn_Addnew
                            btn_AddNew.setText("Thêm mới Văn bản");
                            btn_AddNew.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(activity, AddNewAndUpdateVanBanActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("MaPhong", MaPhong);
                                    bundle.putString("TenPhong", TenPhong);
                                    bundle.putString("MaHop", MaHop);
                                    bundle.putString("TenHop", TenHop);
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
                        return false;
                    }
                });
                gridHopSoSoAdapter = new GridHopSoSoAdapter(activity, R.layout.grid_item, listHeader);
                gridView.setAdapter(gridHopSoSoAdapter);
                total_record = listHeader.get(0).getTotal_record();
                pageLoad(type, pageHopHoSo);
                Helper.setGridViewHeightBasedOnChildren(gridView, 2);
                gridView.setVisibility(View.VISIBLE);
                ll_button.setVisibility(View.VISIBLE);
                ll_detail.setVisibility(View.GONE);
                lv_vanBan.setVelocityScale(View.GONE);
                txt_header.setText(TenPhong + " > " + "Danh sách hộp số");
                btn_AddNew.setText("Thêm mới Hộp số");
                btn_AddNew.setVisibility(View.VISIBLE);
                btn_AddNew.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Fragment fragment = new HopHoSoFragment();
                        FragmentManager fragmentManager = ((FragmentActivity) activity).getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
                    }
                });
            }
        });



        return convertView;
    }

    public static class ViewHolder {
        private ImageButton img_phong;
        private TextView txt_phong;
        private ExpandableListView exp_listView;
    }

    private static ArrayList<Phong_ett> initializePhong() {
        ArrayList<Phong_ett> arrP = new ArrayList<>();
        ArrayList<Phong_ett> arrPhong;
        try {
            arrPhong = new SearchPhongAsyncTask(activity, "ID", "", 1).execute().get();
            for (Phong_ett phong_ett : arrPhong) {
                String MaPhong = String.valueOf(phong_ett.getMaPhong());
                int sl_hophs = new GetNumBerHopHoSoAsyncTask(activity, "HopHoSoByPhong", MaPhong, 1).execute().get();
                arrP.add(new Phong_ett(phong_ett.getMaCQLT(), phong_ett.getMaPhong(), phong_ett.getTenPhong(), phong_ett.getLichSuHinhThanh(),
                        phong_ett.getThoiGianTaiLieu(), phong_ett.getTongSoTaiLieu(), phong_ett.getSoTaiLieuDaChinhLy(), phong_ett.getSoTaiLieuChuaChinhLy(),
                        phong_ett.getCacNhomTaiLieu(), phong_ett.getMaNN(), phong_ett.getThoiGianNhapTaiLieu(), phong_ett.getCongCuTraCuu(), phong_ett.getLapBanSaoBaoHiem(),
                        phong_ett.getGhiChu(), sl_hophs));
            }
            return arrP;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static ArrayList<HopHoSo_ett> initializeListHeader(String MaPhong, int pageHopHoSo) {
        ArrayList<HopHoSo_ett> listHeader = new ArrayList<>();
        ArrayList<HopHoSo_ett> arrHopHoSo;
        try {
            arrHopHoSo = new SearchHopHoSoAsyncTask(activity, Constants.FUNCTION_SEARCH, "", "", "HopHoSoByPhong", MaPhong, pageHopHoSo, Constants.NUM_ROW_PER_PAGE).execute().get();
            for (HopHoSo_ett hopHoSo_ett : arrHopHoSo) {
                String MaHopHS = String.valueOf(hopHoSo_ett.getMaHopHS());
                int sl_hoSo = new GetNumberHoSoAsyncTask(activity, "HoSoByHopHoSo", MaHopHS, 1).execute().get();
                listHeader.add(new HopHoSo_ett(hopHoSo_ett.getMaHopHS(), hopHoSo_ett.getTenHop(), hopHoSo_ett.getGhiChu(),
                        hopHoSo_ett.isActive(), hopHoSo_ett.getMaPhong(), hopHoSo_ett.getTotal_record(), sl_hoSo));
            }
            return listHeader;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static HashMap<HopHoSo_ett, ArrayList<HoSo_ett>> initializeListChild(String MaHop, int pageHoso) {
        HashMap<HopHoSo_ett, ArrayList<HoSo_ett>> listChild = new HashMap<>();

        for (HopHoSo_ett hopHoSo_ett : listHeader) {
            String MaHopHs = String.valueOf(hopHoSo_ett.getMaHopHS());
            ArrayList<HoSo_ett> arrHoSo;
            ArrayList<HoSo_ett> arrHoSo1 = new ArrayList<>();
            try {
                if (MaHopHs.equals(MaHop))
                    arrHoSo = new SearchHoSoAsyncTask(activity, "HoSoByHopHoSo", MaHopHs, pageHoso, Constants.NUM_ROW_PER_PAGE).execute().get();
                else
                    arrHoSo = new SearchHoSoAsyncTask(activity, "HoSoByHopHoSo", MaHopHs, 1, Constants.NUM_ROW_PER_PAGE).execute().get();
                for (HoSo_ett hoSo_ett : arrHoSo) {
                    String HoSoSo = hoSo_ett.getHoSoSo();
                    int sl_vanBan = new GetNumberVanBanAsyncTask(activity, "VbbyHs", HoSoSo, 1).execute().get();
                    HoSo_ett hoSoEtt = new HoSo_ett(hoSo_ett.getMaPhong(), hoSo_ett.getMucLucSo(), hoSo_ett.getMaHopHS(), hoSo_ett.getHoSoSo(), hoSo_ett.getKHTT(),
                            hoSo_ett.getTieuDeHs(), hoSo_ett.getChuGiai(), hoSo_ett.getThoiGianBatDau(), hoSo_ett.getThoiGianKetThuc(), hoSo_ett.getMaNN(), hoSo_ett.getButTich(),
                            hoSo_ett.getSoLuongTo(), hoSo_ett.getMaTHBQ(), hoSo_ett.getMaCDSD(), hoSo_ett.getMaTTVL(), sl_vanBan);
                    arrHoSo1.add(hoSoEtt);
                }
                listChild.put(hopHoSo_ett, arrHoSo1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return listChild;
    }

    public static void pageLoad(int type, int page) {
//        gone button
        btn_first.setVisibility(View.GONE);
        btn_back.setVisibility(View.GONE);
        btn_jump_back.setVisibility(View.GONE);
        btn_1.setVisibility(View.GONE);
        btn_2.setVisibility(View.GONE);
        btn_3.setVisibility(View.GONE);
        btn_4.setVisibility(View.GONE);
        btn_5.setVisibility(View.GONE);
        btn_jump_next.setVisibility(View.GONE);
        btn_next.setVisibility(View.GONE);
        btn_end.setVisibility(View.GONE);
//        if (arrHopHoSo != null) {
//        calculate page total
        pageTotal = (int) Math.ceil((double) total_record / Constants.NUM_ROW_PER_PAGE);

//        thuat toan bat dau
        if (page < 0)
            page = 1;
        if (pageTotal > 1) {
            if (page > pageTotal)
                page = pageTotal;
//            show btn_first, btn_back, btn_next, btn_end
            if (page > 1) {
                setButtonPagingValue(type, btn_first, "<<", 1);
                btn_first.setVisibility(View.VISIBLE);
                setButtonPagingValue(type, btn_back, "<", page - 1);
                btn_back.setVisibility(View.VISIBLE);
            }
            if ((page + 1) <= pageTotal) {
                setButtonPagingValue(type, btn_next, ">", page + 1);
                btn_next.setVisibility(View.VISIBLE);
                setButtonPagingValue(type, btn_end, ">>", pageTotal);
                btn_end.setVisibility(View.VISIBLE);
            }

            //trường hợp page_curr nằm đúng trong khoảng thì thực hiện xử lý paging
            setButtonPagingValue(type, btn_3, page + "", page);
            btn_3.setVisibility(View.VISIBLE);
            if (pageTotal == 2) {
                setButtonPagingValue(type, btn_3, page + "", page);
                if (page == 1) {
                    setButtonPagingValue(type, btn_4, "2", 2);
                    btn_4.setVisibility(View.VISIBLE);
                } else {
                    setButtonPagingValue(type, btn_2, "1", 1);
                    btn_2.setVisibility(View.VISIBLE);
                }
            } else {
                //set btn_2, btn_1 và btn_jump_back theo từng trường hợp
                if (page < 3) {
                    //nếu page_curr < 3 thì xử lý cho trường hợp  =  2 & 1
                    switch (page) {
                        case 2:
                            setButtonPagingValue(type, btn_2, "1", 1);
                            btn_2.setVisibility(View.VISIBLE);
                            break;
                    }
                } else {
                    setButtonPagingValue(type, btn_2, (page - 1) + "", page - 1);
                    btn_2.setVisibility(View.VISIBLE);
                    setButtonPagingValue(type, btn_1, (page - 2) + "", page - 2);
                    btn_1.setVisibility(View.VISIBLE);
                    if ((page - 2) > Constants.PAGE_JUMP_PAGING) {
                        //page_curr > 3 và page_curr - page_jump > 0
                        setButtonPagingValue(type, btn_jump_back, "...", (page - 2 - Constants.PAGE_JUMP_PAGING));
                        btn_jump_back.setVisibility(View.VISIBLE);
                    }
                }

                //set btn_3, btn_4 và p_jump_next theo từng trường hợp
                if ((page + 3) <= pageTotal) {
                    setButtonPagingValue(type, btn_4, (page + 1) + "", (page + 1));
                    btn_4.setVisibility(View.VISIBLE);
                    setButtonPagingValue(type, btn_5, (page + 2) + "", (page + 2));
                    btn_5.setVisibility(View.VISIBLE);
                    if ((pageTotal - page - 2) > Constants.PAGE_JUMP_PAGING) {
                        setButtonPagingValue(type, btn_jump_next, "...", (page + 2 + Constants.PAGE_JUMP_PAGING));
                        btn_jump_next.setVisibility(View.VISIBLE);
                    }
                } else {
                    //nếu page_curr + 3 > page_total thì xử lý cho trường hợp  =  +2 & +1
                    if ((page + 1) == pageTotal) {
                        setButtonPagingValue(type, btn_4, (page + 1) + "", (page + 1));
                        btn_4.setVisibility(View.VISIBLE);
                    } else {
                        if ((page + 2) == pageTotal) {
                            setButtonPagingValue(type, btn_4, (page + 1) + "", (page + 1));
                            btn_4.setVisibility(View.VISIBLE);
                            setButtonPagingValue(type, btn_5, (page + 2) + "", (page + 2));
                            btn_5.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }

        } else {
            page = pageTotal;
        }
//        }
    }

    private static void setButtonPagingValue(final int type, Button b, String title, final int page) {
        b.setText(title);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, page + "", Toast.LENGTH_SHORT).show();
                switch (type) {
                    case 1:
                        pageHopHoSo = page;
                        listHeader = initializeListHeader(MaPhong, pageHopHoSo);
                        gridHopSoSoAdapter = new GridHopSoSoAdapter(activity, R.layout.grid_item, listHeader);
                        gridView.setAdapter(gridHopSoSoAdapter);
                        Helper.setGridViewHeightBasedOnChildren(gridView, 2);

                        arrP = initializePhong();
                        menuPhongAdapter = new MenuPhongAdapter(activity, R.layout.list_item_phong, arrP);
                        lv_qllt.setAdapter(menuPhongAdapter);
                        Helper.setListViewHeightBasedOnChildren(lv_qllt);
                        pageLoad(type, pageHopHoSo);
                        break;
                    case 2:
                        pageHoso = page;
                        try {
                            if (MaHop != null)
                                arrHoSo = new SearchHoSoAsyncTask(activity, "HoSoByHopHoSo", MaHop, pageHoso, Constants.NUM_ROW_PER_PAGE).execute().get();
                            else
                                arrHoSo = new SearchHoSoAsyncTask(activity, search_type, search_value, pageHoso, Constants.NUM_ROW_PER_PAGE).execute().get();
                            gridHoSoAdapter = new GridHoSoAdapter(activity, R.layout.grid_item, arrHoSo);
                            gridView.setAdapter(gridHoSoAdapter);
                            Helper.setGridViewHeightBasedOnChildren(gridView, 2);

                            arrP = initializePhong();
                            menuPhongAdapter = new MenuPhongAdapter(activity, R.layout.list_item_phong, arrP);
                            lv_qllt.setAdapter(menuPhongAdapter);
                            Helper.setListViewHeightBasedOnChildren(lv_qllt);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        pageLoad(type, pageHoso);
                        break;
                    case 3:
                        pageVanBan = page;
                        try {
                            if (HoSoSo != null)
                                arrVanBan = new SearchVanBanAsyncTask(activity, "VbbyHs", HoSoSo, pageVanBan).execute().get();
                            else
                                arrVanBan = new SearchVanBanAsyncTask(activity, search_type, search_value, pageVanBan).execute().get();
                            vanBanAdapter = new VanBanAdapter(activity, R.layout.list_item_vanban, arrVanBan);
                            lv_vanBan.setAdapter(vanBanAdapter);
                            Helper.setListViewHeightBasedOnChildren(lv_vanBan);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        pageLoad(type, pageVanBan);
                        break;
                    case 4:
                        pageHopHoSo = page;
                        try {
                            if (MaPhong != null)
                                arrHopHoSo = new SearchHopHoSoAsyncTask(activity, 2, search_value, MaPhong, "", "", pageHopHoSo, Constants.NUM_ROW_PER_PAGE).execute().get();
                            else
                                arrHopHoSo = new SearchHopHoSoAsyncTask(activity, Constants.FUNCTION_SEARCH, "", "", search_type, search_value, pageHopHoSo, Constants.NUM_ROW_PER_PAGE).execute().get();
                            gridHopSoSoAdapter = new GridHopSoSoAdapter(activity, R.layout.grid_item, arrHopHoSo);
                            gridView.setAdapter(gridHopSoSoAdapter);
                            Helper.setGridViewHeightBasedOnChildren(gridView, 2);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        break;
                }

            }
        });
    }

}
