package com.edu.ustb.entities;

import java.sql.Timestamp;

public class LeaveApply {
    private Integer leaveno;
    private String empno;
    private String empname;
    private String depname;
    private Timestamp start;
    private Timestamp end;
    private String startStr;
    private String endStr;
    private String reason;
    private Integer isapply;
    private String isapplyStr;

    public LeaveApply() {
    }

    @Override
    public String toString() {
        return "LeaveApply{" +
                "leaveno=" + leaveno +
                ", empno='" + empno + '\'' +
                ", empname='" + empname + '\'' +
                ", depname='" + depname + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", startStr='" + startStr + '\'' +
                ", endStr='" + endStr + '\'' +
                ", reason='" + reason + '\'' +
                ", isapply=" + isapply +
                ", isapplyStr='" + isapplyStr + '\'' +
                '}';
    }

    public Integer getIsapply() {
        return isapply;
    }

    public void setIsapply(Integer isapply) {
        this.isapply = isapply;
        if (this.isapply == 0) {
            this.isapplyStr = "处理中";
        } else if (this.isapply == 1) {
            this.isapplyStr = "已批准";
        } else if (this.isapply == 2) {
            this.isapplyStr = "被驳回";
        } else if (this.isapply == 3) {
            this.isapplyStr = "销假中";
        } else {
            this.isapplyStr = "已销假";
        }
    }

    public String getIsapplyStr() {
        return isapplyStr;
    }

    public void setIsapplyStr(String isapplyStr) {
        this.isapplyStr = isapplyStr;
    }

    public Integer getLeaveno() {
        return leaveno;
    }

    public void setLeaveno(Integer leaveno) {
        this.leaveno = leaveno;
    }

    public String getDepname() {
        return depname;
    }

    public void setDepname(String depname) {
        this.depname = depname;
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

    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
        String str = start.toString();
        //提取时间中的 月日 时间， 用与前台显示
        this.startStr = str.substring(str.indexOf('-') + 1, str.lastIndexOf(':'));
    }

    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
        String str = end.toString();
        //提取时间中的 月日 时间， 用与前台显示
        this.endStr = str.substring(str.indexOf('-') + 1, str.lastIndexOf(':'));
    }

    public String getStartStr() {
        return startStr;
    }

    public void setStartStr(String startStr) {
        this.startStr = startStr;
    }

    public String getEndStr() {
        return endStr;
    }

    public void setEndStr(String endStr) {
        this.endStr = endStr;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
