package ngovanquan_803656.datn.model;

/**
 * Created by ngoquan on 5/11/2016.
 */
public class CQLT_ett {
    private String MaCQLT;
    private String TenCQLT;
    private String DiaChi;
    private String SDT;
    private String Email;
    private String Website;
    private String LichSuPhatTrien;
    private int total_record;
    private String ErrCode;
    private String ErrDesc;

    public CQLT_ett() {}
    public CQLT_ett(String MaCQLT, String TenCQLT, String DiaChi, String SDT, String Email, String Website, String LichSuPhatTrien) {
        this.MaCQLT = MaCQLT;
        this.TenCQLT = TenCQLT;
        this.DiaChi = DiaChi;
        this.SDT = SDT;
        this.Email = Email;
        this.Website = Website;
        this.LichSuPhatTrien = LichSuPhatTrien;
    }
    public CQLT_ett(String MaCQLT, String TenCQLT, String DiaChi, String SDT, String Email, String Website, String LichSuPhatTrien, int total_record) {
        this.MaCQLT = MaCQLT;
        this.TenCQLT = TenCQLT;
        this.DiaChi = DiaChi;
        this.SDT = SDT;
        this.Email = Email;
        this.Website = Website;
        this.LichSuPhatTrien = LichSuPhatTrien;
        this.total_record = total_record;
    }
    public CQLT_ett(int total_record, String ErrCode, String ErrDesc) {
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

    public String getTenCQLT() {
        return TenCQLT;
    }

    public void setTenCQLT(String tenCQLT) {
        TenCQLT = tenCQLT;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getWebsite() {
        return Website;
    }

    public void setWebsite(String website) {
        Website = website;
    }

    public String getLichSuPhatTrien() {
        return LichSuPhatTrien;
    }

    public void setLichSuPhatTrien(String lichSuPhatTrien) {
        LichSuPhatTrien = lichSuPhatTrien;
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
