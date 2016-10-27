package ngovanquan_803656.datn.asynctask.qldm;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalFloat;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import ngovanquan_803656.datn.R;
import ngovanquan_803656.datn.model.DoMat_ett;
import ngovanquan_803656.datn.utils.Constants;

/**
 * Created by ngoquan on 4/13/2016.
 */
public class UpdateDoMatAsyncTask extends AsyncTask<Void, Void, DoMat_ett> {
    private Activity activity;
    private long MaDoMat;
    private String TenDoMat;
    private int STT;
    private boolean Active;
    private String GhiChu;
    private ProgressDialog progressDialog;
    private DoMat_ett doMat_ett;

    public UpdateDoMatAsyncTask(Activity activity, long MaDoMat, String TenDoMat, String GhiChu, int STT, boolean Active) {
        this.activity = activity;
        this.MaDoMat = MaDoMat;
        this.TenDoMat = TenDoMat;
        this.GhiChu = GhiChu;
        this.STT = STT;
        this.Active = Active;

        progressDialog = new ProgressDialog(this.activity);
        progressDialog.setTitle(this.activity.getResources().getString(R.string.title_loading));
        progressDialog.setMessage(this.activity.getResources().getString(R.string.message_loading));
        progressDialog.setCancelable(false);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(DoMat_ett doMat_ett) {
        super.onPostExecute(doMat_ett);
        progressDialog.dismiss();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected DoMat_ett doInBackground(Void... params) {
        try {
            final String METHOD_NAME = "Update_DoMat";
            final String SOAP_ACTION = Constants.NAMESPACE + METHOD_NAME;
            SoapObject request = new SoapObject(Constants.NAMESPACE, METHOD_NAME);
            request.addProperty("ma", this.MaDoMat);
            request.addProperty("ten", this.TenDoMat);
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
            HttpTransportSE httpTransportSE = new HttpTransportSE(Constants.URL_DM);
            httpTransportSE.call(SOAP_ACTION, envelope);
            SoapObject soapData = (SoapObject) envelope.getResponse();

            String ErrCode = soapData.getProperty("ErrCode").toString();
            String ErrDesc = soapData.getProperty("ErrDesc").toString();
            int total_record = Integer.parseInt(soapData.getProperty("Total_record").toString());

            doMat_ett = new DoMat_ett(total_record, ErrCode, ErrDesc);
            return doMat_ett;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
