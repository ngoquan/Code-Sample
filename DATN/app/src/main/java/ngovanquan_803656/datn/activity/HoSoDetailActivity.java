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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ngovanquan_803656.datn.R;
import ngovanquan_803656.datn.adapter.HoSoDetailAdapter;
import ngovanquan_803656.datn.asynctask.qllt.SearchVanBanAsyncTask;
import ngovanquan_803656.datn.model.HoSo_ett;
import ngovanquan_803656.datn.model.VanBan_ett;
import ngovanquan_803656.datn.utils.Constants;
import ngovanquan_803656.datn.utils.Helper;

public class HoSoDetailActivity extends Activity {

    TextView txt_header, txt_status;
    Button btn_first, btn_back, btn_jump_back, btn_1, btn_2, btn_3, btn_4, btn_5, btn_jump_next, btn_next, btn_end;
    ListView lv_hoSo;
    LinearLayout ll_button, ll_hoSo;
    HoSo_ett hoSo_ett;
    ImageView btn_close;

    ArrayList<VanBan_ett> arrVanBan = new ArrayList<>();
    HoSoDetailAdapter hoSoDetailAdapter;
    public static int total_record, pageTotal, type;
    public static int pageVanBan = 1;
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
        setContentView(R.layout.activity_ho_so_detail);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.x = -20;
        params.height = 500;
        params.width = 900;
        params.y = -20;
        this.getWindow().setAttributes(params);
        addControls();
        Intent intent = getIntent();
        hoSo_ett = (HoSo_ett) intent.getSerializableExtra("HoSo_ett");
        txt_header.setText("Danh sách các văn bản trong Hồ sơ số " + hoSo_ett.getHoSoSo());
        try {
            arrVanBan = new SearchVanBanAsyncTask(this, "VbbyHs", hoSo_ett.getHoSoSo(), pageVanBan).execute().get();
            if (!arrVanBan.isEmpty()) {
                hoSoDetailAdapter = new HoSoDetailAdapter(this, R.layout.list_item_detail_vanban, arrVanBan);
                lv_hoSo.setAdapter(hoSoDetailAdapter);
                Helper.setListViewHeightBasedOnChildren(lv_hoSo);
                type = 1;
                total_record = arrVanBan.get(0).getTotal_record();
                pageLoad(type, pageVanBan);
                ll_hoSo.setVisibility(View.VISIBLE);
                ll_button.setVisibility(View.VISIBLE);
                txt_status.setVisibility(View.GONE);

            } else {
                ll_hoSo.setVisibility(View.GONE);
                ll_button.setVisibility(View.GONE);
                txt_status.setVisibility(View.VISIBLE);
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
        txt_header = (TextView) findViewById(R.id.txt_header);
        txt_status = (TextView) findViewById(R.id.txt_status);
        btn_first = (Button) findViewById(R.id.btn_first);
        btn_back = (Button) findViewById(R.id.btn_back);
        btn_jump_back = (Button) findViewById(R.id.btn_jump_back);
        btn_1 = (Button) findViewById(R.id.btn_1);
        btn_2 = (Button) findViewById(R.id.btn_2);
        btn_3 = (Button) findViewById(R.id.btn_3);
        btn_4 = (Button) findViewById(R.id.btn_4);
        btn_5 = (Button) findViewById(R.id.btn_5);
        btn_jump_next = (Button) findViewById(R.id.btn_jump_next);
        btn_next = (Button) findViewById(R.id.btn_next);
        btn_end = (Button) findViewById(R.id.btn_end);
        btn_close = (ImageView) findViewById(R.id.btn_close);
        lv_hoSo = (ListView) findViewById(R.id.lv_hoSo);
        ll_hoSo = (LinearLayout) findViewById(R.id.ll_hoSo);
        ll_button = (LinearLayout) findViewById(R.id.ll_button);
    }

    public void pageLoad(int type, int page) {
//        gone button
        btn_first.setVisibility(View.GONE);
        btn_back.setVisibility(View.GONE);
        btn_jump_back.setVisibility(View.GONE);
        btn_1.setVisibility(View.GONE);
        btn_2.setVisibility(View.GONE);
        btn_3.setVisibility(View.GONE);
        btn_4.setVisibility(View.GONE);
        btn_5.setVisibility(View.GONE);
        btn_jump_next.setVisibility(View.GONE);
        btn_next.setVisibility(View.GONE);
        btn_end.setVisibility(View.GONE);
//        if (arrHopHoSo != null) {
//        calculate page total
        pageTotal = (int) Math.ceil((double) total_record / Constants.NUM_ROW_PER_PAGE);

//        thuat toan bat dau
        if (page < 0)
            page = 1;
        if (pageTotal > 1) {
            if (page > pageTotal)
                page = pageTotal;
//            show btn_first, btn_back, btn_next, btn_end
            if (page > 1) {
                setButtonPagingValue(type, btn_first, "<<", 1);
                btn_first.setVisibility(View.VISIBLE);
                setButtonPagingValue(type, btn_back, "<", page - 1);
                btn_back.setVisibility(View.VISIBLE);
            }
            if ((page + 1) <= pageTotal) {
                setButtonPagingValue(type, btn_next, ">", page + 1);
                btn_next.setVisibility(View.VISIBLE);
                setButtonPagingValue(type, btn_end, ">>", pageTotal);
                btn_end.setVisibility(View.VISIBLE);
            }

            //trường hợp page_curr nằm đúng trong khoảng thì thực hiện xử lý paging
            setButtonPagingValue(type, btn_3, page + "", page);
            btn_3.setVisibility(View.VISIBLE);
            if (pageTotal == 2) {
                setButtonPagingValue(type, btn_3, page + "", page);
                if (page == 1) {
                    setButtonPagingValue(type, btn_4, "2", 2);
                    btn_4.setVisibility(View.VISIBLE);
                } else {
                    setButtonPagingValue(type, btn_2, "1", 1);
                    btn_2.setVisibility(View.VISIBLE);
                }
            } else {
                //set btn_2, btn_1 và btn_jump_back theo từng trường hợp
                if (page < 3) {
                    //nếu page_curr < 3 thì xử lý cho trường hợp  =  2 & 1
                    switch (page) {
                        case 2:
                            setButtonPagingValue(type, btn_2, "1", 1);
                            btn_2.setVisibility(View.VISIBLE);
                            break;
                    }
                } else {
                    setButtonPagingValue(type, btn_2, (page - 1) + "", page - 1);
                    btn_2.setVisibility(View.VISIBLE);
                    setButtonPagingValue(type, btn_1, (page - 2) + "", page - 2);
                    btn_1.setVisibility(View.VISIBLE);
                    if ((page - 2) > Constants.PAGE_JUMP_PAGING) {
                        //page_curr > 3 và page_curr - page_jump > 0
                        setButtonPagingValue(type, btn_jump_back, "...", (page - 2 - Constants.PAGE_JUMP_PAGING));
                        btn_jump_back.setVisibility(View.VISIBLE);
                    }
                }

                //set btn_3, btn_4 và p_jump_next theo từng trường hợp
                if ((page + 3) <= pageTotal) {
                    setButtonPagingValue(type, btn_4, (page + 1) + "", (page + 1));
                    btn_4.setVisibility(View.VISIBLE);
                    setButtonPagingValue(type, btn_5, (page + 2) + "", (page + 2));
                    btn_5.setVisibility(View.VISIBLE);
                    if ((pageTotal - page - 2) > Constants.PAGE_JUMP_PAGING) {
                        setButtonPagingValue(type, btn_jump_next, "...", (page + 2 + Constants.PAGE_JUMP_PAGING));
                        btn_jump_next.setVisibility(View.VISIBLE);
                    }
                } else {
                    //nếu page_curr + 3 > page_total thì xử lý cho trường hợp  =  +2 & +1
                    if ((page + 1) == pageTotal) {
                        setButtonPagingValue(type, btn_4, (page + 1) + "", (page + 1));
                        btn_4.setVisibility(View.VISIBLE);
                    } else {
                        if ((page + 2) == pageTotal) {
                            setButtonPagingValue(type, btn_4, (page + 1) + "", (page + 1));
                            btn_4.setVisibility(View.VISIBLE);
                            setButtonPagingValue(type, btn_5, (page + 2) + "", (page + 2));
                            btn_5.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }

        } else {
            page = pageTotal;
        }
//        }
    }

    private void setButtonPagingValue(final int type, Button b, String title, final int page) {
        b.setText(title);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HoSoDetailActivity.this, page + "", Toast.LENGTH_SHORT).show();
                switch (type) {
                    case 1:
                        pageVanBan = page;
                        try {
                            arrVanBan = new SearchVanBanAsyncTask(HoSoDetailActivity.this, "VbbyHs", hoSo_ett.getHoSoSo(), pageVanBan).execute().get();
                            hoSoDetailAdapter = new HoSoDetailAdapter(HoSoDetailActivity.this, R.layout.list_item_detail_vanban, arrVanBan);
                            lv_hoSo.setAdapter(hoSoDetailAdapter);
                            Helper.setListViewHeightBasedOnChildren(lv_hoSo);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        pageLoad(type, pageVanBan);
                        break;
                }

            }
        });
    }

}
