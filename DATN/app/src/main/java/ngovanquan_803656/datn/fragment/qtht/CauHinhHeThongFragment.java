package ngovanquan_803656.datn.fragment.qtht;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import ngovanquan_803656.datn.R;
import ngovanquan_803656.datn.asynctask.qtht.GetInformationSystemAsyncTask;
import ngovanquan_803656.datn.asynctask.qtht.UpdateCHHTAsyncTask;
import ngovanquan_803656.datn.model.CHHT_ett;
import ngovanquan_803656.datn.utils.ConnectionDetector;
import ngovanquan_803656.datn.utils.Constants;
import ngovanquan_803656.datn.utils.Helper;

/**
 * Created by NGOQUAN on 5/30/2016.
 */
public class CauHinhHeThongFragment extends Fragment implements View.OnClickListener{

    EditText txt_tenSNV, txt_tenCCVT, txt_diaChi, txt_SDT, txt_email, txt_website, txt_tenPM, txt_matKhau, txt_MKTD, txt_NLMK;
    ImageButton btn_changePass;
    Button btn_save, btn_cancel;
    TableRow tr_1, tr_2, tr_3;
    LinearLayout ll_1;
    String tenSoNoiVu, tenChiCucVT, diaChi, soDT, email, website, tenPhanMem, matKhau, NLMK, MKTD;
    String password = "";

    ConnectionDetector cd;

    public CauHinhHeThongFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cau_hinh_he_thong, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        cd = new ConnectionDetector(getActivity());
        if (!cd.isConnectingToInternet()) {
            Helper.notificationDialog(getActivity(), "No internert connection", "You don't have internet connection", false);
            return;
        }
        addControls();
        setValueForControls();

        addEvents();

    }

    private void addControls() {
        txt_tenSNV = (EditText) getView().findViewById(R.id.txt_tenSNV);
        txt_tenCCVT = (EditText) getView().findViewById(R.id.txt_tenCCVT);
        txt_diaChi = (EditText) getView().findViewById(R.id.txt_diaChi);
        txt_SDT = (EditText) getView().findViewById(R.id.txt_SDT);
        txt_email = (EditText) getView().findViewById(R.id.txt_email);
        txt_website = (EditText) getView().findViewById(R.id.txt_website);
        txt_tenPM = (EditText) getView().findViewById(R.id.txt_tenPM);
        txt_matKhau = (EditText) getView().findViewById(R.id.txt_matKhau);
        txt_MKTD = (EditText) getView().findViewById(R.id.txt_MKTD);
        txt_NLMK = (EditText) getView().findViewById(R.id.txt_NLMK);
        btn_changePass = (ImageButton) getView().findViewById(R.id.btn_changePass);
        btn_save = (Button) getView().findViewById(R.id.btn_save);
        btn_cancel = (Button) getView().findViewById(R.id.btn_cancel);
        tr_1 = (TableRow) getView().findViewById(R.id.tr_1);
        tr_2 = (TableRow) getView().findViewById(R.id.tr_2);
        tr_3 = (TableRow) getView().findViewById(R.id.tr_3);
        ll_1 = (LinearLayout) getView().findViewById(R.id.ll_1);
    }

    private void setValueForControls() {
        try {
            txt_tenSNV.setText(new GetInformationSystemAsyncTask(getActivity(), Constants.FUNCTION_GET_TEN_SNV).execute().get());
            txt_tenCCVT.setText(new GetInformationSystemAsyncTask(getActivity(), Constants.FUNCTION_GET_TEN_CCVT).execute().get());
            txt_diaChi.setText(new GetInformationSystemAsyncTask(getActivity(), Constants.FUNCTION_GET_DIA_CHI).execute().get());
            txt_SDT.setText(new GetInformationSystemAsyncTask(getActivity(), Constants.FUNCTION_GET_SDT).execute().get());
            txt_email.setText(new GetInformationSystemAsyncTask(getActivity(), Constants.FUNCTION_GET_EMAIL).execute().get());
            txt_website.setText(new GetInformationSystemAsyncTask(getActivity(), Constants.FUNCTION_GET_WEBSITE).execute().get());
            txt_tenPM.setText(new GetInformationSystemAsyncTask(getActivity(), Constants.FUNCTION_GET_TEN_PHAN_MEM).execute().get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        txt_matKhau.setText("");
        txt_MKTD.setText("");
        txt_NLMK.setText("");
    }

    private void addEvents() {
        btn_changePass.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_changePass:
                tr_1.setVisibility(View.VISIBLE);
                tr_2.setVisibility(View.VISIBLE);
                tr_3.setVisibility(View.VISIBLE);
                ll_1.setVisibility(View.GONE);
                break;
            case R.id.btn_save:
                tenSoNoiVu = txt_tenSNV.getText().toString().toUpperCase();
                tenChiCucVT = txt_tenCCVT.getText().toString().toUpperCase();
                diaChi = txt_diaChi.getText().toString();
                soDT = txt_SDT.getText().toString();
                email = txt_email.getText().toString();
                website = txt_website.getText().toString();
                tenPhanMem = txt_tenPM.getText().toString().toUpperCase();
                matKhau = txt_matKhau.getText().toString();
                MKTD = txt_MKTD.getText().toString();
                NLMK = txt_NLMK.getText().toString();

                try {
                    if (tr_1.getVisibility() == View.VISIBLE) {
                        if (!(matKhau.equals("")) && (!MKTD.equals("")) && (!NLMK.equals(""))) {
                            String pass = new GetInformationSystemAsyncTask(getActivity(), Constants.FUNCTION_GET_PASSWORD).execute().get();
                            if (pass.equals(Helper.sha1(matKhau))) {
                                if (MKTD.equals(NLMK)) {
                                    password = Helper.sha1(MKTD);
                                } else {
                                    txt_MKTD.requestFocus();
                                    Toast.makeText(getActivity(), "Mật khẩu thay đổi và Nhập lại mật khẩu phải giống nhau...!", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } else {
                                txt_matKhau.requestFocus();
                                Toast.makeText(getActivity(), "Mật khẩu hiện tại của bạn KHÔNG đúng...!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } else {
                            txt_matKhau.requestFocus();
                            Toast.makeText(getActivity(), getString(R.string.message_input), Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    CHHT_ett chht_ett = new UpdateCHHTAsyncTask(getActivity(), tenSoNoiVu, tenChiCucVT, diaChi, soDT, email, website,
                            tenPhanMem, password).execute().get();
                    Toast.makeText(getActivity(), chht_ett.getErrDesc(), Toast.LENGTH_SHORT).show();
                } catch (InterruptedException e) {
                        e.printStackTrace();
                } catch (ExecutionException e) {
                        e.printStackTrace();
                }
                break;
            case R.id.btn_cancel:
                setValueForControls();
                tr_1.setVisibility(View.GONE);
                tr_2.setVisibility(View.GONE);
                tr_3.setVisibility(View.GONE);
                ll_1.setVisibility(View.VISIBLE);
                break;
        }

    }
}
