package com.bezzo.core.data.local.sampleDB.converter

import android.arch.persistence.room.TypeConverter
import com.bezzo.core.data.model.Alamat
import com.google.gson.Gson

class AlamatConverter {
    @TypeConverter
    fun jsonToObject(value : String) : Alamat {
        return Gson().fromJson<Alamat>(value, Alamat::class.java)
    }

    @TypeConverter
    fun objectToJson(value : Alamat) : String {
        return Gson().toJson(value)
    }
}