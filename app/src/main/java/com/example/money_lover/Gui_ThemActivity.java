package com.example.money_lover;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class Gui_ThemActivity extends AppCompatActivity {

    TextView txt1, txt2, txt3;
    EditText edt;
    Button btn1, btn2;
    int id = -1, tongtien = 0;
    Calendar ngayHT, ngayguitinh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gui__them);
        txt1 = findViewById(R.id.txt_ma_so);
        txt2 = findViewById(R.id.txt_ky_han);
        txt3 = findViewById(R.id.txt_so_tien_ht);
        edt = findViewById(R.id.edt_gui_them);
        btn1 = findViewById(R.id.btn_gui_tien);
        btn2 = findViewById(R.id.btn_huy_gui_tien);
        readDB();


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String guithem = edt.getText().toString();
                String kyhan = txt2.getText().toString();
                String sotiengui = txt3.getText().toString();
                int checkngay = (int) ((ngayHT.getTimeInMillis() - ngayguitinh.getTimeInMillis()) / (1000 * 60 * 60 * 24));
                if (guithem.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Không được để trống", Toast.LENGTH_LONG).show();
                } else {
                    int sotien = Integer.parseInt(sotiengui);
                    int so = Integer.parseInt(guithem);
                    if (so < 100000) {
                        Toast.makeText(getApplicationContext(), "Số tiền gữi thêm tối thiểu 100000đ", Toast.LENGTH_LONG).show();
                    } else {
                        if (kyhan.equalsIgnoreCase("Không kỳ hạn") || kyhan.equalsIgnoreCase("1 tháng") && checkngay > 30 || kyhan.equalsIgnoreCase("3 tháng") && checkngay > 90 ||
                                kyhan.equalsIgnoreCase("6 tháng") && checkngay > 180 || kyhan.equalsIgnoreCase("12 tháng") && checkngay > 360) {
                            tongtien = so + sotien;
                            ContentValues contentValues = new ContentValues();
                            contentValues.put("So_Tien_Gui", tongtien);
                            long kq = MainActivity.database.update("STK", contentValues, "ID=?", new String[]{id + ""});
                            if (kq > 0) {
                                Toast.makeText(getApplicationContext(), "Gữi thêm tiền thành công", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(Gui_ThemActivity.this, STKActivity.class);
                                startActivity(intent);
                            } else
                                Toast.makeText(getApplicationContext(), "Gữi thêm tiền thất bại", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Không thể gữi thêm tiền vì kỳ hạn chưa hết", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Gui_ThemActivity.this, STKActivity.class);
                startActivity(intent);
            }
        });
    }

    private void readDB() {
        Intent intent = getIntent();
        id = intent.getIntExtra("ID", 0);
        Cursor cursor = MainActivity.database.rawQuery("SELECT * FROM STK WHERE ID=?", new String[]{id + ""});
        cursor.moveToNext();
        final int id = cursor.getInt(0);
        int sotiengui = cursor.getInt(3);
        String ngaygui = cursor.getString(2);
        String kyhan = cursor.getString(4);

        txt1.setText("Mã sổ sổ tiết kiệm: " + id);
        txt2.setText(kyhan);
        txt3.setText(sotiengui + "");
        ngayHT = Calendar.getInstance();
        ngayguitinh = Calendar.getInstance();
        String[] ngaygui1 = ngaygui.split("/");
        int ngayguii = Integer.parseInt(ngaygui1[2]);
        int thanggui = Integer.parseInt(ngaygui1[1]);
        thanggui -= 1;
        int namgui = Integer.parseInt(ngaygui1[0]);
        ngayguitinh.set(ngayguii, thanggui, namgui);
    }
}
