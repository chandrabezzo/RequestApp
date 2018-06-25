package com.co.jasamedika.coreandroid.data.model.user

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull
import com.co.jasamedika.coreandroid.util.constanta.AppConstans
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by bezzo on 14/11/17.
 */

@Entity(tableName = AppConstans.USER)
class User {
    @PrimaryKey
    @NonNull
    @SerializedName("id")
    @ColumnInfo(name = "id")
    @Expose
    var id : Int? = null

    @SerializedName("nama")
    @ColumnInfo(name = "nama")
    @Expose
    var nama: String? = null

    @SerializedName("jabatan")
    @ColumnInfo(name = "jabatan")
    @Expose
    var jabatan: String? = null
}
