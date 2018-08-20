package com.bezzo.core.data.local.sampleDB.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.bezzo.core.data.model.JabatanResponse
import com.bezzo.core.util.constanta.AppConstans
import io.reactivex.Flowable

@Dao
interface JabatanDao {
    @Query("SELECT * FROM " + AppConstans.JABATAN)
    fun getAll(): Flowable<List<JabatanResponse.Jabatan>>

    @Query("SELECT * FROM " + AppConstans.JABATAN
            + " LIMIT 1")
    fun get(): Flowable<JabatanResponse.Jabatan>

    @Query("SELECT * FROM " + AppConstans.JABATAN
            + " LIMIT :limit")
    fun getLimit(limit : Int): Flowable<JabatanResponse.Jabatan>

    @Query("SELECT * FROM " + AppConstans.JABATAN
            + " WHERE id=:id")
    fun get(id: Int): Flowable<JabatanResponse.Jabatan>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(value : JabatanResponse.Jabatan)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserts(values : List<JabatanResponse.Jabatan>)

    @Query("DELETE FROM " + AppConstans.JABATAN)
    fun deleteAll()

    @Query("DELETE FROM " + AppConstans.JABATAN
            + " WHERE id=:id")
    fun delete(id: Int)

    @Query("SELECT COUNT(*) FROM " + AppConstans.JABATAN)
    fun count(): Int
}