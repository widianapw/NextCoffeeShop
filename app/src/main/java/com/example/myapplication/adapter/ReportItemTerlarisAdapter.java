package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication.R;
import com.example.myapplication.dao.DetailTransaksiDao;
import com.example.myapplication.database.AppDatabase;
import com.example.myapplication.model.DetailTransaksi;

import java.util.List;

public class ReportItemTerlarisAdapter extends RecyclerView.Adapter<ReportItemTerlarisAdapter.ViewHolder> {

    private List<DetailTransaksiDao.Terlaris> mDaftarTerlaris;


    Context context;

    public ReportItemTerlarisAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_terlaris, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String getNama = mDaftarTerlaris.get(position).getNama_produk();
        String getJumlah = String.valueOf(mDaftarTerlaris.get(position).getTotal());

        holder.nama.setText(getNama);
        holder.jumlah.setText(getJumlah);
    }

    @Override
    public int getItemCount() {
        if (mDaftarTerlaris == null) {
            return 0;
        }
        return mDaftarTerlaris.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nama, jumlah;
        //Deklarasi View yang akan digunakan

        ViewHolder(View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.nama_terlaris);
            jumlah = itemView.findViewById(R.id.jumlah_terlaris);
        }

    }

    public void setTasks(List<DetailTransaksiDao.Terlaris> daftarTerlaris) {
        mDaftarTerlaris = daftarTerlaris;
        notifyDataSetChanged();
    }
}

