package com.bezzo.coreandroid.features.main

import com.bezzo.core.base.BaseActivityView
import com.bezzo.core.base.BasePresenterContract
import com.bezzo.core.data.model.*

class MainContracts {

    interface View : BaseActivityView {
        fun showCountries(values: List<Country>)

        fun showLoadMore()

        fun hideLoadMore()

        fun hideRefreshing()
    }

    interface Presenter<V : View> : BasePresenterContract<V> {
        fun getCountries(limit: Int)
    }
}