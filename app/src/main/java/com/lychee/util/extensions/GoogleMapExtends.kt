package com.lychee.util.extensions

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Geocoder
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng


fun Geocoder.getAddress(latLng: LatLng): String {
    return try {
        val addresses = getFromLocation(latLng.latitude, latLng.longitude, 1)

        if(addresses.isEmpty()) {
            "일시적인 오류로 주소를 표시할 수 없습니다."
        } else {
            addresses[0].getAddressLine(0).toString()
        }
    } catch (exception: Exception) {
        "일시적인 오류로 주소를 표시할 수 없습니다."
    }
}

fun GoogleMap.changeCamera(update: CameraUpdate,
                           animate: Boolean,
                           duration: Int = 0,
                           callback: GoogleMap.CancelableCallback? = null) {
    when {
        animate -> {
            if (duration > 0) animateCamera(update, duration, callback)
            else animateCamera(update, callback)
        }
        else -> moveCamera(update)
    }
}

fun GoogleMap.zoomIn(animate: Boolean = true, duration: Int = 1000) {
    changeCamera(
            CameraUpdateFactory.zoomIn(),
            animate,
            duration
    )
}

fun GoogleMap.zoomOut(animate: Boolean = true, duration: Int = 1000) {
    changeCamera(
            CameraUpdateFactory.zoomOut(),
            animate,
            duration
    )
}

fun View.createDrawableFromView(context: Context): Bitmap {

    val displayMetrics = DisplayMetrics()
    (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
    layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    measure(displayMetrics.widthPixels, displayMetrics.heightPixels)
    layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)
    buildDrawingCache()
    val bitmap = Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888)

    val canvas = Canvas(bitmap)
    draw(canvas)

    return bitmap
}