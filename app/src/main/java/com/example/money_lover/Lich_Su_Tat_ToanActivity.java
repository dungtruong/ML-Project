package com.example.money_lover;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;

public class Lich_Su_Tat_ToanActivity extends AppCompatActivity {

    Button btn;
    ListView listView;
    Adapter_Lich_su adapter_lich_su;
    ArrayList<Contact_Lich_Su> list_ls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich__su__tat__toan);
        listView = findViewById(R.id.listView_show_lich_su);
        list_ls = new ArrayList<>();
        adapter_lich_su = new Adapter_Lich_su(this, list_ls);
        listView.setAdapter(adapter_lich_su);
        btn = findViewById(R.id.btn_huy_lich_su);
        readDB();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Lich_Su_Tat_ToanActivity.this, STKActivity.class);
                startActivity(intent);
            }
        });
    }

    private void readDB() {

    }
}
