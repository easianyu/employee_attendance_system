package com.edu.ustb.entities;

public class Company {

    private String cmpno;
    private String name;
    private Integer tolUsers;
    private String phone;
    private String address;

    public Company(String cmpno, String name, Integer tolUsers, String phone, String address) {
        this.cmpno = cmpno;
        this.name = name;
        this.tolUsers = tolUsers;
        this.phone = phone;
        this.address = address;
    }

    public Company() {
    }

    @Override
    public String toString() {
        return "Company{" +
                "cmpno='" + cmpno + '\'' +
                ", name='" + name + '\'' +
                ", tolUsers=" + tolUsers +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public String getCmpno() {
        return cmpno;
    }

    public void setCmpno(String cmpno) {
        this.cmpno = cmpno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTolUsers() {
        return tolUsers;
    }

    public void setTolUsers(Integer tolUsers) {
        this.tolUsers = tolUsers;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
