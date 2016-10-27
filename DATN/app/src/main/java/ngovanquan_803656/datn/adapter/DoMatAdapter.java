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
import ngovanquan_803656.datn.asynctask.qldm.DeleteDoMatAsyncTask;
import ngovanquan_803656.datn.asynctask.qldm.SearchDoMatAsyncTask;
import ngovanquan_803656.datn.model.DoMat_ett;
import ngovanquan_803656.datn.utils.Constants;

/**
 * Created by ngoquan on 4/13/2016.
 */
public class DoMatAdapter extends ArrayAdapter<DoMat_ett> {
    private Activity activity;
    ArrayList<DoMat_ett> arrData = new ArrayList<>();
    private int layout;

    public DoMatAdapter(Activity activity, int layout, ArrayList<DoMat_ett> arrData) {
        super(activity, layout, arrData);
        this.activity = activity;
        this.layout = layout;
        this.arrData = arrData;
    }
// convert lại listview của độ tin cậy
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final DoMat_ett doMat_ett = arrData.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(activity).inflate(R.layout.list_item_dtc, parent, false);
        }
        TextView txt_STT = (TextView) convertView.findViewById(R.id.txt_STT);
        TextView txt_MaDTC = (TextView) convertView.findViewById(R.id.txt_MaDTC);
        TextView txt_TenDTC = (TextView) convertView.findViewById(R.id.txt_TenDTC);
//        TextView txt_GhiChu = (TextView) convertView.findViewById(R.id.txt_GhiChu);
//        TextView txt_STT1 = (TextView) convertView.findViewById(R.id.txt_STT1);
        ImageView img_Status = (ImageView) convertView.findViewById(R.id.img_Status);
        ImageButton btn_Update = (ImageButton) convertView.findViewById(R.id.btn_Update);
        ImageButton btn_Delete = (ImageButton) convertView.findViewById(R.id.btn_Delete);

        txt_STT.setText((position + 1) + "");
        txt_MaDTC.setText(doMat_ett.getMaDoMat() + "");
        txt_TenDTC.setText(doMat_ett.getTenDoMat());
//        txt_GhiChu.setText(doMat_ett.getGhiChu());
//        txt_STT1.setText(doMat_ett.getSTT() + "");
        if (doMat_ett.isActive())
            img_Status.setImageResource(R.drawable.checked);
        else
            img_Status.setImageResource(R.drawable.not_checked);

        btn_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText txt_MaDM = (EditText) activity.findViewById(R.id.txt_MaDM);
                EditText txt_TenDM =(EditText) activity.findViewById(R.id.txt_TenDM);
                EditText txt_STT =(EditText) activity.findViewById(R.id.txt_STT);
                EditText txt_GhiChu =(EditText) activity.findViewById(R.id.txt_GhiChu);
                CheckBox cb_Active = (CheckBox) activity.findViewById(R.id.cb_Active);
                Button btn_AddNew = (Button) activity.findViewById(R.id.btn_AddNew);
                Button btn_Update = (Button) activity.findViewById(R.id.btn_Update);
                txt_MaDM.setText(String.valueOf(doMat_ett.getMaDoMat() + ""));
                txt_TenDM.setText(doMat_ett.getTenDoMat());
                txt_STT.setText(doMat_ett.getSTT() + "");
                txt_GhiChu.setText(doMat_ett.getGhiChu());
                if (doMat_ett.isActive())
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
                        long MaDoMat = doMat_ett.getMaDoMat();
                        try {
                            DoMat_ett doMatEtt = new DeleteDoMatAsyncTask(activity, MaDoMat).execute().get();
                            arrData.remove(position);
                            new SearchDoMatAsyncTask(activity, "ID", "", 1, Constants.NUM_ROW_PER_PAGE).execute();
                            Toast.makeText(getContext(), doMatEtt.getErrDesc(), Toast.LENGTH_LONG).show();
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
