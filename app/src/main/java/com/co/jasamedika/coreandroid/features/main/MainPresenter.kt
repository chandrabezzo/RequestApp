package com.co.jasamedika.coreandroid.features.main

import com.androidnetworking.error.ANError
import com.co.jasamedika.coreandroid.data.DataManagerContract
import com.co.jasamedika.coreandroid.data.model.jabatan.JabatanResponse
import com.co.jasamedika.coreandroid.data.model.user.UserResponse
import com.co.jasamedika.coreandroid.data.network.ResponseHandler
import com.co.jasamedika.coreandroid.base.BasePresenter
import com.co.jasamedika.coreandroid.util.AppLogger
import com.co.jasamedika.coreandroid.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
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

        compositeDisposable.add(dataManager.getKaryawan(params)
                .subscribe({
                    val exec = Executors.newSingleThreadExecutor()
                    exec.execute {
                        dataManager.localStorageHelper.sampleDatabase.karyawan().deleteAll()
                        dataManager.localStorageHelper.sampleDatabase.karyawan()
                                .inserts(it)
                    }

                    getAllKaryawan()
                }, {
                    if (it is ANError){
                        handleApiError(it)
                    }
                }))
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

        compositeDisposable.add(dataManager.getKaryawan(params)
                .subscribe({
                    val exec = Executors.newSingleThreadExecutor()
                    exec.execute {
                        dataManager.localStorageHelper.sampleDatabase.karyawan().deleteAll()
                        dataManager.localStorageHelper.sampleDatabase.karyawan()
                                .inserts(it)
                    }

                    view?.hideLoadMore()
                    getAllKaryawan()
                }, {
                    if (it is ANError){
                        handleApiError(it)
                    }
                }))
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
                .subscribe({
                    val exec = Executors.newSingleThreadExecutor()
                    exec.execute {
                        dataManager.localStorageHelper.sampleDatabase.jabatan().deleteAll()
                        dataManager.localStorageHelper.sampleDatabase.jabatan()
                                .inserts(it.data!!)
                    }

                    getAllJabatan()
                }, {
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


}