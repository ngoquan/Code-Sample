package ngovanquan_803656.datn.fragment.qldm;

import android.os.Bundle;
import android.os.StrictMode;
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
import ngovanquan_803656.datn.adapter.CheDoSuDungAdapter;
import ngovanquan_803656.datn.asynctask.qldm.AddNewCDSDAsyncTask;
import ngovanquan_803656.datn.asynctask.qldm.SearchCDSDAsyncTask;
import ngovanquan_803656.datn.asynctask.qldm.UpdateCDSDAsyncTask;
import ngovanquan_803656.datn.model.CDSD_ett;
import ngovanquan_803656.datn.utils.Constants;
import ngovanquan_803656.datn.utils.Helper;

/**
 * Created by ngoquan on 4/17/2016.
 */
public class CheDoSuDungFragment extends Fragment implements View.OnClickListener {
    Button btn_AddNew, btn_Cancel, btn_Update, btn_Search;
    EditText txt_TenCDSD, txt_STT, txt_GhiChu, txt_MaCDSD, txt_SearchValue;
    Spinner spn_SearchType;
    CheckBox cb_Active;
    ArrayAdapter<String> adapter;
    ListView lv_CDSD;

    String TenCDSD, GhiChu;
    long MaCDSD;
    int STT;
    boolean Active;
    CDSD_ett cdsd_ett;
    ArrayList<CDSD_ett> arrData = new ArrayList<>();
    CheDoSuDungAdapter cheDoSuDungAdapter;
    String search_type, search_val;
    LinearLayout ll_button;
    int i, n, total_record;
    int page_current = 1;
    public CheDoSuDungFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_che_do_su_dung, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //thiết lập Permit để kết nối internet
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        addControls();
        try {
            arrData = new SearchCDSDAsyncTask(getActivity(), "ID", "", page_current, Constants.NUM_ROW_PER_PAGE).execute().get();
            cheDoSuDungAdapter = new CheDoSuDungAdapter(getActivity(), R.layout.list_item_dtc, arrData);
            lv_CDSD.setAdapter(cheDoSuDungAdapter);
            Helper.setListViewHeightBasedOnChildren(lv_CDSD);
            total_record = arrData.get(0).getTotal_record();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.search_CDSD));
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
        txt_MaCDSD = (EditText) getView().findViewById(R.id.txt_MaCDSD);
        txt_TenCDSD = (EditText) getView().findViewById(R.id.txt_TenCDSD);
        txt_GhiChu = (EditText) getView().findViewById(R.id.txt_GhiChu);
        txt_STT = (EditText) getView().findViewById(R.id.txt_STT);
        txt_SearchValue = (EditText) getView().findViewById(R.id.txt_SearchValue);
        cb_Active = (CheckBox) getView().findViewById(R.id.cb_Active);
        spn_SearchType = (Spinner) getView().findViewById(R.id.spn_SearchType);
        lv_CDSD = (ListView) getView().findViewById(R.id.lv_CDSD);
        ll_button = (LinearLayout) getView().findViewById(R.id.ll_button);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_AddNew:
                if (txt_TenCDSD.getText().toString().equals("") == false) {
                    TenCDSD = txt_TenCDSD.getText().toString();
                    GhiChu = txt_GhiChu.getText().toString();
                    STT = Integer.parseInt(txt_STT.getText().toString());
                    if (cb_Active.isChecked()) {
                        Active = true;
                    } else {
                        Active = false;
                    }
                    try {
                        cdsd_ett = new AddNewCDSDAsyncTask(getActivity(), TenCDSD, GhiChu, STT, Active).execute().get();
                        arrData = new SearchCDSDAsyncTask(getActivity(), "ID", "", page_current, Constants.NUM_ROW_PER_PAGE).execute().get();
                        cheDoSuDungAdapter = new CheDoSuDungAdapter(getActivity(), R.layout.list_item_dtc, arrData);
                        lv_CDSD.setAdapter(cheDoSuDungAdapter);
                        Helper.setListViewHeightBasedOnChildren(lv_CDSD);
                        total_record = arrData.get(0).getTotal_record();
                        Toast.makeText(getActivity(), cdsd_ett.getErrDesc(), Toast.LENGTH_SHORT).show();
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
                if (txt_TenCDSD.getText().toString().equals("") == false) {
                    MaCDSD = Long.parseLong(txt_MaCDSD.getText().toString());
                    TenCDSD = txt_TenCDSD.getText().toString();
                    GhiChu = txt_GhiChu.getText().toString();
                    STT = Integer.parseInt(txt_STT.getText().toString());
                    if (cb_Active.isChecked()) {
                        Active = true;
                    } else {
                        Active = false;
                    }
                    try {
                        cdsd_ett = new UpdateCDSDAsyncTask(getActivity(), MaCDSD, TenCDSD, GhiChu, STT, Active).execute().get();
                        arrData = new SearchCDSDAsyncTask(getActivity(), "ID", "", page_current, Constants.NUM_ROW_PER_PAGE).execute().get();
                        cheDoSuDungAdapter = new CheDoSuDungAdapter(getActivity(), R.layout.list_item_dtc, arrData);
                        lv_CDSD.setAdapter(cheDoSuDungAdapter);
                        Helper.setListViewHeightBasedOnChildren(lv_CDSD);
                        total_record = arrData.get(0).getTotal_record();
                        Toast.makeText(getActivity(), cdsd_ett.getErrDesc(), Toast.LENGTH_SHORT).show();
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
                    arrData = new SearchCDSDAsyncTask(getActivity(), search_type, search_val, 1, Constants.NUM_ROW_PER_PAGE).execute().get();
                    cheDoSuDungAdapter = new CheDoSuDungAdapter(getActivity(), R.layout.list_item_dtc, arrData);
                    lv_CDSD.setAdapter(cheDoSuDungAdapter);
                    Helper.setListViewHeightBasedOnChildren(lv_CDSD);
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
                break;
        }
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
                Toast.makeText(getActivity(), search_type, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                search_type = "ID";
            }
        });

        lv_CDSD.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
    }

    private void setValueForControl() {
        txt_TenCDSD.setText("");
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
                        new SearchCDSDAsyncTask(getActivity(), "ID", "", page_current, Constants.NUM_ROW_PER_PAGE).execute();
                    }
                });
            }
        } else {
            ll_button.setVisibility(View.GONE);
        }
    }
}
