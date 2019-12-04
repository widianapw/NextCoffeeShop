package com.example.myapplication.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.model.Kategori;

import java.util.List;
@Dao
public interface KategoriDao {
    @Query("SELECT * FROM tb_kategori ORDER BY id")
    List<Kategori> loadAllKategoris();

    @Insert
    void insertKategori(Kategori kategori);

    @Update
    void updateKategori(Kategori kategori);

    @Delete
    void delete(Kategori kategori);

    @Query("SELECT * FROM tb_kategori WHERE id = :id")
    Kategori loadKategoriById(int id);
}
