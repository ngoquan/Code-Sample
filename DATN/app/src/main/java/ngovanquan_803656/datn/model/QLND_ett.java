package ngovanquan_803656.datn.model;

import java.io.Serializable;

/**
 * Created by NGOQUAN on 5/26/2016.
 */
public class QLND_ett implements Serializable{
    private String LoginID;
    private String HoTen;
    private int MaNhom;
    private String MatKhau;
    private String GhiChu;
    private boolean Active;
    private int total_record;
    private String ErrCode;
    private String ErrDesc;
    public QLND_ett() {}
    public QLND_ett(String LoginID, String HoTen, int MaNhom, String MatKhau, String GhiChu, boolean Active) {
        this.LoginID = LoginID;
        this.HoTen = HoTen;
        this.MaNhom = MaNhom;
        this.MatKhau = MatKhau;
        this.GhiChu = GhiChu;
        this.Active = Active;
    }

    public QLND_ett(String LoginID, String HoTen, int MaNhom, String MatKhau, String GhiChu, boolean Active, int total_record) {
        this.LoginID = LoginID;
        this.HoTen = HoTen;
        this.MaNhom = MaNhom;
        this.MatKhau = MatKhau;
        this.GhiChu = GhiChu;
        this.Active = Active;
        this.total_record = total_record;
    }

    public QLND_ett(int total_record, String ErrCode, String ErrDesc) {
        this.total_record = total_record;
        this.ErrCode = ErrCode;
        this.ErrDesc = ErrDesc;
    }

    public String getLoginID() {
        return LoginID;
    }

    public void setLoginID(String loginID) {
        LoginID = loginID;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }

    public int getMaNhom() {
        return MaNhom;
    }

    public void setMaNhom(int maNhom) {
        MaNhom = maNhom;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String matKhau) {
        MatKhau = matKhau;
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
