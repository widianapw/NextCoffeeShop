package com.example.myapplication.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.database.AppDatabase;
import com.example.myapplication.database.AppExecutors;
import com.example.myapplication.model.DetailTransaksi;
import com.example.myapplication.model.Keranjang;
import com.example.myapplication.model.KeranjangWithRelations;
import com.example.myapplication.model.MainKasir;
import com.example.myapplication.model.MaxTransaksi;
import com.example.myapplication.model.Transaksi;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class KeranjangBayar extends AppCompatActivity {
    int harga_keranjang;
    Button btnBayarFinal;
    EditText etUang;
    AppDatabase mDb;
    TextView tvTotalBayar;
    TextView tvKembalian;
    MaxTransaksi mTransaksi;
    int n;

    List<KeranjangWithRelations> mKeranjangList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keranjang_bayar);
        mDb = AppDatabase.getDatabase(getApplicationContext());
        btnBayarFinal = findViewById(R.id.button_bayar_final);
        tvTotalBayar = findViewById(R.id.tv_total_belanja);
        tvKembalian = findViewById(R.id.tv_uang_kembalian);
        etUang = findViewById(R.id.etUang);


        btnBayarFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmit();
            }
        });

        etUang.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int a = Integer.parseInt(s.toString());
                int b = Integer.parseInt(tvTotalBayar.getText().toString());
                int c = a - b;
                tvKembalian.setText(Integer.toString(c));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void onSubmit() {
        final String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            final Transaksi data = new Transaksi(
                    1, date
            );

            @Override
            public void run() {
                final List<KeranjangWithRelations> mKeranjangList = mDb.keranjangDao().loadKeranjang();
                mDb.transaksiDao().insertTransaksi(data);
                n = mKeranjangList.size();
                mTransaksi = mDb.transaksiDao().getMaxTransaksi();
                int mIdTransaksi = mTransaksi.getIdTerbesar();
                for (int i = 0; i < n; i++) {
                    DetailTransaksi data = new DetailTransaksi();
                    data.setId_transaksi(mIdTransaksi);
                    data.setId_produk(mKeranjangList.get(i).keranjang.getId_produk());
                    data.setQty(mKeranjangList.get(i).keranjang.getQty());
                    insert(data);
                }
                mDb.keranjangDao().deleteAll();
            }
        });
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);

    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveData();
    }

    public void retrieveData() {
        mDb = AppDatabase.getDatabase(getApplicationContext());
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final MainKasir mainKasir = mDb.keranjangDao().loadMainKasir();
                final List<KeranjangWithRelations> mKeranjangList = mDb.keranjangDao().loadKeranjang();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        harga_keranjang = mainKasir.getTotal_harga();
                        tvTotalBayar.setText(String.valueOf(harga_keranjang));
                        n = mKeranjangList.size();
                    }
                });
            }
        });
    }

    @SuppressLint("StaticFieldLeak")private void insert(final DetailTransaksi detailTransaksi) {
        new AsyncTask<Void, Void, Long>() {
            @Override
            protected Long doInBackground(Void... voids) {
                //Menjalankan proses insert data
                return mDb.detailTransaksiDao().insertDetailTransaksi(detailTransaksi);
            }

            @Override
            protected void onPostExecute(Long status) {
                //Menandakan bahwa data berhasil disimpan

            }
        }.execute();
    }
}
