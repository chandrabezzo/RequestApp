package com.bezzo.coreandroid.di.component

import android.app.Application
import android.content.Context
import com.bezzo.coreandroid.MvpApp
import com.bezzo.coreandroid.data.DataManagerContract
import com.bezzo.coreandroid.di.ApplicationContext
import com.bezzo.coreandroid.di.module.ApplicationModule
import com.bezzo.coreandroid.service.MessagingInstanceIDService
import com.bezzo.coreandroid.service.MessagingService
import com.mybarber18.partner.service.UpdateLocationService
import dagger.Component
import javax.inject.Singleton

/**
 * Created by bezzo on 26/09/17.
 */

@Singleton
@Component(modules = [(ApplicationModule::class)])
interface ApplicationComponent {

    val dataManager: DataManagerContract

    fun inject(app: MvpApp)

    fun inject(messagingInstanceIDService: MessagingInstanceIDService)

    fun inject(messagingService: MessagingService)

    fun inject(updateLocationService: UpdateLocationService)

    @ApplicationContext
    fun context(): Context

    fun application(): Application
}