package com.bezzo.coreandroid.data.network

import com.bezzo.coreandroid.data.model.general.Karyawan
import com.bezzo.coreandroid.data.model.jabatan.Jabatan
import com.bezzo.coreandroid.data.model.jabatan.JabatanResponse
import com.bezzo.coreandroid.data.model.user.UserResponse
import com.rx2androidnetworking.Rx2ANRequest
import io.reactivex.Observable

/**
 * Created by bezzo on 11/01/18.
 */
interface ApiHelperContract {

    fun getUser() : Observable<UserResponse>

    fun getJabatan() : Observable<JabatanResponse>

    fun getKaryawan(params : HashMap<String, String>) : Rx2ANRequest

    fun getSocmed() : Rx2ANRequest
}