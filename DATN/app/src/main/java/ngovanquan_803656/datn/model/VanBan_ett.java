package ngovanquan_803656.datn.model;

import java.io.Serializable;

/**
 * Created by NGOQUAN on 5/24/2016.
 */
public class VanBan_ett implements Serializable{
    private String MucLucSo;
    private String HoSoSo;
    private String ToSo;
    private String SoKyHieu;
    private String ThoiGian;
    private String TacGia;
    private int MaLVB;
    private String TrichYeuND;
    private String KHTT;
    private int MaDoMat;
    private String SoLuongTo;
    private int MaDoTinCay;
    private int MaTTVL;
    private int MaCDSD;
    private int MaNN;
    private String ButTich;
    private String GhiChu;
    private String VanBanId;

    private int total_record;
    private String ErrCode;
    private String ErrDesc;

    public VanBan_ett() {}
    public VanBan_ett(String VanBanId, String MucLucSo, String HoSoSo, String ToSo, String SoKyHieu, String ThoiGian, String TacGia, int MaLVB,
                      String TrichYeuND, String KHTT, int MaDoMat, String SoLuongTo, int MaDoTincay, int MaTTVL, int MaCDSD, int MaNN,
                      String ButTich, String GhiChu) {
        this.VanBanId = VanBanId;
        this.MucLucSo = MucLucSo;
        this.HoSoSo = HoSoSo;
        this.ToSo = ToSo;
        this.SoKyHieu = SoKyHieu;
        this.ThoiGian = ThoiGian;
        this.TacGia = TacGia;
        this.MaLVB = MaLVB;
        this.TrichYeuND = TrichYeuND;
        this.KHTT = KHTT;
        this.MaDoMat = MaDoMat;
        this.SoLuongTo = SoLuongTo;
        this.MaDoTinCay = MaDoTincay;
        this.MaTTVL = MaTTVL;
        this.MaCDSD = MaCDSD;
        this.MaNN = MaNN;
        this.ButTich = ButTich;
        this.GhiChu = GhiChu;
    }

    public VanBan_ett(String VanBanId, String MucLucSo, String HoSoSo, String ToSo, String SoKyHieu, String ThoiGian, String TacGia, int MaLVB,
                      String TrichYeuND, String KHTT, int MaDoMat, String SoLuongTo, int MaDoTincay, int MaTTVL, int MaCDSD, int MaNN,
                      String ButTich, String GhiChu, int total_record, String ErrCode, String ErrDesc) {
        this.VanBanId = VanBanId;
        this.MucLucSo = MucLucSo;
        this.HoSoSo = HoSoSo;
        this.ToSo = ToSo;
        this.SoKyHieu = SoKyHieu;
        this.ThoiGian = ThoiGian;
        this.TacGia = TacGia;
        this.MaLVB = MaLVB;
        this.TrichYeuND = TrichYeuND;
        this.KHTT = KHTT;
        this.MaDoMat = MaDoMat;
        this.SoLuongTo = SoLuongTo;
        this.MaDoTinCay = MaDoTincay;
        this.MaTTVL = MaTTVL;
        this.MaCDSD = MaCDSD;
        this.MaNN = MaNN;
        this.ButTich = ButTich;
        this.GhiChu = GhiChu;
        this.total_record = total_record;
        this.ErrCode = ErrCode;
        this.ErrDesc = ErrDesc;
    }

    public VanBan_ett(int total_record, String ErrCode, String ErrDesc) {
        this.total_record = total_record;
        this.ErrCode = ErrCode;
        this.ErrDesc = ErrDesc;
    }

    public String getMucLucSo() {
        return MucLucSo;
    }

    public void setMucLucSo(String mucLucSo) {
        MucLucSo = mucLucSo;
    }

    public String getHoSoSo() {
        return HoSoSo;
    }

    public void setHoSoSo(String hoSoSo) {
        HoSoSo = hoSoSo;
    }

    public String getToSo() {
        return ToSo;
    }

    public void setToSo(String toSo) {
        ToSo = toSo;
    }

    public String getSoKyHieu() {
        return SoKyHieu;
    }

    public void setSoKyHieu(String soKyHieu) {
        SoKyHieu = soKyHieu;
    }

    public String getThoiGian() {
        return ThoiGian;
    }

    public void setThoiGian(String thoiGian) {
        ThoiGian = thoiGian;
    }

    public String getTacGia() {
        return TacGia;
    }

    public void setTacGia(String tacGia) {
        TacGia = tacGia;
    }

    public int getMaLVB() {
        return MaLVB;
    }

    public void setMaLVB(int maLVB) {
        MaLVB = maLVB;
    }

    public String getTrichYeuND() {
        return TrichYeuND;
    }

    public void setTrichYeuND(String trichYeuND) {
        TrichYeuND = trichYeuND;
    }

    public int getMaDoMat() {
        return MaDoMat;
    }

    public void setMaDoMat(int maDoMat) {
        MaDoMat = maDoMat;
    }

    public String getKHTT() {
        return KHTT;
    }

    public void setKHTT(String KHTT) {
        this.KHTT = KHTT;
    }

    public String getSoLuongTo() {
        return SoLuongTo;
    }

    public void setSoLuongTo(String soLuongTo) {
        SoLuongTo = soLuongTo;
    }

    public int getMaDoTinCay() {
        return MaDoTinCay;
    }

    public void setMaDoTinCay(int maDoTinCay) {
        MaDoTinCay = maDoTinCay;
    }

    public int getMaTTVL() {
        return MaTTVL;
    }

    public void setMaTTVL(int maTTVL) {
        MaTTVL = maTTVL;
    }

    public int getMaCDSD() {
        return MaCDSD;
    }

    public void setMaCDSD(int maCDSD) {
        MaCDSD = maCDSD;
    }

    public int getMaNN() {
        return MaNN;
    }

    public void setMaNN(int maNN) {
        MaNN = maNN;
    }

    public String getButTich() {
        return ButTich;
    }

    public void setButTich(String butTich) {
        ButTich = butTich;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String ghiChu) {
        GhiChu = ghiChu;
    }

    public String getVanBanId() {
        return VanBanId;
    }

    public void setVanBanId(String vanBanId) {
        VanBanId = vanBanId;
    }

    public int getTotal_record() {
        return total_record;
    }

    public void setTotal_record(int total_record) {
        this.total_record = total_record;
    }

    public String getErrCode() {
        return ErrCode;
    }

    public void setErrCode(String errCode) {
        ErrCode = errCode;
    }

    public String getErrDesc() {
        return ErrDesc;
    }

    public void setErrDesc(String errDesc) {
        ErrDesc = errDesc;
    }
}
