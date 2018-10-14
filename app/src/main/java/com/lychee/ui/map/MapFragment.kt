package com.lychee.ui.map

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.transition.ChangeBounds
import android.support.transition.TransitionManager
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.lychee.R
import com.lychee.data.core.model.Expense
import com.lychee.databinding.FragmentMapBinding
import com.lychee.ui.base.ui.BaseMapFragment
import com.lychee.ui.main.NavigationFragment
import com.lychee.util.extensions.changeCamera
import com.lychee.util.extensions.dpToPx
import com.lychee.util.extensions.update
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import javax.inject.Inject

@SuppressLint("MissingPermission")
class MapFragment:
        BaseMapFragment<FragmentMapBinding, MapViewModel>(),
        NavigationFragment
{

    override val layoutResId: Int
        get() = R.layout.fragment_map

    override val viewModelClass: Class<MapViewModel>
        get() = MapViewModel::class.java

    private var mPreviousMarker: Marker? = null

    private val mMarkers: MutableList<Marker> = mutableListOf()

    @Inject lateinit var mFusedLocationProviderClient: FusedLocationProviderClient

    lateinit var mGoogleMap: GoogleMap

    private val onMapReadyCallback = object: OnMapReadyCallback {
        override fun onMapReady(map: GoogleMap?) {
            mGoogleMap = map ?: return

            /* UI 세팅 */
            with(mGoogleMap.uiSettings) {
                // Hide the zoom controls as the button panel will cover it.
                isZoomControlsEnabled = false
                isZoomGesturesEnabled = true
                isRotateGesturesEnabled = true
                isScrollGesturesEnabled = true
                isCompassEnabled = true
                isMyLocationButtonEnabled = false
                isIndoorLevelPickerEnabled = false
            }

            /* MAP 세팅 */
            with(mGoogleMap) {
                // my location button
                mBinding.mapMyLocationButton.setOnClickListener {
                    if(!checkLocationPermission()) requestLocationPermission()
                    else {
                        isMyLocationEnabled = true

                        getLastLocation()
                    }
                }

                // controller ui animation
                setOnMapClickListener {
                    if(mViewModel.expandDetail.value!!) {
                        mViewModel.showDetailPager(false)
                    } else {
                        if(mPreviousMarker != null) {
                            mPreviousMarker?.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.round_place_black_24))
                            mPreviousMarker = null

                            mViewModel.showController()
                        } else {
                            mViewModel.showOrHideController()
                        }
                    }
                }

                // marker animation
                setOnMarkerClickListener {marker ->
                    mViewModel.setCurrentPosition(mMarkers.indexOf(marker))

                    true
                }
            }

            mViewModel.fetchExpenses()
        }
    }

    override fun FragmentMapBinding.onCreateView(
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ) {
        mMapView = mapGoogleMap
        mMapView.onCreate(savedInstanceState)

        // Bitmap Descriptor Factory and Camera Update Factory will be initialized.
        MapsInitializer.initialize(requireContext())

        mMapView.getMapAsync(onMapReadyCallback)

        mapViewPager.adapter = MapViewPagerAdapter { mViewModel.showDetailPager(true) } // TODO CALLBACK
        mapDetailViewPager.adapter = MapDetailViewPagerAdapter()

        // Sync
        mapViewPager.addOnPageChangeListener(object: ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                mViewModel.setCurrentPosition(position)
            }
        })

        mapDetailViewPager.addOnPageChangeListener(object: ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                mViewModel.setCurrentPosition(position)
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.viewModel = mViewModel

        mViewModel.expenses.observe(this, Observer { expenses ->
            expenses
                    ?.takeIf { it.isNotEmpty() }
                    ?.let {
                        mMarkers.clear()

                        val boundsBuilder = LatLngBounds.Builder()

                        expenses
                                .forEach {expense ->
                                    /*
                                    expense
                                            .takeIf { it.latitude != -1.0 && it.longitude != -1.0 }
                                            ?.let {
                                                val latLng = LatLng(it.latitude, it.longitude)

                                                // add to google map
                                                mGoogleMap.addMarker(
                                                        MarkerOptions()
                                                                .position(latLng)
                                                                .title(it.shop)
                                                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.round_place_black_24))
                                                        // add to local variable
                                                ).let { mMarkers.add(it) }

                                                boundsBuilder.include(latLng)
                                            }
                                            */
                                }

                        val bounds = boundsBuilder.build()

                        mGoogleMap.changeCamera(CameraUpdateFactory.newLatLngBounds(bounds, requireContext().dpToPx(64)), true)

                        fetchExpendituresToAdapter(expenses = expenses as MutableList<Expense>)

                        mViewModel.setCurrentPosition(0) // Init Position
                    }
        })

        mViewModel.currentItem.observe(this, Observer { currentItem ->
            currentItem
                    ?.takeIf { it < mMarkers.size }
                    ?.let { index ->
                        updatePagerPosition(index)

                        val currentMarker = mMarkers[index]

                        mPreviousMarker?.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.round_place_black_24))

                        updateCurrentMarker(currentMarker)

                        if(mViewModel.expandDetail.value!!) {
                            updateCameraWhenOpenDetailPager(currentMarker.position)
                        } else {
                            mGoogleMap.changeCamera(CameraUpdateFactory.newLatLngZoom(currentMarker.position, 14f), true)
                        }

                        mViewModel.hideController()
                    }
        })

        mViewModel.expandDetail.observe(this, Observer {
            if(it!!) {
                mBinding.mapControllerParentLayout.update {
                    setGuidelinePercent(R.id.mapTopGuideline, -.5f)
                    setGuidelinePercent(R.id.mapBottomGuideline, .3f)
                    setGuidelinePercent(R.id.mapStartGuideline, -.5f)
                    setGuidelinePercent(R.id.mapEndGuideline, 1.5f)
                }

                TransitionManager.beginDelayedTransition(mBinding.mapControllerParentLayout, ChangeBounds().apply {
                    duration = 1000L
                    interpolator = AccelerateDecelerateInterpolator()
                })

                val currentItem = mViewModel.currentItem.value!! // SHOULD NOT NULL
                if(currentItem < mMarkers.size) {
                    val currentMarker = mMarkers[currentItem]

                    updateCurrentMarker(currentMarker)

                    updateCameraWhenOpenDetailPager(currentMarker.position)
                }
            } else {
                mBinding.mapControllerParentLayout.update {
                    setGuidelinePercent(R.id.mapBottomGuideline, 1f)
                }

                TransitionManager.beginDelayedTransition(mBinding.mapControllerParentLayout, ChangeBounds().apply {
                    duration = 1000L
                    interpolator = AccelerateDecelerateInterpolator()
                })

                val currentItem = mViewModel.currentItem.value!! // SHOULD NOT NULL
                if(currentItem < mMarkers.size) {
                    val currentMarker = mMarkers[currentItem]

                    updateCurrentMarker(currentMarker)

                    mGoogleMap.changeCamera(CameraUpdateFactory.newLatLngZoom(currentMarker.position, 14f), true)
                }
            }
        })
    }

    private fun fetchExpendituresToAdapter(expenses: MutableList<Expense>) {
        with(mBinding) {
            (mapViewPager.adapter as MapViewPagerAdapter).expenses = expenses
            (mapDetailViewPager.adapter as MapDetailViewPagerAdapter).expenses = expenses
        }
    }

    private fun checkLocationPermission()
            = EasyPermissions.hasPermissions(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)

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
        (mBinding.mapGoogleMap.findViewById<View>(Integer.parseInt("1")).parent as View)
                .findViewById<View>(Integer.parseInt("2")).callOnClick()

        mFusedLocationProviderClient.lastLocation
                .addOnCompleteListener { task ->
                    if(task.isSuccessful && task.result != null) {
                        val latLng = LatLng (
                                task.result.latitude,
                                task.result.longitude)

                        mGoogleMap.changeCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14f), true)
                    } else {
                        /*  */
                    }
                }
    }

    private fun updatePagerPosition(position: Int) {
        mBinding.mapViewPager.currentItem = position
        mBinding.mapDetailViewPager.currentItem = position
    }

    private fun updateCameraWhenOpenDetailPager(currentLatLng: LatLng) {

        val latLngPoint = mGoogleMap.projection.toScreenLocation(currentLatLng)

        latLngPoint.y += (mBinding.mapGoogleMap.height * .35f).toInt() - requireContext().dpToPx(12)

        val targetCameraPosition = mGoogleMap.projection.fromScreenLocation(latLngPoint)

        mGoogleMap.changeCamera(CameraUpdateFactory.newLatLng(targetCameraPosition), true)
    }

    private fun updateCurrentMarker(currentMarker: Marker) {
        currentMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.round_place_black_36))
        currentMarker.showInfoWindow()

        mPreviousMarker = currentMarker
    }

    override fun onBackPressed(): Boolean {
        return super.onBackPressed()
    }

    companion object {
        val TAG = MapFragment::class.java.simpleName

        private const val LOCATION_PERMISSION_REQUEST_CODE = 42

        fun newInstance(): MapFragment = MapFragment()
    }
}

