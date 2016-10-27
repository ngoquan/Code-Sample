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
import ngovanquan_803656.datn.asynctask.qldm.DeleteLVBAsyncTask;
import ngovanquan_803656.datn.asynctask.qldm.SearchLVBAsyncTask;
import ngovanquan_803656.datn.model.LVB_ett;
import ngovanquan_803656.datn.utils.Constants;

/**
 * Created by ngoquan on 4/14/2016.
 */
public class LoaiVanBanAdapter extends ArrayAdapter<LVB_ett> {
    private Activity activity;
    ArrayList<LVB_ett> arrData = new ArrayList<>();
    private int layout;

    public LoaiVanBanAdapter(Activity activity, int layout, ArrayList<LVB_ett> arrData) {
        super(activity, layout, arrData);
        this.activity = activity;
        this.layout = layout;
        this.arrData = arrData;
    }

    // convert lại listview của độ tin cậy
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final LVB_ett lvb_ett = arrData.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(activity).inflate(R.layout.list_item_dtc, parent, false);
        }
        TextView txt_STT = (TextView) convertView.findViewById(R.id.txt_STT);
        TextView txt_MaLVB = (TextView) convertView.findViewById(R.id.txt_MaDTC);
        TextView txt_TenLVB = (TextView) convertView.findViewById(R.id.txt_TenDTC);
        ImageView img_Status = (ImageView) convertView.findViewById(R.id.img_Status);
        ImageButton btn_Update = (ImageButton) convertView.findViewById(R.id.btn_Update);
        ImageButton btn_Delete = (ImageButton) convertView.findViewById(R.id.btn_Delete);

        txt_STT.setText((position + 1) + "");
        txt_MaLVB.setText(lvb_ett.getMaLVB() + "");
        txt_TenLVB.setText(lvb_ett.getTenLVB());
//        txt_GhiChu.setText(lvb_ett.getGhiChu());
//        txt_STT1.setText(lvb_ett.getSTT() + "");
        if (lvb_ett.isActive())
            img_Status.setImageResource(R.drawable.checked);
        else
            img_Status.setImageResource(R.drawable.not_checked);

        btn_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText txt_MaLVB = (EditText) activity.findViewById(R.id.txt_MaLVB);
                EditText txt_TenLVB =(EditText) activity.findViewById(R.id.txt_TenLVB);
                EditText txt_STT =(EditText) activity.findViewById(R.id.txt_STT);
                EditText txt_GhiChu =(EditText) activity.findViewById(R.id.txt_GhiChu);
                CheckBox cb_Active = (CheckBox) activity.findViewById(R.id.cb_Active);
                Button btn_AddNew = (Button) activity.findViewById(R.id.btn_AddNew);
                Button btn_Update = (Button) activity.findViewById(R.id.btn_Update);
                txt_MaLVB.setText(String.valueOf(lvb_ett.getMaLVB() + ""));
                txt_TenLVB.setText(lvb_ett.getTenLVB());
                txt_STT.setText(lvb_ett.getSTT() + "");
                txt_GhiChu.setText(lvb_ett.getGhiChu());
                if (lvb_ett.isActive())
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
                        long MaLVB = lvb_ett.getMaLVB();
                        try {
                            LVB_ett thbqEtt = new DeleteLVBAsyncTask(activity, MaLVB).execute().get();
                            arrData.remove(position);
                            new SearchLVBAsyncTask(activity, "ID", "", 1, Constants.NUM_ROW_PER_PAGE).execute();
                            Toast.makeText(getContext(), thbqEtt.getErrDesc(), Toast.LENGTH_LONG).show();
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
