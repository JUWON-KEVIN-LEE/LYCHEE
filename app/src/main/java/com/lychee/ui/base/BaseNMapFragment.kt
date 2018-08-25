package com.lychee.ui.base

import android.databinding.ViewDataBinding
import android.os.Bundle

/**
 * NAVER MAP(NMapView) Fragment
 */
/*
abstract class BaseNMapFragment<DataBindingType: ViewDataBinding, ViewModelType: BaseViewModel>
    : BaseFragment<DataBindingType, ViewModelType>() {

    lateinit var mNMapContext: NMapContext

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mNMapContext = NMapContext(activity)
        mNMapContext.onCreate()
    }

    override fun onStart() {
        super.onStart()
        mNMapContext.onStart()
    }

    override fun onResume() {
        super.onResume()
        mNMapContext.onResume()
    }

    override fun onPause() {
        super.onPause()
        mNMapContext.onPause()
    }

    override fun onStop() {
        mNMapContext.onStop()
        super.onStop()
    }

    override fun onDestroy() {
        mNMapContext.onDestroy()
        super.onDestroy()
    }
}
        */