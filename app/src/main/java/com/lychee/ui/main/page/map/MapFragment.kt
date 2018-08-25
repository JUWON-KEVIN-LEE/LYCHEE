package com.lychee.ui.main.page.map

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.os.Handler
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.View
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import com.lychee.R
import com.lychee.databinding.FragmentMapBinding
import com.lychee.ui.base.BaseMapFragment
import com.lychee.ui.main.page.map.adapter.MapDetailViewPagerAdapter
import com.lychee.ui.main.page.map.adapter.MapViewPagerAdapter
import com.lychee.util.extensions.changeCamera
import com.lychee.util.extensions.dpToPx
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

@SuppressLint("MissingPermission")
class MapFragment:
        BaseMapFragment<FragmentMapBinding, MapViewModel>()
{

    override val layoutResId: Int
        get() = R.layout.fragment_map

    override val viewModelClass: Class<MapViewModel>
        get() = MapViewModel::class.java

    private var mPreviousMarker: Marker? = null

    private val mFusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(mContext)
    }

    lateinit var mGoogleMap: GoogleMap

    private val onMapReadyCallback = object: OnMapReadyCallback {
        override fun onMapReady(map: GoogleMap?) {
            mGoogleMap = map ?: return

            // ui settings
            with(mGoogleMap.uiSettings) {
                // Hide the zoom controls as the button panel will cover it.
                isZoomControlsEnabled = false
                isZoomGesturesEnabled = true
                isRotateGesturesEnabled = true
                isScrollGesturesEnabled = true
                isCompassEnabled = false
                isMyLocationButtonEnabled = false
                isIndoorLevelPickerEnabled = false
            }

            // map settings
            with(mGoogleMap) {
                // my location button
                mBinding.mapMyLocationButton.setOnClickListener {
                    if(!checkLocationPermission()) requestLocationPermission()
                    else {
                        isMyLocationEnabled = true

                        (mBinding.mapGoogleMap.findViewById<View>(Integer.parseInt("1")).parent as View)
                                .findViewById<View>(Integer.parseInt("2")).callOnClick()

                        getLastLocation()
                    }
                }

                // controller ui animation
                setOnMapClickListener {
                    if(mPreviousMarker != null) {
                        mPreviousMarker?.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.round_place_black_24))
                        mPreviousMarker = null
                    } else {
                        mViewModel.showOrHideController()
                    }
                }

                // marker animation
                setOnMarkerClickListener {marker ->
                    mPreviousMarker
                            ?.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.round_place_black_24))

                    marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.round_place_black_36))

                    mPreviousMarker = marker

                    false
                }
            }

            // TEST
            Handler().postDelayed({ mViewModel.fetchExpenditures() }, 5000L)
        }
    }

    override fun onCreateView(savedInstanceState: Bundle?) {
        with(mBinding) {
            mMapView = mapGoogleMap
            mMapView.onCreate(savedInstanceState)

            // Bitmap Descriptor Factory and Camera Update Factory will be initialized.
            MapsInitializer.initialize(mContext)

            mMapView.getMapAsync(onMapReadyCallback)

            mapViewPager.adapter = MapViewPagerAdapter { mViewModel.openOrCloseDetail() } // TODO OPEN WITH EXP
            mapDetailViewPager.adapter = MapDetailViewPagerAdapter()

            // Sync
            mapViewPager.addOnPageChangeListener(object: ViewPager.SimpleOnPageChangeListener() {
                override fun onPageSelected(position: Int) { mapDetailViewPager.currentItem = position }
            })
            mapDetailViewPager.addOnPageChangeListener(object: ViewPager.SimpleOnPageChangeListener() {
                override fun onPageSelected(position: Int) { mapViewPager.currentItem = position }
            })
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.viewModel = mViewModel

        mViewModel.expenditures.observe(this, Observer {
            it?.let { expenditures ->
                val boundsBuilder = LatLngBounds.Builder()

                expenditures
                        .forEach {
                            val latLng = LatLng(it.lat, it.lng)
                            mGoogleMap.addMarker(
                                    MarkerOptions()
                                            .position(latLng)
                                            .title(it.shopName)
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.round_place_black_24))
                            )

                            boundsBuilder.include(latLng)
                        }

                val bounds = boundsBuilder.build()

                mGoogleMap.changeCamera(CameraUpdateFactory.newLatLngBounds(bounds, mContext.dpToPx(32)), true)

                with(mBinding) {
                    val data = expenditures.toMutableList()

                    (mapViewPager.adapter as? MapViewPagerAdapter)?.expenditures = data
                    (mapDetailViewPager.adapter as? MapDetailViewPagerAdapter)?.expenditures = data
                }
            }
        })
    }

    private fun checkLocationPermission()
            = EasyPermissions.hasPermissions(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION)

    private fun requestLocationPermission()
            = EasyPermissions.requestPermissions(
            this,
            getString(R.string.permission_rationale_location),
            LOCATION_PERMISSION_REQUEST_CODE,
            android.Manifest.permission.ACCESS_FINE_LOCATION)

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, object: EasyPermissions.PermissionCallbacks {
            override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {}
            override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
                when(requestCode) {
                    LOCATION_PERMISSION_REQUEST_CODE -> getLastLocation()
                /* ... */
                }
            }
            override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
                when(requestCode) {
                    LOCATION_PERMISSION_REQUEST_CODE -> {
                        if(grantResults.isEmpty()) Log.i(TAG, "User Interaction was cancelled.")
                    }
                }
            }
        })
    }

    @AfterPermissionGranted(LOCATION_PERMISSION_REQUEST_CODE)
    private fun getLastLocation() {
        mFusedLocationProviderClient.lastLocation
                .addOnCompleteListener { task ->
                    if(task.isSuccessful && task.result != null) {
                        val latLng = LatLng (
                                task.result.latitude,
                                task.result.longitude)

                        updateCurrentLocation(latLng)
                    } else {
                        /* */
                    }
                }
    }

    private fun updateCurrentLocation(latLng: LatLng) {
        mGoogleMap.changeCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18f), true)
    }

    companion object {
        private val TAG = MapFragment::class.java.simpleName

        const val REQUEST_CHECK_SETTINGS = 0x1

        private const val LOCATION_PERMISSION_REQUEST_CODE = 42
        private const val UPDATE_INTERVAL = 10000L
        private const val FASTEST_UPDATE_INTERVAL = UPDATE_INTERVAL / 2

        fun newInstance(): MapFragment = MapFragment()
    }
}

