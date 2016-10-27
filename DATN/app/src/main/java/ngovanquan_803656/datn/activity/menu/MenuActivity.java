package ngovanquan_803656.datn.activity.menu;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ngovanquan_803656.datn.R;
import ngovanquan_803656.datn.activity.LoginActivity;
import ngovanquan_803656.datn.adapter.NavDrawerListAdapter;
import ngovanquan_803656.datn.asynctask.qtht.GetListPermissionAsyncTask;
import ngovanquan_803656.datn.fragment.GioiThieuFragment;
import ngovanquan_803656.datn.fragment.TimKiemNangCaoFragment;
import ngovanquan_803656.datn.fragment.TrangChuFragment;
import ngovanquan_803656.datn.fragment.qldm.CheDoSuDungFragment;
import ngovanquan_803656.datn.fragment.qldm.CoQuanLuuTruFragment;
import ngovanquan_803656.datn.fragment.qldm.DoMatFragment;
import ngovanquan_803656.datn.fragment.qldm.DoTinCayFragment;
import ngovanquan_803656.datn.fragment.qldm.LoaiHinhTaiLieuFragment;
import ngovanquan_803656.datn.fragment.qldm.LoaiVanBanFragment;
import ngovanquan_803656.datn.fragment.qldm.NgonNguFragment;
import ngovanquan_803656.datn.fragment.qldm.PhongFragment;
import ngovanquan_803656.datn.fragment.qldm.ThoiHanBaoQuanFragment;
import ngovanquan_803656.datn.fragment.qldm.TinhTrangVatLyFragment;
import ngovanquan_803656.datn.fragment.qldm.HopHoSoFragment;
import ngovanquan_803656.datn.fragment.qllt.HoSoAndVanBanFragment;
import ngovanquan_803656.datn.fragment.qllt.QuanLyLuuTruFragment;
import ngovanquan_803656.datn.fragment.qtht.CauHinhHeThongFragment;
import ngovanquan_803656.datn.fragment.qtht.NguoiDungFragment;
import ngovanquan_803656.datn.fragment.qtht.NhomNguoiDungFragment;
import ngovanquan_803656.datn.fragment.tkbc.DanhSachPhongFragment;
import ngovanquan_803656.datn.fragment.tkbc.MucLucHoSoFragment;
import ngovanquan_803656.datn.fragment.tkbc.MucLucVanBanFragment;
import ngovanquan_803656.datn.fragment.tkbc.ThongKeHoSoFragment;
import ngovanquan_803656.datn.model.NavDrawerItem;
import ngovanquan_803656.datn.model.QLND_ett;
import ngovanquan_803656.datn.utils.Constants;

public class MenuActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
//    private ListView mDrawerList;
    private ExpandableListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar toolbar;

    TextView txt_account, txt_title;
    ImageView btn_logout;

//    nav drawer title
    private CharSequence mDrawerTitle;
//    use to store app title;
    private CharSequence mTitle;
//    slide menu items
//    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    List<NavDrawerItem> listHeader;
    HashMap<NavDrawerItem, List<NavDrawerItem>> listChild;
    ArrayList<String> arrData = new ArrayList<>();

