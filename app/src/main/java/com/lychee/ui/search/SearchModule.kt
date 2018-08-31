package com.lychee.ui.search

import android.arch.lifecycle.ViewModelProvider
import com.lychee.di.scope.ActivityScoped
import dagger.Binds
import dagger.Module

@Module
abstract class SearchModule {

    @Binds @ActivityScoped
    abstract fun provideSearchViewModelFactory(searchViewModelFactory: SearchViewModelFactory): ViewModelProvider.Factory
}