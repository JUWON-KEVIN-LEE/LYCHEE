package com.lychee.ui.main.page.home

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.lychee.data.common.Resource
import com.lychee.data.core.model.Expenditure
import com.lychee.data.weather.model.WResponse
import com.lychee.data.weather.repository.WeatherRepository
import com.lychee.mock.MockData
import com.lychee.ui.base.BaseViewModel
import javax.inject.Inject

class HomeViewModel @Inject constructor(
        private val weatherRepository: WeatherRepository
) : BaseViewModel() {

    private val _weather: MediatorLiveData<Resource<WResponse>> = MediatorLiveData()

    val weather: LiveData<Resource<WResponse>> get() = _weather

    private val _monthTotalExpenditure: MutableLiveData<Int> = MutableLiveData()

    val monthTotalExpenditure: LiveData<Int> get() = _monthTotalExpenditure

    private val _monthTotalIncome: MutableLiveData<Int> = MutableLiveData()

    val monthTotalIncome: LiveData<Int> get() = _monthTotalIncome

    private val _monthCount: MutableLiveData<Int> = MutableLiveData()

    val monthCount: LiveData<Int> get() = _monthCount

    private val _monthBalance: MediatorLiveData<Int> = MediatorLiveData()

    val monthBalance: LiveData<Int> get() = _monthBalance

    private val _expenditures: MutableLiveData<List<Expenditure>> = MutableLiveData()

    val expenditures: LiveData<List<Expenditure>> get() = _expenditures

    init {
        _weather.value = Resource.Loading()

        _monthTotalExpenditure.value = 0
        _monthTotalIncome.value = 0
        _monthCount.value = 0

        _monthBalance.addSource(_monthTotalExpenditure) {
            _monthBalance.value = (_monthTotalIncome.value ?: 0) - (_monthTotalExpenditure.value ?: 0)
        }

        _monthBalance.addSource(_monthTotalIncome) {
            _monthBalance.value = (_monthTotalIncome.value ?: 0) - (_monthTotalExpenditure.value ?: 0)
        }

        _expenditures.value = mutableListOf()
    }

    fun fetchMonthOverview() { // TEST
        _monthTotalExpenditure.value = 2957100
        _monthTotalIncome.value = 2200000
        _monthCount.value = 78
    }

    fun fetchWeatherByCoordinates(latLng: LatLng) {
        val resource = weatherRepository.getWeatherByCoordinates(latLng)
        _weather.addSource(resource) {
            _weather.removeSource(resource)

            _weather.value = it
        }
    }

    fun fetchExpenditures() {
        _expenditures.value = MockData.get()
    }
}