package ngovanquan_803656.datn.fragment.qldm;

import android.app.Activity;
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
import ngovanquan_803656.datn.adapter.HopHoSoAdapter;
import ngovanquan_803656.datn.adapter.SpinnerPhongAdapter;
import ngovanquan_803656.datn.asynctask.qllt.AddNewAndUpdateHopHoSoAsyncTask;
import ngovanquan_803656.datn.asynctask.qllt.SearchHopHoSoAsyncTask;
import ngovanquan_803656.datn.asynctask.qllt.SearchPhongAsyncTask;
import ngovanquan_803656.datn.model.HoSo_ett;
import ngovanquan_803656.datn.model.HopHoSo_ett;
import ngovanquan_803656.datn.model.Phong_ett;
import ngovanquan_803656.datn.utils.ConnectionDetector;
import ngovanquan_803656.datn.utils.Constants;
import ngovanquan_803656.datn.utils.Helper;

/**
 * Created by ngoquan on 5/12/2016.
 */
public class HopHoSoFragment extends Fragment implements View.OnClickListener{

    Button btn_AddNew, btn_Cancel, btn_Update, btn_Search;
    Button btn_first, btn_back, btn_jump_back, btn_1, btn_2, btn_3, btn_4, btn_5, btn_jump_next, btn_next, btn_end;
    EditText txt_MaHopHS, txt_TenHopHS, txt_GhiChu, txt_SearchValue;
    Spinner spn_MaPhong, spn_SearchType;
    CheckBox cb_Active;
    ListView lv_HopHS;
    LinearLayout ll_button;
    ArrayAdapter<String> adapter;


    int total_record, pageTotal;
    public static int page_current = 1;
    ConnectionDetector cd;
    ArrayList<Phong_ett> arrPhong = new ArrayList<>();
    SpinnerPhongAdapter spinnerPhongAdapter;
    ArrayList<HopHoSo_ett> arrHopHoSo = new ArrayList<>();
    HopHoSoAdapter hopHoSoAdapter;
    HopHoSo_ett hopHoSo_ett;
    String TenHopHS, GhiChu, search_type, search_val;
    long MaHopHS;
    int MaPhong;
    boolean Active;
    public HopHoSoFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hop_ho_so, container, false);
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
            arrHopHoSo = new SearchHopHoSoAsyncTask(getActivity(), Constants.FUNCTION_SEARCH, "", "", "ID", "", page_current, Constants.NUM_ROW_PER_PAGE).execute().get();
            hopHoSoAdapter = new HopHoSoAdapter(getActivity(), R.layout.list_item_hophs, arrHopHoSo);
            lv_HopHS.setAdapter(hopHoSoAdapter);
            Helper.setListViewHeightBasedOnChildren(lv_HopHS);
            total_record = arrHopHoSo.get(0).getTotal_record();
//            Log.e("total_record", total_record + "");

            arrPhong = new SearchPhongAsyncTask(getActivity(), "ID", "", 0).execute().get();
            spinnerPhongAdapter = new SpinnerPhongAdapter(getActivity(), R.layout.list_item_spinner, arrPhong);
            spn_MaPhong.setAdapter(spinnerPhongAdapter);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.search_HopHS));
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spn_SearchType.setAdapter(adapter);
        pageLoad(page_current);
