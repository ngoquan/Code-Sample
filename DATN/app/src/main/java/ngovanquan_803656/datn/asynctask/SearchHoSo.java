package ngovanquan_803656.datn.asynctask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.MarshalFloat;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

import ngovanquan_803656.datn.model.HoSo_ett;
import ngovanquan_803656.datn.utils.Constants;

/**
 * Created by NGOQUAN on 5/31/2016.
 */
public class SearchHoSo extends AsyncTask<Void, Void, ArrayList<HoSo_ett>> {

    private Activity activity;
    private String search_type, search_val;
    private int pageCurrent, num_row_per_page;
    private ProgressDialog progressDialog;
    private ArrayList<HoSo_ett> arrData = new ArrayList<>();

    public SearchHoSo(Activity activity, String search_type, String search_val, int pageCurrent, int num_row_per_page) {
        this.activity = activity;
        this.search_type = search_type;
        this.search_val = search_val;
        this.pageCurrent = pageCurrent;
        this.num_row_per_page = num_row_per_page;
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
    protected void onPostExecute(ArrayList<HoSo_ett> hoSo_etts) {
        super.onPostExecute(hoSo_etts);
        progressDialog.dismiss();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected ArrayList<HoSo_ett> doInBackground(Void... params) {
        try {
            final String METHOD_NAME = "SearchHoSo";
            final String SOAP_ACTION = Constants.NAMESPACE + METHOD_NAME;
            SoapObject request = new SoapObject(Constants.NAMESPACE, METHOD_NAME);
            request.addProperty("search_type", this.search_type);
            request.addProperty("search_val", this.search_val);
            request.addProperty("page_curr", this.pageCurrent);
            request.addProperty("num_row_per_page", this.num_row_per_page);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            MarshalFloat marshalFloat = new MarshalFloat();
            marshalFloat.register(envelope);
            HttpTransportSE httpTransportSE = new HttpTransportSE(Constants.URL_TK);
            httpTransportSE.call(SOAP_ACTION, envelope);
            SoapObject soapData = (SoapObject) envelope.getResponse();
            String ErrCode = soapData.getProperty("ErrCode").toString();
            String ErrDesc = soapData.getProperty("ErrDesc").toString();
            int total_record = Integer.parseInt(soapData.getProperty("Total_record").toString());
            if (soapData.hasProperty("Data")) {
                SoapObject soapArr = (SoapObject) soapData.getProperty("Data");
                for (int i = 0; i < soapArr.getPropertyCount(); i++) {
                    SoapObject soapItem = (SoapObject) soapArr.getProperty(i);
                    long MaPhong = 6;
                    if (soapItem.hasProperty("MaPhong") && (soapItem.getProperty("MaPhong") != null)) {
                        MaPhong = Long.parseLong(soapItem.getProperty("MaPhong").toString());
                    }
                    String MucLucSo = "";
                    if (soapItem.hasProperty("MucLucSo"))
                        MucLucSo = soapItem.getProperty("MucLucSo").toString();
                    if (MucLucSo.equals("anyType{}"))
                        MucLucSo = "";
//                    long MaHopHS = 0;
//                    if (soapItem.hasAttribute("MaHopHS"))
                    long    MaHopHS = Long.parseLong(soapItem.getProperty("MaHopHS").toString());
                    String HoSoSo = "";
                    if (soapItem.hasProperty("HoSoSo"))
                        HoSoSo = soapItem.getProperty("HoSoSo").toString();
                    if (HoSoSo.equals("anyType{}"))
                        HoSoSo = "";
                    String KHTT = "";
                    if (soapItem.hasProperty("KHTT"))
                        KHTT = soapItem.getProperty("KHTT").toString();
                    if (KHTT.equals("anyType{}"))
                        KHTT = "";
                    String IDShow = "";
                    if (soapItem.hasProperty("IDShow"))
                        IDShow = soapItem.getProperty("IDShow").toString();
                    if (IDShow.equals("anyType{}"))
                        IDShow = "";
                    String TieuDeHs = "";
                    if (soapItem.hasProperty("TieuDeHs"))
                        TieuDeHs = soapItem.getProperty("TieuDeHs").toString();
                    if (TieuDeHs.equals("anyType{}"))
                        TieuDeHs = "";
                    String ChuGiai = "";
                    if (soapItem.hasProperty("ChuGiai"))
                        ChuGiai = soapItem.getProperty("ChuGiai").toString();
                    if (ChuGiai.equals("anyType{}"))
                        ChuGiai = "";
                    String ThoiGianBatDau = "";
                    if (soapItem.hasProperty("ThoiGianBatDau"))
                        ThoiGianBatDau = soapItem.getProperty("ThoiGianBatDau").toString();
                    if (ThoiGianBatDau.equals("anyType{}"))
                        ThoiGianBatDau = "";
                    String ThoiGianKetThuc = "";
                    if (soapItem.hasProperty("ThoiGianKetThuc"))
                        ThoiGianKetThuc = soapItem.getProperty("ThoiGianKetThuc").toString();
                    if (ThoiGianKetThuc.equals("anyType{}"))
                        ThoiGianKetThuc = "";
                    int MaNN = 1;
                    if (soapItem.hasProperty("MaNN") && (soapItem.getProperty("MaNN") != null))
                        MaNN = Integer.parseInt(soapItem.getProperty("MaNN").toString());
                    String ButTich = "";
                    if (soapItem.hasProperty("ButTich"))
                        ButTich = soapItem.getProperty("ButTich").toString();
                    if (ButTich.equals("anyType{}"))
                        ButTich = "";
                    String SoLuongTo = "";
                    if (soapItem.hasProperty("SoLuongTo"))
                        SoLuongTo = soapItem.getProperty("SoLuongTo").toString();
                    if (SoLuongTo.equals("anyType{}"))
                        SoLuongTo = "";
                    int MaTHBQ = 1;
                    if (soapItem.hasProperty("MaTHBQ") && (soapItem.getProperty("MaTHBQ") != null))
                        MaTHBQ = Integer.parseInt(soapItem.getProperty("MaTHBQ").toString());
                    int MaCDSD = 1;
                    if (soapItem.hasProperty("MaCDSD") && (soapItem.getProperty("MaCDSD") != null))
                        MaCDSD = Integer.parseInt(soapItem.getProperty("MaCDSD").toString());
                    int MaTTVL = 1;
                    if (soapItem.hasProperty("MaTTVL") && (soapItem.getProperty("MaTTVL") != null))
                        MaTTVL = Integer.parseInt(soapItem.getProperty("MaTTVL").toString());
                    HoSo_ett hoSo_ett = new HoSo_ett(MaPhong, MucLucSo, MaHopHS, HoSoSo, KHTT, IDShow, TieuDeHs, ChuGiai, ThoiGianBatDau, ThoiGianKetThuc,
                            MaNN, ButTich, SoLuongTo, MaTHBQ, MaCDSD, MaTTVL, total_record, ErrCode, ErrDesc);
                    arrData.add(hoSo_ett);
                }
            }
            return arrData;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
