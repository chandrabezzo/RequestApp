package com.bezzo.core.data.network

import com.bezzo.core.data.model.JabatanResponse
import com.bezzo.core.data.model.Karyawan
import com.bezzo.core.data.model.UserResponse
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

    fun addKaryawan(value : Karyawan) : Rx2ANRequest
}