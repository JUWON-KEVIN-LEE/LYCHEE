package com.lychee.ui.main.home

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.lychee.R
import com.lychee.databinding.FragmentHomeBinding
import com.lychee.mock.MockData
import com.lychee.ui.base.BaseFragment

/**
 * Home 화면
 */
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home), OnMapReadyCallback {

    override val viewModelClass: Class<HomeViewModel>
        get() = HomeViewModel::class.java

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View
        = super.onCreateView(inflater, container, savedInstanceState)


    override fun init() {
        binding.apply {
            // CALENDAR
            calendar.setOnClickListener { /* TODO calendar view */ }

            // TEXT
            /* TODO bind data to textview on view model & xml */

            // MAP VIEW
            binding.mapView.getMapAsync(this@HomeFragment)

            // RECYCLER VIEW
            recHome.apply {
                adapter = HomeRecAdapter(MockData.get()) /* TODO get data from view model */
                layoutManager = object : LinearLayoutManager(context) {
                    override fun canScrollVertically(): Boolean = false // 스크롤 차단
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.mapView.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView.onSaveInstanceState(outState)
    }

    override fun onMapReady(map : GoogleMap?) {
        map?.apply {
            mapType = GoogleMap.MAP_TYPE_NORMAL

            uiSettings.apply {
                isScrollGesturesEnabled = true
                isZoomGesturesEnabled = true
            }

            val seoul = LatLng(37.56, 126.97)

            val marker = MarkerOptions().apply {
                position(seoul)
                title("SEOUL")
            }

            addMarker(marker).showInfoWindow() // Marker 추가 및 화면 출력
            moveCamera(CameraUpdateFactory.newLatLng(seoul))
            animateCamera(CameraUpdateFactory.zoomTo(13f))
        }
    }
}
