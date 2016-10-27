package ngovanquan_803656.datn.asynctask.qtht;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.MarshalFloat;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

import ngovanquan_803656.datn.model.NhomNguoiDung_ett;
import ngovanquan_803656.datn.utils.Constants;

/**
 * Created by NGOQUAN on 5/26/2016.
 */
public class SearchNhomNguoiDungAsyncTask extends AsyncTask<Void, Void, ArrayList<NhomNguoiDung_ett>> {

    private Activity activity;
    private String search_type, search_value;
    private int page_current, num_row_per_page;
    private ProgressDialog progressDialog;
    private ArrayList<NhomNguoiDung_ett> arrNND = new ArrayList<>();

    public SearchNhomNguoiDungAsyncTask(Activity activity, String search_type, String search_value, int page_current, int num_row_per_page) {
        this.activity = activity;
        this.search_type = search_type;
        this.search_value = search_value;
        this.page_current = page_current;
        this.num_row_per_page = num_row_per_page;
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
    protected void onPostExecute(ArrayList<NhomNguoiDung_ett> nhomNguoiDung_etts) {
        super.onPostExecute(nhomNguoiDung_etts);
        progressDialog.dismiss();
    }

    @Override
    protected ArrayList<NhomNguoiDung_ett> doInBackground(Void... params) {
        try {
            final String METHOD_NAME = "SearchNhomNguoiDung";
            final String SOAP_ACTION = Constants.NAMESPACE + METHOD_NAME;
            SoapObject request = new SoapObject(Constants.NAMESPACE, METHOD_NAME);
            request.addProperty("search_type", this.search_type);
            request.addProperty("search_val", this.search_value);
            request.addProperty("page_curr", this.page_current);
            request.addProperty("num_row_per_page", this.num_row_per_page);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            MarshalFloat marshalFloat = new MarshalFloat();
            marshalFloat.register(envelope);
            HttpTransportSE httpTransportSE = new HttpTransportSE(Constants.URL_NND);
            httpTransportSE.call(SOAP_ACTION, envelope);

            SoapObject soapData = (SoapObject) envelope.getResponse();
            int total_record = Integer.parseInt(soapData.getProperty("Total_record").toString());
            if (soapData.hasProperty("Data")) {
                SoapObject soapArr = (SoapObject) soapData.getProperty("Data");
                for (int i = 0; i < soapArr.getPropertyCount(); i++) {
                    SoapObject soapItem = (SoapObject) soapArr.getProperty(i);
                    int MaNhom = Integer.parseInt(soapItem.getProperty("MaNhom").toString());
                    String TenNhom = "";
                    if (soapItem.hasProperty("TenNhom"))
                        TenNhom = soapItem.getProperty("TenNhom").toString();
                    if (TenNhom.equals("anyType{}"))
                        TenNhom = "";
                    String GhiChu = "";
                    if (soapItem.hasProperty("GhiChu"))
                        GhiChu = soapItem.getProperty("GhiChu").toString();
                    if (GhiChu.equals("anyType{}"))
                        GhiChu = "";
                    boolean Active = Boolean.parseBoolean(soapItem.getProperty("Active").toString());
                    NhomNguoiDung_ett nhomNguoiDung_ett = new NhomNguoiDung_ett(MaNhom, TenNhom, GhiChu, Active, total_record);
                    arrNND.add(nhomNguoiDung_ett);
//                    Log.e("ResultSearch", MaNhom + " - " + TenNhom);
                }
            }
            return arrNND;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
