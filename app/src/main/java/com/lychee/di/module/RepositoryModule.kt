package com.lychee.di.module

import com.lychee.data.core.repository.ExpenseRepository
import com.lychee.data.core.repository.ExpenseRepositoryImpl
import com.lychee.data.weather.repository.WeatherRepository
import com.lychee.data.weather.repository.WeatherRepositoryImpl
import com.lychee.di.scope.ApplicationScoped
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds @ApplicationScoped
    abstract fun provideExpenseRepository(expenseRepositoryImpl: ExpenseRepositoryImpl): ExpenseRepository

    @Binds @ApplicationScoped
    abstract fun provideWeatherRepository(weatherRepositoryImpl: WeatherRepositoryImpl): WeatherRepository
}