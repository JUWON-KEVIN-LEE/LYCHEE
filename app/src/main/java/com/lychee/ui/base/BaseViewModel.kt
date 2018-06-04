package com.lychee.ui.base

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel(), LifecycleObserver {

    open val disposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}