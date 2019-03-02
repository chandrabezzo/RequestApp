package com.bezzo.core.data.network

import com.bezzo.core.data.model.Country
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {

    @GET("rest/v2/all")
    fun getCountries(@Query("limit") limit: Int): Call<List<Country>>
}