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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ngovanquan_803656.datn.R;
import ngovanquan_803656.datn.adapter.DoTinCayAdapter;
import ngovanquan_803656.datn.asynctask.qldm.AddNewDoTincayAsyncTask;
import ngovanquan_803656.datn.asynctask.qldm.SearchDoTinCayAsyncTask;
import ngovanquan_803656.datn.asynctask.qldm.UpdateDoTinCayAsyncTask;
import ngovanquan_803656.datn.model.DoTinCay_ett;
import ngovanquan_803656.datn.utils.ConnectionDetector;
import ngovanquan_803656.datn.utils.Constants;
import ngovanquan_803656.datn.utils.Helper;

/**
 * Created by ngoquan on 4/17/2016.
 */
public class DoTinCayFragment extends Fragment implements View.OnClickListener{
    Button btn_AddNew, btn_Cancel, btn_Update, btn_Search;
    EditText txt_TenDTC, txt_STT, txt_GhiChu, txt_MaDTC, txt_SearchValue;
    Spinner spn_SearchType;
    CheckBox cb_Active;
    ArrayAdapter<String> adapter;
    ListView lv_DTC;
    String TenDoTinCay, GhiChu;
    long MaDoTinCay;
    int STT;
    boolean Active;
    DoTinCay_ett doTinCay_ett;
    ArrayList<DoTinCay_ett> arrDTC = new ArrayList<>();
    DoTinCayAdapter doTinCayAdapter;
    String search_type, search_val;
    LinearLayout ll_button;
    int i, n, total_record;
    int page_current = 1;
    ConnectionDetector cd;
    public DoTinCayFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_do_tin_cay, container, false);
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
            arrDTC = new SearchDoTinCayAsyncTask(getActivity(), "ID", "", page_current, Constants.NUM_ROW_PER_PAGE).execute().get();
            doTinCayAdapter = new DoTinCayAdapter(getActivity(), R.layout.list_item_dtc, arrDTC);
            lv_DTC.setAdapter(doTinCayAdapter);
            Helper.setListViewHeightBasedOnChildren(lv_DTC);
            total_record = arrDTC.get(0).getTotal_record();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.search_DTC));
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
        txt_MaDTC = (EditText) getView().findViewById(R.id.txt_MaDTC);
        txt_TenDTC = (EditText) getView().findViewById(R.id.txt_TenDTC);
        txt_GhiChu = (EditText) getView().findViewById(R.id.txt_GhiChu);
        txt_STT = (EditText) getView().findViewById(R.id.txt_STT);
        txt_SearchValue = (EditText) getView().findViewById(R.id.txt_SearchValue);
        cb_Active = (CheckBox) getView().findViewById(R.id.cb_Active);
        spn_SearchType = (Spinner) getView().findViewById(R.id.spn_SearchType);
        lv_DTC = (ListView) getView().findViewById(R.id.lv_DTC);
        ll_button = (LinearLayout) getView().findViewById(R.id.ll_button);
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
                    case 2:
                        search_type = "Active";
                        break;
                    default:
                        search_type = "ID";
                        break;
                }
//                Toast.makeText(DoTinCayActivity.this, search_type, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                search_type = "ID";
            }
        });

        lv_DTC.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
    }

    private void setValueForControl() {
        txt_TenDTC.setText("");
        txt_GhiChu.setText("");
        txt_STT.setText("");
        cb_Active.setChecked(false);
    }
    private void createButton() {
//        Log.e("Total_record", total_record + "");
        n = (int) Math.ceil((double)total_record / Constants.NUM_ROW_PER_PAGE);
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
                        new SearchDoTinCayAsyncTask(getActivity(), "ID", "", page_current, Constants.NUM_ROW_PER_PAGE).execute();
                    }
                });
            }
        } else {
            ll_button.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_AddNew:
                if (txt_TenDTC.getText().toString().equals("") == false) {
                    TenDoTinCay = txt_TenDTC.getText().toString();
                    GhiChu = txt_GhiChu.getText().toString();
                    STT = Integer.parseInt(txt_STT.getText().toString());
                    if (cb_Active.isChecked()) {
                        Active = true;
                    } else {
                        Active = false;
                    }
                    try {
                        doTinCay_ett = new AddNewDoTincayAsyncTask(getActivity(), TenDoTinCay, GhiChu, STT, Active).execute().get();
                        arrDTC = new SearchDoTinCayAsyncTask(getActivity(), "ID", "", page_current, Constants.NUM_ROW_PER_PAGE).execute().get();
                        doTinCayAdapter = new DoTinCayAdapter(getActivity(), R.layout.list_item_dtc, arrDTC);
                        lv_DTC.setAdapter(doTinCayAdapter);
                        Helper.setListViewHeightBasedOnChildren(lv_DTC);
                        total_record = arrDTC.get(0).getTotal_record();
                        Toast.makeText(getActivity(), doTinCay_ett.getErrDesc(), Toast.LENGTH_SHORT).show();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    setValueForControl();
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.message_input), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_Update:
                if (txt_TenDTC.getText().toString().equals("") == false) {
                    MaDoTinCay = Long.parseLong(txt_MaDTC.getText().toString());
                    TenDoTinCay = txt_TenDTC.getText().toString();
                    GhiChu = txt_GhiChu.getText().toString();
                    STT = Integer.parseInt(txt_STT.getText().toString());
                    if (cb_Active.isChecked()) {
                        Active = true;
                    } else {
                        Active = false;
                    }
                    try {
                        doTinCay_ett = new UpdateDoTinCayAsyncTask(getActivity(), MaDoTinCay, TenDoTinCay, GhiChu, STT, Active).execute().get();
                        arrDTC = new SearchDoTinCayAsyncTask(getActivity(), "ID", "", page_current, Constants.NUM_ROW_PER_PAGE).execute().get();
                        doTinCayAdapter = new DoTinCayAdapter(getActivity(), R.layout.list_item_dtc, arrDTC);
                        lv_DTC.setAdapter(doTinCayAdapter);
                        Helper.setListViewHeightBasedOnChildren(lv_DTC);
                        total_record = arrDTC.get(0).getTotal_record();
                        Toast.makeText(getActivity(), doTinCay_ett.getErrDesc(), Toast.LENGTH_SHORT).show();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    setValueForControl();
                    btn_AddNew.setVisibility(View.VISIBLE);
                    btn_Update.setVisibility(View.GONE);
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.message_input), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_Search:
                search_val = txt_SearchValue.getText().toString();
                try {
                    arrDTC = new SearchDoTinCayAsyncTask(getActivity(), search_type, search_val, 1, Constants.NUM_ROW_PER_PAGE).execute().get();
                    doTinCayAdapter = new DoTinCayAdapter(getActivity(), R.layout.list_item_dtc, arrDTC);
                    lv_DTC.setAdapter(doTinCayAdapter);
                    Helper.setListViewHeightBasedOnChildren(lv_DTC);
                    total_record = arrDTC.get(0).getTotal_record();
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
                break;
        }
    }
}
