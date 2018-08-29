package com.lychee.data.model.weather

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
        var coord: Coord,
        var weather: List<Weather>,
        var base: String,
        var main: Main,
        var visibility: Int

) {
    data class Coord(
            var lat: Double,
            var lon: Double
    )

    data class Weather (
            var id: Int,
            var main: String,
            var description: String,
            var icon: String
    )

    data class Main (
            var temp: Double,
            var pressure: Int,
            var humidity: Int,
            @SerializedName("temp_min")
            var tempMin: Double,
            @SerializedName("temp_max")
            var tempMax: Double
    )

    data class Wind(
            var speed: Float,
            var deg: Int
    )

}

/* {
    * "coord":{"lon":126.98,"lat":37.57},
    * "weather":[
    * {"id":501,"main":"Rain","description":"moderate rain","icon":"10n"},
    * {"id":202,"main":"Thunderstorm","description":"thunderstorm with heavy rain","icon":"11n"},
    * {"id":701,"main":"Mist","description":"mist","icon":"50n"}
    * ],
    * "base":"stations",
    * "main":{"temp":296.98,"pressure":1010,"humidity":94,"temp_min":296.15,"temp_max":299.25},
    * "visibility":10000,
    * "wind":{"speed":3.6,"deg":180},
    * "clouds":{"all":90},
    * "dt":1535461200,
    * "sys":{"type":1,"id":7676,"message":0.0054,"country":"KR","sunrise":1535403539,"sunset":1535450814},
    * "id":1835848,
    * "name":"Seoul",
    * "cod":200
* } */
