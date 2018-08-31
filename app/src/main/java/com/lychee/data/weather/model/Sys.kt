package com.lychee.data.weather.model

data class Sys(
        var type: Int,
        var id: Int,
        var message: Double,
        var country: String,
        var sunrise: Long,
        var sunset: Long
)