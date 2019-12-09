package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.dao.DetailTransaksiDao;

import java.util.List;

public class ReportTahunAdapter extends RecyclerView.Adapter<ReportTahunAdapter.ViewHolder>{
    private List<DetailTransaksiDao.ReportTahun> mDaftarReportTahun;
    Context context;

    public ReportTahunAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_report_tahun, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String getBulan = mDaftarReportTahun.get(position).getBulan();
        String getJumlah = String.valueOf(mDaftarReportTahun.get(position).getTotal());

        holder.bulan.setText(getBulan);
        holder.jumlah.setText("Rp "+getJumlah);

    }

    @Override
    public int getItemCount() {
        if (mDaftarReportTahun == null){
            return 0;
        }
        return mDaftarReportTahun.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView bulan, jumlah;
        //Deklarasi View yang akan digunakan

        ViewHolder(View itemView) {
            super(itemView);
            bulan = itemView.findViewById(R.id.bulan_report_tahun);
            jumlah = itemView.findViewById(R.id.jumlah_report_tahun);
        }

    }

    public void setTasksReport(List<DetailTransaksiDao.ReportTahun> daftarReportTahun){
        mDaftarReportTahun = daftarReportTahun;
        notifyDataSetChanged();
    }
}

