package com.example.money_lover;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class STKActivity extends AppCompatActivity {

    ArrayList<Contact_STK> list_stk;
    Button btn,btn2;
    ListView listView;
    Adapter_STK adapter_stk;
    Spinner spinner1;
    ArrayList<Contact_Ngan_Hang> list_ngan_hang;
    TextView txt;
    int tongtien = 0,id=-1;
    float tienlai,lai;
    Calendar ngayHT,ngaygui1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stk);
        addControls();

        registerForContextMenu(listView);
        chonNganHang();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(STKActivity.this, Them_STKActivity.class);
                startActivity(intent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(STKActivity.this, Lich_Su_Tat_ToanActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_option,menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo= (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Contact_STK contact_stk=list_stk.get(adapterContextMenuInfo.position);
        switch (item.getItemId()){
            case R.id.menu_sua:
                final Intent intent = new Intent(STKActivity.this,Update_STKActivity.class);
                intent.putExtra("ID", contact_stk.id);
                startActivity(intent);
                break;
            case R.id.menu_gui_them:
                Intent intent1 = new Intent(STKActivity.this,Gui_ThemActivity.class);
                intent1.putExtra("ID", contact_stk.id);
                startActivity(intent1);
                break;
            case R.id.menu_rut:
                Intent intent2 = new Intent(STKActivity.this,Rut_Mot_PhanActivity.class);
                intent2.putExtra("ID", contact_stk.id);
                startActivity(intent2);
                break;
            case R.id.menu_tat_toan:
                final AlertDialog.Builder builder=new AlertDialog.Builder(STKActivity.this);
                builder.setTitle("Tất toán");
                builder.setMessage("Bạn muốn tất toán");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int sotiengui=contact_stk.sotiengui;
                        String kyhan=contact_stk.kyhan;
                        if(contact_stk.laisuat.isEmpty()){
                            lai=Float.parseFloat(contact_stk.laisuatkhongkyhan);
                        }else lai=Float.parseFloat(contact_stk.laisuat);
                        ngayHT=Calendar.getInstance();
                        ngaygui1=Calendar.getInstance();
                        String[] ngayguitinh=contact_stk.ngaygui.split("/");
                        int ngayguii=Integer.parseInt(ngayguitinh[2]);
                        int thanggui=Integer.parseInt(ngayguitinh[1]);
                        thanggui-=1;
                        int namgui=Integer.parseInt(ngayguitinh[0]);
                        ngaygui1.set(ngayguii,thanggui,namgui);
                        int checkngay= (int) ((ngayHT.getTimeInMillis()-ngaygui1.getTimeInMillis())/(1000*60*60*24));

                        if(kyhan.equalsIgnoreCase("1 tháng") && checkngay < 30 || kyhan.equalsIgnoreCase("3 tháng") && checkngay < 90 ||
                                kyhan.equalsIgnoreCase("6 tháng") && checkngay < 180 || kyhan.equalsIgnoreCase("12 tháng") && checkngay < 360){
                            tienlai=sotiengui*((float) 0.05/360)*checkngay;
                            int so=Math.round(tienlai);
                            tongtien=sotiengui+so;
                            Toast.makeText(getApplicationContext(),"Bạn đã tất toán trước kỳ hạn, số tiền là: "+tongtien,Toast.LENGTH_LONG).show();
                        }else {
                            tienlai=sotiengui*(lai/360)*checkngay;
                            int so=Math.round(tienlai);
                            tongtien=sotiengui+so;
                            Toast.makeText(getApplicationContext(),"Bạn đã tất số thành công, số tiền là: "+tongtien,Toast.LENGTH_LONG).show();
                        }

                        deleteSTK(contact_stk.id);
                        Intent intent = new Intent(STKActivity.this, Lich_Su_Tat_ToanActivity.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
        }
        return super.onContextItemSelected(item);
    }

    private void chonNganHang() {
        MainActivity.database=Database.initDatabase(this,MainActivity.DATABASE_NAME);
        Cursor cursor=MainActivity.database.rawQuery("SELECT * FROM Ngan_Hang",null);
        list_ngan_hang.clear();
        for (int i =0;i<cursor.getCount();i++){
            cursor.moveToPosition(i);
            String ten=cursor.getString(1);
            list_ngan_hang.add(new Contact_Ngan_Hang(ten));
        }
        ArrayAdapter<Contact_Ngan_Hang> arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,list_ngan_hang);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner1.setAdapter(arrayAdapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long y) {
                Cursor cursor = MainActivity.database.rawQuery("SELECT * FROM STK WHERE Ngan_Hang =?",new String[]{spinner1.getSelectedItem().toString()});
                list_stk.clear();
                int tongtien=0;
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToPosition(i);
                    int id = cursor.getInt(0);
                    String nganhang = cursor.getString(1);
                    String ngaygui=cursor.getString(2);
                    int sotiengui=cursor.getInt(3);
                    String kyhan=cursor.getString(4);
                    String laisuat=cursor.getString(5);
                    String laisuatkhongkyhan=cursor.getString(6);
                    String tralai=cursor.getString(7);
                    String khidenhan=cursor.getString(8);
                    list_stk.add(new Contact_STK(id,nganhang,ngaygui,sotiengui,kyhan,laisuat,laisuatkhongkyhan,tralai,khidenhan));
                    tongtien+=sotiengui;
                    txt.setText("Tổng tiền:  "+tongtien);
                }
                adapter_stk.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void addControls() {
        listView = findViewById(R.id.listView_show_ngan_hang);
        list_stk = new ArrayList<>();
        adapter_stk = new Adapter_STK(this, list_stk);
        listView.setAdapter(adapter_stk);
        btn = findViewById(R.id.btn_them_tk);
        spinner1=findViewById(R.id.spinner_ngan_hang);
        list_ngan_hang=new ArrayList<>();
        txt=findViewById(R.id.txt_tong_tien);
        btn2=findViewById(R.id.btn_lich_su);
    }

    private void deleteSTK(int idSTK){
        MainActivity.database.delete("STK", "ID=?",new String[]{idSTK+""});
        list_stk.clear();
        Cursor cursor=MainActivity.database.rawQuery("SELECT * FROM STK",null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String nganhang = cursor.getString(1);
            String ngaygui=cursor.getString(2);
            int sotiengui=cursor.getInt(3);
            String kyhan=cursor.getString(4);
            String laisuat=cursor.getString(5);
            String laisuatkhongkyhan=cursor.getString(6);
            String tralai=cursor.getString(7);
            String khidenhan=cursor.getString(8);
            list_stk.add(new Contact_STK(id,nganhang,ngaygui,sotiengui,kyhan,laisuat,laisuatkhongkyhan,tralai,khidenhan));

        }
        adapter_stk.notifyDataSetChanged();
    }

}
