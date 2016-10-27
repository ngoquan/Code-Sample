package ngovanquan_803656.datn.fragment.qtht;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ngovanquan_803656.datn.R;
import ngovanquan_803656.datn.adapter.NguoiDungAdapter;
import ngovanquan_803656.datn.adapter.SpinnerNhomNguoiDungAdapter;
import ngovanquan_803656.datn.asynctask.qtht.AddNewUpdateAndDeleteNguoiDungAsyncTask;
import ngovanquan_803656.datn.asynctask.qtht.SearchNguoiDungAsyncTask;
import ngovanquan_803656.datn.asynctask.qtht.SearchNhomNguoiDungAsyncTask;
import ngovanquan_803656.datn.model.NhomNguoiDung_ett;
import ngovanquan_803656.datn.model.QLND_ett;
import ngovanquan_803656.datn.utils.ConnectionDetector;
import ngovanquan_803656.datn.utils.Constants;
import ngovanquan_803656.datn.utils.Helper;

/**
 * Created by NGOQUAN on 5/27/2016.
 */
public class NguoiDungFragment extends Fragment implements View.OnClickListener{

    public static Button btn_addNew, btn_cancel, btn_update, btn_search;
    public static EditText txt_tenDN, txt_ghiChu, txt_matKhau, txt_NLMK, txt_hoTen, txt_searchValue;
    public static Spinner spn_searchType, spn_nhomND;
    public static CheckBox cb_active;
    ArrayAdapter<String> adapter;
    public static ListView lv_nguoiDung;

