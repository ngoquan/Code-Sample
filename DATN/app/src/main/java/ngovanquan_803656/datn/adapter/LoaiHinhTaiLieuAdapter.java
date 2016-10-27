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
import ngovanquan_803656.datn.asynctask.qldm.DeleteLHTLAsyncTask;
import ngovanquan_803656.datn.asynctask.qldm.GetAllDataAysnTask;
import ngovanquan_803656.datn.model.LHTL_ett;

/**
 * Created by ngoquan on 4/4/2016.
 */
public class LoaiHinhTaiLieuAdapter extends ArrayAdapter<LHTL_ett> {
    private Activity activity;
    private int layout;
    private ArrayList<LHTL_ett> arrData = new ArrayList<>();

    public LoaiHinhTaiLieuAdapter(Activity activity, int layout, ArrayList<LHTL_ett> arrData) {
        super(activity, layout, arrData);
        this.activity = activity;
        this.layout = layout;
        this.arrData = arrData;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final LHTL_ett lhtl_ett = arrData.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(activity).inflate(R.layout.list_item_lhtl, parent, false);
        }
        TextView txt_STT = (TextView) convertView.findViewById(R.id.txt_STT);
        TextView txt_MaLHTL = (TextView) convertView.findViewById(R.id.txt_MaLHTL);
        TextView txt_TenLHTL = (TextView) convertView.findViewById(R.id.txt_TenLHTL);
//        TextView txt_GhiChu = (TextView) convertView.findViewById(R.id.txt_GhiChu);
        ImageView img_Status = (ImageView) convertView.findViewById(R.id.img_Status);
        ImageButton btn_Update = (ImageButton) convertView.findViewById(R.id.btn_Update);
        ImageButton btn_Delete = (ImageButton) convertView.findViewById(R.id.btn_Delete);

        txt_STT.setText(lhtl_ett.getSTT() + "");
        txt_MaLHTL.setText(lhtl_ett.getMaLoaiHinhTL() + "");
        txt_TenLHTL.setText(lhtl_ett.getTenLoaiHinhTL());
//        txt_GhiChu.setText(lhtl_ett.getGhiChu() + "");
        if (lhtl_ett.isActive()) {
            img_Status.setImageResource(R.drawable.checked);
        } else {
            img_Status.setImageResource(R.drawable.not_checked);
        }

        btn_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText txt_MaLHTL = (EditText) activity.findViewById(R.id.txt_MaLHTL);
                EditText txt_TenLHTL =(EditText) activity.findViewById(R.id.txt_TenLHTL);
                EditText txt_STT =(EditText) activity.findViewById(R.id.txt_STT);
                EditText txt_GhiChu =(EditText) activity.findViewById(R.id.txt_GhiChu);
                CheckBox cb_Active = (CheckBox) activity.findViewById(R.id.cb_Active);
                Button btn_AddNew = (Button) activity.findViewById(R.id.btn_AddNew);
                Button btn_Update = (Button) activity.findViewById(R.id.btn_Update);
                txt_MaLHTL.setText(String.valueOf(lhtl_ett.getMaLoaiHinhTL()));
                txt_TenLHTL.setText(lhtl_ett.getTenLoaiHinhTL());
                txt_STT.setText(String.valueOf(lhtl_ett.getSTT()));
                txt_GhiChu.setText(lhtl_ett.getGhiChu());
                if (lhtl_ett.isActive())
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
                        long MaLoaiHinhTL = lhtl_ett.getMaLoaiHinhTL();
                        try {
                            LHTL_ett lhtlEtt = new DeleteLHTLAsyncTask(activity, MaLoaiHinhTL).execute().get();
                            arrData.remove(position);
                            new GetAllDataAysnTask(activity, "ID", "", 1).execute();
                            Toast.makeText(getContext(), lhtlEtt.getErrDesc(), Toast.LENGTH_LONG).show();
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
