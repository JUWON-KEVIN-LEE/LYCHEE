package com.lychee.di.module

import com.lychee.di.scope.ActivityScoped
import com.lychee.ui.add.AddActivity
import com.lychee.ui.add.AddModule
import com.lychee.ui.main.MainActivity
import com.lychee.ui.main.MainMapModule
import com.lychee.ui.main.MainModule
import com.lychee.ui.settings.MyCardActivity
import com.lychee.ui.settings.MyCardModule
import com.lychee.ui.splash.SplashActivity
import com.lychee.ui.splash.SplashModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(
            modules = [
                MainModule::class,
                MainMapModule::class
            ]
    )
    abstract fun contributesMainActivity(): MainActivity

    @ActivityScoped
    @ContributesAndroidInjector(
            modules = [
                AddModule::class
            ]
    )
    abstract fun contributesAddActivity(): AddActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [SplashModule::class])
    abstract fun contributesSplashActivity(): SplashActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [MyCardModule::class])
    abstract fun contributesMyCardActivity(): MyCardActivity
}