package ngovanquan_803656.datn.fragment.tkbc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ngovanquan_803656.datn.R;
import ngovanquan_803656.datn.adapter.DanhSachPhongAdapter;
import ngovanquan_803656.datn.adapter.ThongKeHoSoAdapter;
import ngovanquan_803656.datn.asynctask.qllt.SearchPhongAsyncTask;
import ngovanquan_803656.datn.model.Phong_ett;
import ngovanquan_803656.datn.utils.ConnectionDetector;
import ngovanquan_803656.datn.utils.Helper;

/**
 * Created by NGOQUAN on 6/3/2016.
 */
public class ThongKeHoSoFragment extends Fragment {
    ListView lv_TKHS;
    ArrayList<Phong_ett> arrP = new ArrayList<>();
    ThongKeHoSoAdapter thongKeHoSoAdapter;
    public static int total_record, pageTotal, type;
    public static int pagePhong = 1;
    //    private String MaPhong = "";
    ConnectionDetector cd;
    public ThongKeHoSoFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_thong_ke_ho_so, container, false);
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
//            arrP.add(new Phong_ett("", 0, "Tổng số", "", "", 0, 0, 0, "", 0, "", "", "", ""));
            thongKeHoSoAdapter = new ThongKeHoSoAdapter(getActivity(), R.layout.list_item_thong_ke_ho_so, arrP);
            lv_TKHS.setAdapter(thongKeHoSoAdapter);
            Helper.setListViewHeightBasedOnChildren(lv_TKHS);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }

    private void addControls() {
        lv_TKHS = (ListView) getView().findViewById(R.id.lv_TKHS);
    }
}
