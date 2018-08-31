package com.lychee.ui.main.page.setting

import android.arch.lifecycle.ViewModelProvider
import com.lychee.di.qualifier.FragmentLevel
import com.lychee.di.scope.FragmentScoped
import dagger.Binds
import dagger.Module

@Module
abstract class SettingModule {

    @Binds @FragmentScoped @FragmentLevel
    abstract fun provideViewModelFactory(settingViewModelFactory: SettingViewModelFactory): ViewModelProvider.Factory
}