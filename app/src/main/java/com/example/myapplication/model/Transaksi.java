package com.example.myapplication.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "tb_transaksi")
public class Transaksi {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "id_pegawai")
    int id_pegawai;

    @ColumnInfo(name = "tanggal")
    String tanggal;

    public Transaksi(int id, int id_pegawai, String tanggal) {
        this.id = id;
        this.id_pegawai = id_pegawai;
        this.tanggal = tanggal;
    }

    @Ignore
    public Transaksi(int id_pegawai, String tanggal) {
        this.id_pegawai = id_pegawai;
        this.tanggal = tanggal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_pegawai() {
        return id_pegawai;
    }

    public void setId_pegawai(int id_pegawai) {
        this.id_pegawai = id_pegawai;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}