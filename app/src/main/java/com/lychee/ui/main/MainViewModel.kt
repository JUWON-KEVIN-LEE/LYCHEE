package com.lychee.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.lychee.data.core.model.Expenditure
import com.lychee.mock.MockData
import com.lychee.ui.base.BaseViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor() : BaseViewModel() {

    private val _expenditures = MutableLiveData<List<Expenditure>>()

    val expenditures: LiveData<List<Expenditure>> get() = _expenditures

    private val _searchResult = MutableLiveData<Expenditure>()

    val searchResult: LiveData<Expenditure> get() = _searchResult

    init {
        _expenditures.value = emptyList()
    }

    fun fetchExpendituresFromDB() {
        _expenditures.value = MockData.get()
    }
}