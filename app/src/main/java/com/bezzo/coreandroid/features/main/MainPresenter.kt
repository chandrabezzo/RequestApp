package com.bezzo.coreandroid.features.main

import com.androidnetworking.error.ANError
import com.bezzo.core.base.BasePresenter
import com.bezzo.core.data.local.LocalStorageHelper
import com.bezzo.core.data.model.Country
import com.bezzo.core.data.network.ApiHelper
import com.bezzo.core.data.network.ResponseOkHttp
import com.bezzo.core.data.session.SessionHelper
import com.bezzo.core.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import okhttp3.Response
import javax.inject.Inject


/**
 * Created by bezzo on 24/01/18.
 * if you use kotlin, when send to view you must add "?" for null check pointer
 * but if you use java, when send to view you must add if(!isViewAttached) return;
 * before you send data to view
 */

class MainPresenter<V : MainContracts.View> @Inject
constructor(apiHelper: ApiHelper, sessionHelper: SessionHelper, localHelper: LocalStorageHelper,
            schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(apiHelper, sessionHelper, localHelper, schedulerProvider, compositeDisposable), MainContracts.Presenter<V> {

    override fun getCountries(limit: Int) {
        apiHelper.getCountries(limit).getAsOkHttpResponseAndObjectList(Country::class.java,
            object : ResponseOkHttp<ArrayList<Country>>(200){
                override fun onSuccess(response: Response, model: ArrayList<Country>) {
                    view?.dismissProgressDialog()
                    view?.showCountries(model)
                }

                override fun onUnauthorized() {
                    view?.dismissProgressDialog()
                    logout()
                }

                override fun onFailed(response: Response) {
                    view?.dismissProgressDialog()
                    logging(response.message())
                }

                override fun onHasError(error: ANError) {
                    view?.dismissProgressDialog()
                    handleApiError(error)
                }

            })
    }
}