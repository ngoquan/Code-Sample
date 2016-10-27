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
import ngovanquan_803656.datn.adapter.NhomNguoiDungAdapter;
import ngovanquan_803656.datn.asynctask.qtht.AddNewAndUpdateNhomNguoiDungAsyncTask;
import ngovanquan_803656.datn.asynctask.qtht.SearchNhomNguoiDungAsyncTask;
import ngovanquan_803656.datn.model.NhomNguoiDung_ett;
import ngovanquan_803656.datn.utils.ConnectionDetector;
import ngovanquan_803656.datn.utils.Constants;
import ngovanquan_803656.datn.utils.Helper;

/**
 * Created by NGOQUAN on 5/26/2016.
 */
public class NhomNguoiDungFragment extends Fragment implements View.OnClickListener{
    public static Button btn_addNew, btn_cancel, btn_update, btn_search;
    public static EditText txt_tenNhom, txt_ghiChu, txt_maNhom, txt_searchValue;
    Spinner spn_searchType;
    public static CheckBox cb_active;
    ArrayAdapter<String> adapter;
    public static ListView lv_nhomND;

    String TenNhom, GhiChu;
    int MaNhom;
    boolean Active;
    NhomNguoiDung_ett nhomNguoiDung_ett;
    String search_type, search_val;
//    LinearLayout ll_button;
//    int i, n, total_record;
    public static int page_current = 1;
    public static ArrayList<NhomNguoiDung_ett> arrNND = new ArrayList<>();
    public static NhomNguoiDungAdapter nhomNguoiDungAdapter;
    ConnectionDetector cd;

    public NhomNguoiDungFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nhom_nguoi_dung, container, false);
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
            arrNND = new SearchNhomNguoiDungAsyncTask(getActivity(), "ID", "", page_current, Constants.NUM_ROW_PER_PAGE).execute().get();
            nhomNguoiDungAdapter = new NhomNguoiDungAdapter(getActivity(), R.layout.list_item_dtc, arrNND);
            lv_nhomND.setAdapter(nhomNguoiDungAdapter);
            Helper.setListViewHeightBasedOnChildren(lv_nhomND);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.search_nhomND));
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spn_searchType.setAdapter(adapter);
        addEvents();
    }


    private void addControls() {
        btn_addNew = (Button) getView().findViewById(R.id.btn_addNew);
        btn_update = (Button) getView().findViewById(R.id.btn_update);
        btn_search = (Button) getView().findViewById(R.id.btn_search);
        btn_cancel = (Button) getView().findViewById(R.id.btn_cancel);
        txt_maNhom = (EditText) getView().findViewById(R.id.txt_maNhom);
        txt_tenNhom = (EditText) getView().findViewById(R.id.txt_tenNhom);
        txt_ghiChu = (EditText) getView().findViewById(R.id.txt_ghiChu);
        txt_searchValue = (EditText) getView().findViewById(R.id.txt_searchValue);
        spn_searchType = (Spinner) getView().findViewById(R.id.spn_searchType);
        cb_active = (CheckBox) getView().findViewById(R.id.cb_active);
        lv_nhomND = (ListView) getView().findViewById(R.id.lv_nhomND);
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
                        search_type = "Name";
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

        lv_nhomND.setOnTouchListener(new View.OnTouchListener() {
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

    private void setValueForControls() {
        txt_maNhom.setText("");
        txt_tenNhom.setText("");
        txt_ghiChu.setText("");
        cb_active.setChecked(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_addNew:
                TenNhom = txt_tenNhom.getText().toString();
                GhiChu = txt_ghiChu.getText().toString();
                if (cb_active.isChecked())
                    Active = true;
                else
                    Active = false;
                if (!TenNhom.equals("")) {
                    try {
                        nhomNguoiDung_ett = new AddNewAndUpdateNhomNguoiDungAsyncTask(getActivity(), Constants.FUNCTION_ADD_NEW, 0,
                                TenNhom, GhiChu, Active).execute().get();
                        arrNND = new SearchNhomNguoiDungAsyncTask(getActivity(), "ID", "", page_current, Constants.NUM_ROW_PER_PAGE).execute().get();
                        nhomNguoiDungAdapter = new NhomNguoiDungAdapter(getActivity(), R.layout.list_item_dtc, arrNND);
                        lv_nhomND.setAdapter(nhomNguoiDungAdapter);
                        Helper.setListViewHeightBasedOnChildren(lv_nhomND);
                        Toast.makeText(getActivity(), nhomNguoiDung_ett.getErrDesc(), Toast.LENGTH_SHORT).show();
                        setValueForControls();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                } else
                    Toast.makeText(getActivity(), getString(R.string.message_input), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_update:
                MaNhom = Integer.parseInt(txt_maNhom.getText().toString());
                TenNhom = txt_tenNhom.getText().toString();
                GhiChu = txt_ghiChu.getText().toString();
                if (cb_active.isChecked())
                    Active = true;
                else
                    Active = false;
                if (!TenNhom.equals("")) {
                    try {
                        nhomNguoiDung_ett = new AddNewAndUpdateNhomNguoiDungAsyncTask(getActivity(), Constants.FUNCTION_UPDATE, MaNhom,
                                TenNhom, GhiChu, Active).execute().get();
                        arrNND = new SearchNhomNguoiDungAsyncTask(getActivity(), "ID", "", page_current, Constants.NUM_ROW_PER_PAGE).execute().get();
                        nhomNguoiDungAdapter = new NhomNguoiDungAdapter(getActivity(), R.layout.list_item_dtc, arrNND);
                        lv_nhomND.setAdapter(nhomNguoiDungAdapter);
                        Helper.setListViewHeightBasedOnChildren(lv_nhomND);
                        Toast.makeText(getActivity(), nhomNguoiDung_ett.getErrDesc(), Toast.LENGTH_SHORT).show();
                        setValueForControls();
                        btn_addNew.setVisibility(View.VISIBLE);
                        btn_update.setVisibility(View.GONE);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                } else
                    Toast.makeText(getActivity(), getString(R.string.message_input), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_search:
                search_val = txt_searchValue.getText().toString();
                try {
                    arrNND = new SearchNhomNguoiDungAsyncTask(getActivity(), search_type, search_val, page_current, Constants.NUM_ROW_PER_PAGE).execute().get();
                    nhomNguoiDungAdapter = new NhomNguoiDungAdapter(getActivity(), R.layout.list_item_dtc, arrNND);
                    lv_nhomND.setAdapter(nhomNguoiDungAdapter);
                    Helper.setListViewHeightBasedOnChildren(lv_nhomND);
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
