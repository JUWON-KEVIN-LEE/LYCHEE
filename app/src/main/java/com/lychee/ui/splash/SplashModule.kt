package com.lychee.ui.splash

import android.arch.lifecycle.ViewModelProvider
import com.lychee.di.scope.ActivityScope
import dagger.Binds
import dagger.Module

@Module
abstract class SplashModule {

    @Binds @ActivityScope
    abstract fun provideViewModelFactory(splashViewModelFactory: SplashViewModelFactory): ViewModelProvider.Factory
}