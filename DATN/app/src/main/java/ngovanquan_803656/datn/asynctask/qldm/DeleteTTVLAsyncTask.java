package ngovanquan_803656.datn.asynctask.qldm;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalFloat;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import ngovanquan_803656.datn.model.TTVL_ett;
import ngovanquan_803656.datn.utils.Constants;

/**
 * Created by ngoquan on 4/14/2016.
 */
public class DeleteTTVLAsyncTask extends AsyncTask<Void, Void, TTVL_ett> {
    private Activity activity;
    private long MaTTVL;
    private ProgressDialog progressDialog;
    private TTVL_ett ttvl_ett;

    public DeleteTTVLAsyncTask(Activity activity,long MaTTVL) {
        this.activity = activity;
        this.MaTTVL = MaTTVL;
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
    protected void onPostExecute(TTVL_ett ttvl_ett) {
        super.onPostExecute(ttvl_ett);
        progressDialog.dismiss();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected TTVL_ett doInBackground(Void... params) {
        try {
            final String METHOD_NAME = "Delete_TTVL";
            final String SOAP_ACTION = Constants.NAMESPACE + METHOD_NAME;
            SoapObject request = new SoapObject(Constants.NAMESPACE, METHOD_NAME);
            request.addProperty("mattvl", this.MaTTVL);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            MarshalFloat marshalFloat = new MarshalFloat();
            marshalFloat.register(envelope);
            HttpTransportSE httpTransportSE = new HttpTransportSE(Constants.URL_TTVL);
            httpTransportSE.call(SOAP_ACTION, envelope);

            SoapObject soapData = (SoapObject) envelope.getResponse();

            String ErrCode = soapData.getProperty("ErrCode").toString();
            String ErrDesc = soapData.getProperty("ErrDesc").toString();
            int total_record = Integer.parseInt(soapData.getProperty("Total_record").toString());
            ttvl_ett = new TTVL_ett(total_record, ErrCode, ErrDesc);
            return ttvl_ett;

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
