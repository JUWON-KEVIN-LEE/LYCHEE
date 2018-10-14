package com.lychee.ui.main

import android.arch.lifecycle.ViewModel
import android.content.Context
import com.lychee.di.qualifier.ActivityLevel
import com.lychee.di.qualifier.ViewModelKey
import com.lychee.di.scope.ActivityScoped
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class MainActivityModule {

    @Binds
    @ActivityScoped
    @ActivityLevel
    abstract fun provideActivityContext(mainActivity: MainActivity): Context

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun provideMainViewModel(mainViewModel: MainViewModel): ViewModel
}