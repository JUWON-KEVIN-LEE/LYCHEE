package com.lychee

import com.lychee.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import io.realm.Realm

class App : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()

        Realm.init(this) // ready to use
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication>
        = DaggerAppComponent.builder().create(this)
}