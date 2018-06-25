package com.co.jasamedika.coreandroid.data.network

import com.co.jasamedika.coreandroid.data.model.general.Karyawan
import com.co.jasamedika.coreandroid.data.model.jabatan.Jabatan
import com.co.jasamedika.coreandroid.data.model.jabatan.JabatanResponse
import com.co.jasamedika.coreandroid.data.model.user.UserResponse
import io.reactivex.Observable

/**
 * Created by bezzo on 11/01/18.
 */
interface ApiHelperContract {

    fun getUser() : Observable<UserResponse>

    fun getJabatan() : Observable<JabatanResponse>

    fun getKaryawan(params : HashMap<String, String>) : Observable<List<Karyawan>>
}