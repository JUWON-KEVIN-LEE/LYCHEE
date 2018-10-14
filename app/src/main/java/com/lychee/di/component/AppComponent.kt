package com.lychee.di.component

import com.lychee.Application
import com.lychee.di.module.*
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
    RepositoryModule::class,
    SharedPreferenceModule::class,
    LocationModule::class,
    ViewModelModule::class
])
interface AppComponent : AndroidInjector<Application> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<Application>()
}