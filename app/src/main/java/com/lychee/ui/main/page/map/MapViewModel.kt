package com.lychee.ui.main.page.map

import android.arch.lifecycle.MutableLiveData
import com.lychee.ui.base.BaseViewModel

class MapViewModel constructor() : BaseViewModel() {

    val isWidgetsVisible: MutableLiveData<Boolean> = MutableLiveData()

    init {
        isWidgetsVisible.value = true
    }
}