package com.lychee.di.component

import com.lychee.Application
import com.lychee.di.module.ActivityBindingModule
import com.lychee.di.module.AppModule
import com.lychee.di.module.NetworkModule
import com.lychee.di.module.RepositoryModule
import com.lychee.di.scope.ApplicationScoped
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@ApplicationScoped
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    ActivityBindingModule::class,
    NetworkModule::class,
    RepositoryModule::class
])
interface AppComponent : AndroidInjector<Application> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<Application>()
}