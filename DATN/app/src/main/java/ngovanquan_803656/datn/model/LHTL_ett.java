package ngovanquan_803656.datn.model;

/**
 * Created by ngoquan on 4/1/2016.
 */
public class LHTL_ett {
    private long MaLoaiHinhTL;
    private String TenLoaiHinhTL;
    private long STT;
    private boolean Active;
    private String GhiChu;
    private String ErrCode;
    private String ErrDesc;
    private int total_record;
    public LHTL_ett() {}
    public LHTL_ett(long MaLoaiHinhTL, String TenLoaiHinhTL, long STT, boolean Active, String GhiChu, int total_record) {
        this.MaLoaiHinhTL = MaLoaiHinhTL;
        this.TenLoaiHinhTL = TenLoaiHinhTL;
        this.STT = STT;
        this.Active = Active;
        this.GhiChu = GhiChu;
        this.total_record = total_record;
    }

    public LHTL_ett(long MaLoaiHinhTL, String TenLoaiHinhTL, long STT, boolean Active, String GhiChu, int total_record, String ErrCode, String ErrDesc) {
        this.MaLoaiHinhTL = MaLoaiHinhTL;
        this.TenLoaiHinhTL = TenLoaiHinhTL;
        this.STT = STT;
        this.Active = Active;
        this.GhiChu = GhiChu;
        this.total_record = total_record;
        this.ErrCode = ErrCode;
        this.ErrDesc = ErrDesc;
    }
    public LHTL_ett(int total_record, String ErrCode, String ErrDesc) {
        this.total_record = total_record;
        this.ErrCode = ErrCode;
        this.ErrDesc = ErrDesc;
    }

    public long getMaLoaiHinhTL() {
        return MaLoaiHinhTL;
    }

    public void setMaLoaiHinhTL(long maLoaiHinhTL) {
        MaLoaiHinhTL = maLoaiHinhTL;
    }

    public String getTenLoaiHinhTL() {
        return TenLoaiHinhTL;
    }

    public void setTenLoaiHinhTL(String tenLoaiHinhTL) {
        TenLoaiHinhTL = tenLoaiHinhTL;
    }

    public long getSTT() {
        return STT;
    }

    public void setSTT(long STT) {
        this.STT = STT;
    }

    public boolean isActive() {
        return Active;
    }

    public void setActive(boolean active) {
        Active = active;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String ghiChu) {
        GhiChu = ghiChu;
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

    @Override
    public String toString() {
        return this.MaLoaiHinhTL + " - " + this.TenLoaiHinhTL + " - " + this.STT;
    }
}
