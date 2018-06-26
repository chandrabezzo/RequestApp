package com.co.jasamedika.coreandroid.data.network

import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.OkHttpResponseAndParsedRequestListener
import com.co.jasamedika.coreandroid.data.model.general.Karyawan
import com.co.jasamedika.coreandroid.data.model.jabatan.Jabatan
import com.co.jasamedika.coreandroid.data.model.jabatan.JabatanResponse
import com.co.jasamedika.coreandroid.data.model.user.UserResponse
import com.co.jasamedika.coreandroid.data.session.SessionHelperContract
import com.co.jasamedika.coreandroid.util.SchedulerProvider
import com.rx2androidnetworking.Rx2ANRequest
import io.reactivex.Observable
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by bezzo on 11/01/18.
 */

@Singleton
class ApiHelper @Inject
constructor(val schedulerProvider: SchedulerProvider) : ApiHelperContract {

    @Inject
    lateinit var session : SessionHelperContract

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

    override fun getKaryawan(params: HashMap<String, String>): Rx2ANRequest {
        return RestApi.get(ApiEndPoint.KARYAWAN, params, null, null)
    }

    override fun getSocmed(): Rx2ANRequest {
        return RestApi.get(ApiEndPoint.SOCMED, null, null, null)
    }
}
