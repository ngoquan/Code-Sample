package ngovanquan_803656.datn.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ngovanquan_803656.datn.R;
import ngovanquan_803656.datn.adapter.GridHoSoAdapter;
import ngovanquan_803656.datn.adapter.MenuPhongAdapter;
import ngovanquan_803656.datn.adapter.SpinnerCDSDAdapter;
import ngovanquan_803656.datn.adapter.SpinnerHopHoSoAdapter;
import ngovanquan_803656.datn.adapter.SpinnerNgonNguAdapter;
import ngovanquan_803656.datn.adapter.SpinnerTHBQAdapter;
import ngovanquan_803656.datn.adapter.SpinnerTTVLAdapter;
import ngovanquan_803656.datn.asynctask.qldm.SearchCDSDAsyncTask;
import ngovanquan_803656.datn.asynctask.qldm.SearchNgonNguAsyncTask;
import ngovanquan_803656.datn.asynctask.qldm.SearchTHBQAsyncTask;
import ngovanquan_803656.datn.asynctask.qldm.SearchTTVLAsyncTask;
import ngovanquan_803656.datn.asynctask.qllt.AddNewAndUpdateHoSo;
import ngovanquan_803656.datn.asynctask.qllt.SearchHoSoAsyncTask;
import ngovanquan_803656.datn.asynctask.qllt.SearchHopHoSoAsyncTask;
import ngovanquan_803656.datn.model.CDSD_ett;
import ngovanquan_803656.datn.model.HoSo_ett;
import ngovanquan_803656.datn.model.HopHoSo_ett;
import ngovanquan_803656.datn.model.NgonNgu_ett;
import ngovanquan_803656.datn.model.THBQ_ett;
import ngovanquan_803656.datn.model.TTVL_ett;
import ngovanquan_803656.datn.utils.Constants;
import ngovanquan_803656.datn.utils.Helper;

public class AddNewAndUpdateHoSoActivity extends Activity implements View.OnClickListener{

    TextView txt_header;
    EditText txt_hoSoSo, txt_mucLucSo, txt_KHTT, txt_tieuDeHS, txt_chuGiai, txt_TGBD, txt_TGKT, txt_butTich, txt_SLT;
    Spinner spn_maHopHS, spn_ngonNgu, spn_THBQ, spn_CDSD, spn_TTVL;
    Button btn_addNew, btn_update, btn_cancel;
    ImageView btn_close;
    String MaPhong, TenPhong, MaHop, TenHop;
    HoSo_ett hoSo_ett;
    ArrayList<HopHoSo_ett> arrHopHoSo = new ArrayList<>();
    SpinnerHopHoSoAdapter spinnerHopHoSoAdapter;
    ArrayList<NgonNgu_ett> arrNgonNgu = new ArrayList<>();
    SpinnerNgonNguAdapter spinnerNgonNguAdapter;
    ArrayList<THBQ_ett> arrTHBQ = new ArrayList<>();
    SpinnerTHBQAdapter spinnerTHBQAdapter;
    ArrayList<CDSD_ett> arrCDSD = new ArrayList<>();
    SpinnerCDSDAdapter spinnerCDSDAdapter;
    ArrayList<TTVL_ett> arrTTVL = new ArrayList<>();
    SpinnerTTVLAdapter spinnerTTVLAdapter;

