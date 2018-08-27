package com.lychee.ui.main.page.map

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.lychee.data.model.Expenditure
import com.lychee.mock.MockData
import com.lychee.ui.base.BaseViewModel

class MapViewModel constructor() : BaseViewModel() {

    private val _expenditures: MutableLiveData<List<Expenditure>> = MutableLiveData()

    val expenditures: LiveData<List<Expenditure>> get() = _expenditures

    private val _controllerVisibility: MutableLiveData<Boolean> = MutableLiveData()

    val controllerVisibility: MutableLiveData<Boolean> get() = _controllerVisibility

    private val _expandDetail = MutableLiveData<Boolean>()

    val expandDetail: LiveData<Boolean> get() = _expandDetail

    private val _currentItem: MutableLiveData<Int> = MutableLiveData()

    val currentItem: LiveData<Int> get() = _currentItem

    init {
        _controllerVisibility.value = true
        _expandDetail.value = false
        _currentItem.value = 0
    }

    fun fetchExpenditures() {
        _expenditures.value = MockData.get() // TEST
    }

    fun setCurrentPosition(position: Int) {
        if(position >= 0 ) _currentItem.value = position
    }

    fun showDetailPager(show: Boolean) {
        _expandDetail.value = show
    }

    fun showController() {
        _controllerVisibility.value = true
    }

    fun hideController() {
        _controllerVisibility.value = false
    }

    fun showOrHideController() {
        _controllerVisibility.value?.let { _controllerVisibility.value = !it }
    }
}