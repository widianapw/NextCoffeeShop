package com.example.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.database.AppDatabase;
import com.example.myapplication.database.AppExecutors;
import com.example.myapplication.model.Kategori;
import com.example.myapplication.model.ProdukWithRelations;
import com.example.myapplication.ui.ui.kategori.KategoriEdit;
import com.example.myapplication.ui.ui.kategori.KategoriFragment;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class KategoriAdapter extends RecyclerView.Adapter<KategoriAdapter.MyViewHolder> {
    private Context context;
    private List<Kategori> mKategoriList;
    private AppDatabase mDb;
    private KategoriFragment kategoriFragment;

    public KategoriAdapter(Context context, KategoriFragment kategoriFragment) {
        this.context = context;
        this.kategoriFragment = kategoriFragment;
    }

    public void setTasks(List<Kategori> kategoriList) {
        mKategoriList = kategoriList;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_temp, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.mNama.setText(mKategoriList.get(position).getKategori());
    }

    @Override
    public int getItemCount() {
        if (mKategoriList == null) {
            return 0;
        }
        return mKategoriList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mNama;
        ImageView mEdit, mDelete;

        MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            kategoriFragment = new KategoriFragment();
            mDb = AppDatabase.getDatabase(context);
            mNama = itemView.findViewById(R.id.nama_kategori);
            mEdit = itemView.findViewById(R.id.edit_kategori);
            mDelete = itemView.findViewById(R.id.hapus_kategori);

            mEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int idKategori = mKategoriList.get(getAdapterPosition()).getId();
                    Intent i = new Intent(context, KategoriEdit.class);
                    i.putExtra("update",idKategori);
                    context.startActivity(i);

                }
            });

            mDelete.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    final SweetAlertDialog sDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
                    sDialog.setTitle("Hapus Data");
                    sDialog.setContentText("Ingin menghapus data " + mKategoriList.get(getAdapterPosition()).getKategori() + " ?");
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

    private void onDeleteData(int position){
        mDb = AppDatabase.getDatabase(context);
        final Kategori kategori = mKategoriList.get(position);
        mDb.kategoriDao().delete(kategori);
        kategoriFragment.retrieveData();
        mKategoriList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mKategoriList.size());
    }
}
