package com.lychee.ui.main

import android.content.Context
import android.location.Geocoder
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.places.GeoDataClient
import com.google.android.gms.location.places.PlaceDetectionClient
import com.google.android.gms.location.places.Places
import com.lychee.di.qualifier.ActivityLevel
import com.lychee.di.scope.ActivityScoped
import dagger.Module
import dagger.Provides

@Module
class MainMapModule {

    @Provides @ActivityScoped
    fun provideFusedLocationProviderClient(@ActivityLevel context: Context): FusedLocationProviderClient
            = LocationServices.getFusedLocationProviderClient(context)

    @Provides @ActivityScoped
    fun provideGeocoder(@ActivityLevel context: Context): Geocoder = Geocoder(context)

    @Provides @ActivityScoped
    fun provideGeoDataClient(@ActivityLevel context: Context): GeoDataClient = Places.getGeoDataClient(context)

    @Provides @ActivityScoped
    fun providePlaceDetectionClient(@ActivityLevel context: Context): PlaceDetectionClient = Places.getPlaceDetectionClient(context)
}