package ngovanquan_803656.datn.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ngovanquan_803656.datn.R;
import ngovanquan_803656.datn.asynctask.qldm.DeleteCQLTAsyncTask;
import ngovanquan_803656.datn.asynctask.qldm.SearchCQLTAsyncTask;
import ngovanquan_803656.datn.model.CQLT_ett;
import ngovanquan_803656.datn.utils.Constants;
import ngovanquan_803656.datn.utils.Helper;

/**
 * Created by ngoquan on 5/11/2016.
 */
public class CoQuanLuuTruAdapter extends ArrayAdapter<CQLT_ett> {
    private Activity activity;
    private int layout;
    private ArrayList<CQLT_ett> arrData = new ArrayList<>();
    public CoQuanLuuTruAdapter(Activity activity, int layout, ArrayList<CQLT_ett> arrData) {
        super(activity, layout, arrData);
        this.activity = activity;
        this.layout = layout;
        this.arrData = arrData;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final CQLT_ett cqlt_ett = arrData.get(position);
        if (convertView == null)
            convertView = LayoutInflater.from(activity).inflate(R.layout.list_item_cqlt, parent, false);
        TextView txt_STT = (TextView) convertView.findViewById(R.id.txt_STT);
        TextView txt_MaCQLT = (TextView) convertView.findViewById(R.id.txt_MaCQLT);
        TextView txt_TenCQLT = (TextView) convertView.findViewById(R.id.txt_TenCQLT);
        TextView txt_DiaChi = (TextView) convertView.findViewById(R.id.txt_DiaChi);
        ImageButton btn_update = (ImageButton) convertView.findViewById(R.id.btn_update);
        ImageButton btn_delete = (ImageButton) convertView.findViewById(R.id.btn_delete);

        txt_STT.setText((position + 1) + "");
        txt_MaCQLT.setText(cqlt_ett.getMaCQLT());
        txt_TenCQLT.setText(cqlt_ett.getTenCQLT());
        txt_DiaChi.setText(cqlt_ett.getDiaChi());

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText txt_MaCQLT = (EditText) activity.findViewById(R.id.txt_MaCQLT);
                EditText txt_TenCQLT = (EditText) activity.findViewById(R.id.txt_TenCQLT);
                EditText txt_DiaChi = (EditText) activity.findViewById(R.id.txt_DiaChi);
                EditText txt_SDT = (EditText) activity.findViewById(R.id.txt_SDT);
                EditText txt_Email = (EditText) activity.findViewById(R.id.txt_Email);
                EditText txt_Website = (EditText) activity.findViewById(R.id.txt_Website);
                Button btn_Update = (Button) activity.findViewById(R.id.btn_Update);
                Button btn_AddNew = (Button) activity.findViewById(R.id.btn_AddNew);

                txt_MaCQLT.setText(cqlt_ett.getMaCQLT());
                txt_MaCQLT.setEnabled(false);
                txt_TenCQLT.setText(cqlt_ett.getTenCQLT());
                txt_DiaChi.setText(cqlt_ett.getDiaChi());
                txt_SDT.setText(cqlt_ett.getSDT());
                txt_Email.setText(cqlt_ett.getEmail());
                txt_Website.setText(cqlt_ett.getWebsite());
                btn_AddNew.setVisibility(View.GONE);
                btn_Update.setVisibility(View.VISIBLE);

            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
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
                        String MaCQLT = cqlt_ett.getMaCQLT();
                        try {
                            CQLT_ett cqltEtt = new DeleteCQLTAsyncTask(activity, Constants.FUNCTION_DELETE, MaCQLT).execute().get();
                            arrData.remove(position);
                            ListView lv_CQLT = (ListView) activity.findViewById(R.id.lv_cqlt);
                            ArrayList<CQLT_ett> arrCQLT = new SearchCQLTAsyncTask(activity, "ID", "", 1, Constants.NUM_ROW_PER_PAGE).execute().get();
                            CoQuanLuuTruAdapter coQuanLuuTruAdapter = new CoQuanLuuTruAdapter(activity, R.layout.list_item_cqlt, arrCQLT);
                            lv_CQLT.setAdapter(coQuanLuuTruAdapter);
                            Helper.setListViewHeightBasedOnChildren(lv_CQLT);
                            Toast.makeText(getContext(), cqltEtt.getErrDesc(), Toast.LENGTH_LONG).show();
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
