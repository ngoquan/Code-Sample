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

import ngovanquan_803656.datn.model.CDSD_ett;
import ngovanquan_803656.datn.utils.Constants;

/**
 * Created by ngoquan on 4/14/2016.
 */
public class UpdateCDSDAsyncTask extends AsyncTask<Void, Void, CDSD_ett> {
    private Activity activity;
    private long MaCDSD;
    private String TenCDSD;
    private int STT;
    private boolean Active;
    private String GhiChu;
    private ProgressDialog progressDialog;
    private CDSD_ett cdsd_ett;

    public UpdateCDSDAsyncTask(Activity activity, long MaCDSD, String TenCDSD, String GhiChu, int STT, boolean Active) {
        this.activity = activity;
        this.MaCDSD = MaCDSD;
        this.TenCDSD = TenCDSD;
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
    protected void onPostExecute(CDSD_ett cdsd_ett) {
        super.onPostExecute(cdsd_ett);
        progressDialog.dismiss();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected CDSD_ett doInBackground(Void... params) {
        try {
            final String METHOD_NAME = "Update_CDSD";
            final String SOAP_ACTION = Constants.NAMESPACE + METHOD_NAME;
            SoapObject request = new SoapObject(Constants.NAMESPACE, METHOD_NAME);
            request.addProperty("ma", this.MaCDSD);
            request.addProperty("ten", this.TenCDSD);
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
            HttpTransportSE httpTransportSE = new HttpTransportSE(Constants.URL_CDSD);
            httpTransportSE.call(SOAP_ACTION, envelope);

            SoapObject soapData = (SoapObject) envelope.getResponse();

            String ErrCode = soapData.getProperty("ErrCode").toString();
            String ErrDesc = soapData.getProperty("ErrDesc").toString();
            int total_record = Integer.parseInt(soapData.getProperty("Total_record").toString());
            cdsd_ett = new CDSD_ett(total_record, ErrCode, ErrDesc);
            return cdsd_ett;

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
