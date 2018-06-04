package com.lychee.ui.main.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.MapView
import com.lychee.R
import com.lychee.databinding.FragmentMapBinding
import com.lychee.ui.base.BaseFragment

/**
 *
 */
class MapFragment : BaseFragment<FragmentMapBinding, MapViewModel>(R.layout.fragment_map) {

    lateinit var mapView : MapView

    override val viewModelClass: Class<MapViewModel>
        get() = MapViewModel::class.java

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View
        = super.onCreateView(inflater, container, savedInstanceState)

    override fun init() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}
