package ngovanquan_803656.datn.asynctask.qllt;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalFloat;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

import ngovanquan_803656.datn.model.Phong_ett;
import ngovanquan_803656.datn.utils.Constants;

/**
 * Created by ngoquan on 5/5/2016.
 */
public class SearchPhongAsyncTask extends AsyncTask<Void, Void, ArrayList<Phong_ett>> {
    private Activity activity;
    private String search_type, search_val;
    private int pageCurrent;
    private ProgressDialog progressDialog;
    ArrayList<Phong_ett> arrData = new ArrayList<>();

    public SearchPhongAsyncTask(Activity activity, String search_type, String search_val, int pageCurrent) {
        this.activity = activity;
        this.search_type = search_type;
        this.search_val = search_val;
        this.pageCurrent = pageCurrent;
        progressDialog = new ProgressDialog(this.activity);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Please wait...!");
        progressDialog.setCancelable(false);
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(ArrayList<Phong_ett> phong_etts) {
        super.onPostExecute(phong_etts);
        progressDialog.dismiss();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected ArrayList<Phong_ett> doInBackground(Void... params) {
        try {
            final String METHOD_NAME = "SearchPhong";
            final String SOAP_ACTION = Constants.NAMESPACE + METHOD_NAME;
            SoapObject request = new SoapObject(Constants.NAMESPACE, METHOD_NAME);
            request.addProperty("search_type", this.search_type);
            request.addProperty("search_val", this.search_val);
            request.addProperty("page_curr", this.pageCurrent);
            request.addProperty("num_row_per_page", Constants.NUM_ROW_PER_PAGE);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            MarshalFloat marshalFloat = new MarshalFloat();
            marshalFloat.register(envelope);
            HttpTransportSE httpTransportSE = new HttpTransportSE(Constants.URL_PHONG);
            httpTransportSE.call(SOAP_ACTION, envelope);
            SoapObject soapData = (SoapObject) envelope.getResponse();
            String ErrCode = soapData.getProperty("ErrCode").toString();
            String ErrDesc = soapData.getProperty("ErrDesc").toString();
            int total_record = Integer.parseInt(soapData.getProperty("Total_record").toString());
            if (soapData.hasProperty("Data")) {
                SoapObject soapArr = (SoapObject) soapData.getProperty("Data");
                for (int i = 0; i < soapArr.getPropertyCount(); i++) {
                    SoapObject soapItem = (SoapObject) soapArr.getProperty(i);
                    String MaCQLT = "";
                    if (soapItem.hasProperty("MaCQLT"))
                        MaCQLT = soapItem.getProperty("MaCQLT").toString();
                    if (MaCQLT.equals("anyType{}"))
                        MaCQLT = "";
                    long MaPhong = Long.parseLong(soapItem.getProperty("MaPhong").toString());
                    String TenPhong = "";
                    if (soapItem.hasProperty("TenPhong"))
                        TenPhong = soapItem.getProperty("TenPhong").toString();
                    if (TenPhong.equals("anyType{}"))
                        TenPhong = "";
                    String LichSuHinhThanh = "";
                    if (soapItem.hasProperty("LichSuHinhThanh"))
                        LichSuHinhThanh = soapItem.getProperty("LichSuHinhThanh").toString();
                    if (LichSuHinhThanh.equals("anyType{}"))
                        LichSuHinhThanh = "";
                    String ThoiGianTaiLieu = "";
                    if (soapItem.hasProperty("ThoiGianTaiLieu"))
                        ThoiGianTaiLieu = soapItem.getProperty("ThoiGianTaiLieu").toString();
                    if (ThoiGianTaiLieu.equals("anyType{}"))
                        ThoiGianTaiLieu = "";
                    int TongSoTaiLieu = 0;
                    if (soapItem.hasProperty("TongSoTaiLieu") && (soapItem.getProperty("TongSoTaiLieu") != null))
                        TongSoTaiLieu = Integer.parseInt(soapItem.getProperty("TongSoTaiLieu").toString());
                    int SoTaiLieuDaChinhLy = 0;
                    if (soapItem.hasProperty("SoTaiLieuDaChinhLy") && (soapItem.getProperty("SoTaiLieuDaChinhLy") != null))
                        SoTaiLieuDaChinhLy = Integer.parseInt(soapItem.getProperty("SoTaiLieuDaChinhLy").toString());
                    int SoTaiLieuChuaChinhLy = 0;
                    if (soapItem.hasProperty("SoTaiLieuChuaChinhLy") && (soapItem.getProperty("SoTaiLieuChuaChinhLy") != null))
                        SoTaiLieuChuaChinhLy = Integer.parseInt(soapItem.getProperty("SoTaiLieuChuaChinhLy").toString());
                    String CacNhomTaiLieu = "";
                    if (soapItem.hasProperty("CacNhomTaiLieu"))
                        CacNhomTaiLieu = soapItem.getProperty("CacNhomTaiLieu").toString();
                    if (CacNhomTaiLieu.equals("anyType{}"))
                        CacNhomTaiLieu = "";
//                    long MaLoaiHinhTaiLieu = Long.parseLong(soapItem.getProperty("MaLoaiHinhTaiLieu").toString());
                    long MaNN = 1;
                    if (soapItem.hasProperty("MaNN") && (soapItem.getProperty("MaNN") != null))
                        MaNN = Long.parseLong(soapItem.getProperty("MaNN").toString());
                    String ThoiGianNhapTaiLieu = "";
                    if (soapItem.hasProperty("ThoiGianNhapTaiLieu"))
                        ThoiGianNhapTaiLieu = soapItem.getProperty("ThoiGianNhapTaiLieu").toString();
                    if (ThoiGianNhapTaiLieu.equals("anyType{}"))
                        ThoiGianNhapTaiLieu = "";
                    String CongCuTraCuu = "";
                    if (soapItem.hasProperty("CongCuTraCuu"))
                        CongCuTraCuu = soapItem.getProperty("CongCuTraCuu").toString();
                    if (CongCuTraCuu.equals("anyType{}"))
                        CongCuTraCuu = "";
                    String LapBanSaoBaoHiem = "";
                    if (soapItem.hasProperty("LapBanSaoBaoHiem"))
                        LapBanSaoBaoHiem = soapItem.getProperty("LapBanSaoBaoHiem").toString();
                    if (LapBanSaoBaoHiem.equals("anyType{}"))
                        LapBanSaoBaoHiem = "";
                    String GhiChu = "";
                    if (soapItem.hasProperty("GhiChu"))
                        GhiChu = soapItem.getProperty("GhiChu").toString();
                    if (GhiChu.equals("anyType{}"))
                        GhiChu = "";

                    Phong_ett phong_ett = new Phong_ett(MaCQLT, MaPhong, TenPhong, LichSuHinhThanh, ThoiGianTaiLieu, TongSoTaiLieu, SoTaiLieuDaChinhLy,
                            SoTaiLieuChuaChinhLy, CacNhomTaiLieu, MaNN, ThoiGianNhapTaiLieu, CongCuTraCuu, LapBanSaoBaoHiem, GhiChu, total_record,
                            ErrCode, ErrDesc);
                    arrData.add(phong_ett);
                }
            }
//            Log.e("Result property", ErrCode + " - " + ErrDesc + " - " + total_record);
            return arrData;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
