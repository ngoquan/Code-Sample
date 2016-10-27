package ngovanquan_803656.datn.fragment.qllt;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ngovanquan_803656.datn.R;
import ngovanquan_803656.datn.adapter.MenuPhongAdapter;
import ngovanquan_803656.datn.asynctask.qllt.GetNumBerHopHoSoAsyncTask;
import ngovanquan_803656.datn.asynctask.qllt.SearchPhongAsyncTask;
import ngovanquan_803656.datn.model.Phong_ett;
import ngovanquan_803656.datn.utils.Helper;

/**
 * Created by NGOQUAN on 5/27/2016.
 */
public class QuanLyLuuTruFragment extends Fragment {

    ListView lv_qllt;
    ArrayList<Phong_ett> arrP = new ArrayList<>();
    MenuPhongAdapter menuPhongAdapter;
    public QuanLyLuuTruFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quan_ly_luu_tru, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addControls();
        arrP = initializePhong();
        menuPhongAdapter = new MenuPhongAdapter(getActivity(), R.layout.list_item_phong, arrP);
        lv_qllt.setAdapter(menuPhongAdapter);
        Helper.setListViewHeightBasedOnChildren(lv_qllt);
    }

    private void addControls() {
        lv_qllt = (ListView) getView().findViewById(R.id.lv_qllt);
    }

    private ArrayList<Phong_ett> initializePhong() {
        ArrayList<Phong_ett> arrP = new ArrayList<>();
        ArrayList<Phong_ett> arrPhong;
        try {
            arrPhong = new SearchPhongAsyncTask(getActivity(), "ID", "", 1).execute().get();
            for (Phong_ett phong_ett : arrPhong) {
                String MaPhong = String.valueOf(phong_ett.getMaPhong());
                int sl_hophs = new GetNumBerHopHoSoAsyncTask(getActivity(), "HopHoSoByPhong", MaPhong, 1).execute().get();
                arrP.add(new Phong_ett(phong_ett.getMaCQLT(), phong_ett.getMaPhong(), phong_ett.getTenPhong(), phong_ett.getLichSuHinhThanh(),
                        phong_ett.getThoiGianTaiLieu(), phong_ett.getTongSoTaiLieu(), phong_ett.getSoTaiLieuDaChinhLy(), phong_ett.getSoTaiLieuChuaChinhLy(),
                        phong_ett.getCacNhomTaiLieu(), phong_ett.getMaNN(), phong_ett.getThoiGianNhapTaiLieu(), phong_ett.getCongCuTraCuu(), phong_ett.getLapBanSaoBaoHiem(),
                        phong_ett.getGhiChu(), sl_hophs));
            }
            return arrP;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }


}
