package ngovanquan_803656.datn.asynctask.qldm;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
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
import ngovanquan_803656.datn.adapter.LoaiHinhTaiLieuAdapter;
import ngovanquan_803656.datn.model.LHTL_ett;
import ngovanquan_803656.datn.utils.Constants;
import ngovanquan_803656.datn.utils.Helper;

/**
 * Created by ngoquan on 4/4/2016.
 */
public class GetAllDataAysnTask extends AsyncTask<Void, LHTL_ett, Integer> {

    private Activity activity;
    private String search_type, search_val;
    private int pageCurrent;
    private ProgressDialog progressDialog;
    ListView lv_LHTL;
    ArrayList<LHTL_ett> arrData = new ArrayList<>();
    LoaiHinhTaiLieuAdapter adapter;
    public GetAllDataAysnTask(Activity activity, String search_type, String search_val, int pageCurrent) {
        this.activity = activity;
        this.search_type = search_type;
        this.search_val = search_val;
        this.pageCurrent = pageCurrent;
        progressDialog = new ProgressDialog(this.activity);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Please wait...!");
        progressDialog.setCancelable(false);


        lv_LHTL = (ListView) activity.findViewById(R.id.lv_LHTL);
        adapter = new LoaiHinhTaiLieuAdapter(this.activity, R.layout.list_item_lhtl, arrData);
        lv_LHTL.setAdapter(adapter);


    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(Integer lhtl_etts) {
        super.onPostExecute(lhtl_etts);
        progressDialog.dismiss();
        Helper.setListViewHeightBasedOnChildren(lv_LHTL);
    }

    @Override
    protected void onProgressUpdate(LHTL_ett... values) {
        super.onProgressUpdate(values);
        arrData.add(values[0]);
        adapter.notifyDataSetChanged();

    }

    @Override
    protected Integer doInBackground(Void... params) {
        try {
//            final String NAMESPACE = "http://tempuri.org/";
            final String METHOD_NAME = "Search_LHTL";
            final String SOAP_ACTION = Constants.NAMESPACE + METHOD_NAME;
            SoapObject request = new SoapObject(Constants.NAMESPACE, METHOD_NAME);
            request.addProperty("search_type", this.search_type);
            request.addProperty("search_val", this.search_val);
            request.addProperty("page_curr", this.pageCurrent);
            request.addProperty("num_row_per_page", Constants.NUM_ROW_PER_PAGE);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            MarshalFloat marshalFloat = new MarshalFloat();
            marshalFloat.register(envelope);
            HttpTransportSE httpTransportSE = new HttpTransportSE(Constants.URL_LHTL);
            httpTransportSE.call(SOAP_ACTION, envelope);

            SoapObject soapData = (SoapObject) envelope.getResponse();
            int total_record = Integer.parseInt(soapData.getProperty("Total_record").toString());
            if (soapData.hasProperty("Data")) {
                SoapObject soapArr = (SoapObject) soapData.getProperty("Data");
                for (int i = 0; i < soapArr.getPropertyCount(); i++) {
                    SoapObject soapItem = (SoapObject) soapArr.getProperty(i);
                    long MaLoaiHinhTL = Long.parseLong(soapItem.getProperty("MaLoaiHinhTL").toString());
                    String TenLoaiHinhTL = soapItem.getProperty("TenLoaiHinhTL").toString();
                    long STT = Long.parseLong(soapItem.getProperty("STT").toString());
                    boolean Active = Boolean.parseBoolean(soapItem.getProperty("Active").toString());
                    String GhiChu = soapItem.getProperty("GhiChu").toString();
                    LHTL_ett lhtlEtt = new LHTL_ett(MaLoaiHinhTL, TenLoaiHinhTL, STT, Active, GhiChu, total_record);
                    Log.e("Result", lhtlEtt.toString());
                    publishProgress(lhtlEtt);
                }
            }
            return total_record;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
