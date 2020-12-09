package com.example.money_lover;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Update_STKActivity extends AppCompatActivity {

    Button btn1, btn2, btn3;
    EditText edt2;
    Calendar calendar, calendar2;
    SimpleDateFormat simpleDateFormat;
    Spinner spinner, spinner2, spinner3, spinner4;
    TextView txt1, txt2, edt1;
    int id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__stk);
        addControls();
        chonNganHang();
        chonKyHan();
        chonTraLai();
        chonKhiHanDen();
        initUI();
        addEvents();

    }

    private void initUI() {
        Intent intent = getIntent();
        id = intent.getIntExtra("ID", 0);
        Cursor cursor = MainActivity.database.rawQuery("SELECT * FROM STK WHERE ID=?", new String[]{id + ""});
        cursor.moveToNext();
        String nganhang = cursor.getString(1);
        String ngaygui = cursor.getString(2);
        int sotiengui = cursor.getInt(3);
        String kyhan = cursor.getString(4);
        String laisuat = cursor.getString(5);
        String laisuatkhongkyhan = cursor.getString(6);
        String tralai = cursor.getString(7);
        String khidenhan = cursor.getString(8);

        edt1.setText(ngaygui);
        spinner.setSelection(getIndex(spinner, nganhang));
        edt2.setText(sotiengui + "");
        spinner2.setSelection(getIndex(spinner2, kyhan));
        txt1.setText(laisuat);
        txt2.setText(laisuatkhongkyhan);
        spinner3.setSelection(getIndex(spinner3, tralai));
        spinner4.setSelection(getIndex(spinner4, khidenhan));
    }

    private int getIndex(Spinner spin, String myString) {
        for (int i = 0; i < spin.getCount(); i++) {
            if (spin.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                return i;
            }
        }
        return 0;
    }

    private void updateSTK() {
        String ngaygui = edt1.getText().toString();
        String nganhang = spinner.getSelectedItem().toString();
        String sotiengui = edt2.getText().toString();
        String kyhan = spinner2.getSelectedItem().toString();
        String laisuat = txt1.getText().toString();
        String laisuatkhongkyhan = txt2.getText().toString();
        String tralai = spinner3.getSelectedItem().toString();
        String khidenhan = spinner4.getSelectedItem().toString();


        if (ngaygui.isEmpty() || nganhang.isEmpty() || sotiengui.isEmpty() || kyhan.isEmpty() || tralai.isEmpty() || khidenhan.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Không được để trống", Toast.LENGTH_LONG).show();
        } else {
            int so = Integer.parseInt(sotiengui);
            if (so < 1000000) {
                Toast.makeText(getApplicationContext(), "Tối thiểu là 1.000.000đ", Toast.LENGTH_LONG).show();
            } else {
                ContentValues contentValues = new ContentValues();
                contentValues.put("Ngay_Gui", ngaygui);
                contentValues.put("Ngan_Hang", nganhang);
                contentValues.put("So_Tien_Gui", so);
                contentValues.put("Ky_Han", kyhan);
                contentValues.put("Lai_Suat", laisuat);
                contentValues.put("Lai_Suat_Khong_Ky_Han", laisuatkhongkyhan);
                contentValues.put("Tra_Lai", tralai);
                contentValues.put("Khi_Den_Han", khidenhan);

                MainActivity.database = Database.initDatabase(this, MainActivity.DATABASE_NAME);
                long kq = MainActivity.database.update("STK", contentValues, "id=?", new String[]{id + ""});
                if (kq > 0) {
                    Toast.makeText(getApplicationContext(), "Sửa Sổ Tiết Kiệm thành công", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Update_STKActivity.this, STKActivity.class);
                    startActivity(intent);
                } else
                    Toast.makeText(getApplicationContext(), "Sửa Sổ Tiết Kiệm thất bại", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void chonKhiHanDen() {
        final List<String> list_khi_den_han = new ArrayList<>();
        list_khi_den_han.add("Chọn khi hạn đến");
        list_khi_den_han.add("Tái tục gốc và lãi");
        list_khi_den_han.add("Tái tục gốc");
        list_khi_den_han.add("Tất toán sổ");

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list_khi_den_han);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner4.setAdapter(adapter);
        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void chonTraLai() {
        List<String> list_tra_lai = new ArrayList<>();
        list_tra_lai.add("Chọn tra Lai");
        list_tra_lai.add("Cuối kỳ");
        list_tra_lai.add("Đầu kỳ");
        list_tra_lai.add("Định kỳ hàng tháng");

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list_tra_lai);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner3.setAdapter(adapter);
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void chonKyHan() {
        List<String> list = new ArrayList<>();
        list.add("Chọn kỳ hạn");
        list.add("Không kỳ hạn");
        list.add("1 tháng");
        list.add("3 tháng");
        list.add("6 tháng");
        list.add("12 tháng");

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner2.setAdapter(adapter);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spinner2.getSelectedItem().toString().equalsIgnoreCase("Không kỳ hạn")) {
                    txt1.setText("");
                    txt2.setText("0.05");
                } else if (spinner2.getSelectedItem().toString().equalsIgnoreCase("1 tháng")) {
                    txt2.setText("");
                    txt1.setText("1");
                } else if (spinner2.getSelectedItem().toString().equalsIgnoreCase("3 tháng")) {
                    txt2.setText("");
                    txt1.setText("3.5");
                } else if (spinner2.getSelectedItem().toString().equalsIgnoreCase("6 tháng")) {
                    txt2.setText("");
                    txt1.setText("6.8");
                } else if (spinner2.getSelectedItem().toString().equalsIgnoreCase("12 tháng")) {
                    txt2.setText("");
                    txt1.setText("7.5");
                } else {
                    txt2.setText("");
                    txt1.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void chonNganHang() {
        final List<Contact_Ngan_Hang> list_ngan_hang = new ArrayList<>();
        MainActivity.database = Database.initDatabase(this, MainActivity.DATABASE_NAME);
        Cursor cursor = MainActivity.database.rawQuery("SELECT * FROM Ngan_hang", null);
        list_ngan_hang.clear();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            String ten = cursor.getString(1);
            list_ngan_hang.add(new Contact_Ngan_Hang(ten));
        }
        ArrayAdapter<Contact_Ngan_Hang> arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list_ngan_hang);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void addEvents() {
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Update_STKActivity.this, Them_Ngan_HangActivity.class);
                startActivity(intent);
            }
        });
        edt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ngayGui();
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSTK();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Update_STKActivity.this, STKActivity.class);
                startActivity(intent);
            }
        });
    }


    private void addControls() {
        spinner3 = findViewById(R.id.spinner_sua_tra_lai);
        spinner4 = findViewById(R.id.spinner_sua_khi_den_han);
        txt1 = findViewById(R.id.edt_sua_lai_suat);
        txt2 = findViewById(R.id.edt_sua_lai_suat_k_ky_han);
        edt2 = findViewById(R.id.edt_sua_so_tien_gui);
        spinner2 = findViewById(R.id.spinner_sua_ky_han);
        btn3 = findViewById(R.id.btn_sua_them_ngan_hang);
        spinner = findViewById(R.id.spinner_sua_ngan_hang);
        btn1 = findViewById(R.id.btn_sua_tk);
        btn2 = findViewById(R.id.btn_huy_sua_tk);
        edt1 = findViewById(R.id.edt_sua_ngay_them_tk);
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    }

    private void ngayGui() {
        calendar2 = Calendar.getInstance();
        calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int dayOfMonth, int month, int year) {
                calendar.set(dayOfMonth, month, year);
                if (calendar.getTimeInMillis() <= calendar2.getTimeInMillis()) {
                    edt1.setText(simpleDateFormat.format(calendar.getTime()));
                } else {
                    Toast.makeText(getApplicationContext(), "Ngày gữi phải nhỏ hơn hoặc bằng ngày hiện tại", Toast.LENGTH_LONG).show();
                }
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }
}
