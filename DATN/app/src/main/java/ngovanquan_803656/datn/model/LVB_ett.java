package ngovanquan_803656.datn.model;

/**
 * Created by ngoquan on 4/14/2016.
 */
public class LVB_ett {
    private long MaLVB;
    private String TenLVB;
    private String GhiChu;
    private int STT;
    private boolean Active;
    private String ErrCode;
    private String ErrDesc;
    private int total_record;
    public LVB_ett() {}
    public LVB_ett(long MaLVB, String TenLVB, String GhiChu, int STT, boolean Active, int total_record) {
        this.MaLVB = MaLVB;
        this.TenLVB = TenLVB;
        this.GhiChu = GhiChu;
        this.STT = STT;
        this.Active = Active;
        this.total_record = total_record;
    }

    public LVB_ett(long MaLVB, String TenLVB, String GhiChu, int STT, boolean Active, int total_record, String ErrCode, String ErrDesc) {
        this.MaLVB = MaLVB;
        this.TenLVB = TenLVB;
        this.GhiChu = GhiChu;
        this.STT = STT;
        this.Active = Active;
        this.total_record = total_record;
        this.ErrCode = ErrCode;
        this.ErrDesc = ErrDesc;
    }

    public LVB_ett(int total_record, String ErrCode, String ErrDesc) {
        this.total_record = total_record;
        this.ErrCode = ErrCode;
        this.ErrDesc = ErrDesc;
    }

    public long getMaLVB() {
        return MaLVB;
    }

    public void setMaLVB(long maLVB) {
        MaLVB = maLVB;
    }

    public String getTenLVB() {
        return TenLVB;
    }

    public void setTenLVB(String tenLVB) {
        TenLVB = tenLVB;
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

    public String getErrDesc() {
        return ErrDesc;
    }

    public void setErrDesc(String errDesc) {
        ErrDesc = errDesc;
    }

    public String getErrCode() {
        return ErrCode;
    }

    public void setErrCode(String errCode) {
        ErrCode = errCode;
    }
}
