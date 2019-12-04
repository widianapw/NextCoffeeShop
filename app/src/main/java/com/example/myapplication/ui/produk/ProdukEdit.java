package com.example.myapplication.ui.produk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.myapplication.R;
import com.example.myapplication.database.AppDatabase;
import com.example.myapplication.database.AppExecutors;
import com.example.myapplication.model.Kategori;
import com.example.myapplication.model.Produk;
import com.example.myapplication.model.ProdukWithRelations;

import java.util.ArrayList;
import java.util.List;

import static com.example.myapplication.MyApplication.getContext;

public class ProdukEdit extends AppCompatActivity {
    Intent intent;
    int midProduk;
    AppDatabase mDb;
    List<String> kategoriDisplay;
    List<Integer> kategoriValue;
    ArrayAdapter<String> adapterKategori;
    Spinner kategoriProduk;
    Button btnUpdate;
    EditText mNama, mHarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produk_edit);
        mDb = AppDatabase.getDatabase(getApplicationContext());

        setSpinners();
        intent = getIntent();
        if (intent != null && intent.hasExtra("update")) {
            midProduk = intent.getIntExtra("update", -1);
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    ProdukWithRelations produk = mDb.produkDao().loadProdukById(midProduk);
                    setUiData(produk);
                }
            });
        }
        btnUpdate = findViewById(R.id.btn_update_produk);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmit();
            }
        });
    }

    public void setUiData(final ProdukWithRelations produk) {
        if (produk == null) {
            return;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mNama = findViewById(R.id.et_nama_produk_edit);
                mHarga = findViewById(R.id.et_harga_edit);
                kategoriProduk = findViewById(R.id.spnKategori_edit);
                mNama.setText(produk.produk.getNama_produk());
                mHarga.setText(String.valueOf(produk.produk.getHarga()));
                kategoriProduk.setSelection(kategoriValue.indexOf(produk.kategoris.get(0).getId()));
            }
        });
    }

    private void setSpinners() {
        kategoriDisplay =  new ArrayList<String>();
        kategoriValue =  new ArrayList<Integer>();

        adapterKategori = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, kategoriDisplay);
        adapterKategori.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Log.e("adapter",adapterKategori+"");
        kategoriProduk = findViewById(R.id.spnKategori_edit);
        kategoriProduk.setAdapter(adapterKategori);

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<Kategori> kategoriList = mDb.kategoriDao().loadAllKategoris();

                for (Kategori kategori : kategoriList) {
                    kategoriDisplay.add(kategori.getKategori());
                    kategoriValue.add(kategori.getId());
                }
                adapterKategori.notifyDataSetChanged();

            }
        });
    }

    public void onSubmit() {
//        kategoriProduk = findViewById(R.id.spnKategori);
        final Produk produk = new Produk(
                kategoriValue.get(kategoriProduk.getSelectedItemPosition()),
                mNama.getText().toString(),
                Integer.parseInt(mHarga.getText().toString())
        );

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                produk.setId(midProduk);
                mDb.produkDao().updateProduk(produk);
                finish();
            }

        });

    }
}
