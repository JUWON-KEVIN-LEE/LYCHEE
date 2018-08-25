package com.lychee.ui.base

import android.databinding.ViewDataBinding
import com.google.android.gms.maps.MapView

abstract class BaseMapFragment<DataBindingType: ViewDataBinding, ViewModelType: BaseViewModel>
    : BaseFragment<DataBindingType, ViewModelType>() {

    lateinit var mMapView: MapView

    override fun onResume() {
        super.onResume()
        mMapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mMapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mMapView.onLowMemory()
    }
}