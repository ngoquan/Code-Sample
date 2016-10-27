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
import java.util.ArrayList;

import ngovanquan_803656.datn.model.VanBan_ett;
import ngovanquan_803656.datn.utils.Constants;

/**
 * Created by NGOQUAN on 5/25/2016.
 */
public class AddNewAndUpdateVanBanAsyncTask extends AsyncTask<Void, Void, VanBan_ett> {

    private Activity activity;
    private ProgressDialog progressDialog;
    private String MucLucSo, HoSoSo, ToSo, SoKyHieu, ThoiGian, TacGia, TrichYeuND, KHTT, SoLuongTo, ButTich, GhiChu, VanBanID;

    private int function, MaLVB, MaDoMat, MaDoTinCay, MaTTVL, MaNN, MaCDSD;

    public AddNewAndUpdateVanBanAsyncTask(Activity activity, int function, String VanBanID, String MucLucSo, String HoSoSo, String ToSo, String SoKyHieu,
                                          String ThoiGian, String TacGia, String TrichYeuND, String KHTT, String SoLuongTo, String ButTich, String GhiChu,
                                          int MaLVB, int MaDoMat, int MaDoTinCay, int MaTTVL, int MaNN, int MaCDSD) {
        this.activity = activity;
        this.function = function;
        this.VanBanID = VanBanID;
        this.MucLucSo = MucLucSo;
        this.HoSoSo = HoSoSo;
        this.ToSo = ToSo;
        this.SoKyHieu = SoKyHieu;
        this.ThoiGian = ThoiGian;
        this.TacGia = TacGia;
        this.TrichYeuND = TrichYeuND;
        this.KHTT = KHTT;
        this.SoLuongTo = SoLuongTo;
        this.ButTich = ButTich;
        this.GhiChu = GhiChu;
        this.MaLVB = MaLVB;
        this.MaDoMat = MaDoMat;
        this.MaDoTinCay = MaDoTinCay;
        this.MaTTVL = MaTTVL;
        this.MaNN = MaNN;
        this.MaCDSD = MaCDSD;
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
    protected void onPostExecute(VanBan_ett vanBan_ett) {
        super.onPostExecute(vanBan_ett);
        progressDialog.dismiss();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected VanBan_ett doInBackground(Void... params) {
        try {
            String METHOD_NAME = "Insert_VanBan";
            if (function == Constants.FUNCTION_UPDATE)
                METHOD_NAME = "Update_VanBan";
            final String SOAP_ACTION = Constants.NAMESPACE + METHOD_NAME;
            SoapObject request = new SoapObject(Constants.NAMESPACE, METHOD_NAME);
            request.addProperty("id", this.VanBanID);
            request.addProperty("muclucso", this.MucLucSo);
            request.addProperty("hsso", this.HoSoSo);
            request.addProperty("toso", this.ToSo);
            request.addProperty("sokyhieu", this.SoKyHieu);
            request.addProperty("thoigian", this.ThoiGian);
            request.addProperty("tacgia", this.TacGia);
            request.addProperty("maLVB", this.MaLVB);
            request.addProperty("trichyeuND", this.TrichYeuND);
            request.addProperty("khtt", this.KHTT);
            request.addProperty("madomat", this.MaDoMat);
            request.addProperty("soluongto", this.SoLuongTo);
            request.addProperty("madotincay", this.MaDoTinCay);
            request.addProperty("maTTVL", this.MaTTVL);
            request.addProperty("maCDSD", this.MaCDSD);
            request.addProperty("maNN", this.MaNN);
            request.addProperty("buttich", this.ButTich);
            request.addProperty("ghichu", this.GhiChu);
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
            VanBan_ett vanBan_ett = new VanBan_ett(total_record, ErrCode, ErrDesc);
            return vanBan_ett;
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
