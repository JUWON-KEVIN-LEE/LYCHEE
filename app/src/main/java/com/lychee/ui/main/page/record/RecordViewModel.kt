package com.lychee.ui.main.page.record

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.lychee.ui.base.BaseViewModel
import java.util.*

class RecordViewModel constructor() : BaseViewModel() {

    var month : MutableLiveData<Int> = MutableLiveData()

    var year : MutableLiveData<Int> = MutableLiveData()


    init {
        val cal = Calendar.getInstance()

        month.value = cal.get(Calendar.MONTH) + 1

        year.value = cal.get(Calendar.YEAR)
    }
}