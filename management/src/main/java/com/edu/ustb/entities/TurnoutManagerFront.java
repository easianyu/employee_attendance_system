package com.edu.ustb.entities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * User：经理
 * Method：查看出勤信息
 * Function: 给后端提取的数据进行进一步封装
 */
public class TurnoutManagerFront {
    private Integer arrno;
    private String empno;
    private String empname;
    private String depname;
    private String empposition;
    private String checktypename;
    private String ddddate;
    private String start;
    private String end;
    private String rlstart;
    private String rlend;
    private String isover;
    private String islate;
    private String isearly;
    private String isleave;
    private String istemporary;
    private TurnoutManager turnoutManager;


    public TurnoutManagerFront(TurnoutManager turnoutManager) {
        this.arrno = turnoutManager.getArrno();
        this.empno = turnoutManager.getEmpno();
        this.empname = turnoutManager.getEmpname();
        this.depname = turnoutManager.getDepname();
        this.empposition = turnoutManager.getEmpposition();
        this.checktypename = turnoutManager.getChecktypename();

        //提取日期
        String rawDate = turnoutManager.getStart().toString();
        this.ddddate = rawDate.substring(rawDate.indexOf('-') + 1, rawDate.indexOf(' '));

        //提取 时-分
        String rawStart = turnoutManager.getStart().toString();
        this.start = rawStart.substring(rawStart.indexOf(' ') + 1, rawStart.lastIndexOf(':'));

        String rawEnd = turnoutManager.getEnd().toString();
        this.end = rawEnd.substring(rawEnd.indexOf(' ') + 1, rawEnd.lastIndexOf(':'));

        if (turnoutManager.getRlstart() != null) {
            String rawRlstart = turnoutManager.getRlstart().toString();
            this.rlstart = rawRlstart.substring(rawRlstart.indexOf(' ') + 1, rawRlstart.lastIndexOf(':'));
        } else {
            this.rlstart = "";
        }

        if (turnoutManager.getRlend() != null) {
            String rawRlend = turnoutManager.getRlend().toString();
            this.rlend = rawRlend.substring(rawRlend.indexOf(' ') + 1, rawRlend.lastIndexOf(':'));
        } else {
            this.rlend = "";
        }

        if (turnoutManager.getIsover() == null) {
            this.isover = "";
        } else if (turnoutManager.getIsover()) {
            this.isover = "是";
        } else {
            this.isover = "否";
        }


        if (turnoutManager.getIslate() == null) {
            this.islate = "";
        } else if (turnoutManager.getIslate()) {
            this.islate = "是";
        } else {
            this.islate = "否";
        }

        if (turnoutManager.getIsearly() == null) {
            this.isearly = "";
        } else if (turnoutManager.getIsearly()) {
            this.isearly = "是";
        } else {
            this.isearly = "否";
        }

        if (turnoutManager.getIsleave() == null) {
            this.isleave = "";
        } else if (turnoutManager.getIsleave()) {
            this.isleave = "是";
        } else {
            this.isleave = "否";
        }

        if (turnoutManager.getIstemporary() == null) {
            this.istemporary = "";
        } else if (turnoutManager.getIstemporary()) {
            this.istemporary = "是";
        } else {
            this.istemporary = "否";
        }

    }

    //将查询返回的TurnoutManager类型的list转换成本类类型的（便于送到前台）
    public static List<TurnoutManagerFront> transferToFront(List<TurnoutManager> list) {
        List<TurnoutManagerFront> newList = new ArrayList<>();
        for (TurnoutManager t : list) {
            TurnoutManagerFront newT = new TurnoutManagerFront(t);
            newList.add(newT);
        }
        return newList;
    }

    @Override
    public String toString() {
        return "TurnoutManagerFront{" +
                "arrno=" + arrno +
                ", empno='" + empno + '\'' +
                ", empname='" + empname + '\'' +
                ", depname='" + depname + '\'' +
                ", empposition='" + empposition + '\'' +
                ", checktypename='" + checktypename + '\'' +
                ", ddddate='" + ddddate + '\'' +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                ", rlstart='" + rlstart + '\'' +
                ", rlend='" + rlend + '\'' +
                ", isover='" + isover + '\'' +
                ", islate='" + islate + '\'' +
                ", isearly='" + isearly + '\'' +
                ", isleave='" + isleave + '\'' +
                ", istemporary='" + istemporary + '\'' +
                ", turnoutManager=" + turnoutManager +
                '}';
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

    public String getDdddate() {
        return ddddate;
    }

    public void setDdddate(String ddddate) {
        this.ddddate = ddddate;
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

    public String getIsover() {
        return isover;
    }

    public void setIsover(String isover) {
        this.isover = isover;
    }

    public String getIslate() {
        return islate;
    }

    public void setIslate(String islate) {
        this.islate = islate;
    }

    public String getIsearly() {
        return isearly;
    }

    public void setIsearly(String isearly) {
        this.isearly = isearly;
    }

    public String getIsleave() {
        return isleave;
    }

    public void setIsleave(String isleave) {
        this.isleave = isleave;
    }

    public TurnoutManager getTurnoutManager() {
        return turnoutManager;
    }

    public void setTurnoutManager(TurnoutManager turnoutManager) {
        this.turnoutManager = turnoutManager;
    }

    public String getIstemporary() {
        return istemporary;
    }

    public void setIstemporary(String istemporary) {
        this.istemporary = istemporary;
    }
}
