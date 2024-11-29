package team08.oop.lms.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Properties {
    
    private String id;
    private String tennhan;
    private String tacdong;
    private int chieudai;

    private String ghichu;
    private String thoigian;
    private String giaiphap;

    private String huyenref;
    private String xaref;
    private String thuocsong;


    public String getId() {
        return id;
    }

    public void setId(String  id) {
        this.id = id;
    }

    public String getTennhan() {
        return tennhan;
    }

    public void setTennhan(String tennhan) {
        this.tennhan = tennhan;
    }

    public String getTacdong() {
        return tacdong;
    }

    public void setTacdong(String tacdong) {
        this.tacdong = tacdong;
    }

    public int getChieudai() {
        return chieudai;
    }

    public void setChieudai(int chieudai) {
        this.chieudai = chieudai;
    }

    public String getThoigian() {
        return thoigian;
    }

    public String getGhichu(){
        return ghichu;
    }

    public void setGhichu(String ghichu){
        this.ghichu = ghichu;
    }

    public void setThoigian(String thoigian) {
        this.thoigian = thoigian;
    }

    public String getGiaiphap() {
        return giaiphap;
    }

    public void setGiaiphap(String giaiphap) {
        this.giaiphap = giaiphap;
    }

    public String getHuyenref() {
        return huyenref;
    }

    public void setHuyenref(String huyenref) {
        this.huyenref = huyenref;
    }

    public String getXaref() {
        return xaref;
    }

    public void setXaref(String xaref) {
        this.xaref = xaref;
    }


    public String getThuocsong() {
        return thuocsong;
    }

    public void setThuocsong(String thuocsong) {
        this.thuocsong = thuocsong;
    }

    public Properties(){

    }

}
