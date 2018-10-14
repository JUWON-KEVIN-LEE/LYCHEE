package com.lychee.ui.home

import android.databinding.BindingAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.lychee.data.common.util.Result
import com.lychee.data.weather.model.WeatherResponse
import com.lychee.util.extensions.gone
import com.lychee.util.extensions.visible

@BindingAdapter("celcius")
fun celcius(
        textView: TextView,
        weather: Result<WeatherResponse>) {
    when(weather) {
        is Result.Loading -> {
            /* LOADING */
        }
        is Result.Success -> {
            val response = weather.data

            val temperature = response.main.temp
            val celsius = Math.round(temperature - 273.15)

            textView.text = "${celsius}°"
        }
        is Result.Error -> {
            /* ERROR */
        }
    }
}

@BindingAdapter("weatherIcon")
fun weatherIcon(
        imageView: ImageView,
        weather: Result<WeatherResponse>) {
    when(weather) {
        is Result.Loading -> {
            imageView.gone()
        }
        is Result.Success -> {
            imageView.visible()

            val response = weather.data

            val icon = response.weather[0].icon

            /**
            day         night
            01d.png  	01n.png  	clear sky
            02d.png  	02n.png  	few clouds
            03d.png  	03n.png  	scattered clouds
            04d.png  	04n.png  	broken clouds
            09d.png  	09n.png  	shower rain
            10d.png  	10n.png  	rain
            11d.png  	11n.png  	thunderstorm
            13d.png  	13n.png  	snow
            50d.png  	50n.png  	mist
             */
            if(icon.endsWith("d")) { // day time
                when(icon) {
                    "01d" -> {}
                    "02d" -> {}
                    "03d" -> {}
                    "04d" -> {}
                    "09d" -> {}
                    "10d" -> {}
                    "11d" -> {}
                    "13d" -> {}
                    "50d" -> {}
                }
            } else { // night time
                when(icon) {
                    "01n" -> {}
                    "02n" -> {}
                    "03n" -> {}
                    "04n" -> {}
                    "09n" -> {}
                    "10n" -> {}
                    "11n" -> {}
                    "13n" -> {}
                    "50n" -> {}
                }
            }
            Glide.with(imageView.context)
                    .load("http://openweathermap.org/img/w/$icon.png")
                    .apply(RequestOptions.centerCropTransform())
                    .into(imageView)
        }
        is Result.Error -> {
            imageView.gone()
        }
    }
}