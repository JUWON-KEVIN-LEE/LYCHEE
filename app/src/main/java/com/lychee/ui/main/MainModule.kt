package com.lychee.ui.main

import android.arch.lifecycle.ViewModelProvider
import com.lychee.di.scope.ActivityScope
import com.lychee.di.scope.FragmentScope
import com.lychee.ui.main.page.home.HomeFragment
import com.lychee.ui.main.page.home.HomeModule
import com.lychee.ui.main.page.map.MapFragment
import com.lychee.ui.main.page.map.MapModule
import com.lychee.ui.main.page.record.RecordFragment
import com.lychee.ui.main.page.record.RecordModule
import com.lychee.ui.main.page.setting.SettingFragment
import com.lychee.ui.main.page.setting.SettingModule
import com.lychee.ui.main.page.statistic.StatisticFragment
import com.lychee.ui.main.page.statistic.StatisticModule
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class MainModule {

    @Binds @ActivityScope
    abstract fun provideViewModelFactory(mainViewModelFactory: MainViewModelFactory): ViewModelProvider.Factory

    @FragmentScope
    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun contributesHomeFragment() : HomeFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [RecordModule::class])
    abstract fun contributesRecordFragment() : RecordFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [MapModule::class])
    abstract fun contributesMapFragment() : MapFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [StatisticModule::class])
    abstract fun contributesStatisticFragment() : StatisticFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [SettingModule::class])
    abstract fun contributesSettingFragment() : SettingFragment


}