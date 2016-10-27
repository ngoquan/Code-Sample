package ngovanquan_803656.datn.fragment.tkbc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ngovanquan_803656.datn.R;
import ngovanquan_803656.datn.adapter.DanhSachPhongAdapter;
import ngovanquan_803656.datn.asynctask.qllt.GetNumberOfObject;
import ngovanquan_803656.datn.asynctask.qllt.SearchPhongAsyncTask;
import ngovanquan_803656.datn.model.Phong_ett;
import ngovanquan_803656.datn.utils.ConnectionDetector;
import ngovanquan_803656.datn.utils.Constants;
import ngovanquan_803656.datn.utils.Helper;

/**
 * Created by NGOQUAN on 6/2/2016.
 */
public class DanhSachPhongFragment extends Fragment {

//    TextView txt_header, txt_status;
//    Spinner spn_searchType;
    ListView lv_DSP;
    Button btn_first, btn_back, btn_jump_back, btn_1, btn_2, btn_3, btn_4, btn_5, btn_jump_next, btn_next, btn_end;
    LinearLayout ll_button;

    ArrayList<Phong_ett> arrP = new ArrayList<>();
    DanhSachPhongAdapter danhSachPhongAdapter;
    public static int total_record, pageTotal, type;
    public static int pagePhong = 1;
//    private String MaPhong = "";
    ConnectionDetector cd;
    public DanhSachPhongFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_danh_sach_phong, container, false);

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
            arrP = new SearchPhongAsyncTask(getActivity(), "ID", "", pagePhong).execute().get();
            danhSachPhongAdapter = new DanhSachPhongAdapter(getActivity(), R.layout.list_item_danh_sach_phong, arrP);
            lv_DSP.setAdapter(danhSachPhongAdapter);
            Helper.setListViewHeightBasedOnChildren(lv_DSP);
