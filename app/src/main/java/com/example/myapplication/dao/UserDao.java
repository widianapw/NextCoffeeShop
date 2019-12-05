package com.example.myapplication.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.model.User;

@Dao
public interface UserDao {
    @Insert
    void insertUser(User user);

    @Update
    void updateUser(User user);

    @Query("Select *from tb_user where username = :username and password = :password")
    User loginUser(String username, String password);

    @Query("select *from tb_user where id = :id")
    User getUser(int id);
}
