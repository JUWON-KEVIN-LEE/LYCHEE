package com.lychee.di.module

import android.content.Context
import com.lychee.Application
import com.lychee.di.scope.ApplicationScoped
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides @ApplicationScoped
    fun provideApplicationContext(application : Application): Context
            = application.applicationContext
}