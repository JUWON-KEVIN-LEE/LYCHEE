package com.lychee.data.weather.repository

import android.arch.lifecycle.LiveData
import com.google.android.gms.maps.model.LatLng
import com.lychee.data.common.Resource
import com.lychee.data.weather.model.WResponse
import com.lychee.data.weather.remote.WeatherRemoteDataSource
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
        private val weatherRemoteDataSource: WeatherRemoteDataSource
): WeatherRepository {

    override fun getWeatherByCoordinates(latLng: LatLng): LiveData<Resource<WResponse>>
            = weatherRemoteDataSource.getWeatherByCoordinates(latLng)
}