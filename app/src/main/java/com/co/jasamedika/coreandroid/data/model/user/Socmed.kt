package com.co.jasamedika.coreandroid.data.model.user

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull
import com.co.jasamedika.coreandroid.util.constanta.AppConstans
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = AppConstans.SOCMED)
class Socmed {
    @SerializedName("linkedin")
    @ColumnInfo(name = "linkedin")
    @Expose
    var linkedin: String? = null

    @PrimaryKey
    @NonNull
    @SerializedName("email")
    @ColumnInfo(name = "email")
    @Expose
    var email: String? = null
}