package com.example.myapplication.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.model.Kategori;
import com.example.myapplication.model.Keranjang;
import com.example.myapplication.model.KeranjangWithRelations;
import com.example.myapplication.model.MainKasir;
import com.example.myapplication.model.ProdukWithRelations;

import java.util.List;

@Dao
public interface KeranjangDao {
    @Insert
    void insertKeranjang(Keranjang keranjang);

    @Query("SELECT TOTAL(tb_produk.harga * qty) as total_harga, TOTAL(qty) as total_qty from tb_keranjang INNER JOIN tb_produk on tb_keranjang.id_produk = tb_produk.id")
    MainKasir loadMainKasir();

    @Query("SELECT * from tb_keranjang")
    List<KeranjangWithRelations> loadKeranjang();

    @Query("Select * from tb_keranjang where id = :id")
    KeranjangWithRelations loadKeranjangById(int id);

    @Query("Select * from tb_keranjang where id_produk = :idProduk")
    KeranjangWithRelations loadKeranjangByIdProduk(int idProduk);

    @Update
    void updateKeranjang(Keranjang keranjang);

    @Delete
    void delete(Keranjang keranjang);

    @Query("Delete from tb_keranjang")
    void deleteAll();

}
