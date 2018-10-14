package com.lychee.ui.statistic

import android.arch.lifecycle.ViewModel
import com.lychee.di.qualifier.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class StatisticFragmentModule {

    @Binds
    @IntoMap
    @ViewModelKey(StatisticViewModel::class)
    abstract fun bindStatisticViewModel(statisticViewModel: StatisticViewModel): ViewModel
}