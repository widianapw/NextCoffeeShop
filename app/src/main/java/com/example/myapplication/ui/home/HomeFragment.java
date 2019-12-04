package com.example.myapplication.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.myapplication.model.ProdukWithRelations;

import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    RecyclerView recycler_kasir;
    AppDatabase mDb;
    KasirAdapter mAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        recycler_kasir = root.findViewById(R.id.recycler_kasir);
        mAdapter = new KasirAdapter(getContext());
        recycler_kasir.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler_kasir.setAdapter(mAdapter);
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
                final List<ProdukWithRelations> data = mDb.produkDao().loadAllProduks();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.setTasks(data);
                    }
                });
            }
        });
    }
}