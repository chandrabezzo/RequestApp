package com.bezzo.coreandroid.data.network

import android.content.Context
import com.bezzo.coreandroid.BuildConfig
import com.bezzo.coreandroid.data.network.services.EmployeeServices
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by bezzo on 11/01/18.
 */

@Singleton
class ApiHelper @Inject
constructor(val context: Context){

    var employeeServices = NetworkClient.create(context, BuildConfig.BASE_URL)
            .create(EmployeeServices::class.java)

}
