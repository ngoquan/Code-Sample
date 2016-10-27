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

import java.util.concurrent.ExecutionException;

import ngovanquan_803656.datn.R;
import ngovanquan_803656.datn.asynctask.qldm.AddNewLHTLAsyncTask;
import ngovanquan_803656.datn.asynctask.qldm.GetAllDataAysnTask;
import ngovanquan_803656.datn.asynctask.qldm.UpdateLHTLAsyncTask;
import ngovanquan_803656.datn.model.LHTL_ett;
import ngovanquan_803656.datn.utils.ConnectionDetector;
import ngovanquan_803656.datn.utils.Constants;
import ngovanquan_803656.datn.utils.Helper;

/**
 * Created by ngoquan on 4/17/2016.
 */
public class LoaiHinhTaiLieuFragment extends Fragment implements View.OnClickListener {
    //    Button btn_getLHTL;
    Button btn_AddNew, btn_Cancel, btn_Update, btn_Search;
    EditText txt_TenLHTL, txt_STT, txt_GhiChu, txt_MaLHTL, txt_SearchValue;
    Spinner spn_SearchType;
    CheckBox cb_Active;
    ListView lv_LHTL;
    ArrayAdapter<String> adapter;


    //    Parameter
    String TenLoaiHinhTL, GhiChu;
    long MaLoaiHinhTL, STT;
    boolean Active;
    LHTL_ett lhtl_ett;
    String search_type, search_val;
    LinearLayout ll_button;
    int i, n, total_record;
    int page_current = 1;
    ConnectionDetector cd;

    public LoaiHinhTaiLieuFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_loai_hinh_tai_lieu, container, false);
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
            total_record = new GetAllDataAysnTask(getActivity(), "ID", "", page_current).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.search_LHTL));
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spn_SearchType.setAdapter(adapter);
        createButton();
        addEvents();
    }
    private void addControls() {
        lv_LHTL = (ListView) getView().findViewById(R.id.lv_LHTL);
        btn_AddNew = (Button) getView().findViewById(R.id.btn_AddNew);
        btn_Update = (Button) getView().findViewById(R.id.btn_Update);
        btn_Cancel = (Button) getView().findViewById(R.id.btn_Cancel);
        btn_Search = (Button) getView().findViewById(R.id.btn_Search);
        txt_MaLHTL = (EditText) getView().findViewById(R.id.txt_MaLHTL);
        txt_TenLHTL = (EditText) getView().findViewById(R.id.txt_TenLHTL);
        txt_STT = (EditText) getView().findViewById(R.id.txt_STT);
        txt_GhiChu = (EditText) getView().findViewById(R.id.txt_GhiChu);
        cb_Active = (CheckBox) getView().findViewById(R.id.cb_Active);
        txt_SearchValue = (EditText) getView().findViewById(R.id.txt_SearchValue);
        spn_SearchType = (Spinner) getView().findViewById(R.id.spn_SearchType);
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
//                Toast.makeText(LoaiHinhTaiLieuActivity.this, search_type, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                search_type = "ID";
            }
        });
        lv_LHTL.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
    }

    private void setValueForControl() {
        txt_TenLHTL.setText("");
        txt_STT.setText("");
        txt_GhiChu.setText("");
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
                        new GetAllDataAysnTask(getActivity(), "ID", "", page_current).execute();
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
                if (txt_TenLHTL.getText().toString().equals("") == false) {
                    TenLoaiHinhTL = txt_TenLHTL.getText().toString();
                    STT = Long.parseLong(txt_STT.getText().toString());
                    GhiChu = txt_GhiChu.getText().toString();
                    if (cb_Active.isChecked())
                        Active = true;
                    else
                        Active = false;
                    try {
                        lhtl_ett = new AddNewLHTLAsyncTask(getActivity(), TenLoaiHinhTL, STT, GhiChu, Active).execute().get();
                        total_record = new GetAllDataAysnTask(getActivity(), "ID", "", page_current).execute().get();
                        Toast.makeText(getActivity(), lhtl_ett.getErrDesc(), Toast.LENGTH_LONG).show();
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
                if (txt_TenLHTL.getText().toString().equals("") == false) {
                    MaLoaiHinhTL = Long.parseLong(txt_MaLHTL.getText().toString());
                    TenLoaiHinhTL = txt_TenLHTL.getText().toString();
                    STT = Long.parseLong(txt_STT.getText().toString());
                    GhiChu = txt_GhiChu.getText().toString();
                    if (cb_Active.isChecked())
                        Active = true;
                    else
                        Active = false;
                    try {
                        lhtl_ett = new UpdateLHTLAsyncTask(getActivity(), MaLoaiHinhTL, TenLoaiHinhTL, STT, GhiChu, Active).execute().get();
                        total_record = new GetAllDataAysnTask(getActivity(), "ID", "", page_current).execute().get();
                        Toast.makeText(getActivity(), lhtl_ett.getErrDesc(), Toast.LENGTH_LONG).show();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    btn_AddNew.setVisibility(View.VISIBLE);
                    btn_Update.setVisibility(View.GONE);
                    setValueForControl();
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.message_input), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_Search:
                search_val = txt_SearchValue.getText().toString();
                try {
                    total_record = new GetAllDataAysnTask(getActivity(), search_type, search_val, 1).execute().get();
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
