package com.example.myapplication.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.database.AppDatabase;
import com.example.myapplication.database.AppExecutors;
import com.example.myapplication.model.Keranjang;
import com.example.myapplication.model.KeranjangWithRelations;

public class KeranjangEdit extends AppCompatActivity {

    EditText mKuantiti;
    Button mSave;
    AppDatabase mDb;
    TextView mNama;
    KeranjangWithRelations keranjang;
    int id;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keranjang_edit);
        intent = getIntent();
        id = intent.getIntExtra("update",-1);
        mDb = AppDatabase.getDatabase(getApplicationContext());
        mNama = findViewById(R.id.labelNamaProdukEdit);
        mKuantiti = findViewById(R.id.etKuantitiEdit);
        mSave = findViewById(R.id.btnSaveKuantitiEdit);
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmit();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveData();
    }

    public void retrieveData(){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                keranjang = mDb.keranjangDao().loadKeranjangById(id);
                mKuantiti.setText(String.valueOf(keranjang.keranjang.getQty()));
                mNama.setText(keranjang.produks.get(0).getNama_produk());
            }
        });
    }

    public void onSubmit(){
        final Keranjang data = new Keranjang(
                keranjang.produks.get(0).getId(),
                Integer.parseInt(mKuantiti.getText().toString())
        );
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                data.setId(keranjang.keranjang.getId());
                mDb.keranjangDao().updateKeranjang(data);
            }
        });
        finish();
    }
}
