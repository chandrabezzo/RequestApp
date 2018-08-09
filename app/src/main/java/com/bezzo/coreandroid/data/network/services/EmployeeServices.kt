package com.bezzo.coreandroid.data.network.services

import com.bezzo.coreandroid.base.BaseResponse
import com.bezzo.coreandroid.data.model.general.Karyawan
import com.bezzo.coreandroid.data.model.jabatan.Jabatan
import com.bezzo.coreandroid.data.model.user.Socmed
import com.bezzo.coreandroid.data.model.user.User
import com.bezzo.coreandroid.data.network.ApiEndPoint
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface EmployeeServices {
    @GET(ApiEndPoint.USER)
    fun getUser() : Observable<BaseResponse<User>>

    @GET(ApiEndPoint.JABATAN)
    fun getJabatan() : Observable<BaseResponse<ArrayList<Jabatan>>>

    @GET(ApiEndPoint.KARYAWAN)
    fun getKaryawan(@Query("_page") page : String, @Query("_limit") limit : String)
        : Observable<Response<ArrayList<Karyawan>>>

    @GET(ApiEndPoint.SOCMED)
    fun getSocmed() : Observable<Response<Socmed>>
}