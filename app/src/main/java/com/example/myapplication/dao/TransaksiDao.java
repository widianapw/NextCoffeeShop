package com.example.myapplication.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myapplication.model.Keranjang;
import com.example.myapplication.model.MaxTransaksi;
import com.example.myapplication.model.Transaksi;

@Dao
public interface TransaksiDao {
    @Insert
    void insertTransaksi(Transaksi transaksi);

    @Query("Select max(id) as idTerbesar from tb_transaksi")
    MaxTransaksi getMaxTransaksi();


}
