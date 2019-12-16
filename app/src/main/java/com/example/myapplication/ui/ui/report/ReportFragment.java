package com.example.myapplication.ui.ui.report;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.adapter.ReportItemTerlarisAdapter;
import com.example.myapplication.adapter.ReportTahunAdapter;
import com.example.myapplication.dao.DetailTransaksiDao;
import com.example.myapplication.database.AppDatabase;
import com.example.myapplication.database.AppExecutors;

import java.util.ArrayList;
import java.util.List;


public class ReportFragment extends Fragment {

    private AppDatabase mDb;
    View mView;
    ReportItemTerlarisAdapter mAdapter;
    ReportTahunAdapter mAdapterTahun;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.report_fragment, container, false);
        mView = view;
        mAdapter = new ReportItemTerlarisAdapter(getContext());
        mAdapterTahun = new ReportTahunAdapter(getContext());
        RecyclerView recyclerView = view.findViewById(R.id.recycler_terlaris);
        RecyclerView recyclerViewReport = view.findViewById(R.id.recycler_report_tahun);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewReport.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter);
        recyclerViewReport.setAdapter(mAdapterTahun);
        mDb = AppDatabase.getDatabase(getContext());
//        retrieveData();
        return mView;

    }

    @Override
    public void onResume() {
        super.onResume();
        retrieveData();

    }

    public void retrieveData() {
        mDb = AppDatabase.getDatabase(getContext());
        final List<DetailTransaksiDao.Terlaris> data = mDb.detailTransaksiDao().readDataTerlaris();
        mAdapter.setTasks(data);
        final List<DetailTransaksiDao.ReportTahun> data1 = mDb.detailTransaksiDao().reportTahun();
        Log.e("da",""+data1);
        mAdapterTahun.setTasksReport(data1);

    }

}
