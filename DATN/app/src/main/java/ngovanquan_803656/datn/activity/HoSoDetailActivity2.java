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
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ngovanquan_803656.datn.R;
import ngovanquan_803656.datn.asynctask.qldm.SearchCDSDAsyncTask;
import ngovanquan_803656.datn.asynctask.qldm.SearchNgonNguAsyncTask;
import ngovanquan_803656.datn.asynctask.qldm.SearchTHBQAsyncTask;
import ngovanquan_803656.datn.asynctask.qldm.SearchTTVLAsyncTask;
import ngovanquan_803656.datn.asynctask.qllt.SearchHoSoAsyncTask;
import ngovanquan_803656.datn.asynctask.qllt.SearchHopHoSoAsyncTask;
import ngovanquan_803656.datn.model.CDSD_ett;
import ngovanquan_803656.datn.model.HoSo_ett;
import ngovanquan_803656.datn.model.HopHoSo_ett;
import ngovanquan_803656.datn.model.NgonNgu_ett;
import ngovanquan_803656.datn.model.THBQ_ett;
import ngovanquan_803656.datn.model.TTVL_ett;
import ngovanquan_803656.datn.utils.Constants;

public class HoSoDetailActivity2 extends Activity {

    ImageView btn_close;
    TextView txt_hopSo, txt_hoSoSo, txt_KHTT, txt_mucLucSo, txt_ngonNgu, txt_tieuDeHS, txt_chuGiai, txt_butTich, txt_TGBD, txt_THBQ, txt_CDSD,
            txt_TGKT, txt_SLT, txt_TTVL;

    ArrayList<HopHoSo_ett> arrHHS = new ArrayList<>();
    ArrayList<NgonNgu_ett> arrNN = new ArrayList<>();
    ArrayList<THBQ_ett> arrTHBQ = new ArrayList<>();
    ArrayList<CDSD_ett> arrCDSD = new ArrayList<>();
    ArrayList<TTVL_ett> arrTTVL = new ArrayList<>();

    HoSo_ett hoSo_ett;

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
        setContentView(R.layout.activity_ho_so_detail2);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.x = -20;
        params.height = 500;
        params.width = 900;
        params.y = -20;
        this.getWindow().setAttributes(params);
        addControls();
        Intent intent = getIntent();
        hoSo_ett = (HoSo_ett) intent.getSerializableExtra("HoSo_ett");
        txt_hoSoSo.setText(hoSo_ett.getIDShow());
        txt_KHTT.setText(hoSo_ett.getKHTT());
        txt_mucLucSo.setText(hoSo_ett.getMucLucSo());
        txt_tieuDeHS.setText(hoSo_ett.getTieuDeHs());
        txt_chuGiai.setText(hoSo_ett.getChuGiai());
        txt_butTich.setText(hoSo_ett.getButTich());
        txt_TGBD.setText(hoSo_ett.getThoiGianBatDau());
        txt_TGKT.setText(hoSo_ett.getThoiGianKetThuc());
        txt_SLT.setText(hoSo_ett.getSoLuongTo());

        try {
            arrHHS = new SearchHopHoSoAsyncTask(this, Constants.FUNCTION_SEARCH, "", "", "ID", "", 1, 0).execute().get();
            for (HopHoSo_ett hopHoSo_ett : arrHHS) {
                if (hopHoSo_ett.getMaHopHS() == hoSo_ett.getMaHopHS())
                    txt_hopSo.setText(hopHoSo_ett.getTenHop());
            }

            arrNN = new SearchNgonNguAsyncTask(this, "ID", "", 1, 0).execute().get();
            for (NgonNgu_ett ngonNgu_ett : arrNN) {
                if ((int)ngonNgu_ett.getMaNN() == hoSo_ett.getMaNN())
                    txt_ngonNgu.setText(ngonNgu_ett.getTenNN());
            }

            arrTHBQ = new SearchTHBQAsyncTask(this, "ID", "", 1, 0).execute().get();
            for (THBQ_ett thbq_ett : arrTHBQ) {
                if ((int)thbq_ett.getMaTHBQ() == hoSo_ett.getMaTHBQ())
                    txt_THBQ.setText(thbq_ett.getTenTHBQ());
            }

            arrCDSD = new SearchCDSDAsyncTask(this, "ID", "", 1, 0).execute().get();
            for (CDSD_ett cdsd_ett : arrCDSD) {
                if ((int) cdsd_ett.getMaCDSD() == hoSo_ett.getMaCDSD())
                    txt_CDSD.setText(cdsd_ett.getTenCDSD());
            }

            arrTTVL = new SearchTTVLAsyncTask(this, "ID", "", 1, 0).execute().get();
            for (TTVL_ett ttvl_ett : arrTTVL) {
                if ((int) ttvl_ett.getMaTTVL() == hoSo_ett.getMaTTVL())
                    txt_TTVL.setText(ttvl_ett.getTenTTVL());
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

    private void addControls() {
        txt_hopSo = (TextView) findViewById(R.id.txt_hopSo);
        txt_hoSoSo = (TextView) findViewById(R.id.txt_hoSoSo);
        txt_KHTT = (TextView) findViewById(R.id.txt_KHTT);
        txt_mucLucSo = (TextView) findViewById(R.id.txt_mucLucSo);
        txt_ngonNgu = (TextView) findViewById(R.id.txt_ngonNgu);
        txt_tieuDeHS = (TextView) findViewById(R.id.txt_tieuDeHS);
        txt_chuGiai = (TextView) findViewById(R.id.txt_chuGiai);
        txt_butTich = (TextView) findViewById(R.id.txt_butTich);
        txt_TGBD = (TextView) findViewById(R.id.txt_TGBD);
        txt_THBQ = (TextView) findViewById(R.id.txt_THBQ);
        txt_CDSD = (TextView) findViewById(R.id.txt_CDSD);
        txt_TGKT = (TextView) findViewById(R.id.txt_TGKT);
        txt_SLT = (TextView) findViewById(R.id.txt_SLT);
        txt_TTVL = (TextView) findViewById(R.id.txt_TTVL);
        btn_close = (ImageView) findViewById(R.id.btn_close);
    }
}
