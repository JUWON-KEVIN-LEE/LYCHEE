package com.lychee.ui.main.page.map

import android.os.Bundle
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.lychee.R
import com.lychee.databinding.FragmentMapBinding
import com.lychee.ui.base.BaseFragment

class MapFragment : BaseFragment<FragmentMapBinding, MapViewModel>(), OnMapReadyCallback {

    override val layoutResId: Int
        get() = R.layout.fragment_map

    override val viewModelClass: Class<MapViewModel>
        get() = MapViewModel::class.java

    override fun onCreateView(savedInstanceState: Bundle?) {

    }

    override fun onMapReady(p0: GoogleMap?) {

    }

    companion object {
        fun newInstance(): MapFragment = MapFragment()
    }
}
