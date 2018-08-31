package com.lychee.di.module

import android.content.Context
import com.lychee.Application
import com.lychee.di.qualifier.ApplicationLevel
import com.lychee.di.scope.ApplicationScoped
import dagger.Binds
import dagger.Module

/**
 * TODO VIEW MODEL SCOPE 변경 , MODULE SCOPE 변경 to internal
 */
@Module
abstract class AppModule {

    @Binds @ApplicationScoped @ApplicationLevel
    abstract fun provideApplicationContext(application : Application): Context


}