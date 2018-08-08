package com.lychee

import com.lychee.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import io.realm.Realm

class TreeApp : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication>
            = DaggerAppComponent.builder().create(this)

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)
    }
}