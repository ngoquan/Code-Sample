package ngovanquan_803656.datn.asynctask.qldm;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.ListView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalFloat;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

import ngovanquan_803656.datn.R;
import ngovanquan_803656.datn.adapter.TinhTrangVatLyAdapter;
import ngovanquan_803656.datn.model.TTVL_ett;
import ngovanquan_803656.datn.utils.Constants;
import ngovanquan_803656.datn.utils.Helper;

/**
 * Created by ngoquan on 4/14/2016.
 */
public class SearchTTVLAsyncTask extends AsyncTask<Void, Void, ArrayList<TTVL_ett>> {
    private Activity activity;
    private String search_type, search_val;
    private int pageCurrent, num_row_per_page;
    private ProgressDialog progressDialog;
//    private ListView lv_TTVL;
    private ArrayList<TTVL_ett> arrData = new ArrayList<>();
//    private TinhTrangVatLyAdapter adapter;

    public SearchTTVLAsyncTask(Activity activity, String search_type, String search_val, int pageCurrent, int num_row_per_page) {
        this.activity = activity;
        this.search_type = search_type;
        this.search_val = search_val;
        this.pageCurrent = pageCurrent;
        this.num_row_per_page = num_row_per_page;
        progressDialog = new ProgressDialog(this.activity);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Please wait...!");
        progressDialog.setCancelable(false);

//        lv_TTVL = (ListView) this.activity.findViewById(R.id.lv_TTVL);
//        adapter = new TinhTrangVatLyAdapter(this.activity, R.layout.list_item_dtc, arrData);
//        lv_TTVL.setAdapter(adapter);
    }

    @Override
    protected void onPostExecute(ArrayList<TTVL_ett> ttvl_etts) {
        super.onPostExecute(ttvl_etts);
        progressDialog.dismiss();
//        Helper.setListViewHeightBasedOnChildren(lv_TTVL);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.show();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
//        arrData.add(values[0]);
//        adapter.notifyDataSetChanged();
    }

    @Override
    protected ArrayList<TTVL_ett> doInBackground(Void... params) {
        try {
//            final String NAMESPACE = "http://tempuri.org/";
            final String METHOD_NAME = "SearchTTVL";
            final String SOAP_ACTION = Constants.NAMESPACE + METHOD_NAME;
            SoapObject request = new SoapObject(Constants.NAMESPACE, METHOD_NAME);
            request.addProperty("search_type", this.search_type);
            request.addProperty("search_val", this.search_val);
            request.addProperty("page_curr", this.pageCurrent);
            request.addProperty("num_row_per_page", this.num_row_per_page);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            MarshalFloat marshalFloat = new MarshalFloat();
            marshalFloat.register(envelope);
            HttpTransportSE httpTransportSE = new HttpTransportSE(Constants.URL_TTVL);
            httpTransportSE.call(SOAP_ACTION, envelope);

            SoapObject soapData = (SoapObject) envelope.getResponse();
            int total_record = Integer.parseInt(soapData.getProperty("Total_record").toString());
            if (soapData.hasProperty("Data")) {
                SoapObject soapArr = (SoapObject) soapData.getProperty("Data");
                for (int i = 0; i < soapArr.getPropertyCount(); i++) {
                    SoapObject soapItem = (SoapObject) soapArr.getProperty(i);
                    long MaTTVL = Long.parseLong(soapItem.getProperty("MaTTVL").toString());
                    String TenTTVL = soapItem.getProperty("TenTTVL").toString();
                    String GhiChu = soapItem.getProperty("GhiChu").toString();
                    int STT = Integer.parseInt(soapItem.getProperty("STT").toString());
                    boolean Active = Boolean.parseBoolean(soapItem.getProperty("Active").toString());
                    TTVL_ett ttvl_ett = new TTVL_ett(MaTTVL, TenTTVL, GhiChu, STT, Active, total_record);
//                    publishProgress(ttvl_ett);
                    arrData.add(ttvl_ett);
                }
            }
            return arrData;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
