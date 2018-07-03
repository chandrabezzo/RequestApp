package com.bezzo.coreandroid.data.model.user

import com.bezzo.coreandroid.base.BaseResponse
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserResponse : BaseResponse() {
    @SerializedName("data")
    @Expose
    var data: User? = null
}