package com.lychee.ui.main.page.home

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import com.lychee.ui.base.BaseViewModel

class HomeViewModel constructor() : BaseViewModel() {

    private val _monthTotalExpenditure: MutableLiveData<Int> = MutableLiveData()

    val monthTotalExpenditure: LiveData<Int> get() = _monthTotalExpenditure

    private val _monthTotalIncome: MutableLiveData<Int> = MutableLiveData()

    val monthTotalIncome: LiveData<Int> get() = _monthTotalIncome

    private val _monthCount: MutableLiveData<Int> = MutableLiveData()

    val monthCount: LiveData<Int> get() = _monthCount

    private val _monthBalance: MediatorLiveData<Int> = MediatorLiveData()

    val monthBalance: LiveData<Int> get() = _monthBalance

    init {
        _monthTotalExpenditure.value = 0
        _monthTotalIncome.value = 0
        _monthCount.value = 0

        _monthBalance.addSource(_monthTotalExpenditure) {
            _monthBalance.value = (_monthTotalIncome.value ?: 0) - (_monthTotalExpenditure.value ?: 0)
        }

        _monthBalance.addSource(_monthTotalIncome) {
            _monthBalance.value = (_monthTotalIncome.value ?: 0) - (_monthTotalExpenditure.value ?: 0)
        }
    }

    fun fetchMonthOverview() { // TEST
        _monthTotalExpenditure.value = 2957100
        _monthTotalIncome.value = 2200000
        _monthCount.value = 78
    }
}