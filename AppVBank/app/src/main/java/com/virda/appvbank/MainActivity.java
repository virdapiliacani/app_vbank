package com.virda.appvbank;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    String[] nabung;
    ListView listView;
    Menu menu;
    protected Cursor cursor;
    Database database;
    public static MainActivity ma;
    Button btnPemasukan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton f = findViewById(R.id.btnAdd);
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent IntentAdd = new Intent(MainActivity.this,CreateActivity.class);
                startActivity(IntentAdd);

            }
        });
        ma = this;
        database = new Database(this);
        RefreshList();
    }

    public void RefreshList() {
        SQLiteDatabase db = database.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM tabungan", null);
        nabung = new String[cursor.getCount()];
        cursor.moveToFirst();

        for(int i=0; i< cursor.getCount(); i++){
            cursor.moveToPosition(i);
            nabung[i] = cursor.getString(0).toString();

        }
        listView = (ListView) findViewById(R.id.listNabung);
        listView.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, nabung));
        listView.setSelected(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final String selection = nabung[arg2];
                final CharSequence[] dialogitem = {"Lihat Detail Tabungan", "Update Tabungan", "Hapus Tabungan"};
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item){
                            case 0:
                                Intent i = new Intent(getApplicationContext(), DetailActivity.class);
                                i.putExtra("tanggal", selection);
                                startActivity(i);
                                break;
                            case 1:
                                Intent in = new Intent(getApplicationContext(), UpdateActivity.class);
                                in.putExtra("tanggal", selection);
                                startActivity(in);
                                break;
                            case 2:
                                SQLiteDatabase db = database.getWritableDatabase();
                                db.execSQL("delete from tabungan where tanggal = '" + selection + "'");
                                RefreshList();
                                break;
                        }

                    }
                });
                builder.create().show();

            }
        });
        btnPemasukan = findViewById(R.id.btnPemasukan);
        btnPemasukan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent IntentAdd = new Intent(MainActivity.this,PemasukanActivity.class);
                startActivity(IntentAdd);
//                Hitung();
            }

//            private void Hitung() {
//                double jumlah = Integer.parseInt(nominal.getText().toString());
//                double total = jumlah + jumlah;
//
//                nominal.setText(String.valueOf(total));
//
//            }
        });


        ((ArrayAdapter) listView.getAdapter()).notifyDataSetInvalidated();

    }
}