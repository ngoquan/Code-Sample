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

import ngovanquan_803656.datn.model.NhomNguoiDung_ett;
import ngovanquan_803656.datn.utils.Constants;

/**
 * Created by NGOQUAN on 5/26/2016.
 */
public class AddNewAndUpdateNhomNguoiDungAsyncTask extends AsyncTask<Void, Void, NhomNguoiDung_ett> {

    private Activity activity;
    private int function, MaNhom;
    private String TenNhom, GhiChu;
    private boolean Active;
    private ProgressDialog progressDialog;

    public AddNewAndUpdateNhomNguoiDungAsyncTask(Activity activity, int function, int MaNhom, String TenNhom, String GhiChu, boolean Active) {
        this.activity = activity;
        this.function = function;
        this.MaNhom = MaNhom;
        this.TenNhom = TenNhom;
        this.GhiChu = GhiChu;
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
    protected void onPostExecute(NhomNguoiDung_ett nhomNguoiDung_ett) {
        super.onPostExecute(nhomNguoiDung_ett);
        progressDialog.dismiss();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected NhomNguoiDung_ett doInBackground(Void... params) {
        try {
            String METHOD_NAME = "InsertNhomNguoiDung";
            if (function == Constants.FUNCTION_UPDATE)
                METHOD_NAME = "UpdateNhomNguoiDung";
            else if (function == Constants.FUNCTION_DELETE)
                METHOD_NAME = "DeleteNhomNguoiDung";
            final String SOAP_ACTION = Constants.NAMESPACE + METHOD_NAME;
            SoapObject request = new SoapObject(Constants.NAMESPACE, METHOD_NAME);
            if ((function == Constants.FUNCTION_UPDATE) || (function == Constants.FUNCTION_DELETE))
                request.addProperty("ma", this.MaNhom);
            if ((function == Constants.FUNCTION_ADD_NEW) || (function == Constants.FUNCTION_UPDATE)) {
                request.addProperty("ten", this.TenNhom);
                request.addProperty("ghichu", this.GhiChu);
                if (this.Active == true)
                    request.addProperty("active", 1);
                else
                    request.addProperty("active", 0);
            }
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            MarshalFloat marshalFloat = new MarshalFloat();
            marshalFloat.register(envelope);
            HttpTransportSE httpTransportSE = new HttpTransportSE(Constants.URL_NND);
            httpTransportSE.call(SOAP_ACTION, envelope);
            SoapObject soapData = (SoapObject) envelope.getResponse();
            int total_record = Integer.parseInt(soapData.getProperty("Total_record").toString());
            String ErrCode = soapData.getProperty("ErrCode").toString();
            String ErrDesc = soapData.getProperty("ErrDesc").toString();
            NhomNguoiDung_ett nhomNguoiDung_ett = new NhomNguoiDung_ett(total_record, ErrCode, ErrDesc);
            return nhomNguoiDung_ett;
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
