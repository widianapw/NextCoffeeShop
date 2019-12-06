package com.example.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.ProdukWithRelations;
import com.example.myapplication.ui.home.KeranjangTambah;

import java.util.List;

public class KasirAdapter extends RecyclerView.Adapter<KasirAdapter.MyViewHolder> {
    Context context;
    List<ProdukWithRelations> mProdukList;

    public KasirAdapter(Context context) {
        this.context = context;
    }

    public void setTasks(List<ProdukWithRelations> produkList) {
        this.mProdukList = produkList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_cashier, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.mNama.setText(mProdukList.get(position).produk.getNama_produk());
        holder.mHarga.setText(String.valueOf(mProdukList.get(position).produk.getHarga()));
    }

    @Override
    public int getItemCount() {
        if (mProdukList == null) {
            return 0;
        }
        return mProdukList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mNama, mHarga;
        CardView mMainCard;
        MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            mNama = itemView.findViewById(R.id.nama_produk_kasir);
            mHarga = itemView.findViewById(R.id.harga_produk_kasir);
            mMainCard = itemView.findViewById(R.id.cardview_kasir);
            mMainCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, KeranjangTambah.class);
                    i.putExtra("insert", mProdukList.get(getAdapterPosition()).produk.getId());
                    context.startActivity(i);
                }
            });
        }
    }
}
