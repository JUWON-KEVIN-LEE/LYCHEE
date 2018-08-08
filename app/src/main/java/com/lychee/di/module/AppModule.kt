package com.lychee.di.module

import android.content.Context
import com.lychee.TreeApp
import com.lychee.di.scope.ActivityScope
import com.lychee.di.scope.ApplicationScope
import com.lychee.ui.main.MainActivity
import com.lychee.ui.main.MainModule
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * TODO
 * VIEW MODEL SCOPE 변경 , MODULE SCOPE 변경 to internal
 */
@Module
abstract class AppModule {

    @Binds @ApplicationScope
    abstract fun provideApplicationContext(application : TreeApp): Context

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun contributesMainActivity(): MainActivity
}