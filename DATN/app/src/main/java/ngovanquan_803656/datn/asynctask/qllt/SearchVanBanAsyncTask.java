package ngovanquan_803656.datn.asynctask.qllt;

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

import ngovanquan_803656.datn.model.VanBan_ett;
import ngovanquan_803656.datn.utils.Constants;

/**
 * Created by NGOQUAN on 5/24/2016.
 */
public class SearchVanBanAsyncTask extends AsyncTask<Void, Void, ArrayList<VanBan_ett>> {

    private Activity activity;
    private ProgressDialog progressDialog;
    private String search_type, search_val;
    private int page_current;
    private ArrayList<VanBan_ett> arrVanBan = new ArrayList<>();
    public SearchVanBanAsyncTask(Activity activity, String search_type, String search_val, int page_current) {
        this.activity = activity;
        this.search_type = search_type;
        this.search_val = search_val;
        this.page_current = page_current;
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
    protected void onPostExecute(ArrayList<VanBan_ett> vanBan_etts) {
        super.onPostExecute(vanBan_etts);
        progressDialog.dismiss();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected ArrayList<VanBan_ett> doInBackground(Void... params) {
        try {
            final String METHOD_NAME = "SearchVanBan";
            final String SOAP_ACTION = Constants.NAMESPACE + METHOD_NAME;
            SoapObject request = new SoapObject(Constants.NAMESPACE, METHOD_NAME);
            request.addProperty("search_type", this.search_type);
            request.addProperty("search_val", this.search_val);
            request.addProperty("page_curr", this.page_current);
            request.addProperty("num_row_per_page", Constants.NUM_ROW_PER_PAGE);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            MarshalFloat marshalFloat = new MarshalFloat();
            marshalFloat.register(envelope);
            HttpTransportSE httpTransportSE = new HttpTransportSE(Constants.URL_VANBAN);
            httpTransportSE.call(SOAP_ACTION, envelope);
            SoapObject soapData = (SoapObject) envelope.getResponse();
            String ErrCode = soapData.getProperty("ErrCode").toString();
            String ErrDesc = soapData.getProperty("ErrDesc").toString();
            int total_record = Integer.parseInt(soapData.getProperty("Total_record").toString());
            if (soapData.hasProperty("Data")) {
                SoapObject soapArr = (SoapObject) soapData.getProperty("Data");
                for (int i = 0; i < soapArr.getPropertyCount(); i++) {
                    SoapObject soapItem = (SoapObject) soapArr.getProperty(i);
                    String MucLucSo = "";
                    if (soapItem.hasProperty("MucLucSo")) {
                        MucLucSo = soapItem.getProperty("MucLucSo").toString();
                    }
                    if (MucLucSo.equals("anyType{}"))
                        MucLucSo = "";
                    String HoSoSo = "";
                    if (soapItem.hasProperty("HoSoSo"))
                        HoSoSo = soapItem.getProperty("HoSoSo").toString();
                    if (HoSoSo.equals("anyType{}"))
                        HoSoSo = "";
                    String ToSo = "";
                    if (soapItem.hasProperty("ToSo"))
                        ToSo = soapItem.getProperty("ToSo").toString();
                    if (ToSo.equals("anyType{}"))
                        ToSo = "";
                    String SoKyHieu = "";
                    if (soapItem.hasProperty("SoKyHieu"))
                        SoKyHieu = soapItem.getProperty("SoKyHieu").toString();
                    if (SoKyHieu.equals("anyType{}"))
                        SoKyHieu = "";
                    String ThoiGian = "";
                    if (soapItem.hasProperty("ThoiGian"))
                        ThoiGian = soapItem.getProperty("ThoiGian").toString();
                    if (ThoiGian.equals("anyType{}"))
                        ThoiGian = "";
                    String TacGia = "";
                    if (soapItem.hasProperty("TacGia"))
                        TacGia = soapItem.getProperty("TacGia").toString();
                    if (TacGia.equals("anyType{}"))
                        TacGia = "";
                    int MaLVB = 1;
                    if (soapItem.hasProperty("MaLVB") && (soapItem.getProperty("MaLVB") != null)) {
                        MaLVB = Integer.parseInt(soapItem.getProperty("MaLVB").toString());
                    }
                    String TrichYeuND = "";
                    if (soapItem.hasProperty("TrichYeuND"))
                        TrichYeuND = soapItem.getProperty("TrichYeuND").toString();
                    if (TrichYeuND.equals("anyType{}"))
                        TrichYeuND = "";
                    String KHTT = "";
                    if (soapItem.hasProperty("KHTT"))
                        KHTT = soapItem.getProperty("KHTT").toString();
                    if (KHTT.equals("anyType{}"))
                        KHTT = "";
                    int MaDoMat = 1;
                    if (soapItem.hasProperty("MaDoMat") && (soapItem.getProperty("MaDoMat") != null))
                        MaDoMat = Integer.parseInt(soapItem.getProperty("MaDoMat").toString());
                    String SoLuongTo = "";
                    if (soapItem.hasProperty("SoLuongTo"))
                        SoLuongTo = soapItem.getProperty("SoLuongTo").toString();
                    if (SoLuongTo.equals("anyType{}"))
                        SoLuongTo = "";
                    int MaDoTinCay = 1;
                    if (soapItem.hasAttribute("MaDoTinCay") && (soapItem.getProperty("MaDoTinCay") != null))
                        MaDoTinCay = Integer.parseInt(soapItem.getProperty("MaDoTinCay").toString());
                    int MaTTVL = 1;
                    if (soapItem.hasAttribute("MaTTVL") && (soapItem.getProperty("MaTTVL") != null))
                        MaTTVL = Integer.parseInt(soapItem.getProperty("MaTTVL").toString());
                    int MaCDSD = 1;
                    if (soapItem.hasAttribute("MaCDSD") && (soapItem.getProperty("MaCDSD") != null))
                        MaCDSD = Integer.parseInt(soapItem.getProperty("MaCDSD").toString());
                    int MaNN = 1;
                    if (soapItem.hasAttribute("MaNN") && (soapItem.getProperty("MaNN") != null))
                        MaNN = Integer.parseInt(soapItem.getProperty("MaNN").toString());
                    String ButTich = "";
                    if (soapItem.hasProperty("ButTich"))
                        ButTich = soapItem.getProperty("ButTich").toString();
                    if (ButTich.equals("anyType{}"))
                        ButTich = "";
                    String GhiChu = "";
                    if (soapItem.hasProperty("GhiChu"))
                        GhiChu = soapItem.getProperty("GhiChu").toString();
                    if (GhiChu.equals("anyType{}"))
                        GhiChu = "";
                    String VanBanId = "";
                    if (soapItem.hasProperty("VanBanId"))
                        VanBanId = soapItem.getProperty("VanBanId").toString();
//                    Log.e("ResultSearch", VanBanId + " - " + ErrDesc);
                    VanBan_ett vanBan_ett = new VanBan_ett(VanBanId, MucLucSo, HoSoSo, ToSo, SoKyHieu, ThoiGian, TacGia, MaLVB, TrichYeuND,
                            KHTT, MaDoMat, SoLuongTo, MaDoTinCay, MaTTVL, MaCDSD, MaNN, ButTich, GhiChu, total_record, ErrCode, ErrDesc);
                    arrVanBan.add(vanBan_ett);
                }
            }

            return arrVanBan;
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
