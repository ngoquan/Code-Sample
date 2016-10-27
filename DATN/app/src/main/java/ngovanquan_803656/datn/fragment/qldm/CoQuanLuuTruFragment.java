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
import ngovanquan_803656.datn.adapter.CoQuanLuuTruAdapter;
import ngovanquan_803656.datn.asynctask.qldm.AddNewCQLTAsyncTask;
import ngovanquan_803656.datn.asynctask.qldm.SearchCQLTAsyncTask;
import ngovanquan_803656.datn.model.CQLT_ett;
import ngovanquan_803656.datn.utils.ConnectionDetector;
import ngovanquan_803656.datn.utils.Constants;
import ngovanquan_803656.datn.utils.Helper;

/**
 * Created by ngoquan on 5/11/2016.
 */
public class CoQuanLuuTruFragment extends Fragment implements View.OnClickListener{
    Button btn_AddNew, btn_Cancel, btn_Update, btn_Search;
    EditText txt_MaCQLT, txt_TenCQLT, txt_DiaChi, txt_SDT, txt_Email, txt_Website, txt_SearchValue;
    Spinner spn_SearchType;
    ArrayAdapter<String> adapter;
    ListView lv_CQLT;
    String MaCQLT, TenCQLT, DiaChi, SDT, Email, Website;
    CQLT_ett cqlt_ett;
    String search_type, search_val;
    LinearLayout ll_button;
    ArrayList<CQLT_ett> arrData = new ArrayList<>();
    CoQuanLuuTruAdapter coQuanLuuTruAdapter;
    int i, n, total_record;
    int page_current = 1;
    ConnectionDetector cd;
    public CoQuanLuuTruFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_co_quan_luu_tru, container, false);
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
            arrData = new SearchCQLTAsyncTask(getActivity(), "ID", "", page_current, Constants.NUM_ROW_PER_PAGE).execute().get();
            total_record = arrData.get(0).getTotal_record();
            coQuanLuuTruAdapter = new CoQuanLuuTruAdapter(getActivity(), R.layout.list_item_cqlt, arrData);
            lv_CQLT.setAdapter(coQuanLuuTruAdapter);
            Helper.setListViewHeightBasedOnChildren(lv_CQLT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.search_CQLT));
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spn_SearchType.setAdapter(adapter);
        createButton();
        addEvents();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_AddNew:
                if (!txt_MaCQLT.getText().toString().equals("")) {
                    try {
                        MaCQLT = txt_MaCQLT.getText().toString();
                        TenCQLT = txt_TenCQLT.getText().toString();
                        DiaChi = txt_DiaChi.getText().toString();
                        SDT = txt_SDT.getText().toString();
                        Email = txt_Email.getText().toString();
                        Website = txt_Website.getText().toString();
                        cqlt_ett = new AddNewCQLTAsyncTask(getActivity(), Constants.FUNCTION_ADD_NEW, MaCQLT, TenCQLT, DiaChi, SDT, Email, Website, "").execute().get();
                        arrData = new SearchCQLTAsyncTask(getActivity(), "ID", "", page_current, Constants.NUM_ROW_PER_PAGE).execute().get();
                        coQuanLuuTruAdapter = new CoQuanLuuTruAdapter(getActivity(), R.layout.list_item_cqlt, arrData);
                        lv_CQLT.setAdapter(coQuanLuuTruAdapter);
                        Helper.setListViewHeightBasedOnChildren(lv_CQLT);
//                        total_record = arrData.get(0).getTotal_record();
//                        createButton();
                        Toast.makeText(getActivity(), cqlt_ett.getErrDesc(), Toast.LENGTH_SHORT).show();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    setValueForControls();
                } else
                    Toast.makeText(getActivity(), getResources().getString(R.string.message_input), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_Update:
                if (!txt_MaCQLT.getText().toString().equals("")) {
                    try {
                        MaCQLT = txt_MaCQLT.getText().toString();
                        TenCQLT = txt_TenCQLT.getText().toString();
                        DiaChi = txt_DiaChi.getText().toString();
                        SDT = txt_SDT.getText().toString();
                        Email = txt_Email.getText().toString();
                        Website = txt_Website.getText().toString();
                        cqlt_ett = new AddNewCQLTAsyncTask(getActivity(), Constants.FUNCTION_UPDATE, MaCQLT, TenCQLT, DiaChi, SDT, Email, Website, "").execute().get();
                        arrData = new SearchCQLTAsyncTask(getActivity(), "ID", "", page_current, Constants.NUM_ROW_PER_PAGE).execute().get();
                        coQuanLuuTruAdapter = new CoQuanLuuTruAdapter(getActivity(), R.layout.list_item_cqlt, arrData);
                        lv_CQLT.setAdapter(coQuanLuuTruAdapter);
                        Helper.setListViewHeightBasedOnChildren(lv_CQLT);
//                        total_record = arrData.get(0).getTotal_record();
                        Toast.makeText(getActivity(), cqlt_ett.getErrDesc(), Toast.LENGTH_SHORT).show();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    setValueForControls();
                    txt_MaCQLT.setEnabled(true);
                    btn_AddNew.setVisibility(View.VISIBLE);
                    btn_Update.setVisibility(View.GONE);
                } else
                    Toast.makeText(getActivity(), getResources().getString(R.string.message_input), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_Search:
                search_val = txt_SearchValue.getText().toString();
                try {
                    arrData = new SearchCQLTAsyncTask(getActivity(), search_type, search_val, 1, Constants.NUM_ROW_PER_PAGE).execute().get();
                    coQuanLuuTruAdapter = new CoQuanLuuTruAdapter(getActivity(), R.layout.list_item_cqlt, arrData);
                    lv_CQLT.setAdapter(coQuanLuuTruAdapter);
                    Helper.setListViewHeightBasedOnChildren(lv_CQLT);
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
                setValueForControls();
                break;
        }
    }

    private void addControls() {
        txt_MaCQLT = (EditText) getView().findViewById(R.id.txt_MaCQLT);
        txt_TenCQLT = (EditText) getView().findViewById(R.id.txt_TenCQLT);
        txt_DiaChi = (EditText) getView().findViewById(R.id.txt_DiaChi);
        txt_SDT = (EditText) getView().findViewById(R.id.txt_SDT);
        txt_Email = (EditText) getView().findViewById(R.id.txt_Email);
        txt_Website = (EditText) getView().findViewById(R.id.txt_Website);
        txt_SearchValue = (EditText) getView().findViewById(R.id.txt_SearchValue);
        spn_SearchType = (Spinner) getView().findViewById(R.id.spn_SearchType);
        btn_AddNew = (Button) getView().findViewById(R.id.btn_AddNew);
        btn_Update = (Button) getView().findViewById(R.id.btn_Update);
        btn_Search = (Button) getView().findViewById(R.id.btn_Search);
        btn_Cancel = (Button) getView().findViewById(R.id.btn_Cancel);
        lv_CQLT = (ListView) getView().findViewById(R.id.lv_cqlt);
        ll_button = (LinearLayout) getView().findViewById(R.id.ll_button);
//        add value
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
                        search_type = "DiaChi";
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
        lv_CQLT.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
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
                            arrData = new SearchCQLTAsyncTask(getActivity(), "ID", "", page_current, Constants.NUM_ROW_PER_PAGE).execute().get();
                            coQuanLuuTruAdapter = new CoQuanLuuTruAdapter(getActivity(), R.layout.list_item_cqlt, arrData);
                            lv_CQLT.setAdapter(coQuanLuuTruAdapter);
                            Helper.setListViewHeightBasedOnChildren(lv_CQLT);
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

    private void setValueForControls() {
        txt_MaCQLT.setText("");
        txt_TenCQLT.setText("");
        txt_DiaChi.setText("");
        txt_SDT.setText("");
        txt_Email.setText("");
        txt_Website.setText("");
    }
}
