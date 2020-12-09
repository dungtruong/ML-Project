package com.example.money_lover;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class Dang_KyActivity extends AppCompatActivity {

    EditText edt1, edt2;
    Button btn1, btn2;

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=!])" +   //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{2,}" +               //at least 8 characters
                    "$");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang__ky);
        addControls();
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edt1.getText().toString();
                String password = edt2.getText().toString();
                String comfirm_Password="0";
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Khong duoc de trong", Toast.LENGTH_LONG).show();
                } else {
                    if (password.length() < 8) {
                        Toast.makeText(getApplicationContext(), "Mật khẩu tối thiểu có 8 ký tự", Toast.LENGTH_LONG).show();
                    } else {
                        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                            Toast.makeText(getApplicationContext(),"Nhập sai định dạng Email",Toast.LENGTH_LONG).show();
                        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
                            Toast.makeText(getApplicationContext(),"Nhập sai định dạng Mật khẩu gồm ít nhất 1 ký tự đặt biệt, 1 chữ số, 1 chữ viết hoa, 1 chữ viết thường, không bao gồm dấu cách!",Toast.LENGTH_LONG).show();
                        } else {
                            ContentValues contentValues = new ContentValues();
                            contentValues.put("Email", email);
                            contentValues.put("Password", password);
                            contentValues.put("Comfirm_Password", comfirm_Password);
                            long kp = MainActivity.database.insert("User", null, contentValues);
                            if (kp > 0) {
                                Toast.makeText(getApplicationContext(), "Dang ky thanh cong", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(Dang_KyActivity.this, Dang_NhapActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "Dang ky that bai", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dang_KyActivity.this, Dang_NhapActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addControls() {
        edt1 = findViewById(R.id.edt_email_dang_ky);
        edt2 = findViewById(R.id.edt_password_dang_ky);
        btn1 = findViewById(R.id.btn_dang_ky_dk);
        btn2 = findViewById(R.id.btn_dang_nhap_dk);
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
