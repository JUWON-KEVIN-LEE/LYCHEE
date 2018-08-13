package com.lychee.ui.main.page.home

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.lychee.R
import com.lychee.databinding.FragmentHomeBinding
import com.lychee.ui.base.BaseFragment
import com.lychee.ui.main.page.home.adapter.HomeRecentRecyclerViewAdapter
import com.lychee.util.extensions.moveToMarker

/**
 * Home 화면
 */
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(), OnMapReadyCallback {

    override val layoutResId: Int
        get() = R.layout.fragment_home

    override val viewModelClass: Class<HomeViewModel>
        get() = HomeViewModel::class.java

    lateinit var mGoogleMap: GoogleMap

    override fun onCreateView(savedInstanceState: Bundle?) {
        with(mBinding) {
            // home nav image view click > animation + activity listen
            homeRecentRecyclerView.apply {
                adapter = HomeRecentRecyclerViewAdapter(mContext)
                layoutManager = LinearLayoutManager(mContext)
            }

            homeMapView.getMapAsync(this@HomeFragment)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mBinding.homeMapView.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        mBinding.homeMapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mBinding.homeMapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mBinding.homeMapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mBinding.homeMapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.homeMapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mBinding.homeMapView.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mBinding.homeMapView.onSaveInstanceState(outState)
    }

    override fun onMapReady(map : GoogleMap?) {
        map?.let {
            mGoogleMap = it
            setUpGoogleMap(mGoogleMap)
        }
    }

    private fun setUpGoogleMap(googleMap: GoogleMap) {
        with(googleMap) {
            mapType = GoogleMap.MAP_TYPE_NORMAL

            uiSettings.isScrollGesturesEnabled = true
            uiSettings.isZoomGesturesEnabled = true

            val seoul = LatLng(37.56, 126.97)

            val marker = MarkerOptions()
                    .apply {
                        position(seoul)
                        title("SEOUL")
                    }

            moveToMarker(latLng = seoul, anim = true)
        }
    }

    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }
}
