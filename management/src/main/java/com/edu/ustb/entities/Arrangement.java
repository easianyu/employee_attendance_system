package com.edu.ustb.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class Arrangement implements Serializable {
    private String empno;
    private String start;
    private String end;
    private String checktypeno;
    private String rlstart;
    private String rlend;
    private String isearly;
    private String islate;
    private String isover;
    private String isleave;

    public Arrangement() {
    }

    public Arrangement(String empno, String start, String end, String checktypeno, String rlstart, String rlend, String isearly, String islate, String isover, String isleave) {
        this.empno = empno;
        this.start = start;
        this.end = end;
        this.checktypeno = checktypeno;
        this.rlstart = rlstart;
        this.rlend = rlend;
        this.isearly = isearly;
        this.islate = islate;
        this.isover = isover;
        this.isleave = isleave;
    }

    @Override
    public String toString() {
        return "Arrangement{" +
                "empno='" + empno + '\'' +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                ", checktypeno='" + checktypeno + '\'' +
                ", rlstart='" + rlstart + '\'' +
                ", rlend='" + rlend + '\'' +
                ", isearly='" + isearly + '\'' +
                ", islate='" + islate + '\'' +
                ", isover='" + isover + '\'' +
                ", isleave='" + isleave + '\'' +
                '}';
    }

    public String getEmpno() {
        return empno;
    }

    public void setEmpno(String empno) {
        this.empno = empno;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getChecktypeno() {
        return checktypeno;
    }

    public void setChecktypeno(String checktypeno) {
        this.checktypeno = checktypeno;
    }

    public String getRlstart() {
        return rlstart;
    }

    public void setRlstart(String rlstart) {
        this.rlstart = rlstart;
    }

    public String getRlend() {
        return rlend;
    }

    public void setRlend(String rlend) {
        this.rlend = rlend;
    }

    public String getIsearly() {
        return isearly;
    }

    public void setIsearly(String isearly) {
        this.isearly = isearly;
    }

    public String getIslate() {
        return islate;
    }

    public void setIslate(String islate) {
        this.islate = islate;
    }

    public String getIsover() {
        return isover;
    }

    public void setIsover(String isover) {
        this.isover = isover;
    }

    public String getIsleave() {
        return isleave;
    }

    public void setIsleave(String isleave) {
        this.isleave = isleave;
    }
}
