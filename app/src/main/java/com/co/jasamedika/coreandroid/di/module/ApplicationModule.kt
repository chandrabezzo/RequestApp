package com.co.jasamedika.coreandroid.di.module

import android.app.Application
import android.content.Context
import com.co.jasamedika.coreandroid.data.DataManager
import com.co.jasamedika.coreandroid.data.DataManagerContract
import com.co.jasamedika.coreandroid.data.local.LocalStorageHelper
import com.co.jasamedika.coreandroid.data.local.LocalStorageHelperContract
import com.co.jasamedika.coreandroid.data.network.ApiHelper
import com.co.jasamedika.coreandroid.data.network.ApiHelperContract
import com.co.jasamedika.coreandroid.data.session.SessionHelper
import com.co.jasamedika.coreandroid.data.session.SessionHelperContract
import com.co.jasamedika.coreandroid.di.ApplicationContext
import com.co.jasamedika.coreandroid.util.SchedulerProvider
import com.co.jasamedika.coreandroid.util.constanta.AppConstans
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
    fun provideDataManager(appDataManager: DataManager): DataManagerContract {
        return appDataManager
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
    fun provideApiHelper(appApiHelper: ApiHelper): ApiHelperContract {
        return appApiHelper
    }

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider = SchedulerProvider()
}
