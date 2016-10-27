package ngovanquan_803656.datn.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ngovanquan_803656.datn.R;
import ngovanquan_803656.datn.adapter.HoSoTrangChuAdapter;
import ngovanquan_803656.datn.adapter.PhongSearchAdapter;
import ngovanquan_803656.datn.adapter.VanBanTrangChuAdapter;
import ngovanquan_803656.datn.asynctask.SearchHoSo;
import ngovanquan_803656.datn.asynctask.SearchVanBan;
import ngovanquan_803656.datn.asynctask.qllt.SearchPhongAsyncTask;
import ngovanquan_803656.datn.model.HoSo_ett;
import ngovanquan_803656.datn.model.Phong_ett;
import ngovanquan_803656.datn.model.VanBan_ett;
import ngovanquan_803656.datn.utils.ConnectionDetector;
import ngovanquan_803656.datn.utils.Constants;
import ngovanquan_803656.datn.utils.Helper;

/**
 * Created by NGOQUAN on 6/3/2016.
 */
public class TimKiemNangCaoFragment extends Fragment {
    EditText txt_searchValue;
    Spinner spn_searchType;
    private  Button btn_search, btn_first, btn_back, btn_jump_back, btn_1, btn_2, btn_3, btn_4, btn_5, btn_jump_next, btn_next, btn_end;
    LinearLayout ll_header, ll_hoSo, ll_vanBan, ll_button, ll_phong;
    private ListView lv_result;
    TextView txt_header, txt_status;
    RadioGroup rd_group;

//    private static Activity activity;

    private ArrayList<Phong_ett> arrPhong = new ArrayList<>();
    PhongSearchAdapter phongSearchAdapter;
    private  ArrayList<HoSo_ett> arrHoSo = new ArrayList<>();
    private  HoSoTrangChuAdapter hoSoTrangChuAdapter;
    private  ArrayList<VanBan_ett> arrVanBan = new ArrayList<>();
    private  VanBanTrangChuAdapter vanBanTrangChuAdapter;
    ArrayAdapter<String> adapter;
    public static int total_record, pageTotal, type;
    public static int pagePhong = 1;
    public static int pageHoSo = 1;
    public static int pageVanBan = 1;
    private String search_type, search_value;
    ConnectionDetector cd;
    public TimKiemNangCaoFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tim_kiem_nang_cao, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        super.onActivityCreated(savedInstanceState);
        cd = new ConnectionDetector(getActivity());
        if (!cd.isConnectingToInternet()) {
            Helper.notificationDialog(getActivity(), "No internert connection", "You don't have internet connection", false);
            return;
        }
        addControls();
//        rd_group.check(R.id.rd_phong);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.search_phong));
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spn_searchType.setAdapter(adapter);
        ll_phong.setVisibility(View.GONE);
        ll_vanBan.setVisibility(View.GONE);
        ll_hoSo.setVisibility(View.GONE);
        ll_button.setVisibility(View.GONE);
        ll_header.setVisibility(View.GONE);
        lv_result.setVisibility(View.GONE);
        txt_status.setVisibility(View.GONE);
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
                }
//                                Toast.makeText(getActivity(), search_type, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                search_type = "ID";
            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_value = txt_searchValue.getText().toString();
                try {
                    arrPhong = new SearchPhongAsyncTask(getActivity(), search_type, search_value, pagePhong).execute().get();
                    if (!arrPhong.isEmpty()) {
                        phongSearchAdapter = new PhongSearchAdapter(getActivity(), R.layout.list_item_search_phong, arrPhong);
                        lv_result.setAdapter(phongSearchAdapter);
                        Helper.setListViewHeightBasedOnChildren(lv_result);
                        total_record = arrPhong.get(0).getTotal_record();
                        type = 1;
                        pageLoad(type, pagePhong);
                        txt_header.setText("Kết quả tìm kiếm: Có " + total_record + " kết quả");
                        ll_phong.setVisibility(View.VISIBLE);
                        ll_header.setVisibility(View.VISIBLE);
                        ll_button.setVisibility(View.VISIBLE);
                        ll_hoSo.setVisibility(View.GONE);
                        ll_vanBan.setVisibility(View.GONE);
                        txt_status.setVisibility(View.GONE);
                        lv_result.setVisibility(View.VISIBLE);
                    } else {
                        txt_header.setText("Kết quả tìm kiếm: Có 0 kết quả");
                        ll_phong.setVisibility(View.VISIBLE);
                        ll_header.setVisibility(View.VISIBLE);
                        ll_button.setVisibility(View.GONE);
                        ll_hoSo.setVisibility(View.GONE);
                        ll_vanBan.setVisibility(View.GONE);
                        txt_status.setVisibility(View.VISIBLE);
                        lv_result.setVisibility(View.GONE);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                txt_searchValue.setText("");
            }
        });
        addEvents();

    }

    private void addControls() {
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
        ll_phong = (LinearLayout) getView().findViewById(R.id.ll_phong);
        ll_hoSo = (LinearLayout) getView().findViewById(R.id.ll_hoSo);
        ll_vanBan = (LinearLayout) getView().findViewById(R.id.ll_vanBan);
        ll_button = (LinearLayout) getView().findViewById(R.id.ll_button);
        lv_result = (ListView) getView().findViewById(R.id.lv_result);
        txt_header = (TextView) getView().findViewById(R.id.txt_header);
        txt_status = (TextView) getView().findViewById(R.id.txt_status);
        rd_group = (RadioGroup) getView().findViewById(R.id.rd_group);
    }

    private void addEvents() {
        rd_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rd_phong:
                        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.search_phong));
                        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                        spn_searchType.setAdapter(adapter);
                        ll_phong.setVisibility(View.GONE);
                        ll_vanBan.setVisibility(View.GONE);
                        ll_hoSo.setVisibility(View.GONE);
                        ll_button.setVisibility(View.GONE);
                        ll_header.setVisibility(View.GONE);
                        lv_result.setVisibility(View.GONE);
                        txt_status.setVisibility(View.GONE);
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
                                }
