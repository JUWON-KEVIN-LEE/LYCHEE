package com.lychee.ui.main.page.home

import android.arch.lifecycle.Observer
import android.location.Geocoder
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.lychee.R
import com.lychee.data.common.Resource
import com.lychee.databinding.FragmentHomeBinding
import com.lychee.ui.base.BaseMapFragment
import com.lychee.ui.main.MainActivity
import com.lychee.ui.main.page.home.adapter.HomeRecentRecyclerViewAdapter
import com.lychee.util.extensions.changeCamera
import pub.devrel.easypermissions.EasyPermissions
import javax.inject.Inject

/**
 * Home 화면
 */
class HomeFragment : BaseMapFragment<FragmentHomeBinding, HomeViewModel>() {

    override val layoutResId: Int
        get() = R.layout.fragment_home

    override val viewModelClass: Class<HomeViewModel>
        get() = HomeViewModel::class.java

    @Inject lateinit var mFusedLocationProviderClient: FusedLocationProviderClient

    @Inject lateinit var mGeocoder: Geocoder

    lateinit var mGoogleMap: GoogleMap

    private val onMapReadyCallback = object: OnMapReadyCallback {
        override fun onMapReady(googleMap: GoogleMap?) {
            mGoogleMap = googleMap ?: return

            with(mGoogleMap.uiSettings) {
                isZoomControlsEnabled = false
                isZoomGesturesEnabled = false
                isRotateGesturesEnabled = false
                isScrollGesturesEnabled = false
                isCompassEnabled = false
                isMyLocationButtonEnabled = false
                isIndoorLevelPickerEnabled = false
            }

            with(mGoogleMap) {
                setOnMapClickListener { (mContext as MainActivity).navigateToMapPage() }
            }

            mViewModel.fetchExpenditures()
        }
    }

    override fun onCreateView(savedInstanceState: Bundle?) {
        with(mBinding) {
            mMapView = homeMapView
            mMapView.onCreate(savedInstanceState)

            // Bitmap Descriptor Factory and Camera Update Factory will be initialized.
            MapsInitializer.initialize(mContext)

            mMapView.getMapAsync(onMapReadyCallback)

            // home nav image view click > animation + activity listen
            homeRecentRecyclerView.apply {
                adapter = HomeRecentRecyclerViewAdapter(mContext)
                layoutManager = LinearLayoutManager(mContext)
            }

            homeMonthMoreInfoTextView.setOnClickListener { mViewModel.fetchMonthOverview() }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.viewModel = mViewModel

        getWeatherByCoordinates()

        mViewModel.weather.observe(this, Observer {
            when(it) {
                is Resource.Loading -> {
                    Log.d("JUWONLEE", "날씨 로딩")
                }
                is Resource.Success -> {
                    val response = it.data

                    val temperature = response.main.temp
                    val celsius = Math.round(temperature - 273.15)

                    mBinding.homeWeatherTextView.text = "${celsius}°"

                    val icon = response.weather[0].icon

                    with(mBinding.homeWeatherImageView) {
                        Glide.with(context)
                                .load("http://openweathermap.org/img/w/$icon.png")
                                .apply(RequestOptions.centerCropTransform())
                                .into(this)
                    }
                }
                is Resource.Error -> {
                    Log.d("JUWONLEE", "날씨 에러: ${it.error}")
                }
            }
        })

        mViewModel.expenditures.observe(this, Observer {
            it
                    ?.takeIf { it.isNotEmpty() }
                    ?.let { expenditures ->
                        // most recent expenditure on map
                        val expenditure = expenditures[0]

                        val mostRecentPosition = LatLng(expenditure.lat, expenditure.lng)

                        mGoogleMap
                                .addMarker(
                                        MarkerOptions()
                                                .position(mostRecentPosition)
                                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.round_place_black_36))
                                )

                        mGoogleMap.changeCamera(CameraUpdateFactory.newLatLngZoom(mostRecentPosition, 10f), true)

                        // recent expenditure list
                        val adapter = (mBinding.homeRecentRecyclerView.adapter as HomeRecentRecyclerViewAdapter)
                        adapter.expenditures = expenditures.toMutableList()
                    }
        })
    }

    private fun getWeatherByCoordinates() {
        if(EasyPermissions.hasPermissions(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            @SuppressWarnings("MissingPermission")
            val mLastLocation = mFusedLocationProviderClient.lastLocation

            mLastLocation.addOnCompleteListener { task ->
                if(task.isSuccessful && task.result != null) {
                    val latLng = LatLng (
                            task.result.latitude,
                            task.result.longitude)

                    mViewModel.fetchWeatherByCoordinates(latLng)
                    /*
                    val address = mGeocoder.getAddress(latLng)

                    address?.let {
                        Log.d("JUWONLEE", "주소: $it, 위도 / 경도: ${latLng.latitude} / ${latLng.longitude}")
                    }
                    */
                } else {
                    Log.e("JUWONLEE", "주소를 찾을 수 없습니다.")
                }
            }
        } else {

        }
    }


    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }
}
