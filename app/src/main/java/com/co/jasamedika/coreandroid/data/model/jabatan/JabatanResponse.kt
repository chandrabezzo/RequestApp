package com.co.jasamedika.coreandroid.data.model.jabatan

import com.co.jasamedika.coreandroid.base.BaseResponse
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class JabatanResponse : BaseResponse() {
    @SerializedName("data")
    @Expose
    var data: ArrayList<Jabatan>? = null
}