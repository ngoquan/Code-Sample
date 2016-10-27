package ngovanquan_803656.datn.asynctask.qldm;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
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
import ngovanquan_803656.datn.adapter.LoaiVanBanAdapter;
import ngovanquan_803656.datn.model.LVB_ett;
import ngovanquan_803656.datn.utils.Constants;
import ngovanquan_803656.datn.utils.Helper;

/**
 * Created by ngoquan on 4/14/2016.
 */
public class SearchLVBAsyncTask extends AsyncTask<Void, Void, ArrayList<LVB_ett>> {
    private Activity activity;
    private String search_type, search_val;
    private int pageCurrnet, num_row_per_page;
    private ProgressDialog progressDialog;
//    private ListView lv_LVB;
    private ArrayList<LVB_ett> arrData = new ArrayList<>();
//    private LoaiVanBanAdapter adapter;

    public SearchLVBAsyncTask(Activity activity, String search_type, String search_val, int pageCurrnet, int num_row_per_page) {
        this.activity = activity;
        this.search_type = search_type;
        this.search_val = search_val;
        this.pageCurrnet = pageCurrnet;
        this.num_row_per_page = num_row_per_page;
        progressDialog = new ProgressDialog(this.activity);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Please wait...!");
        progressDialog.setCancelable(false);

//        lv_LVB = (ListView) this.activity.findViewById(R.id.lv_LVB);
//        adapter = new LoaiVanBanAdapter(this.activity, R.layout.list_item_dtc, arrData);
//        lv_LVB.setAdapter(adapter);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(ArrayList<LVB_ett> lvb_etts) {
        super.onPostExecute(lvb_etts);
        progressDialog.dismiss();
//        Helper.setListViewHeightBasedOnChildren(lv_LVB);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
//        arrData.add(values[0]);
//        adapter.notifyDataSetChanged();
    }

    @Override
    protected ArrayList<LVB_ett> doInBackground(Void... params) {
        try {
//            final String NAMESPACE = "http://tempuri.org/";
            final String METHOD_NAME = "SearchLVB";
            final String SOAP_ACTION = Constants.NAMESPACE + METHOD_NAME;
            SoapObject request = new SoapObject(Constants.NAMESPACE, METHOD_NAME);
            request.addProperty("search_type", this.search_type);
            request.addProperty("search_val", this.search_val);
            request.addProperty("page_curr", this.pageCurrnet);
            request.addProperty("num_row_per_page", this.num_row_per_page);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            MarshalFloat marshalFloat = new MarshalFloat();
            marshalFloat.register(envelope);
            HttpTransportSE httpTransportSE = new HttpTransportSE(Constants.URL_LVB);
            httpTransportSE.call(SOAP_ACTION, envelope);

            SoapObject soapData = (SoapObject) envelope.getResponse();
            int total_record = Integer.parseInt(soapData.getProperty("Total_record").toString());
//            Log.e("Result", soapData.toString());
            if (soapData.hasProperty("Data")) {
                SoapObject soapArr = (SoapObject) soapData.getProperty("Data");
                for (int i = 0; i < soapArr.getPropertyCount(); i++) {
                    SoapObject soapItem = (SoapObject) soapArr.getProperty(i);
                    long MaLVB = Long.parseLong(soapItem.getProperty("MaLVB").toString());
                    String TenLVB = soapItem.getProperty("TenLVB").toString();
                    String GhiChu = "";
                    if (soapItem.hasProperty("GhiChu"))
                        GhiChu = soapItem.getProperty("GhiChu").toString();
                    if (GhiChu.equals("anyType{}"))
                        GhiChu = "";
                    int STT = Integer.parseInt(soapItem.getProperty("STT").toString());
                    boolean Active = Boolean.parseBoolean(soapItem.getProperty("Active").toString());
                    LVB_ett lvb_ett = new LVB_ett(MaLVB, TenLVB, GhiChu, STT, Active, total_record);
//                    publishProgress(lvb_ett);
                    arrData.add(lvb_ett);
                }
            }
//            Log.e("Result size", "OKOKOKO");
            return arrData;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
