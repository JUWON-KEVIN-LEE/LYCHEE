package com.lychee.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import com.lychee.data.core.model.Expense
import com.lychee.data.core.repository.ExpenseRepository
import com.lychee.ui.base.viewmodel.BaseViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(
        private val expenseRepository: ExpenseRepository
) : BaseViewModel() {

    private val _expenditures = MediatorLiveData<List<Expense>>()
    val expenditures: LiveData<List<Expense>>
        get() = _expenditures

    private val _searchResult = MutableLiveData<Expense>()
    val searchResult: LiveData<Expense>
        get() = _searchResult

    init {

    }
}