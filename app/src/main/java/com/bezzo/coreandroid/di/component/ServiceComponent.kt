package com.bezzo.coreandroid.di.component

import com.bezzo.coreandroid.di.PerService
import com.bezzo.coreandroid.di.module.ServiceModule
import com.bezzo.coreandroid.service.MessagingInstanceIDService
import com.bezzo.coreandroid.service.MessagingService
import com.mybarber18.partner.service.UpdateLocationService
import dagger.Component

/**
 * Created by bezzo on 26/09/17.
 */

@PerService
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = [(ServiceModule::class)])
interface ServiceComponent {

    fun inject(messagingInstanceIDService: MessagingInstanceIDService)

    fun inject(messagingService: MessagingService)

    fun inject(updateLocationService: UpdateLocationService)
}