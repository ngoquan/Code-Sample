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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ngovanquan_803656.datn.R;
import ngovanquan_803656.datn.asynctask.qldm.DeleteTTVLAsyncTask;
import ngovanquan_803656.datn.asynctask.qldm.SearchTTVLAsyncTask;
import ngovanquan_803656.datn.model.TTVL_ett;
import ngovanquan_803656.datn.utils.Constants;

/**
 * Created by ngoquan on 4/14/2016.
 */
public class TinhTrangVatLyAdapter extends ArrayAdapter<TTVL_ett> {
    private Activity activity;
    ArrayList<TTVL_ett> arrData = new ArrayList<>();
    private int layout;

    public TinhTrangVatLyAdapter(Activity activity, int layout, ArrayList<TTVL_ett> arrData) {
        super(activity, layout, arrData);
        this.activity = activity;
        this.layout = layout;
        this.arrData = arrData;
    }

    // convert lại listview của độ tin cậy
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final TTVL_ett ttvl_ett = arrData.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(activity).inflate(R.layout.list_item_dtc, parent, false);
        }
        TextView txt_STT = (TextView) convertView.findViewById(R.id.txt_STT);
        TextView txt_MaTTVL = (TextView) convertView.findViewById(R.id.txt_MaDTC);
        TextView txt_TenTTVL = (TextView) convertView.findViewById(R.id.txt_TenDTC);
//        TextView txt_GhiChu = (TextView) convertView.findViewById(R.id.txt_GhiChu);
//        TextView txt_STT1 = (TextView) convertView.findViewById(R.id.txt_STT1);
        ImageView img_Status = (ImageView) convertView.findViewById(R.id.img_Status);
        ImageButton btn_Update = (ImageButton) convertView.findViewById(R.id.btn_Update);
        ImageButton btn_Delete = (ImageButton) convertView.findViewById(R.id.btn_Delete);

        txt_STT.setText((position + 1) + "");
        txt_MaTTVL.setText(ttvl_ett.getMaTTVL() + "");
        txt_TenTTVL.setText(ttvl_ett.getTenTTVL());
//        txt_GhiChu.setText(ttvl_ett.getGhiChu());
//        txt_STT1.setText(ttvl_ett.getSTT() + "");
        if (ttvl_ett.isActive())
            img_Status.setImageResource(R.drawable.checked);
        else
            img_Status.setImageResource(R.drawable.not_checked);

        btn_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText txt_MaTTVL = (EditText) activity.findViewById(R.id.txt_MaTTVL);
                EditText txt_TenTTVL =(EditText) activity.findViewById(R.id.txt_TenTTVL);
                EditText txt_STT =(EditText) activity.findViewById(R.id.txt_STT);
                EditText txt_GhiChu =(EditText) activity.findViewById(R.id.txt_GhiChu);
                CheckBox cb_Active = (CheckBox) activity.findViewById(R.id.cb_Active);
                Button btn_AddNew = (Button) activity.findViewById(R.id.btn_AddNew);
                Button btn_Update = (Button) activity.findViewById(R.id.btn_Update);
                txt_MaTTVL.setText(String.valueOf(ttvl_ett.getMaTTVL() + ""));
                txt_TenTTVL.setText(ttvl_ett.getTenTTVL());
                txt_STT.setText(ttvl_ett.getSTT() + "");
                txt_GhiChu.setText(ttvl_ett.getGhiChu());
                if (ttvl_ett.isActive())
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
                        long MaTTVL = ttvl_ett.getMaTTVL();
                        try {
                            TTVL_ett ttvlEtt = new DeleteTTVLAsyncTask(activity, MaTTVL).execute().get();
                            arrData.remove(position);
                            new SearchTTVLAsyncTask(activity, "ID", "", 1, Constants.NUM_ROW_PER_PAGE).execute();
                            Toast.makeText(getContext(), ttvlEtt.getErrDesc(), Toast.LENGTH_LONG).show();
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
