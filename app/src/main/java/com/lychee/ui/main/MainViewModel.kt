package com.lychee.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.lychee.data.model.Expenditure
import com.lychee.ui.base.BaseViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor() : BaseViewModel() {

    private val _searchResult = MutableLiveData<Expenditure>()

    val searchResult: LiveData<Expenditure> get() = _searchResult

    init {

    }

    override fun onCleared() {
        super.onCleared()
    }
}