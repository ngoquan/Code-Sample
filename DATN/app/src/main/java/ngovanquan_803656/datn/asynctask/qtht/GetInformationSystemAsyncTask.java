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

import ngovanquan_803656.datn.utils.Constants;

/**
 * Created by NGOQUAN on 5/26/2016.
 */
public class GetInformationSystemAsyncTask extends AsyncTask<Void, Void, String> {

    private Activity activity;
    private int function;
    private ProgressDialog progressDialog;

    public GetInformationSystemAsyncTask(Activity activity, int function) {
        this.activity = activity;
        this.function = function;
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
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressDialog.dismiss();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            String METHOD_NAME = "";
            switch (function){
                case Constants.FUNCTION_GET_DIA_CHI:
                    METHOD_NAME = "GetDiaChi";
                    break;
                case Constants.FUNCTION_GET_EMAIL:
                    METHOD_NAME = "GetEmail";
                    break;
                case Constants.FUNCTION_GET_GIOI_THIEU:
                    METHOD_NAME = "GetGioiThieu";
                    break;
                case Constants.FUNCTION_GET_PASSWORD:
                    METHOD_NAME = "GetPassword";
                    break;
                case Constants.FUNCTION_GET_SDT:
                    METHOD_NAME = "GetSDT";
                    break;
                case Constants.FUNCTION_GET_TEN_CCVT:
                    METHOD_NAME = "GetTenChiCucVatTu";
                    break;
                case Constants.FUNCTION_GET_TEN_PHAN_MEM:
                    METHOD_NAME = "GetTenPhanMem";
                    break;
                case Constants.FUNCTION_GET_TEN_SNV:
                    METHOD_NAME = "GetTenSoNoiVu";
                    break;
                case Constants.FUNCTION_GET_WEBSITE:
                    METHOD_NAME = "GetWebsite";
                    break;
            }
            final String SOAP_ACTION = Constants.NAMESPACE + METHOD_NAME;
            SoapObject request = new SoapObject(Constants.NAMESPACE, METHOD_NAME);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            MarshalFloat marshalFloat = new MarshalFloat();
            marshalFloat.register(envelope);
            HttpTransportSE httpTransportSE = new HttpTransportSE(Constants.URL_CHHT);
            httpTransportSE.call(SOAP_ACTION, envelope);
            SoapObject soapData = (SoapObject) envelope.getResponse();
            String Data = "";
            if (soapData.hasProperty("Data"))
                Data = soapData.getProperty("Data").toString();
            if (Data.equals("anyType{}"))
                Data = "";
            return Data;
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
