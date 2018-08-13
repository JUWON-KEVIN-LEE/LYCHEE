package com.lychee.ui.main.page.map

import android.Manifest
import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.support.transition.ChangeBounds
import android.support.transition.Transition
import android.support.transition.TransitionManager
import android.view.View
import android.view.animation.LinearInterpolator
import com.google.android.gms.location.*
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.lychee.R
import com.lychee.databinding.FragmentMapBinding
import com.lychee.ui.base.BaseFragment
import com.lychee.ui.main.page.map.adapter.MapViewPagerAdapter
import com.lychee.util.extensions.dpToPx
import com.lychee.util.extensions.moveToMarker
import com.lychee.util.extensions.update
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

class MapFragment:
        BaseFragment<FragmentMapBinding, MapViewModel>(),
        OnMapReadyCallback,
        EasyPermissions.PermissionCallbacks
{

    override val layoutResId: Int
        get() = R.layout.fragment_map

    override val viewModelClass: Class<MapViewModel>
        get() = MapViewModel::class.java

    lateinit var mGoogleMap: GoogleMap

    // INJECT
    /*
    private val mLocationClient = LocationServices.getFusedLocationProviderClient(mContext)

    private val mLocationRequest = LocationRequest.create().apply {
        interval = LOCATION_UPDATE_INTERVAL
        fastestInterval = LOCATION_FASTEST_UPDATE_INTERVAL
        priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
    }

    val mLocationCallback = object: LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                drawLocation(locationResult.lastLocation)
            }
        }
    */

    private lateinit var releaseWork: Runnable

    override fun onCreateView(savedInstanceState: Bundle?) {
        // super: initMapView(savedInstanceState)
        with(mBinding) {
            val supportMapFragment
                    = childFragmentManager.findFragmentById(R.id.mapSupportMapFragment) as SupportMapFragment
            supportMapFragment.getMapAsync(this@MapFragment)

            mapMyLocationButton.setOnClickListener { getCurrentLocation() }

            mapViewPager.adapter = MapViewPagerAdapter(mContext)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel.isWidgetsVisible.observe(this, Observer { isWidgetsVisible ->
            isWidgetsVisible
                    ?.takeIf { it }
                    ?.let { showWidgets() }
                    ?:let { hideWidgets() }
        })
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap?.let {
            mGoogleMap = it
            setUpGoogleMap(mGoogleMap)
        }
    }

    private fun setUpGoogleMap(googleMap: GoogleMap) {
        with(googleMap) {
            mapType = GoogleMap.MAP_TYPE_NORMAL
            setOnMapClickListener { mViewModel.isWidgetsVisible.value = !mViewModel.isWidgetsVisible.value!! }
            getCurrentLocation()
            /* more ... */
        }
    }

    @AfterPermissionGranted(FINE_LOCATION_REQUEST_CODE)
    private fun getCurrentLocation() {
        if(EasyPermissions.hasPermissions(mContext, Manifest.permission.ACCESS_FINE_LOCATION)) {
            findLocation()
        } else {
            EasyPermissions.requestPermissions(
                    this,
                    "위치 권한 요청 메시지",
                    FINE_LOCATION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        when(requestCode) {
            FINE_LOCATION_REQUEST_CODE -> {
                findLocation()
            }
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        when(requestCode) {
            FINE_LOCATION_REQUEST_CODE -> {

            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    @SuppressLint("MissingPermission")
    private fun findLocation() {
        val mLocationClient = LocationServices.getFusedLocationProviderClient(mContext)

        val mLocationRequest = LocationRequest.create().apply {
            interval = LOCATION_UPDATE_INTERVAL
            fastestInterval = LOCATION_FASTEST_UPDATE_INTERVAL
            priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        }

        val mLocationCallback = object: LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                drawLocation(locationResult.lastLocation)
            }
        }

        mLocationClient.lastLocation.addOnSuccessListener { drawLocation(it) }

        mLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper())

        LocationServices
                .getSettingsClient(mContext)
                .checkLocationSettings(
                        LocationSettingsRequest
                                .Builder()
                                .setAlwaysShow(true)
                                .setNeedBle(true)
                                .addLocationRequest(mLocationRequest)
                                .build()
                )

        // TEST TODO
        releaseWork = Runnable {
            mLocationClient.flushLocations()
            mLocationClient.removeLocationUpdates(mLocationCallback)
        }
    }

    private fun drawLocation(location: Location?) {
        location?.let {
            mGoogleMap.moveToMarker(latLng = LatLng(it.latitude, it.longitude), anim = true)
            releaseWork.run()
        }
    }

    private fun showWidgets() {
        mBinding.mapParentLayout.update {
            setGuidelinePercent(R.id.mapTopGuideline, .04f)
            setGuidelinePercent(R.id.mapEndGuideline, 1f)
            setGuidelineEnd(R.id.mapBottomGuideline, mContext.dpToPx(120))
        }

        TransitionManager.beginDelayedTransition(mBinding.mapParentLayout, ChangeBounds().apply {
            duration = 200L
            interpolator = LinearInterpolator()
            addListener(object: Transition.TransitionListener {
                override fun onTransitionEnd(transition: Transition) {
                    // (mContext as? PageInteractionListener)?.showToolbarAndBottomNavigationView()
                }
                override fun onTransitionResume(transition: Transition) {}
                override fun onTransitionPause(transition: Transition) {}
                override fun onTransitionCancel(transition: Transition) {}
                override fun onTransitionStart(transition: Transition) {}
            })
        })
    }

    private fun hideWidgets() {
        mBinding.mapParentLayout.update {
            setGuidelinePercent(R.id.mapTopGuideline, -.5f)
            setGuidelinePercent(R.id.mapEndGuideline, 1.5f)
            setGuidelineEnd(R.id.mapBottomGuideline, 0)
        }

        TransitionManager.beginDelayedTransition(mBinding.mapParentLayout, ChangeBounds().apply {
            duration = 200L
            interpolator = LinearInterpolator()
            addListener(object: Transition.TransitionListener {
                override fun onTransitionEnd(transition: Transition) {
                    // (mContext as? PageInteractionListener)?.hideToolbarAndBottomNavigationView()
                }
                override fun onTransitionResume(transition: Transition) {}
                override fun onTransitionPause(transition: Transition) {}
                override fun onTransitionCancel(transition: Transition) {}
                override fun onTransitionStart(transition: Transition) {}
            })
        })
    }

    companion object {
        private const val FINE_LOCATION_REQUEST_CODE = 0

        private const val LOCATION_UPDATE_INTERVAL = 15000L
        private const val LOCATION_FASTEST_UPDATE_INTERVAL = 5000L


        fun newInstance(): MapFragment = MapFragment()
    }
}
