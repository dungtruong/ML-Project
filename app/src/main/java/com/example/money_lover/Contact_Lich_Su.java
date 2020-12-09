package com.example.money_lover;

public class Contact_Lich_Su {
    int id;
    int sotiengui;
    String kyhan;
    String laisuat;
    String laisuatkhongkyhan;
    String ngaygui;

    public Contact_Lich_Su(int id, int sotiengui, String kyhan, String laisuat, String laisuatkhongkyhan, String ngaygui) {
        this.id = id;
        this.sotiengui = sotiengui;
        this.kyhan = kyhan;
        this.laisuat = laisuat;
        this.laisuatkhongkyhan = laisuatkhongkyhan;
        this.ngaygui = ngaygui;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getNgaygui() {
        return ngaygui;
    }

    public void setNgaygui(String ngaygui) {
        this.ngaygui = ngaygui;
    }
}
