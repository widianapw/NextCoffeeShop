package com.example.myapplication.ui.produk;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.ProdukAdapter;
import com.example.myapplication.database.AppDatabase;
import com.example.myapplication.database.AppExecutors;
import com.example.myapplication.model.Kategori;
import com.example.myapplication.model.Produk;
import com.example.myapplication.model.ProdukWithRelations;
import com.example.myapplication.ui.dashboard.DashboardViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.myapplication.MyApplication.getContext;


public class ProdukFragment extends Fragment {
    FloatingActionButton fab;
    Toolbar toolbar;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;

    EditText mNama, mHarga;
    AppDatabase mDb;
    List<String> kategoriDisplay;
    List<Integer> kategoriValue;
    ArrayAdapter<String> adapterKategori;
    Spinner kategoriProduk;

    ProdukAdapter mAdapter;
    RecyclerView recycler_produk;

    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_produk, container, false);
        recycler_produk = root.findViewById(R.id.recycler_produk);
        mAdapter = new ProdukAdapter(getContext(), this);

        recycler_produk.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler_produk.setAdapter(mAdapter);
        mDb = AppDatabase.getDatabase(getContext());
        fab = (FloatingActionButton) root.findViewById(R.id.tambah_produk);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogForm();
            }
        });


        return root;
    }

    private void DialogForm() {
        dialog = new AlertDialog.Builder(getActivity());
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.tambah_produk, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setTitle("Tambah Produk");
        setSpinners();
        mNama = (EditText) dialogView.findViewById(R.id.et_nama_produk_create);
        mHarga = (EditText) dialogView.findViewById(R.id.et_harga_create);
        kategoriProduk = (Spinner) dialogView.findViewById(R.id.spnKategori_create);

        dialog.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                final Produk produk = new Produk(
                        kategoriValue.get(kategoriProduk.getSelectedItemPosition()),
                        mNama.getText().toString(),
                        Integer.parseInt(mHarga.getText().toString())
                );

                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        mDb.produkDao().insertProduk(produk);
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
                Log.e("aktiviti",""+getActivity());
                final List<ProdukWithRelations> data = mDb.produkDao().loadAllProduks();
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

    private void setSpinners() {
        kategoriDisplay = new ArrayList<String>();
        kategoriValue = new ArrayList<Integer>();

        adapterKategori = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, kategoriDisplay);
        adapterKategori.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        kategoriProduk = dialogView.findViewById(R.id.spnKategori_create);
        Log.e("adapter",adapterKategori+"");
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


}