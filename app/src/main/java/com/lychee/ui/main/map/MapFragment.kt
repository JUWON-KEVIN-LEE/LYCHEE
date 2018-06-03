package com.lychee.ui.main.map


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lychee.R
import com.lychee.ui.base.BaseFragment

/**
 *
 */
class MapFragment : BaseFragment<MapViewModel>() {

    override val viewModelClass: Class<MapViewModel>
        get() = MapViewModel::class.java

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
        = inflater.inflate(R.layout.fragment_map, container, false)


}
