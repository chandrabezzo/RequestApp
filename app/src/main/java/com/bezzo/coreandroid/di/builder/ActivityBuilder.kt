package com.bezzo.coreandroid.di.builder

import com.bezzo.coreandroid.features.main.MainActivity
import com.bezzo.coreandroid.features.main.MainModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [(MainModule::class)])
    abstract fun bindMain() : MainActivity
}