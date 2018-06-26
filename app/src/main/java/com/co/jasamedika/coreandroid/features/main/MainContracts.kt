package com.co.jasamedika.coreandroid.features.main

import com.co.jasamedika.coreandroid.data.model.user.User
import com.co.jasamedika.coreandroid.base.BaseActivityView
import com.co.jasamedika.coreandroid.base.BasePresenterContract
import com.co.jasamedika.coreandroid.data.model.general.Karyawan
import com.co.jasamedika.coreandroid.data.model.jabatan.Jabatan
import com.co.jasamedika.coreandroid.data.model.user.Socmed

class MainContracts {

    interface View : BaseActivityView {
        fun showUser(user : User)

        fun showJabatan(jabatan : List<Jabatan>)

        fun showLoadMore()

        fun hideLoadMore()

        fun showKaryawan(values : List<Karyawan>)

        fun hideRefreshing()

        fun showSocmed(value : Socmed)
    }

    interface Presenter<V : View> : BasePresenterContract<V> {
        fun getUserApi()

        fun getUserLocal()

        fun getJabatanApi()

        fun getAllJabatan()

        fun getKaryawanApi()

        fun getAllKaryawan()

        fun loadMoreKaryawan(limit : Int)

        fun getSocmedApi()

        fun getSocmed()
    }
}