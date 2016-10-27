package ngovanquan_803656.datn.asynctask.qllt;

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

import ngovanquan_803656.datn.utils.Constants;

/**
 * Created by ngoquan on 5/14/2016.
 */
public class GetNumberOfObject extends AsyncTask<Void, Void, Integer> {
    private Activity activity;
    private String id;
    private int function;
    private ProgressDialog progressDialog;
    public GetNumberOfObject(Activity activity, int function, String id) {
        this.activity = activity;
        this.function = function;
        this.id = id;
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
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        progressDialog.dismiss();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected Integer doInBackground(Void... params) {
        try {
            String METHOD_NAME = "";
            switch (function) {
                case Constants.F_GET_NB_HOPHS_PHONG:
                    METHOD_NAME = "GetNumberOfHopHoSoInPhong";
                    break;
                case Constants.F_GET_NB_HOSO_HOPHS:
                    METHOD_NAME = "GetNumberOfHoSoInHopHoSo";
                    break;
                case Constants.F_GET_NB_HOSO_PHONG:
                    METHOD_NAME = "GetNumberOfHoSoInPhong";
                    break;
                case Constants.F_GET_NB_VB_HOSO:
                    METHOD_NAME = "GetNumberOfVanBanInHoSo";
                    break;
                case Constants.F_GET_NB_VB_PHONG:
                    METHOD_NAME = "GetNumberOfVanBanInPhong";
                    break;
                case Constants.F_GET_NB_VB_FILE_ATTACH_PHONG:
                    METHOD_NAME = "GetNumberOfVanBanFileAttachInPhong";
                    break;
            }
            final String SOAP_ACTION = Constants.NAMESPACE + METHOD_NAME;
            SoapObject request = new SoapObject(Constants.NAMESPACE, METHOD_NAME);
            switch (function) {
                case Constants.F_GET_NB_HOSO_HOPHS:
                    request.addProperty("hophoso_id", this.id);
                    break;
                case Constants.F_GET_NB_HOPHS_PHONG:
                case Constants.F_GET_NB_HOSO_PHONG:
                case Constants.F_GET_NB_VB_PHONG:
                case Constants.F_GET_NB_VB_FILE_ATTACH_PHONG:
                    request.addProperty("phong_id", this.id);
                    break;
                case Constants.F_GET_NB_VB_HOSO:
                    request.addProperty("hososo", this.id);
                    break;
            }
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            MarshalFloat marshalFloat = new MarshalFloat();
            marshalFloat.register(envelope);
            HttpTransportSE httpTransportSE = new HttpTransportSE(Constants.URL_MIXED);
            httpTransportSE.call(SOAP_ACTION, envelope);
            SoapObject soapData = (SoapObject) envelope.getResponse();
//            String ErrCode = "";
//            if (soapData.hasProperty("ErrCode"))
//                ErrCode = soapData.getProperty("ErrCode").toString();
//            String ErrDesc = "";
//            if (soapData.hasProperty("ErrDesc"))
//                ErrDesc = soapData.getProperty("ErrDesc").toString();
//            int total_record = 0;
//            if (soapData.hasAttribute("Total_record"))
//            int total_record = Integer.parseInt(soapData.getProperty("Total_record").toString());
//            int Data = 0;
//            if (soapData.hasAttribute("Data"))
            int Data = Integer.parseInt(soapData.getProperty("Data").toString());
//            Log.e("ResultData", ErrCode + " - " + ErrDesc + " - " + total_record + " - " + Data);
            return Data;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
