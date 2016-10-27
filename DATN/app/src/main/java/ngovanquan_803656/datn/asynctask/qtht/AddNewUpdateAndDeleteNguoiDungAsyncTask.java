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

import ngovanquan_803656.datn.model.QLND_ett;
import ngovanquan_803656.datn.utils.Constants;

/**
 * Created by NGOQUAN on 5/30/2016.
 */
public class AddNewUpdateAndDeleteNguoiDungAsyncTask extends AsyncTask<Void, Void, QLND_ett> {

    private Activity activity;
    private ProgressDialog progressDialog;
    private String LoginID, HoTen, MatKhau, GhiChu;
    private int MaNhom, function;
    private boolean Active;

    public AddNewUpdateAndDeleteNguoiDungAsyncTask(Activity activity, int function, String LoginID, String HoTen, String MatKhau, String GhiChu,
                                                   int MaNhom, boolean Active) {
        this.activity = activity;
        this.function = function;
        this.LoginID = LoginID;
        this.HoTen = HoTen;
        this.MatKhau = MatKhau;
        this.GhiChu = GhiChu;
        this.MaNhom = MaNhom;
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
    protected void onPostExecute(QLND_ett qlnd_ett) {
        super.onPostExecute(qlnd_ett);
        progressDialog.dismiss();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected QLND_ett doInBackground(Void... params) {
        try {
            String METHOD_NAME = "InsertNguoiDung";
            if (function == Constants.FUNCTION_UPDATE)
                METHOD_NAME = "UpdateNguoiDung";
            else if (function == Constants.FUNCTION_DELETE)
                METHOD_NAME = "DeleteNguoiDung";

            final String SOAP_ACTION = Constants.NAMESPACE + METHOD_NAME;
            SoapObject request = new SoapObject(Constants.NAMESPACE, METHOD_NAME);
            request.addProperty("loginID", this.LoginID);
            if ((function == Constants.FUNCTION_ADD_NEW) || (function == Constants.FUNCTION_UPDATE)) {
                request.addProperty("ten", this.HoTen);
                request.addProperty("manhom", this.MaNhom);
                request.addProperty("matkhau", this.MatKhau);
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
            HttpTransportSE httpTransportSE = new HttpTransportSE(Constants.URL_ND);
            httpTransportSE.call(SOAP_ACTION, envelope);
            SoapObject soapData = (SoapObject) envelope.getResponse();
            String ErrCode = soapData.getProperty("ErrCode").toString();
            String ErrDesc = soapData.getProperty("ErrDesc").toString();
            int total_record = Integer.parseInt(soapData.getProperty("Total_record").toString());
            QLND_ett qlnd_ett = new QLND_ett(total_record, ErrCode, ErrDesc);
            return qlnd_ett;
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
