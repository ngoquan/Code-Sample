package ngovanquan_803656.datn.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import ngovanquan_803656.datn.R;
import ngovanquan_803656.datn.adapter.MenuPhongAdapter;
import ngovanquan_803656.datn.adapter.SpinnerCDSDAdapter;
import ngovanquan_803656.datn.adapter.SpinnerDoMatAdapter;
import ngovanquan_803656.datn.adapter.SpinnerDoTinCayAdapter;
import ngovanquan_803656.datn.adapter.SpinnerHoSoAdapter;
import ngovanquan_803656.datn.adapter.SpinnerLVBAdapter;
import ngovanquan_803656.datn.adapter.SpinnerNgonNguAdapter;
import ngovanquan_803656.datn.adapter.SpinnerTTVLAdapter;
import ngovanquan_803656.datn.adapter.VanBanAdapter;
import ngovanquan_803656.datn.asynctask.qldm.SearchCDSDAsyncTask;
import ngovanquan_803656.datn.asynctask.qldm.SearchDoMatAsyncTask;
import ngovanquan_803656.datn.asynctask.qldm.SearchDoTinCayAsyncTask;
import ngovanquan_803656.datn.asynctask.qldm.SearchLVBAsyncTask;
import ngovanquan_803656.datn.asynctask.qldm.SearchNgonNguAsyncTask;
import ngovanquan_803656.datn.asynctask.qldm.SearchTTVLAsyncTask;
import ngovanquan_803656.datn.asynctask.qllt.AddNewAndUpdateVanBanAsyncTask;
import ngovanquan_803656.datn.asynctask.qllt.SearchHoSoAsyncTask;
import ngovanquan_803656.datn.asynctask.qllt.SearchVanBanAsyncTask;
import ngovanquan_803656.datn.model.CDSD_ett;
import ngovanquan_803656.datn.model.DoMat_ett;
import ngovanquan_803656.datn.model.DoTinCay_ett;
import ngovanquan_803656.datn.model.HoSo_ett;
import ngovanquan_803656.datn.model.LVB_ett;
import ngovanquan_803656.datn.model.NgonNgu_ett;
import ngovanquan_803656.datn.model.TTVL_ett;
import ngovanquan_803656.datn.model.VanBan_ett;
import ngovanquan_803656.datn.utils.Constants;
import ngovanquan_803656.datn.utils.Helper;

public class AddNewAndUpdateVanBanActivity extends Activity implements View.OnClickListener{
    private EditText txt_maHopHS, txt_soKHVB, txt_mucLucSo, txt_thoiGian, txt_KHTT, txt_toSo, txt_tacGia, txt_SLT, txt_trichYeu, txt_butTich, txt_ghiChu, txt_vanBanID;
    private Spinner spn_hoSoSo, spn_ngonNgu, spn_LVB, spn_TTVL, spn_DM, spn_DTC, spn_CDSD;
    private Button btn_addNew, btn_update, btn_cancel;
    TextView txt_header;
    ImageView btn_close;
    String MaPhong, TenPhong, MaHop, TenHop, HoSoSo;
    VanBan_ett vanBan_ett;

