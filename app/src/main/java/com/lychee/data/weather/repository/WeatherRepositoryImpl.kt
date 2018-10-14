package com.lychee.data.weather.repository

import com.google.android.gms.maps.model.LatLng
import com.lychee.data.weather.model.WeatherResponse
import com.lychee.data.weather.remote.WeatherApi
import com.lychee.di.scope.ApplicationScoped
import io.reactivex.Single
import javax.inject.Inject

@ApplicationScoped
class WeatherRepositoryImpl @Inject constructor(
        private val weatherApi: WeatherApi
): WeatherRepository {

    override fun fetchWeatherByCoordinates(latLng: LatLng): Single<WeatherResponse> {

        return weatherApi.fetchWeather(
                mutableMapOf(
                        Pair("lat", latLng.latitude.toString()),
                        Pair("lon", latLng.longitude.toString()),
                        Pair("appid", "878d7026e3ade7448f1dda3538c9d93f"),
                        Pair("lang","kr"))
        )
    }
}