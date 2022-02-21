package com.edu.ustb.entities;


public class DepartmentInfo {
    private String depname; // 部门名
    private String empname; // 部门主管名
    private String depphone; // 部门电话
    private Integer toltalusers; // 部门总员工数

    public DepartmentInfo(String depname, String empname, String depphone, Integer toltalusers) {
        this.depname = depname;
        this.empname = empname;
        this.depphone = depphone;
        this.toltalusers = toltalusers;
    }

    public DepartmentInfo() {
    }

    @Override
    public String toString() {
        return "DepartmentInfo{" +
                "depname='" + depname + '\'' +
                ", empname='" + empname + '\'' +
                ", depphone='" + depphone + '\'' +
                ", toltalusers=" + toltalusers +
                '}';
    }

    public String getDepname() {
        return depname;
    }

    public void setDepname(String depname) {
        this.depname = depname;
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname;
    }

    public String getDepphone() {
        return depphone;
    }

    public void setDepphone(String depphone) {
        this.depphone = depphone;
    }

    public Integer getToltalusers() {
        return toltalusers;
    }

    public void setToltalusers(Integer toltalusers) {
        this.toltalusers = toltalusers;
    }
}
