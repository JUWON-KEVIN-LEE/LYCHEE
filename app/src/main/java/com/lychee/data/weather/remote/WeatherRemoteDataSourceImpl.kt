package com.lychee.data.weather.remote

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.lychee.data.common.Resource
import com.lychee.data.weather.model.WResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class WeatherRemoteDataSourceImpl @Inject constructor(
        private val api: WeatherApi
): WeatherRemoteDataSource {

    override fun getWeatherByCityName(city: String): LiveData<Resource<WResponse>> {
        val options = mutableMapOf<String, String>()

        options["q"] = city

        options["appid"] = "878d7026e3ade7448f1dda3538c9d93f"

        val resource = MutableLiveData<Resource<WResponse>>()

        api.getWeatherByCityName(options)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    resource.value = Resource.Success(it)
                },{
                    resource.value = Resource.Error(it.message ?: "unexpected error")
                })

        return resource
    }

    override fun getWeatherByCoordinates(latLng: LatLng): LiveData<Resource<WResponse>> {
        val options = mutableMapOf<String, String>()

        options["lat"] = latLng.latitude.toString()
        options["lon"] = latLng.longitude.toString()

        options["appid"] = "878d7026e3ade7448f1dda3538c9d93f"

        val resource: MutableLiveData<Resource<WResponse>> = MutableLiveData()

        api.getWeatherByCityName(options)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d("JUWONLEE", "success")
                    resource.value = Resource.Success(it)
                },{
                    Log.d("JUWONLEE", "error")
                    resource.value = Resource.Error(it.message ?: "unexpected error")
                })

        return resource
    }

}