    ArrayList<HoSo_ett> arrHoSo = new ArrayList<>();
    SpinnerHoSoAdapter spinnerHoSoAdapter;
    ArrayList<NgonNgu_ett> arrNgonNgu = new ArrayList<>();
    SpinnerNgonNguAdapter spinnerNgonNguAdapter;
    ArrayList<LVB_ett> arrLVB = new ArrayList<>();
    SpinnerLVBAdapter spinnerLVBAdapter;
    ArrayList<TTVL_ett> arrTTVL = new ArrayList<>();
    SpinnerTTVLAdapter spinnerTTVLAdapter;
    ArrayList<DoMat_ett> arrDM = new ArrayList<>();
    SpinnerDoMatAdapter spinnerDoMatAdapter;
    ArrayList<DoTinCay_ett> arrDTC = new ArrayList<>();
    SpinnerDoTinCayAdapter spinnerDoTinCayAdapter;
    ArrayList<CDSD_ett> arrCDSD = new ArrayList<>();
    SpinnerCDSDAdapter spinnerCDSDAdapter;

//    String HoSoSo;
    int MaLVB, MaNN, MaDoMat, MaDoTinCay, MaCDSD, MaTTVL;
    String MucLucSo, ToSo, SoKyHieu, ThoiGian, TacGia, TrichYeuND, KHTT, SoLuongTo, ButTich, GhiChu, VanBanID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // hide titlebar of application
        // must be before setting the layout
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // hide statusbar of Android
        // could also be done later
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_new_and_update_van_ban);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.x = -20;
        params.height = 500;
        params.width = 900;
        params.y = -20;
        this.getWindow().setAttributes(params);
        addControls();
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("Data");
        if (bundle != null) {
            MaPhong = bundle.getString("MaPhong");
            TenPhong = bundle.getString("TenPhong");
            MaHop = bundle.getString("MaHop");
            TenHop = bundle.getString("TenHop");
            HoSoSo = bundle.getString("HoSoSo");
            btn_addNew.setVisibility(View.VISIBLE);
            btn_update.setVisibility(View.GONE);
        }
        else {
            bundle = intent.getBundleExtra("Data1");
            MaPhong = bundle.getString("MaPhong");
            TenPhong = bundle.getString("TenPhong");
            MaHop = bundle.getString("MaHop");
            TenHop = bundle.getString("TenHop");
            HoSoSo = bundle.getString("HoSoSo");
            vanBan_ett = (VanBan_ett) bundle.getSerializable("VanBan_ett");

            txt_vanBanID.setText(vanBan_ett.getVanBanId());
            txt_soKHVB.setText(vanBan_ett.getSoKyHieu());
            txt_mucLucSo.setText(vanBan_ett.getMucLucSo());
            txt_thoiGian.setText(vanBan_ett.getThoiGian());
            txt_KHTT.setText(vanBan_ett.getKHTT());
            txt_toSo.setText(vanBan_ett.getToSo());
            txt_tacGia.setText(vanBan_ett.getTacGia());
            txt_SLT.setText(vanBan_ett.getSoLuongTo());
            txt_trichYeu.setText(vanBan_ett.getTrichYeuND());
            txt_butTich.setText(vanBan_ett.getButTich());
            txt_ghiChu.setText(vanBan_ett.getGhiChu());
            btn_addNew.setVisibility(View.GONE);
            btn_update.setVisibility(View.VISIBLE);
        }

//        Log.e("MaNN", vanBan_ett.getMaNN() + "");


        txt_maHopHS.setText(TenHop);
        txt_maHopHS.setEnabled(false);
        txt_header.setText(TenPhong + " > " + "Hộp " + TenHop + " > " + "Hồ sơ số " + HoSoSo + " > " + "Thêm mới Văn bản");
