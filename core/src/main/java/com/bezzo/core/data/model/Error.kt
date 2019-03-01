package com.bezzo.core.data.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.ColorInt
import android.support.annotation.NonNull
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by bezzo on 14/11/17.
 */

@Entity(tableName = "Error")
class Error {
    @PrimaryKey
    @NonNull
    @SerializedName("error")
    @ColumnInfo(name = "error")
    @Expose
    var error: String? = null
}
