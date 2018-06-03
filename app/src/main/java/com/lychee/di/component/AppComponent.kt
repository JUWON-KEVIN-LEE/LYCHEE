package com.lychee.di.component

import com.lychee.App
import com.lychee.di.module.AppModule
import dagger.Component
import dagger.android.AndroidInjector

@Component(modules = [AppModule::class])
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()
}