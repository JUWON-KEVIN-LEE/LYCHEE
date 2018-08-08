package com.lychee.ui.main.page.home

import android.arch.lifecycle.ViewModelProvider
import com.lychee.di.qualifier.FragmentLevel
import com.lychee.di.scope.FragmentScope
import dagger.Binds
import dagger.Module

@Module
abstract class HomeModule {

    @Binds @FragmentScope @FragmentLevel
    abstract fun provideViewModelFactory(homeViewModelFactory: HomeViewModelFactory): ViewModelProvider.Factory
}