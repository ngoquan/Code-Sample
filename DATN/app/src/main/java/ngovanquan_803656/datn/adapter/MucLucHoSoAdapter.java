package ngovanquan_803656.datn.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ngovanquan_803656.datn.R;
import ngovanquan_803656.datn.asynctask.qllt.SearchHopHoSoAsyncTask;
import ngovanquan_803656.datn.model.HoSo_ett;
import ngovanquan_803656.datn.model.HopHoSo_ett;
import ngovanquan_803656.datn.utils.Constants;

/**
 * Created by NGOQUAN on 6/2/2016.
 */
public class MucLucHoSoAdapter extends ArrayAdapter<HoSo_ett> implements View.OnClickListener {

    private Activity activity;
    private int layout;
    private ArrayList<HoSo_ett> arrHoSo = new ArrayList<>();
    ArrayList<HopHoSo_ett> arrHopHS = new ArrayList<>();

    public MucLucHoSoAdapter(Activity activity, int layout, ArrayList<HoSo_ett> arrHoSo) {
        super(activity, layout, arrHoSo);
        this.activity = activity;
        this.layout = layout;
        this.arrHoSo = arrHoSo;
        try {
            arrHopHS = new SearchHopHoSoAsyncTask(activity, Constants.FUNCTION_SEARCH, "", "", "ID", "", 1, 0).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HoSo_ett hoSo_ett = arrHoSo.get(position);
        if (convertView == null)
            convertView = LayoutInflater.from(activity).inflate(R.layout.list_item_muc_luc_ho_so, parent, false);
        TextView txt_hopHS = (TextView) convertView.findViewById(R.id.txt_hopHS);
        TextView txt_hoSoSo = (TextView) convertView.findViewById(R.id.txt_hoSoSo);
        TextView txt_tieuDeHS = (TextView) convertView.findViewById(R.id.txt_tieuDeHS);
        TextView txt_NTBDKT = (TextView) convertView.findViewById(R.id.txt_NTBDKT);
        TextView txt_SLT = (TextView) convertView.findViewById(R.id.txt_SLT);

        for (HopHoSo_ett hopHoSo_ett : arrHopHS) {
            if (hopHoSo_ett.getMaHopHS() == hoSo_ett.getMaHopHS())
                txt_hopHS.setText(hopHoSo_ett.getTenHop());
        }

        txt_hoSoSo.setText(hoSo_ett.getIDShow());
        txt_tieuDeHS.setText(hoSo_ett.getTieuDeHs());
        txt_NTBDKT.setText(hoSo_ett.getThoiGianBatDau() + " - " + hoSo_ett.getThoiGianKetThuc());
        txt_SLT.setText(hoSo_ett.getSoLuongTo());
        txt_hopHS.setOnClickListener(this);
        txt_hoSoSo.setOnClickListener(this);
        txt_tieuDeHS.setOnClickListener(this);
        txt_NTBDKT.setOnClickListener(this);
        txt_SLT.setOnClickListener(this);


        return convertView;
    }

    @Override
    public void onClick(View v) {

    }
}
