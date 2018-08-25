package com.lychee.ui.main.page.record

import android.arch.lifecycle.MutableLiveData
import com.lychee.ui.base.BaseViewModel

class RecordViewModel constructor() : BaseViewModel() {

    val year: MutableLiveData<Int> = MutableLiveData()

    val month: MutableLiveData<Int> = MutableLiveData()
}