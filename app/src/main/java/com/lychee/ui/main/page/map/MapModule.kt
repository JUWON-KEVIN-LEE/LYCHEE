package com.lychee.ui.main.page.map

import android.arch.lifecycle.ViewModelProvider
import com.lychee.di.qualifier.FragmentLevel
import com.lychee.di.scope.FragmentScoped
import dagger.Binds
import dagger.Module

@Module
abstract class MapModule {

    @Binds @FragmentScoped @FragmentLevel
    abstract fun provideViewModelFactory(mapViewModelFactory: MapViewModelFactory): ViewModelProvider.Factory
}