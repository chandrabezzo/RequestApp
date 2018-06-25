package com.co.jasamedika.coreandroid.di.component

import android.app.Application
import android.content.Context
import com.co.jasamedika.coreandroid.MvpApp
import com.co.jasamedika.coreandroid.data.DataManagerContract
import com.co.jasamedika.coreandroid.di.ApplicationContext
import com.co.jasamedika.coreandroid.di.module.ApplicationModule
import com.co.jasamedika.coreandroid.service.MessagingInstanceIDService
import com.co.jasamedika.coreandroid.service.MessagingService
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
