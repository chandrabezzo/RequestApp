package com.bezzo.coreandroid.features.main

import com.bezzo.core.di.PerActivity
import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @Provides
    @PerActivity
    fun provideMainPresenter(presenter: MainPresenter<MainContracts.View>):
            MainContracts.Presenter<MainContracts.View> {
        return presenter
    }
}