package ngovanquan_803656.datn.model;

/**
 * Created by ngoquan on 4/13/2016.
 */
public class DoMat_ett {
    private long MaDoMat;
    private String TenDoMat;
    private String GhiChu;
    private int STT;
    private boolean Active;
    private String ErrCode;
    private String ErrDesc;
    private int total_record;

    public DoMat_ett() {}
    public DoMat_ett(long MaDoMat, String TenDoMat, String GhiChu, int STT, boolean Active, int total_record) {
        this.MaDoMat = MaDoMat;
        this.TenDoMat = TenDoMat;
        this.GhiChu = GhiChu;
        this.STT = STT;
        this.Active = Active;
        this.total_record = total_record;
    }

    public DoMat_ett(long MaDoMat, String TenDoMat, String GhiChu, int STT, boolean Active, int total_record, String ErrCode, String ErrDesc) {
        this.MaDoMat = MaDoMat;
        this.TenDoMat = TenDoMat;
        this.GhiChu = GhiChu;
        this.STT = STT;
        this.Active = Active;
        this.ErrCode = ErrCode;
        this.ErrDesc = ErrDesc;
        this.total_record = total_record;
    }

    public DoMat_ett(int total_record, String ErrCode, String ErrDesc) {
        this.total_record = total_record;
        this.ErrCode = ErrCode;
        this.ErrDesc = ErrDesc;
    }

    public long getMaDoMat() {
        return MaDoMat;
    }

    public void setMaDoMat(long maDoMat) {
        MaDoMat = maDoMat;
    }

    public String getTenDoMat() {
        return TenDoMat;
    }

    public void setTenDoMat(String tenDoMat) {
        TenDoMat = tenDoMat;
    }

    public int getSTT() {
        return STT;
    }

    public void setSTT(int STT) {
        this.STT = STT;
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
