package com.lychee.sms.receiver

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.provider.Telephony
import android.util.Log
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.places.Places
import com.lychee.data.core.model.Expense
import com.lychee.sms.model.Target
import pub.devrel.easypermissions.EasyPermissions

/* PERMISSION */
@SuppressLint("LogNotTimber")
class SmsRecevier: BroadcastReceiver() {

    private var mGpsProviderEnabled: Boolean = false

    private var mLocationPermissionGranted: Boolean = false

    // above Build.VERSION_CODES.KITKAT( 19 )
    override fun onReceive(context: Context, intent: Intent?) {
        intent
                ?.takeIf { Telephony.Sms.Intents.SMS_RECEIVED_ACTION == it.action }
                ?.apply {
                    val messages = Telephony.Sms.Intents.getMessagesFromIntent(this)
                    val message = messages[0]
                    val address = message.displayOriginatingAddress
                    val body = message.messageBody

                    Log.d(TAG, "번호: $address / 내용: $body")

                    Target.checkTargetAddressAndGetParser(address)
                            ?.let { parser ->
                                val expense = parser.smsToExpense(body)

                                expense?.shopName
                                        ?.let { shop ->
                                            checkLocationPermission(context)

                                            checkGpsProviderEnabled(context)

                                            requestPlaceLikelihood(context, expense, shop)
                                        }
                                        ?:Log.e(TAG, "지원하는 문자 양식이 아닙니다.")
                            }
                            ?:Log.e(TAG, "타겟 카드사가 아닙니다.")
                }
                ?:Log.e(TAG, "문자를 수신하는 BroadCast 가 아닙니다.")
    }

    private fun checkLocationPermission(context: Context) {
        if(!mLocationPermissionGranted) {
            mLocationPermissionGranted =
                    EasyPermissions.hasPermissions(context, android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun checkGpsProviderEnabled(context: Context) {
        if(!mGpsProviderEnabled) {
            mGpsProviderEnabled =
                    (context.getSystemService(Context.LOCATION_SERVICE) as LocationManager).isProviderEnabled(LocationManager.GPS_PROVIDER)
        }
    }

    /**
     * GPS 필수
     */
    private fun requestPlaceLikelihood(context: Context, expense: Expense, shop: String) {
        if(mLocationPermissionGranted && mGpsProviderEnabled) {
            val mPlaceDetectionClient = Places.getPlaceDetectionClient(context)

            @SuppressLint("MissingPermission")
            val result = mPlaceDetectionClient.getCurrentPlace(null)

            result.addOnCompleteListener { task ->
                task
                        .takeIf { it.isSuccessful && it.result != null}
                        ?.result
                        ?.let { response ->

                            for(placeLikelihood in response) {
                                with(placeLikelihood.place) {
                                    val contains = name.contains("씨유미아솔매로점"/*shop*/)

                                    if(contains) {
                                        /*
                                        expense.address = address?.toString()

                                        expense.latitude = latLng.latitude
                                        expense.longitude = latLng.longitude
                                        */

                                        /* SAVE */
                                        Log.d("TAG", expense.toString())

                                        task.result.release()

                                        return@addOnCompleteListener
                                    }
                                    Log.d(TAG, "$name, $address $attributions $latLng")
                                }
                            }

                            /* SAVE */
                            task.result.release()
                        }
                        ?:Log.e(TAG, "Exception: ${task.exception}") /* HANDLE EXCEPTION */
            }
        } else {
            /* HANDLING TODO */
            // GPS 를 사용할 수 없기 때문에 상점명으로 타 Place API 를 사용해야 한다.
            // 또는 Location Not Found.
        }
    }

    /**
     * GPS Provider 를 설정하지 않았거나 사용할 수 없을 경우
     */
    private fun handlePlaceNotFoundException() {

    }

    /**
     * GPS 필수
     */
    @SuppressLint("MissingPermission")
    private fun requestLastLocation(context: Context) {
        if(mLocationPermissionGranted && mGpsProviderEnabled) {
            val mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

            val mLastLocation = mFusedLocationProviderClient.lastLocation

            mLastLocation.addOnCompleteListener { task ->
                task
                        .takeIf { it.isSuccessful && it.result != null}
                        ?.result
                        ?.run {
                            Log.d(TAG, "현재 내 위치는: $latitude, $longitude")
                        }
                        ?:Log.e(TAG, "현재 위치 찾을 수 없음.")
                        /*mFusedLocationProviderClient.requestLocationUpdates(
                                LocationRequest()
                                        .setInterval(10000L)
                                        .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
                                        .setFastestInterval(5000L),

                                object: LocationCallback() {
                                    override fun onLocationResult(locationResult: LocationResult?) {
                                        locationResult?.lastLocation
                                        mFusedLocationProviderClient.removeLocationUpdates(this)
                                    }
                                },

                                Looper.myLooper()
                        )
                        */
            }
        } else {

        }
    }

    companion object {
        private val TAG = SmsRecevier::class.java.simpleName
    }
}