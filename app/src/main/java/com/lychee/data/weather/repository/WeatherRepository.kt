package com.lychee.data.weather.repository

import android.arch.lifecycle.LiveData
import com.google.android.gms.maps.model.LatLng
import com.lychee.data.common.Resource
import com.lychee.data.weather.model.WResponse

interface WeatherRepository {

    fun getWeatherByCoordinates(latLng: LatLng): LiveData<Resource<WResponse>>
}