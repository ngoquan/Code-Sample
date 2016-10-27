package ngovanquan_803656.datn.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ngovanquan_803656.datn.R;
import ngovanquan_803656.datn.asynctask.qllt.DeleteHopHoSoAsyncTask;
import ngovanquan_803656.datn.asynctask.qllt.SearchHopHoSoAsyncTask;
import ngovanquan_803656.datn.asynctask.qllt.SearchPhongAsyncTask;
import ngovanquan_803656.datn.fragment.qldm.HopHoSoFragment;
import ngovanquan_803656.datn.model.DoMat_ett;
import ngovanquan_803656.datn.model.HopHoSo_ett;
import ngovanquan_803656.datn.model.Phong_ett;
import ngovanquan_803656.datn.utils.Constants;
import ngovanquan_803656.datn.utils.Helper;

/**
 * Created by ngoquan on 5/12/2016.
 */
public class HopHoSoAdapter extends ArrayAdapter<HopHoSo_ett> {
    private Activity activity;
    private int layout;
    private ArrayList<HopHoSo_ett> arrData = new ArrayList<>();
    private ArrayList<Phong_ett> arrPhong = new ArrayList<>();
    public HopHoSoAdapter(Activity activity, int layout, ArrayList<HopHoSo_ett> arrData) {
        super(activity, layout, arrData);
        this.activity = activity;
        this.layout = layout;
        this.arrData = arrData;
        try {
            arrPhong = new SearchPhongAsyncTask(activity, "ID", "", 0).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final HopHoSo_ett hopHoSo_ett = arrData.get(position);
        if (convertView == null)
            convertView = LayoutInflater.from(activity).inflate(R.layout.list_item_hophs, parent, false);
        TextView txt_STT = (TextView) convertView.findViewById(R.id.txt_STT);
        TextView txt_MaHopHS = (TextView) convertView.findViewById(R.id.txt_MaHopHS);
        TextView txt_TenHopHS = (TextView) convertView.findViewById(R.id.txt_TenHopHS);
        TextView txt_TenPhong = (TextView) convertView.findViewById(R.id.txt_TenPhong);
        ImageView img_Status = (ImageView) convertView.findViewById(R.id.img_Status);
        ImageButton btn_Update = (ImageButton) convertView.findViewById(R.id.btn_Update);
        ImageButton btn_Delete = (ImageButton) convertView.findViewById(R.id.btn_Delete);

        txt_STT.setText(((HopHoSoFragment.page_current - 1) * 10) + position + "");
        txt_MaHopHS.setText(hopHoSo_ett.getMaHopHS() + "");
        txt_TenHopHS.setText(hopHoSo_ett.getTenHop());
        for (Phong_ett item : arrPhong) {
            if (item.getMaPhong() == hopHoSo_ett.getMaPhong())
                txt_TenPhong.setText(item.getTenPhong());
        }
        if (hopHoSo_ett.isActive())
            img_Status.setImageResource(R.drawable.checked);
        else
            img_Status.setImageResource(R.drawable.not_checked);
        btn_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner spn_MaPhong = (Spinner) activity.findViewById(R.id.spn_MaPhong);
                TextView txt_MaHopHS = (TextView) activity.findViewById(R.id.txt_MaHopHS);
                TextView txt_TenHopHS = (TextView) activity.findViewById(R.id.txt_TenHopHS);
                TextView txt_GhiChu = (TextView) activity.findViewById(R.id.txt_GhiChu);
                CheckBox cb_Active = (CheckBox) activity.findViewById(R.id.cb_Active);
                Button btn_AddNew = (Button) activity.findViewById(R.id.btn_AddNew);
                Button btn_Update = (Button) activity.findViewById(R.id.btn_Update);

                for (int i = 0; i < arrPhong.size(); i++) {
                    if (arrPhong.get(i).getMaPhong() == hopHoSo_ett.getMaPhong())
                        spn_MaPhong.setSelection(i);
                }

                txt_MaHopHS.setText(hopHoSo_ett.getMaHopHS() + "");
                txt_TenHopHS.setText(hopHoSo_ett.getTenHop());
                txt_GhiChu.setText(hopHoSo_ett.getGhiChu());
                if (hopHoSo_ett.isActive())
                    cb_Active.setChecked(true);
                else
                    cb_Active.setChecked(false);
                btn_AddNew.setVisibility(View.GONE);
                btn_Update.setVisibility(View.VISIBLE);
            }
        });

        btn_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
                alertDialog.setIcon(R.drawable.question_delete);
                alertDialog.setTitle(activity.getResources().getString(R.string.title_delete));
                alertDialog.setMessage(activity.getResources().getString(R.string.message_delete));

                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "You selected No", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        long MaHopHS = hopHoSo_ett.getMaHopHS();
                        try {
                            HopHoSo_ett hopHoSoEtt = new DeleteHopHoSoAsyncTask(activity, Constants.FUNCTION_DELETE, MaHopHS).execute().get();
                            arrData.remove(position);
                            ListView lv_HopHS = (ListView) activity.findViewById(R.id.lv_HopHS);
                            ArrayList<HopHoSo_ett> arrHopHoSo = new SearchHopHoSoAsyncTask(activity, Constants.FUNCTION_SEARCH, "", "", "ID", "", 1, Constants.NUM_ROW_PER_PAGE).execute().get();
                            HopHoSoAdapter hopHoSoAdapter = new HopHoSoAdapter(activity, R.layout.list_item_hophs, arrHopHoSo);
                            lv_HopHS.setAdapter(hopHoSoAdapter);
                            Helper.setListViewHeightBasedOnChildren(lv_HopHS);
                            Toast.makeText(getContext(), hopHoSoEtt.getErrDesc(), Toast.LENGTH_LONG).show();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }

                    }
                });
                alertDialog.show();
            }
        });



        return convertView;
    }
}
