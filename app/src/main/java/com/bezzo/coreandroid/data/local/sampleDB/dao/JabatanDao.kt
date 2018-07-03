package com.bezzo.coreandroid.data.local.sampleDB.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.bezzo.coreandroid.data.model.jabatan.Jabatan
import com.bezzo.coreandroid.util.constanta.AppConstans
import io.reactivex.Flowable

@Dao
interface JabatanDao {
    @Query("SELECT * FROM " + AppConstans.JABATAN)
    fun getAll(): Flowable<List<Jabatan>>

    @Query("SELECT * FROM " + AppConstans.JABATAN
            + " LIMIT 1")
    fun get(): Flowable<Jabatan>

    @Query("SELECT * FROM " + AppConstans.JABATAN
            + " LIMIT :limit")
    fun getLimit(limit : Int): Flowable<Jabatan>

    @Query("SELECT * FROM " + AppConstans.JABATAN
            + " WHERE id=:id")
    fun get(id: Int): Flowable<Jabatan>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(value : Jabatan)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserts(values : List<Jabatan>)

    @Query("DELETE FROM " + AppConstans.JABATAN)
    fun deleteAll()

    @Query("DELETE FROM " + AppConstans.JABATAN
            + " WHERE id=:id")
    fun delete(id: Int)

    @Query("SELECT COUNT(*) FROM " + AppConstans.JABATAN)
    fun count(): Int
}