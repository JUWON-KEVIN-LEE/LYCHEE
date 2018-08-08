package com.lychee.ui.main.page.home

import android.os.Bundle
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.lychee.R
import com.lychee.databinding.FragmentHomeBinding
import com.lychee.ui.base.BaseFragment

/**
 * Home 화면
 */
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(), OnMapReadyCallback {

    override val layoutResId: Int
        get() = R.layout.fragment_home

    override val viewModelClass: Class<HomeViewModel>
        get() = HomeViewModel::class.java

    override fun onCreateView(savedInstanceState: Bundle?) {
        with(mBinding) {
            // home nav image view click > animation + activity listen
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    override fun onMapReady(map : GoogleMap?) {
        /*
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
        */
    }

    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }
}
