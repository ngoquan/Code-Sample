package ngovanquan_803656.datn.model;

/**
 * Created by ngoquan on 5/5/2016.
 */
public class Phong_ett {
    private String MaCQLT;
    private long MaPhong;
    private String TenPhong;
    private String LichSuHinhThanh;
    private String ThoiGianTaiLieu;
    private int TongSoTaiLieu;
    private int SoTaiLieuDaChinhLy;
    private int SoTaiLieuChuaChinhLy;
    private String CacNhomTaiLieu;
//    private long MaLoaiHinhTaiLieu;
    private long MaNN;
    private String ThoiGianNhapTaiLieu;
    private String CongCuTraCuu;
    private String LapBanSaoBaoHiem;
    private String GhiChu;
    private int sl_phong;
    private int total_record;
    private String ErrCode;
    private String ErrDesc;
    public Phong_ett() {}
    public Phong_ett(String MaCQLT, long MaPhong, String TenPhong, String LichSuHinhThanh, String ThoiGianTaiLieu, int TongSoTaiLieu,
                     int SoTaiLieuDaChinhLy, int SoTaiLieuChuaChinhLy, String CacNhomTaiLieu, long MaNN,
                     String ThoiGianNhapTaiLieu, String CongCuTraCuu, String LapBanSaoBaoHiem, String GhiChu) {
        this.MaCQLT = MaCQLT;
        this.MaPhong = MaPhong;
        this.TenPhong = TenPhong;
        this.LichSuHinhThanh = LichSuHinhThanh;
        this.ThoiGianTaiLieu = ThoiGianTaiLieu;
        this.TongSoTaiLieu = TongSoTaiLieu;
        this.SoTaiLieuDaChinhLy = SoTaiLieuDaChinhLy;
        this.SoTaiLieuChuaChinhLy = SoTaiLieuChuaChinhLy;
        this.CacNhomTaiLieu = CacNhomTaiLieu;
//        this.MaLoaiHinhTaiLieu = MaLoaiHinhTaiLieu;
        this.MaNN = MaNN;
        this.ThoiGianNhapTaiLieu = ThoiGianNhapTaiLieu;
        this.CongCuTraCuu = CongCuTraCuu;
        this.LapBanSaoBaoHiem = LapBanSaoBaoHiem;
        this.GhiChu = GhiChu;
    }

    public Phong_ett(String MaCQLT, long MaPhong, String TenPhong, String LichSuHinhThanh, String ThoiGianTaiLieu, int TongSoTaiLieu,
                     int SoTaiLieuDaChinhLy, int SoTaiLieuChuaChinhLy, String CacNhomTaiLieu, long MaNN,
                     String ThoiGianNhapTaiLieu, String CongCuTraCuu, String LapBanSaoBaoHiem, String GhiChu, int sl_phong) {
        this.MaCQLT = MaCQLT;
        this.MaPhong = MaPhong;
        this.TenPhong = TenPhong;
        this.LichSuHinhThanh = LichSuHinhThanh;
        this.ThoiGianTaiLieu = ThoiGianTaiLieu;
        this.TongSoTaiLieu = TongSoTaiLieu;
        this.SoTaiLieuDaChinhLy = SoTaiLieuDaChinhLy;
        this.SoTaiLieuChuaChinhLy = SoTaiLieuChuaChinhLy;
        this.CacNhomTaiLieu = CacNhomTaiLieu;
//        this.MaLoaiHinhTaiLieu = MaLoaiHinhTaiLieu;
        this.MaNN = MaNN;
        this.ThoiGianNhapTaiLieu = ThoiGianNhapTaiLieu;
        this.CongCuTraCuu = CongCuTraCuu;
        this.LapBanSaoBaoHiem = LapBanSaoBaoHiem;
        this.GhiChu = GhiChu;
        this.sl_phong = sl_phong;
    }
    public Phong_ett(String MaCQLT, long MaPhong, String TenPhong, String LichSuHinhThanh, String ThoiGianTaiLieu, int TongSoTaiLieu,
                     int SoTaiLieuDaChinhLy, int SoTaiLieuChuaChinhLy, String CacNhomTaiLieu, long MaNN,
                     String ThoiGianNhapTaiLieu, String CongCuTraCuu, String LapBanSaoBaoHiem, String GhiChu, int total_record,
                     String ErrCode, String ErrDesc) {
        this.MaCQLT = MaCQLT;
        this.MaPhong = MaPhong;
        this.TenPhong = TenPhong;
        this.LichSuHinhThanh = LichSuHinhThanh;
        this.ThoiGianTaiLieu = ThoiGianTaiLieu;
        this.TongSoTaiLieu = TongSoTaiLieu;
        this.SoTaiLieuDaChinhLy = SoTaiLieuDaChinhLy;
        this.SoTaiLieuChuaChinhLy = SoTaiLieuChuaChinhLy;
        this.CacNhomTaiLieu = CacNhomTaiLieu;
//        this.MaLoaiHinhTaiLieu = MaLoaiHinhTaiLieu;
        this.MaNN = MaNN;
        this.ThoiGianNhapTaiLieu = ThoiGianNhapTaiLieu;
        this.CongCuTraCuu = CongCuTraCuu;
        this.LapBanSaoBaoHiem = LapBanSaoBaoHiem;
        this.GhiChu = GhiChu;
        this.total_record = total_record;
        this.ErrCode = ErrCode;
        this.ErrDesc = ErrDesc;
    }

