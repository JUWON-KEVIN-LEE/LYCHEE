package com.lychee.ui.map

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.lychee.data.core.model.Expense
import com.lychee.ui.base.viewmodel.BaseViewModel
import javax.inject.Inject

class MapViewModel @Inject constructor(

) : BaseViewModel() {

    private val _expenses: MutableLiveData<List<Expense>> = MutableLiveData()

    val expenses: LiveData<List<Expense>> get() = _expenses

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

    fun fetchExpenses() {
        // _expenses.value = MockData.get()
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