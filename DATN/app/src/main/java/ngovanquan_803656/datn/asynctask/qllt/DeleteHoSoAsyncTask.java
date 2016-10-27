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
 * Created by NGOQUAN on 5/30/2016.
 */
public class DeleteHoSoAsyncTask extends AsyncTask<Void, Void, HoSo_ett> {

    private Activity activity;
    private ProgressDialog progressDialog;
    private String HoSoSo;

    public DeleteHoSoAsyncTask(Activity activity, String HoSoSo) {
        this.activity = activity;
        this.HoSoSo = HoSoSo;
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
            final String METHOD_NAME = "Delete_HoSo";
            final String SOAP_ACTION = Constants.NAMESPACE + METHOD_NAME;
            SoapObject request = new SoapObject(Constants.NAMESPACE, METHOD_NAME);
            request.addProperty("maHoSo", this.HoSoSo);
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
