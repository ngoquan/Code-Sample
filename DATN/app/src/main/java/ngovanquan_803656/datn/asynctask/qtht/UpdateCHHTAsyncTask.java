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

import ngovanquan_803656.datn.model.CHHT_ett;
import ngovanquan_803656.datn.utils.Constants;

/**
 * Created by NGOQUAN on 5/30/2016.
 */
public class UpdateCHHTAsyncTask extends AsyncTask<Void, Void, CHHT_ett> {

    private Activity activity;
    private ProgressDialog progressDialog;
    private String tenSoNoiVu, tenChiCucVT, diaChi, soDT, email, website, tenPhanMem, password;
    public UpdateCHHTAsyncTask(Activity activity, String tenSoNoiVu, String tenChiCucVT, String diaChi, String soDT, String email, String website,
                               String tenPhanMem, String password) {
        this.activity = activity;
        this.tenSoNoiVu = tenSoNoiVu;
        this.tenChiCucVT = tenChiCucVT;
        this.diaChi = diaChi;
        this.soDT = soDT;
        this.email = email;
        this.website = website;
        this.tenPhanMem = tenPhanMem;
        this.password = password;
        progressDialog = new ProgressDialog(this.activity);
        progressDialog.setTitle("Loading ...");
        progressDialog.setMessage("Please wait ..!");
        progressDialog.setCancelable(false);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(CHHT_ett chht_ett) {
        super.onPostExecute(chht_ett);
        progressDialog.dismiss();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected CHHT_ett doInBackground(Void... params) {
        try {
            String METHOD_NAME = "Insert_CHHT";
            final String SOAP_ACTION = Constants.NAMESPACE + METHOD_NAME;
            SoapObject request = new SoapObject(Constants.NAMESPACE, METHOD_NAME);
            request.addProperty("tenSoNoiVu", this.tenSoNoiVu);
            request.addProperty("tenChiCucVT", this.tenChiCucVT);
            request.addProperty("dc", this.diaChi);
            request.addProperty("sdt", this.soDT);
            request.addProperty("email", this.email);
            request.addProperty("website", this.website);
            request.addProperty("tenPM", this.tenPhanMem);
            if (!this.password.equals(""))
                request.addProperty("password", this.password);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            MarshalFloat marshalFloat = new MarshalFloat();
            marshalFloat.register(envelope);
            HttpTransportSE httpTransportSE = new HttpTransportSE(Constants.URL_CHHT);
            httpTransportSE.call(SOAP_ACTION, envelope);
            SoapObject soapData = (SoapObject) envelope.getResponse();
            String ErrCode = soapData.getProperty("ErrCode").toString();
            String ErrDesc = soapData.getProperty("ErrDesc").toString();

            CHHT_ett chht_ett = new CHHT_ett(ErrCode, ErrDesc);
            return chht_ett;
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
