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
import ngovanquan_803656.datn.asynctask.qldm.DeleteTHBQAsyncTask;
import ngovanquan_803656.datn.asynctask.qldm.SearchTHBQAsyncTask;
import ngovanquan_803656.datn.model.THBQ_ett;
import ngovanquan_803656.datn.utils.Constants;

/**
 * Created by ngoquan on 4/14/2016.
 */
public class ThoiHanBaoQuanAdapter extends ArrayAdapter<THBQ_ett> {

    private Activity activity;
    ArrayList<THBQ_ett> arrData = new ArrayList<>();
    private int layout;

    public ThoiHanBaoQuanAdapter(Activity activity, int layout, ArrayList<THBQ_ett> arrData) {
        super(activity, layout, arrData);
        this.activity = activity;
        this.layout = layout;
        this.arrData = arrData;
    }

    // convert lại listview của độ tin cậy
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final THBQ_ett thbq_ett = arrData.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(activity).inflate(R.layout.list_item_dtc, parent, false);
        }
        TextView txt_STT = (TextView) convertView.findViewById(R.id.txt_STT);
        TextView txt_MaTHBQ = (TextView) convertView.findViewById(R.id.txt_MaDTC);
        TextView txt_TenTHBQ = (TextView) convertView.findViewById(R.id.txt_TenDTC);
//        TextView txt_GhiChu = (TextView) convertView.findViewById(R.id.txt_GhiChu);
//        TextView txt_STT1 = (TextView) convertView.findViewById(R.id.txt_STT1);
        ImageView img_Status = (ImageView) convertView.findViewById(R.id.img_Status);
        ImageButton btn_Update = (ImageButton) convertView.findViewById(R.id.btn_Update);
        ImageButton btn_Delete = (ImageButton) convertView.findViewById(R.id.btn_Delete);

        txt_STT.setText((position + 1) + "");
        txt_MaTHBQ.setText(thbq_ett.getMaTHBQ() + "");
        txt_TenTHBQ.setText(thbq_ett.getTenTHBQ());
//        txt_GhiChu.setText(thbq_ett.getGhiChu());
//        txt_STT1.setText(thbq_ett.getSTT() + "");
        if (thbq_ett.isActive())
            img_Status.setImageResource(R.drawable.checked);
        else
            img_Status.setImageResource(R.drawable.not_checked);

        btn_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText txt_MaTHBQ = (EditText) activity.findViewById(R.id.txt_MaTHBQ);
                EditText txt_TenTHBQ =(EditText) activity.findViewById(R.id.txt_TenTHBQ);
                EditText txt_STT =(EditText) activity.findViewById(R.id.txt_STT);
                EditText txt_GhiChu =(EditText) activity.findViewById(R.id.txt_GhiChu);
                CheckBox cb_Active = (CheckBox) activity.findViewById(R.id.cb_Active);
                Button btn_AddNew = (Button) activity.findViewById(R.id.btn_AddNew);
                Button btn_Update = (Button) activity.findViewById(R.id.btn_Update);
                txt_MaTHBQ.setText(String.valueOf(thbq_ett.getMaTHBQ() + ""));
                txt_TenTHBQ.setText(thbq_ett.getTenTHBQ());
                txt_STT.setText(thbq_ett.getSTT() + "");
                txt_GhiChu.setText(thbq_ett.getGhiChu());
                if (thbq_ett.isActive())
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
                        long MaTHBQ = thbq_ett.getMaTHBQ();
                        try {
                            THBQ_ett thbqEtt = new DeleteTHBQAsyncTask(activity, MaTHBQ).execute().get();
                            arrData.remove(position);
                            new SearchTHBQAsyncTask(activity, "ID", "", 1, Constants.NUM_ROW_PER_PAGE).execute();
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
