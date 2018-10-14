package com.lychee.ui.home

import android.location.Geocoder
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.lychee.R
import com.lychee.databinding.FragmentHomeBinding
import com.lychee.ui.base.ui.BaseMapFragment
import com.lychee.ui.main.MainActivity
import com.lychee.ui.main.MainViewModel
import com.lychee.ui.main.NavigationFragment
import com.lychee.util.extensions.getActivityViewModel
import pub.devrel.easypermissions.EasyPermissions
import javax.inject.Inject

/**
 * Home 화면
 */
class HomeFragment : BaseMapFragment<FragmentHomeBinding, HomeViewModel>(),
        NavigationFragment
{

    override val layoutResId: Int
        get() = R.layout.fragment_home

    override val viewModelClass: Class<HomeViewModel>
        get() = HomeViewModel::class.java

    private lateinit var mActivityViewModel: MainViewModel

    @Inject lateinit var mFusedLocationProviderClient: FusedLocationProviderClient

    @Inject lateinit var mGeocoder: Geocoder

    lateinit var mGoogleMap: GoogleMap

    private val mIcon: BitmapDescriptor by lazy { BitmapDescriptorFactory.fromResource(R.drawable.round_place_black_36) }

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
                setOnMapClickListener { (requireActivity() as MainActivity).navigateToMapPage() }
            }

            mViewModel.fetchExpenses()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mActivityViewModel = getActivityViewModel(viewModelFactory, MainViewModel::class.java)
    }

    override fun FragmentHomeBinding.onCreateView(
            container: ViewGroup?,
            savedInstanceState: Bundle?)
    {
        mMapView = homeMapView
        mMapView.onCreate(savedInstanceState)
        // Bitmap Descriptor Factory and Camera Update Factory will be initialized.
        // MapsInitializer.initialize(requireContext())

        mMapView.getMapAsync(onMapReadyCallback)

        // home nav image view click > animation + activity listen
        homeRecentRecyclerView.apply {
            adapter = HomeRecyclerViewAdapter(this@HomeFragment)
            layoutManager = LinearLayoutManager(context)
        }

        // homeMonthMoreInfoTextView.setOnClickListener { mViewModel.fetchMonthOverview() }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchWeatherByCoordinates()

//        mViewModel.expenses.observe(this, Observer { expenses ->
//            expenses?.takeIf { it.isNotEmpty() } ?: return@Observer
//
//            expenses.forEach { expense ->
//                /*
//                if(expense.latitude != -1.0
//                        && expense.longitude != -1.0) {
//
//                }
//                expense
//                        .takeIf { it.latitude != -1.0 && it.longitude != -1.0 }
//                        ?.apply {
//                            val latLng = LatLng(latitude, longitude)
//
//                            mGoogleMap.addMarker(
//                                    MarkerOptions()
//                                            .position(latLng)
//                                            .icon(mIcon))
//
//                            mGoogleMap.changeCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f), true)
//
//                            mBinding.homeMapShopTextView.text = "$shop\n$address"
//                        }
//                        */
//            }
//
//            // fetchExpenses(expenses)
//        })
    }

    private fun fetchWeatherByCoordinates() {
        if(EasyPermissions.hasPermissions(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            @SuppressWarnings("MissingPermission")
            val mLastLocation = mFusedLocationProviderClient.lastLocation

            mLastLocation.addOnCompleteListener { task ->
                if(task.isSuccessful && task.result != null) {
                    val latLng = LatLng (
                            task.result.latitude,
                            task.result.longitude)

                    mViewModel.fetchWeatherByCoordinates(latLng)
                } else {
                    // mBinding.homeWeatherTextView.text = "날씨를 표시할 수 없습니다."
                }
            }
        } else {
            // mBinding.homeWeatherTextView.text = "날씨를 표시할 수 없습니다."
        }
    }

    /*
    private fun fetchExpenses(expenses: List<Expense>) {
        val adapter = mBinding.homeRecentRecyclerView.adapter as? HomeRecentRecyclerViewAdapter
        adapter?.expenses = expenses as MutableList<Expense>
    }
    */

    override fun onBackPressed(): Boolean {
        return false
    }

    companion object {
        val TAG = HomeFragment::class.java.simpleName

        fun newInstance(): HomeFragment = HomeFragment()
    }
}
