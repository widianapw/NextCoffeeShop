package com.example.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.database.AppDatabase;
import com.example.myapplication.database.AppExecutors;
import com.example.myapplication.model.Keranjang;
import com.example.myapplication.model.KeranjangWithRelations;
import com.example.myapplication.ui.home.KeranjangActivity;
import com.example.myapplication.ui.ui.kategori.KategoriEdit;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class KeranjangAdapter extends RecyclerView.Adapter<KeranjangAdapter.MyViewHolder> {
    Context context;
    List<KeranjangWithRelations> mKeranjangList;
    AppDatabase mDb;

    public KeranjangAdapter(Context context) {
        this.context = context;
    }

    public void setTasks(List<KeranjangWithRelations> keranjangList) {
        mKeranjangList = keranjangList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_keranjang, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.mNama.setText(mKeranjangList.get(position).produks.get(0).getNama_produk());
        holder.mHarga.setText(String.valueOf(mKeranjangList.get(position).produks.get(0).getHarga()));
        holder.mQty.setText(String.valueOf(mKeranjangList.get(position).keranjang.getQty()));
        holder.mSubtotal.setText(String.valueOf(mKeranjangList.get(position).produks.get(0).getHarga() * mKeranjangList.get(position).keranjang.getQty()));

       holder.mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idKeranjang = mKeranjangList.get(position).keranjang.getId();
                Log.e("idKeranjang",""+idKeranjang);
                Intent i = new Intent(context, KategoriEdit.class);
                i.putExtra("update",idKeranjang);
                context.startActivity(i);
            }
        });

        holder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Keranjang keranjang = mKeranjangList.get(position).keranjang;
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        mDb.keranjangDao().delete(keranjang);
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mKeranjangList == null) {
            return 0;
        }
        return mKeranjangList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mNama, mHarga, mQty, mSubtotal;
        Button mDelete, mEdit;

        MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            mDb = AppDatabase.getDatabase(context);
            mNama = itemView.findViewById(R.id.nama_keranjang);
            mHarga = itemView.findViewById(R.id.harga_keranjang);
            mQty = itemView.findViewById(R.id.qty_keranjang);
            mSubtotal = itemView.findViewById(R.id.subtotal_keranjang);
            mDelete = itemView.findViewById(R.id.button_delete_keranjang);
            mEdit = itemView.findViewById(R.id.button_edit_keranjang);

        }
    }
}
