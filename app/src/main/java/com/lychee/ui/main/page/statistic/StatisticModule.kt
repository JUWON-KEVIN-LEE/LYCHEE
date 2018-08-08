package com.lychee.ui.main.page.statistic

import android.arch.lifecycle.ViewModelProvider
import com.lychee.di.qualifier.FragmentLevel
import com.lychee.di.scope.FragmentScope
import dagger.Binds
import dagger.Module

@Module
abstract class StatisticModule {

    @Binds @FragmentScope @FragmentLevel
    abstract fun provideViewModelFactory(statisticViewModelFactory: StatisticViewModelFactory): ViewModelProvider.Factory
}