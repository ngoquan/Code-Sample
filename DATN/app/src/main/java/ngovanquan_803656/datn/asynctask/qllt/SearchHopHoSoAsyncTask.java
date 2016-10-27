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
import java.util.ArrayList;

import ngovanquan_803656.datn.model.HopHoSo_ett;
import ngovanquan_803656.datn.utils.Constants;

/**
 * Created by ngoquan on 5/10/2016.
 */
public class SearchHopHoSoAsyncTask extends AsyncTask<Void, Void, ArrayList<HopHoSo_ett>>{

    private Activity activity;
    private String search_type, search_val;
    private int pageCurrent, num_row_per_page;
    private String name, phong_id;
    private ProgressDialog progressDialog;
    private int function;
    private ArrayList<HopHoSo_ett> arrData = new ArrayList<>();

    public SearchHopHoSoAsyncTask(Activity activity, int function, String name, String phong_id, String search_type, String search_val,
                                  int pageCurrent, int num_row_per_page) {
        this.activity = activity;
        this.function = function;
        this.name = name;
        this.phong_id = phong_id;
        this.search_type = search_type;
        this.search_val = search_val;
        this.pageCurrent = pageCurrent;
        this.num_row_per_page = num_row_per_page;
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
    protected void onPostExecute(ArrayList<HopHoSo_ett> hopHoSo_etts) {
        super.onPostExecute(hopHoSo_etts);
        progressDialog.dismiss();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected ArrayList<HopHoSo_ett> doInBackground(Void... params) {
        try {
            String METHOD_NAME = "SearchHopHoSo";
            if (function == Constants.FUNCTION_SEARCH) {
                METHOD_NAME = "SearchHopHoSo";
            }
            else if (function == 2) {
                METHOD_NAME = "SearchHopHoSoByNameAndPhongID";
            }
            final String SOAP_ACTION = Constants.NAMESPACE + METHOD_NAME;
            SoapObject request = new SoapObject(Constants.NAMESPACE, METHOD_NAME);
            if (function == Constants.FUNCTION_SEARCH) {
                request.addProperty("search_type", this.search_type);
                request.addProperty("search_val", this.search_val);
            } else if (function == 2) {
                request.addProperty("name", this.name);
                request.addProperty("phong_id", this.phong_id);
            }
            request.addProperty("page_curr", this.pageCurrent);
            request.addProperty("num_row_per_page", num_row_per_page);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            MarshalFloat marshalFloat = new MarshalFloat();
            marshalFloat.register(envelope);
            HttpTransportSE httpTransportSE = new HttpTransportSE(Constants.URL_HOPHS);
            httpTransportSE.call(SOAP_ACTION, envelope);
            SoapObject soapData = (SoapObject) envelope.getResponse();
            String ErrCode = soapData.getProperty("ErrCode").toString();
            String ErrDesc = soapData.getProperty("ErrDesc").toString();
            int total_record = Integer.parseInt(soapData.getProperty("Total_record").toString());
            if (soapData.hasProperty("Data")) {
                SoapObject soapArr = (SoapObject) soapData.getProperty("Data");
                for (int i = 0; i < soapArr.getPropertyCount(); i++) {
                    SoapObject soapItem = (SoapObject) soapArr.getProperty(i);
                    long MaHopHS = Long.parseLong(soapItem.getProperty("MaHopHS").toString());
                    String TenHop = "";
                    if (soapItem.hasProperty("TenHop"))
                        TenHop = soapItem.getProperty("TenHop").toString();
                    if (TenHop.equals("anyType{}"))
                        TenHop = "";
                    String GhiChu = "";
                    if (soapItem.hasProperty("GhiChu"))
                        GhiChu = soapItem.getProperty("GhiChu").toString();
                    if (GhiChu.equals("anyType{}"))
                        GhiChu = "";
                    boolean Active = true;
                    if (soapItem.hasAttribute("Active"))
                        Active = Boolean.parseBoolean(soapItem.getProperty("Active").toString());
                    int MaPhong = 6;
                    if (soapItem.hasProperty("MaPhong") && (soapItem.getProperty("MaPhong") != null))
                        MaPhong = Integer.parseInt(soapItem.getProperty("MaPhong").toString());
                    HopHoSo_ett hopHoSo_ett = new HopHoSo_ett(MaHopHS, TenHop, GhiChu, Active, MaPhong, total_record, ErrCode, ErrDesc);
                    arrData.add(hopHoSo_ett);
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
