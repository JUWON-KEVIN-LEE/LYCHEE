package com.lychee.ui.main

import com.lychee.di.scope.PerFragment
import com.lychee.ui.main.home.HomeFragment
import com.lychee.ui.main.map.MapFragment
import com.lychee.ui.main.record.RecordFragment
import com.lychee.ui.main.setting.SettingFragment
import com.lychee.ui.main.statistic.StatisticFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class MainActivityModule {

    @PerFragment
    @ContributesAndroidInjector
    abstract fun contributesHomeFragment() : HomeFragment

    @PerFragment
    @ContributesAndroidInjector
    abstract fun contributesRecordFragment() : RecordFragment

    @PerFragment
    @ContributesAndroidInjector
    abstract fun contributesMapFragment() : MapFragment

    @PerFragment
    @ContributesAndroidInjector
    abstract fun contributesStatisticFragment() : StatisticFragment

    @ContributesAndroidInjector
    abstract fun contributesSettingFragment() : SettingFragment
}