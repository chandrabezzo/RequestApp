package com.bezzo.core.data.network

import com.bezzo.core.data.model.JabatanResponse
import com.bezzo.core.data.model.Karyawan
import com.bezzo.core.data.model.UserResponse
import com.bezzo.core.data.network.repository.CompanyRepo
import com.bezzo.core.data.session.SessionHelper
import com.bezzo.core.util.SchedulerProvider
import com.rx2androidnetworking.Rx2ANRequest
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by bezzo on 11/01/18.
 */

@Singleton
class ApiHelper @Inject
constructor(val schedulerProvider: SchedulerProvider) {

    @Inject
    lateinit var session : SessionHelper

    var companyRepo = CompanyRepo(schedulerProvider)
}
