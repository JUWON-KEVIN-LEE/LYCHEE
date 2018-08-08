package com.lychee.ui.base

import android.databinding.ViewDataBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback

abstract class BaseMapFragment<DataBindingType: ViewDataBinding, ViewModelType: BaseViewModel>
    : BaseFragment<DataBindingType, ViewModelType>(), OnMapReadyCallback {


    override fun onMapReady(p0: GoogleMap?) {
        
    }
}