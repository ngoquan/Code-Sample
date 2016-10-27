package ngovanquan_803656.datn.fragment;

import android.app.Activity;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ngovanquan_803656.datn.R;
import ngovanquan_803656.datn.adapter.GridHoSoAdapter;
import ngovanquan_803656.datn.adapter.HoSoTrangChuAdapter;
import ngovanquan_803656.datn.adapter.VanBanTrangChuAdapter;
import ngovanquan_803656.datn.asynctask.SearchHoSo;
import ngovanquan_803656.datn.asynctask.SearchVanBan;
import ngovanquan_803656.datn.asynctask.qllt.SearchHoSoAsyncTask;
import ngovanquan_803656.datn.model.HoSo_ett;
import ngovanquan_803656.datn.model.VanBan_ett;
import ngovanquan_803656.datn.utils.ConnectionDetector;
import ngovanquan_803656.datn.utils.Constants;
import ngovanquan_803656.datn.utils.Helper;

/**
 * Created by NGOQUAN on 5/31/2016.
 */
public class TrangChuFragment extends Fragment {

//    ScrollView scrollView;
    EditText txt_searchValue;
    Spinner spn_searchType;
    private static Button btn_search, btn_first, btn_back, btn_jump_back, btn_1, btn_2, btn_3, btn_4, btn_5, btn_jump_next, btn_next, btn_end;
    LinearLayout ll_header, ll_hoSo, ll_vanBan, ll_button;
    private static ListView lv_trangChu;
    TextView txt_header;

    private static Activity activity;

    private static ArrayList<HoSo_ett> arrHoSo = new ArrayList<>();
    private static HoSoTrangChuAdapter hoSoTrangChuAdapter;
    private static ArrayList<VanBan_ett> arrVanBan = new ArrayList<>();
    private static VanBanTrangChuAdapter vanBanTrangChuAdapter;
    ArrayAdapter<String> adapter;
    public static int total_record, pageTotal, type;
    public static int pageHoSo = 1;
    public static int pageVanBan = 1;
    private static String search_type, search_value;
    ConnectionDetector cd;



