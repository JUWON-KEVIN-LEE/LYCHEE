package com.lychee.ui.main.page.record

import android.arch.lifecycle.ViewModelProvider
import com.lychee.di.qualifier.FragmentLevel
import com.lychee.di.scope.FragmentScoped
import dagger.Binds
import dagger.Module

@Module
abstract class RecordModule {

    @Binds @FragmentScoped @FragmentLevel
    abstract fun provideViewModelFactory(recordViewModelFactory: RecordViewModelFactory): ViewModelProvider.Factory
}