package com.lychee.di.module

import android.arch.lifecycle.ViewModelProvider
import com.lychee.di.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun provideViewModelFactory(viewModelFactory: ViewModelFactory)
            : ViewModelProvider.Factory
}