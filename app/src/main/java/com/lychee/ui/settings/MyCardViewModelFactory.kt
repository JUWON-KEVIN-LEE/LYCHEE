package com.lychee.ui.settings

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject

class MyCardViewModelFactory @Inject constructor(

): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MyCardViewModel() as T
    }
}