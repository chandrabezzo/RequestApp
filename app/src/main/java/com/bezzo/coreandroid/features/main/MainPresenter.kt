package com.bezzo.coreandroid.features.main

import com.bezzo.coreandroid.base.BasePresenter
import com.bezzo.coreandroid.base.BaseResponse
import com.bezzo.coreandroid.data.local.LocalStorageHelper
import com.bezzo.coreandroid.data.model.general.Karyawan
import com.bezzo.coreandroid.data.model.jabatan.Jabatan
import com.bezzo.coreandroid.data.model.user.Socmed
import com.bezzo.coreandroid.data.model.user.User
import com.bezzo.coreandroid.data.network.ApiHelper
import com.bezzo.coreandroid.data.network.RequestOkHttpHandler
import com.bezzo.coreandroid.data.network.RequestRxHandler
import com.bezzo.coreandroid.data.session.SessionHelperContract
import com.bezzo.coreandroid.util.AppLogger
import com.bezzo.coreandroid.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import retrofit2.Response
import java.util.concurrent.Executors
import javax.inject.Inject


/**
 * Created by bezzo on 24/01/18.
 * if you use kotlin, when send to view you must add "?" for null check pointer
 * but if you use java, when send to view you must add if(!isViewAttached) return;
 * before you send data to view
 */

class MainPresenter<V : MainContracts.View> @Inject
constructor(apiHelper: ApiHelper, sessionHelper: SessionHelperContract, localHelper: LocalStorageHelper,
            schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(apiHelper, sessionHelper, localHelper, schedulerProvider, compositeDisposable), MainContracts.Presenter<V> {

    override fun getKaryawanApi() {
        compositeDisposable.add(apiHelper.employeeServices.getKaryawan("1", "15")
                .compose(schedulerProvider.ioToMainObservableScheduler())
                .subscribe(object : RequestOkHttpHandler<ArrayList<Karyawan>>(200){
                    override fun onSuccess(model: ArrayList<Karyawan>) {
                        view?.showKaryawan(model)
                    }

                    override fun onAuthorized() {
                        logout()
                    }

                    override fun onError(response: Response<ArrayList<Karyawan>>) {
                        logging(response.message())
                    }
                }, Consumer {
                    handleApiError(it)
                }))
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
        compositeDisposable.add(apiHelper.employeeServices.getKaryawan("1", limit.toString())
                .compose(schedulerProvider.ioToMainObservableScheduler())
                .subscribe(object : RequestOkHttpHandler<ArrayList<Karyawan>>(200){
                    override fun onSuccess(model: ArrayList<Karyawan>) {
                        view?.showKaryawan(model)
                    }

                    override fun onAuthorized() {
                        logout()
                    }

                    override fun onError(response: Response<ArrayList<Karyawan>>) {
                        logging(response.message())
                    }

                }))
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
        compositeDisposable.add(apiHelper.employeeServices.getJabatan()
                .compose(schedulerProvider.ioToMainObservableScheduler())
                .subscribe(object : RequestRxHandler<ArrayList<Jabatan>>(200){
                    override fun onSuccess(model: ArrayList<Jabatan>) {
                        view?.showJabatan(model)
                    }

                    override fun onAuthorized() {
                        logout()
                    }

                    override fun onError(response: BaseResponse<ArrayList<Jabatan>>) {
                        logging(response.message)
                    }
                }, Consumer {
                    handleApiError(it)
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
        compositeDisposable.add(apiHelper.employeeServices.getUser()
                .compose(schedulerProvider.ioToMainObservableScheduler())
                .subscribe(object : RequestRxHandler<User>(200){
                    override fun onSuccess(model: User) {
                        view?.showUser(model)
                    }

                    override fun onAuthorized() {
                        logout()
                    }

                    override fun onError(response: BaseResponse<User>) {
                        logging(response.message)
                    }
                }, Consumer {
                    handleApiError(it)
                }))
    }

    override fun getSocmedApi() {
        compositeDisposable.add(apiHelper.employeeServices.getSocmed()
                .compose(schedulerProvider.ioToMainObservableScheduler())
                .subscribe(object : RequestOkHttpHandler<Socmed>(200){
                    override fun onSuccess(model: Socmed) {
                        view?.showSocmed(model)
                    }

                    override fun onAuthorized() {
                        logout()
                    }

                    override fun onError(response: Response<Socmed>) {
                        logging(response.message())
                    }
                }, Consumer {
                    handleApiError(it)
                }))
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
}