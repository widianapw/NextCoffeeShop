package com.example.myapplication.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myapplication.dao.DetailTransaksiDao;
import com.example.myapplication.dao.KategoriDao;
import com.example.myapplication.dao.KeranjangDao;
import com.example.myapplication.dao.ProdukDao;
import com.example.myapplication.dao.TransaksiDao;
import com.example.myapplication.dao.UserDao;
import com.example.myapplication.model.DetailTransaksi;
import com.example.myapplication.model.Kategori;
import com.example.myapplication.model.Keranjang;
import com.example.myapplication.model.Produk;
import com.example.myapplication.model.Transaksi;
import com.example.myapplication.model.User;

@Database(entities = {Kategori.class, Produk.class, Keranjang.class, User.class, Transaksi.class, DetailTransaksi.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract KategoriDao kategoriDao();

    public abstract ProdukDao produkDao();

    public abstract KeranjangDao keranjangDao();

    public abstract TransaksiDao transaksiDao();

    public abstract DetailTransaksiDao detailTransaksiDao();

    public abstract UserDao userDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "testimoni").allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}