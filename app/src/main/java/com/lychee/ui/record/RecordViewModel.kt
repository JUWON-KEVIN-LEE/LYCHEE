package com.lychee.ui.record

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import com.lychee.data.core.model.Expense
import com.lychee.data.core.repository.ExpenseRepository
import com.lychee.ui.base.viewmodel.BaseViewModel
import java.util.*
import javax.inject.Inject

class RecordViewModel @Inject constructor(
        private val expenseRepository: ExpenseRepository
) : BaseViewModel() {

    val month: MutableLiveData<Int> = MutableLiveData()

    val year: MutableLiveData<Int> = MutableLiveData()

    private val _date: MediatorLiveData<String> = MediatorLiveData()
    val date: LiveData<String>
        get() = _date

    private val _expenses: MutableLiveData<List<Expense>> = MutableLiveData()
    val expenses: LiveData<List<Expense>>
        get() = _expenses

    init {
        val calendar = Calendar.getInstance()

        month.value = calendar.get(Calendar.MONTH) + 1
        year.value = calendar.get(Calendar.YEAR)

        _date.addSource(month) {
            _date.value = "${it}월 ${year.value}"
        }

        _date.addSource(year) {
            _date.value = "${month.value}월 $it"
        }
    }
}