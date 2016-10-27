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
import ngovanquan_803656.datn.adapter.DoMatAdapter;
import ngovanquan_803656.datn.model.DoMat_ett;
import ngovanquan_803656.datn.utils.Constants;
import ngovanquan_803656.datn.utils.Helper;

/**
 * Created by ngoquan on 4/13/2016.
 */
public class SearchDoMatAsyncTask extends AsyncTask<Void, Void, ArrayList<DoMat_ett>> {
    private Activity activity;
    private String search_type, search_val;
    private int pageCurrent, num_row_per_page;
    private ProgressDialog progressDialog;
//    private ListView lv_DM;
    private ArrayList<DoMat_ett> arrData = new ArrayList<>();
//    private DoMatAdapter adapter;

    public SearchDoMatAsyncTask(Activity activity, String search_type, String search_val, int pageCurrent, int num_row_per_page) {
        this.activity = activity;
        this.search_type = search_type;
        this.search_val = search_val;
        this.pageCurrent = pageCurrent;
        this.num_row_per_page = num_row_per_page;
        progressDialog = new ProgressDialog(this.activity);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Please wait...!");
        progressDialog.setCancelable(false);

//        lv_DM = (ListView) this.activity.findViewById(R.id.lv_DM);
//        adapter = new DoMatAdapter(this.activity, R.layout.list_item_dtc, arrData);
//        lv_DM.setAdapter(adapter);
//        Helper.setListViewHeightBasedOnChildren(lv_DM);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.show();
    }

    @Override
    protected ArrayList<DoMat_ett> doInBackground(Void... params) {
        try {
//            final String NAMESPACE = "http://tempuri.org/";
            final String METHOD_NAME = "SearchDoMat";
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
            HttpTransportSE httpTransportSE = new HttpTransportSE(Constants.URL_DM);
            httpTransportSE.call(SOAP_ACTION, envelope);

            SoapObject soapData = (SoapObject) envelope.getResponse();
            int total_record = Integer.parseInt(soapData.getProperty("Total_record").toString());
            if (soapData.hasProperty("Data")) {
                SoapObject soapArr = (SoapObject) soapData.getProperty("Data");
                for (int i = 0; i < soapArr.getPropertyCount(); i++) {
                    SoapObject soapItem = (SoapObject) soapArr.getProperty(i);
                    long MaDoMat = Long.parseLong(soapItem.getProperty("MaDoMat").toString());
                    String TenDoMat = soapItem.getProperty("TenDoMat").toString();
                    String GhiChu = soapItem.getProperty("GhiChu").toString();
                    int STT = Integer.parseInt(soapItem.getProperty("STT").toString());
                    boolean Active = Boolean.parseBoolean(soapItem.getProperty("Active").toString());
                    DoMat_ett doMat_ett = new DoMat_ett(MaDoMat, TenDoMat, GhiChu, STT, Active, total_record);
//                    publishProgress(doMat_ett);
                    arrData.add(doMat_ett);
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

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
//        arrData.add(values[0]);
//        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onPostExecute(ArrayList<DoMat_ett> doMat_etts) {
        super.onPostExecute(doMat_etts);
        progressDialog.dismiss();
//        Helper.setListViewHeightBasedOnChildren(lv_DM);
    }
}
