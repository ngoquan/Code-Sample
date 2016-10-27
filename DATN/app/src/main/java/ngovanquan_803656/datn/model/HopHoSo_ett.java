package ngovanquan_803656.datn.model;

import java.util.ArrayList;

/**
 * Created by ngoquan on 5/9/2016.
 */
public class HopHoSo_ett {
    private long MaHopHS;
    private String TenHop;
    private String GhiChu;
    private boolean Active;
    private int MaPhong;
    private int total_record;
    private String ErrCode;
    private String ErrDesc;
    private int sl_hhs;
    private ArrayList<HoSo_ett> list_HoSo = new ArrayList<>();
    public HopHoSo_ett() {}
    public HopHoSo_ett(long MaHopHS, String TenHop, String GhiChu, boolean Active, int MaPhong) {
        this.MaHopHS = MaHopHS;
        this.TenHop = TenHop;
        this.GhiChu = GhiChu;
        this.Active = Active;
        this.MaPhong = MaPhong;
    }

    public HopHoSo_ett(long MaHopHS, String TenHop, String GhiChu, boolean Active, int MaPhong, int sl_hhs) {
        this.MaHopHS = MaHopHS;
        this.TenHop = TenHop;
        this.GhiChu = GhiChu;
        this.Active = Active;
        this.MaPhong = MaPhong;
        this.sl_hhs = sl_hhs;
    }

    public HopHoSo_ett(long MaHopHS, String TenHop, String GhiChu, boolean Active, int MaPhong, int total_record, int sl_hhs) {
        this.MaHopHS = MaHopHS;
        this.TenHop = TenHop;
        this.GhiChu = GhiChu;
        this.Active = Active;
        this.MaPhong = MaPhong;
        this.total_record = total_record;
        this.sl_hhs = sl_hhs;
    }

    public HopHoSo_ett(long MaHopHS, String TenHop, String GhiChu, boolean Active, int MaPhong, int sl_hhs, ArrayList<HoSo_ett> list_HoSo) {
        this.MaHopHS = MaHopHS;
        this.TenHop = TenHop;
        this.GhiChu = GhiChu;
        this.Active = Active;
        this.MaPhong = MaPhong;
        this.sl_hhs = sl_hhs;
        this.list_HoSo = list_HoSo;
    }

    public HopHoSo_ett(long MaHopHS, String TenHop, String GhiChu, boolean Active, int MaPhong, int total_record, String ErrCode, String ErrDesc) {
        this.MaHopHS = MaHopHS;
        this.TenHop = TenHop;
        this.GhiChu = GhiChu;
        this.Active = Active;
        this.MaPhong = MaPhong;
        this.total_record = total_record;
        this.ErrCode = ErrCode;
        this.ErrDesc = ErrDesc;
    }

    public HopHoSo_ett(int total_record, String ErrCode, String ErrDesc) {
        this.total_record = total_record;
        this.ErrCode = ErrCode;
        this.ErrDesc = ErrDesc;
    }

    public long getMaHopHS() {
        return MaHopHS;
    }

    public void setMaHopHS(long maHopHS) {
        MaHopHS = maHopHS;
    }

    public String getTenHop() {
        return TenHop;
    }

    public void setTenHop(String tenHop) {
        TenHop = tenHop;
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

    public int getMaPhong() {
        return MaPhong;
    }

    public void setMaPhong(int maPhong) {
        MaPhong = maPhong;
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

    public int getSl_hhs() {
        return sl_hhs;
    }

    public void setSl_hhs(int sl_hhs) {
        this.sl_hhs = sl_hhs;
    }

    public ArrayList<HoSo_ett> getList_HoSo() {
        return list_HoSo;
    }

    public void setList_HoSo(ArrayList<HoSo_ett> list_HoSo) {
        this.list_HoSo = list_HoSo;
    }
}
