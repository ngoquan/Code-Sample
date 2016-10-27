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

import ngovanquan_803656.datn.model.THBQ_ett;
import ngovanquan_803656.datn.utils.Constants;

/**
 * Created by ngoquan on 4/14/2016.
 */
public class UpdateTHBQAsyncTask extends AsyncTask<Void, Void, THBQ_ett> {
    private Activity activity;
    private long MaTHBQ;
    private String TenTHBQ;
    private int STT;
    private boolean Active;
    private String GhiChu;
    private ProgressDialog progressDialog;
    private THBQ_ett thbq_ett;

    public UpdateTHBQAsyncTask(Activity activity, long MaTHBQ, String TenTHBQ, String GhiChu, int STT, boolean Active) {
        this.activity = activity;
        this.MaTHBQ = MaTHBQ;
        this.TenTHBQ = TenTHBQ;
        this.GhiChu = GhiChu;
        this.STT = STT;
        this.Active = Active;

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
    protected void onPostExecute(THBQ_ett thbq_ett) {
        super.onPostExecute(thbq_ett);
        progressDialog.dismiss();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected THBQ_ett doInBackground(Void... params) {
        try {
            final String METHOD_NAME = "Update_THBQ";
            final String SOAP_ACTION = Constants.NAMESPACE + METHOD_NAME;
            SoapObject request = new SoapObject(Constants.NAMESPACE, METHOD_NAME);
            request.addProperty("mathbq", this.MaTHBQ);
            request.addProperty("tenthbq", this.TenTHBQ);
            request.addProperty("ghichu", this.GhiChu);
            request.addProperty("stt", this.STT);
            if (this.Active == true)
                request.addProperty("active", 1);
            else
                request.addProperty("active", 0);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            MarshalFloat marshalFloat = new MarshalFloat();
            marshalFloat.register(envelope);
            HttpTransportSE httpTransportSE = new HttpTransportSE(Constants.URL_THBQ);
            httpTransportSE.call(SOAP_ACTION, envelope);

            SoapObject soapData = (SoapObject) envelope.getResponse();

            String ErrCode = soapData.getProperty("ErrCode").toString();
            String ErrDesc = soapData.getProperty("ErrDesc").toString();
            int total_record = Integer.parseInt(soapData.getProperty("Total_record").toString());
            thbq_ett = new THBQ_ett(total_record, ErrCode, ErrDesc);
            return thbq_ett;

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
