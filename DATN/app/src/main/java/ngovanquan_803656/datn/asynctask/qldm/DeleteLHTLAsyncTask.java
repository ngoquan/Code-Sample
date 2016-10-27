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

import ngovanquan_803656.datn.R;
import ngovanquan_803656.datn.model.LHTL_ett;
import ngovanquan_803656.datn.utils.Constants;

/**
 * Created by ngoquan on 4/11/2016.
 */
public class DeleteLHTLAsyncTask extends AsyncTask<Void, Void, LHTL_ett> {

    private Activity activity;
    private long MaLoaiHinhTL;
    private ProgressDialog progressDialog;
    private LHTL_ett lhtl_ett;

    public DeleteLHTLAsyncTask(Activity activity, long MaLoaiHinhTL) {
        this.activity = activity;
        this.MaLoaiHinhTL = MaLoaiHinhTL;
        progressDialog = new ProgressDialog(this.activity);
        progressDialog.setTitle(this.activity.getResources().getString(R.string.title_loading));
        progressDialog.setMessage(this.activity.getResources().getString(R.string.message_loading));
        progressDialog.setCancelable(false);
    }
    @Override
    protected void onPostExecute(LHTL_ett lhtl_ett) {
        super.onPostExecute(lhtl_ett);
        progressDialog.dismiss();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.show();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected LHTL_ett doInBackground(Void... params) {
        try {
            final String METHOD_NAME = "Delete_LHTL";
            final String SOAP_ACTION = Constants.NAMESPACE + METHOD_NAME;
            SoapObject request = new SoapObject(Constants.NAMESPACE, METHOD_NAME);
            request.addProperty("ma", this.MaLoaiHinhTL);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            MarshalFloat marshalFloat = new MarshalFloat();
            marshalFloat.register(envelope);
            HttpTransportSE httpTransportSE = new HttpTransportSE(Constants.URL_LHTL);
            httpTransportSE.call(SOAP_ACTION, envelope);
            SoapObject soapData = (SoapObject) envelope.getResponse();

            String ErrCode = soapData.getProperty("ErrCode").toString();
            String ErrDesc = soapData.getProperty("ErrDesc").toString();
            int total_record = Integer.parseInt(soapData.getProperty("Total_record").toString());

            lhtl_ett = new LHTL_ett(total_record, ErrCode, ErrDesc);
            return lhtl_ett;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
