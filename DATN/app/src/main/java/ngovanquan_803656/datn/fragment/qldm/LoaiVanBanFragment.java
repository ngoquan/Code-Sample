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
import ngovanquan_803656.datn.adapter.LoaiVanBanAdapter;
import ngovanquan_803656.datn.asynctask.qldm.AddNewLVBAsyncTask;
import ngovanquan_803656.datn.asynctask.qldm.SearchLVBAsyncTask;
import ngovanquan_803656.datn.asynctask.qldm.UpdateLVBAsyncTask;
import ngovanquan_803656.datn.model.LVB_ett;
import ngovanquan_803656.datn.utils.ConnectionDetector;
import ngovanquan_803656.datn.utils.Constants;
import ngovanquan_803656.datn.utils.Helper;

/**
 * Created by ngoquan on 4/17/2016.
 */
public class LoaiVanBanFragment extends Fragment implements View.OnClickListener{

    Button btn_AddNew, btn_Cancel, btn_Update, btn_Search;
    EditText txt_TenLVB, txt_STT, txt_GhiChu, txt_MaLVB, txt_SearchValue;
    Spinner spn_SearchType;
    CheckBox cb_Active;
    ArrayAdapter<String> adapter;
    ListView lv_LVB;

    String TenLVB, GhiChu;
    long MaLVB;
    int STT;
    boolean Active;
    LVB_ett lvb_ett;
    String search_type, search_val;
    LinearLayout ll_button;
    int i, n, total_record;
    int page_current = 1;
    ArrayList<LVB_ett> arrLVB = new ArrayList<>();
    LoaiVanBanAdapter loaiVanBanAdapter;
    ConnectionDetector cd;
    public LoaiVanBanFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_loai_van_ban, container, false);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
            arrLVB = new SearchLVBAsyncTask(getActivity(), "ID", "", page_current, Constants.NUM_ROW_PER_PAGE).execute().get();
            loaiVanBanAdapter = new LoaiVanBanAdapter(getActivity(), R.layout.list_item_dtc, arrLVB);
            lv_LVB.setAdapter(loaiVanBanAdapter);
            Helper.setListViewHeightBasedOnChildren(lv_LVB);
            total_record = arrLVB.get(0).getTotal_record();
//            ArrayList<Phong_ett> arrData = new SearchPhongAsyncTask(getActivity(), "ID", "", page_current).execute().get();
//            Log.e("Countarray", arrData.size() + "");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.search_LVB));
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spn_SearchType.setAdapter(adapter);
        createButton();
        addEvents();
    }

    private void addControls() {
        btn_AddNew = (Button) getActivity().findViewById(R.id.btn_AddNew);
        btn_Update = (Button) getActivity().findViewById(R.id.btn_Update);
        btn_Search = (Button) getActivity().findViewById(R.id.btn_Search);
        btn_Cancel = (Button) getActivity().findViewById(R.id.btn_Cancel);
        txt_MaLVB = (EditText) getActivity().findViewById(R.id.txt_MaLVB);
        txt_TenLVB = (EditText) getActivity().findViewById(R.id.txt_TenLVB);
        txt_GhiChu = (EditText) getActivity().findViewById(R.id.txt_GhiChu);
        txt_STT = (EditText) getActivity().findViewById(R.id.txt_STT);
        txt_SearchValue = (EditText) getActivity().findViewById(R.id.txt_SearchValue);
        cb_Active = (CheckBox) getActivity().findViewById(R.id.cb_Active);
        spn_SearchType = (Spinner) getActivity().findViewById(R.id.spn_SearchType);
        lv_LVB = (ListView) getActivity().findViewById(R.id.lv_LVB);
        ll_button = (LinearLayout) getActivity().findViewById(R.id.ll_button);
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                search_type = "ID";
            }
        });

        lv_LVB.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
    }

    private void setValueForControl() {
        txt_TenLVB.setText("");
        txt_GhiChu.setText("");
        txt_STT.setText("");
        cb_Active.setChecked(false);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_AddNew:
                if (txt_TenLVB.getText().toString().equals("") == false) {
                    TenLVB = txt_TenLVB.getText().toString();
                    GhiChu = txt_GhiChu.getText().toString();
                    STT = Integer.parseInt(txt_STT.getText().toString());
                    if (cb_Active.isChecked()) {
                        Active = true;
                    } else {
                        Active = false;
                    }
                    try {
                        lvb_ett = new AddNewLVBAsyncTask(getActivity(), TenLVB, GhiChu, STT, Active).execute().get();
                        arrLVB = new SearchLVBAsyncTask(getActivity(), "ID", "", page_current, Constants.NUM_ROW_PER_PAGE).execute().get();
                        loaiVanBanAdapter = new LoaiVanBanAdapter(getActivity(), R.layout.list_item_dtc, arrLVB);
                        lv_LVB.setAdapter(loaiVanBanAdapter);
                        Helper.setListViewHeightBasedOnChildren(lv_LVB);
                        total_record = arrLVB.get(0).getTotal_record();
                        Toast.makeText(getActivity(), lvb_ett.getErrDesc(), Toast.LENGTH_SHORT).show();
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
                if (txt_TenLVB.getText().toString().equals("") == false) {
                    MaLVB = Long.parseLong(txt_MaLVB.getText().toString());
                    TenLVB = txt_TenLVB.getText().toString();
                    GhiChu = txt_GhiChu.getText().toString();
                    STT = Integer.parseInt(txt_STT.getText().toString());
                    if (cb_Active.isChecked()) {
                        Active = true;
                    } else {
                        Active = false;
                    }
                    try {
                        lvb_ett = new UpdateLVBAsyncTask(getActivity(), MaLVB, TenLVB, GhiChu, STT, Active).execute().get();
                        arrLVB = new SearchLVBAsyncTask(getActivity(), "ID", "", page_current, Constants.NUM_ROW_PER_PAGE).execute().get();
                        loaiVanBanAdapter = new LoaiVanBanAdapter(getActivity(), R.layout.list_item_dtc, arrLVB);
                        lv_LVB.setAdapter(loaiVanBanAdapter);
                        Helper.setListViewHeightBasedOnChildren(lv_LVB);
                        total_record = arrLVB.get(0).getTotal_record();
                        Toast.makeText(getActivity(), lvb_ett.getErrDesc(), Toast.LENGTH_SHORT).show();
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
                    arrLVB = new SearchLVBAsyncTask(getActivity(), search_type, search_val, 1, Constants.NUM_ROW_PER_PAGE).execute().get();
                    loaiVanBanAdapter = new LoaiVanBanAdapter(getActivity(), R.layout.list_item_dtc, arrLVB);
                    lv_LVB.setAdapter(loaiVanBanAdapter);
                    Helper.setListViewHeightBasedOnChildren(lv_LVB);
                    total_record = arrLVB.get(0).getTotal_record();
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
                        new SearchLVBAsyncTask(getActivity(), "ID", "", page_current, Constants.NUM_ROW_PER_PAGE).execute();
                    }
                });
            }
        } else {
            ll_button.setVisibility(View.GONE);
        }
    }
}
