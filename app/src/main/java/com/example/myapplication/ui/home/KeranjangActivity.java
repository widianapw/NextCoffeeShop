package com.example.myapplication.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;
import com.example.myapplication.adapter.KeranjangAdapter;
import com.example.myapplication.database.AppDatabase;
import com.example.myapplication.database.AppExecutors;
import com.example.myapplication.model.KeranjangWithRelations;

import java.util.List;

public class KeranjangActivity extends AppCompatActivity {

    RecyclerView recycler_keranjang;
    AppDatabase mDb;
    KeranjangAdapter mAdapter;
    Button mBayar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keranjang);
        recycler_keranjang = findViewById(R.id.recycler_keranjang);
        mAdapter = new KeranjangAdapter(getApplicationContext(), this);
        recycler_keranjang.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recycler_keranjang.setAdapter(mAdapter);
        mDb = AppDatabase.getDatabase(getApplicationContext());
        mBayar = findViewById(R.id.button_bayar);
        mBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), KeranjangBayar.class);
                startActivity(i);
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
                final List<KeranjangWithRelations> data = mDb.keranjangDao().loadKeranjang();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.setTasks(data);
                    }
                });
            }
        });
    }
}
