package com.mdshi.common.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.mdshi.common.db.entity.ContactsEntity;

import java.util.List;

/**
 * Created by MaDeng on 2018/9/19.
 */
@Dao
public interface ContactsDao {

    @Query("SELECT * FROM tb_contacts WHERE userid in(:userid) ORDER BY name")
    public LiveData<List<ContactsEntity>> findAllContacts(long userid);

    @Query("SELECT * FROM tb_contacts WHERE userid = :userid AND contactsid = :contactsId")
    public ContactsEntity findContacts(long userid,long contactsId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(ContactsEntity... entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(List<ContactsEntity> entities);

    @Delete
    public void delete(ContactsEntity... entity);

}
