package com.example.myapplication.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.KasirAdapter;
import com.example.myapplication.database.AppDatabase;
import com.example.myapplication.database.AppExecutors;
import com.example.myapplication.model.MainKasir;
import com.example.myapplication.model.ProdukWithRelations;

import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    RecyclerView recycler_kasir;
    AppDatabase mDb;
    KasirAdapter mAdapter;
    MainKasir mainKasir;
    TextView mTotalQty, mTotalHarga;
    Button mDetail;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        recycler_kasir = root.findViewById(R.id.recycler_kasir);
        mAdapter = new KasirAdapter(getContext());
        recycler_kasir.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler_kasir.setAdapter(mAdapter);

        mTotalHarga = root.findViewById(R.id.total_kasir);
        mTotalQty = root.findViewById(R.id.qty_kasir);
        mDb = AppDatabase.getDatabase(getContext());

        mDetail = root.findViewById(R.id.btnDetailKeranjang);
        mDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),KeranjangActivity.class);
                startActivity(i);
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        retrieveData();
    }

    public void retrieveData() {
        mDb = AppDatabase.getDatabase(getContext());
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final MainKasir mainKasir = mDb.keranjangDao().loadMainKasir();
                final List<ProdukWithRelations> data = mDb.produkDao().loadAllProduks();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("total_harga",String.valueOf(mainKasir.getTotal_harga()));
                        mTotalHarga.setText(String.valueOf(mainKasir.getTotal_harga()));
                        mTotalQty.setText(String.valueOf(mainKasir.getTotal_qty()));
                        mAdapter.setTasks(data);
                    }
                });
            }
        });
    }
}