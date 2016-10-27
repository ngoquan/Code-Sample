package ngovanquan_803656.datn.asynctask.qldm;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.ListView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.MarshalFloat;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

import ngovanquan_803656.datn.R;
import ngovanquan_803656.datn.adapter.CoQuanLuuTruAdapter;
import ngovanquan_803656.datn.model.CQLT_ett;
import ngovanquan_803656.datn.utils.Constants;
import ngovanquan_803656.datn.utils.Helper;

/**
 * Created by ngoquan on 5/11/2016.
 */
public class SearchCQLTAsyncTask extends AsyncTask<Void, Void, ArrayList<CQLT_ett>> {

    private Activity activity;
    private String search_type, search_val;
    private int pageCurrent, num_row_per_page;
    private ProgressDialog progressDialog;
//    private ListView lv_CQLT;
    private ArrayList<CQLT_ett> arrData = new ArrayList<>();
//    private CoQuanLuuTruAdapter adapter;

    public SearchCQLTAsyncTask(Activity activity, String search_type, String search_val, int pageCurrent, int num_row_per_page) {
        this.activity = activity;
        this.search_type = search_type;
        this.search_val = search_val;
        this.pageCurrent = pageCurrent;
        this.num_row_per_page = num_row_per_page;
        progressDialog = new ProgressDialog(this.activity);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Please wait...!");
        progressDialog.setCancelable(false);

//        lv_CQLT = (ListView) activity.findViewById(R.id.lv_cqlt);
//        adapter = new CoQuanLuuTruAdapter(this.activity, R.layout.list_item_cqlt, arrData);
//        lv_CQLT.setAdapter(adapter);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(ArrayList<CQLT_ett> integer) {
        super.onPostExecute(integer);
        progressDialog.dismiss();
//        Helper.setListViewHeightBasedOnChildren(lv_CQLT);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
//        arrData.add(values[0]);
//        adapter.notifyDataSetChanged();
    }

    @Override
    protected ArrayList<CQLT_ett> doInBackground(Void... params) {
        try {
            final String METHOD_NAME = "SearchCQLT";
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
            HttpTransportSE httpTransportSE = new HttpTransportSE(Constants.URL_CQLT);
            httpTransportSE.call(SOAP_ACTION, envelope);

            SoapObject soapData = (SoapObject) envelope.getResponse();
            int total_record = Integer.parseInt(soapData.getProperty("Total_record").toString());
            if (soapData.hasProperty("Data")) {
                SoapObject soapArr = (SoapObject) soapData.getProperty("Data");
                for (int i = 0; i < soapArr.getPropertyCount(); i++) {
                    SoapObject soapItem = (SoapObject) soapArr.getProperty(i);
                    String MaCQLT = soapItem.getProperty("MaCQLT").toString();
                    String TenCQLT = soapItem.getProperty("TenCQLT").toString();
                    String DiaChi = "";
                    if (soapItem.hasProperty("DiaChi"))
                        DiaChi = soapItem.getProperty("DiaChi").toString();
                    if (DiaChi.equals("anyType{}"))
                        DiaChi = "";
                    String SDT = "";
                    if (soapItem.hasProperty("SDT"))
                        SDT = soapItem.getProperty("SDT").toString();
                    if (SDT.equals("anyType{}"))
                        SDT = "";
                    String Email = "";
                    if (soapItem.hasProperty("Email"))
                        Email = soapItem.getProperty("Email").toString();
                    if (Email.equals("anyType{}"))
                        Email = "";
                    String Website = "";
                    if (soapItem.hasProperty("Website"))
                        Website = soapItem.getProperty("Website").toString();
                    if (Website.equals("anyType{}"))
                        Website = "";
                    String LichSuPhatTrien = "";
                    if (soapItem.hasProperty("LichSuPhatTrien"))
                        LichSuPhatTrien = soapItem.getProperty("LichSuPhatTrien").toString();
                    if (LichSuPhatTrien.equals("anyType{}"))
                        LichSuPhatTrien = "";

                    CQLT_ett cqlt_ett = new CQLT_ett(MaCQLT, TenCQLT, DiaChi, SDT, Email, Website, LichSuPhatTrien, total_record);
//                    publishProgress(cqlt_ett);
                    arrData.add(cqlt_ett);
                }
            }
            return arrData;
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
