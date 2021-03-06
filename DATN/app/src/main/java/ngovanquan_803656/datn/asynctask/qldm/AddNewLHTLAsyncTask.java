package ngovanquan_803656.datn.asynctask.qldm;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.Marshal;
import org.ksoap2.serialization.MarshalFloat;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import ngovanquan_803656.datn.model.LHTL_ett;
import ngovanquan_803656.datn.utils.Constants;

/**
 * Created by ngoquan on 4/11/2016.
 */
public class AddNewLHTLAsyncTask extends AsyncTask<Void, Void, LHTL_ett> {

    private Activity activity;
    private String TenLoaiHinhTL;
    private long STT;
    private boolean Active;
    private String GhiChu;
    private ProgressDialog progressDialog;
    private LHTL_ett lhtl_ett;
    public AddNewLHTLAsyncTask(Activity activity, String TenLoaiHinhTL, long STT, String GhiChu, boolean Active) {
        this.activity = activity;
        this.TenLoaiHinhTL = TenLoaiHinhTL;
        this.STT = STT;
        this.GhiChu = GhiChu;
        this.Active = Active;
        progressDialog = new ProgressDialog(this.activity);
        progressDialog.setTitle("Loading ...");
        progressDialog.setMessage("Please wait ..!");
        progressDialog.setCancelable(false);
    }


    @Override
    protected void onPostExecute(LHTL_ett aVoid) {
        super.onPostExecute(aVoid);
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
            final String METHOD_NAME = "Insert_LHTL";
            final String SOAP_ACTION = Constants.NAMESPACE + METHOD_NAME;
            SoapObject request = new SoapObject(Constants.NAMESPACE, METHOD_NAME);
            request.addProperty("ten", this.TenLoaiHinhTL);
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
