package ngovanquan_803656.datn.fragment.qllt;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ngovanquan_803656.datn.R;
import ngovanquan_803656.datn.adapter.ExpandableListAdapter;
import ngovanquan_803656.datn.adapter.GridHopSoSoAdapter;
import ngovanquan_803656.datn.asynctask.qllt.GetNumBerHopHoSoAsyncTask;
import ngovanquan_803656.datn.asynctask.qllt.GetNumberVanBanAsyncTask;
import ngovanquan_803656.datn.asynctask.qllt.SearchHoSoAsyncTask;
import ngovanquan_803656.datn.asynctask.qllt.SearchHopHoSoAsyncTask;
import ngovanquan_803656.datn.asynctask.qllt.SearchPhongAsyncTask;
import ngovanquan_803656.datn.asynctask.qllt.SearchVanBanAsyncTask;
import ngovanquan_803656.datn.model.HoSo_ett;
import ngovanquan_803656.datn.model.HopHoSo_ett;
import ngovanquan_803656.datn.model.Phong_ett;
import ngovanquan_803656.datn.model.VanBan_ett;
import ngovanquan_803656.datn.utils.Constants;
import ngovanquan_803656.datn.utils.Helper;

/**
 * Created by ngoquan on 5/14/2016.
 */
public class HoSoAndVanBanFragment extends Fragment {
    ExpandableListView exp_listview;
    GridView grid_view;

    ExpandableListAdapter adapter;
    List<Phong_ett> listHeader = new ArrayList<>();
    HashMap<Phong_ett, List<HopHoSo_ett>> listChild = new HashMap<>();

    ArrayList<HopHoSo_ett> arrHopHoSo = new ArrayList<>();
    GridHopSoSoAdapter gridHopSoSoAdapter;

    public static String MaP, MaHop;
    private int pageHopHoSo = 1;
    private int pageHoSo;
    private int pageTotal;


