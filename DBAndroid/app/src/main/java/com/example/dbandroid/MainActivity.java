package com.example.dbandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText etNRP, etNama;
    Button btnSimpan, btnAmbilData, btnUpdate, btnHapus;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNRP = findViewById(R.id.etNRP);
        etNama = findViewById(R.id.etNama);
        btnSimpan = findViewById(R.id.btnSimpan);
        btnAmbilData = findViewById(R.id.btnAmbilData);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnHapus = findViewById(R.id.btnHapus);

        dbHelper = new DBHelper(this);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nrp = etNRP.getText().toString().trim();
                String nama = etNama.getText().toString().trim();

                if (!nrp.isEmpty() && !nama.isEmpty()) {
                    long result = dbHelper.insertData(nrp, nama);
                    if (result != -1) {
                        Toast.makeText(MainActivity.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Gagal menyimpan data", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "NRP dan Nama tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnAmbilData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = dbHelper.getData();
                if (cursor.getCount() == 0) {
                    Toast.makeText(MainActivity.this, "Tidak ada data", Toast.LENGTH_SHORT).show();
                    return;
                }

                StringBuilder stringBuilder = new StringBuilder();
                while (cursor.moveToNext()) {
                    stringBuilder.append("NRP: ").append(cursor.getString(0)).append(", Nama: ").append(cursor.getString(1)).append("\n");
                }

                Toast.makeText(MainActivity.this, stringBuilder.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nrp = etNRP.getText().toString().trim();
                String nama = etNama.getText().toString().trim();

                if (!nrp.isEmpty() && !nama.isEmpty()) {
                    int result = dbHelper.updateData(nrp, nama);
                    if (result > 0) {
                        Toast.makeText(MainActivity.this, "Data berhasil diupdate", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Gagal mengupdate data", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "NRP dan Nama tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nrp = etNRP.getText().toString().trim();

                if (!nrp.isEmpty()) {
                    int result = dbHelper.deleteData(nrp);
                    if (result > 0) {
                        Toast.makeText(MainActivity.this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Gagal menghapus data", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "NRP tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static class DBHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "DBSederhana.db";
        private static final String TABLE_NAME = "mahasiswa";
        private static final String COL_NRP = "nrp";
        private static final String COL_NAMA = "nama";

        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                    COL_NRP + " TEXT PRIMARY KEY," +
                    COL_NAMA + " TEXT)";
            db.execSQL(createTableQuery);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }

        public long insertData(String nrp, String nama) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_NRP, nrp);
            contentValues.put(COL_NAMA, nama);
            return db.insert(TABLE_NAME, null, contentValues);
        }

        public Cursor getData() {
            SQLiteDatabase db = this.getReadableDatabase();
            return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        }

        public int updateData(String nrp, String nama) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_NAMA, nama);
            return db.update(TABLE_NAME, contentValues, COL_NRP + " = ?", new String[]{nrp});
        }

        public int deleteData(String nrp) {
            SQLiteDatabase db = this.getWritableDatabase();
            return db.delete(TABLE_NAME, COL_NRP + " = ?", new String[]{nrp});
        }
    }
}
