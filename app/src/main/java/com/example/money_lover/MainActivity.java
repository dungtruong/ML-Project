package com.example.money_lover;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public static String DATABASE_NAME="MoneyLover.sqlite";
    public static SQLiteDatabase database;
    Button btn1,btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database=Database.initDatabase(this,DATABASE_NAME);
        btn1=findViewById(R.id.btn_ld_sd);
        btn2=findViewById(R.id.btn_dsd);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Dang_KyActivity.class);
                startActivity(intent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Dang_NhapActivity.class);
                startActivity(intent);
            }
        });
    }
}
