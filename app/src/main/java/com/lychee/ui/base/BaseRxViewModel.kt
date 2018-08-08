package com.lychee.ui.base

import io.reactivex.disposables.CompositeDisposable

abstract class BaseRxViewModel: BaseViewModel() {

    val disposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}