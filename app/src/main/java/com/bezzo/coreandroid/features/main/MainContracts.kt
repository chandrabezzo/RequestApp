package com.bezzo.coreandroid.features.main

import com.bezzo.core.base.BaseActivityView
import com.bezzo.core.base.BasePresenterContract
import com.bezzo.core.data.model.JabatanResponse
import com.bezzo.core.data.model.Karyawan
import com.bezzo.core.data.model.Socmed
import com.bezzo.core.data.model.UserResponse

class MainContracts {

    interface View : BaseActivityView {
        fun showUser(user : UserResponse.User)

        fun showJabatan(jabatan : List<JabatanResponse.Jabatan>)

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

        fun addKaryawan(value : Karyawan)
    }
}