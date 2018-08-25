package com.lychee

import android.content.Context
import android.support.multidex.MultiDex
import com.lychee.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import io.realm.Realm

class TreeApp : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication>
            = DaggerAppComponent.builder().create(this)

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)
    }
}