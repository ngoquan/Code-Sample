package ngovanquan_803656.datn.model;

/**
 * Created by ngoquan on 4/14/2016.
 */
public class THBQ_ett {
    private long MaTHBQ;
    private String TenTHBQ;
    private String GhiChu;
    private int STT;
    private boolean Active;
    private String ErrCode;
    private String ErrDesc;
    private int total_record;

    public THBQ_ett() {}
    public THBQ_ett(long MaTHBQ, String TenTHBQ, String GhiChu, int STT, boolean Active, int total_record) {
        this.MaTHBQ = MaTHBQ;
        this.TenTHBQ = TenTHBQ;
        this.GhiChu = GhiChu;
        this.STT = STT;
        this.Active = Active;
        this.total_record = total_record;
    }

    public THBQ_ett(long MaTHBQ, String TenTHBQ, String GhiChu, int STT, boolean Active, int total_record, String ErrCode, String ErrDesc) {
        this.MaTHBQ = MaTHBQ;
        this.TenTHBQ = TenTHBQ;
        this.GhiChu = GhiChu;
        this.STT = STT;
        this.Active = Active;
        this.total_record = total_record;
        this.ErrCode = ErrCode;
        this.ErrDesc = ErrDesc;
    }

    public THBQ_ett(int total_record, String ErrCode, String ErrDesc) {
        this.total_record = total_record;
        this.ErrCode = ErrCode;
        this.ErrDesc = ErrDesc;
    }

    public long getMaTHBQ() {
        return MaTHBQ;
    }

    public void setMaTHBQ(long maTHBQ) {
        MaTHBQ = maTHBQ;
    }

    public String getTenTHBQ() {
        return TenTHBQ;
    }

    public void setTenTHBQ(String tenTHBQ) {
        TenTHBQ = tenTHBQ;
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
