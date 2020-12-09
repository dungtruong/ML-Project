package com.example.money_lover;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Rut_Mot_PhanActivity extends AppCompatActivity {

    TextView txt1, txt2, txt3;
    EditText edt;
    Button btn1, btn2;
    int id = -1, tongtien = 0,tongtienlai=0;
    float tienlai,lai;
    Calendar ngayHT, ngaygui1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rut__mot__phan);
        addControls();
        readDB();
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String ruttien = edt.getText().toString();
                String kyhan = txt2.getText().toString();
                final String sotiengui = txt3.getText().toString();
                final int checkngay = (int) ((ngayHT.getTimeInMillis() - ngaygui1.getTimeInMillis()) / (1000 * 60 * 60 * 24));

                if (ruttien.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Không được để trống", Toast.LENGTH_LONG).show();
                } else {
                    int sotienrut = Integer.parseInt(ruttien);
                    int sotienHT = Integer.parseInt(sotiengui);
                    if (sotienrut>sotienHT) {
                        Toast.makeText(getApplicationContext(), "Số tiền rút phải nhỏ hơn hoặc bằng số tiền gốc", Toast.LENGTH_LONG).show();
                    } else if (kyhan.equalsIgnoreCase("1 tháng") && checkngay < 30 || kyhan.equalsIgnoreCase("3 tháng") && checkngay < 90 ||
                            kyhan.equalsIgnoreCase("6 tháng") && checkngay < 180 || kyhan.equalsIgnoreCase("12 tháng") && checkngay < 360) {
                        thongBao();
                    }else if(kyhan.equalsIgnoreCase("Không kỳ hạn")||checkngay<=15){
                        Toast.makeText(getApplicationContext(),"Rút tiền không kỳ hạn phải trên 15 ngày",Toast.LENGTH_LONG).show();
                    }else {
                        tongtien=sotienHT-sotienrut;
                        tienlai=sotienrut*(lai/360)*checkngay;
                        int solai=Math.round(tienlai);
                        tongtienlai=solai+sotienrut;
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("So_Tien_Gui", tongtien);
                        long kq = MainActivity.database.update("STK", contentValues, "ID=?", new String[]{id + ""});
                        if (kq > 0) {
                            Toast.makeText(getApplicationContext(), "Số tiền bạn rút tính luôn lãi là: " + tongtienlai, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Rut_Mot_PhanActivity.this, STKActivity.class);
                            startActivity(intent);
                        }
                        else Toast.makeText(getApplicationContext(), "Rút tiền thất bại", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Rut_Mot_PhanActivity.this, STKActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addControls() {
        txt1 = findViewById(R.id.txt_ma_so_rut);
        txt2 = findViewById(R.id.txt_ky_han_rut);
        txt3 = findViewById(R.id.txt_so_tien_rut);
        edt = findViewById(R.id.edt_rut_tien);
        btn1 = findViewById(R.id.btn_rut_tien);
        btn2 = findViewById(R.id.btn_huy_rut_tien);
    }

    private void thongBao(){
        final String ruttien = edt.getText().toString();
        final String sotiengui = txt3.getText().toString();
        final int checkngay=(int) ((ngayHT.getTimeInMillis() - ngaygui1.getTimeInMillis()) / (1000 * 60 * 60 * 24));
        AlertDialog.Builder builder = new AlertDialog.Builder(Rut_Mot_PhanActivity.this);
        builder.setTitle("Thông báo");
        builder.setIcon(R.drawable.bee);
        builder.setMessage("Sổ tiết kiệm có mã số" + id + ". Kỳ hạn mới được " + checkngay + "ngày . Số tiền được rút trước kì hạn sẽ được tính theo lãi xuất không kì hạn (0.05 %/năm). Bạn có muốn tiếp tục");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tongtien = Integer.parseInt(sotiengui) - Integer.parseInt(ruttien);
                tienlai = Integer.parseInt(ruttien) * ((float)0.05 /360) *checkngay;
                int solai= Math.round(tienlai);
                tongtienlai=solai+Integer.parseInt(ruttien);
                ContentValues contentValues = new ContentValues();
                contentValues.put("So_Tien_Gui", tongtien);
                long kq = MainActivity.database.update("STK", contentValues, "ID=?", new String[]{id + ""});
                if (kq > 0) {
                    Toast.makeText(getApplicationContext(), "Số tiền bạn rút tính luôn lãi là: " + tongtienlai, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Rut_Mot_PhanActivity.this, STKActivity.class);
                    startActivity(intent);
                }
                else Toast.makeText(getApplicationContext(), "Rút tiền thất bại", Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Rut_Mot_PhanActivity.this, STKActivity.class);
                startActivity(intent);
            }
        });
        builder.show();
    }

    private void readDB() {
        Intent intent = getIntent();
        id = intent.getIntExtra("ID", 0);
        Cursor cursor = MainActivity.database.rawQuery("SELECT * FROM STK WHERE ID=?", new String[]{id + ""});
        cursor.moveToNext();
        int id = cursor.getInt(0);
        int sotiengui = cursor.getInt(3);
        String ngaygui = cursor.getString(2);
        String kyhan = cursor.getString(4);
        String laisuat=cursor.getString(5);
        String laisuatkhongkyhan=cursor.getString(6);

        txt1.setText("Mã sổ sổ tiết kiệm: " + id);
        txt2.setText(kyhan);
        txt3.setText(sotiengui + "");
        ngayHT = Calendar.getInstance();
        ngaygui1 = Calendar.getInstance();
        String[] ngayguitinh = ngaygui.split("/");
        int ngayguii = Integer.parseInt(ngayguitinh[2]);
        int thanggui = Integer.parseInt(ngayguitinh[1]);
        thanggui -= 1;
        int namgui = Integer.parseInt(ngayguitinh[0]);
        ngaygui1.set(ngayguii, thanggui, namgui);
        if(laisuat.isEmpty()){
            lai=Float.parseFloat(laisuatkhongkyhan);
        }else lai=Float.parseFloat(laisuat);
    }
}