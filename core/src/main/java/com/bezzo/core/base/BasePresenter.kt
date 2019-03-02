package com.bezzo.core.base

import android.widget.Toast
import com.bezzo.core.data.local.LocalStorageHelper
import com.bezzo.core.data.network.ApiHelper
import com.bezzo.core.data.session.SessionConstants
import com.bezzo.core.data.session.SessionHelper
import com.bezzo.core.util.AppLogger
import com.bezzo.core.util.SchedulerProvider
import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.Executors
import javax.inject.Inject

/**
 * Created by bezzo on 26/09/17.
 */

open class BasePresenter<V : BaseView> @Inject
constructor(val apiHelper: ApiHelper,
            val sessionHelper : SessionHelper,
            val localHelper : LocalStorageHelper,
            val schedulerProvider: SchedulerProvider,
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

    override fun setUserAsLoggedOut() {

    }

    override fun clearLog() {
        sessionHelper.setLogin(false)
        sessionHelper.deleteSession(SessionConstants.TOKEN)

        val exec = Executors.newSingleThreadExecutor()
        exec.execute { }
    }

    override fun logout() {
        clearLog()

        view?.handleError(5)
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
