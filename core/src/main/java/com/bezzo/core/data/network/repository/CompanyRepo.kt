package com.bezzo.core.data.network.repository

import com.bezzo.core.data.model.JabatanResponse
import com.bezzo.core.data.model.Karyawan
import com.bezzo.core.data.model.UserResponse
import com.bezzo.core.data.network.ApiEndPoint
import com.bezzo.core.data.network.RestApi
import com.bezzo.core.util.SchedulerProvider
import com.rx2androidnetworking.Rx2ANRequest
import io.reactivex.Observable

class CompanyRepo constructor(val schedulerProvider: SchedulerProvider) {

    fun getUser(): Observable<UserResponse> {
        return RestApi.get(ApiEndPoint.USER, null, null, null)
                .getObjectObservable(UserResponse::class.java)
                .compose(schedulerProvider.ioToMainObservableScheduler())
    }

    fun getJabatan(): Observable<JabatanResponse> {
        return RestApi.get(ApiEndPoint.JABATAN, null, null, null)
                .getObjectObservable(JabatanResponse::class.java)
                .compose(schedulerProvider.ioToMainObservableScheduler())
    }

    fun getKaryawan(page : String, limit : String): Rx2ANRequest {
        var params = HashMap<String, String>()
        params["_page"] = page
        params["_limit"] = limit

        return RestApi.get(ApiEndPoint.KARYAWAN, params, null, null)
    }

    fun addKaryawan(value : Karyawan): Rx2ANRequest {
        return RestApi.post(ApiEndPoint.KARYAWAN, null, null, null, value)
    }

    fun getSocmed(): Rx2ANRequest {
        return RestApi.get(ApiEndPoint.SOCMED, null, null, null)
    }
}