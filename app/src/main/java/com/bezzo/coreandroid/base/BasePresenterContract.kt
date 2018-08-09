package com.bezzo.coreandroid.base

/**
 * Created by bezzo on 21/12/17.
 */

interface BasePresenterContract<in V : BaseView> {

    fun onAttach(mvpView: V)

    fun onDetach()

    fun handleApiError(error: Throwable)

    fun setUserAsLoggedOut()

    fun logout()

    fun clearLog()

    fun logging(message : String?)
}