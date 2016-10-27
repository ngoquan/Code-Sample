package ngovanquan_803656.datn.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import ngovanquan_803656.datn.R;
import ngovanquan_803656.datn.adapter.ExpandableListAdapter;
import ngovanquan_803656.datn.adapter.MenuHopHoSoAndHoSoAdapter;
import ngovanquan_803656.datn.asynctask.SearchHoSo;
import ngovanquan_803656.datn.asynctask.SearchVanBan;
import ngovanquan_803656.datn.asynctask.qllt.GetNumberHoSoAsyncTask;
import ngovanquan_803656.datn.asynctask.qllt.GetNumberVanBanAsyncTask;
import ngovanquan_803656.datn.asynctask.qllt.SearchHoSoAsyncTask;
import ngovanquan_803656.datn.asynctask.qllt.SearchHopHoSoAsyncTask;
import ngovanquan_803656.datn.asynctask.qllt.SearchPhongAsyncTask;
import ngovanquan_803656.datn.asynctask.qllt.SearchVanBanAsyncTask;
import ngovanquan_803656.datn.asynctask.qtht.GetInformationSystemAsyncTask;
import ngovanquan_803656.datn.asynctask.qtht.GetListPermissionAsyncTask;
import ngovanquan_803656.datn.asynctask.qtht.SearchNhomNguoiDungAsyncTask;
import ngovanquan_803656.datn.model.HoSo_ett;
import ngovanquan_803656.datn.model.HopHoSo_ett;
import ngovanquan_803656.datn.model.Phong_ett;
import ngovanquan_803656.datn.model.VanBan_ett;
import ngovanquan_803656.datn.utils.Constants;
import ngovanquan_803656.datn.utils.Helper;

public class MainActivity extends Activity implements View.OnClickListener {

//    List<Phong_ett> listHeader;
//    HashMap<Phong_ett, List<HopHoSo_ett>> listChild;
//    LinearLayout ll_paging;
//    Button btn_first, btn_back, btn_jump_back, btn_1, btn_2, btn_3, btn_4, btn_5, btn_jump_next, btn_next, btn_end;
//    ArrayList<Button> arrButton = new ArrayList<>();
//    private int total_record = 1000;
//    int pageCurrent = 1;
//    int pageTotal;

    public static final int READ_REQUEST_CODE = 92;

    List<HopHoSo_ett> listHeader = new ArrayList<>();
    HashMap<HopHoSo_ett, List<HoSo_ett>> listChild = new HashMap<>();
    MenuHopHoSoAndHoSoAdapter menuHopHoSoAndHoSoAdapter;
    Button btn_getPath;
    TextView txt_paths;
    ExpandableListView exp_listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_getPath = (Button) findViewById(R.id.btn_getPath);
        txt_paths = (TextView) findViewById(R.id.txt_paths);
        exp_listView = (ExpandableListView) findViewById(R.id.exp_listview);
        btn_getPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performFileSearch();
            }
        });

        try {
            ArrayList<VanBan_ett> arrHoSo = new SearchVanBan(this, "TrichYeuND", "", 1).execute().get();
            Log.e("ArraySize", arrHoSo.size() + "");
            Log.e("total_record", arrHoSo.get(0).getTotal_record() + "");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void performFileSearch() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if (data != null) {
                uri = data.getData();
                String paths = uri.getPath();
//                String fileName =
                byte[] result = convertToByteArray(paths);
//                Log.e("ResultLength", result.toString());
                int lastIndex = paths.lastIndexOf(File.separator);
                String fileName = paths.substring(lastIndex + 1);
                txt_paths.setText(fileName);
            }
        }
    }


    private byte[] convertToByteArray(String filePath) {
        File file = new File(filePath);
        long length = file.length();
        byte[] buf = new byte[(int) length];
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            InputStream fis = new FileInputStream(filePath);
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                bos.write(buf, 0, readNum);
            }
            byte[] bytes = bos.toByteArray();
            return bytes;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //    private void pageLoad(int page) {
