package com.lychee.ui.main

import com.lychee.ui.main.home.HomeFragment
import com.lychee.ui.main.home.HomeFragmentModule
import com.lychee.ui.main.map.MapFragment
import com.lychee.ui.main.map.MapFragmentModule
import com.lychee.ui.main.record.RecordFragment
import com.lychee.ui.main.record.RecordFragmentModule
import com.lychee.ui.main.setting.SettingFragment
import com.lychee.ui.main.setting.SettingFragmentModule
import com.lychee.ui.main.statistic.StatisticFragment
import com.lychee.ui.main.statistic.StatisticFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class MainActivityModule {

    @ContributesAndroidInjector(modules = [HomeFragmentModule::class])
    abstract fun contributesHomeFragment() : HomeFragment

    @ContributesAndroidInjector(modules = [RecordFragmentModule::class])
    abstract fun contributesRecordFragment() : RecordFragment

    @ContributesAndroidInjector(modules = [MapFragmentModule::class])
    abstract fun contributesMapFragment() : MapFragment

    @ContributesAndroidInjector(modules = [StatisticFragmentModule::class])
    abstract fun contributesStatisticFragment() : StatisticFragment

    @ContributesAndroidInjector(modules = [SettingFragmentModule::class])
    abstract fun contributesSettingFragment() : SettingFragment
}