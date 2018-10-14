package com.lychee.ui.home

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.lychee.data.common.util.Result
import com.lychee.data.core.model.Expense
import com.lychee.data.core.repository.ExpenseRepository
import com.lychee.data.weather.model.WeatherResponse
import com.lychee.data.weather.repository.WeatherRepository
import com.lychee.ui.base.viewmodel.BaseRxViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeViewModel @Inject constructor(
        private val weatherRepository: WeatherRepository,
        private val expenseRepository: ExpenseRepository
) : BaseRxViewModel() {

    private val _weather: MediatorLiveData<Result<WeatherResponse>> = MediatorLiveData()

    val weather: LiveData<Result<WeatherResponse>> get() = _weather

    private val _monthTotalExpenditure: MutableLiveData<Int> = MutableLiveData()

    val monthTotalExpenditure: LiveData<Int> get() = _monthTotalExpenditure

    private val _monthTotalIncome: MutableLiveData<Int> = MutableLiveData()

    val monthTotalIncome: LiveData<Int> get() = _monthTotalIncome

    private val _monthCount: MutableLiveData<Int> = MutableLiveData()

    val monthCount: LiveData<Int> get() = _monthCount

    private val _monthBalance: MediatorLiveData<Int> = MediatorLiveData()

    val monthBalance: LiveData<Int> get() = _monthBalance

    private val _expenses: MediatorLiveData<List<Expense>> = MediatorLiveData()

    val expenses: LiveData<List<Expense>> get() = _expenses

    /**
     * LiveData 는 DataBinding, BindingAdapter 를 이용해서 처리
     * 0. from repository
     *   : LiveData<List<Expense>>
     * 1. from db
     * 2. from server TODO
     */

    init {
        _weather.value = Result.Loading()

        _monthTotalExpenditure.value = 0
        _monthTotalIncome.value = 0
        _monthCount.value = 0

        _monthBalance.addSource(_monthTotalExpenditure) {
            _monthBalance.value = (_monthTotalIncome.value ?: 0) - (_monthTotalExpenditure.value ?: 0)
        }

        _monthBalance.addSource(_monthTotalIncome) {
            _monthBalance.value = (_monthTotalIncome.value ?: 0) - (_monthTotalExpenditure.value ?: 0)
        }

        _expenses.value = mutableListOf()
    }

    fun fetchMonthOverview() { // TEST
        _monthTotalExpenditure.value = 2957100
        _monthTotalIncome.value = 2200000
        _monthCount.value = 78
    }

    fun fetchWeatherByCoordinates(latLng: LatLng) {
        weatherRepository.fetchWeatherByCoordinates(latLng)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { _weather.value = Result.Loading() }
                .subscribe({
                    response ->  _weather.value = Result.Success(response)
                }, {
                    throwable -> _weather.value = Result.Error(throwable.message ?: "Unexpected Error.")
                })
                .addTo(mCompositeDisposable)
    }

    fun fetchExpenses() {
        val latest = expenseRepository.getExpensesLatest(3)

        _expenses.addSource(latest) {
            _expenses.removeSource(latest)

            _expenses.value = it
        }
    }
}