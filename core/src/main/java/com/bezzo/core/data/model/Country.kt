package com.bezzo.core.data.model

import com.bezzo.core.base.BaseResponse
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Country(

    @SerializedName("name")
    @Expose
    var name: String,

    @SerializedName("flag")
    @Expose
    var flag: String
)