//        Log.e("ResultGetIntent", TenPhong + " - " + TenHop + " - " + HoSoSo);

        setUpSpinner();
        addEvents();
    }

    private void addControls() {
        txt_maHopHS = (EditText) findViewById(R.id.txt_maHopHS);
        txt_soKHVB = (EditText) findViewById(R.id.txt_soKHVB);
        txt_mucLucSo = (EditText) findViewById(R.id.txt_mucLucSo);
        txt_thoiGian = (EditText) findViewById(R.id.txt_thoiGian);
        txt_KHTT = (EditText) findViewById(R.id.txt_KHTT);
        txt_toSo = (EditText) findViewById(R.id.txt_toSo);
        txt_tacGia = (EditText) findViewById(R.id.txt_tacGia);
        txt_SLT = (EditText) findViewById(R.id.txt_SLT);
        txt_trichYeu = (EditText) findViewById(R.id.txt_trichYeu);
        txt_butTich = (EditText) findViewById(R.id.txt_butTich);
        txt_ghiChu = (EditText) findViewById(R.id.txt_ghiChu);
        txt_vanBanID = (EditText) findViewById(R.id.txt_vanBanID);
        spn_hoSoSo = (Spinner) findViewById(R.id.spn_hoSoSo);
        spn_ngonNgu = (Spinner) findViewById(R.id.spn_ngonNgu);
        spn_LVB = (Spinner) findViewById(R.id.spn_LVB);
        spn_TTVL = (Spinner) findViewById(R.id.spn_TTVL);
        spn_DM = (Spinner) findViewById(R.id.spn_DM);
        spn_DTC = (Spinner) findViewById(R.id.spn_DTC);
        spn_CDSD = (Spinner) findViewById(R.id.spn_CDSD);
        btn_addNew = (Button) findViewById(R.id.btn_AddNew);
        btn_update = (Button) findViewById(R.id.btn_Update);
        btn_cancel = (Button) findViewById(R.id.btn_Cancel);
        txt_header = (TextView) findViewById(R.id.txt_header);
        btn_close = (ImageView) findViewById(R.id.btn_close);
    }

    private void setUpSpinner() {
        try {
            arrHoSo = new SearchHoSoAsyncTask(this, "HoSoByHopHoSo", MaHop, 0, 0).execute().get();
            spinnerHoSoAdapter = new SpinnerHoSoAdapter(this, R.layout.list_item_spinner, arrHoSo);
            spn_hoSoSo.setAdapter(spinnerHoSoAdapter);

            arrNgonNgu = new SearchNgonNguAsyncTask(this, "ID", "", 0, 0).execute().get();
            spinnerNgonNguAdapter = new SpinnerNgonNguAdapter(this, R.layout.list_item_spinner, arrNgonNgu);
            spn_ngonNgu.setAdapter(spinnerNgonNguAdapter);

            arrLVB = new SearchLVBAsyncTask(this, "ID", "", 0, 0).execute().get();
            spinnerLVBAdapter = new SpinnerLVBAdapter(this, R.layout.list_item_spinner, arrLVB);
            spn_LVB.setAdapter(spinnerLVBAdapter);

            arrTTVL = new SearchTTVLAsyncTask(this, "ID", "", 0, 0).execute().get();
            spinnerTTVLAdapter = new SpinnerTTVLAdapter(this, R.layout.list_item_spinner, arrTTVL);
            spn_TTVL.setAdapter(spinnerTTVLAdapter);

            arrDM = new SearchDoMatAsyncTask(this, "ID", "", 0, 0).execute().get();
            spinnerDoMatAdapter = new SpinnerDoMatAdapter(this, R.layout.list_item_spinner, arrDM);
            spn_DM.setAdapter(spinnerDoMatAdapter);

            arrDTC = new SearchDoTinCayAsyncTask(this, "ID", "", 0, 0).execute().get();
            spinnerDoTinCayAdapter = new SpinnerDoTinCayAdapter(this, R.layout.list_item_spinner, arrDTC);
            spn_DTC.setAdapter(spinnerDoTinCayAdapter);

            arrCDSD = new SearchCDSDAsyncTask(this, "ID", "", 0, 0).execute().get();
            spinnerCDSDAdapter = new SpinnerCDSDAdapter(this, R.layout.list_item_spinner, arrCDSD);
            spn_CDSD.setAdapter(spinnerCDSDAdapter);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void addEvents() {
        spn_hoSoSo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                HoSoSo = arrHoSo.get(position).getHoSoSo();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                for (int i = 0; i < arrHoSo.size(); i++) {
                    if (arrHoSo.get(i).getHoSoSo().equals(HoSoSo)) {
                        spn_hoSoSo.setSelection(i);
                    }
                }
            }
        });

        spn_LVB.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MaLVB = (int) arrLVB.get(position).getMaLVB();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                MaLVB = (int) arrLVB.get(0).getMaLVB();
            }
        });

        spn_ngonNgu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MaNN = (int) arrNgonNgu.get(position).getMaNN();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                MaNN = (int) arrNgonNgu.get(0).getMaNN();
            }
        });

        spn_TTVL.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MaTTVL = (int) arrTTVL.get(position).getMaTTVL();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                MaTTVL = (int) arrTTVL.get(0).getMaTTVL();
            }
        });

        spn_DM.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MaDoMat = (int) arrDM.get(position).getMaDoMat();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                MaDoMat = (int) arrDM.get(0).getMaDoMat();
            }
        });

        spn_DTC.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MaDoTinCay = (int) arrDTC.get(position).getMaDoTinCay();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                MaDoTinCay = (int) arrDTC.get(0).getMaDoTinCay();
            }
        });

        spn_CDSD.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MaCDSD = (int) arrCDSD.get(position).getMaCDSD();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                MaCDSD = (int) arrCDSD.get(0).getMaCDSD();
            }
        });
        btn_addNew.setOnClickListener(this);
        btn_update.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        txt_thoiGian.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    Helper.showDatePickerDialog(AddNewAndUpdateVanBanActivity.this, txt_thoiGian);
            }
        });
        btn_close.setOnClickListener(this);

    }
    private void setValueForControls() {
        txt_soKHVB.setText("");
        txt_mucLucSo.setText("");
        txt_thoiGian.setText("");
        txt_KHTT.setText("");
        txt_toSo.setText("");
        txt_tacGia.setText("");
        txt_SLT.setText("");
        txt_trichYeu.setText("");
        txt_butTich.setText("");
        txt_ghiChu.setText("");
        spn_LVB.setSelection(0);
        spn_CDSD.setSelection(0);
        spn_DM.setSelection(0);
        spn_DTC.setSelection(0);
        spn_ngonNgu.setSelection(0);
        spn_TTVL.setSelection(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_AddNew:
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Calendar calendar = Calendar.getInstance();
                String date = dateFormat.format(calendar.getTime());
                String strMd5 = Helper.md5(date);
                VanBanID = Helper.createUUID(strMd5);
                MucLucSo = txt_mucLucSo.getText().toString();
                ToSo = txt_toSo.getText().toString();
                SoKyHieu = txt_soKHVB.getText().toString();
                ThoiGian = txt_thoiGian.getText().toString();
                TacGia = txt_tacGia.getText().toString();
                TrichYeuND = txt_trichYeu.getText().toString();
                KHTT = txt_KHTT.getText().toString();
                SoLuongTo = txt_SLT.getText().toString();
                ButTich = txt_butTich.getText().toString();
                GhiChu = txt_ghiChu.getText().toString();
                if (!VanBanID.equals("") && !MucLucSo.equals("") && !SoKyHieu.equals("") && !TacGia.equals("") && !TrichYeuND.equals("")) {
                    try {
                        vanBan_ett = new AddNewAndUpdateVanBanAsyncTask(this, Constants.FUNCTION_ADD_NEW, VanBanID, MucLucSo, HoSoSo, ToSo,
                                SoKyHieu, ThoiGian, TacGia, TrichYeuND, KHTT, SoLuongTo, ButTich, GhiChu, MaLVB, MaDoMat, MaDoTinCay, MaTTVL,
                                MaNN, MaCDSD).execute().get();
                        MenuPhongAdapter.arrVanBan = new SearchVanBanAsyncTask(this, "VbbyHs", HoSoSo, MenuPhongAdapter.pageVanBan).execute().get();
                        MenuPhongAdapter.vanBanAdapter = new VanBanAdapter(this, R.layout.list_item_vanban, MenuPhongAdapter.arrVanBan);
                        MenuPhongAdapter.lv_vanBan.setAdapter(MenuPhongAdapter.vanBanAdapter);
                        Helper.setListViewHeightBasedOnChildren(MenuPhongAdapter.lv_vanBan);
                        MenuPhongAdapter.type = 3;
                        if (!MenuPhongAdapter.arrVanBan.isEmpty()) {
                            MenuPhongAdapter.total_record = MenuPhongAdapter.arrVanBan.get(0).getTotal_record();
                            MenuPhongAdapter.pageLoad(MenuPhongAdapter.type, MenuPhongAdapter.pageVanBan);
                        }
                        Toast.makeText(AddNewAndUpdateVanBanActivity.this, vanBan_ett.getErrDesc(), Toast.LENGTH_SHORT).show();
                        setValueForControls();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(AddNewAndUpdateVanBanActivity.this, getString(R.string.message_input), Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.btn_Update:
//                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//                Calendar calendar = Calendar.getInstance();
//                String date = dateFormat.format(calendar.getTime());
//                String strMd5 = Helper.md5(date);
                VanBanID = txt_vanBanID.getText().toString();
                MucLucSo = txt_mucLucSo.getText().toString();
                ToSo = txt_toSo.getText().toString();
                SoKyHieu = txt_soKHVB.getText().toString();
                ThoiGian = txt_thoiGian.getText().toString();
                TacGia = txt_tacGia.getText().toString();
                TrichYeuND = txt_trichYeu.getText().toString();
                KHTT = txt_KHTT.getText().toString();
                SoLuongTo = txt_SLT.getText().toString();
                ButTich = txt_butTich.getText().toString();
                GhiChu = txt_ghiChu.getText().toString();
                if (!VanBanID.equals("") && !MucLucSo.equals("") && !SoKyHieu.equals("") && !TacGia.equals("") && !TrichYeuND.equals("")) {
                    try {
                        vanBan_ett = new AddNewAndUpdateVanBanAsyncTask(this, Constants.FUNCTION_UPDATE, VanBanID, MucLucSo, HoSoSo, ToSo,
                                SoKyHieu, ThoiGian, TacGia, TrichYeuND, KHTT, SoLuongTo, ButTich, GhiChu, MaLVB, MaDoMat, MaDoTinCay, MaTTVL,
                                MaNN, MaCDSD).execute().get();
                        MenuPhongAdapter.arrVanBan = new SearchVanBanAsyncTask(this, "VbbyHs", HoSoSo, MenuPhongAdapter.pageVanBan).execute().get();
                        MenuPhongAdapter.vanBanAdapter = new VanBanAdapter(this, R.layout.list_item_vanban, MenuPhongAdapter.arrVanBan);
                        MenuPhongAdapter.lv_vanBan.setAdapter(MenuPhongAdapter.vanBanAdapter);
                        Helper.setListViewHeightBasedOnChildren(MenuPhongAdapter.lv_vanBan);
                        MenuPhongAdapter.type = 3;
                        if (!MenuPhongAdapter.arrVanBan.isEmpty()) {
                            MenuPhongAdapter.total_record = MenuPhongAdapter.arrVanBan.get(0).getTotal_record();
                            MenuPhongAdapter.pageLoad(MenuPhongAdapter.type, MenuPhongAdapter.pageVanBan);
                        }
                        Toast.makeText(AddNewAndUpdateVanBanActivity.this, vanBan_ett.getErrDesc(), Toast.LENGTH_SHORT).show();
                        setValueForControls();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(AddNewAndUpdateVanBanActivity.this, getString(R.string.message_input), Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btn_Cancel:
                setValueForControls();
                break;
            case R.id.btn_close:
                finish();
                break;
        }
    }
}
