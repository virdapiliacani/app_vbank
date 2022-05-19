package com.virda.appvbank;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {
    protected Cursor cursor;
    Database database;
    Button btnSimpan;
    EditText tanggal, nominal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        database = new Database(this);
        tanggal = findViewById(R.id.editTanggal);
        nominal = findViewById(R.id.editNominal);
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

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = database.getReadableDatabase();
                db.execSQL("update tabungan set tanggal= '" +
                        tanggal.getText().toString() + "', nominal= '" +
                        nominal.getText().toString() +"' where tanggal = '" +
                        getIntent().getStringExtra("tanggal")+ "'");
                Toast.makeText(UpdateActivity.this, "Update Berhasil", Toast.LENGTH_SHORT).show();
                MainActivity.ma.RefreshList();
                finish();

            }
        });
    }
}