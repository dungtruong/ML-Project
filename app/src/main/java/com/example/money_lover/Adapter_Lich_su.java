package com.example.money_lover;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter_Lich_su extends BaseAdapter {
    Activity context;
    ArrayList<Contact_Lich_Su> list_ls;

    public Adapter_Lich_su(Activity context, ArrayList<Contact_Lich_Su> list_ls) {
        this.context = context;
        this.list_ls = list_ls;
    }

    @Override
    public int getCount() {
        return list_ls.size();
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
        TextView txt1=row.findViewById(R.id.show_stt_ls);
        TextView txt2=row.findViewById(R.id.show_so_tien_gui_ls);
        TextView txt3=row.findViewById(R.id.show_kyhan_ls);
        TextView txt4=row.findViewById(R.id.show_lai_suat_ls);
        TextView txt5=row.findViewById(R.id.show_ngay_gui_ls);


        Contact_Lich_Su contact_lich_su=list_ls.get(position);
        txt1.setText(contact_lich_su.id+"");
        txt2.setText(contact_lich_su.sotiengui+"");
        txt3.setText(contact_lich_su.kyhan);
        txt5.setText(contact_lich_su.ngaygui);
        if(contact_lich_su.laisuat.isEmpty()){
            txt4.setText(contact_lich_su.laisuatkhongkyhan);
        }else txt4.setText(contact_lich_su.laisuat);
        return row;
    }
}
