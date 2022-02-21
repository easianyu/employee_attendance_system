package com.edu.ustb.entities;
import java.sql.Timestamp;
import java.sql.Date;

/**
 * User：经理
 * Method：查看出勤信息
 * Function: 取出数据库中出勤信息进行封装（送到前台还需要一步封装）
 */
public class TurnoutManager {
    private Integer arrno;
    private String empno;
    private String empname;
    private String depname;
    private String empposition;
    private String checktypename;
    private Timestamp start;
    private Timestamp end;
    private Timestamp rlstart;
    private Timestamp rlend;
    private Boolean isover;
    private Boolean islate;
    private Boolean isearly;
    private Boolean isleave;
    private Boolean istemporary;

    public TurnoutManager(Integer arrno, String empno, String empname, String depname, String empposition, String checktypename, Timestamp start, Timestamp end, Timestamp rlstart, Timestamp rlend, Boolean isover, Boolean islate, Boolean isearly, Boolean isleave, Boolean istemporary) {
        this.arrno = arrno;
        this.empno = empno;
        this.empname = empname;
        this.depname = depname;
        this.empposition = empposition;
        this.checktypename = checktypename;
        this.start = start;
        this.end = end;
        this.rlstart = rlstart;
        this.rlend = rlend;
        this.isover = isover;
        this.islate = islate;
        this.isearly = isearly;
        this.isleave = isleave;
        this.istemporary = istemporary;
    }

    @Override
    public String toString() {
        return "TurnoutManager{" +
                "arrno=" + arrno +
                ", empno='" + empno + '\'' +
                ", empname='" + empname + '\'' +
                ", depname='" + depname + '\'' +
                ", empposition='" + empposition + '\'' +
                ", checktypename='" + checktypename + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", rlstart=" + rlstart +
                ", rlend=" + rlend +
                ", isover=" + isover +
                ", islate=" + islate +
                ", isearly=" + isearly +
                ", isleave=" + isleave +
                ", istemporary=" + istemporary +
                '}';
    }

    public TurnoutManager() {
    }

    public Integer getArrno() {
        return arrno;
    }

    public void setArrno(Integer arrno) {
        this.arrno = arrno;
    }

    public String getEmpposition() {
        return empposition;
    }

    public void setEmpposition(String empposition) {
        this.empposition = empposition;
    }

    public String getEmpno() {
        return empno;
    }

    public void setEmpno(String empno) {
        this.empno = empno;
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname;
    }

    public String getDepname() {
        return depname;
    }

    public void setDepname(String depname) {
        this.depname = depname;
    }

    public String getChecktypename() {
        return checktypename;
    }

    public void setChecktypename(String checktypename) {
        this.checktypename = checktypename;
    }

    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }

    public Timestamp getRlstart() {
        return rlstart;
    }

    public void setRlstart(Timestamp rlstart) {
        this.rlstart = rlstart;
    }

    public Timestamp getRlend() {
        return rlend;
    }

    public void setRlend(Timestamp rlend) {
        this.rlend = rlend;
    }

    public Boolean getIsover() {
        return isover;
    }

    public void setIsover(Boolean isover) {
        this.isover = isover;
    }

    public Boolean getIslate() {
        return islate;
    }

    public void setIslate(Boolean islate) {
        this.islate = islate;
    }

    public Boolean getIsearly() {
        return isearly;
    }

    public void setIsearly(Boolean isearly) {
        this.isearly = isearly;
    }

    public Boolean getIsleave() {
        return isleave;
    }

    public void setIsleave(Boolean isleave) {
        this.isleave = isleave;
    }

    public Boolean getIstemporary() {
        return istemporary;
    }

    public void setIstemporary(Boolean istemporary) {
        this.istemporary = istemporary;
    }
}
