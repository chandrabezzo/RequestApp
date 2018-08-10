package com.bezzo.coreandroid.data.local.sampleDB.converter

import android.arch.persistence.room.TypeConverter
import com.bezzo.coreandroid.data.model.Alamat
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

// Sample if object as list

//    @TypeConverter
//    fun gsonToList(value : String) : List<Model> {
//        val listType = object : TypeToken<List<Model>>() {}.type
//        return Gson().fromJson<List<Model>>(value, listType)
//    }
//
//    @TypeConverter
//    fun listToGson(list : List<Model>) : String {
//        return Gson().toJson(list)
//    }
}