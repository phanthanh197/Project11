package com.example.project11.setget;

public class ThongTin {
    String thongtin,ten;
    String photopath,thoigian;
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getThongtin() {
        return thongtin;
    }

    public void setThongtin(String thongtin) {
        this.thongtin = thongtin;
    }

    public String getPhotopath() {
        return photopath;
    }

    public String getThoigian() {
        return thoigian;
    }

    public void setThoigian(String thoigian) {
        this.thoigian = thoigian;
    }

    public void setPhotopath(String photopath) {
        this.photopath = photopath;
    }

    public ThongTin(int id, String ten, String thongtin, String photopath,String thoigian) {
        this.thongtin = thongtin;
        this.photopath= photopath;
        this.ten = ten;
        this.id = id;
        this.thoigian = thoigian;
    }
}
