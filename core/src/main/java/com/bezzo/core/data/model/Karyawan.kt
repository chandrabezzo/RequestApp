package com.bezzo.core.data.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull
import com.bezzo.core.util.constanta.AppConstans
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = AppConstans.KARYAWAN)
class Karyawan {
    @PrimaryKey
    @NonNull
    @SerializedName("id")
    @ColumnInfo(name = "id")
    @Expose
    var id: Int? = null

    @SerializedName("nama")
    @ColumnInfo(name = "nama")
    @Expose
    var nama: String? = null
}