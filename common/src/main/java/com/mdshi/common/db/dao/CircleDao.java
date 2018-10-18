package com.mdshi.common.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.mdshi.common.db.entity.CircleEntity;

import java.util.List;

/**
 * Created by MaDeng on 2018/10/17.
 */
@Dao
public interface CircleDao {

    @Query("SELECT * FROM tb_circle ORDER BY create_time DESC")
    LiveData<List<CircleEntity>> getCircleAll();

    @Query("SELECT * FROM tb_circle ORDER BY create_time DESC LIMIT (:pageSize) offset (:pageSize-1)*(:pageNo)")
    LiveData<List<CircleEntity>> getCircleAll(int pageSize, int pageNo);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CircleEntity... data);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<CircleEntity> data);

    @Delete
    void delete(CircleEntity... data);

}
