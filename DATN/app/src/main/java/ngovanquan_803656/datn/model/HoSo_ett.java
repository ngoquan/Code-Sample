package ngovanquan_803656.datn.model;

import java.io.Serializable;

/**
 * Created by ngoquan on 5/9/2016.
 */
public class HoSo_ett implements Serializable{
    private long MaPhong;
    private String MucLucSo;
    private long MaHopHS;
    private String HoSoSo;
    private String KHTT;
    private String IDShow;
    private String TieuDeHs;
    private String ChuGiai;
    private String ThoiGianBatDau;
    private String ThoiGianKetThuc;
    private int MaNN;
    private String ButTich;
    private String SoLuongTo;
    private int MaTHBQ;
    private int MaCDSD;
    private int MaTTVL;
    private int sl_hs;
    private String ErrCode;
    private String ErrDesc;
    private int total_record;

    public HoSo_ett() {}
    public HoSo_ett(long MaPhong, String MucLucSo, long MaHopHS, String HoSoSo, String KHTT, String TieuDeHS, String ChuGiai, String ThoiGianBatDau,
                    String ThoiGianKetThuc, int MaNN, String ButTich, String SoLuongTo, int MaTHBQ, int MaCDSD, int MaTTVL) {
        this.MaPhong = MaPhong;
        this.MucLucSo = MucLucSo;
        this.MaHopHS = MaHopHS;
        this.HoSoSo = HoSoSo;
        this.KHTT = KHTT;
        this.TieuDeHs = TieuDeHS;
        this.ChuGiai = ChuGiai;
        this.ThoiGianBatDau = ThoiGianBatDau;
        this.ThoiGianKetThuc = ThoiGianKetThuc;
        this.MaNN = MaNN;
        this.ButTich = ButTich;
        this.SoLuongTo = SoLuongTo;
        this.MaTHBQ = MaTHBQ;
        this.MaCDSD = MaCDSD;
        this.MaTTVL = MaTTVL;
    }

    public HoSo_ett(long MaPhong, String MucLucSo, long MaHopHS, String HoSoSo, String KHTT, String TieuDeHS, String ChuGiai, String ThoiGianBatDau,
                    String ThoiGianKetThuc, int MaNN, String ButTich, String SoLuongTo, int MaTHBQ, int MaCDSD, int MaTTVL, int sl_hs) {
        this.MaPhong = MaPhong;
        this.MucLucSo = MucLucSo;
        this.MaHopHS = MaHopHS;
        this.HoSoSo = HoSoSo;
        this.KHTT = KHTT;
        this.TieuDeHs = TieuDeHS;
        this.ChuGiai = ChuGiai;
        this.ThoiGianBatDau = ThoiGianBatDau;
        this.ThoiGianKetThuc = ThoiGianKetThuc;
        this.MaNN = MaNN;
        this.ButTich = ButTich;
        this.SoLuongTo = SoLuongTo;
        this.MaTHBQ = MaTHBQ;
        this.MaCDSD = MaCDSD;
        this.MaTTVL = MaTTVL;
        this.sl_hs = sl_hs;
    }

    public HoSo_ett(long MaPhong, String MucLucSo, long MaHopHS, String HoSoSo, String KHTT, String TieuDeHS, String ChuGiai, String ThoiGianBatDau,
                    String ThoiGianKetThuc, int MaNN, String ButTich, String SoLuongTo, int MaTHBQ, int MaCDSD, int MaTTVL, int total_record,
                    String ErrCode, String ErrDesc) {
        this.MaPhong = MaPhong;
        this.MucLucSo = MucLucSo;
        this.MaHopHS = MaHopHS;
        this.HoSoSo = HoSoSo;
        this.KHTT = KHTT;
        this.TieuDeHs = TieuDeHS;
        this.ChuGiai = ChuGiai;
        this.ThoiGianBatDau = ThoiGianBatDau;
        this.ThoiGianKetThuc = ThoiGianKetThuc;
        this.MaNN = MaNN;
        this.ButTich = ButTich;
        this.SoLuongTo = SoLuongTo;
        this.MaTHBQ = MaTHBQ;
        this.MaCDSD = MaCDSD;
        this.MaTTVL = MaTTVL;
        this.ErrCode = ErrCode;
        this.ErrDesc = ErrDesc;
        this.total_record = total_record;
    }

