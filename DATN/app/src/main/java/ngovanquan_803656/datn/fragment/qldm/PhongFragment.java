package ngovanquan_803656.datn.fragment.qldm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ngovanquan_803656.datn.R;
import ngovanquan_803656.datn.adapter.PhongAdapter;
import ngovanquan_803656.datn.adapter.SpinnerCQLTAdapter;
import ngovanquan_803656.datn.adapter.SpinnerNgonNguAdapter;
import ngovanquan_803656.datn.asynctask.qldm.SearchNgonNguAsyncTask;
import ngovanquan_803656.datn.asynctask.qldm.SearchCQLTAsyncTask;
import ngovanquan_803656.datn.asynctask.qllt.AddNewAndUpdatePhongAsyncTask;
import ngovanquan_803656.datn.asynctask.qllt.SearchPhongAsyncTask;
import ngovanquan_803656.datn.model.CQLT_ett;
import ngovanquan_803656.datn.model.NgonNgu_ett;
import ngovanquan_803656.datn.model.Phong_ett;
import ngovanquan_803656.datn.utils.ConnectionDetector;
import ngovanquan_803656.datn.utils.Constants;
import ngovanquan_803656.datn.utils.Helper;

/**
 * Created by ngoquan on 5/11/2016.
 */
public class PhongFragment extends Fragment implements View.OnClickListener{

    Button btn_AddNew, btn_Cancel, btn_Update, btn_Search;
    EditText txt_MaPhong, txt_TenPhong, txt_TSTL, txt_STLCCL, txt_STLDCL, txt_CCTC, txt_TGTL, txt_CNTL, txt_TGNTL, txt_LBSBH,
    txt_LSPT, txt_GhiChu, txt_SearchValue;
    Spinner spn_CQLT, spn_NgonNgu, spn_SearchType;
    ArrayList<CQLT_ett> arrCQLT = new ArrayList<>();
    SpinnerCQLTAdapter spinnerCQLTAdapter;
    ArrayList<NgonNgu_ett> arrNN = new ArrayList<>();
    SpinnerNgonNguAdapter spinnerNgonNguAdapter;
    ArrayAdapter<String> adapter;
    ListView lv_p;
    String MaCQLT, TenPhong, LichSuHinhThanh, ThoiGianTaiLieu, CacNhomTaiLieu, ThoiGianNhapTaiLieu, CongCuTraCuu, LapBanSaoBaoHiem, GhiChu;
    long MaPhong, MaNN;
    int TongSoTaiLieu, SoTaiLieuDaChinhLy, SoTaiLieuChuaChinhLy;
    String search_type, search_val;
    Phong_ett phong_ett;
    ArrayList<Phong_ett> arrData = new ArrayList<>();
    PhongAdapter phongAdapter;
    LinearLayout ll_button;
    int i, n, total_record;
    int page_current = 1;
    ConnectionDetector cd;
    public PhongFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_phong, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        cd = new ConnectionDetector(getActivity());
        if (!cd.isConnectingToInternet()) {
            Helper.notificationDialog(getActivity(), "No internert connection", "You don't have internet connection", false);
            return;
        }
        addControls();
        try {
            arrData = new SearchPhongAsyncTask(getActivity(), "ID", "", page_current).execute().get();
            phongAdapter = new PhongAdapter(getActivity(), R.layout.list_item_cqlt, arrData);
            lv_p.setAdapter(phongAdapter);
            Helper.setListViewHeightBasedOnChildren(lv_p);

            arrCQLT = new SearchCQLTAsyncTask(getActivity(), "ID", "", 0, 0).execute().get();
            spinnerCQLTAdapter = new SpinnerCQLTAdapter(getActivity(), R.layout.list_item_spinner, arrCQLT);
            spn_CQLT.setAdapter(spinnerCQLTAdapter);

            arrNN = new SearchNgonNguAsyncTask(getActivity(), "ID", "", 0, 0).execute().get();
            spinnerNgonNguAdapter = new SpinnerNgonNguAdapter(getActivity(), R.layout.list_item_spinner, arrNN);
            spn_NgonNgu.setAdapter(spinnerNgonNguAdapter);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.search_phong));
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spn_SearchType.setAdapter(adapter);
        createButton();
        addEvents();
    }

    private void addControls() {
        btn_AddNew = (Button) getView().findViewById(R.id.btn_AddNew);
        btn_Update = (Button) getView().findViewById(R.id.btn_Update);
        btn_Search = (Button) getView().findViewById(R.id.btn_Search);
        btn_Cancel = (Button) getView().findViewById(R.id.btn_Cancel);
        txt_MaPhong = (EditText) getView().findViewById(R.id.txt_MaPhong);
        txt_TenPhong = (EditText) getView().findViewById(R.id.txt_TenPhong);
        txt_TSTL = (EditText) getView().findViewById(R.id.txt_TSTL);
        txt_STLCCL = (EditText) getView().findViewById(R.id.txt_STLCCL);
        txt_STLDCL = (EditText) getView().findViewById(R.id.txt_STLDCL);
        txt_CCTC = (EditText) getView().findViewById(R.id.txt_CCTC);
        txt_TGTL = (EditText) getView().findViewById(R.id.txt_TGTL);
        txt_CNTL = (EditText) getView().findViewById(R.id.txt_CNTL);
        txt_TGNTL = (EditText) getView().findViewById(R.id.txt_TGNTL);
        txt_LBSBH = (EditText) getView().findViewById(R.id.txt_LBSBH);
        txt_LSPT = (EditText) getView().findViewById(R.id.txt_LSPT);
        txt_GhiChu = (EditText) getView().findViewById(R.id.txt_GhiChu);
        txt_SearchValue = (EditText) getView().findViewById(R.id.txt_SearchValue);
        spn_CQLT = (Spinner) getView().findViewById(R.id.spn_CQLT);
        spn_NgonNgu = (Spinner) getView().findViewById(R.id.spn_NgonNgu);
        spn_SearchType = (Spinner) getView().findViewById(R.id.spn_SearchType);
        lv_p = (ListView) getView().findViewById(R.id.lv_p);
        ll_button = (LinearLayout) getView().findViewById(R.id.ll_button);

    }

    private void setValueForControl() {
        txt_MaPhong.setText("");
        txt_TenPhong.setText("");
        txt_TSTL.setText("");
        txt_STLCCL.setText("");
        txt_STLDCL.setText("");
        txt_CCTC.setText("");
        txt_TGTL.setText("");
        txt_CNTL.setText("");
        txt_TGNTL.setText("");
        txt_LBSBH.setText("");
        txt_LSPT.setText("");
        txt_GhiChu.setText("");
        spn_CQLT.setSelection(0);
        spn_NgonNgu.setSelection(0);
    }

    private void addEvents() {
        btn_AddNew.setOnClickListener(this);
        btn_Update.setOnClickListener(this);
        btn_Search.setOnClickListener(this);
        btn_Cancel.setOnClickListener(this);
        spn_SearchType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        search_type = "ID";
                        break;
                    case 1:
                        search_type = "Name";
                        break;
                    default:
                        search_type = "ID";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                search_type = "ID";
            }
        });
        spn_CQLT.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MaCQLT = arrCQLT.get(position).getMaCQLT();
