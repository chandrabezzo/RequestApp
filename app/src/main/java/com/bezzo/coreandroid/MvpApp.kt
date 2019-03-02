package com.bezzo.coreandroid

import android.app.Activity
import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.bezzo.core.util.AppLogger
import com.bezzo.core.util.LocaleHelper
import com.bezzo.coreandroid.di.component.DaggerApplicationComponent
import com.orhanobut.hawk.Hawk
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

/**
 * Created by bezzo on 11/01/18.
 */
class MvpApp : Application(), HasActivityInjector {

    @Inject
    lateinit var activityDispatchingAndroidInjector : DispatchingAndroidInjector<Activity>

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(LocaleHelper.onAttach(base, LocaleHelper.getLanguage(base)))

        MultiDex.install(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return activityDispatchingAndroidInjector
    }

    override fun onCreate() {
        super.onCreate()

        DaggerApplicationComponent.builder()
                .application(this)
                .build()
                .inject(this)

        AppLogger.init()
        Hawk.init(this).build()
    }
}