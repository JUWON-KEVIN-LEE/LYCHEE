package com.lychee.di.module

import android.app.Application
import android.content.Context
import com.lychee.App
import com.lychee.ui.cardlist.CardListActivity
import com.lychee.ui.main.MainActivity
import com.lychee.ui.main.MainActivityModule
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * TODO
 * VIEW MODEL SCOPE 변경 , MODULE SCOPE 변경 to internal
 */
@Module(includes = [
    AndroidSupportInjectionModule::class,
    DatabaseModule::class,
    NetworkModule::class,
    RepositoryModule::class,
    ViewModelModule::class
])
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun application(app : App) : Application

    @Binds
    @Singleton
    abstract fun applicationContext(app : App) : Context


    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun contributesMainActivity() : MainActivity

    @ContributesAndroidInjector
    abstract fun contributesCardListActivity() : CardListActivity
}