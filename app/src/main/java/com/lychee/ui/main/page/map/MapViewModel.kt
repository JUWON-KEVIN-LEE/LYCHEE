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

    init {
        _controllerVisibility.value = true
        _expandDetail.value = false
    }

    fun fetchExpenditures() {
        _expenditures.value = MockData.get() // TEST
    }

    fun openOrCloseDetail() {
        _expandDetail.value?.let { _expandDetail.value = !it }
    }

    fun showOrHideController() {
        _controllerVisibility.value?.let { _controllerVisibility.value = !it }
    }
}