////        gone button
//        btn_first.setVisibility(View.GONE);
//        btn_back.setVisibility(View.GONE);
//        btn_jump_back.setVisibility(View.GONE);
//        btn_1.setVisibility(View.GONE);
//        btn_2.setVisibility(View.GONE);
//        btn_3.setVisibility(View.GONE);
//        btn_4.setVisibility(View.GONE);
//        btn_5.setVisibility(View.GONE);
//        btn_jump_next.setVisibility(View.GONE);
//        btn_next.setVisibility(View.GONE);
//        btn_end.setVisibility(View.GONE);
////        calculate page total
//        pageTotal = (int) Math.ceil((double) total_record / Constants.NUM_ROW_PER_PAGE);
//
////        thuat toan bat dau
//        if (page < 0)
//            page = 1;
//        if (pageTotal > 1) {
//            if (page > pageTotal)
//                page = pageTotal;
////            show btn_first, btn_back, btn_next, btn_end
//            if (page > 1) {
//                setButtonPagingValue(btn_first, "<<", 1);
//                btn_first.setVisibility(View.VISIBLE);
//                setButtonPagingValue(btn_back, "<", page - 1);
//                btn_back.setVisibility(View.VISIBLE);
//            }
//            if ((page + 1) <= pageTotal) {
//                setButtonPagingValue(btn_next, ">", page + 1);
//                btn_next.setVisibility(View.VISIBLE);
//                setButtonPagingValue(btn_end, ">>", pageTotal);
//                btn_end.setVisibility(View.VISIBLE);
//            }
//
//            //trường hợp page_curr nằm đúng trong khoảng thì thực hiện xử lý paging
//            setButtonPagingValue(btn_3, page + "", page);
//            btn_3.setVisibility(View.VISIBLE);
//            if (pageTotal == 2) {
//                setButtonPagingValue(btn_3, page + "", page);
//                if (page == 1) {
//                    setButtonPagingValue(btn_4, "2", 2);
//                    btn_4.setVisibility(View.VISIBLE);
//                } else {
//                    setButtonPagingValue(btn_2, "1", 1);
//                    btn_2.setVisibility(View.VISIBLE);
//                }
//            } else {
//                //set btn_2, btn_1 và btn_jump_back theo từng trường hợp
//                if (page < 3) {
//                    //nếu page_curr < 3 thì xử lý cho trường hợp  =  2 & 1
//                    switch (page) {
//                        case 2:
//                            setButtonPagingValue(btn_2, "1", 1);
//                            btn_2.setVisibility(View.VISIBLE);
//                            break;
//                    }
//                } else {
//                    setButtonPagingValue(btn_2, (page - 1) + "", page - 1);
//                    btn_2.setVisibility(View.VISIBLE);
//                    setButtonPagingValue(btn_1, (page - 2) + "", page - 2);
//                    btn_1.setVisibility(View.VISIBLE);
//                    if ((page - 2) > Constants.PAGE_JUMP_PAGING) {
//                        //page_curr > 3 và page_curr - page_jump > 0
//                        setButtonPagingValue(btn_jump_back, "...", (page - 2 - Constants.PAGE_JUMP_PAGING));
//                        btn_jump_back.setVisibility(View.VISIBLE);
//                    }
//                }
//
//                //set btn_3, btn_4 và p_jump_next theo từng trường hợp
//                if ((page + 3) <= pageTotal) {
//                    setButtonPagingValue(btn_4, (page + 1) + "", (page + 1));
//                    btn_4.setVisibility(View.VISIBLE);
//                    setButtonPagingValue(btn_5, (page + 2) + "", (page + 2));
//                    btn_5.setVisibility(View.VISIBLE);
//                    if ((pageTotal - page - 2) > Constants.PAGE_JUMP_PAGING) {
//                        setButtonPagingValue(btn_jump_next, "...", (page + 2 + Constants.PAGE_JUMP_PAGING));
//                        btn_jump_next.setVisibility(View.VISIBLE);
//                    }
//                } else {
//                    //nếu page_curr + 3 > page_total thì xử lý cho trường hợp  =  +2 & +1
//                    if ((page + 1) == pageTotal) {
//                        setButtonPagingValue(btn_4, (page + 1) + "", (page + 1));
//                        btn_4.setVisibility(View.VISIBLE);
//                    } else {
//                        if ((page + 2) == pageTotal) {
//                            setButtonPagingValue(btn_4, (page + 1) + "", (page + 1));
//                            btn_4.setVisibility(View.VISIBLE);
//                            setButtonPagingValue(btn_5, (page + 2) + "", (page + 2));
//                            btn_5.setVisibility(View.VISIBLE);
//                        }
//                    }
//                }
//            }
//
//        } else {
//            page = pageTotal;
//        }
//    }
    
