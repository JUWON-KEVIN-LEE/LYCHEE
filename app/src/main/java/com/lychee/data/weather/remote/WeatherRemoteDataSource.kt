package com.lychee.data.weather.remote

import android.arch.lifecycle.LiveData
import com.google.android.gms.maps.model.LatLng
import com.lychee.data.common.Resource
import com.lychee.data.weather.model.WResponse

interface WeatherRemoteDataSource {

    fun getWeatherByCityName(city: String): LiveData<Resource<WResponse>>

    fun getWeatherByCoordinates(latLng: LatLng): LiveData<Resource<WResponse>>
}