package com.lychee.di.module

import com.lychee.data.weather.remote.WeatherRemoteDataSource
import com.lychee.data.weather.remote.WeatherRemoteDataSourceImpl
import com.lychee.data.weather.repository.WeatherRepository
import com.lychee.data.weather.repository.WeatherRepositoryImpl
import com.lychee.di.scope.ApplicationScoped
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds @ApplicationScoped
    abstract fun provideWeatherRemoteDataSource(weatherRemoteDataSourceImpl: WeatherRemoteDataSourceImpl)
            : WeatherRemoteDataSource

    @Binds @ApplicationScoped
    abstract fun provideWeatherRepository(weatherRepositoryImpl: WeatherRepositoryImpl): WeatherRepository
}