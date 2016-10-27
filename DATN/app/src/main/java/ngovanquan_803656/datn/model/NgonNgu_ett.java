package ngovanquan_803656.datn.model;

/**
 * Created by ngoquan on 4/13/2016.
 */
public class NgonNgu_ett {
    private long MaNN;
    private String TenNN;
    private String GhiChu;
    private int STT;
    private boolean Active;
    private String ErrCode;
    private String ErrDesc;
    private int total_record;
    public NgonNgu_ett() {}
    public NgonNgu_ett(long MaNN, String TenNN, String Ghichu, int STT, boolean Active, int total_record) {
        this.MaNN = MaNN;
        this.TenNN = TenNN;
        this.GhiChu = Ghichu;
        this.STT = STT;
        this.Active = Active;
        this.total_record = total_record;
    }
    public NgonNgu_ett(long MaNN, String TenNN, String GhiChu, int STT, boolean Active, int total_record, String ErrCode, String ErrDesc) {
        this.MaNN = MaNN;
        this.TenNN = TenNN;
        this.GhiChu = GhiChu;
        this.STT = STT;
        this.Active = Active;
        this.total_record = total_record;
        this.ErrCode = ErrCode;
        this.ErrDesc = ErrDesc;
    }

    public NgonNgu_ett(int total_record, String ErrCode, String ErrDesc) {
        this.total_record = total_record;
        this.ErrCode = ErrCode;
        this.ErrDesc = ErrDesc;
    }

    public long getMaNN() {
        return MaNN;
    }

    public void setMaNN(long maNN) {
        MaNN = maNN;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String ghiChu) {
        GhiChu = ghiChu;
    }

    public String getTenNN() {
        return TenNN;
    }

    public void setTenNN(String tenNN) {
        TenNN = tenNN;
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
