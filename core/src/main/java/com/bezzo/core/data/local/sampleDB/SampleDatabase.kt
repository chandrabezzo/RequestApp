package com.bezzo.core.data.local.sampleDB

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.arch.persistence.room.migration.Migration
import android.content.Context
import com.bezzo.core.data.local.sampleDB.converter.AlamatConverter
import com.bezzo.core.data.local.sampleDB.dao.JabatanDao
import com.bezzo.core.data.local.sampleDB.dao.KaryawanDao
import com.bezzo.core.data.local.sampleDB.dao.SocmedDao
import com.bezzo.core.data.local.sampleDB.dao.UserDao
import com.bezzo.core.data.model.JabatanResponse
import com.bezzo.core.data.model.Karyawan
import com.bezzo.core.data.model.Socmed
import com.bezzo.core.data.model.UserResponse
import com.bezzo.core.di.ApplicationContext
import com.bezzo.core.util.constanta.AppConstans

/**
 * Created by bezzo on 11/01/18.
 * Add more entities = arrayOf(UserLokal::class, SampleBTable::class)
 * Add more converter must unique
 */
@Database(entities =
    [(UserResponse.User::class), (JabatanResponse.Jabatan::class), (Karyawan::class),
    (Socmed::class)], version = 1)
@TypeConverters(AlamatConverter::class)
abstract class SampleDatabase : RoomDatabase() {

    abstract fun user() : UserDao
    abstract fun jabatan() : JabatanDao
    abstract fun karyawan() : KaryawanDao
    abstract fun socmed() : SocmedDao

    companion object {
        @Volatile private var INSTANCE: SampleDatabase? = null

        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Since we didn't alter the table, there's nothing else to do here.
            }
        }

        fun getInstance(@ApplicationContext context: Context): SampleDatabase {
            if (INSTANCE == null) {
                synchronized(SampleDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context,
                                SampleDatabase::class.java, AppConstans.DB_NAME)
                                .fallbackToDestructiveMigration().build()
                    }
                }
            }
            return INSTANCE!!
        }
    }

    override fun clearAllTables() {

    }
}