//    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerList = (ExpandableListView) findViewById(R.id.list_slidermenu);
        Intent intent = getIntent();
        QLND_ett qlnd_ett = (QLND_ett) intent.getSerializableExtra("user");
        if (qlnd_ett != null) {
            try {
                arrData = new GetListPermissionAsyncTask(this, qlnd_ett.getMaNhom()).execute().get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            prepareData1();
        } else {
            prepareData();
        }
        adapter = new NavDrawerListAdapter(this, listHeader, listChild);
        mDrawerList.setAdapter(adapter);
//        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
        addEvents();
        // enabling action bar app icon and behaving it as toggle button
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        View mCustomView = LayoutInflater.from(this).inflate(R.layout.custom_actionbar, null);
        txt_title = (TextView) mCustomView.findViewById(R.id.txt_title);
        txt_account = (TextView) mCustomView.findViewById(R.id.txt_account);
        btn_logout = (ImageView) mCustomView.findViewById(R.id.btn_logout);
        getSupportActionBar().setCustomView(mCustomView);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        txt_title.setText(mTitle);
        if (qlnd_ett != null)
            txt_account.setText(qlnd_ett.getLoginID());
        else
            txt_account.setText("admin");
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent i = new Intent(MenuActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
//                getSupportActionBar().setTitle(mDrawerTitle);
                txt_title.setText(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
//                getSupportActionBar().setTitle(mTitle);
                txt_title.setText(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        if (savedInstanceState == null) {
            displayView(0, 0);
        }


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
//        if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        mTitle = title;
//        getSupportActionBar().setTitle(mTitle);
        txt_title.setText(mTitle);
    }
    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

//    slide menu item click listener
//    private class SlideMenuClickListener implements ListView.OnItemClickListener {
//
//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        displayView(position);
//    }
//}
    public void displayView(int groupPosition, int childPosition) {
        Fragment fragment = null;
        switch (groupPosition) {
            case 0:
                fragment = new TrangChuFragment();
                break;
            case 1:
                fragment = new GioiThieuFragment();
                break;
            case 2:
//                switch (childPosition) {
//                    case 0:
//                        fragment = new HoSoAndVanBanFragment();
//                        break;
//                    case 1:
//                        fragment = new QuanLyLuuTruFragment();
//                        break;
//                }
                fragment = new QuanLyLuuTruFragment();
                break;
            case 3:
                switch (childPosition) {
                    case 0:
                        fragment = new CoQuanLuuTruFragment();
                        break;
                    case 1:
                        fragment = new PhongFragment();
                        break;
                    case 2:
                        fragment = new HopHoSoFragment();
                        break;
                    case 3:
                        fragment = new LoaiVanBanFragment();
                        break;
                    case 4:
                        fragment = new ThoiHanBaoQuanFragment();
                        break;
                    case 5:
                        fragment = new CheDoSuDungFragment();
                        break;
                    case 6:
                        fragment = new TinhTrangVatLyFragment();
                        break;
                    case 7:
                        fragment = new NgonNguFragment();
                        break;
                    case 8:
                        fragment = new DoMatFragment();
                        break;
                    case 9:
                        fragment = new DoTinCayFragment();
                        break;
                    case 10:
                        fragment = new LoaiHinhTaiLieuFragment();
                        break;
                }
                break;
            case 4:
                switch (childPosition) {
                    case 0:
                        fragment = new MucLucHoSoFragment();
                        break;
                    case 1:
                        fragment = new MucLucVanBanFragment();
                        break;
                    case 2:
                        fragment = new DanhSachPhongFragment();
                        break;
                    case 3:
                        fragment = new ThongKeHoSoFragment();
                        break;
                }
                break;
            case 5:
                fragment = new TimKiemNangCaoFragment();
                break;
            case 6:
                switch (childPosition) {
                    case 0:
                        fragment = new NguoiDungFragment();
                        break;
                    case 1:
                        fragment = new CauHinhHeThongFragment();
                        break;
//                    case 3:
//                        fragment = new CauHinhHeThongFragment();
//                        break;
                }
                break;
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
//            update selected item and title, then close the drawer
//            mDrawerList.setItemChecked(groupPosition, true);
//            mDrawerList.setSelection(groupPosition);
            mDrawerList.setSelectedGroup(groupPosition);
            mDrawerList.setSelectedChild(groupPosition, childPosition, true);
            setTitle(listHeader.get(groupPosition).getTitle());
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            Log.e("MenuActivity", "Error in creating fragment");
        }
    }

    private void prepareData() {
        listHeader = new ArrayList<>();
        listChild = new HashMap<>();
        navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);
//        add data header
        listHeader.add(new NavDrawerItem(Constants.Menu.Trangchu.getTitle(), navMenuIcons.getResourceId(0, -1), 0, false));
        listHeader.add(new NavDrawerItem(Constants.Menu.GioithieuChicuc.getTitle(), navMenuIcons.getResourceId(1, -1), 0, false));
        listHeader.add(new NavDrawerItem(Constants.Menu.QuanlyLuutru.getTitle(), navMenuIcons.getResourceId(2, -1), 0, false));
        listHeader.add(new NavDrawerItem(Constants.Menu.QuanlyDanhmuc.getTitle(), navMenuIcons.getResourceId(3, -1), 11, true));
        listHeader.add(new NavDrawerItem(Constants.Menu.ThongKeBaoCao.getTitle(), navMenuIcons.getResourceId(4, -1), 4, true));
        listHeader.add(new NavDrawerItem(Constants.Menu.TimkiemNangcao.getTitle(), navMenuIcons.getResourceId(5, -1), 0, false));
        listHeader.add(new NavDrawerItem(Constants.Menu.QuantriHethong.getTitle(), navMenuIcons.getResourceId(6, -1), 2, true));
//      add child data
//        List<NavDrawerItem> QuanlyLuuTru = new ArrayList<>();
//        QuanlyLuuTru.add(new NavDrawerItem(Constants.Menu.QuanLyHoSoVanBan.getTitle(), 0, false));
//        QuanlyLuuTru.add(new NavDrawerItem(Constants.Menu.HoSoTuExcel.getTitle(), 0, false));
//        QuanlyLuuTru.add(new NavDrawerItem(Constants.Menu.VanBanTuExcel.getTitle(), 0, false));

        List<NavDrawerItem> quanLyDanhMuc = new ArrayList<>();
        quanLyDanhMuc.add(new NavDrawerItem(Constants.Menu.QuanlyCQLT.getTitle(), 0, false));
        quanLyDanhMuc.add(new NavDrawerItem(Constants.Menu.QuanlyPhong.getTitle(), 0, false));
        quanLyDanhMuc.add(new NavDrawerItem(Constants.Menu.QuanlyHopHoSo.getTitle(), 0, false));
        quanLyDanhMuc.add(new NavDrawerItem(Constants.Menu.QuanlyLVB.getTitle(), 0, false));
        quanLyDanhMuc.add(new NavDrawerItem(Constants.Menu.QuanlyTHBQ.getTitle(), 0, false));
        quanLyDanhMuc.add(new NavDrawerItem(Constants.Menu.QuanlyCDSD.getTitle(), 0, false));
        quanLyDanhMuc.add(new NavDrawerItem(Constants.Menu.QuanlyTTVL.getTitle(), 0, false));
        quanLyDanhMuc.add(new NavDrawerItem(Constants.Menu.QuanlyNgonNgu.getTitle(), 0, false));
        quanLyDanhMuc.add(new NavDrawerItem(Constants.Menu.QuanlyDoMat.getTitle(), 0, false));
        quanLyDanhMuc.add(new NavDrawerItem(Constants.Menu.QuanlyDoTinCay.getTitle(), 0, false));
        quanLyDanhMuc.add(new NavDrawerItem(Constants.Menu.QuanlyLoaiHinhTaiLieu.getTitle(), 0, false));

        List<NavDrawerItem> thongKeBaoCao = new ArrayList<>();
        thongKeBaoCao.add(new NavDrawerItem(Constants.Menu.MucLucHoSo.getTitle(), 0, false));
        thongKeBaoCao.add(new NavDrawerItem(Constants.Menu.MucLucVanBan.getTitle(), 0, false));
        thongKeBaoCao.add(new NavDrawerItem(Constants.Menu.DanhSachPhong.getTitle(), 0, false));
        thongKeBaoCao.add(new NavDrawerItem(Constants.Menu.ThongKeHoSoLuuTru.getTitle(), 0, false));

        List<NavDrawerItem> quanTriHeThong = new ArrayList<>();
//        quanTriHeThong.add(new NavDrawerItem(Constants.Menu.QuanlyNhomNguoiDung.getTitle(), 0, false));
        quanTriHeThong.add(new NavDrawerItem(Constants.Menu.QuanlyNguoiDung.getTitle(), 0, false));
//        quanTriHeThong.add(new NavDrawerItem(Constants.Menu.QuanlyPhanQuyen.getTitle(), 0, false));
        quanTriHeThong.add(new NavDrawerItem(Constants.Menu.cauhinhhethong.getTitle(), 0, false));
//        quanTriHeThong.add(new NavDrawerItem(Constants.Menu.GioiThieuHeThong.getTitle(), 0, false));

        listChild.put(listHeader.get(0), null);
        listChild.put(listHeader.get(1), null);
        listChild.put(listHeader.get(2), null);
        listChild.put(listHeader.get(3), quanLyDanhMuc);
        listChild.put(listHeader.get(4), thongKeBaoCao);
        listChild.put(listHeader.get(5), null);
        listChild.put(listHeader.get(6), quanTriHeThong);
    }

    private void prepareData1() {
        listHeader = new ArrayList<>();
        listChild = new HashMap<>();

//      add child data
//        List<NavDrawerItem> QuanlyLuuTru = new ArrayList<>();
//        if (arrData.contains("QuanLyHoSoVanBan"))
//            QuanlyLuuTru.add(new NavDrawerItem(Constants.Menu.QuanLyHoSoVanBan.getTitle(), 0, false));
//        if (arrData.contains("HoSoTuExcel"))
//            QuanlyLuuTru.add(new NavDrawerItem(Constants.Menu.HoSoTuExcel.getTitle(), 0, false));
//        if (arrData.contains("VanBanTuExcel"))
//            QuanlyLuuTru.add(new NavDrawerItem(Constants.Menu.VanBanTuExcel.getTitle(), 0, false));

        navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);
        List<NavDrawerItem> quanLyDanhMuc = new ArrayList<>();
        if (arrData.contains("QuanlyCQLT"))
            quanLyDanhMuc.add(new NavDrawerItem(Constants.Menu.QuanlyCQLT.getTitle(), 0, false));
        if (arrData.contains("QuanlyPhong"))
            quanLyDanhMuc.add(new NavDrawerItem(Constants.Menu.QuanlyPhong.getTitle(), 0, false));
        if (arrData.contains("QuanlyHopHoSo"))
            quanLyDanhMuc.add(new NavDrawerItem(Constants.Menu.QuanlyHopHoSo.getTitle(), 0, false));
        if (arrData.contains("QuanlyLVB"))
            quanLyDanhMuc.add(new NavDrawerItem(Constants.Menu.QuanlyLVB.getTitle(), 0, false));
        if (arrData.contains("QuanlyTHBQ"))
            quanLyDanhMuc.add(new NavDrawerItem(Constants.Menu.QuanlyTHBQ.getTitle(), 0, false));
        if (arrData.contains("QuanlyCDSD"))
            quanLyDanhMuc.add(new NavDrawerItem(Constants.Menu.QuanlyCDSD.getTitle(), 0, false));
        if (arrData.contains("QuanlyTTVL"))
            quanLyDanhMuc.add(new NavDrawerItem(Constants.Menu.QuanlyTTVL.getTitle(), 0, false));
        if (arrData.contains("QuanlyNgonNgu"))
            quanLyDanhMuc.add(new NavDrawerItem(Constants.Menu.QuanlyNgonNgu.getTitle(), 0, false));
        if (arrData.contains("QuanlyDoMat"))
            quanLyDanhMuc.add(new NavDrawerItem(Constants.Menu.QuanlyDoMat.getTitle(), 0, false));
        if (arrData.contains("QuanlyDoTinCay"))
            quanLyDanhMuc.add(new NavDrawerItem(Constants.Menu.QuanlyDoTinCay.getTitle(), 0, false));
        if (arrData.contains("QuanlyLoaiHinhTaiLieu"))
            quanLyDanhMuc.add(new NavDrawerItem(Constants.Menu.QuanlyLoaiHinhTaiLieu.getTitle(), 0, false));

        List<NavDrawerItem> thongKeBaoCao = new ArrayList<>();
        if (arrData.contains("MucLucHoSo"))
            thongKeBaoCao.add(new NavDrawerItem(Constants.Menu.MucLucHoSo.getTitle(), 0, false));
        if (arrData.contains("MucLucVanBan"))
            thongKeBaoCao.add(new NavDrawerItem(Constants.Menu.MucLucVanBan.getTitle(), 0, false));
        if (arrData.contains("DanhSachPhong"))
            thongKeBaoCao.add(new NavDrawerItem(Constants.Menu.DanhSachPhong.getTitle(), 0, false));
        if (arrData.contains("ThongKeHoSoLuuTru"))
            thongKeBaoCao.add(new NavDrawerItem(Constants.Menu.ThongKeHoSoLuuTru.getTitle(), 0, false));

        List<NavDrawerItem> quanTriHeThong = new ArrayList<>();
//        if (arrData.contains("QuanlyNhomNguoiDung"))
//            quanTriHeThong.add(new NavDrawerItem(Constants.Menu.QuanlyNhomNguoiDung.getTitle(), 0, false));
        if (arrData.contains("QuanlyNguoiDung"))
            quanTriHeThong.add(new NavDrawerItem(Constants.Menu.QuanlyNguoiDung.getTitle(), 0, false));
//        if (arrData.contains("QuanlyPhanQuyen"))
//            quanTriHeThong.add(new NavDrawerItem(Constants.Menu.QuanlyPhanQuyen.getTitle(), 0, false));
        if (arrData.contains("cauhinhhethong"))
            quanTriHeThong.add(new NavDrawerItem(Constants.Menu.cauhinhhethong.getTitle(), 0, false));
//        if (arrData.contains("GioiThieuHeThong"))
//            quanTriHeThong.add(new NavDrawerItem(Constants.Menu.GioiThieuHeThong.getTitle(), 0, false));
//add list header
        if (arrData.contains("Trangchu"))
            listHeader.add(new NavDrawerItem(Constants.Menu.Trangchu.getTitle(), navMenuIcons.getResourceId(0, -1),  0, false));
        else
            listHeader.add(new NavDrawerItem("", 0, false));

        if (arrData.contains("GioithieuChicuc"))
            listHeader.add(new NavDrawerItem(Constants.Menu.GioithieuChicuc.getTitle(), navMenuIcons.getResourceId(1, -1), 0, false));
        else
            listHeader.add(new NavDrawerItem("", 0, false));

        if (arrData.contains("QuanlyLuutru"))
            listHeader.add(new NavDrawerItem(Constants.Menu.QuanlyLuutru.getTitle(), navMenuIcons.getResourceId(2, -1), 0, false));
        else
            listHeader.add(new NavDrawerItem("", 0, false));
        if (arrData.contains("QuanlyDanhmuc"))
            listHeader.add(new NavDrawerItem(Constants.Menu.QuanlyDanhmuc.getTitle(), navMenuIcons.getResourceId(3, -1), quanLyDanhMuc.size(), true));
        else
            listHeader.add(new NavDrawerItem("", 0, false));
        if (arrData.contains("ThongKeBaoCao"))
            listHeader.add(new NavDrawerItem(Constants.Menu.ThongKeBaoCao.getTitle(), navMenuIcons.getResourceId(4, -1), thongKeBaoCao.size(), true));
        else
            listHeader.add(new NavDrawerItem("", 0, false));
        if (arrData.contains("TimkiemNangcao"))
            listHeader.add(new NavDrawerItem(Constants.Menu.TimkiemNangcao.getTitle(), navMenuIcons.getResourceId(5, -1), 0, false));
        else
            listHeader.add(new NavDrawerItem("", 0, false));
        if (arrData.contains("QuantriHethong"))
            listHeader.add(new NavDrawerItem(Constants.Menu.QuantriHethong.getTitle(), navMenuIcons.getResourceId(6, -1), quanTriHeThong.size(), true));
        else
            listHeader.add(new NavDrawerItem("", 0, false));

        listChild.put(listHeader.get(0), null);
        listChild.put(listHeader.get(1), null);
        listChild.put(listHeader.get(2), null);
        listChild.put(listHeader.get(3), quanLyDanhMuc);
        listChild.put(listHeader.get(4), thongKeBaoCao);
        listChild.put(listHeader.get(5), null);
        listChild.put(listHeader.get(6), quanTriHeThong);
    }

    private void addEvents() {
        mDrawerList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Toast.makeText(MenuActivity.this, groupPosition + "", Toast.LENGTH_SHORT).show();
                displayView(groupPosition, 100);
                return false;
            }
        });

        mDrawerList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(MenuActivity.this, groupPosition + " - " + childPosition, Toast.LENGTH_SHORT).show();
                displayView(groupPosition, childPosition);
                return false;
            }
        });

        mDrawerList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });

        mDrawerList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });
    }


}
