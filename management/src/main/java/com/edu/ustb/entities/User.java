package com.edu.ustb.entities;


import java.util.Objects;

public class User {
    private String empno;
    private String empname;
    private String emppassword;
    private String empposno;
    private String empemail;
    private String empphoneno;
    private String depno;
    private Boolean isnotify;

    public User(String empno, String empname, String emppassword, String empposno, String empemail, String empphoneno, String depno, Boolean isnotify) {
        this.empno = empno;
        this.empname = empname;
        this.emppassword = emppassword;
        this.empposno = empposno;
        this.empemail = empemail;
        this.empphoneno = empphoneno;
        this.depno = depno;
        this.isnotify = isnotify;
    }

    public User() {
    }

    public void updateInfo(User newUser) {
        if (newUser.getEmpname() != null && newUser.getEmpname().length() > 1) {
            this.setEmpname(newUser.getEmpname());
        }

        if (newUser.getEmppassword() != null && newUser.getEmppassword().length() > 1) {
            this.setEmppassword(newUser.getEmppassword());
        }

        if (newUser.getEmpposno() != null && newUser.getEmpposno().length() > 1) {
            this.setEmpposno(newUser.getEmpposno());
        }

        if (newUser.getEmpemail() != null && newUser.getEmpemail().length() > 1) {
            this.setEmpemail(newUser.getEmpemail());
        }

        if (newUser.getEmpphoneno() != null && newUser.getEmpphoneno().length() > 1) {
            this.setEmpphoneno(newUser.getEmpphoneno());
        }

        if (newUser.getDepno() != null && newUser.getDepno().length() > 1) {
            this.setDepno(newUser.getDepno());
        }
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

    public String getEmppassword() {
        return emppassword;
    }

    public void setEmppassword(String emppassword) {
        this.emppassword = emppassword;
    }

    public String getEmpposno() {
        return empposno;
    }

    public void setEmpposno(String empposno) {
        this.empposno = empposno;
    }

    public String getEmpemail() {
        return empemail;
    }

    public void setEmpemail(String empemail) {
        this.empemail = empemail;
    }

    public String getEmpphoneno() {
        return empphoneno;
    }

    public void setEmpphoneno(String empphoneno) {
        this.empphoneno = empphoneno;
    }

    public String getDepno() {
        return depno;
    }

    public void setDepno(String depno) {
        this.depno = depno;
    }

    public Boolean getIsnotify() {
        return isnotify;
    }

    public void setIsnotify(Boolean isnotify) {
        this.isnotify = isnotify;
    }

    @Override
    public String toString() {
        return "User{" +
                "empno='" + empno + '\'' +
                ", empname='" + empname + '\'' +
                ", emppassword='" + emppassword + '\'' +
                ", empposno='" + empposno + '\'' +
                ", empemail='" + empemail + '\'' +
                ", empphoneno='" + empphoneno + '\'' +
                ", depno='" + depno + '\'' +
                ", isnotify=" + isnotify +
                '}';
    }
}
