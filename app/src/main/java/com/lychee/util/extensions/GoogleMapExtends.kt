package com.lychee.util.extensions

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

fun GoogleMap.moveToMarker(latLng: LatLng,
                           zoom: Float = 13f,
                           anim: Boolean = true,
                           markerOptions: MarkerOptions
                           = MarkerOptions()
                                   .position(latLng)
                                   .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
) {
    addMarker(markerOptions)

    if(anim) animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))
    else moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))
}