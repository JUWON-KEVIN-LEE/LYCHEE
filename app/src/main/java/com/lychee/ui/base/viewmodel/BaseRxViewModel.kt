package com.lychee.ui.base.viewmodel

import io.reactivex.disposables.CompositeDisposable

abstract class BaseRxViewModel: BaseViewModel() {

    val mCompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        mCompositeDisposable.clear()
    }
}