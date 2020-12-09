package com.example.money_lover;

import androidx.annotation.NonNull;

public class Contact_Ngan_Hang {
    String ten;

    public Contact_Ngan_Hang(String ten) {
        this.ten = ten;
    }

    @NonNull
    @Override
    public String toString() {
        return ten;
    }
}
