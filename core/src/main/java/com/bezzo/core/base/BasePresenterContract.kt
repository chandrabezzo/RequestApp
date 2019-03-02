package com.bezzo.core.base

/**
 * Created by bezzo on 21/12/17.
 */

interface BasePresenterContract<in V : BaseView> {

    fun onAttach(mvpView: V)

    fun onDetach()

    fun setUserAsLoggedOut()

    fun logout()

    fun clearLog()

    fun logging(message : String?)
}