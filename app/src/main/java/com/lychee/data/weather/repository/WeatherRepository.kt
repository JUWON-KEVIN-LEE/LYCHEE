package com.lychee.data.weather.repository

import com.google.android.gms.maps.model.LatLng
import com.lychee.data.weather.model.WeatherResponse
import io.reactivex.Single

interface WeatherRepository {

    fun fetchWeatherByCoordinates(latLng: LatLng): Single<WeatherResponse>
}