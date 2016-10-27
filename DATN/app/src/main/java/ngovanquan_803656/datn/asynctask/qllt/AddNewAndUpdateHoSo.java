package ngovanquan_803656.datn.asynctask.qllt;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.MarshalFloat;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import ngovanquan_803656.datn.model.HoSo_ett;
import ngovanquan_803656.datn.utils.Constants;

/**
 * Created by NGOQUAN on 5/23/2016.
 */
public class AddNewAndUpdateHoSo extends AsyncTask<Void, Void, HoSo_ett> {

    Activity activity;
    ProgressDialog progressDialog;
    String MucLucSo, HoSoSo, KHTT, TieuDeHS, ChuGiai, ThoiGianBatDau, ThoigianKetThuc, ButTich, SoLuongTo, MaHopHS;
//    long MaHopHS;
    int function, MaNN, MaTHBQ, MaCDSD, MaTTVL;

    public AddNewAndUpdateHoSo(Activity activity, int function, String MucLucSo, String HoSoSo, String KHTT, String TieuDeHs, String ChuGiai, String ThoiGianBatDau,
                               String ThoiGianKetThuc, String ButTich, String SoLuongTo, String MaHopHS, int MaNN, int MaTHBQ, int MaCDSD, int MaTTVL) {
        this.activity = activity;
        this.function = function;
        this.MucLucSo = MucLucSo;
        this.HoSoSo = HoSoSo;
        this.KHTT = KHTT;
        this.TieuDeHS = TieuDeHs;
        this.ChuGiai = ChuGiai;
        this.ThoiGianBatDau = ThoiGianBatDau;
        this.ThoigianKetThuc = ThoiGianKetThuc;
        this.ButTich = ButTich;
        this.SoLuongTo = SoLuongTo;
        this.MaHopHS = MaHopHS;
        this.MaNN = MaNN;
        this.MaTHBQ = MaTHBQ;
        this.MaCDSD = MaCDSD;
        this.MaTTVL = MaTTVL;

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
    protected void onPostExecute(HoSo_ett hoSo_ett) {
        super.onPostExecute(hoSo_ett);
        progressDialog.dismiss();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected HoSo_ett doInBackground(Void... params) {
        try {
            String METHOD_NAME = "Insert_HoSo";
            if (function == Constants.FUNCTION_UPDATE) {
                METHOD_NAME = "Update_HoSo";
            }
            final String SOAP_ACTION = Constants.NAMESPACE + METHOD_NAME;
            SoapObject request = new SoapObject(Constants.NAMESPACE, METHOD_NAME);
            request.addProperty("muclucso", this.MucLucSo);
            request.addProperty("hopso", this.MaHopHS);
            request.addProperty("hososo", this.HoSoSo);
            request.addProperty("khtt", this.KHTT);
            request.addProperty("mangonngu", this.MaNN);
            request.addProperty("tieudehoso", this.TieuDeHS);
            request.addProperty("chugiai", this.ChuGiai);
            request.addProperty("thoigianbatdau", this.ThoiGianBatDau);
            request.addProperty("thoigianketthuc", this.ThoigianKetThuc);
            request.addProperty("buttich", this.ButTich);
            request.addProperty("thoihanbaoquan", this.MaTHBQ);
            request.addProperty("soluongto", this.SoLuongTo);
            request.addProperty("chedosudung", this.MaCDSD);
            request.addProperty("tinhtrangvatly", this.MaTTVL);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            MarshalFloat marshalFloat = new MarshalFloat();
            marshalFloat.register(envelope);
            HttpTransportSE httpTransportSE = new HttpTransportSE(Constants.URL_HOSO);
            httpTransportSE.call(SOAP_ACTION, envelope);
            SoapObject soapData = (SoapObject) envelope.getResponse();
            String ErrCode = soapData.getProperty("ErrCode").toString();
            String ErrDesc = soapData.getProperty("ErrDesc").toString();
            int total_record = Integer.parseInt(soapData.getProperty("Total_record").toString());
            HoSo_ett hoSo_ett = new HoSo_ett(total_record, ErrCode, ErrDesc);
            return hoSo_ett;
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
