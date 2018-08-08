package com.lychee.ui.main.page.record

import android.arch.lifecycle.ViewModelProvider
import com.lychee.di.qualifier.FragmentLevel
import com.lychee.di.scope.FragmentScope
import dagger.Binds
import dagger.Module

@Module
abstract class RecordModule {

    @Binds @FragmentScope @FragmentLevel
    abstract fun provideViewModelFactory(recordViewModelFactory: RecordViewModelFactory): ViewModelProvider.Factory
}