package com.bezzo.coreandroid.di.module

import android.app.Application
import android.content.Context
import com.bezzo.coreandroid.data.local.LocalStorageHelper
import com.bezzo.coreandroid.data.local.LocalStorageHelperContract
import com.bezzo.coreandroid.data.network.ApiHelper
import com.bezzo.coreandroid.data.network.ApiHelperContract
import com.bezzo.coreandroid.data.session.SessionHelper
import com.bezzo.coreandroid.data.session.SessionHelperContract
import com.bezzo.coreandroid.di.ApplicationContext
import com.bezzo.coreandroid.util.SchedulerProvider
import com.bezzo.coreandroid.util.constanta.AppConstans
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by bezzo on 26/09/17.
 */

@Module
class ApplicationModule(private val mApplication: Application) {

    @Provides
    @ApplicationContext
    fun provideContext(): Context {
        return mApplication
    }

    @Provides
    fun provideApplication(): Application {
        return mApplication
    }

    @Provides
    fun provideDatabaseName(): String {
        return AppConstans.DB_NAME
    }

    @Provides
    @Singleton
    fun provideLocalStorageHelper(appLocalStorage: LocalStorageHelper): LocalStorageHelperContract {
        return appLocalStorage
    }

    @Provides
    @Singleton
    fun provideSessionHelper(sessionHelper: SessionHelper): SessionHelperContract {
        return sessionHelper
    }

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: ApiHelper): ApiHelperContract {
        return apiHelper
    }

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider = SchedulerProvider()
}
