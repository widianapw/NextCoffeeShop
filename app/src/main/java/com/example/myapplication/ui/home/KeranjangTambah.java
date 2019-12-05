package com.example.myapplication.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.R;
import com.example.myapplication.database.AppDatabase;
import com.example.myapplication.database.AppExecutors;
import com.example.myapplication.model.Keranjang;
import com.example.myapplication.model.KeranjangWithRelations;

public class KeranjangTambah extends AppCompatActivity {
    AppDatabase mDb;
    EditText mKuantiti;
    Button mSubmit;
    int mIdProduk;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keranjang_tambah);
        mDb = AppDatabase.getDatabase(getApplicationContext());
        intent = getIntent();
        mIdProduk = intent.getIntExtra("insert",-1);
        mKuantiti = findViewById(R.id.etKuantitiTambah);

        mSubmit = findViewById(R.id.btnSaveKuantitiTambah);
        mSubmit = findViewById(R.id.btnSaveKuantitiTambah);
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmit();
            }
        });
    }

    public void onSubmit() {
        final Keranjang data = new Keranjang(
                mIdProduk,
                Integer.parseInt(mKuantiti.getText().toString())
        );

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.keranjangDao().insertKeranjang(data);
                finish();
            }
        });


    }
}
