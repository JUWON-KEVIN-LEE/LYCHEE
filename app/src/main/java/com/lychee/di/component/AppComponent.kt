package com.lychee.di.component

import com.lychee.TreeApp
import com.lychee.di.module.AppModule
import com.lychee.di.scope.ApplicationScope
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@ApplicationScope
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class
])
interface AppComponent : AndroidInjector<TreeApp> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<TreeApp>()
}