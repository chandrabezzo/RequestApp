package com.bezzo.coreandroid.features.main

import android.widget.Toast
import com.androidnetworking.error.ANError
import com.bezzo.core.util.AppLogger
import com.bezzo.core.util.SchedulerProvider
import com.bezzo.core.base.BasePresenter
import com.bezzo.core.data.local.LocalStorageHelper
import com.bezzo.core.data.model.JabatanResponse
import com.bezzo.core.data.model.Karyawan
import com.bezzo.core.data.model.Socmed
import com.bezzo.core.data.model.UserResponse
import com.bezzo.core.data.network.ApiHelper
import com.bezzo.core.data.network.ResponseHandler
import com.bezzo.core.data.network.ResponseOkHttp
import com.bezzo.core.data.session.SessionHelper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import okhttp3.Response
import java.util.concurrent.Executors
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

    override fun getKaryawanApi() {
        apiHelper.getKaryawan("1", "15")
                .getAsOkHttpResponseAndObjectList(Karyawan::class.java, object : ResponseOkHttp<List<Karyawan>>(200) {
                    override fun onSuccess(response: Response, model: List<Karyawan>) {
                        val exec = Executors.newSingleThreadExecutor()
                        exec.execute {
                            localHelper.sampleDatabase.karyawan().deleteAll()
                            localHelper.sampleDatabase.karyawan()
                                    .inserts(model)
                        }

                        getAllKaryawan()
                    }

                    override fun onUnauthorized() {
                        logout()
                    }

                    override fun onFailed(response: Response) {
                        logging(response.message())
                    }

                    override fun onHasError(error: ANError) {
                        handleApiError(error)
                    }

                })
    }

    override fun getAllKaryawan() {
        compositeDisposable.add(localHelper.sampleDatabase.karyawan()
                .getAll().compose(schedulerProvider.ioToMainFlowableScheduler())
                .subscribe({
                    view?.hideRefreshing()
                    view?.showKaryawan(it)
                }, {
                    logging(it.toString())
                }))
    }

    override fun loadMoreKaryawan(limit: Int) {
        apiHelper.getKaryawan("1", limit.toString())
                .getAsOkHttpResponseAndObjectList(Karyawan::class.java, object : ResponseOkHttp<List<Karyawan>>(200) {
                    override fun onSuccess(response: Response, model: List<Karyawan>) {
                        val exec = Executors.newSingleThreadExecutor()
                        exec.execute {
                            localHelper.sampleDatabase.karyawan().deleteAll()
                            localHelper.sampleDatabase.karyawan()
                                    .inserts(model)
                        }

                        view?.hideLoadMore()
                        getAllKaryawan()
                    }

                    override fun onUnauthorized() {
                        logout()
                    }

                    override fun onFailed(response: Response) {
                        logging(response.message())
                    }

                    override fun onHasError(error: ANError) {
                        handleApiError(error)
                    }

                })
    }

    override fun getUserLocal() {
        localHelper.sampleDatabase.user().get(1)
                .compose(schedulerProvider.ioToMainFlowableScheduler())
                .subscribe({
                    view?.showUser(it)
                }, {
                    AppLogger.e(it.toString())
                })
    }

    override fun getJabatanApi() {
        compositeDisposable.add(apiHelper.getJabatan()
                .subscribe(object : ResponseHandler<JabatanResponse>(200){
                    override fun onSuccess(model: JabatanResponse) {
                        val exec = Executors.newSingleThreadExecutor()
                        exec.execute {
                            localHelper.sampleDatabase.jabatan().deleteAll()
                            localHelper.sampleDatabase.jabatan()
                                    .inserts(model.data!!)
                        }

                        getAllJabatan()
                    }

                    override fun onUnauthorized() {
                        logout()
                    }

                    override fun onError(model: JabatanResponse) {
                        logging(model.message)
                    }

                }, Consumer {
                   if (it is ANError) {
                       handleApiError(it)
                   }
                }))
    }

    override fun getAllJabatan() {
        compositeDisposable.add(localHelper.sampleDatabase.jabatan()
                .getAll().compose(schedulerProvider.ioToMainFlowableScheduler())
                .subscribe({
                    view?.showJabatan(it)
                }, {
                    logging(it.toString())
                }))
    }

    override fun getUserApi() {
        compositeDisposable.add(apiHelper.getUser()
                .subscribe(object : ResponseHandler<UserResponse>(200){
                    override fun onSuccess(model: UserResponse) {
                        val exec = Executors.newSingleThreadExecutor()
                        exec.execute {
                            localHelper.sampleDatabase.user().deleteAll()
                            localHelper.sampleDatabase.user()
                                    .insert(model.data!!)
                        }

                        getUserLocal()
                    }

                    override fun onUnauthorized() {
                        logout()
                    }

                    override fun onError(model: UserResponse) {
                        logging(model.message)
                    }

                }, Consumer {
                    if (it is ANError){
                        handleApiError(it)
                    }
                }))
    }

    override fun getSocmedApi() {
        apiHelper.getSocmed()
                .getAsOkHttpResponseAndObject(Socmed::class.java, object : ResponseOkHttp<Socmed>(200){
                    override fun onSuccess(response: Response, model: Socmed) {
                        val exec = Executors.newSingleThreadExecutor()
                        exec.execute {
                            localHelper.sampleDatabase.socmed().deleteAll()
                            localHelper.sampleDatabase.socmed()
                                    .insert(model)
                        }

                        getSocmed()
                    }

                    override fun onUnauthorized() {
                        logout()
                    }

                    override fun onFailed(response: Response) {
                        logging(response.message())
                    }

                    override fun onHasError(error: ANError) {
                        handleApiError(error)
                    }
                })
    }

    override fun getSocmed() {
        compositeDisposable.add(localHelper.sampleDatabase.socmed().get()
                .compose(schedulerProvider.ioToMainFlowableScheduler())
                .subscribe({
                    view?.showSocmed(it)
                }, {
                    logging(it.message)
                }))
    }

    override fun addKaryawan(value: Karyawan) {
        apiHelper.addKaryawan(value)
                .getAsOkHttpResponseAndObject(Karyawan::class.java, object : ResponseOkHttp<Karyawan>(201){
                    override fun onSuccess(response: Response, model: Karyawan) {
                        view?.showToast("yeah", Toast.LENGTH_SHORT)
                    }

                    override fun onUnauthorized() {
                        logout()
                    }

                    override fun onFailed(response: Response) {
                        logging(response.message())
                    }

                    override fun onHasError(error: ANError) {
                        handleApiError(error)
                    }

                })
    }
}