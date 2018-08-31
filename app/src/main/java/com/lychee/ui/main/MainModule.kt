package com.lychee.ui.main

import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.lychee.di.qualifier.ActivityLevel
import com.lychee.di.scope.ActivityScoped
import com.lychee.di.scope.FragmentScoped
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

    @Binds @ActivityScoped @ActivityLevel
    abstract fun provideMainActivityContext(mainActivity: MainActivity): Context

    @Binds @ActivityScoped
    abstract fun provideViewModelFactory(mainViewModelFactory: MainViewModelFactory): ViewModelProvider.Factory

    @FragmentScoped
    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun contributesHomeFragment() : HomeFragment

    @FragmentScoped
    @ContributesAndroidInjector(modules = [RecordModule::class])
    abstract fun contributesRecordFragment() : RecordFragment

    @FragmentScoped
    @ContributesAndroidInjector(modules = [MapModule::class])
    abstract fun contributesMapFragment() : MapFragment

    @FragmentScoped
    @ContributesAndroidInjector(modules = [StatisticModule::class])
    abstract fun contributesStatisticFragment() : StatisticFragment

    @FragmentScoped
    @ContributesAndroidInjector(modules = [SettingModule::class])
    abstract fun contributesSettingFragment() : SettingFragment
}