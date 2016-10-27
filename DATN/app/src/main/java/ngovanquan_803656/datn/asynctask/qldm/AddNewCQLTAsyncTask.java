package ngovanquan_803656.datn.asynctask.qldm;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.MarshalFloat;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import ngovanquan_803656.datn.model.CQLT_ett;
import ngovanquan_803656.datn.model.NgonNgu_ett;
import ngovanquan_803656.datn.utils.Constants;

/**
 * Created by ngoquan on 5/11/2016.
 */
public class AddNewCQLTAsyncTask extends AsyncTask<Void, Void, CQLT_ett> {
    private Activity activity;
    private String MaCQLT, TenCQLT, DiaChi, SDT, Email, Website, LichSuPhatTrien;
    private int function;
    private ProgressDialog progressDialog;
    private CQLT_ett cqlt_ett;
    public AddNewCQLTAsyncTask(Activity activity, int function, String MaCQLT, String TenCQLT, String DiaChi, String SDT, String Email, String Website,
                               String LichSuPhatTrien) {
        this.activity = activity;
        this.function = function;
        this.MaCQLT = MaCQLT;
        this.TenCQLT = TenCQLT;
        this.DiaChi = DiaChi;
        this.SDT = SDT;
        this.Email = Email;
        this.Website = Website;
        this.LichSuPhatTrien = LichSuPhatTrien;
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
    protected void onPostExecute(CQLT_ett cqlt_ett) {
        super.onPostExecute(cqlt_ett);
        progressDialog.dismiss();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected CQLT_ett doInBackground(Void... params) {
        try {
            String METHOD_NAME = "Insert_CQLT";
            if (function == Constants.FUNCTION_UPDATE)
                METHOD_NAME = "Update_CQLT";
            String SOAP_ACTION = Constants.NAMESPACE + METHOD_NAME;
            SoapObject request = new SoapObject(Constants.NAMESPACE, METHOD_NAME);
            request.addProperty("ma", this.MaCQLT);
            request.addProperty("ten", this.TenCQLT);
            request.addProperty("diachi", this.DiaChi);
            request.addProperty("sdt", this.SDT);
            request.addProperty("email", this.Email);
            request.addProperty("website", this.Website);
            request.addProperty("lichsupt", this.LichSuPhatTrien);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            MarshalFloat marshalFloat = new MarshalFloat();
            marshalFloat.register(envelope);
            HttpTransportSE httpTransportSE = new HttpTransportSE(Constants.URL_CQLT);
            httpTransportSE.call(SOAP_ACTION, envelope);

            SoapObject soapData = (SoapObject) envelope.getResponse();

            String ErrCode = soapData.getProperty("ErrCode").toString();
            String ErrDesc = soapData.getProperty("ErrDesc").toString();
            int total_record = Integer.parseInt(soapData.getProperty("Total_record").toString());
            cqlt_ett = new CQLT_ett(total_record, ErrCode, ErrDesc);
//            Log.e("ErrDesc", ErrDesc);
            return cqlt_ett;
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
