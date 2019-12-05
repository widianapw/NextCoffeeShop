package com.example.myapplication.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.example.myapplication.model.DetailTransaksi;

@Dao
public interface DetailTransaksiDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertDetailTransaksi(DetailTransaksi detailTransaksi);

}
