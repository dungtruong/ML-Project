package com.example.money_lover;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Them_Ngan_HangActivity extends AppCompatActivity {

    EditText edt;
    Button btn1,btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them__ngan__hang);
        edt=findViewById(R.id.edt_them_ngan_hang);
        btn1=findViewById(R.id.btn_them_ngan_hang2);
        btn2=findViewById(R.id.btn_huy_them_ngan_hang);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = edt.getText().toString();
                ContentValues contentValues = new ContentValues();
                contentValues.put("Ten",ten);
                //goi database de luu du lieu
                long kp = MainActivity.database.insert("Ngan_Hang",null,contentValues);
                if(kp>0) {
                    Toast.makeText(getApplicationContext(),"Thêm thành công",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Them_Ngan_HangActivity.this, Them_STKActivity.class);
                    startActivity(intent);
                }
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Them_Ngan_HangActivity.this, Them_STKActivity.class);
                startActivity(intent);
            }
        });
    }
}
