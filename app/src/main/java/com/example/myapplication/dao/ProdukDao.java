package com.example.myapplication.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.model.Produk;
import com.example.myapplication.model.ProdukWithRelations;

import java.util.List;

@Dao
public interface ProdukDao {
    @Query("SELECT * FROM tb_produk ORDER BY id")
    List<ProdukWithRelations> loadAllProduks();

    @Insert
    void insertProduk(Produk produk);

    @Update
    void updateProduk(Produk produk);

    @Delete
    void delete(Produk produk);

    @Query("SELECT * FROM tb_produk WHERE id = :id")
    ProdukWithRelations loadProdukById(int id);
}
