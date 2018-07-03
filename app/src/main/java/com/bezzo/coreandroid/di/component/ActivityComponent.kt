package com.bezzo.coreandroid.di.component

import com.bezzo.coreandroid.di.PerActivity
import com.bezzo.coreandroid.di.module.ActivityModule
import com.bezzo.coreandroid.features.main.MainActivity
import dagger.Component

/**
 * Created by bezzo on 26/09/17.
 */

@PerActivity
@Component(
        dependencies = [(ApplicationComponent::class)],
        modules = [(ActivityModule::class)])
interface ActivityComponent {

    fun inject(mainActivity: MainActivity)
}
