package com.co.jasamedika.coreandroid.base

import android.widget.Toast
import com.androidnetworking.error.ANError
import com.co.jasamedika.coreandroid.data.DataManagerContract
import com.co.jasamedika.coreandroid.data.model.general.ApiError
import com.co.jasamedika.coreandroid.data.session.SessionConstants
import com.co.jasamedika.coreandroid.util.AppLogger
import com.co.jasamedika.coreandroid.util.CommonUtils
import com.co.jasamedika.coreandroid.util.SchedulerProvider
import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.Executors
import javax.inject.Inject

/**
 * Created by bezzo on 26/09/17.
 */

open class BasePresenter<V : BaseView> @Inject
constructor(val dataManager: DataManagerContract,
            val schedulerProvider : SchedulerProvider,
            val compositeDisposable: CompositeDisposable) : BasePresenterContract<V> {

    @Inject
    lateinit var gson: Gson

    var view: V? = null
        private set

    val isViewAttached: Boolean
        get() = view != null

    override fun onAttach(mvpView: V) {
        view = mvpView
    }

    override fun onDetach() {
        compositeDisposable.dispose()
        view = null
    }

    override fun handleApiError(error: ANError) {
        if (CommonUtils.isJSONValid(error.errorBody)){
            val apiError = gson.fromJson(error.errorBody,
                    ApiError::class.java)

            if (apiError != null) {
                if (error.errorCode == 401) {
                    logout()
                } else {
                    if (view != null){
                        view?.showToast(apiError.message!!, Toast.LENGTH_SHORT)
                    }
                }
            } else {
                if (view != null){
                    view?.showToast(error.message!!, Toast.LENGTH_SHORT)
                }
                else {
                    view?.showToast("Ada Kesalahan", Toast.LENGTH_SHORT)
                }
            }
        }
        else {
            if (view != null){
                if (error.toString().contains("UnknownHost")){
                    view?.showToast("Service tidak ditemukan", Toast.LENGTH_SHORT)
                }
                else if (error.toString().contains("timed out") || error.toString().contains("timeout")){
                    view?.showToast("Pastikan internet Anda stabil", Toast.LENGTH_SHORT)
                }
                else if (error.toString().contains("java") || error.toString().contains("html")){
                    view?.showToast("Kesalahan Server", Toast.LENGTH_SHORT)
                }
                else if (error.errorBody != null) {
                    if (error.errorBody.contains("html") || error.errorBody.contains("java")) {
                        view?.showToast("Kesalahan Server", Toast.LENGTH_SHORT)
                    }
                    else {
                        view?.showToast("Ada Kesalahan", Toast.LENGTH_SHORT)
                    }
                }
                else {
                    view?.showToast("Tidak terhubung ke server", Toast.LENGTH_SHORT)
                }
            }
        }
    }

    override fun setUserAsLoggedOut() {
        dataManager
    }

    override fun clearLog() {
        dataManager.setLogin(false)
        dataManager.deleteSession(SessionConstants.TOKEN)

        val exec = Executors.newSingleThreadExecutor()
        exec.execute { }
    }

    override fun logout() {
        clearLog()

        view?.showToast("You Have No Access", Toast.LENGTH_SHORT)
        view?.openActivityOnTokenExpire()
    }

    companion object {
        private val TAG = "BasePresenter"
    }

    override fun logging(message: String?) {
        if (message != null){
            AppLogger.i(message)
            view?.showToast(message, Toast.LENGTH_SHORT)
        }
    }
}
