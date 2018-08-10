package com.bezzo.coreandroid.data.network

import com.bezzo.coreandroid.data.model.JabatanResponse
import com.bezzo.coreandroid.data.model.UserResponse
import com.rx2androidnetworking.Rx2ANRequest
import io.reactivex.Observable

/**
 * Created by bezzo on 11/01/18.
 */
interface ApiHelperContract {

    fun getUser() : Observable<UserResponse>

    fun getJabatan() : Observable<JabatanResponse>

    fun getKaryawan(page : String, limit : String) : Rx2ANRequest

    fun getSocmed() : Rx2ANRequest
}