    String LoginID, HoTen, MatKhau, NLMK, GhiChu;
    int MaNhom;
    boolean Active;
    QLND_ett qlnd_ett;
    String search_type, search_val;
    //    LinearLayout ll_button;
//    int i, n, total_record;
    public static int page_current = 1;
    public static ArrayList<QLND_ett> arrND = new ArrayList<>();
    public static NguoiDungAdapter nguoiDungAdapter;
    public static ArrayList<NhomNguoiDung_ett> arrNND = new ArrayList<>();
    public static SpinnerNhomNguoiDungAdapter spinnerNhomNguoiDungAdapter;
    ConnectionDetector cd;
    public NguoiDungFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nguoi_dung, container, false);
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
            arrNND = new SearchNhomNguoiDungAsyncTask(getActivity(), "ID", "", page_current, 0).execute().get();
            spinnerNhomNguoiDungAdapter = new SpinnerNhomNguoiDungAdapter(getActivity(), R.layout.list_item_spinner, arrNND);
            spn_nhomND.setAdapter(spinnerNhomNguoiDungAdapter);

            arrND = new SearchNguoiDungAsyncTask(getActivity(), "ID", "", page_current).execute().get();
            nguoiDungAdapter = new NguoiDungAdapter(getActivity(), R.layout.list_item_dtc, arrND);
            lv_nguoiDung.setAdapter(nguoiDungAdapter);
            Helper.setListViewHeightBasedOnChildren(lv_nguoiDung);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.search_ND));
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spn_searchType.setAdapter(adapter);
        addEvents();
    }

    private void addControls() {
        btn_addNew = (Button) getView().findViewById(R.id.btn_addNew);
        btn_update = (Button) getView().findViewById(R.id.btn_update);
        btn_search = (Button) getView().findViewById(R.id.btn_search);
        btn_cancel = (Button) getView().findViewById(R.id.btn_cancel);
        txt_tenDN = (EditText) getView().findViewById(R.id.txt_tenDN);
        txt_matKhau = (EditText) getView().findViewById(R.id.txt_matKhau);
        txt_NLMK = (EditText) getView().findViewById(R.id.txt_NLMK);
        txt_hoTen = (EditText) getView().findViewById(R.id.txt_hoTen);
        txt_ghiChu = (EditText) getView().findViewById(R.id.txt_ghiChu);
        txt_searchValue = (EditText) getView().findViewById(R.id.txt_searchValue);
        spn_searchType = (Spinner) getView().findViewById(R.id.spn_searchType);
        spn_nhomND = (Spinner) getView().findViewById(R.id.spn_nhomND);
        cb_active = (CheckBox) getView().findViewById(R.id.cb_active);
        lv_nguoiDung = (ListView) getView().findViewById(R.id.lv_nguoiDung);
    }

    private void setValueForControls() {
        txt_tenDN.setText("");
        txt_matKhau.setText("");
        txt_NLMK.setText("");
        txt_hoTen.setText("");
        txt_ghiChu.setText("");
        cb_active.setChecked(false);
        spn_nhomND.setSelection(0);
    }

    private void addEvents() {
        spn_searchType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        search_type = "ID";
                        break;
                    case 1:
                        search_type = "Nhom";
                        break;
                    case 2:
                        search_type = "Active";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                search_type = "ID";
            }
        });

        spn_nhomND.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MaNhom = arrNND.get(position).getMaNhom();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                MaNhom = arrNND.get(0).getMaNhom();
            }
        });

        lv_nguoiDung.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        btn_addNew.setOnClickListener(this);
        btn_update.setOnClickListener(this);
        btn_search.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_addNew:
                LoginID = txt_tenDN.getText().toString();
                HoTen = txt_hoTen.getText().toString();
                MatKhau = txt_matKhau.getText().toString();
                NLMK = txt_NLMK.getText().toString();
                GhiChu = txt_ghiChu.getText().toString();
                if (cb_active.isChecked())
                    Active = true;
                else
                    Active = false;
                if (!LoginID.equals("") && !MatKhau.equals("") && !NLMK.equals("")) {
                    if (MatKhau.equals(NLMK)) {
                        try {
                            qlnd_ett = new AddNewUpdateAndDeleteNguoiDungAsyncTask(getActivity(), Constants.FUNCTION_ADD_NEW, LoginID, HoTen, Helper.sha1(MatKhau),
                                    GhiChu, MaNhom, Active).execute().get();
                            arrND = new SearchNguoiDungAsyncTask(getActivity(), "ID", "", page_current).execute().get();
                            nguoiDungAdapter = new NguoiDungAdapter(getActivity(), R.layout.list_item_dtc, arrND);
                            lv_nguoiDung.setAdapter(nguoiDungAdapter);
                            Helper.setListViewHeightBasedOnChildren(lv_nguoiDung);
                            Toast.makeText(getActivity(), qlnd_ett.getErrDesc(), Toast.LENGTH_SHORT).show();
                            setValueForControls();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    } else {
                        txt_NLMK.requestFocus();
                        Toast.makeText(getActivity(), "Mật khẩu và nhập lại mật khẩu phải giống nhau...!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    txt_tenDN.requestFocus();
                    Toast.makeText(getActivity(), getString(R.string.message_input), Toast.LENGTH_SHORT).show();
                }


                break;

            case R.id.btn_update:
                LoginID = txt_tenDN.getText().toString();
                HoTen = txt_hoTen.getText().toString();
                MatKhau = txt_matKhau.getText().toString();
                NLMK = txt_NLMK.getText().toString();
                GhiChu = txt_ghiChu.getText().toString();
                if (cb_active.isChecked())
                    Active = true;
                else
                    Active = false;
                if (!LoginID.equals("") && !MatKhau.equals("") && !NLMK.equals("")) {
                    if (MatKhau.equals(NLMK)) {
                        try {
                            qlnd_ett = new AddNewUpdateAndDeleteNguoiDungAsyncTask(getActivity(), Constants.FUNCTION_UPDATE, LoginID, HoTen, Helper.sha1(MatKhau),
                                    GhiChu, MaNhom, Active).execute().get();
                            arrND = new SearchNguoiDungAsyncTask(getActivity(), "ID", "", page_current).execute().get();
                            nguoiDungAdapter = new NguoiDungAdapter(getActivity(), R.layout.list_item_dtc, arrND);
                            lv_nguoiDung.setAdapter(nguoiDungAdapter);
                            Helper.setListViewHeightBasedOnChildren(lv_nguoiDung);
                            Toast.makeText(getActivity(), qlnd_ett.getErrDesc(), Toast.LENGTH_SHORT).show();
                            setValueForControls();
                            btn_addNew.setVisibility(View.VISIBLE);
                            btn_update.setVisibility(View.GONE);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    } else {
                        txt_NLMK.requestFocus();
                        Toast.makeText(getActivity(), "Mật khẩu và nhập lại mật khẩu phải giống nhau...!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    txt_tenDN.requestFocus();
                    Toast.makeText(getActivity(), getString(R.string.message_input), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_search:
                search_val = txt_searchValue.getText().toString();
                try {
                    arrND = new SearchNguoiDungAsyncTask(getActivity(), search_type, search_val, page_current).execute().get();
                    nguoiDungAdapter = new NguoiDungAdapter(getActivity(), R.layout.list_item_dtc, arrND);
                    lv_nguoiDung.setAdapter(nguoiDungAdapter);
                    Helper.setListViewHeightBasedOnChildren(lv_nguoiDung);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                txt_searchValue.setText("");
                break;
            case R.id.btn_cancel:
                setValueForControls();
                btn_addNew.setVisibility(View.VISIBLE);
                btn_update.setVisibility(View.GONE);
                break;
        }
    }
}
