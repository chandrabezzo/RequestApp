package com.bezzo.core.data.network

import com.bezzo.core.BuildConfig
import com.bezzo.core.data.model.Country
import com.bezzo.core.data.session.SessionHelper
import com.bezzo.core.util.SchedulerProvider
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by bezzo on 11/01/18.
 */

@Singleton
class ApiHelper @Inject
constructor(val schedulerProvider: SchedulerProvider) {

    @Inject
    lateinit var session : SessionHelper

    private val client = OkHttpClient.Builder().addInterceptor(HttpInterceptor()).build()

    private val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

    private val service = retrofit.create(Service::class.java)

    fun getCountries(limit: Int): Call<List<Country>> {
        return service.getCountries(limit)
    }
}
