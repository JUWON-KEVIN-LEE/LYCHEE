package com.lychee.ui.add

import android.arch.lifecycle.ViewModelProvider
import com.lychee.di.scope.ActivityScoped
import dagger.Binds
import dagger.Module

@Module
abstract class AddModule {

    @Binds @ActivityScoped
    abstract fun provideViewModelFactory(addViewModelFactory: AddViewModelFactory): ViewModelProvider.Factory
}