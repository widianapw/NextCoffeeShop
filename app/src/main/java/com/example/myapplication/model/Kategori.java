package com.example.myapplication.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "tb_kategori")
public class Kategori {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") private int id;

    @ColumnInfo(name = "kategori")
    private String kategori;

    public Kategori(int id, String kategori) {
        this.id = id;
        this.kategori = kategori;
    }

    @Ignore
    public Kategori(String kategori) {
        this.kategori = kategori;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }
}
