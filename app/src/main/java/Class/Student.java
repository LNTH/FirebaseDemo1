package Class;

import java.io.Serializable;

/**
 * Created by Thanh Huy on 9/19/2016.
 */
public class Student implements Serializable {
    private String Hoten;
    private String MSSV;
    private String sClass;

    public Student(){

    }
    public Student(String hoten, String MSSV, String sClass) {
        Hoten = hoten;
        this.MSSV = MSSV;
        this.sClass = sClass;
    }

    public String getHoten() {
        return Hoten;
    }

    public void setHoten(String hoten) {
        Hoten = hoten;
    }

    public String getMSSV() {
        return MSSV;
    }

    public void setMSSV(String MSSV) {
        this.MSSV = MSSV;
    }

    public String getsClass() {
        return sClass;
    }

    public void setsClass(String sClass) {
        this.sClass = sClass;
    }
}
