package com.bezzo.coreandroid.features.main

import com.androidnetworking.error.ANError
import com.bezzo.coreandroid.data.DataManagerContract
import com.bezzo.coreandroid.data.model.jabatan.JabatanResponse
import com.bezzo.coreandroid.data.model.user.UserResponse
import com.bezzo.coreandroid.data.network.ResponseHandler
import com.bezzo.coreandroid.base.BasePresenter
import com.bezzo.coreandroid.data.model.general.Karyawan
import com.bezzo.coreandroid.data.model.user.Socmed
import com.bezzo.coreandroid.data.network.ApiEndPoint
import com.bezzo.coreandroid.data.network.ResponseOkHttp
import com.bezzo.coreandroid.data.network.RestApi
import com.bezzo.coreandroid.util.AppLogger
import com.bezzo.coreandroid.util.SchedulerProvider
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
constructor(dataManager: DataManagerContract, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), MainContracts.Presenter<V> {

    override fun getKaryawanApi() {
        var params = HashMap<String, String>()
        params["_page"] = "1"
        params["_limit"] = "15"

        dataManager.getKaryawan(params)
                .getAsOkHttpResponseAndObjectList(Karyawan::class.java, object : ResponseOkHttp<List<Karyawan>>(200) {
                    override fun onSuccess(response: Response, model: List<Karyawan>) {
                        val exec = Executors.newSingleThreadExecutor()
                        exec.execute {
                            dataManager.localStorageHelper.sampleDatabase.karyawan().deleteAll()
                            dataManager.localStorageHelper.sampleDatabase.karyawan()
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
        compositeDisposable.add(dataManager.localStorageHelper.sampleDatabase.karyawan()
                .getAll().compose(schedulerProvider.ioToMainFlowableScheduler())
                .subscribe({
                    view?.hideRefreshing()
                    view?.showKaryawan(it)
                }, {
                    logging(it.toString())
                }))
    }

    override fun loadMoreKaryawan(limit: Int) {
        var params = HashMap<String, String>()
        params["_page"] = "1"
        params["_limit"] = limit.toString()

        dataManager.getKaryawan(params)
                .getAsOkHttpResponseAndObjectList(Karyawan::class.java, object : ResponseOkHttp<List<Karyawan>>(200) {
                    override fun onSuccess(response: Response, model: List<Karyawan>) {
                        val exec = Executors.newSingleThreadExecutor()
                        exec.execute {
                            dataManager.localStorageHelper.sampleDatabase.karyawan().deleteAll()
                            dataManager.localStorageHelper.sampleDatabase.karyawan()
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
        dataManager.localStorageHelper.sampleDatabase.user().get(1)
                .compose(schedulerProvider.ioToMainFlowableScheduler())
                .subscribe({
                    view?.showUser(it)
                }, {
                    AppLogger.e(it.toString())
                })
    }

    override fun getJabatanApi() {
        compositeDisposable.add(dataManager.getJabatan()
                .subscribe(object : ResponseHandler<JabatanResponse>(200){
                    override fun onSuccess(model: JabatanResponse) {
                        val exec = Executors.newSingleThreadExecutor()
                        exec.execute {
                            dataManager.localStorageHelper.sampleDatabase.jabatan().deleteAll()
                            dataManager.localStorageHelper.sampleDatabase.jabatan()
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
        compositeDisposable.add(dataManager.localStorageHelper.sampleDatabase.jabatan()
                .getAll().compose(schedulerProvider.ioToMainFlowableScheduler())
                .subscribe({
                    view?.showJabatan(it)
                }, {
                    logging(it.toString())
                }))
    }

    override fun getUserApi() {
        compositeDisposable.add(dataManager.getUser()
                .subscribe(object : ResponseHandler<UserResponse>(200){
                    override fun onSuccess(model: UserResponse) {
                        val exec = Executors.newSingleThreadExecutor()
                        exec.execute {
                            dataManager.localStorageHelper.sampleDatabase.user().deleteAll()
                            dataManager.localStorageHelper.sampleDatabase.user()
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
        dataManager.getSocmed()
                .getAsOkHttpResponseAndObject(Socmed::class.java, object : ResponseOkHttp<Socmed>(200){
                    override fun onSuccess(response: Response, model: Socmed) {
                        val exec = Executors.newSingleThreadExecutor()
                        exec.execute {
                            dataManager.localStorageHelper.sampleDatabase.socmed().deleteAll()
                            dataManager.localStorageHelper.sampleDatabase.socmed()
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
        compositeDisposable.add(dataManager.localStorageHelper.sampleDatabase.socmed().get()
                .compose(schedulerProvider.ioToMainFlowableScheduler())
                .subscribe({
                    view?.showSocmed(it)
                }, {
                    logging(it.message)
                }))
    }
}