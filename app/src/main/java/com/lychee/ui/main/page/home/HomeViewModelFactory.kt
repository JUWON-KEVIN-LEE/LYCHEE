package com.lychee.ui.main.page.home

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.lychee.data.weather.repository.WeatherRepository
import javax.inject.Inject

class HomeViewModelFactory @Inject constructor(
        private val weatherRepository: WeatherRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(weatherRepository) as T
    }
}