    public TrangChuFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trang_chu, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        cd = new ConnectionDetector(getActivity());
        if (!cd.isConnectingToInternet()) {
            Helper.notificationDialog(getActivity(), "No internert connection", "You don't have internet connection", false);
            return;
        }
        activity = getActivity();
        addControls();

        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.search_TC));
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spn_searchType.setAdapter(adapter);


        addEvents();
    }


    private void addControls() {
//        scrollView = (ScrollView) getView().findViewById(R.id.scrollView);
        txt_searchValue = (EditText) getView().findViewById(R.id.txt_searchValue);
        spn_searchType = (Spinner) getView().findViewById(R.id.spn_searchType);
        btn_search = (Button) getView().findViewById(R.id.btn_search);
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
        ll_header = (LinearLayout) getView().findViewById(R.id.ll_header);
        ll_hoSo = (LinearLayout) getView().findViewById(R.id.ll_hoSo);
        ll_vanBan = (LinearLayout) getView().findViewById(R.id.ll_vanBan);
        ll_button = (LinearLayout) getView().findViewById(R.id.ll_button);
        lv_trangChu = (ListView) getView().findViewById(R.id.lv_trangChu);
        txt_header = (TextView) getView().findViewById(R.id.txt_header);
    }

    private void addEvents() {
        spn_searchType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        search_type = "TieuDe";
                        break;
                    case 1:
                        search_type = "TrichYeuND";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                search_type = "TieuDe";
            }
        });

        lv_trangChu.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_value = txt_searchValue.getText().toString();
                try {
                    if (search_type.equals("TieuDe")) {
                        arrHoSo = new SearchHoSo(getActivity(), search_type, search_value, pageHoSo, Constants.NUM_ROW_PER_PAGE).execute().get();
                        hoSoTrangChuAdapter = new HoSoTrangChuAdapter(getActivity(), R.layout.list_item_hoso_trangchu, arrHoSo);
                        lv_trangChu.setAdapter(hoSoTrangChuAdapter);
                        Helper.setListViewHeightBasedOnChildren(lv_trangChu);
                        total_record = arrHoSo.get(0).getTotal_record();
                        type = 1;
                        pageLoad(type, pageHoSo);
                        txt_header.setText("Kết quả tìm kiếm: Có " + total_record + " kết quả");
                        ll_header.setVisibility(View.VISIBLE);
                        ll_hoSo.setVisibility(View.VISIBLE);
                        ll_button.setVisibility(View.VISIBLE);
                        ll_vanBan.setVisibility(View.GONE);

                    } else if (search_type.equals("TrichYeuND")) {
                        arrVanBan = new SearchVanBan(getActivity(), search_type, search_value, pageVanBan).execute().get();
                        vanBanTrangChuAdapter = new VanBanTrangChuAdapter(getActivity(), R.layout.list_item_vanban_trangchu, arrVanBan);
                        lv_trangChu.setAdapter(vanBanTrangChuAdapter);
                        Helper.setListViewHeightBasedOnChildren(lv_trangChu);
                        total_record = arrVanBan.get(0).getTotal_record();
                        type = 2;
                        pageLoad(type, pageVanBan);
                        txt_header.setText("Kết quả tìm kiếm: Có " + total_record + " kết quả");
                        ll_header.setVisibility(View.VISIBLE);
                        ll_vanBan.setVisibility(View.VISIBLE);
                        ll_button.setVisibility(View.VISIBLE);
                        ll_hoSo.setVisibility(View.GONE);
                    }
                    txt_searchValue.setText("");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public static void pageLoad(int type, int page) {
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
//        if (arrHopHoSo != null) {
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
                setButtonPagingValue(type, btn_first, "<<", 1);
                btn_first.setVisibility(View.VISIBLE);
                setButtonPagingValue(type, btn_back, "<", page - 1);
                btn_back.setVisibility(View.VISIBLE);
            }
            if ((page + 1) <= pageTotal) {
                setButtonPagingValue(type, btn_next, ">", page + 1);
                btn_next.setVisibility(View.VISIBLE);
                setButtonPagingValue(type, btn_end, ">>", pageTotal);
                btn_end.setVisibility(View.VISIBLE);
            }

            //trường hợp page_curr nằm đúng trong khoảng thì thực hiện xử lý paging
            setButtonPagingValue(type, btn_3, page + "", page);
            btn_3.setVisibility(View.VISIBLE);
            if (pageTotal == 2) {
                setButtonPagingValue(type, btn_3, page + "", page);
                if (page == 1) {
                    setButtonPagingValue(type, btn_4, "2", 2);
                    btn_4.setVisibility(View.VISIBLE);
                } else {
                    setButtonPagingValue(type, btn_2, "1", 1);
                    btn_2.setVisibility(View.VISIBLE);
                }
            } else {
                //set btn_2, btn_1 và btn_jump_back theo từng trường hợp
                if (page < 3) {
                    //nếu page_curr < 3 thì xử lý cho trường hợp  =  2 & 1
                    switch (page) {
                        case 2:
                            setButtonPagingValue(type, btn_2, "1", 1);
                            btn_2.setVisibility(View.VISIBLE);
                            break;
                    }
                } else {
                    setButtonPagingValue(type, btn_2, (page - 1) + "", page - 1);
                    btn_2.setVisibility(View.VISIBLE);
                    setButtonPagingValue(type, btn_1, (page - 2) + "", page - 2);
                    btn_1.setVisibility(View.VISIBLE);
                    if ((page - 2) > Constants.PAGE_JUMP_PAGING) {
                        //page_curr > 3 và page_curr - page_jump > 0
                        setButtonPagingValue(type, btn_jump_back, "...", (page - 2 - Constants.PAGE_JUMP_PAGING));
                        btn_jump_back.setVisibility(View.VISIBLE);
                    }
                }

                //set btn_3, btn_4 và p_jump_next theo từng trường hợp
                if ((page + 3) <= pageTotal) {
                    setButtonPagingValue(type, btn_4, (page + 1) + "", (page + 1));
                    btn_4.setVisibility(View.VISIBLE);
                    setButtonPagingValue(type, btn_5, (page + 2) + "", (page + 2));
                    btn_5.setVisibility(View.VISIBLE);
                    if ((pageTotal - page - 2) > Constants.PAGE_JUMP_PAGING) {
                        setButtonPagingValue(type, btn_jump_next, "...", (page + 2 + Constants.PAGE_JUMP_PAGING));
                        btn_jump_next.setVisibility(View.VISIBLE);
                    }
                } else {
                    //nếu page_curr + 3 > page_total thì xử lý cho trường hợp  =  +2 & +1
                    if ((page + 1) == pageTotal) {
                        setButtonPagingValue(type, btn_4, (page + 1) + "", (page + 1));
                        btn_4.setVisibility(View.VISIBLE);
                    } else {
                        if ((page + 2) == pageTotal) {
                            setButtonPagingValue(type, btn_4, (page + 1) + "", (page + 1));
                            btn_4.setVisibility(View.VISIBLE);
                            setButtonPagingValue(type, btn_5, (page + 2) + "", (page + 2));
                            btn_5.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }

        } else {
            page = pageTotal;
        }
//        }
    }

    private static void setButtonPagingValue(final int type, Button b, String title, final int page) {
        b.setText(title);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, page + "", Toast.LENGTH_SHORT).show();
                switch (type) {
                    case 1:
                        pageHoSo = page;
                        try {
                            arrHoSo = new SearchHoSo(activity, search_type, search_value, pageHoSo, Constants.NUM_ROW_PER_PAGE).execute().get();
                            hoSoTrangChuAdapter = new HoSoTrangChuAdapter(activity, R.layout.list_item_hoso_trangchu, arrHoSo);
                            lv_trangChu.setAdapter(hoSoTrangChuAdapter);
                            Helper.setListViewHeightBasedOnChildren(lv_trangChu);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        pageLoad(type, pageHoSo);
                        break;
                    case 2:
                        pageVanBan = page;
                        try {
                            arrVanBan = new SearchVanBan(activity, search_type, search_value, pageVanBan).execute().get();
                            vanBanTrangChuAdapter = new VanBanTrangChuAdapter(activity, R.layout.list_item_vanban_trangchu, arrVanBan);
                            lv_trangChu.setAdapter(vanBanTrangChuAdapter);
                            Helper.setListViewHeightBasedOnChildren(lv_trangChu);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        pageLoad(type, pageVanBan);
                        break;
                }

            }
        });
    }
}