//        createButton();
        addEvents();

    }

    private void pageLoad(int page) {
//        gone button
        btn_first.setVisibility(View.GONE);
        btn_back.setVisibility(View.GONE);
        btn_jump_back.setVisibility(View.GONE);
        btn_1.setVisibility(View.GONE);
        btn_2.setVisibility(View.GONE);
        btn_3.setVisibility(View.GONE);
        btn_4.setVisibility(View.GONE);
        btn_5.setVisibility(View.GONE);
        btn_jump_next.setVisibility(View.GONE);
        btn_next.setVisibility(View.GONE);
        btn_end.setVisibility(View.GONE);
        if (arrHopHoSo != null) {
//        calculate page total
            pageTotal = (int) Math.ceil((double) total_record / Constants.NUM_ROW_PER_PAGE);

//        thuat toan bat dau
            if (page < 0)
                page = 1;
            if (pageTotal > 1) {
                if (page > pageTotal)
                    page = pageTotal;
//            show btn_first, btn_back, btn_next, btn_end
                if (page > 1) {
                    setButtonPagingValue(btn_first, "<<", 1);
                    btn_first.setVisibility(View.VISIBLE);
                    setButtonPagingValue(btn_back, "<", page - 1);
                    btn_back.setVisibility(View.VISIBLE);
                }
                if ((page + 1) <= pageTotal) {
                    setButtonPagingValue(btn_next, ">", page + 1);
                    btn_next.setVisibility(View.VISIBLE);
                    setButtonPagingValue(btn_end, ">>", pageTotal);
                    btn_end.setVisibility(View.VISIBLE);
                }

                //trường hợp page_curr nằm đúng trong khoảng thì thực hiện xử lý paging
                setButtonPagingValue(btn_3, page + "", page);
                btn_3.setVisibility(View.VISIBLE);
                if (pageTotal == 2) {
                    setButtonPagingValue(btn_3, page + "", page);
                    if (page == 1) {
                        setButtonPagingValue(btn_4, "2", 2);
                        btn_4.setVisibility(View.VISIBLE);
                    } else {
                        setButtonPagingValue(btn_2, "1", 1);
                        btn_2.setVisibility(View.VISIBLE);
                    }
                } else {
                    //set btn_2, btn_1 và btn_jump_back theo từng trường hợp
                    if (page < 3) {
                        //nếu page_curr < 3 thì xử lý cho trường hợp  =  2 & 1
                        switch (page) {
                            case 2:
                                setButtonPagingValue(btn_2, "1", 1);
                                btn_2.setVisibility(View.VISIBLE);
                                break;
                        }
                    } else {
                        setButtonPagingValue(btn_2, (page - 1) + "", page - 1);
                        btn_2.setVisibility(View.VISIBLE);
                        setButtonPagingValue(btn_1, (page - 2) + "", page - 2);
                        btn_1.setVisibility(View.VISIBLE);
                        if ((page - 2) > Constants.PAGE_JUMP_PAGING) {
                            //page_curr > 3 và page_curr - page_jump > 0
                            setButtonPagingValue(btn_jump_back, "...", (page - 2 - Constants.PAGE_JUMP_PAGING));
                            btn_jump_back.setVisibility(View.VISIBLE);
                        }
                    }

                    //set btn_3, btn_4 và p_jump_next theo từng trường hợp
                    if ((page + 3) <= pageTotal) {
                        setButtonPagingValue(btn_4, (page + 1) + "", (page + 1));
                        btn_4.setVisibility(View.VISIBLE);
                        setButtonPagingValue(btn_5, (page + 2) + "", (page + 2));
                        btn_5.setVisibility(View.VISIBLE);
                        if ((pageTotal - page - 2) > Constants.PAGE_JUMP_PAGING) {
                            setButtonPagingValue(btn_jump_next, "...", (page + 2 + Constants.PAGE_JUMP_PAGING));
                            btn_jump_next.setVisibility(View.VISIBLE);
                        }
                    } else {
                        //nếu page_curr + 3 > page_total thì xử lý cho trường hợp  =  +2 & +1
                        if ((page + 1) == pageTotal) {
                            setButtonPagingValue(btn_4, (page + 1) + "", (page + 1));
                            btn_4.setVisibility(View.VISIBLE);
                        } else {
                            if ((page + 2) == pageTotal) {
                                setButtonPagingValue(btn_4, (page + 1) + "", (page + 1));
                                btn_4.setVisibility(View.VISIBLE);
                                setButtonPagingValue(btn_5, (page + 2) + "", (page + 2));
                                btn_5.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }

            } else {
                page = pageTotal;
            }
        }
    }

    private void setButtonPagingValue(Button b, String title, final int page) {
        b.setText(title);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), page + "", Toast.LENGTH_SHORT).show();
                page_current = page;
                try {
                    arrHopHoSo = new SearchHopHoSoAsyncTask(getActivity(), Constants.FUNCTION_SEARCH, "", "", "ID", "", page_current, Constants.NUM_ROW_PER_PAGE).execute().get();
                    hopHoSoAdapter = new HopHoSoAdapter(getActivity(), R.layout.list_item_hophs, arrHopHoSo);
                    lv_HopHS.setAdapter(hopHoSoAdapter);
                    Helper.setListViewHeightBasedOnChildren(lv_HopHS);
                    total_record = arrHopHoSo.get(0).getTotal_record();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                pageLoad(page_current);
            }
        });
    }

    private void addControls() {
        btn_AddNew = (Button) getView().findViewById(R.id.btn_AddNew);
        btn_Update = (Button) getView().findViewById(R.id.btn_Update);
        btn_Search = (Button) getView().findViewById(R.id.btn_Search);
        btn_Cancel = (Button) getView().findViewById(R.id.btn_Cancel);
        btn_first = (Button) getView().findViewById(R.id.btn_first);
        btn_back = (Button) getView().findViewById(R.id.btn_back);
        btn_jump_back = (Button) getView().findViewById(R.id.btn_jump_back);
        btn_1 = (Button) getView().findViewById(R.id.btn_1);
        btn_2 = (Button) getView().findViewById(R.id.btn_2);
        btn_3 = (Button) getView().findViewById(R.id.btn_3);
        btn_4 = (Button) getView().findViewById(R.id.btn_4);
        btn_5 = (Button) getView().findViewById(R.id.btn_5);
        btn_jump_next = (Button) getView().findViewById(R.id.btn_jump_next);
        btn_next = (Button) getView().findViewById(R.id.btn_next);
        btn_end = (Button) getView().findViewById(R.id.btn_end);
        txt_MaHopHS = (EditText) getView().findViewById(R.id.txt_MaHopHS);
        txt_TenHopHS = (EditText) getView().findViewById(R.id.txt_TenHopHS);
        txt_GhiChu = (EditText) getView().findViewById(R.id.txt_GhiChu);
        txt_SearchValue = (EditText) getView().findViewById(R.id.txt_SearchValue);
        spn_MaPhong = (Spinner) getView().findViewById(R.id.spn_MaPhong);
        spn_SearchType = (Spinner) getView().findViewById(R.id.spn_SearchType);
        cb_Active = (CheckBox) getView().findViewById(R.id.cb_Active);
        lv_HopHS = (ListView) getView().findViewById(R.id.lv_HopHS);
        ll_button = (LinearLayout) getView().findViewById(R.id.ll_button);

    }

    public void setValueForControls() {
        txt_MaHopHS.setText("");
        txt_TenHopHS.setText("");
        txt_GhiChu.setText("");
        spn_MaPhong.setSelection(0);
        cb_Active.setChecked(false);
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

        spn_MaPhong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MaPhong = (int)arrPhong.get(position).getMaPhong();
//                Log.e("MaPhong", MaPhong + "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                MaPhong = arrHopHoSo.get(0).getMaPhong();
//                Log.e("MaPhong", MaPhong + "");
            }
        });

        lv_HopHS.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_AddNew:
                if (!txt_TenHopHS.getText().toString().equals("")) {
                    TenHopHS = txt_TenHopHS.getText().toString();
                    GhiChu = txt_GhiChu.getText().toString();
                    if (cb_Active.isChecked())
                        Active = true;
                    else
                        Active = false;
                    try {
                        hopHoSo_ett = new AddNewAndUpdateHopHoSoAsyncTask(getActivity(), Constants.FUNCTION_ADD_NEW, 0, TenHopHS, GhiChu, Active, MaPhong).execute().get();
                        arrHopHoSo = new SearchHopHoSoAsyncTask(getActivity(), Constants.FUNCTION_SEARCH, "", "", "ID", "", page_current, Constants.NUM_ROW_PER_PAGE).execute().get();
                        hopHoSoAdapter = new HopHoSoAdapter(getActivity(), R.layout.list_item_hophs, arrHopHoSo);
                        lv_HopHS.setAdapter(hopHoSoAdapter);
                        Helper.setListViewHeightBasedOnChildren(lv_HopHS);
                        total_record = arrHopHoSo.get(0).getTotal_record();
                        pageLoad(page_current);
                        Toast.makeText(getActivity(), hopHoSo_ett.getErrDesc(), Toast.LENGTH_SHORT).show();
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
                if (!txt_TenHopHS.getText().toString().equals("")) {
                    MaHopHS = Long.parseLong(txt_MaHopHS.getText().toString());
                    TenHopHS = txt_TenHopHS.getText().toString();
                    GhiChu = txt_GhiChu.getText().toString();
                    if (cb_Active.isChecked())
                        Active = true;
                    else
                        Active = false;
                    try {
                        hopHoSo_ett = new AddNewAndUpdateHopHoSoAsyncTask(getActivity(), Constants.FUNCTION_UPDATE, MaHopHS, TenHopHS, GhiChu, Active, MaPhong).execute().get();
                        arrHopHoSo = new SearchHopHoSoAsyncTask(getActivity(), Constants.FUNCTION_SEARCH, "", "", "ID", "", page_current, Constants.NUM_ROW_PER_PAGE).execute().get();
                        hopHoSoAdapter = new HopHoSoAdapter(getActivity(), R.layout.list_item_hophs, arrHopHoSo);
                        lv_HopHS.setAdapter(hopHoSoAdapter);
                        Helper.setListViewHeightBasedOnChildren(lv_HopHS);
                        total_record = arrHopHoSo.get(0).getTotal_record();
                        pageLoad(page_current);
                        Toast.makeText(getActivity(), hopHoSo_ett.getErrDesc(), Toast.LENGTH_SHORT).show();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    setValueForControls();
                    btn_AddNew.setVisibility(View.VISIBLE);
                    btn_Update.setVisibility(View.GONE);
                } else
                    Toast.makeText(getActivity(), getResources().getString(R.string.message_input), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_Search:
                search_val = txt_SearchValue.getText().toString();
                try {
                    arrHopHoSo = new SearchHopHoSoAsyncTask(getActivity(), Constants.FUNCTION_SEARCH, "", "", search_type, search_val, 1, Constants.NUM_ROW_PER_PAGE).execute().get();
                    hopHoSoAdapter = new HopHoSoAdapter(getActivity(), R.layout.list_item_cqlt, arrHopHoSo);
                    lv_HopHS.setAdapter(hopHoSoAdapter);
                    Helper.setListViewHeightBasedOnChildren(lv_HopHS);
                    total_record = arrHopHoSo.get(0).getTotal_record();
                    pageLoad(page_current);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                txt_SearchValue.setText("");
                break;

            case R.id.btn_Cancel:
                setValueForControls();
                btn_AddNew.setVisibility(View.VISIBLE);
                btn_Update.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
}
