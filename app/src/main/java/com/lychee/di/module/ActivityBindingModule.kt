package com.lychee.di.module

import com.lychee.di.scope.ActivityScoped
import com.lychee.ui.add.AddActivity
import com.lychee.ui.add.AddModule
import com.lychee.ui.main.MainActivity
import com.lychee.ui.main.MainActivityModule
import com.lychee.ui.main.PageBindingModule
import com.lychee.ui.settings.MyCardActivity
import com.lychee.ui.settings.MyCardActivityModule
import com.lychee.ui.splash.SplashActivity
import com.lychee.ui.splash.SplashActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(
            modules = [
                MainActivityModule::class,
                PageBindingModule::class
            ]
    )
    abstract fun contributesMainActivity(): MainActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [AddModule::class])
    abstract fun contributesAddActivity(): AddActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [SplashActivityModule::class])
    abstract fun contributesSplashActivity(): SplashActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [MyCardActivityModule::class])
    abstract fun contributesMyCardActivity(): MyCardActivity
}