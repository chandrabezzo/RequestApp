package com.co.jasamedika.coreandroid.data

import com.co.jasamedika.coreandroid.data.local.LocalStorageHelper
import com.co.jasamedika.coreandroid.data.model.general.Karyawan
import com.co.jasamedika.coreandroid.data.model.jabatan.Jabatan
import com.co.jasamedika.coreandroid.data.model.jabatan.JabatanResponse
import com.co.jasamedika.coreandroid.data.model.user.UserResponse
import com.co.jasamedika.coreandroid.data.network.ApiHelperContract
import com.co.jasamedika.coreandroid.data.session.SessionHelperContract
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by bezzo on 26/09/17.
 */

@Singleton
class DataManager @Inject
constructor(private val mSessionHelper: SessionHelperContract,
            private val mApiHelper: ApiHelperContract)
    : DataManagerContract {

    @Inject
    lateinit var local : LocalStorageHelper

    override val localStorageHelper: LocalStorageHelper
        get() = local

    override fun setLogin(isLogin: Boolean) {
        mSessionHelper.setLogin(isLogin)
    }

    override fun isLogin(): Boolean? {
        return mSessionHelper.isLogin()
    }

    override fun getSession(key: String, defaultValue: String): String {
        return mSessionHelper.getSession(key, defaultValue)
    }

    override fun getSession(key: String, defaultValue: Int?): Int? {
        return mSessionHelper.getSession(key, defaultValue)
    }

    override fun getSession(key: String, defaultValue: Double?): Double? {
        return mSessionHelper.getSession(key, defaultValue)
    }

    override fun getSession(key: String, defaultValue: Boolean?): Boolean? {
        return mSessionHelper.getSession(key, defaultValue)
    }

    override fun getSession(key: String, defaultValue: Long?): Long? {
        return mSessionHelper.getSession(key, defaultValue)
    }

    override fun getSession(key: String, defaultValue: Short?): Short? {
        return mSessionHelper.getSession(key, defaultValue)
    }

    override fun getSession(key: String, defaultValue: Byte?): Byte? {
        return mSessionHelper.getSession(key, defaultValue)
    }

    override fun getSession(key: String, defaultValue: Char?): Char? {
        return mSessionHelper.getSession(key, defaultValue)
    }

    override fun getSession(key: String, defaultValue: Float?): Float? {
        return mSessionHelper.getSession(key, defaultValue)
    }

    override fun addSession(key: String, value: String) {
        mSessionHelper.addSession(key, value)
    }

    override fun addSession(key: String, value: Int?) {
        mSessionHelper.addSession(key, value)
    }

    override fun addSession(key: String, value: Double?) {
        mSessionHelper.addSession(key, value)
    }

    override fun addSession(key: String, value: Boolean?) {
        mSessionHelper.addSession(key, value)
    }

    override fun addSession(key: String, value: Long?) {
        mSessionHelper.addSession(key, value)
    }

    override fun addSession(key: String, value: Short?) {
        mSessionHelper.addSession(key, value)
    }

    override fun addSession(key: String, value: Byte?) {
        mSessionHelper.addSession(key, value)
    }

    override fun addSession(key: String, value: Char?) {
        mSessionHelper.addSession(key, value)
    }

    override fun addSession(key: String, value: Float?) {
        mSessionHelper.addSession(key, value)
    }

    override fun deleteSession(key: String) {
        mSessionHelper.deleteSession(key)
    }

    override fun clearSession() {
        mSessionHelper.clearSession()
    }

    override fun getUser(): Observable<UserResponse> {
        return mApiHelper.getUser()
    }

    override fun getJabatan(): Observable<JabatanResponse> {
        return mApiHelper.getJabatan()
    }

    override fun getKaryawan(params: HashMap<String, String>): Observable<List<Karyawan>> {
        return mApiHelper.getKaryawan(params)
    }
}