    public HoSoAndVanBanFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hoso_and_vanban, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addControls();
//        prepareData();
        listHeader = createListHeader();
        listChild  = createListChild("", 1, "", 1);
        adapter = new ExpandableListAdapter(getActivity(), listHeader, listChild);
        exp_listview.setAdapter(adapter);


    }

    private void addControls() {
        exp_listview = (ExpandableListView) getView().findViewById(R.id.exp_listview);
        grid_view = (GridView) getView().findViewById(R.id.grid_view);

    }


    private List<Phong_ett> createListHeader() {
        List<Phong_ett> listHeader = new ArrayList<>();
        ArrayList<Phong_ett> arrPhong;
        try {
            arrPhong = new SearchPhongAsyncTask(getActivity(), "ID", "", 1).execute().get();
            for (Phong_ett phong_ett : arrPhong) {
                String MaPhong = String.valueOf(phong_ett.getMaPhong());
                int sl_hophs = new GetNumBerHopHoSoAsyncTask(getActivity(), "HopHoSoByPhong", MaPhong, 1).execute().get();
                listHeader.add(new Phong_ett(phong_ett.getMaCQLT(), phong_ett.getMaPhong(), phong_ett.getTenPhong(), phong_ett.getLichSuHinhThanh(),
                        phong_ett.getThoiGianTaiLieu(), phong_ett.getTongSoTaiLieu(), phong_ett.getSoTaiLieuDaChinhLy(), phong_ett.getSoTaiLieuChuaChinhLy(),
                        phong_ett.getCacNhomTaiLieu(), phong_ett.getMaNN(), phong_ett.getThoiGianNhapTaiLieu(), phong_ett.getCongCuTraCuu(), phong_ett.getLapBanSaoBaoHiem(),
                        phong_ett.getGhiChu(), sl_hophs));
            }
            return listHeader;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private HashMap<Phong_ett, List<HopHoSo_ett>> createListChild(String MaP, int pageHopHoSo, String MaHop, int pageHoSo) {
        HashMap<Phong_ett, List<HopHoSo_ett>> listChild = new HashMap<>();
        ArrayList<HopHoSo_ett> arrHopHoSo;
        ArrayList<HopHoSo_ett> arrHopHoSo1 = new ArrayList<>();

        for (Phong_ett phong_ett : listHeader) {
            String MaPhong = String.valueOf(phong_ett.getMaPhong());
            try {
                if (MaPhong.equals(MaP))
                    arrHopHoSo = new SearchHopHoSoAsyncTask(getActivity(), Constants.FUNCTION_SEARCH, "", "", "HopHoSoByPhong", MaPhong, pageHopHoSo, Constants.NUM_ROW_PER_PAGE).execute().get();
                else
                    arrHopHoSo = new SearchHopHoSoAsyncTask(getActivity(), Constants.FUNCTION_SEARCH, "", "", "HopHoSoByPhong", MaPhong, 1, Constants.NUM_ROW_PER_PAGE).execute().get();
                arrHopHoSo1 = new ArrayList<>();
                for (HopHoSo_ett hopHoSo_ett : arrHopHoSo) {
                    String MaHopHS = String.valueOf(hopHoSo_ett.getMaHopHS());
                    ArrayList<HoSo_ett> arrHoSo;
                    if (MaHopHS.equals(MaHop))
                        arrHoSo = new SearchHoSoAsyncTask(getActivity(), "HoSoByHopHoSo", MaHopHS, pageHoSo, Constants.NUM_ROW_PER_PAGE).execute().get();
                    else
                        arrHoSo = new SearchHoSoAsyncTask(getActivity(), "HoSoByHopHoSo", MaHopHS, 1, Constants.NUM_ROW_PER_PAGE).execute().get();
//                    ArrayList<HoSo_ett> arrHoSo1 = new ArrayList<>();
                    int sl_hoso = 0;
                    if (!arrHoSo.isEmpty())
                        sl_hoso = arrHoSo.get(0).getTotal_record();
//                    int sl_vanban = 0;
//                    for (HoSo_ett hoSo_ett : arrHoSo) {
////                        sl_vanban = new GetNumberVanBanAsyncTask(getActivity(), "VbbyHs", hoSo_ett.getHoSoSo(), 1).execute().get();
//                        HoSo_ett hoSoEtt = new HoSo_ett(hoSo_ett.getMaPhong(), hoSo_ett.getMucLucSo(), hoSo_ett.getMaHopHS(), hoSo_ett.getHoSoSo(), hoSo_ett.getKHTT(),
//                                hoSo_ett.getTieuDeHs(), hoSo_ett.getChuGiai(), hoSo_ett.getThoiGianBatDau(), hoSo_ett.getThoiGianKetThuc(), hoSo_ett.getMaNN(), hoSo_ett.getButTich(),
//                                hoSo_ett.getSoLuongTo(), hoSo_ett.getMaTHBQ(), hoSo_ett.getMaCDSD(), hoSo_ett.getMaTTVL(), sl_vanban);
//                        arrHoSo1.add(hoSoEtt);
//                        if (sl_hoso == 0)
//                            sl_hoso = hoSo_ett.getTotal_record();
//                    }
                    arrHopHoSo1.add(new HopHoSo_ett(hopHoSo_ett.getMaHopHS(), hopHoSo_ett.getTenHop(), hopHoSo_ett.getGhiChu(), hopHoSo_ett.isActive(),
                            hopHoSo_ett.getMaPhong(), sl_hoso, arrHoSo));
//                    arrHopHoSo1.add(new HopHoSo_ett(hopHoSo_ett.getMaHopHS(), hopHoSo_ett.getTenHop(), hopHoSo_ett.getGhiChu(), hopHoSo_ett.isActive(),
//                            hopHoSo_ett.getMaPhong(), sl_hoso, arrHoSo1));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            listChild.put(phong_ett, arrHopHoSo1);
        }
        return listChild;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
