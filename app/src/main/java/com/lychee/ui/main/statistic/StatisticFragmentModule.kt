package com.lychee.ui.main.statistic

import android.arch.lifecycle.ViewModel
import com.lychee.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class StatisticFragmentModule {

    @Binds
    @IntoMap
    @ViewModelKey(StatisticViewModel::class)
    abstract fun bindStatisticViewModel(statisticViewModel: StatisticViewModel) : ViewModel
}