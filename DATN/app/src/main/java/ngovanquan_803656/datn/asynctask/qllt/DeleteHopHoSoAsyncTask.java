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

import ngovanquan_803656.datn.model.HopHoSo_ett;
import ngovanquan_803656.datn.utils.Constants;

/**
 * Created by ngoquan on 5/12/2016.
 */
public class DeleteHopHoSoAsyncTask extends AsyncTask<Void, Void, HopHoSo_ett> {
    private Activity activity;
    private long MaHopHS;
    private int function;
    private ProgressDialog progressDialog;
    private HopHoSo_ett hopHoSo_ett;
    public DeleteHopHoSoAsyncTask(Activity activity, int function, long MaHopHS) {
        this.activity = activity;
        this.function = function;
        this.MaHopHS = MaHopHS;
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
    protected void onPostExecute(HopHoSo_ett hopHoSo_ett) {
        super.onPostExecute(hopHoSo_ett);
        progressDialog.dismiss();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected HopHoSo_ett doInBackground(Void... params) {
        try {
            String METHOD_NAME = "Delete_HopHoSo";
            if (function == Constants.FUNCTION_CHECK) {
                METHOD_NAME = "Check_IDHopHoSo";
            }
            final String SOAP_ACTION = Constants.NAMESPACE + METHOD_NAME;
            SoapObject request = new SoapObject(Constants.NAMESPACE, METHOD_NAME);
            request.addProperty("ma", this.MaHopHS);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            MarshalFloat marshalFloat = new MarshalFloat();
            marshalFloat.register(envelope);
            HttpTransportSE httpTransportSE = new HttpTransportSE(Constants.URL_HOPHS);
            httpTransportSE.call(SOAP_ACTION, envelope);
            SoapObject soapData = (SoapObject) envelope.getResponse();
            String ErrCode = soapData.getProperty("ErrCode").toString();
            String ErrDesc = soapData.getProperty("ErrDesc").toString();
            int total_record = Integer.parseInt(soapData.getProperty("Total_record").toString());
            hopHoSo_ett = new HopHoSo_ett(total_record, ErrCode, ErrDesc);
            return hopHoSo_ett;
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