//            type = 1;
//            total_record = arrP.get(0).getTotal_record();
//            pageLoad(type, pagePhong);
//            ll_button.setVisibility(View.VISIBLE);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }


    private void addControls() {
        lv_DSP = (ListView) getView().findViewById(R.id.lv_DSP);
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

//    public void pageLoad(int type, int page) {
////        gone button
//        btn_first.setVisibility(View.GONE);
//        btn_back.setVisibility(View.GONE);
//        btn_jump_back.setVisibility(View.GONE);
//        btn_1.setVisibility(View.GONE);
//        btn_2.setVisibility(View.GONE);
//        btn_3.setVisibility(View.GONE);
//        btn_4.setVisibility(View.GONE);
//        btn_5.setVisibility(View.GONE);
//        btn_jump_next.setVisibility(View.GONE);
//        btn_next.setVisibility(View.GONE);
//        btn_end.setVisibility(View.GONE);
////        if (arrHopHoSo != null) {
////        calculate page total
//        pageTotal = (int) Math.ceil((double) total_record / Constants.NUM_ROW_PER_PAGE);
//
////        thuat toan bat dau
//        if (page < 0)
//            page = 1;
//        if (pageTotal > 1) {
//            if (page > pageTotal)
//                page = pageTotal;
////            show btn_first, btn_back, btn_next, btn_end
//            if (page > 1) {
//                setButtonPagingValue(type, btn_first, "<<", 1);
//                btn_first.setVisibility(View.VISIBLE);
//                setButtonPagingValue(type, btn_back, "<", page - 1);
//                btn_back.setVisibility(View.VISIBLE);
//            }
//            if ((page + 1) <= pageTotal) {
//                setButtonPagingValue(type, btn_next, ">", page + 1);
//                btn_next.setVisibility(View.VISIBLE);
//                setButtonPagingValue(type, btn_end, ">>", pageTotal);
//                btn_end.setVisibility(View.VISIBLE);
//            }
//
//            //trường hợp page_curr nằm đúng trong khoảng thì thực hiện xử lý paging
//            setButtonPagingValue(type, btn_3, page + "", page);
//            btn_3.setVisibility(View.VISIBLE);
//            if (pageTotal == 2) {
//                setButtonPagingValue(type, btn_3, page + "", page);
//                if (page == 1) {
//                    setButtonPagingValue(type, btn_4, "2", 2);
//                    btn_4.setVisibility(View.VISIBLE);
//                } else {
//                    setButtonPagingValue(type, btn_2, "1", 1);
//                    btn_2.setVisibility(View.VISIBLE);
//                }
//            } else {
//                //set btn_2, btn_1 và btn_jump_back theo từng trường hợp
//                if (page < 3) {
//                    //nếu page_curr < 3 thì xử lý cho trường hợp  =  2 & 1
//                    switch (page) {
//                        case 2:
//                            setButtonPagingValue(type, btn_2, "1", 1);
//                            btn_2.setVisibility(View.VISIBLE);
//                            break;
//                    }
//                } else {
//                    setButtonPagingValue(type, btn_2, (page - 1) + "", page - 1);
//                    btn_2.setVisibility(View.VISIBLE);
//                    setButtonPagingValue(type, btn_1, (page - 2) + "", page - 2);
//                    btn_1.setVisibility(View.VISIBLE);
//                    if ((page - 2) > Constants.PAGE_JUMP_PAGING) {
//                        //page_curr > 3 và page_curr - page_jump > 0
//                        setButtonPagingValue(type, btn_jump_back, "...", (page - 2 - Constants.PAGE_JUMP_PAGING));
//                        btn_jump_back.setVisibility(View.VISIBLE);
//                    }
//                }
//
//                //set btn_3, btn_4 và p_jump_next theo từng trường hợp
//                if ((page + 3) <= pageTotal) {
//                    setButtonPagingValue(type, btn_4, (page + 1) + "", (page + 1));
//                    btn_4.setVisibility(View.VISIBLE);
//                    setButtonPagingValue(type, btn_5, (page + 2) + "", (page + 2));
//                    btn_5.setVisibility(View.VISIBLE);
//                    if ((pageTotal - page - 2) > Constants.PAGE_JUMP_PAGING) {
//                        setButtonPagingValue(type, btn_jump_next, "...", (page + 2 + Constants.PAGE_JUMP_PAGING));
//                        btn_jump_next.setVisibility(View.VISIBLE);
//                    }
//                } else {
//                    //nếu page_curr + 3 > page_total thì xử lý cho trường hợp  =  +2 & +1
//                    if ((page + 1) == pageTotal) {
//                        setButtonPagingValue(type, btn_4, (page + 1) + "", (page + 1));
//                        btn_4.setVisibility(View.VISIBLE);
//                    } else {
//                        if ((page + 2) == pageTotal) {
//                            setButtonPagingValue(type, btn_4, (page + 1) + "", (page + 1));
//                            btn_4.setVisibility(View.VISIBLE);
//                            setButtonPagingValue(type, btn_5, (page + 2) + "", (page + 2));
//                            btn_5.setVisibility(View.VISIBLE);
//                        }
//                    }
//                }
//            }
//
//        } else {
//            page = pageTotal;
//        }
////        }
//    }
//
//    private void setButtonPagingValue(final int type, Button b, String title, final int page) {
//        b.setText(title);
//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getActivity(), page + "", Toast.LENGTH_SHORT).show();
//                switch (type) {
//                    case 1:
//                        pagePhong = page;
//                        try {
//                            arrP = new SearchPhongAsyncTask(getActivity(), "ID", "", pagePhong).execute().get();
//                            danhSachPhongAdapter = new DanhSachPhongAdapter(getActivity(), R.layout.list_item_danh_sach_phong, arrP);
//                            lv_DSP.setAdapter(danhSachPhongAdapter);
//                            Helper.setListViewHeightBasedOnChildren(lv_DSP);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        } catch (ExecutionException e) {
//                            e.printStackTrace();
//                        }
//                        pageLoad(type, pagePhong);
//                        break;
//                }
//
//            }
//        });
//    }


}
