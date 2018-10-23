package com.mdshi.common.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import com.mdshi.common.db.entity.ContactsEntity;

import java.util.List;

/**
 * Created by MaDeng on 2018/9/19.
 */
@Dao
public abstract class ContactsDao {

    @Query("SELECT * FROM tb_contacts WHERE id in(:userid) ORDER BY contacts_name")
    public abstract LiveData<List<ContactsEntity>> findAllContacts(long userid);

    @Query("SELECT * FROM tb_contacts WHERE id = :userid AND contacts_id = :contactsId")
    public abstract ContactsEntity findContacts(long userid,long contactsId);
    @Query("SELECT * FROM tb_contacts WHERE id = :userid AND contacts_id = :contactsId")
    public abstract LiveData<ContactsEntity> findContactsToLiveData(long userid,long contactsId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(ContactsEntity... entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(List<ContactsEntity> entities);

    @Delete
    public abstract void delete(ContactsEntity... entity);

    @Query("DELETE FROM tb_contacts WHERE id = :userId")
    public abstract void delete(long userId);
    @Transaction
    public void updateContacts(List<ContactsEntity> entities,long userId) {
         delete(userId);
         insert(entities);
    }
}
