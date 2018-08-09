package com.bezzo.coreandroid.base

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class BaseResponse<D> {
    @SerializedName("code")
    @Expose
    var code: Int? = null
    @SerializedName("message")
    @Expose
    var message: String? = null
    var data : D? = null
}