//                Log.e("MaCQLT", MaCQLT);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                MaCQLT = arrCQLT.get(0).getMaCQLT();
            }
        });
        spn_NgonNgu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MaNN = arrNN.get(position).getMaNN();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                MaNN = arrNN.get(0).getMaNN();
            }
        });
        lv_p.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
//        txt_TGTL.setOnClickListener(this);
//        txt_TGNTL.setOnClickListener(this);
        txt_TGTL.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    Helper.showDatePickerDialog(getActivity(), txt_TGTL);
            }
        });
        txt_TGNTL.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    Helper.showDatePickerDialog(getActivity(), txt_TGNTL);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_AddNew:
                if (!txt_TenPhong.getText().toString().equals("")) {
                    TenPhong = txt_TenPhong.getText().toString();
                    LichSuHinhThanh = txt_LSPT.getText().toString();
                    ThoiGianTaiLieu = txt_TGTL.getText().toString();
                    TongSoTaiLieu = Integer.parseInt(txt_TSTL.getText().toString());
                    SoTaiLieuDaChinhLy = Integer.parseInt(txt_STLDCL.getText().toString());
                    SoTaiLieuChuaChinhLy = Integer.parseInt(txt_STLCCL.getText().toString());
                    CacNhomTaiLieu = txt_CNTL.getText().toString();
                    ThoiGianNhapTaiLieu = txt_TGNTL.getText().toString();
                    CongCuTraCuu = txt_CCTC.getText().toString();
                    LapBanSaoBaoHiem = txt_LBSBH.getText().toString();
                    GhiChu = txt_GhiChu.getText().toString();
                    try {
                        phong_ett = new AddNewAndUpdatePhongAsyncTask(getActivity(), Constants.FUNCTION_ADD_NEW, 0, MaCQLT,
                                TenPhong, LichSuHinhThanh, ThoiGianTaiLieu, TongSoTaiLieu, SoTaiLieuDaChinhLy, SoTaiLieuChuaChinhLy,
                                CacNhomTaiLieu, MaNN, ThoiGianNhapTaiLieu, CongCuTraCuu, LapBanSaoBaoHiem, GhiChu).execute().get();
                        arrData = new SearchPhongAsyncTask(getActivity(), "ID", "", page_current).execute().get();
                        phongAdapter = new PhongAdapter(getActivity(), R.layout.list_item_cqlt, arrData);
                        lv_p.setAdapter(phongAdapter);
                        Helper.setListViewHeightBasedOnChildren(lv_p);
                        Toast.makeText(getActivity(), phong_ett.getErrDesc(), Toast.LENGTH_SHORT).show();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    setValueForControl();
                } else
                    Toast.makeText(getActivity(), getResources().getString(R.string.message_input), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_Update:
                if (!txt_TenPhong.getText().toString().equals("")) {
                    MaPhong = Long.parseLong(txt_MaPhong.getText().toString());
                    TenPhong = txt_TenPhong.getText().toString();
                    LichSuHinhThanh = txt_LSPT.getText().toString();
                    ThoiGianTaiLieu = txt_TGTL.getText().toString();
                    TongSoTaiLieu = Integer.parseInt(txt_TSTL.getText().toString());
                    SoTaiLieuDaChinhLy = Integer.parseInt(txt_STLDCL.getText().toString());
                    SoTaiLieuChuaChinhLy = Integer.parseInt(txt_STLCCL.getText().toString());
                    CacNhomTaiLieu = txt_CNTL.getText().toString();
                    ThoiGianNhapTaiLieu = txt_TGNTL.getText().toString();
                    CongCuTraCuu = txt_CCTC.getText().toString();
                    LapBanSaoBaoHiem = txt_LBSBH.getText().toString();
                    GhiChu = txt_GhiChu.getText().toString();
                    try {
                        phong_ett = new AddNewAndUpdatePhongAsyncTask(getActivity(), Constants.FUNCTION_UPDATE, MaPhong, MaCQLT,
                                TenPhong, LichSuHinhThanh, ThoiGianTaiLieu, TongSoTaiLieu, SoTaiLieuDaChinhLy, SoTaiLieuChuaChinhLy,
                                CacNhomTaiLieu, MaNN, ThoiGianNhapTaiLieu, CongCuTraCuu, LapBanSaoBaoHiem, GhiChu).execute().get();
                        arrData = new SearchPhongAsyncTask(getActivity(), "ID", "", page_current).execute().get();
                        phongAdapter = new PhongAdapter(getActivity(), R.layout.list_item_cqlt, arrData);
                        lv_p.setAdapter(phongAdapter);
                        Helper.setListViewHeightBasedOnChildren(lv_p);
                        Toast.makeText(getActivity(), phong_ett.getErrDesc(), Toast.LENGTH_SHORT).show();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    setValueForControl();
                    btn_AddNew.setVisibility(View.VISIBLE);
                    btn_Update.setVisibility(View.GONE);
                } else
                    Toast.makeText(getActivity(), getResources().getString(R.string.message_input), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_Search:
                search_val = txt_SearchValue.getText().toString();
                try {
                    arrData = new SearchPhongAsyncTask(getActivity(), search_type, search_val, 1).execute().get();
                    phongAdapter = new PhongAdapter(getActivity(), R.layout.list_item_cqlt, arrData);
                    lv_p.setAdapter(phongAdapter);
                    Helper.setListViewHeightBasedOnChildren(lv_p);
                    total_record = arrData.get(0).getTotal_record();
                    n = (int) Math.ceil((double)total_record / Constants.NUM_ROW_PER_PAGE);
                    if (n <= 1)
                        ll_button.setVisibility(View.GONE);
                    else
                        ll_button.setVisibility(View.VISIBLE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                txt_SearchValue.setText("");
                break;
            case R.id.btn_Cancel:
                setValueForControl();
                btn_AddNew.setVisibility(View.VISIBLE);
                btn_Update.setVisibility(View.GONE);
                break;
//            case R.id.txt_TGTL:
//                Helper.showDatePickerDialog(getActivity(), txt_TGTL);
//                break;
//
//            case R.id.txt_TGNTL:
//                Helper.showDatePickerDialog(getActivity(), txt_TGNTL);
//                break;
        }
    }
    private void createButton() {
//        Log.e("Total_record", total_record + "");
        n = (int) Math.ceil((double) total_record / Constants.NUM_ROW_PER_PAGE);
//        Log.e("i", i + "");
        if ( n > 1) {
            ll_button.setVisibility(View.VISIBLE);
            for (i = 1; i <= n; i++) {
                final Button b = new Button(getActivity());
                b.setText(i + "");
                b.setWidth(15);
                b.setHeight(15);
                ll_button.addView(b);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        page_current = Integer.parseInt(b.getText().toString());
                        Log.e("page", page_current + "");
                        try {
                            arrData = new SearchPhongAsyncTask(getActivity(), "ID", "", page_current).execute().get();
                            phongAdapter = new PhongAdapter(getActivity(), R.layout.list_item_cqlt, arrData);
                            lv_p.setAdapter(phongAdapter);
                            Helper.setListViewHeightBasedOnChildren(lv_p);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        } else {
            ll_button.setVisibility(View.GONE);
        }
    }
}
