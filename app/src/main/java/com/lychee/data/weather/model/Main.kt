package com.lychee.data.weather.model

import com.google.gson.annotations.SerializedName

data class Main (
        var temp: Double,
        var pressure: Double,
        var humidity: Double,
        @SerializedName("temp_min")
        var tempMin: Double,
        @SerializedName("temp_max")
        var tempMax: Double
)