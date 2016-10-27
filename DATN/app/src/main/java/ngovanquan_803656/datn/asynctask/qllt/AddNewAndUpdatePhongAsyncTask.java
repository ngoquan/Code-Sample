package ngovanquan_803656.datn.asynctask.qllt;

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

import ngovanquan_803656.datn.model.Phong_ett;
import ngovanquan_803656.datn.utils.Constants;

/**
 * Created by ngoquan on 5/12/2016.
 */
public class AddNewAndUpdatePhongAsyncTask extends AsyncTask<Void, Void, Phong_ett> {
    private Activity activity;
    private String MaCQLT, TenPhong, LichSuHinhThanh, ThoiGianTaiLieu, CacNhomTaiLieu, ThoiGianNhapTaiLieu, CongCuTraCuu, LapBanSaoBaoHiem, GhiChu;
    private long MaPhong, MaNN;
    private int TongSoTaiLieu, SoTaiLieuDaChinhLy, SoTaiLieuChuaChinhLy, function;
    private Phong_ett phong_ett;
    private ProgressDialog progressDialog;

    public AddNewAndUpdatePhongAsyncTask(Activity activity, int function, long MaPhong, String MaCQLT, String TenPhong,
                                         String LichSuHinhThanh, String ThoiGianTaiLieu, int TongSoTaiLieu, int SoTaiLieuDaChinhLy,
                                         int SoTaiLieuChuaChinhLy, String CacNhomTaiLieu, long MaNN, String ThoiGianNhapTaiLieu,
                                         String CongCuTraCuu, String LapBanSaoBaoHiem, String GhiChu) {
        this.activity = activity;
        this.function = function;
        this.MaPhong = MaPhong;
        this.MaCQLT = MaCQLT;
        this.TenPhong = TenPhong;
        this.LichSuHinhThanh = LichSuHinhThanh;
        this.ThoiGianTaiLieu = ThoiGianTaiLieu;
        this.TongSoTaiLieu = TongSoTaiLieu;
        this.SoTaiLieuDaChinhLy = SoTaiLieuDaChinhLy;
        this.SoTaiLieuChuaChinhLy = SoTaiLieuChuaChinhLy;
        this.CacNhomTaiLieu = CacNhomTaiLieu;
        this.MaNN = MaNN;
        this.ThoiGianNhapTaiLieu = ThoiGianNhapTaiLieu;
        this.CongCuTraCuu = CongCuTraCuu;
        this.LapBanSaoBaoHiem = LapBanSaoBaoHiem;
        this.GhiChu = GhiChu;

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
    protected void onPostExecute(Phong_ett phong_ett) {
        super.onPostExecute(phong_ett);
        progressDialog.dismiss();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected Phong_ett doInBackground(Void... params) {
        try {
            String METHOD_NAME = "Insert_Phong";
            if (function == Constants.FUNCTION_UPDATE)
                METHOD_NAME = "Update_Phong";

            final String SOAP_ACTION = Constants.NAMESPACE + METHOD_NAME;
            SoapObject request = new SoapObject(Constants.NAMESPACE, METHOD_NAME);
            if (function == Constants.FUNCTION_UPDATE)
                request.addProperty("maphong", this.MaPhong);
            request.addProperty("macqlt", this.MaCQLT);
            request.addProperty("tenphong", this.TenPhong);
            request.addProperty("lichsuhinhthanh", this.LichSuHinhThanh);
            request.addProperty("thoigiantailieu", this.ThoiGianTaiLieu);
            request.addProperty("tongsotailieu", this.TongSoTaiLieu);
            request.addProperty("sotailieudachinhly", this.SoTaiLieuDaChinhLy);
            request.addProperty("sotailieuchuachinhly", this.SoTaiLieuChuaChinhLy);
            request.addProperty("cacnhomtailieu", this.CacNhomTaiLieu);
            request.addProperty("mangonngu", this.MaNN);
            request.addProperty("thoigiannhaptailieu", this.ThoiGianNhapTaiLieu);
            request.addProperty("congcutracuu", this.CongCuTraCuu);
            request.addProperty("lapbansaobaohiem", this.LapBanSaoBaoHiem);
            request.addProperty("ghichu", this.GhiChu);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            MarshalFloat marshalFloat = new MarshalFloat();
            marshalFloat.register(envelope);
            HttpTransportSE httpTransportSE = new HttpTransportSE(Constants.URL_PHONG);
            httpTransportSE.call(SOAP_ACTION, envelope);
            SoapObject soapData = (SoapObject) envelope.getResponse();
            String ErrCode = soapData.getProperty("ErrCode").toString();
            String ErrDesc = soapData.getProperty("ErrDesc").toString();
            int total_record = Integer.parseInt(soapData.getProperty("Total_record").toString());
            phong_ett = new Phong_ett(total_record, ErrCode, ErrDesc);
            return phong_ett;
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