//                                Toast.makeText(getActivity(), search_type, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                                search_type = "ID";
                            }
                        });

                        btn_search.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                search_value = txt_searchValue.getText().toString();
                                try {
                                    arrPhong = new SearchPhongAsyncTask(getActivity(), search_type, search_value, pagePhong).execute().get();
                                    if (!arrPhong.isEmpty()) {
                                        phongSearchAdapter = new PhongSearchAdapter(getActivity(), R.layout.list_item_search_phong, arrPhong);
                                        lv_result.setAdapter(phongSearchAdapter);
                                        Helper.setListViewHeightBasedOnChildren(lv_result);
                                        total_record = arrPhong.get(0).getTotal_record();
                                        type = 1;
                                        pageLoad(type, pagePhong);
                                        txt_header.setText("Kết quả tìm kiếm: Có " + total_record + " kết quả");
                                        ll_phong.setVisibility(View.VISIBLE);
                                        ll_header.setVisibility(View.VISIBLE);
                                        ll_button.setVisibility(View.VISIBLE);
                                        ll_hoSo.setVisibility(View.GONE);
                                        ll_vanBan.setVisibility(View.GONE);
                                        txt_status.setVisibility(View.GONE);
                                        lv_result.setVisibility(View.VISIBLE);
                                    } else {
                                        txt_header.setText("Kết quả tìm kiếm: Có 0 kết quả");
                                        ll_phong.setVisibility(View.VISIBLE);
                                        ll_header.setVisibility(View.VISIBLE);
                                        ll_button.setVisibility(View.GONE);
                                        ll_hoSo.setVisibility(View.GONE);
                                        ll_vanBan.setVisibility(View.GONE);
                                        txt_status.setVisibility(View.VISIBLE);
                                        lv_result.setVisibility(View.GONE);
                                    }
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                }
                                txt_searchValue.setText("");
                            }
                        });
                        break;
                    case R.id.rd_hoSo:
                        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.search_nangcao_hoso));
                        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                        spn_searchType.setAdapter(adapter);
                        ll_phong.setVisibility(View.GONE);
                        ll_vanBan.setVisibility(View.GONE);
                        ll_hoSo.setVisibility(View.GONE);
                        ll_button.setVisibility(View.GONE);
                        ll_header.setVisibility(View.GONE);
                        lv_result.setVisibility(View.GONE);
                        txt_status.setVisibility(View.GONE);
                        spn_searchType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                switch (position) {
                                    case 0:
                                        search_type = "TieuDe";
                                        break;
                                    case 1:
                                        search_type = "HSSo";
                                        break;
                                    case 2:
                                        search_type = "MucLucSo";
                                        break;

                                }
//                                Toast.makeText(getActivity(), search_type, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                                search_type = "TieuDe";
                            }
                        });

                        btn_search.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                search_value = txt_searchValue.getText().toString();
                                try {
                                    arrHoSo = new SearchHoSo(getActivity(), search_type, search_value, pageHoSo, Constants.NUM_ROW_PER_PAGE).execute().get();
                                    if (!arrHoSo.isEmpty()) {
                                        hoSoTrangChuAdapter = new HoSoTrangChuAdapter(getActivity(), R.layout.list_item_hoso_trangchu, arrHoSo);
                                        lv_result.setAdapter(hoSoTrangChuAdapter);
                                        Helper.setListViewHeightBasedOnChildren(lv_result);
                                        total_record = arrHoSo.get(0).getTotal_record();
                                        type = 2;
                                        pageLoad(type, pageHoSo);
                                        txt_header.setText("Kết quả tìm kiếm: Có " + total_record + " kết quả");
                                        ll_header.setVisibility(View.VISIBLE);
                                        ll_phong.setVisibility(View.GONE);
                                        ll_hoSo.setVisibility(View.VISIBLE);
                                        ll_button.setVisibility(View.VISIBLE);
                                        ll_vanBan.setVisibility(View.GONE);
                                        txt_status.setVisibility(View.GONE);
                                        lv_result.setVisibility(View.VISIBLE);
                                    } else {
                                        txt_header.setText("Kết quả tìm kiếm: Có 0 kết quả");
                                        ll_header.setVisibility(View.VISIBLE);
                                        ll_phong.setVisibility(View.GONE);
                                        ll_hoSo.setVisibility(View.VISIBLE);
                                        ll_button.setVisibility(View.GONE);
                                        ll_vanBan.setVisibility(View.GONE);
                                        txt_status.setVisibility(View.VISIBLE);
                                        lv_result.setVisibility(View.GONE);
                                    }
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                }
                                txt_searchValue.setText("");
                            }
                        });
                        break;
                    case R.id.rd_vanBan:
                        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.search_nangcao_vanban));
                        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                        spn_searchType.setAdapter(adapter);
                        ll_phong.setVisibility(View.GONE);
                        ll_vanBan.setVisibility(View.GONE);
                        ll_hoSo.setVisibility(View.GONE);
                        ll_button.setVisibility(View.GONE);
                        ll_header.setVisibility(View.GONE);
                        lv_result.setVisibility(View.GONE);
                        txt_status.setVisibility(View.GONE);
                        spn_searchType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                switch (position) {
                                    case 0:
                                        search_type = "TrichYeuND";
                                        break;
                                    case 1:
                                        search_type = "KyHieuVanBan";
                                        break;
                                    case 2:
                                        search_type = "TacGia";
                                        break;

                                }
