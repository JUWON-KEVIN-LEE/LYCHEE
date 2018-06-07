package com.lychee.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.lychee.di.ViewModelKey
import com.lychee.ui.cardlist.CardListViewModel
import com.lychee.ui.main.MainViewModel
import com.lychee.ui.main.home.HomeViewModel
import com.lychee.ui.main.map.MapViewModel
import com.lychee.ui.main.record.RecordViewModel
import com.lychee.ui.main.setting.SettingViewModel
import com.lychee.ui.main.statistic.StatisticViewModel
import com.lychee.ui.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory) : ViewModelProvider.Factory

    // MAIN ACTIVITY
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RecordViewModel::class)
    abstract fun bindRecordViewModel(recordViewModel: RecordViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MapViewModel::class)
    abstract fun bindMapViewModel(mapViewModel: MapViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(StatisticViewModel::class)
    abstract fun bindStatisticViewModel(statisticViewModel: StatisticViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingViewModel::class)
    abstract fun bindSettingViewModel(settingViewModel: SettingViewModel) : ViewModel

    // CARD LIST ACTIVITY
    @Binds
    @IntoMap
    @ViewModelKey(CardListViewModel::class)
    abstract fun bindCardListViewModel(cardListViewModel: CardListViewModel) : ViewModel
}