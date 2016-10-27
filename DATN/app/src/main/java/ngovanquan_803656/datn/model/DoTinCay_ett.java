package ngovanquan_803656.datn.model;

/**
 * Created by ngoquan on 4/13/2016.
 */
public class DoTinCay_ett {
    private String ErrCode;
    private String ErrDesc;
    private long MaDoTinCay;
    private String TenDoTinCay;
    private String GhiChu;
    private int STT;
    private boolean Active;
    private int total_record;
    public DoTinCay_ett() {}
    public DoTinCay_ett(long MaDoTinCay, String TenDoTinCay, String GhiChu, int STT, boolean Active, int total_record) {
        this.MaDoTinCay = MaDoTinCay;
        this.TenDoTinCay = TenDoTinCay;
        this.GhiChu = GhiChu;
        this.STT = STT;
        this.Active = Active;
        this.total_record = total_record;
    }

    public DoTinCay_ett(long MaDoTinCay, String TenDoTinCay, String GhiChu, int STT, boolean Active, int total_record, String ErrCode, String ErrDesc) {
        this.MaDoTinCay = MaDoTinCay;
        this.TenDoTinCay = TenDoTinCay;
        this.GhiChu = GhiChu;
        this.STT = STT;
        this.Active = Active;
        this.total_record = total_record;
        this.ErrCode = ErrCode;
        this.ErrDesc = ErrDesc;
    }

    public DoTinCay_ett(int total_record, String ErrCode, String ErrDesc) {
        this.total_record = total_record;
        this.ErrCode = ErrCode;
        this.ErrDesc = ErrDesc;
    }

    public String getErrCode() {
        return ErrCode;
    }

    public void setErrCode(String errCode) {
        ErrCode = errCode;
    }

    public long getMaDoTinCay() {
        return MaDoTinCay;
    }

    public void setMaDoTinCay(long maDoTinCay) {
        MaDoTinCay = maDoTinCay;
    }

    public String getErrDesc() {
        return ErrDesc;
    }

    public void setErrDesc(String errDesc) {
        ErrDesc = errDesc;
    }

    public String getTenDoTinCay() {
        return TenDoTinCay;
    }

    public void setTenDoTinCay(String tenDoTinCay) {
        TenDoTinCay = tenDoTinCay;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String ghiChu) {
        GhiChu = ghiChu;
    }

    public int getSTT() {
        return STT;
    }

    public void setSTT(int STT) {
        this.STT = STT;
    }

    public boolean isActive() {
        return Active;
    }

    public void setActive(boolean active) {
        Active = active;
    }

    public int getTotal_record() {
        return total_record;
    }

    public void setTotal_record(int total_record) {
        this.total_record = total_record;
    }
}
