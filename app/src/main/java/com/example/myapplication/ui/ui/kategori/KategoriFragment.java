package com.example.myapplication.ui.ui.kategori;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.KategoriAdapter;
import com.example.myapplication.database.AppDatabase;
import com.example.myapplication.database.AppExecutors;
import com.example.myapplication.model.Kategori;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;

public class KategoriFragment extends Fragment {

    private KategoriViewModel mViewModel;
    FloatingActionButton fab;
    Toolbar toolbar;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    ImageView edit;
    EditText nama;
    RecyclerView recycler_kategori;
    KategoriAdapter mAdapter;
    AppDatabase mDb;

    public static KategoriFragment newInstance() {
        return new KategoriFragment();
    }


    private KategoriViewModel kategoriViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.kategori_fragment, container, false);
        fab = (FloatingActionButton) root.findViewById(R.id.tambah_kategori);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogForm();
            }
        });
        mAdapter = new KategoriAdapter(getContext(), this);
        recycler_kategori = root.findViewById(R.id.recycler_kategori);
        recycler_kategori.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler_kategori.setAdapter(mAdapter);
        mDb = AppDatabase.getDatabase(getContext());



        return root;
    }

    private void DialogForm() {
        dialog = new AlertDialog.Builder(getActivity());
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.tambah_kategori, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setTitle("Tambah Kategori");

        nama = (EditText) dialogView.findViewById(R.id.nama_kategori_tambah);

        dialog.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final Kategori kategori = new Kategori(
                        nama.getText().toString()
                );
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        mDb.kategoriDao().insertKategori(kategori);
                        retrieveData();
                    }
                });
                dialog.dismiss();
            }
        });

        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
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
                if(null == getActivity()) {
                    return;
                }
                final List<Kategori> data = mDb.kategoriDao().loadAllKategoris();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.setTasks(data);
                        }
                    });
                }
            }
        });
    }

}
