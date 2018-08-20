package com.bezzo.core.data.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull
import com.bezzo.core.base.BaseResponse
import com.bezzo.core.util.constanta.AppConstans
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class JabatanResponse : BaseResponse() {
    @SerializedName("data")
    @Expose
    var data: ArrayList<Jabatan>? = null

    @Entity(tableName = AppConstans.JABATAN)
    class Jabatan {
        @PrimaryKey
        @NonNull
        @SerializedName("id")
        @ColumnInfo(name = "id")
        @Expose
        var id: Int? = null

        @SerializedName("jenis")
        @ColumnInfo(name = "jenis")
        @Expose
        var jenis: String? = null
    }
}