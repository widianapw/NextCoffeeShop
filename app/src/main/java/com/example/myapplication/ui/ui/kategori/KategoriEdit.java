package com.example.myapplication.ui.ui.kategori;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.R;
import com.example.myapplication.database.AppDatabase;
import com.example.myapplication.database.AppExecutors;
import com.example.myapplication.model.Kategori;

public class KategoriEdit extends AppCompatActivity {
    AppDatabase mDb;
    Intent intent;
    int mKategoriId;
    EditText namaKategori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori_edit);
        mDb = AppDatabase.getDatabase(getApplicationContext());
        namaKategori = findViewById(R.id.etKategori);
        intent = getIntent();
        if ((intent != null && intent.hasExtra("update"))) {
            mKategoriId = intent.getIntExtra("update", -1);

            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    Kategori kategori = mDb.kategoriDao().loadKategoriById(mKategoriId);
                    setUiData(kategori);
                }
            });
        }

        Button btnSimpan = findViewById(R.id.btnUpdateKategori);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmit();
            }
        });
    }

    private void setUiData(final Kategori kategori){
        if (kategori == null) {
            return;
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                namaKategori.setText(kategori.getKategori());
            }
        });

    }

    private void onSubmit(){
        namaKategori = findViewById(R.id.etKategori);
        Log.e("nama",namaKategori.getText().toString());
        final Kategori kategori = new Kategori(
                namaKategori.getText().toString()
        );

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                kategori.setId(mKategoriId);
                mDb.kategoriDao().updateKategori(kategori);
                finish();
            }
        });
    }
}
