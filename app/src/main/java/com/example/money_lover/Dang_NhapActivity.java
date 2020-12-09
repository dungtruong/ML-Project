package com.example.money_lover;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Dang_NhapActivity extends AppCompatActivity {

    EditText edt1,edt2;
    Button btn1,btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang__nhap);
        addControls();
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=edt1.getText().toString();
                String pass=edt2.getText().toString();
                if(email.isEmpty()||pass.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Khong duoc de trong",Toast.LENGTH_LONG).show();
                }else {
                        Cursor cursor = MainActivity.database.rawQuery("SELECT * FROM User", null);
                        while (cursor.moveToNext()) {
                            String emailDB = cursor.getString(1);
                            if (email.equalsIgnoreCase(emailDB)) {
                                String passDB = cursor.getString(2);
                                if (pass.equalsIgnoreCase(passDB)) {
                                    Intent intent = new Intent(Dang_NhapActivity.this, STKActivity.class);
                                    intent.putExtra("ID", cursor.getInt(0));
                                    startActivity(intent);
                                    Toast.makeText(getApplicationContext(), "Dang nhap thanh cong", Toast.LENGTH_LONG).show();
                                } else Toast.makeText(getApplicationContext(), "Sai Email hoac Mat Khau", Toast.LENGTH_LONG).show();
                            }

                        }
                    }
                }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dang_NhapActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addControls() {
        edt1=findViewById(R.id.emailDangNhap);
        edt2=findViewById(R.id.passwordDangNhap);
        btn1=findViewById(R.id.btn_DangNhap_dn);
        btn2=findViewById(R.id.btn_huy_dn);
    }

    public void ShowHidePass(View view) {
        if(view.getId()==R.id.show_password){
            if(edt2.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                edt2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }else {
                edt2.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
    }
}
