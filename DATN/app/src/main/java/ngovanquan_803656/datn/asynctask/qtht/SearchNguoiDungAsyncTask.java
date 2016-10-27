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
import java.util.ArrayList;

import ngovanquan_803656.datn.model.NhomNguoiDung_ett;
import ngovanquan_803656.datn.model.QLND_ett;
import ngovanquan_803656.datn.utils.Constants;

/**
 * Created by NGOQUAN on 5/27/2016.
 */
public class SearchNguoiDungAsyncTask extends AsyncTask<Void, Void, ArrayList<QLND_ett>> {

    private Activity activity;
    private String search_type, search_val;
    private int page_current;
    private ProgressDialog progressDialog;
    private ArrayList<QLND_ett> arrND = new ArrayList<>();

    public SearchNguoiDungAsyncTask(Activity activity, String search_type, String search_val, int page_current) {
        this.activity = activity;
        this.search_type = search_type;
        this.search_val = search_val;
        this.page_current = page_current;
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
    protected void onPostExecute(ArrayList<QLND_ett> qlnd_etts) {
        super.onPostExecute(qlnd_etts);
        progressDialog.dismiss();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected ArrayList<QLND_ett> doInBackground(Void... params) {
        try {
            final String METHOD_NAME = "SearchNguoiDung";
            final String SOAP_ACTION = Constants.NAMESPACE + METHOD_NAME;
            SoapObject request = new SoapObject(Constants.NAMESPACE, METHOD_NAME);
            request.addProperty("search_type", this.search_type);
            request.addProperty("search_val", this.search_val);
            request.addProperty("page_curr", this.page_current);
            request.addProperty("num_row_per_page", Constants.NUM_ROW_PER_PAGE);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            MarshalFloat marshalFloat = new MarshalFloat();
            marshalFloat.register(envelope);
            HttpTransportSE httpTransportSE = new HttpTransportSE(Constants.URL_ND);
            httpTransportSE.call(SOAP_ACTION, envelope);
            SoapObject soapData = (SoapObject) envelope.getResponse();
            int total_record = Integer.parseInt(soapData.getProperty("Total_record").toString());
            if (soapData.hasProperty("Data")) {
                SoapObject soapArr = (SoapObject) soapData.getProperty("Data");
                for (int i = 0; i < soapArr.getPropertyCount(); i++) {
                    SoapObject soapItem = (SoapObject) soapArr.getProperty(i);
                    String LoginID = soapItem.getProperty("LoginID").toString();
                    String HoTen = "";
                    if (soapItem.hasProperty("HoTen"))
                        HoTen = soapItem.getProperty("HoTen").toString();
                    if (HoTen.equals("anyType{}"))
                        HoTen = "";
                    int MaNhom = Integer.parseInt(soapItem.getProperty("MaNhom").toString());
                    String MatKhau = soapItem.getProperty("MatKhau").toString();
                    String GhiChu = "";
                    if (soapItem.hasProperty("GhiChu"))
                        GhiChu = soapItem.getProperty("GhiChu").toString();
                    if (GhiChu.equals("anyType{}"))
                        GhiChu = "";
                    boolean Active = Boolean.parseBoolean(soapItem.getProperty("Active").toString());
                    QLND_ett qlnd_ett = new QLND_ett(LoginID, HoTen, MaNhom, MatKhau, GhiChu, Active, total_record);
                    arrND.add(qlnd_ett);
                }
            }
            return arrND;
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
