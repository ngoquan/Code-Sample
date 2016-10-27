package ngovanquan_803656.datn.model;

/**
 * Created by NGOQUAN on 5/26/2016.
 */
public class NhomNguoiDung_ett {
    private int MaNhom;
    private String TenNhom;
    private String GhiChu;
    private boolean Active;
    private int total_record;
    private String ErrCode;
    private String ErrDesc;

    public NhomNguoiDung_ett() {}
    public NhomNguoiDung_ett(int MaNhom, String TenNhom, String GhiChu, boolean Active) {
        this.MaNhom = MaNhom;
        this.TenNhom = TenNhom;
        this.GhiChu = GhiChu;
        this.Active = Active;
    }

    public NhomNguoiDung_ett(int MaNhom, String TenNhom, String GhiChu, boolean Active, int total_record) {
        this.MaNhom = MaNhom;
        this.TenNhom = TenNhom;
        this.GhiChu = GhiChu;
        this.Active = Active;
        this.total_record = total_record;
    }

    public NhomNguoiDung_ett(int total_record, String ErrCode, String ErrDesc) {
        this.total_record = total_record;
        this.ErrCode = ErrCode;
        this.ErrDesc = ErrDesc;
    }

    public int getMaNhom() {
        return MaNhom;
    }

    public void setMaNhom(int maNhom) {
        MaNhom = maNhom;
    }

    public String getTenNhom() {
        return TenNhom;
    }

    public void setTenNhom(String tenNhom) {
        TenNhom = tenNhom;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String ghiChu) {
        GhiChu = ghiChu;
    }

    public boolean isActive() {
        return Active;
    }

    public void setActive(boolean active) {
        Active = active;
    }

    public String getErrCode() {
        return ErrCode;
    }

    public void setErrCode(String errCode) {
        ErrCode = errCode;
    }

    public int getTotal_record() {
        return total_record;
    }

    public void setTotal_record(int total_record) {
        this.total_record = total_record;
    }

    public String getErrDesc() {
        return ErrDesc;
    }

    public void setErrDesc(String errDesc) {
        ErrDesc = errDesc;
    }
}