//                                Toast.makeText(getActivity(), search_type, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                                search_type = "TrichYeuND";
                            }
                        });

                        btn_search.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                search_value = txt_searchValue.getText().toString();
                                try {
                                    arrVanBan = new SearchVanBan(getActivity(), search_type, search_value, pageVanBan).execute().get();
                                    if (!arrVanBan.isEmpty()) {
                                        vanBanTrangChuAdapter = new VanBanTrangChuAdapter(getActivity(), R.layout.list_item_vanban_trangchu, arrVanBan);
                                        lv_result.setAdapter(vanBanTrangChuAdapter);
                                        Helper.setListViewHeightBasedOnChildren(lv_result);
                                        total_record = arrVanBan.get(0).getTotal_record();
                                        type = 3;
                                        pageLoad(type, pageVanBan);
                                        txt_header.setText("Kết quả tìm kiếm: Có " + total_record + " kết quả");
                                        ll_header.setVisibility(View.VISIBLE);
                                        ll_vanBan.setVisibility(View.VISIBLE);
                                        ll_button.setVisibility(View.VISIBLE);
                                        ll_hoSo.setVisibility(View.GONE);
                                        ll_phong.setVisibility(View.GONE);
                                        txt_status.setVisibility(View.GONE);
                                        lv_result.setVisibility(View.VISIBLE);
                                    } else {
                                        txt_header.setText("Kết quả tìm kiếm: Có 0 kết quả");
                                        ll_header.setVisibility(View.VISIBLE);
                                        ll_vanBan.setVisibility(View.VISIBLE);
                                        ll_button.setVisibility(View.GONE);
                                        ll_hoSo.setVisibility(View.GONE);
                                        ll_phong.setVisibility(View.GONE);
                                        txt_status.setVisibility(View.VISIBLE);
                                        lv_result.setVisibility(View.GONE);
                                    }
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                }
                                txt_searchValue.setText("");
                            }
                        });
                        break;
                }
            }
        });

    }


    public void pageLoad(int type, int page) {
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

    private void setButtonPagingValue(final int type, Button b, String title, final int page) {
        b.setText(title);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), page + "", Toast.LENGTH_SHORT).show();
                switch (type) {
                    case 1:
                        pagePhong = page;
                        try {
                            arrPhong = new SearchPhongAsyncTask(getActivity(), search_type, search_value, pagePhong).execute().get();
                            hoSoTrangChuAdapter = new HoSoTrangChuAdapter(getActivity(), R.layout.list_item_hoso_trangchu, arrHoSo);
                            lv_result.setAdapter(hoSoTrangChuAdapter);
                            Helper.setListViewHeightBasedOnChildren(lv_result);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        pageLoad(type, pagePhong);
                        break;
                    case 2:
                        pageHoSo = page;
                        try {
                            arrHoSo = new SearchHoSo(getActivity(), search_type, search_value, pageHoSo, Constants.NUM_ROW_PER_PAGE).execute().get();
                            hoSoTrangChuAdapter = new HoSoTrangChuAdapter(getActivity(), R.layout.list_item_hoso_trangchu, arrHoSo);
                            lv_result.setAdapter(hoSoTrangChuAdapter);
                            Helper.setListViewHeightBasedOnChildren(lv_result);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        pageLoad(type, pageHoSo);
                        break;
                    case 3:
                        pageVanBan = page;
                        try {
                            arrVanBan = new SearchVanBan(getActivity(), search_type, search_value, pageVanBan).execute().get();
                            vanBanTrangChuAdapter = new VanBanTrangChuAdapter(getActivity(), R.layout.list_item_vanban_trangchu, arrVanBan);
                            lv_result.setAdapter(vanBanTrangChuAdapter);
                            Helper.setListViewHeightBasedOnChildren(lv_result);
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
