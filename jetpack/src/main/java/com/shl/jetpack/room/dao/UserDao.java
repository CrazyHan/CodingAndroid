package com.shl.jetpack.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.shl.jetpack.room.entity.User;

import java.util.List;

/**
 * 在注解里面匹配条件使用 "："+参数名   数组使用  (: 参数名 )
 */
@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("select * from user where uid in (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("select*from user where first_name like :first and last_name like :last")
    List<User> findByName(String first, String last);

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);
}
