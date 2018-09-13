package com.mdshi.common.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.mdshi.common.db.entity.UserEntity;

/**
 * Created by MaDeng on 2018/9/13.
 */
@Dao
public interface UserDao {

    @Query("SELECT * FROM tb_user WHERE id IN (:id)")
    UserEntity getUser(long id);

    @Insert
    void insertUser(UserEntity entity);

    @Update
    void updateUser(UserEntity entity);

    @Delete
    void deleteUser(UserEntity entity);
}
