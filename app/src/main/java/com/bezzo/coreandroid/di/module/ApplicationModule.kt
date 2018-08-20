package com.bezzo.coreandroid.di.module

import android.app.Application
import android.content.Context
import com.bezzo.core.di.ApplicationContext
import com.bezzo.core.util.SchedulerProvider
import com.bezzo.core.util.constanta.AppConstans
import com.bezzo.core.data.local.LocalStorageHelper
import com.bezzo.core.data.network.ApiHelper
import com.bezzo.core.data.network.ApiHelperContract
import com.bezzo.core.data.session.SessionHelper
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

/**
 * Created by bezzo on 26/09/17.
 */

@Module
class ApplicationModule {

    @Provides
    @ApplicationContext
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    fun provideDatabaseName(): String {
        return AppConstans.DB_NAME
    }

    @Provides
    @Singleton
    fun provideLocalStorageHelper(application: Application) = LocalStorageHelper(application)

    @Provides
    @Singleton
    fun provideSessionHelper() = SessionHelper()

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: ApiHelper): ApiHelperContract {
        return apiHelper
    }

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider = SchedulerProvider()
}