//    private void setButtonPagingValue(Button b, String title, final int page) {
//        b.setText(title);
//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, page + "", Toast.LENGTH_SHORT).show();
//                pageCurrent = page;
//                pageLoad(pageCurrent);
//            }
//        });
//    }

//    private void prepareData() {
//        listHeader = new ArrayList<>();
//        listChild = new HashMap<>();
////        add list header
//        ArrayList<Phong_ett> arrPhong = new ArrayList<>();
////        ArrayList<HopHoSo_ett> arrHopHoSo = new ArrayList<>();
//        try {
//            arrPhong = new SearchPhongAsyncTask(this, "ID", "", 1).execute().get();
//            for (Phong_ett phong_ett : arrPhong) {
//                String MaPhong = String.valueOf(phong_ett.getMaPhong());
//                ArrayList<HopHoSo_ett> arrHopHS =  new SearchHopHoSoAsyncTask(this, Constants.FUNCTION_SEARCH, "", "", "HopHoSoByPhong", MaPhong, 1).execute().get();
//                int sl_hophs = arrHopHS.get(0).getTotal_record();
//                listHeader.add(new Phong_ett(phong_ett.getMaCQLT(), phong_ett.getMaPhong(), phong_ett.getTenPhong(), phong_ett.getLichSuHinhThanh(),
//                        phong_ett.getThoiGianTaiLieu(), phong_ett.getTongSoTaiLieu(), phong_ett.getSoTaiLieuDaChinhLy(), phong_ett.getSoTaiLieuChuaChinhLy(),
//                        phong_ett.getCacNhomTaiLieu(), phong_ett.getMaNN(), phong_ett.getThoiGianNhapTaiLieu(), phong_ett.getCongCuTraCuu(), phong_ett.getLapBanSaoBaoHiem(),
//                        phong_ett.getGhiChu(), sl_hophs));
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//
////        add list child
//        ArrayList<HopHoSo_ett> arrHopHoSo = new ArrayList<>();
//        ArrayList<HopHoSo_ett> arrHopHoSo1 = new ArrayList<>();
//        for (Phong_ett phong_ett : listHeader) {
//            String MaPhong = String.valueOf(phong_ett.getMaPhong());
//            try {
//                arrHopHoSo = new SearchHopHoSoAsyncTask(this, Constants.FUNCTION_SEARCH, "", "", "HopHoSoByPhong", MaPhong, 1).execute().get();
//                arrHopHoSo1 = new ArrayList<>();
//                for (HopHoSo_ett hopHoSo_ett : arrHopHoSo) {
//                    String MaHopHS = String.valueOf(hopHoSo_ett.getMaHopHS());
//                    ArrayList<HoSo_ett> arrHoSo = new SearchHoSoAsyncTask(this, "HoSoByHopHoSo", MaHopHS, 1).execute().get();
//                    ArrayList<HoSo_ett> arrHoSo1 = new ArrayList<>();
//                    int sl_hoso = 0;
//                    for (HoSo_ett hoSo_ett : arrHoSo) {
//                        HoSo_ett hoSoEtt = new HoSo_ett(hoSo_ett.getMaPhong(), hoSo_ett.getMucLucSo(), hoSo_ett.getMaHopHS(), hoSo_ett.getHoSoSo(), hoSo_ett.getKHTT(),
//                                hoSo_ett.getTieuDeHs(), hoSo_ett.getChuGiai(), hoSo_ett.getThoiGianBatDau(), hoSo_ett.getThoiGianKetThuc(), hoSo_ett.getMaNN(), hoSo_ett.getButTich(),
//                                hoSo_ett.getSoLuongTo(), hoSo_ett.getMaTHBQ(), hoSo_ett.getMaCDSD(), hoSo_ett.getMaTTVL(), 5);
//                        arrHoSo1.add(hoSoEtt);
//                        if (sl_hoso == 0)
//                            sl_hoso = hoSo_ett.getTotal_record();
//                    }
//                    arrHopHoSo1.add(new HopHoSo_ett(hopHoSo_ett.getMaHopHS(), hopHoSo_ett.getTenHop(), hopHoSo_ett.getGhiChu(), hopHoSo_ett.isActive(),
//                            hopHoSo_ett.getMaPhong(), sl_hoso, arrHoSo1));
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//            listChild.put(phong_ett, arrHopHoSo1);
//        }
//
//
//    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

    }
}
