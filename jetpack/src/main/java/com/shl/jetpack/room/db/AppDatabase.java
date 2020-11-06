package com.shl.jetpack.room.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.shl.jetpack.room.dao.UserDao;
import com.shl.jetpack.room.entity.User;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
