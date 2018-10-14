package com.lychee.di.module

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.lychee.di.scope.ApplicationScoped
import dagger.Module
import dagger.Provides

@Module
class SharedPreferenceModule {

    @Provides @ApplicationScoped
    fun provideSharedPreference(context: Context): SharedPreferences
            = PreferenceManager.getDefaultSharedPreferences(context)
}