    public HoSo_ett(long MaPhong, String MucLucSo, long MaHopHS, String HoSoSo, String KHTT, String IDShow, String TieuDeHS, String ChuGiai, String ThoiGianBatDau,
                    String ThoiGianKetThuc, int MaNN, String ButTich, String SoLuongTo, int MaTHBQ, int MaCDSD, int MaTTVL, int total_record,
                    String ErrCode, String ErrDesc) {
        this.MaPhong = MaPhong;
        this.MucLucSo = MucLucSo;
        this.MaHopHS = MaHopHS;
        this.HoSoSo = HoSoSo;
        this.KHTT = KHTT;
        this.IDShow = IDShow;
        this.TieuDeHs = TieuDeHS;
        this.ChuGiai = ChuGiai;
        this.ThoiGianBatDau = ThoiGianBatDau;
        this.ThoiGianKetThuc = ThoiGianKetThuc;
        this.MaNN = MaNN;
        this.ButTich = ButTich;
        this.SoLuongTo = SoLuongTo;
        this.MaTHBQ = MaTHBQ;
        this.MaCDSD = MaCDSD;
        this.MaTTVL = MaTTVL;
        this.ErrCode = ErrCode;
        this.ErrDesc = ErrDesc;
        this.total_record = total_record;
    }
    public HoSo_ett(int total_record, String ErrCode, String ErrDesc) {
        this.ErrCode = ErrCode;
        this.ErrDesc = ErrDesc;
        this.total_record = total_record;
    }

    public long getMaPhong() {
        return MaPhong;
    }

    public void setMaPhong(long maPhong) {
        MaPhong = maPhong;
    }

    public String getMucLucSo() {
        return MucLucSo;
    }

    public void setMucLucSo(String mucLucSo) {
        MucLucSo = mucLucSo;
    }

    public long getMaHopHS() {
        return MaHopHS;
    }

    public void setMaHopHS(long maHopHS) {
        MaHopHS = maHopHS;
    }

    public String getHoSoSo() {
        return HoSoSo;
    }

    public void setHoSoSo(String hoSoSo) {
        HoSoSo = hoSoSo;
    }

    public String getKHTT() {
        return KHTT;
    }

    public void setKHTT(String KHTT) {
        this.KHTT = KHTT;
    }

    public String getIDShow() {
        return IDShow;
    }

    public void setIDShow(String IDShow) {
        this.IDShow = IDShow;
    }

    public String getTieuDeHs() {
        return TieuDeHs;
    }

    public void setTieuDeHs(String tieuDeHs) {
        TieuDeHs = tieuDeHs;
    }

    public String getChuGiai() {
        return ChuGiai;
    }

    public void setChuGiai(String chuGiai) {
        ChuGiai = chuGiai;
    }

    public String getThoiGianBatDau() {
        return ThoiGianBatDau;
    }

    public void setThoiGianBatDau(String thoiGianBatDau) {
        ThoiGianBatDau = thoiGianBatDau;
    }

    public String getThoiGianKetThuc() {
        return ThoiGianKetThuc;
    }

    public void setThoiGianKetThuc(String thoiGianKetThuc) {
        ThoiGianKetThuc = thoiGianKetThuc;
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

    public String getSoLuongTo() {
        return SoLuongTo;
    }

    public void setSoLuongTo(String soLuongTo) {
        SoLuongTo = soLuongTo;
    }

    public int getMaTHBQ() {
        return MaTHBQ;
    }

    public void setMaTHBQ(int maTHBQ) {
        MaTHBQ = maTHBQ;
    }

    public int getMaCDSD() {
        return MaCDSD;
    }

    public void setMaCDSD(int maCDSD) {
        MaCDSD = maCDSD;
    }

    public int getMaTTVL() {
        return MaTTVL;
    }

    public void setMaTTVL(int maTTVL) {
        MaTTVL = maTTVL;
    }

    public int getSl_hs() {
        return sl_hs;
    }

    public void setSl_hs(int sl_hs) {
        this.sl_hs = sl_hs;
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

    public int getTotal_record() {
        return total_record;
    }

    public void setTotal_record(int total_record) {
        this.total_record = total_record;
    }
}
