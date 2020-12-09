package com.example.money_lover;


public class Contact_STK {
    int id;
    String nganhang;
    String ngaygui;
    int sotiengui;
    String kyhan;
    String laisuat;
    String laisuatkhongkyhan;
    String tralai;
    String khidenhan;

    public Contact_STK(int id, String nganhang, String ngaygui, int sotiengui, String kyhan, String laisuat, String laisuatkhongkyhan, String tralai, String khidenhan) {
        this.id = id;
        this.nganhang = nganhang;
        this.ngaygui = ngaygui;
        this.sotiengui = sotiengui;
        this.kyhan = kyhan;
        this.laisuat = laisuat;
        this.laisuatkhongkyhan = laisuatkhongkyhan;
        this.tralai = tralai;
        this.khidenhan = khidenhan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNganhang() {
        return nganhang;
    }

    public void setNganhang(String nganhang) {
        this.nganhang = nganhang;
    }

    public String getNgaygui() {
        return ngaygui;
    }

    public void setNgaygui(String ngaygui) {
        this.ngaygui = ngaygui;
    }

    public int getSotiengui() {
        return sotiengui;
    }

    public void setSotiengui(int sotiengui) {
        this.sotiengui = sotiengui;
    }

    public String getKyhan() {
        return kyhan;
    }

    public void setKyhan(String kyhan) {
        this.kyhan = kyhan;
    }

    public String getLaisuat() {
        return laisuat;
    }

    public void setLaisuat(String laisuat) {
        this.laisuat = laisuat;
    }

    public String getLaisuatkhongkyhan() {
        return laisuatkhongkyhan;
    }

    public void setLaisuatkhongkyhan(String laisuatkhongkyhan) {
        this.laisuatkhongkyhan = laisuatkhongkyhan;
    }

    public String getTralai() {
        return tralai;
    }

    public void setTralai(String tralai) {
        this.tralai = tralai;
    }

    public String getKhidenhan() {
        return khidenhan;
    }

    public void setKhidenhan(String khidenhan) {
        this.khidenhan = khidenhan;
    }
}
