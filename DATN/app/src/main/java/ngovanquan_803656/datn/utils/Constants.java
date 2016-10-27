package ngovanquan_803656.datn.utils;

/**
 * Created by ngoquan on 4/3/2016.
 */
public class Constants {
    public static final String NAMESPACE = "http://tempuri.org/";
//    Link URL
    public static final String URL_LHTL = "http://daotao.nuce.edu.vn:8289/LoaiHinhTaiLieu_WebService.asmx?WSDL";
    public static final String URL_DTC = "http://daotao.nuce.edu.vn:8289/QLDoTinCay_WebService.asmx?WSDL";
    public static final String URL_DM = "http://daotao.nuce.edu.vn:8289/QLDoMat_WebService.asmx?WSDL";
    public static final String URL_NN = "http://daotao.nuce.edu.vn:8289/QLNgonNgu_WebService.asmx?WSDL";
    public static final String URL_TTVL = "http://daotao.nuce.edu.vn:8289/QLTTVL_Webservice.asmx?WSDL";
    public static final String URL_CDSD = "http://daotao.nuce.edu.vn:8289/QLCDSD_WebService.asmx?WSDL";
    public static final String URL_THBQ = "http://daotao.nuce.edu.vn:8289/QLTHBQ_WebService.asmx?WSDL";
    public static final String URL_LVB = "http://daotao.nuce.edu.vn:8289/QLLVB_WebService.asmx?WSDL";
    public static final String URL_PHONG = "http://daotao.nuce.edu.vn:8289/QLphong_webservice.asmx?WSDL";
    public static final String URL_HOPHS = "http://daotao.nuce.edu.vn:8289/QLHopHoSo_WebService.asmx?WSDL";
    public static final String URL_CQLT = "http://daotao.nuce.edu.vn:8289/QLCQLT_WebService.asmx?WSDL";
    public static final String URL_HOSO = "http://daotao.nuce.edu.vn:8289/QLHoSo_Webservice.asmx?WSDL";
    public static final String URL_MIXED = "http://daotao.nuce.edu.vn:8289/Mixed_WebService.asmx?WSDL";
    public static final String URL_VANBAN = "http://daotao.nuce.edu.vn:8289/QLVanBan_WebService.asmx?WSDL";
    public static final String URL_PQ = "http://daotao.nuce.edu.vn:8289/QLPhanQuyen_WebService.asmx?WSDL";
    public static final String URL_CHHT = "http://daotao.nuce.edu.vn:8289/CauHinhHeThong_WebService.asmx?WSDL";
    public static final String URL_AUTH = "http://daotao.nuce.edu.vn:8289/Auth_WebService.asmx?WSDL";
    public static final String URL_NND = "http://daotao.nuce.edu.vn:8289/QLNhomNguoiDung_WebService.asmx?WSDL";
    public static final String URL_ND = "http://daotao.nuce.edu.vn:8289/QLNguoiDung_WebService.asmx?WSDL";
    public static final String URL_TK = "http://daotao.nuce.edu.vn:8289/TimKiem.asmx?WSDL";


    public enum Menu {
        Trangchu(0, "Trang chủ"),
        QuanlyLuutru(1, "Quản lý Hồ sơ/Văn bản"),
        QuanlyDanhmuc(2, "Quản lý Danh mục"),
        QuanlyCQLT(3, "Quản lý Cơ quan lưu trữ"),
        QuanlyPhong(4, "Quản lý Phông"),
        QuanlyKho(5, "Quản lý Kho"),
        QuanlyMucLuc(6, "Quản lý Mục lục"),
        QuanlyHopHoSo(7, "Quản lý Hộp hồ sơ"),
        QuanlyLVB(8, "Quản lý Loại văn bản"),
        QuanlyTHBQ(9, "Quản lý Thời hạn bảo quản"),
        QuanlyCDSD (10, "Quản lý Chế độ sử dụng"),
        QuanlyTTVL(11, "Quản lý Trạng thái vật lý"),
        QuanlyNgonNgu(12, "Quản lý Ngôn ngữ"),
        QuanlyDVHC(13, "Quản lý DVHC"),
        QuanlyDoMat(14, "Quản lý Độ mật"),
        QuanlyDoTinCay(15, "Quản lý Độ tin cậy"),
        QuanlyLoaiHinhTaiLieu(16, "Quản lý Loại Hình tài liệu"),
        ThongKeBaoCao(17, "Thống kê báo cáo"),
        MucLucHoSo(18, "Mục lục Hồ sơ"),
        MucLucVanBan(19, "Mục lục Văn bản"),
        DanhSachPhong(20, "Danh sách Phông lưu trữ"),
        TimkiemNangcao(21, "Tìm kiếm Nâng cao"),
        QuantriHethong(22, "Quản trị Hệ thống"),
        QuanlyNhomNguoiDung(23, "Quản lý Nhóm người dùng"),
        QuanlyNguoiDung(24, "Quản lý Người dùng"),
        QuanlyPhanQuyen(25, "Quản lý Phân quyền"),
        GioithieuChicuc(26, "Giới thiệu"),
        TracucBaocao(27, "Tra cuc Báo cáo"),
        TimKiem(28, "Tìm kiếm"),
        cauhinhhethong(29, "Thiết lập hệ thống"),
        GioiThieuHeThong(30, "Giới thiệu Hệ thống"),
        ThongKeHoSoLuuTru(31, "Thống kê Hồ sơ lưu trữ"),
        QuanLyHoSoVanBan(32, "Quản lý Hồ sơ/Văn bản"),
        HoSoTuExcel(33, "Nhập mục lục Hồ sơ từ tệp Excel"),
        VanBanTuExcel(34, "Nhập mục lục Văn bản từ Excel");


        private int value;
        private String title;
        private Menu(int value, String title) {
            this.value = value;
            this.title = title;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
//
//    public enum HSSearchType
//    {
//        TieuDe, HSSo, MucLucSo
//    }
//
//    public enum VBSearchType
//    {
//        TrichYeuND, KyHieuVanBan, TacGia, HoSoSo
//    }

    public static final String ADMIN_NAME = "admin";

    public static final int NUM_ROW_PER_PAGE = 10;
    public static final int PAGE_JUMP_PAGING = 5;

    public static final int FUNCTION_ADD_NEW = 1;
    public static final int FUNCTION_UPDATE = 2;
    public static final int FUNCTION_DELETE = 3;
    public static final int FUNCTION_SEARCH = 4;
    public static final int FUNCTION_CHECK = 5;

    public static final int FUNCTION_GET_DIA_CHI = 1;
    public static final int FUNCTION_GET_EMAIL = 2;
    public static final int FUNCTION_GET_GIOI_THIEU = 3;
    public static final int FUNCTION_GET_PASSWORD = 4;
    public static final int FUNCTION_GET_SDT = 5;
    public static final int FUNCTION_GET_TEN_CCVT = 6;
    public static final int FUNCTION_GET_TEN_PHAN_MEM = 7;
    public static final int FUNCTION_GET_TEN_SNV = 8;
    public static final int FUNCTION_GET_WEBSITE = 9;

    public static final int F_GET_NB_HOSO_HOPHS = 1;
    public static final int F_GET_NB_HOSO_PHONG = 2;
    public static final int F_GET_NB_HOPHS_PHONG = 3;
    public static final int F_GET_NB_VB_FILE_ATTACH_PHONG = 4;
    public static final int F_GET_NB_VB_HOSO = 5;
    public static final int F_GET_NB_VB_PHONG = 6;





}
