package com.lychee.data.weather.model

data class WResponse(
        var coord: Coord,
        var weather: List<Weather>,
        var base: String,
        var main: Main,
        var visibility: Int,
        var wind: Wind,
        var clouds: Clouds,
        var dt: Long,
        var sys: Sys,
        var id: Long,
        var name: String,
        var cod: Int
)