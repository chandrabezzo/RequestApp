package com.bezzo.coreandroid.data.network

import com.bezzo.coreandroid.data.model.JabatanResponse
import com.bezzo.coreandroid.data.model.UserResponse
import com.bezzo.coreandroid.data.session.SessionHelper
import com.bezzo.coreandroid.util.SchedulerProvider
import com.rx2androidnetworking.Rx2ANRequest
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by bezzo on 11/01/18.
 */

@Singleton
class ApiHelper @Inject
constructor(val schedulerProvider: SchedulerProvider) : ApiHelperContract {

    @Inject
    lateinit var session : SessionHelper

    override fun getUser(): Observable<UserResponse> {
        return RestApi.get(ApiEndPoint.USER, null, null, null)
                .getObjectObservable(UserResponse::class.java)
                .compose(schedulerProvider.ioToMainObservableScheduler())
    }

    override fun getJabatan(): Observable<JabatanResponse> {
        return RestApi.get(ApiEndPoint.JABATAN, null, null, null)
                .getObjectObservable(JabatanResponse::class.java)
                .compose(schedulerProvider.ioToMainObservableScheduler())
    }

    override fun getKaryawan(page : String, limit : String): Rx2ANRequest {
        var params = HashMap<String, String>()
        params["_page"] = page
        params["_limit"] = limit

        return RestApi.get(ApiEndPoint.KARYAWAN, params, null, null)
    }

    override fun getSocmed(): Rx2ANRequest {
        return RestApi.get(ApiEndPoint.SOCMED, null, null, null)
    }
}
