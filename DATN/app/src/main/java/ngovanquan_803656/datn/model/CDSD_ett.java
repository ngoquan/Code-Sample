package ngovanquan_803656.datn.model;

/**
 * Created by ngoquan on 4/14/2016.
 */
public class CDSD_ett {
    private long MaCDSD;
    private String TenCDSD;
    private String GhiChu;
    private int STT;
    private boolean Active;
    private String ErrCode;
    private String ErrDesc;
    private int total_record;

    public CDSD_ett() {}
    public CDSD_ett(long MaCSD, String TenCDSD, String GhiChu, int STT, boolean Active, int total_record) {
        this.MaCDSD = MaCSD;
        this.TenCDSD = TenCDSD;
        this.GhiChu = GhiChu;
        this.STT = STT;
        this.Active = Active;
        this.total_record = total_record;
    }

    public CDSD_ett(long MaCSD, String TenCDSD, String GhiChu, int STT, boolean Active, int total_record, String ErrCode, String ErrDesc) {
        this.MaCDSD = MaCSD;
        this.TenCDSD = TenCDSD;
        this.GhiChu = GhiChu;
        this.STT = STT;
        this.Active = Active;
        this.total_record = total_record;
        this.ErrCode = ErrCode;
        this.ErrDesc = ErrDesc;
    }

    public CDSD_ett(int total_record, String ErrCode, String ErrDesc) {
        this.ErrDesc = ErrDesc;
        this.ErrCode = ErrCode;
        this.total_record = total_record;
    }

    public long getMaCDSD() {
        return MaCDSD;
    }

    public void setMaCDSD(long maCDSD) {
        MaCDSD = maCDSD;
    }

    public String getTenCDSD() {
        return TenCDSD;
    }

    public void setTenCDSD(String tenCDSD) {
        TenCDSD = tenCDSD;
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
