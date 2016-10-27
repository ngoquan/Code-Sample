package ngovanquan_803656.datn.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ngovanquan_803656.datn.R;
import ngovanquan_803656.datn.asynctask.qldm.SearchCDSDAsyncTask;
import ngovanquan_803656.datn.asynctask.qldm.SearchDoMatAsyncTask;
import ngovanquan_803656.datn.asynctask.qldm.SearchDoTinCayAsyncTask;
import ngovanquan_803656.datn.asynctask.qldm.SearchLVBAsyncTask;
import ngovanquan_803656.datn.asynctask.qldm.SearchNgonNguAsyncTask;
import ngovanquan_803656.datn.asynctask.qldm.SearchTTVLAsyncTask;
import ngovanquan_803656.datn.model.CDSD_ett;
import ngovanquan_803656.datn.model.DoMat_ett;
import ngovanquan_803656.datn.model.DoTinCay_ett;
import ngovanquan_803656.datn.model.LVB_ett;
import ngovanquan_803656.datn.model.NgonNgu_ett;
import ngovanquan_803656.datn.model.TTVL_ett;
import ngovanquan_803656.datn.model.VanBan_ett;

public class VanBanDetailActivity extends Activity {

    ImageView btn_close;
    TextView txt_hoSoSo, txt_soKHVB, txt_thoiGian, txt_ngonNgu, txt_tacGia, txt_mucLucSo, txt_KHTT, txt_toSo, txt_SLT, txt_trichYeu, txt_butTich,
                txt_LVB, txt_doMat, txt_ghiChu, txt_TLDK, txt_TTVL, txt_DTC, txt_CDSD;
    ArrayList<NgonNgu_ett> arrNN = new ArrayList<>();
    ArrayList<LVB_ett> arrLVB = new ArrayList<>();
    ArrayList<DoMat_ett> arrDM = new ArrayList<>();
    ArrayList<TTVL_ett> arrTTVL = new ArrayList<>();
    ArrayList<DoTinCay_ett> arrDTC = new ArrayList<>();
    ArrayList<CDSD_ett> arrCDSD = new ArrayList<>();

    VanBan_ett vanBan_ett;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // hide titlebar of application
        // must be before setting the layout
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // hide statusbar of Android
        // could also be done later
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_van_ban_detail);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.x = -20;
        params.height = 500;
        params.width = 900;
        params.y = -20;
        this.getWindow().setAttributes(params);
        addControls();
        Intent intent = getIntent();
        vanBan_ett = (VanBan_ett) intent.getSerializableExtra("VanBan_ett");
        txt_hoSoSo.setText(vanBan_ett.getHoSoSo());
        txt_soKHVB.setText(vanBan_ett.getSoKyHieu());
        txt_thoiGian.setText(vanBan_ett.getThoiGian());
        txt_tacGia.setText(vanBan_ett.getTacGia());
        txt_mucLucSo.setText(vanBan_ett.getMucLucSo());
        txt_KHTT.setText(vanBan_ett.getKHTT());
        txt_toSo.setText(vanBan_ett.getToSo());
        txt_SLT.setText(vanBan_ett.getSoLuongTo());
        txt_trichYeu.setText(vanBan_ett.getTrichYeuND());
        txt_butTich.setText(vanBan_ett.getButTich());
        txt_ghiChu.setText(vanBan_ett.getGhiChu());
        try {
            arrNN = new SearchNgonNguAsyncTask(this, "ID", "", 1, 0).execute().get();
            for (NgonNgu_ett ngonNgu_ett : arrNN) {
                if ((int) ngonNgu_ett.getMaNN() == vanBan_ett.getMaNN()) {
                    txt_ngonNgu.setText(ngonNgu_ett.getTenNN());
                }
            }
            arrLVB = new SearchLVBAsyncTask(this, "ID", "", 1, 0).execute().get();
            for (LVB_ett lvb_ett : arrLVB) {
                if ((int) lvb_ett.getMaLVB() == vanBan_ett.getMaLVB()) {
                    txt_LVB.setText(lvb_ett.getTenLVB());
                }
            }

            arrDM = new SearchDoMatAsyncTask(this, "ID", "", 1, 0).execute().get();
            for (DoMat_ett doMat_ett : arrDM) {
                if ((int) doMat_ett.getMaDoMat() == vanBan_ett.getMaDoMat()) {
                    txt_doMat.setText(doMat_ett.getTenDoMat());
                }
            }

            arrTTVL = new SearchTTVLAsyncTask(this, "ID", "", 1, 0).execute().get();
            for (TTVL_ett ttvl_ett : arrTTVL) {
                if ((int) ttvl_ett.getMaTTVL() == vanBan_ett.getMaTTVL())
                    txt_TTVL.setText(ttvl_ett.getTenTTVL());
            }

            arrDTC = new SearchDoTinCayAsyncTask(this, "ID", "", 1, 0).execute().get();
            for (DoTinCay_ett doTinCay_ett : arrDTC) {
                if ((int) doTinCay_ett.getMaDoTinCay() == vanBan_ett.getMaDoTinCay())
                    txt_DTC.setText(doTinCay_ett.getTenDoTinCay());
            }

            arrCDSD = new SearchCDSDAsyncTask(this, "ID", "", 1, 0).execute().get();
            for (CDSD_ett cdsd_ett : arrCDSD) {
                if ((int) cdsd_ett.getMaCDSD() == vanBan_ett.getMaCDSD())
                    txt_CDSD.setText(cdsd_ett.getTenCDSD());
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


//    TextView txt_hoSoSo, txt_soKHVB, txt_thoiGian, txt_ngonNgu, txt_tacGia, txt_mucLucSo, txt_KHTT, txt_toSo, txt_SLT, txt_trichYeu, txt_butTich,
//            txt_LVB, txt_doMat, txt_ghiChu, txt_TLDK, txt_TTVL, txt_DTC, txt_CDSD;
    private void addControls() {
        btn_close = (ImageView) findViewById(R.id.btn_close);
        txt_hoSoSo = (TextView) findViewById(R.id.txt_hoSoSo);
        txt_soKHVB = (TextView) findViewById(R.id.txt_soKHVB);
        txt_thoiGian = (TextView) findViewById(R.id.txt_thoiGian);
        txt_ngonNgu = (TextView) findViewById(R.id.txt_ngonNgu);
        txt_tacGia = (TextView) findViewById(R.id.txt_tacGia);
        txt_mucLucSo = (TextView) findViewById(R.id.txt_mucLucSo);
        txt_KHTT = (TextView) findViewById(R.id.txt_KHTT);
        txt_toSo = (TextView) findViewById(R.id.txt_toSo);
        txt_SLT = (TextView) findViewById(R.id.txt_SLT);
        txt_trichYeu = (TextView) findViewById(R.id.txt_trichYeu);
        txt_butTich = (TextView) findViewById(R.id.txt_butTich);
        txt_LVB = (TextView) findViewById(R.id.txt_LVB);
        txt_doMat = (TextView) findViewById(R.id.txt_doMat);
        txt_ghiChu = (TextView) findViewById(R.id.txt_ghiChu);
        txt_TLDK = (TextView) findViewById(R.id.txt_TLDK);
        txt_TTVL = (TextView) findViewById(R.id.txt_TTVL);
        txt_DTC = (TextView) findViewById(R.id.txt_DTC);
        txt_CDSD = (TextView) findViewById(R.id.txt_CDSD);

    }
}
