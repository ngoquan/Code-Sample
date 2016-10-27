package ngovanquan_803656.datn.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ngovanquan_803656.datn.R;
import ngovanquan_803656.datn.asynctask.qllt.GetNumberOfObject;
import ngovanquan_803656.datn.model.Phong_ett;
import ngovanquan_803656.datn.utils.Constants;

/**
 * Created by NGOQUAN on 6/3/2016.
 */
public class ThongKeHoSoAdapter extends ArrayAdapter<Phong_ett> implements View.OnClickListener{

    private Activity activity;
    private int layout;
    private ArrayList<Phong_ett> arrP = new ArrayList<>();
//    private static int total_HopHS;
//    private static int total_HoSo;
//    private static int total_VanBan;

    public ThongKeHoSoAdapter(Activity activity, int layout, ArrayList<Phong_ett> arrP) {
        super(activity, layout, arrP);
        this.activity = activity;
        this.layout = layout;
        this.arrP = arrP;
//        Log.e("ResultSize", arrP.size() + "");
//        total_HopHS = 0;
//        total_HoSo = 0;
//        total_VanBan = 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Phong_ett phong_ett = arrP.get(position);
//        ViewHolder viewHolder;
//        if (convertView == null) {
//            viewHolder = new ViewHolder();
//            convertView = LayoutInflater.from(activity).inflate(R.layout.list_item_thong_ke_ho_so, parent, false);
//            viewHolder.txt_STT = (TextView) convertView.findViewById(R.id.txt_STT);
//            viewHolder.txt_tenPhong = (TextView) convertView.findViewById(R.id.txt_tenPhong);
//            viewHolder.txt_soHopHS = (TextView) convertView.findViewById(R.id.txt_soHopHS);
//            viewHolder.txt_soHoSo = (TextView) convertView.findViewById(R.id.txt_soHoSo);
//            viewHolder.txt_soVanBan = (TextView) convertView.findViewById(R.id.txt_soVanBan);
//            convertView.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }

        if (convertView == null)
            convertView = LayoutInflater.from(activity).inflate(R.layout.list_item_thong_ke_ho_so, parent, false);
        TextView txt_STT = (TextView) convertView.findViewById(R.id.txt_STT);
        TextView txt_tenPhong = (TextView) convertView.findViewById(R.id.txt_tenPhong);
        TextView txt_soHopHS = (TextView) convertView.findViewById(R.id.txt_soHopHS);
        TextView txt_soHoSo = (TextView) convertView.findViewById(R.id.txt_soHoSo);
        TextView txt_soVanBan = (TextView) convertView.findViewById(R.id.txt_soVanBan);
        String MaPhong = phong_ett.getMaPhong() + "";
//        if ((position + 1) != arrP.size()) {
            txt_STT.setText((position + 1) + "");
            txt_tenPhong.setText(phong_ett.getTenPhong());

            try {
                int soHopHS = new GetNumberOfObject(activity, Constants.F_GET_NB_HOPHS_PHONG, MaPhong).execute().get();
//                total_HopHS += soHopHS;
                int soHoSo = new GetNumberOfObject(activity, Constants.F_GET_NB_HOSO_PHONG, MaPhong).execute().get();
//                total_HoSo += soHoSo;
                int soVanBan = new GetNumberOfObject(activity, Constants.F_GET_NB_VB_PHONG, MaPhong).execute().get();
//                total_HoSo += soVanBan;

                txt_soHopHS.setText(soHopHS + "");
                txt_soHoSo.setText(soHoSo + "");
                txt_soVanBan.setText(soVanBan + "");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
//        }
//        else {
//            txt_STT.setText("");
//            txt_tenPhong.setText(phong_ett.getTenPhong());
//            txt_soHopHS.setText(total_HopHS + "");
//            txt_soHoSo.setText(total_HoSo + "");
//            txt_soVanBan.setText(total_VanBan + "");
//        }

        txt_STT.setOnClickListener(this);
        txt_tenPhong.setOnClickListener(this);
        txt_soHopHS.setOnClickListener(this);
        txt_soHoSo.setOnClickListener(this);
        txt_soVanBan.setOnClickListener(this);


        return convertView;
    }

    @Override
    public void onClick(View v) {

    }

    public class ViewHolder {
        private TextView txt_STT;
        private TextView txt_tenPhong;
        private TextView txt_soHopHS;
        private TextView txt_soHoSo;
        private TextView txt_soVanBan;
    }
}
