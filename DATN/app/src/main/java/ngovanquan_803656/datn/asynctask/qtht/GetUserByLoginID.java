package ngovanquan_803656.datn.asynctask.qtht;

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

import ngovanquan_803656.datn.model.QLND_ett;
import ngovanquan_803656.datn.utils.Constants;

/**
 * Created by NGOQUAN on 5/26/2016.
 */
public class GetUserByLoginID extends AsyncTask<Void, Void, QLND_ett> {

    private Activity activity;
    private String LoginID;
    private ProgressDialog progressDialog;

    public GetUserByLoginID(Activity activity, String LoginID) {
        this.activity = activity;
        this.LoginID = LoginID;
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
    protected void onPostExecute(QLND_ett qlnd_ett) {
        super.onPostExecute(qlnd_ett);
        progressDialog.dismiss();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected QLND_ett doInBackground(Void... params) {
        try {
            final String METHOD_NAME = "GetUserByLoginID";
            final String SOAP_ACTION = Constants.NAMESPACE + METHOD_NAME;
            SoapObject request = new SoapObject(Constants.NAMESPACE, METHOD_NAME);
            request.addProperty("LoginID", this.LoginID);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            MarshalFloat marshalFloat = new MarshalFloat();
            marshalFloat.register(envelope);
            HttpTransportSE httpTransportSE = new HttpTransportSE(Constants.URL_AUTH);
            httpTransportSE.call(SOAP_ACTION, envelope);
            SoapObject soapData = (SoapObject) envelope.getResponse();
            int total_record = Integer.parseInt(soapData.getProperty("Total_record").toString());
            if (soapData.hasProperty("Data")) {
                SoapObject soapUser = (SoapObject) soapData.getProperty("Data");
                String LoginID = soapUser.getProperty("LoginID").toString();
                String HoTen = "";
                if (soapUser.hasProperty("HoTen"))
                    HoTen = soapUser.getProperty("HoTen").toString();
                if (HoTen.equals("anyType{}"))
                    HoTen = "";
                int MaNhom = Integer.parseInt(soapUser.getProperty("MaNhom").toString());
                String MatKhau = soapUser.getProperty("MatKhau").toString();
                String GhiChu = "";
                if (soapUser.hasProperty("GhiChu"))
                    GhiChu = soapUser.getProperty("GhiChu").toString();
                if (GhiChu.equals("anyType{}"))
                    GhiChu = "";
                boolean Active = Boolean.parseBoolean(soapUser.getProperty("Active").toString());
                QLND_ett qlnd_ett = new QLND_ett(LoginID, HoTen, MaNhom, MatKhau, GhiChu, Active);
                return qlnd_ett;
            }
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
