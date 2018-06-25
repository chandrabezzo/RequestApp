package com.co.jasamedika.coreandroid.data.local.sampleDB.converter

import android.arch.persistence.room.TypeConverter
import com.co.jasamedika.coreandroid.data.model.user.User
import com.google.gson.Gson

class SampleConverter {

    @TypeConverter
    fun jsonToModel(json : String) : User {
        return Gson().fromJson<User>(json, User::class.java)
    }

    @TypeConverter
    fun modelToJson(value : User) : String {
        return Gson().toJson(value)
    }
}