package com.example.money_lover;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter_STK extends BaseAdapter {
    Activity context;
    ArrayList<Contact_STK> list_stk;

    public Adapter_STK(Activity context, ArrayList<Contact_STK> list_stk) {
        this.context = context;
        this.list_stk = list_stk;
    }

    @Override
    public int getCount() {
        return list_stk.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row=layoutInflater.inflate(R.layout.show_ngan_hang,null);
        TextView txt1=row.findViewById(R.id.show_stt);
        TextView txt2=row.findViewById(R.id.show_so_tien_gui);
        TextView txt3=row.findViewById(R.id.show_kyhan);
        TextView txt4=row.findViewById(R.id.show_lai_suat);
        TextView txt5=row.findViewById(R.id.show_ngay_gui);


        Contact_STK contact_stk=list_stk.get(position);
        txt1.setText(contact_stk.id+"");
        txt2.setText(contact_stk.sotiengui+"");
        txt3.setText(contact_stk.kyhan);
        txt5.setText(contact_stk.ngaygui);
        if(contact_stk.laisuat.isEmpty()){
            txt4.setText(contact_stk.laisuatkhongkyhan);
        }else txt4.setText(contact_stk.laisuat);
        return row;
    }
}
