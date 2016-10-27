package ngovanquan_803656.datn.model;

/**
 * Created by ngoquan on 4/14/2016.
 */
public class TTVL_ett {
    private long MaTTVL;
    private String TenTTVL;
    private String GhiChu;
    private int STT;
    private boolean Active;
    private String ErrCode;
    private String ErrDesc;
    private int total_record;
    public TTVL_ett() {}
    public TTVL_ett(long MaTTVL, String TenTTVL, String GhiChu, int STT, boolean Active, int total_record) {
        this.MaTTVL = MaTTVL;
        this.TenTTVL = TenTTVL;
        this.GhiChu = GhiChu;
        this.STT = STT;
        this.Active = Active;
        this.total_record = total_record;
    }

    public TTVL_ett(long MaTTVL, String TenTTVL, String GhiChu, int STT, boolean Active, int total_record, String ErrCode, String ErrDesc) {
        this.MaTTVL = MaTTVL;
        this.TenTTVL = TenTTVL;
        this.GhiChu = GhiChu;
        this.STT = STT;
        this.Active = Active;
        this.total_record = total_record;
        this.ErrCode = ErrCode;
        this.ErrDesc = ErrDesc;
    }

    public TTVL_ett(int total_record, String ErrCode, String ErrDesc) {
        this.total_record = total_record;
        this.ErrCode = ErrCode;
        this.ErrDesc = ErrDesc;
    }

    public String getTenTTVL() {
        return TenTTVL;
    }

    public void setTenTTVL(String tenTTVL) {
        TenTTVL = tenTTVL;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String ghiChu) {
        GhiChu = ghiChu;
    }

    public long getMaTTVL() {
        return MaTTVL;
    }

    public void setMaTTVL(long maTTVL) {
        MaTTVL = maTTVL;
    }

    public int getSTT() {
        return STT;
    }

    public void setSTT(int STT) {
        this.STT = STT;
    }

    public String getErrCode() {
        return ErrCode;
    }

    public void setErrCode(String errCode) {
        ErrCode = errCode;
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

    public String getErrDesc() {
        return ErrDesc;
    }

    public void setErrDesc(String errDesc) {
        ErrDesc = errDesc;
    }
}
