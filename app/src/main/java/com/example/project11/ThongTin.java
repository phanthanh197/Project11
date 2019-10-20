package com.example.project11;

public class ThongTin {
    String thongtin,ten;
    String thoigian;

    public ThongTin(String thongtin, String ten, String thoigian) {
        this.thongtin = thongtin;
        this.ten = ten;
        this.thoigian = thoigian;
    }

    public String getThongtin() {
        return thongtin;
    }

    public void setThongtin(String thongtin) {
        this.thongtin = thongtin;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getThoigian() {
        return thoigian;
    }

    public void setThoigian(String thoigian) {
        this.thoigian = thoigian;
    }
}
