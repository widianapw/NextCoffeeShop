package com.example.myapplication.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.PrimaryKey;

@Entity(tableName = "tb_user")
public class User {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "username")
    private String username;

    @ColumnInfo(name = "password")
    private String password;

    @ColumnInfo(name = "nama")
    private String nama;

    @ColumnInfo(name = "telepon")
    private String telepon;

    public User(int id, String username, String password, String nama, String telepon) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nama = nama;
        this.telepon = telepon;
    }

    @Ignore
    public User(String username, String nama, String telepon) {
        this.username = username;
        this.nama = nama;
        this.telepon = telepon;
    }

    @Ignore
    public User(String username, String password, String nama, String telepon) {
        this.username = username;
        this.password = password;
        this.nama = nama;
        this.telepon = telepon;
    }

    @Ignore
    public User(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }
}