    public Phong_ett(int total_record, String ErrCode, String ErrDesc) {
        this.total_record = total_record;
        this.ErrCode = ErrCode;
        this.ErrDesc = ErrDesc;
    }

    public String getMaCQLT() {
        return MaCQLT;
    }

    public void setMaCQLT(String maCQLT) {
        MaCQLT = maCQLT;
    }

    public long getMaPhong() {
        return MaPhong;
    }

    public void setMaPhong(long maPhong) {
        MaPhong = maPhong;
    }

    public String getTenPhong() {
        return TenPhong;
    }

    public void setTenPhong(String tenPhong) {
        TenPhong = tenPhong;
    }

    public String getLichSuHinhThanh() {
        return LichSuHinhThanh;
    }

    public void setLichSuHinhThanh(String lichSuHinhThanh) {
        LichSuHinhThanh = lichSuHinhThanh;
    }

    public String getThoiGianTaiLieu() {
        return ThoiGianTaiLieu;
    }

    public void setThoiGianTaiLieu(String thoiGianTaiLieu) {
        ThoiGianTaiLieu = thoiGianTaiLieu;
    }

    public int getTongSoTaiLieu() {
        return TongSoTaiLieu;
    }

    public void setTongSoTaiLieu(int tongSoTaiLieu) {
        TongSoTaiLieu = tongSoTaiLieu;
    }

    public int getSoTaiLieuDaChinhLy() {
        return SoTaiLieuDaChinhLy;
    }

    public void setSoTaiLieuDaChinhLy(int soTaiLieuDaChinhLy) {
        SoTaiLieuDaChinhLy = soTaiLieuDaChinhLy;
    }

    public String getCacNhomTaiLieu() {
        return CacNhomTaiLieu;
    }

    public void setCacNhomTaiLieu(String cacNhomTaiLieu) {
        CacNhomTaiLieu = cacNhomTaiLieu;
    }

    public int getSoTaiLieuChuaChinhLy() {
        return SoTaiLieuChuaChinhLy;
    }

    public void setSoTaiLieuChuaChinhLy(int soTaiLieuChuaChinhLy) {
        SoTaiLieuChuaChinhLy = soTaiLieuChuaChinhLy;
    }

//    public long getMaLoaiHinhTaiLieu() {
//        return MaLoaiHinhTaiLieu;
//    }
//
//    public void setMaLoaiHinhTaiLieu(long maLoaiHinhTaiLieu) {
//        MaLoaiHinhTaiLieu = maLoaiHinhTaiLieu;
//    }

    public long getMaNN() {
        return MaNN;
    }

    public void setMaNN(long maNN) {
        MaNN = maNN;
    }

    public String getThoiGianNhapTaiLieu() {
        return ThoiGianNhapTaiLieu;
    }

    public void setThoiGianNhapTaiLieu(String thoiGianNhapTaiLieu) {
        ThoiGianNhapTaiLieu = thoiGianNhapTaiLieu;
    }

    public String getCongCuTraCuu() {
        return CongCuTraCuu;
    }

    public void setCongCuTraCuu(String congCuTraCuu) {
        CongCuTraCuu = congCuTraCuu;
    }

    public String getLapBanSaoBaoHiem() {
        return LapBanSaoBaoHiem;
    }

    public void setLapBanSaoBaoHiem(String lapBanSaoBaoHiem) {
        LapBanSaoBaoHiem = lapBanSaoBaoHiem;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String ghiChu) {
        GhiChu = ghiChu;
    }

    public int getSl_phong() {
        return sl_phong;
    }

    public void setSl_phong(int sl_phong) {
        this.sl_phong = sl_phong;
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
