package com.example.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.database.AppDatabase;
import com.example.myapplication.database.AppExecutors;
import com.example.myapplication.model.Produk;
import com.example.myapplication.model.ProdukWithRelations;
import com.example.myapplication.ui.produk.ProdukEdit;
import com.example.myapplication.ui.produk.ProdukFragment;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ProdukAdapter extends RecyclerView.Adapter<ProdukAdapter.MyViewHolder> {
    public Context context;
    List<ProdukWithRelations> mProdukList;
    AppDatabase mDb;
    ProdukFragment produkFragment;

    public ProdukAdapter(Context context, ProdukFragment produkFragment) {
        this.context = context;
        this.produkFragment = produkFragment;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_product, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.e("AS", "onBindViewHolder: "+ mProdukList.get(position).produk.getNama_produk());
        holder.mNama.setText(mProdukList.get(position).produk.getNama_produk());
        holder.mHarga.setText(String.valueOf(mProdukList.get(position).produk.getHarga()));
        holder.mKategori.setText(mProdukList.get(position).kategoris.get(0).getKategori());

    }

    @Override
    public int getItemCount() {
        if (mProdukList == null) {
            return 0;
        }
        return mProdukList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mNama, mHarga, mKategori;
        ImageView mEdit, mDelete;

        MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            mNama = itemView.findViewById(R.id.namaProduk);
            mHarga = itemView.findViewById(R.id.harga);
            mKategori = itemView.findViewById(R.id.kategoriProduk);
            mEdit = itemView.findViewById(R.id.edit_produk);
            mDelete = itemView.findViewById(R.id.hapus_produk);
            mDb = AppDatabase.getDatabase(context);
            mEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, ProdukEdit.class);
                    i.putExtra("update",mProdukList.get(getAdapterPosition()).produk.getId());
                    context.startActivity(i);
                }
            });

            mDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final SweetAlertDialog sDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
                    sDialog.setTitle("Hapus Data");
                    sDialog.setContentText("Ingin menghapus data "+mProdukList.get(getAdapterPosition()).produk.getNama_produk()+" ?");
                    sDialog.setConfirmButton("Ya", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            onDeleteData(getAdapterPosition());
                            sDialog.dismissWithAnimation();
                        }
                    });
                    sDialog.setCancelButton("Tidak", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sDialog.dismissWithAnimation();
                        }
                    });
                    sDialog.show();

                }
            });
        }
    }

    public void setTasks(List<ProdukWithRelations> produkList){
        mProdukList = produkList;

        notifyDataSetChanged();
    }

    private void onDeleteData(int position){
        mDb = AppDatabase.getDatabase(context);
        final Produk produk = mProdukList.get(position).produk;
        mDb.produkDao().delete(produk);
        mProdukList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mProdukList.size());
    }
}
