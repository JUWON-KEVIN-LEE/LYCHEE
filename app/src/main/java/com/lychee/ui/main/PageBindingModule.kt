package com.lychee.ui.main

import com.lychee.di.scope.FragmentScoped
import com.lychee.ui.home.HomeFragment
import com.lychee.ui.home.HomeFragmentModule
import com.lychee.ui.map.MapFragment
import com.lychee.ui.map.MapFragmentModule
import com.lychee.ui.record.RecordFragment
import com.lychee.ui.record.RecordFragmentModule
import com.lychee.ui.setting.SettingFragment
import com.lychee.ui.setting.SettingFragmentModule
import com.lychee.ui.statistic.StatisticFragment
import com.lychee.ui.statistic.StatisticFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class PageBindingModule {

    @FragmentScoped
    @ContributesAndroidInjector(modules = [
        HomeFragmentModule::class
    ])
    internal abstract fun contributesHomeFragment() : HomeFragment

    @FragmentScoped
    @ContributesAndroidInjector(modules = [RecordFragmentModule::class])
    internal abstract fun contributesRecordFragment() : RecordFragment

    @FragmentScoped
    @ContributesAndroidInjector(modules = [MapFragmentModule::class])
    internal abstract fun contributesMapFragment() : MapFragment

    @FragmentScoped
    @ContributesAndroidInjector(modules = [StatisticFragmentModule::class])
    internal abstract fun contributesStatisticFragment() : StatisticFragment

    @FragmentScoped
    @ContributesAndroidInjector(modules = [SettingFragmentModule::class])
    internal abstract fun contributesSettingFragment() : SettingFragment
}