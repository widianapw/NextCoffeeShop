package com.example.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.database.AppDatabase;
import com.example.myapplication.database.AppExecutors;
import com.example.myapplication.model.Kategori;
import com.example.myapplication.model.Keranjang;
import com.example.myapplication.model.KeranjangWithRelations;
import com.example.myapplication.ui.home.KeranjangActivity;
import com.example.myapplication.ui.home.KeranjangEdit;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class KeranjangAdapter extends RecyclerView.Adapter<KeranjangAdapter.MyViewHolder>{
    Context context;
    KeranjangActivity keranjangActivity;
    private List<KeranjangWithRelations> mKeranjangList;
    private AppDatabase mDb;

    public KeranjangAdapter(Context context, KeranjangActivity keranjangActivity){
        this.context = context;
        this.keranjangActivity = keranjangActivity;
    }

    public void setTasks(List<KeranjangWithRelations> keranjangWithRelations) {
        mKeranjangList = keranjangWithRelations;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_keranjang, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.mNama.setText(mKeranjangList.get(position).produks.get(0).getNama_produk());
        holder.mHarga.setText("Rp "+mKeranjangList.get(position).produks.get(0).getHarga());
        holder.mQty.setText(String.valueOf(mKeranjangList.get(position).keranjang.getQty()));
        holder.mSub.setText("Rp "+mKeranjangList.get(position).produks.get(0).getHarga() * mKeranjangList.get(position).keranjang.getQty());

    }

    @Override
    public int getItemCount() {
        if (mKeranjangList == null){
            return 0;
        }
        return mKeranjangList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mNama, mHarga, mSub, mQty;
        Button mDelete, mEdit;

        MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            mNama = itemView.findViewById(R.id.nama_keranjang);
            mHarga = itemView.findViewById(R.id.harga_keranjang);
            mSub = itemView.findViewById(R.id.subtotal_keranjang);
            mQty = itemView.findViewById(R.id.qty_keranjang);
            mEdit = itemView.findViewById(R.id.button_edit_keranjang);
            mDelete = itemView.findViewById(R.id.button_delete_keranjang);
            mDb = AppDatabase.getDatabase(context);

            mEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(keranjangActivity, KeranjangEdit.class);
                    i.putExtra("update",mKeranjangList.get(getAdapterPosition()).keranjang.getId());
                    keranjangActivity.startActivity(i);
                }
            });

            mDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final SweetAlertDialog sDialog = new SweetAlertDialog(keranjangActivity, SweetAlertDialog.WARNING_TYPE);
                    sDialog.setTitle("Hapus Data");
                    sDialog.setContentText("Ingin menghapus data " + mKeranjangList.get(getAdapterPosition()).produks.get(0).getNama_produk() + " ?");
                    sDialog.setConfirmButton("Ya", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            final Keranjang keranjang = mKeranjangList.get(getAdapterPosition()).keranjang;
                            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                @Override
                                public void run() {
                                    mDb.keranjangDao().delete(keranjang);
                                }
                            });
                            keranjangActivity.retrieveData();
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
}
