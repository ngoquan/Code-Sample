package ngovanquan_803656.datn.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ngovanquan_803656.datn.R;
import ngovanquan_803656.datn.activity.AddNewAndUpdateHoSoActivity;
import ngovanquan_803656.datn.activity.menu.MenuActivity;
import ngovanquan_803656.datn.asynctask.qllt.GetNumBerHopHoSoAsyncTask;
import ngovanquan_803656.datn.asynctask.qllt.SearchHoSoAsyncTask;
import ngovanquan_803656.datn.asynctask.qllt.SearchHopHoSoAsyncTask;
import ngovanquan_803656.datn.asynctask.qllt.SearchPhongAsyncTask;
import ngovanquan_803656.datn.asynctask.qllt.SearchVanBanAsyncTask;
import ngovanquan_803656.datn.fragment.qldm.HopHoSoFragment;
import ngovanquan_803656.datn.fragment.qllt.HoSoAndVanBanFragment;
import ngovanquan_803656.datn.model.HoSo_ett;
import ngovanquan_803656.datn.model.HopHoSo_ett;
import ngovanquan_803656.datn.model.Phong_ett;
import ngovanquan_803656.datn.model.VanBan_ett;
import ngovanquan_803656.datn.utils.Constants;
import ngovanquan_803656.datn.utils.Helper;

