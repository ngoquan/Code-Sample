package ngovanquan_803656.datn.fragment.tkbc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ngovanquan_803656.datn.R;
import ngovanquan_803656.datn.adapter.MucLucVanBanAdapter;
import ngovanquan_803656.datn.adapter.SpinnerPhongAdapter;
import ngovanquan_803656.datn.asynctask.qllt.SearchPhongAsyncTask;
import ngovanquan_803656.datn.asynctask.qllt.SearchVanBanAsyncTask;
import ngovanquan_803656.datn.model.Phong_ett;
import ngovanquan_803656.datn.model.VanBan_ett;
import ngovanquan_803656.datn.utils.ConnectionDetector;
import ngovanquan_803656.datn.utils.Constants;
import ngovanquan_803656.datn.utils.Helper;

/**
 * Created by NGOQUAN on 6/2/2016.
 */
public class MucLucVanBanFragment extends Fragment {
    TextView txt_header, txt_status;
    Spinner spn_searchType;
    ListView lv_mucLucVB;
    Button btn_first, btn_back, btn_jump_back, btn_1, btn_2, btn_3, btn_4, btn_5, btn_jump_next, btn_next, btn_end;
    LinearLayout ll_button;

    ArrayList<VanBan_ett> arrVanBan = new ArrayList<>();
    MucLucVanBanAdapter mucLucVanBanAdapter;
    ArrayList<Phong_ett> arrP = new ArrayList<>();
    SpinnerPhongAdapter spinnerPhongAdapter;
    public static int total_record, pageTotal, type;
    public static int pageVanBan = 1;
    private String MaPhong = "";
    ConnectionDetector cd;

    public MucLucVanBanFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_muc_luc_van_ban, container, false);
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
            arrP = new SearchPhongAsyncTask(getActivity(), "ID", "", 1).execute().get();
            arrP.add(0, new Phong_ett("", 0, "Tất cả các phông", "", "", 0, 0, 0, "", 0, "", "", "", ""));
            spinnerPhongAdapter = new SpinnerPhongAdapter(getActivity(), R.layout.list_item_spinner, arrP);
            spn_searchType.setAdapter(spinnerPhongAdapter);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        addEvents();
    }


    private void addControls() {
        txt_status = (TextView) getView().findViewById(R.id.txt_status);
        txt_header = (TextView) getView().findViewById(R.id.txt_header);
        spn_searchType = (Spinner) getView().findViewById(R.id.spn_searchType);
        lv_mucLucVB = (ListView) getView().findViewById(R.id.lv_mucLucVB);
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
        ll_button = (LinearLayout) getView().findViewById(R.id.ll_button);
    }

    private void addEvents() {

        lv_mucLucVB.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        spn_searchType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MaPhong = String.valueOf(arrP.get(position).getMaPhong());
                if (MaPhong.equals("0"))
                    MaPhong = "";
//                Toast.makeText(getActivity(), MaPhong, Toast.LENGTH_SHORT).show();
                try {
                    arrVanBan = new SearchVanBanAsyncTask(getActivity(), "VanBanByPhong", MaPhong, pageVanBan).execute().get();
                    if (!arrVanBan.isEmpty()) {
                        mucLucVanBanAdapter = new MucLucVanBanAdapter(getActivity(), R.layout.list_item_muc_luc_van_ban, arrVanBan);
                        lv_mucLucVB.setAdapter(mucLucVanBanAdapter);
                        Helper.setListViewHeightBasedOnChildren(lv_mucLucVB);
                        type = 1;
                        total_record = arrVanBan.get(0).getTotal_record();
                        txt_header.setText("Có " + total_record + " văn bản");
                        pageLoad(type, pageVanBan);
                        ll_button.setVisibility(View.VISIBLE);
                        txt_status.setVisibility(View.GONE);
                        lv_mucLucVB.setVisibility(View.VISIBLE);
                    } else {
                        txt_status.setVisibility(View.VISIBLE);
                        ll_button.setVisibility(View.GONE);
                        lv_mucLucVB.setVisibility(View.GONE);
                        txt_header.setText("Có 0 văn bản");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                MaPhong = "";
//                Toast.makeText(getActivity(), MaPhong, Toast.LENGTH_SHORT).show();
                try {
                    arrVanBan = new SearchVanBanAsyncTask(getActivity(), "VanBanByPhong", MaPhong, pageVanBan).execute().get();
                    if (!arrVanBan.isEmpty()) {
                        mucLucVanBanAdapter = new MucLucVanBanAdapter(getActivity(), R.layout.list_item_muc_luc_van_ban, arrVanBan);
                        lv_mucLucVB.setAdapter(mucLucVanBanAdapter);
                        Helper.setListViewHeightBasedOnChildren(lv_mucLucVB);
                        type = 1;
                        total_record = arrVanBan.get(0).getTotal_record();
                        txt_header.setText("Có " + total_record + " văn bản");
                        pageLoad(type, pageVanBan);
                        ll_button.setVisibility(View.VISIBLE);
                        txt_status.setVisibility(View.GONE);
                        lv_mucLucVB.setVisibility(View.VISIBLE);
                    } else {
                        txt_status.setVisibility(View.VISIBLE);
                        ll_button.setVisibility(View.GONE);
                        lv_mucLucVB.setVisibility(View.GONE);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
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
                        pageVanBan = page;
                        try {
                            arrVanBan = new SearchVanBanAsyncTask(getActivity(), "VanBanByPhong", MaPhong, pageVanBan).execute().get();
                            mucLucVanBanAdapter = new MucLucVanBanAdapter(getActivity(), R.layout.list_item_muc_luc_van_ban, arrVanBan);
                            lv_mucLucVB.setAdapter(mucLucVanBanAdapter);
                            Helper.setListViewHeightBasedOnChildren(lv_mucLucVB);
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