    String MucLucSo, HoSoSo, KHTT, TieuDeHS, ChuGiai, ThoiGianBatDau, ThoigianKetThuc, ButTich, SoLuongTo, MaHopHS;
//    long MaHopHS;
    int MaNN, MaTHBQ, MaCDSD, MaTTVL;

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
        setContentView(R.layout.activity_add_new_and_update_ho_so);
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
            btn_addNew.setVisibility(View.VISIBLE);
            btn_update.setVisibility(View.GONE);
        } else {
            bundle = intent.getBundleExtra("Data1");
            MaPhong = bundle.getString("MaPhong");
            TenPhong = bundle.getString("TenPhong");
            MaHop = bundle.getString("MaHop");
            TenHop = bundle.getString("TenHop");
            hoSo_ett = (HoSo_ett) bundle.getSerializable("HoSo_ett");

            txt_hoSoSo.setText(hoSo_ett.getIDShow());
            txt_hoSoSo.setEnabled(false);
            txt_mucLucSo.setText(hoSo_ett.getMucLucSo());
            txt_KHTT.setText(hoSo_ett.getKHTT());
            txt_tieuDeHS.setText(hoSo_ett.getTieuDeHs());
            txt_chuGiai.setText(hoSo_ett.getChuGiai());
            txt_TGBD.setText(hoSo_ett.getThoiGianBatDau());
            txt_TGKT.setText(hoSo_ett.getThoiGianKetThuc());
            txt_butTich.setText(hoSo_ett.getButTich());
            txt_SLT.setText(hoSo_ett.getSoLuongTo());
            btn_addNew.setVisibility(View.GONE);
            btn_update.setVisibility(View.VISIBLE);

        }
        setUpSpinner();
        addEvents();

        txt_header.setText(TenPhong + " > " + "Hộp " + TenHop + " > " + "Thêm mới Hồ sơ");
    }

    private void addControls() {
        txt_header = (TextView) findViewById(R.id.txt_header);
        txt_hoSoSo = (EditText) findViewById(R.id.txt_hoSoSo);
        txt_mucLucSo = (EditText) findViewById(R.id.txt_mucLucSo);
        txt_KHTT = (EditText) findViewById(R.id.txt_KHTT);
        txt_tieuDeHS = (EditText) findViewById(R.id.txt_tieuDeHS);
        txt_chuGiai = (EditText) findViewById(R.id.txt_chuGiai);
        txt_TGBD = (EditText) findViewById(R.id.txt_TGBD);
        txt_TGKT = (EditText) findViewById(R.id.txt_TGKT);
        txt_butTich = (EditText) findViewById(R.id.txt_butTich);
        txt_SLT = (EditText) findViewById(R.id.txt_SLT);
        spn_maHopHS = (Spinner) findViewById(R.id.spn_maHopHS);
        spn_ngonNgu = (Spinner) findViewById(R.id.spn_ngonNgu);
        spn_THBQ = (Spinner) findViewById(R.id.spn_THBQ);
        spn_CDSD = (Spinner) findViewById(R.id.spn_CDSD);
        spn_TTVL = (Spinner) findViewById(R.id.spn_TTVL);
        btn_addNew = (Button) findViewById(R.id.btn_AddNew);
        btn_update = (Button) findViewById(R.id.btn_Update);
        btn_cancel = (Button) findViewById(R.id.btn_Cancel);
        btn_close = (ImageView) findViewById(R.id.btn_close);
    }


    private void setUpSpinner() {
        try {
            arrHopHoSo = new SearchHopHoSoAsyncTask(this, Constants.FUNCTION_SEARCH, "", "", "HopHoSoByPhong", MaPhong, 0, 0).execute().get();
            spinnerHopHoSoAdapter = new SpinnerHopHoSoAdapter(this, R.layout.list_item_spinner, arrHopHoSo);
            spn_maHopHS.setAdapter(spinnerHopHoSoAdapter);

            arrNgonNgu = new SearchNgonNguAsyncTask(this, "ID", "", 0, 0).execute().get();
            spinnerNgonNguAdapter = new SpinnerNgonNguAdapter(this, R.layout.list_item_spinner, arrNgonNgu);
            spn_ngonNgu.setAdapter(spinnerNgonNguAdapter);

            arrCDSD = new SearchCDSDAsyncTask(this, "ID", "", 0, 0).execute().get();
            spinnerCDSDAdapter = new SpinnerCDSDAdapter(this, R.layout.list_item_spinner, arrCDSD);
            spn_CDSD.setAdapter(spinnerCDSDAdapter);

            arrTHBQ = new SearchTHBQAsyncTask(this, "ID", "", 0, 0).execute().get();
            spinnerTHBQAdapter = new SpinnerTHBQAdapter(this, R.layout.list_item_spinner, arrTHBQ);
            spn_THBQ.setAdapter(spinnerTHBQAdapter);

            arrTTVL = new SearchTTVLAsyncTask(this, "ID", "", 0, 0).execute().get();
            spinnerTTVLAdapter = new SpinnerTTVLAdapter(this, R.layout.list_item_spinner, arrTTVL);
            spn_TTVL.setAdapter(spinnerTTVLAdapter);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void addEvents() {
        spn_maHopHS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MaHopHS = String.valueOf(arrHopHoSo.get(position).getMaHopHS());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                if (!MaHop.equals("")) {
                    for (int i = 0; i < arrHopHoSo.size(); i++) {
                        if (arrHopHoSo.get(i).getMaHopHS() == Long.parseLong(MaHop)) {
                            spn_maHopHS.setSelection(i);
                            MaHopHS = MaHop;
                        }
                    }
                } else {
                    MaHopHS = String.valueOf(arrHopHoSo.get(0).getMaHopHS());
                }

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

        spn_THBQ.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MaTHBQ = (int) arrTHBQ.get(position).getMaTHBQ();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                MaTHBQ = (int) arrTHBQ.get(0).getMaTHBQ();
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

        btn_addNew.setOnClickListener(this);
        btn_update.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        btn_close.setOnClickListener(this);
        txt_TGBD.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    Helper.showDatePickerDialog(AddNewAndUpdateHoSoActivity.this, txt_TGBD);
            }
        });

        txt_TGKT.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    Helper.showDatePickerDialog(AddNewAndUpdateHoSoActivity.this, txt_TGKT);
            }
        });
    }

    private void setValueDefaultForControl() {
        txt_mucLucSo.setText("");
        txt_hoSoSo.setText("");
        txt_KHTT.setText("");
        txt_tieuDeHS.setText("");
        txt_chuGiai.setText("");
        txt_TGBD.setText("");
        txt_TGKT.setText("");
        txt_butTich.setText("");
        txt_SLT.setText("");
        spn_ngonNgu.setSelection(0);
        spn_TTVL.setSelection(0);
        spn_CDSD.setSelection(0);
        spn_THBQ.setSelection(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_AddNew:
                MucLucSo = txt_mucLucSo.getText().toString();
                HoSoSo = txt_hoSoSo.getText().toString();
                KHTT = txt_KHTT.getText().toString();
                TieuDeHS = txt_tieuDeHS.getText().toString();
                ChuGiai = txt_chuGiai.getText().toString();
                ThoiGianBatDau = txt_TGBD.getText().toString();
                ThoigianKetThuc = txt_TGKT.getText().toString();
                ButTich = txt_butTich.getText().toString();
                SoLuongTo = txt_SLT.getText().toString();
                if (!HoSoSo.equals("")) {
                    try {
                        hoSo_ett = new AddNewAndUpdateHoSo(this, Constants.FUNCTION_ADD_NEW, MucLucSo, HoSoSo, KHTT, TieuDeHS, ChuGiai, ThoiGianBatDau,
                                ThoigianKetThuc, ButTich, SoLuongTo, MaHopHS, MaNN, MaTHBQ, MaCDSD, MaTTVL).execute().get();
                        Toast.makeText(AddNewAndUpdateHoSoActivity.this, hoSo_ett.getErrDesc(), Toast.LENGTH_SHORT).show();
                        MenuPhongAdapter.arrHoSo = new SearchHoSoAsyncTask(this, "HoSoByHopHoSo", MaHop, MenuPhongAdapter.pageHoso, Constants.NUM_ROW_PER_PAGE).execute().get();
                        MenuPhongAdapter.gridHoSoAdapter = new GridHoSoAdapter(this, R.layout.grid_item, MenuPhongAdapter.arrHoSo);
                        MenuPhongAdapter.gridView.setAdapter(MenuPhongAdapter.gridHoSoAdapter);
                        Helper.setGridViewHeightBasedOnChildren(MenuPhongAdapter.gridView, 2);
                        MenuPhongAdapter.total_record = MenuPhongAdapter.arrHoSo.get(0).getTotal_record();
                        MenuPhongAdapter.pageLoad(MenuPhongAdapter.type, MenuPhongAdapter.pageHoso);
                        Log.e("ErrDesc", hoSo_ett.getErrDesc());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    setValueDefaultForControl();
                } else {
                    Toast.makeText(AddNewAndUpdateHoSoActivity.this, getString(R.string.message_input), Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btn_Update:
                MucLucSo = txt_mucLucSo.getText().toString();
                HoSoSo = txt_hoSoSo.getText().toString();
                KHTT = txt_KHTT.getText().toString();
                TieuDeHS = txt_tieuDeHS.getText().toString();
                ChuGiai = txt_chuGiai.getText().toString();
                ThoiGianBatDau = txt_TGBD.getText().toString();
                ThoigianKetThuc = txt_TGKT.getText().toString();
                ButTich = txt_butTich.getText().toString();
                SoLuongTo = txt_SLT.getText().toString();
                if (!HoSoSo.equals("")) {
                    try {
                        hoSo_ett = new AddNewAndUpdateHoSo(this, Constants.FUNCTION_UPDATE, MucLucSo, HoSoSo, KHTT, TieuDeHS, ChuGiai, ThoiGianBatDau,
                                ThoigianKetThuc, ButTich, SoLuongTo, MaHopHS, MaNN, MaTHBQ, MaCDSD, MaTTVL).execute().get();
                        MenuPhongAdapter.arrHoSo = new SearchHoSoAsyncTask(this, "HoSoByHopHoSo", MaHop, MenuPhongAdapter.pageHoso, Constants.NUM_ROW_PER_PAGE).execute().get();
                        MenuPhongAdapter.gridHoSoAdapter = new GridHoSoAdapter(this, R.layout.grid_item, MenuPhongAdapter.arrHoSo);
                        MenuPhongAdapter.gridView.setAdapter(MenuPhongAdapter.gridHoSoAdapter);
                        Helper.setGridViewHeightBasedOnChildren(MenuPhongAdapter.gridView, 2);
                        MenuPhongAdapter.total_record = MenuPhongAdapter.arrHoSo.get(0).getTotal_record();
                        MenuPhongAdapter.pageLoad(MenuPhongAdapter.type, MenuPhongAdapter.pageHoso);
                        Toast.makeText(AddNewAndUpdateHoSoActivity.this, hoSo_ett.getErrDesc(), Toast.LENGTH_SHORT).show();
                        Log.e("ErrDesc", hoSo_ett.getErrDesc());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
//                    setValueDefaultForControl();
                } else {
                    Toast.makeText(AddNewAndUpdateHoSoActivity.this, getString(R.string.message_input), Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btn_Cancel:
                setValueDefaultForControl();
                break;
            case R.id.btn_close:
                finish();
                break;
        }
    }
}
