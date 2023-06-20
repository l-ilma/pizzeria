package com.example.pizzeria.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.pizzeria.entity.User;

import java.util.List;


@Dao
public interface UserDao {
    @Insert
    public void insertAll(List<User> users);
    @Query("SELECT * FROM user")
    User getAll();

    @Query("SELECT * FROM user WHERE email = :email AND password = :password")
    User findByCredentials(String email, String password);

    @Query("SELECT * FROM user WHERE isLoggedIn = 1")
    User findLoggedInUser();

    @Insert
    void insert(User user);

    @Query("UPDATE user SET isLoggedIn = :isLoggedIn WHERE email = :email")
    void setIsLoggedIn(String email, boolean isLoggedIn);

    @Query("SELECT EXISTS(SELECT * FROM user WHERE username = :username OR email = :email)")
    boolean verifyCredentialsUniqueness(String username, String email);
}
