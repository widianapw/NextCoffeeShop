package com.example.myapplication.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.myapplication.model.Kategori;
import com.example.myapplication.model.Keranjang;

@Dao
public interface KeranjangDao {
    @Insert
    void insertKeranjang(Keranjang keranjang);
}
