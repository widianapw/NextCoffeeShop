package com.example.myapplication.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myapplication.dao.KategoriDao;
import com.example.myapplication.dao.KeranjangDao;
import com.example.myapplication.dao.ProdukDao;
import com.example.myapplication.model.Kategori;
import com.example.myapplication.model.Keranjang;
import com.example.myapplication.model.Produk;

@Database(entities = {Kategori.class, Produk.class, Keranjang.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract KategoriDao kategoriDao();

    public abstract ProdukDao produkDao();

    public abstract KeranjangDao keranjangDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "coffee_shop")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
