package com.lychee.ui.settings

import android.arch.lifecycle.ViewModelProvider
import com.lychee.di.scope.ActivityScoped
import dagger.Binds
import dagger.Module

@Module
abstract class MyCardModule {

    @Binds @ActivityScoped
    abstract fun provideViewModelFactory(myCardViewModelFactory: MyCardViewModelFactory): ViewModelProvider.Factory
}