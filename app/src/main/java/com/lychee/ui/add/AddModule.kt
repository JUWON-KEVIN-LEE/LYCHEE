package com.lychee.ui.add

import android.arch.lifecycle.ViewModelProvider
import com.lychee.di.scope.ActivityScope
import dagger.Binds
import dagger.Module

@Module
abstract class AddModule {

    @Binds @ActivityScope
    abstract fun provideViewModelFactory(addViewModelFactory: AddViewModelFactory): ViewModelProvider.Factory
}