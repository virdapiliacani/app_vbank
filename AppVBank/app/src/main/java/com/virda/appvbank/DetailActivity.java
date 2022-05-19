package com.virda.appvbank;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {
    protected Cursor cursor;
    Database database;
    Button btnSimpan;
    TextView tanggal, nominal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        database = new Database(this);
        tanggal = findViewById(R.id.txtTanggal);
        nominal = findViewById(R.id.txtNominal);
//        jumlah = findViewById(R.id.editJumlah);
        btnSimpan = findViewById(R.id.btnSimpan);


        SQLiteDatabase db = database.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM tabungan WHERE tanggal = '" +
                getIntent().getStringExtra("tanggal")+ "'", null);
        cursor.moveToFirst();

        if(cursor.getCount()>0){
            cursor.moveToPosition(0);
            tanggal.setText(cursor.getString(0).toString());
            nominal.setText(cursor.getString(1).toString());
        }

    }
}