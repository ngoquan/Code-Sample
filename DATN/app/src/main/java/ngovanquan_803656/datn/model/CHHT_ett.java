package ngovanquan_803656.datn.model;

/**
 * Created by NGOQUAN on 5/26/2016.
 */
public class CHHT_ett {
    private String TenSoNoiVu;
    private String TenChiCucVT;
    private String DiaChi;
    private String SoDT;
    private String Email;
    private String Website;
    private String TenPhanMem;
    private String GioiThieu;
    private String ErrCode;
    private String ErrDesc;
    public CHHT_ett() {}
    public CHHT_ett(String TenSoNoiVu, String TenChiCucVT, String DiaChi, String SoDT, String Email, String Website, String TenPhanMem,
                    String GioiThieu) {
        this.TenSoNoiVu = TenSoNoiVu;
        this.TenChiCucVT = TenChiCucVT;
        this.DiaChi = DiaChi;
        this.SoDT = SoDT;
        this.Email = Email;
        this.Website = Website;
        this.TenPhanMem = TenPhanMem;
        this.GioiThieu = GioiThieu;
    }

    public CHHT_ett(String ErrCode, String ErrDesc) {
        this.ErrCode = ErrCode;
        this.ErrDesc = ErrDesc;
    }

    public String getTenSoNoiVu() {
        return TenSoNoiVu;
    }

    public void setTenSoNoiVu(String tenSoNoiVu) {
        TenSoNoiVu = tenSoNoiVu;
    }

    public String getTenChiCucVT() {
        return TenChiCucVT;
    }

    public void setTenChiCucVT(String tenChiCucVT) {
        TenChiCucVT = tenChiCucVT;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public String getSoDT() {
        return SoDT;
    }

    public void setSoDT(String soDT) {
        SoDT = soDT;
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

    public String getTenPhanMem() {
        return TenPhanMem;
    }

    public void setTenPhanMem(String tenPhanMem) {
        TenPhanMem = tenPhanMem;
    }

    public String getGioiThieu() {
        return GioiThieu;
    }

    public void setGioiThieu(String gioiThieu) {
        GioiThieu = gioiThieu;
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