/**
 * Created by ngoquan on 5/10/2016.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private static Activity context;
    private static Button btn_first, btn_back, btn_jump_back, btn_1, btn_2, btn_3, btn_4, btn_5, btn_jump_next, btn_next, btn_end;
    public static Button btn_AddNew;
    public static GridView gridView;
    private static LinearLayout ll_button;
    public static LinearLayout  ll_detail;
    public static ListView lv_vanBan;
    public static TextView txt_header;
    private static List<Phong_ett> listHeader;
    private static HashMap<Phong_ett, List<HopHoSo_ett>> listChild;
    private boolean isExpand;
    private static ArrayList<HopHoSo_ett> arrHopHoSo = new ArrayList<>();
    private static GridHopSoSoAdapter gridHopSoSoAdapter;
    public static ArrayList<HoSo_ett> arrHoSo = new ArrayList<>();
    public static GridHoSoAdapter gridHoSoAdapter;
    public static ArrayList<VanBan_ett> arrVanBan = new ArrayList<>();
    public static VanBanAdapter vanBanAdapter;
    private static ExpandableListView exp_listview;
    private static ExpandableListAdapter adapter;
    public static int total_record;
    private static int pageTotal;
    public static int pageHopHoSo = 1;
    public static int pageHoSo = 1;
    public static int pageVanBan = 1;
    public static String MaPhong, MaHop, TenPhong, TenHop, HoSoSo;
    public static int type;
    public ExpandableListAdapter(Activity context, List<Phong_ett> listHeader, HashMap<Phong_ett, List<HopHoSo_ett>> listChild) {
        this.context = context;
        this.listChild = listChild;
        this.listHeader = listHeader;
        
//        create button;
       addControls();
    }

    private void addControls() {
        btn_first = (Button) context.findViewById(R.id.btn_first);
        btn_back = (Button) context.findViewById(R.id.btn_back);
        btn_jump_back = (Button) context.findViewById(R.id.btn_jump_back);
        btn_1 = (Button) context.findViewById(R.id.btn_1);
        btn_2 = (Button) context.findViewById(R.id.btn_2);
        btn_3 = (Button) context.findViewById(R.id.btn_3);
        btn_4 = (Button) context.findViewById(R.id.btn_4);
        btn_5 = (Button) context.findViewById(R.id.btn_5);
        btn_jump_next = (Button) context.findViewById(R.id.btn_jump_next);
        btn_next = (Button) context.findViewById(R.id.btn_next);
        btn_end = (Button) context.findViewById(R.id.btn_end);
        btn_AddNew = (Button) context.findViewById(R.id.btn_AddNew);
        gridView = (GridView) context.findViewById(R.id.grid_view);
        exp_listview = (ExpandableListView) context.findViewById(R.id.exp_listview);
        ll_button = (LinearLayout) context.findViewById(R.id.ll_button);
//        ll_header = (RelativeLayout) context.findViewById(R.id.ll_header);
        txt_header = (TextView) context.findViewById(R.id.txt_header);
        ll_detail = (LinearLayout) context.findViewById(R.id.ll_detail);
        lv_vanBan = (ListView) context.findViewById(R.id.lv_vanBan);
    }

    @Override
    public int getGroupCount() {
        return listHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listChild.get(listHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listChild.get(listHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, final ViewGroup parent) {
        final Phong_ett phong_ett = (Phong_ett) getGroup(groupPosition);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_phong, parent, false);
        }
        ImageView img_phong = (ImageView) convertView.findViewById(R.id.img_phong);
        TextView txt_phong = (TextView) convertView.findViewById(R.id.txt_phong);
        txt_phong.setText(phong_ett.getTenPhong() + "(" + phong_ett.getSl_phong() + ")");
        img_phong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                HoSoAndVanBanFragment.MaP = String.valueOf(phong_ett.getMaPhong());
//                isExpanded = true;
                MaPhong = String.valueOf(phong_ett.getMaPhong());
                TenPhong = phong_ett.getTenPhong();
                type = 1;
                try {
                    arrHopHoSo = new SearchHopHoSoAsyncTask(context, Constants.FUNCTION_SEARCH, "", "", "HopHoSoByPhong", MaPhong, pageHopHoSo, Constants.NUM_ROW_PER_PAGE).execute().get();
                    gridHopSoSoAdapter = new GridHopSoSoAdapter(context, R.layout.grid_item, arrHopHoSo);
                    gridView.setAdapter(gridHopSoSoAdapter);
                    total_record = arrHopHoSo.get(0).getTotal_record();
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
                            FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                            fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
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
                Toast.makeText(context, page + "", Toast.LENGTH_SHORT).show();
                switch (type) {
                    case 1:
                        pageHopHoSo = page;
                        try {
                            arrHopHoSo = new SearchHopHoSoAsyncTask(context, Constants.FUNCTION_SEARCH, "", "", "HopHoSoByPhong", MaPhong, pageHopHoSo, Constants.NUM_ROW_PER_PAGE).execute().get();
                            gridHopSoSoAdapter = new GridHopSoSoAdapter(context, R.layout.grid_item, arrHopHoSo);
                            gridView.setAdapter(gridHopSoSoAdapter);
                            Helper.setGridViewHeightBasedOnChildren(gridView, 2);

                            listHeader = createListHeader();
                            listChild = createListChild(MaPhong, pageHopHoSo, "", 1);
                            adapter = new ExpandableListAdapter(context, listHeader, listChild);
                            exp_listview.setAdapter(adapter);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        pageLoad(type, pageHopHoSo);
                        break;
                    case 2:
                        pageHoSo = page;
                        try {
                            arrHoSo = new SearchHoSoAsyncTask(context, "HoSoByHopHoSo", MaHop, pageHoSo, Constants.NUM_ROW_PER_PAGE).execute().get();
                            gridHoSoAdapter = new GridHoSoAdapter(context, R.layout.grid_item, arrHoSo);
                            gridView.setAdapter(gridHoSoAdapter);
                            Helper.setGridViewHeightBasedOnChildren(gridView, 2);

                            listHeader = createListHeader();
                            listChild = createListChild(MaPhong, pageHopHoSo, MaHop, pageHoSo);
                            adapter = new ExpandableListAdapter(context, listHeader, listChild);
                            exp_listview.setAdapter(adapter);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        pageLoad(type, pageHoSo);
                        break;
                    case 3:
                        pageVanBan = page;
                        try {
                            arrVanBan = new SearchVanBanAsyncTask(context, "VbbyHs", HoSoSo, pageVanBan).execute().get();
                            vanBanAdapter = new VanBanAdapter(context, R.layout.list_item_vanban, arrVanBan);
                            lv_vanBan.setAdapter(vanBanAdapter);
                            Helper.setListViewHeightBasedOnChildren(lv_vanBan);

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

    private static List<Phong_ett> createListHeader() {
        List<Phong_ett> listHeader = new ArrayList<>();
        ArrayList<Phong_ett> arrPhong;
        try {
            arrPhong = new SearchPhongAsyncTask(context, "ID", "", 1).execute().get();
            for (Phong_ett phong_ett : arrPhong) {
                String MaPhong = String.valueOf(phong_ett.getMaPhong());
                int sl_hophs = new GetNumBerHopHoSoAsyncTask(context, "HopHoSoByPhong", MaPhong, 1).execute().get();
                listHeader.add(new Phong_ett(phong_ett.getMaCQLT(), phong_ett.getMaPhong(), phong_ett.getTenPhong(), phong_ett.getLichSuHinhThanh(),
                        phong_ett.getThoiGianTaiLieu(), phong_ett.getTongSoTaiLieu(), phong_ett.getSoTaiLieuDaChinhLy(), phong_ett.getSoTaiLieuChuaChinhLy(),
                        phong_ett.getCacNhomTaiLieu(), phong_ett.getMaNN(), phong_ett.getThoiGianNhapTaiLieu(), phong_ett.getCongCuTraCuu(), phong_ett.getLapBanSaoBaoHiem(),
                        phong_ett.getGhiChu(), sl_hophs));
            }
            return listHeader;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static HashMap<Phong_ett, List<HopHoSo_ett>> createListChild(String MaP, int pageHopHoSo, String MaHop, int pageHoSo) {
        HashMap<Phong_ett, List<HopHoSo_ett>> listChild = new HashMap<>();
        ArrayList<HopHoSo_ett> arrHopHoSo = new ArrayList<>();
        ArrayList<HopHoSo_ett> arrHopHoSo1 = new ArrayList<>();

        for (Phong_ett phong_ett : listHeader) {
            String MaPhong = String.valueOf(phong_ett.getMaPhong());
            try {
                if (MaPhong.equals(MaP))
                    arrHopHoSo = new SearchHopHoSoAsyncTask(context, Constants.FUNCTION_SEARCH, "", "", "HopHoSoByPhong", MaPhong, pageHopHoSo, Constants.NUM_ROW_PER_PAGE).execute().get();
                else
                    arrHopHoSo = new SearchHopHoSoAsyncTask(context, Constants.FUNCTION_SEARCH, "", "", "HopHoSoByPhong", MaPhong, 1, Constants.NUM_ROW_PER_PAGE).execute().get();
                arrHopHoSo1 = new ArrayList<>();
                for (HopHoSo_ett hopHoSo_ett : arrHopHoSo) {
                    String MaHopHS = String.valueOf(hopHoSo_ett.getMaHopHS());
                    ArrayList<HoSo_ett> arrHoSo = new ArrayList<>();
                    if (MaHopHS.equals(MaHop))
                        arrHoSo = new SearchHoSoAsyncTask(context, "HoSoByHopHoSo", MaHopHS, pageHoSo, Constants.NUM_ROW_PER_PAGE).execute().get();
                    else
                        arrHoSo = new SearchHoSoAsyncTask(context, "HoSoByHopHoSo", MaHopHS, 1, Constants.NUM_ROW_PER_PAGE).execute().get();
//                    ArrayList<HoSo_ett> arrHoSo1 = new ArrayList<>();
                    int sl_hoso = 0;
                    if (!arrHoSo.isEmpty())
                        sl_hoso = arrHoSo.get(0).getTotal_record();
//                    int sl_vanban = 0;
//                    for (HoSo_ett hoSo_ett : arrHoSo) {
//                        HoSo_ett hoSoEtt = new HoSo_ett(hoSo_ett.getMaPhong(), hoSo_ett.getMucLucSo(), hoSo_ett.getMaHopHS(), hoSo_ett.getHoSoSo(), hoSo_ett.getKHTT(),
//                                hoSo_ett.getTieuDeHs(), hoSo_ett.getChuGiai(), hoSo_ett.getThoiGianBatDau(), hoSo_ett.getThoiGianKetThuc(), hoSo_ett.getMaNN(), hoSo_ett.getButTich(),
//                                hoSo_ett.getSoLuongTo(), hoSo_ett.getMaTHBQ(), hoSo_ett.getMaCDSD(), hoSo_ett.getMaTTVL(), sl_vanban);
//
//                        arrHoSo1.add(hoSoEtt);
//                        if (sl_hoso == 0)
//                            sl_hoso = hoSo_ett.getTotal_record();
//                    }
                    arrHopHoSo1.add(new HopHoSo_ett(hopHoSo_ett.getMaHopHS(), hopHoSo_ett.getTenHop(), hopHoSo_ett.getGhiChu(), hopHoSo_ett.isActive(),
                            hopHoSo_ett.getMaPhong(), sl_hoso, arrHoSo));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            listChild.put(phong_ett, arrHopHoSo1);
        }
        return listChild;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, final boolean isLastChild, View convertView, ViewGroup parent) {
        final HopHoSo_ett hopHoSo_ett = (HopHoSo_ett) getChild(groupPosition, childPosition);
        final ArrayList<HoSo_ett> listHoso;
        final HoSoAdapter adapter;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_hophs_hs, parent, false);
        }
        ImageView img_hophoso = (ImageView) convertView.findViewById(R.id.img_hophoso);
        TextView txt_hophoso = (TextView) convertView.findViewById(R.id.txt_hophoso);
        final ListView lv_hoso = (ListView) convertView.findViewById(R.id.lv_hoso);
        txt_hophoso.setText("Hộp :" + hopHoSo_ett.getTenHop() + "(" + hopHoSo_ett.getSl_hhs() + ")");
        listHoso = hopHoSo_ett.getList_HoSo();
        adapter = new HoSoAdapter(context, R.layout.list_item, listHoso);
        lv_hoso.setAdapter(adapter);
        Helper.setListViewHeightBasedOnChildren(lv_hoso);
        img_hophoso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isExpand = !isExpand;
                if (isExpand) {
                    lv_hoso.setVisibility(View.VISIBLE);
                } else
                    lv_hoso.setVisibility(View.GONE);

                MaHop = String.valueOf(hopHoSo_ett.getMaHopHS());
                TenHop = hopHoSo_ett.getTenHop();
                type = 2;
                gridHoSoAdapter = new GridHoSoAdapter(context, R.layout.grid_item, listHoso);
                gridView.setAdapter(gridHoSoAdapter);
                Helper.setGridViewHeightBasedOnChildren(gridView, 2);
                total_record = hopHoSo_ett.getSl_hhs();
                pageLoad(type, pageHoSo);
                txt_header.setText(TenPhong + " > " + "Hộp " + TenHop + " > " + "Danh sách Hồ sơ");
                btn_AddNew.setText("Thêm mới Hồ sơ");
                btn_AddNew.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, AddNewAndUpdateHoSoActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("MaPhong", MaPhong);
                        bundle.putString("TenPhong", TenPhong);
                        bundle.putString("MaHop", MaHop);
                        bundle.putString("TenHop", TenHop);
                        intent.putExtra("Data", bundle);
                        context.startActivity(intent);
                    }
                });

                btn_AddNew.setVisibility(View.VISIBLE);
                ll_button.setVisibility(View.VISIBLE);
                gridView.setVisibility(View.VISIBLE);
                ll_detail.setVisibility(View.GONE);
                lv_vanBan.setVisibility(View.GONE);
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
