package com.lychee.util.map

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.location.LocationManager
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*

@SuppressLint("MissingPermission")
class GoogleLocationManager constructor(
        private val context: Context,
        private val update: (LocationResult) -> Unit
) {

    private val mFusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    private var mPriority: Int = LocationRequest.PRIORITY_HIGH_ACCURACY
        get() = getPriority()

    private val mLocationRequest: LocationRequest = LocationRequest().apply {
        interval = UPDATE_INTERVAL
        fastestInterval = FASTEST_UPDATE_INTERVAL
        priority = mPriority
    }

    private val mLocationCallback: LocationCallback = object: LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            locationResult
                    ?.let(update)
                    ?: return
        }
    }

    fun LocationSettings() {
        LocationServices
                .getSettingsClient(context)
                .checkLocationSettings(
                        LocationSettingsRequest
                                .Builder()
                                .addLocationRequest(mLocationRequest)
                                .build()
                )
                .addOnSuccessListener {
                    mFusedLocationClient.requestLocationUpdates(
                            mLocationRequest,
                            mLocationCallback,
                            null)
                }
                .addOnFailureListener { exception ->
                    (exception as? ApiException)
                            ?.statusCode
                            ?.let {code ->
                                when(code) {
                                    CommonStatusCodes.RESOLUTION_REQUIRED -> {
                                        val resolvable = exception as? ResolvableApiException
                                        resolvable
                                                ?.startResolutionForResult(context as Activity, 0x01)
                                    }
                                    else -> {

                                    }
                                }
                            }

                }
    }

    fun startLocationUpdates() {
        mFusedLocationClient.requestLocationUpdates (
                mLocationRequest,
                mLocationCallback,
                null)
    }

    fun stopLocationUpdates() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback)
    }

    private fun isGpsProviderAvailable(): Boolean
            = (context.getSystemService(Context.LOCATION_SERVICE) as LocationManager).isProviderEnabled(LocationManager.GPS_PROVIDER)

    private fun isNetworkProviderAvailable(): Boolean
            = (context.getSystemService(Context.LOCATION_SERVICE) as LocationManager).isProviderEnabled(LocationManager.NETWORK_PROVIDER)

    private fun getPriority(): Int {
        return when {
            isGpsProviderAvailable() -> LocationRequest.PRIORITY_HIGH_ACCURACY
            isNetworkProviderAvailable() -> LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
            else -> LocationRequest.PRIORITY_NO_POWER
        }
    }

    companion object {
        private const val UPDATE_INTERVAL = 10000L
        private const val FASTEST_UPDATE_INTERVAL = UPDATE_INTERVAL / 2
    }
}