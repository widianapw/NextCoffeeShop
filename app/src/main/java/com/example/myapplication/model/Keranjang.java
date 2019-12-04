package com.example.myapplication.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "tb_keranjang")
public class Keranjang {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "id_produk")
    private int id_produk;

    @ColumnInfo(name = "qty")
    private int qty;

    public Keranjang(int id, int id_produk, int qty) {
        this.id = id;
        this.id_produk = id_produk;
        this.qty = qty;
    }

    @Ignore
    public Keranjang(int id_produk, int qty) {
        this.id_produk = id_produk;
        this.qty = qty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_produk() {
        return id_produk;
    }

    public void setId_produk(int id_produk) {
        this.id_produk = id_produk;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
