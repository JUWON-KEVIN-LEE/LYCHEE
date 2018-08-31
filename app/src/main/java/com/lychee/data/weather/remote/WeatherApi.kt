package com.lychee.data.weather.remote

import com.lychee.data.weather.model.WResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface WeatherApi {

    @GET("/data/2.5/weather")
    fun getWeatherByCityName(@QueryMap options: Map<String, String>): Single<WResponse>

    @GET("/data/2.5/weather")
    fun getWeatherByCoordinates(@QueryMap options: Map<String, String>): Single<WResponse>
}